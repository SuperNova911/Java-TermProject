package upbit;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;
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
	private SaveManager saveManager;
	
	private ScheduledThreadPoolExecutor scheduledExecutor;
	private ExecutorService executorService; 
	

	
	private int refreshTime;

	public Upbit(Account account)
	{
		setAccount(account);
		
		cryptoList = new ArrayList<CryptoCurrency>();
		orderBookList = new ArrayList<OrderBook>();
	}
	
	public void initiate()
	{
		loadCryptoCurrency();
		autoUpdate();

		//saveManager.manualSave();
	}
	
	public void autoUpdate()
	{
		ScheduledThreadPoolExecutor executor = getScheduledExecutor();

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
			gui.updateCoinTable();
			gui.updateMyCoinTable();
			gui.updateTradeHistoryTable();
			gui.updateInfo();
			gui.updateAsset();
			confirmOrder();
		}, 3, updateDelay, TimeUnit.SECONDS);

		executor.scheduleAtFixedRate(() ->
		{
			if (crawler.isReady())
			{
				gui.showOrderBook(true);
				updateOrderBook(gui.getCurrentMarket(), gui.getCurrentCoinSymbol());
			}
			
		}, 0, 2, TimeUnit.SECONDS);

		executor.scheduleAtFixedRate(() ->
		{
			updateData(gui.getCurrentMarket(), gui.getCurrentTermType(), gui.getCurrentTerm(), 100);
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
					account.addBalance(order.getCoinSymbol(), order.getQuantity(), order.getTradePrice());
					order.setQuantity_Conclusion(order.getQuantity());
					order.setConclusion(true);
				}
			}
			else
			{
				if (orderPrice <= tradePrice)
				{
					switch (order.getMarket())
					{
					case KRW:
						account.addKRW(Math.round(order.getTotalPrice()));
						break;
						
					case BTC:
						account.addBalance(CoinSymbol.BTC, order.getTotalPrice(), order.getTradePrice());
						break;
						
					case ETH:
						account.addBalance(CoinSymbol.ETH, order.getTotalPrice(), order.getTradePrice());
						break;
						
					default:
						return;
					}
					order.setQuantity_Conclusion(order.getQuantity());
					order.setConclusion(true);
				}
			}
		}
		
		gui.updateBuySell(true);
		//saveManager.manualSave();
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
		
		double balance;
		UUID id = UUID.randomUUID();
		
		if (buy)
		{
			switch (market)
			{
			case KRW:
				balance = account.getKRW() + 1;
				break;

			case BTC:
				balance = account.getBalance(CoinSymbol.BTC);
				break;
				
			case ETH:
				balance = account.getBalance(CoinSymbol.ETH);
				break;
				
			default:
				return;
			}
			
			if (balance < tradePrice * quantity)
				return;
			
			switch (market)
			{
			case KRW:
				account.subtractKRW(Math.round(tradePrice * quantity));
				break;

			case BTC:
				account.subtractBalance(CoinSymbol.BTC, tradePrice * quantity);
				break;
				
			case ETH:
				account.subtractBalance(CoinSymbol.BTC, tradePrice * quantity);
				break;
				
			default:
				return;
			}
		}
		else
		{
			balance = account.getBalance(coinSymbol);
			
			if (balance < quantity)
				return;
			
			account.subtractBalance(coinSymbol, quantity);
		}

		order = new Order(market, coinSymbol, id, date, tradePrice, quantity, buy);
		
		account.getOrderList().add(order);
		
		confirmOrder();
		//saveManager.manualSave();
	}
	
	public void cancelOrder(UUID OrderID)
	{
		for (Order order : account.getOrderList())
		{
			if (order.getId() == OrderID)
			{
				if (order.isBuy())
				{
					switch (order.getMarket())
					{
					case KRW:
						account.addKRW(Math.round(order.getTotalPrice()));
						break;
						
					case BTC:
						account.addBalance(CoinSymbol.BTC, order.getQuantity(), account.searchWallet(CoinSymbol.BTC).getAveragePrice());
						break;
						
					case ETH:
						account.addBalance(CoinSymbol.ETH, order.getQuantity(), account.searchWallet(CoinSymbol.ETH).getAveragePrice());
						break;

					default:
						return;
					}
					
					account.getOrderList().remove(order);
					return;
				}
				else
				{
					double tradePrice = 0;
					
					try
					{
						CryptoCurrency cryptoCurrency = getCryptoCurrency(createName(order.getMarket(), order.getCoinSymbol(), TermType.minutes, 1));
						tradePrice = Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice));
					}
					catch (Exception e)
					{	
					}
					
					account.addBalance(order.getCoinSymbol(), order.getQuantity(), tradePrice);
					account.getOrderList().remove(order);
					return;
				}
			}
		}
		
		//saveManager.manualSave();
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
	
	public double getVolumePrice(Market market, CoinSymbol coinSymbol, int hours)
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

	public void updateStat()
	{
		CryptoCurrency cryptoCurrency;
		Stat stat = account.getStat();
		
		double assetValue, earnings, earningsRate, seed;
		
		seed = account.getStat().getSeed();
		assetValue = 0;
		
		assetValue += account.getKRW();
		
		for (Wallet wallet : account.getWalletList())
		{
			try
			{
				cryptoCurrency = getCryptoCurrency(createName(Market.KRW, wallet.getCoinSymbol(), TermType.minutes, 1));
			}
			catch (Exception e)
			{
				try
				{
					cryptoCurrency = requestData(Market.KRW, wallet.getCoinSymbol(), TermType.minutes, 1, 1);
				}
				catch (Exception e1)
				{
					continue;
				}
			}
			
			assetValue += wallet.getBalance() * Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice));
		}

		for (Order order : account.getOrderList())
		{
			if (order.isConclusion() == false)
				assetValue += order.getTotalPrice();
		}
		
		earnings = assetValue - seed;
		earningsRate = (assetValue - seed) / seed * 100;
		
		stat.setAssetValue(assetValue);
		stat.setEarningsRate(earningsRate);
		stat.setEarnings(earnings);
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
	
	public void setCryptoList(ArrayList<CryptoCurrency> cryptoList)
	{
//		this.cryptoList = cryptoList;
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

	public SaveManager getSaveManager()
	{
		return saveManager;
	}

	public void setSaveManager(SaveManager saveManager)
	{
		this.saveManager = saveManager;
	}
}
