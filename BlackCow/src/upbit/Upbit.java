package upbit;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonObject;

import gui.GUI;
import gui.OrderTableElement;
import javafx.scene.shape.Cylinder;
import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;
import upbit.JsonManager.JsonKey;
import upbit.Request.TermType;

public class Upbit
{
	private ArrayList<CryptoCurrency> cryptoList;
	private ArrayList<OrderBook> orderList;
	
	private DynamicCrawler crawler;
	private Account account;
	private GUI gui;
	
	private ScheduledThreadPoolExecutor scheduledExecutor;
	private ExecutorService executorService; 
	

	
	private int refreshTime;

	public Upbit(Account account)
	{
		setAccount(account);
		
		cryptoList = new ArrayList<CryptoCurrency>();
		orderList = new ArrayList<OrderBook>();

		loadCryptoCurrency();
	}
	
	public void initiate()
	{
		autoUpdate();
	}
	
	public void autoUpdate()
	{
		ScheduledThreadPoolExecutor executor = getScheduledExecutor();
		Market market = Market.KRW;
		
		int updateDelay = 5;
		int startDelay = 60 - Calendar.getInstance().get(Calendar.SECOND) + 1;
		
		executor.scheduleAtFixedRate(()->updateData(market, TermType.minutes, 1, 2), 0, updateDelay, TimeUnit.SECONDS);
		executor.scheduleAtFixedRate(()->updateData(market, TermType.days, 1, 1), 1, updateDelay, TimeUnit.SECONDS);	
		executor.scheduleAtFixedRate(()->updateData(market, TermType.minutes, 60, 2), startDelay, 3600, TimeUnit.SECONDS);

		executor.scheduleAtFixedRate(()->getGui().updateCoinTable(market), 3, updateDelay, TimeUnit.SECONDS);
	}
	
	public void loadCryptoCurrency()
	{
		updateData(Market.KRW, TermType.minutes, 60, 24);
	}
	
	public void updateData(Market market, TermType termType, int term, int dataAmount)
	{
		String url;
		LinkedList<JSONObject> list;
		ArrayList<CoinSymbol> listCoinSymbol = new ArrayList<>();
		CryptoCurrency cryptoCurrency;
		
		switch (market)
		{
		case KRW:
			listCoinSymbol = CoinList.listKRW;
			break;
			
		case BTC:
			listCoinSymbol = CoinList.listBTC;
			break;
			
		case ETH:
			listCoinSymbol = CoinList.listETH;
			break;
		}
		
		for (CoinSymbol coinSymbol : listCoinSymbol)
		{
			try
			{
				cryptoCurrency = requestData(market, coinSymbol, termType, term, dataAmount);
			}
			catch (Exception e1)
			{
				continue;
			}
			
			try
			{
				getCryptoCurrency(createName(market, coinSymbol, termType, term)).addData(cryptoCurrency);
			}
			catch (Exception e)
			{
				addCryptoCurrency(cryptoCurrency);
			}
		}
	}
	
	public void updateOrderBook(Market market, CoinSymbol coinSymbol)
	{
		OrderBook orderBook;
		
		orderBook = crawler.getOrderBook(market, coinSymbol);
		addOrderBook(orderBook);
		gui.updateOrderTable(market, coinSymbol);
	}
	
	public CryptoCurrency requestData(Market market, CoinSymbol coinSymbol, TermType termType, int term, int dataAmount) throws Exception
	{
		String url = Request.Upbit.createUrl(market, coinSymbol, termType, term, dataAmount);
		LinkedList<JSONObject> list;
		CryptoCurrency cryptoCurrency;
		
		try
		{
			list = JsonManager.parse(Request.request(url));
			cryptoCurrency = new CryptoCurrency(list, market, coinSymbol, termType, term);
			return cryptoCurrency;
		}
		catch (Exception e)
		{
			throw new Exception();
		}
	}
	
	public boolean buy(Market market, CoinSymbol coinSymbol, double price, double quantity)
	{
		double totalPrice = price * quantity;
		double balance = 0;
		
		if (market == Market.KRW)
			Math.round(totalPrice);
		
		switch (market)
		{
		case KRW:
			balance = account.getKRW();
			account.subtractKRW((long) totalPrice);
			break;
			
		case BTC:
			balance = account.getBalance(CoinSymbol.BTC);
			account.subtractBalance(CoinSymbol.BTC, totalPrice);
			break;
			
		case ETH:
			balance = account.getBalance(CoinSymbol.ETC);
			account.subtractBalance(CoinSymbol.ETC, totalPrice);
			break;
		}
		
		if (balance >= totalPrice)
		{
			account.addBalance(coinSymbol, quantity, price);
			
			return true;
		}

		return false;
	}
	
