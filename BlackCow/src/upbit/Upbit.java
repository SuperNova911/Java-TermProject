package upbit;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jfree.date.DateUtilities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonObject;

import gui.GUI;
import gui.OrderTableElement;
import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;
import upbit.JsonManager.JsonKey;
import upbit.Request.TermType;

public class Upbit
{
	private ArrayList<CryptoCurrency> cryptoList;
	private ArrayList<OrderBook> orderBookList;
	
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
		orderBookList = new ArrayList<OrderBook>();

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

		executor.scheduleAtFixedRate(() -> updateData(gui.getCurrentMarket(), TermType.minutes, 1, 2), 0, updateDelay,
				TimeUnit.SECONDS);
		executor.scheduleAtFixedRate(() -> updateData(gui.getCurrentMarket(), TermType.days, 1, 1), 1, updateDelay,
				TimeUnit.SECONDS);
		executor.scheduleAtFixedRate(() -> updateData(gui.getCurrentMarket(), TermType.minutes, 60, 2), startDelay,
				3600, TimeUnit.SECONDS);

		executor.scheduleAtFixedRate(() ->
		{
			gui.updateCoinTable(gui.getCurrentMarket());
			gui.updateInfo(gui.getCurrentMarket(), gui.getCurrentCoinSymbol());
		}, 3, updateDelay, TimeUnit.SECONDS);

		executor.scheduleAtFixedRate(() ->
		{
			if (crawler.isReady())
				updateOrderBook(gui.getCurrentMarket(), gui.getCurrentCoinSymbol());
			confirmOrder();
			gui.updateTradeHistoryTable();
		}, 0, updateDelay, TimeUnit.SECONDS);

		executor.scheduleAtFixedRate(() ->
		{
			updateData(gui.getCurrentMarket(), gui.getCurrentTermType(), gui.getCurrentTerm(), 3);
			gui.updateChart(gui.getCurrentMarket(), gui.getCurrentCoinSymbol(), gui.getCurrentTermType(), gui.getCurrentTerm(), 0, 99);
		}, 3, updateDelay, TimeUnit.SECONDS);
	}
	
	public void loadCryptoCurrency()
	{
		updateData(Market.KRW, TermType.minutes, 1, 100);
		updateData(Market.KRW, TermType.minutes, 3, 100);
		updateData(Market.KRW, TermType.minutes, 5, 100);
		updateData(Market.KRW, TermType.minutes, 10, 100);
		updateData(Market.KRW, TermType.minutes, 15, 100);
		updateData(Market.KRW, TermType.minutes, 30, 100);
		updateData(Market.KRW, TermType.minutes, 60, 100);
		updateData(Market.KRW, TermType.minutes, 240, 100);
		updateData(Market.KRW, TermType.days, 1, 100);
		updateData(Market.KRW, TermType.weeks, 1, 100);
		updateData(Market.KRW, TermType.months, 1, 100);
	}
	
	public void updateData(Market market, TermType termType, int term, int dataAmount)
	{
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
		gui.updateOrderTable(market, coinSymbol);
		
		OrderBook orderBook = crawler.getOrderBook_Fast(market, coinSymbol);
		
		try
		{
			CryptoCurrency cryptoCurrency = getCryptoCurrency(createName(market, coinSymbol, TermType.minutes, 1));
			double tradePrice = Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice));
			
			for (int i = 0; i < orderBook.getOrderBookElementList().size(); i++)
			{
				if (orderBook.getOrderBookElement(i).getPrice() == tradePrice)
				{
					orderBook.setTradeIndex(i);
					break;
				}
			}
		}
		catch (Exception e)
		{
			orderBook.setTradeIndex(10);
		}
		
		addOrderBook(orderBook);
		gui.updateOrderTable(market, coinSymbol);
	}
	
	public void confirmOrder()
	{
		ArrayList<Order> orderList = account.getOrderList();
		
		double orderPrice, tradePrice;
		
		for (Order order : orderList)
		{
			if (order.isConclusion())
				continue;
			
			orderPrice = order.getTradePrice();
			
			try
			{
				tradePrice = Double.parseDouble(getCryptoCurrency(createName(order.getMarket(), order.getCoinSymbol(), TermType.minutes, 1)).getData(JsonKey.tradePrice));
			}
			catch (Exception e)
			{
				try
				{
					tradePrice = Double.parseDouble(requestData(order.getMarket(), order.getCoinSymbol(), TermType.minutes, 1, 1).getData(JsonKey.tradePrice));
				}
				catch (Exception e1)
				{
					return;
				}
			}
			
			if (order.isBuy())
			{
				if (orderPrice >= tradePrice)
				{
					buy(order);
					order.setQuantity_Conclusion(order.getQuantity());
					order.setConclusion(true);
					
					System.out.println("Order confirmed");
				}
			}
			else
			{
				if (orderPrice <= tradePrice)
				{
					sell(order);
					order.setQuantity_Conclusion(order.getQuantity());
					order.setConclusion(true);

					System.out.println("Order confirmed");
				}
			}
		}
		
		gui.updateBuySell(true);
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
	
	public void createOrder(Market market, CoinSymbol coinSymbol, double tradePrice, double quantity, boolean buy)
	{
		Order order;
		Date date = new Date();

		int id = 0;	// юс╫ц

		order = new Order(market, coinSymbol, id, date, tradePrice, quantity, buy);
		
		System.out.println(order.toString());
		
		account.getOrderList().add(order);
	}
	

	public boolean buy(Order order)
	{
		Market market = order.getMarket();
		CoinSymbol coinSymbol = order.getCoinSymbol();
		
		double totalPrice = order.getTotalPrice();
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

		account.addBalance(coinSymbol, order.getQuantity(), order.getTradePrice());
		return true;
	}
	
	public boolean sell(Order order)
	{
		Market market = order.getMarket();
		CoinSymbol coinSymbol = order.getCoinSymbol();

		double totalPrice = order.getTotalPrice();
		double balance = account.getBalance(coinSymbol);
		
		if (market == Market.KRW)
			Math.round(totalPrice);
				
		switch (market)
		{
		case KRW:
			account.addKRW((long) totalPrice);
			break;
			
		case BTC:
			account.addBalance(coinSymbol, totalPrice, order.getTradePrice());
			break;
			
		case ETH:
			account.addBalance(coinSymbol, totalPrice, order.getTradePrice());
			break;
		}

		account.subtractBalance(coinSymbol, order.getQuantity());
		return true;
		
//		if (balance >= order.getQuantity())
//		{
//			return true;
//		}
//		
//		return false;
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
		for (OrderBook orderBook : orderBookList)
		{
			if (orderBook.getMarket() == market && orderBook.getCoinSymbol() == coinSymbol)
				return orderBook;
		}

		throw new Exception();
	}
	
	public void addOrderBook(OrderBook orderBook)
	{
		for (OrderBook book : orderBookList)
		{
			if (orderBook.getMarket() == book.getMarket() && orderBook.getCoinSymbol() == book.getCoinSymbol())
			{
				orderBookList.remove(book);
				orderBookList.add(orderBook);
				
				return;
			}
		}
		orderBookList.add(orderBook);
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

	public ArrayList<OrderBook> getOrderBookList()
	{
		return orderBookList;
	}

	public void setOrderBookList(ArrayList<OrderBook> orderBookList)
	{
		this.orderBookList = orderBookList;
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