	public boolean sell(Market market, CoinSymbol coinSymbol, double price, double quantity)
	{
		double totalPrice = price * quantity;
		double balance = account.getBalance(coinSymbol);
		
		if (market == Market.KRW)
			Math.round(totalPrice);
				
		switch (market)
		{
		case KRW:
			account.addKRW((long) totalPrice);
			break;
			
		case BTC:
			account.addBalance(coinSymbol, totalPrice, price);
			break;
			
		case ETH:
			account.addBalance(coinSymbol, totalPrice, price);
			break;
		}
		
		if (balance >= quantity)
		{
			account.subtractBalance(coinSymbol, quantity);
			return true;
		}
		
		return false;
	}
	
	public double getTradePrice(Market market, CoinSymbol coinSymbol)
	{
		CryptoCurrency cryptoCurrency;
		double tradePrice = 0;
		
		try
		{
			cryptoCurrency = getCryptoCurrency(createName(market, coinSymbol, TermType.minutes, 1));
			tradePrice = Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice));
		}
		catch (Exception e)
		{
			System.out.println("Failed to getTradePrice");
			e.printStackTrace();
		}
		
		return tradePrice;
	}
	
	public CryptoCurrency getCryptoCurrency(String name) throws Exception
	{
		for (CryptoCurrency cryptoCurrency : cryptoList)
		{
			if (cryptoCurrency.getName().equals(name))
				return cryptoCurrency;
		}
		
		throw new Exception();
	}
	
	public void addCryptoCurrency(CryptoCurrency cryptoCurrency)
	{
		for (CryptoCurrency currency : cryptoList)
		{
			if (currency.getName().equals(cryptoCurrency.getName()))
				return;
		}

		cryptoList.add(cryptoCurrency);
	}
	
	public OrderBook getOrderBook(Market market, CoinSymbol coinSymbol) throws Exception
	{
		for (OrderBook orderBook : orderList)
		{
			if (orderBook.getMarket() == market && orderBook.getCoinSymbol() == coinSymbol)
				return orderBook;
		}

		throw new Exception();
	}
	
	public void addOrderBook(OrderBook orderBook)
	{
		for (OrderBook book : orderList)
		{
			if (orderBook.getMarket() == book.getMarket() && orderBook.getCoinSymbol() == book.getCoinSymbol())
			{
				book = orderBook;
				return;
			}
		}
		
		orderList.add(orderBook);
	}
	
	public String createName(Market market, CoinSymbol coinSymbol, TermType termType, int term)
	{
		return market + "-" + coinSymbol + "-" + termType + "-" + term;
	}
	
	public double getVolume(Market market, CoinSymbol coinSymbol, int hours)
	{
		CryptoCurrency cryptoCurrency;
		double volume = 0;
		
		try
		{
			cryptoCurrency = getCryptoCurrency(createName(market, coinSymbol, TermType.minutes, 60));
		}
		catch (Exception e)
		{
			try
			{
				cryptoCurrency = requestData(market, coinSymbol, TermType.minutes, 60, hours);
				addCryptoCurrency(cryptoCurrency);
			}
			catch (Exception e1)
			{
				return 0;
			}
		}
		
		for (int index = 0; index < hours; index++)
		{
			volume += Double.parseDouble(cryptoCurrency.getData(JsonKey.candleAccTradePrice, index));
		}

		return volume;
	}
	
	
	// Getter, Setter
	public Account getAccount()
	{
		return account;
	}
	
	public void setAccount(Account account)
	{
		this.account = account;
	}
	
	public int getRefreshTime()
	{
		return refreshTime;
	}
	
	public void setRefreshTime(int refreshTime)
	{
		this.refreshTime = refreshTime;
	}
	
	public ArrayList<CryptoCurrency> getCryptoList()
	{
		return cryptoList;
	}
	
	public GUI getGui()
	{
		return gui;
	}

	public void setGui(GUI gui)
	{
		this.gui = gui;
	}

	public ScheduledThreadPoolExecutor getScheduledExecutor()
	{
		return scheduledExecutor;
	}

	public void setScheduledExecutor(ScheduledThreadPoolExecutor scheduledExecutor)
	{
		this.scheduledExecutor = scheduledExecutor;
	}

	public DynamicCrawler getCrawler()
	{
		return crawler;
	}

	public void setCrawler(DynamicCrawler crawler)
	{
		this.crawler = crawler;
	}

	public ArrayList<OrderBook> getOrderList()
	{
		return orderList;
	}

	public void setOrderList(ArrayList<OrderBook> orderList)
	{
		this.orderList = orderList;
	}

	public ExecutorService getExecutorService()
	{
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService)
	{
		this.executorService = executorService;
	}
}
