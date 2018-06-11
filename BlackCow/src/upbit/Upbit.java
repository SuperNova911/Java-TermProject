package upbit;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonObject;

import gui.GUI;
import javafx.scene.shape.Cylinder;
import upbit.CoinList.CoinSymbol;
import upbit.CoinList.CoinSymbolKRW;
import upbit.CoinList.Market;
import upbit.JsonManager.JsonKey;
import upbit.Request.TermType;

public class Upbit
{
	private ArrayList<CryptoCurrency> cryptoList;
	private Account account;
	private CoinList coinList;
	private GUI gui;
	
	private int refreshTime;

	public Upbit(Account account)
	{
		setAccount(account);
		
		cryptoList = new ArrayList<CryptoCurrency>();
		loadCryptoCurrency();
		
		coinList = new CoinList();
		
		updateData(Market.KRW);
	}
	
	public boolean loadCryptoCurrency()
	{
		return false;
	}
	
	public void updateData(Market market)
	{
		int updateFrequency = 5;
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(3);
		
		executor.scheduleAtFixedRate(
				new Runnable()
				{
					@Override
					public void run()
					{
						String url;
						LinkedList<JSONObject> list;
						CryptoCurrency cryptoCurrency;
						
						switch (market)
						{
						case KRW:
							for (CoinSymbol coinSymbol : coinList.getListKRW())
							{
								System.out.println(coinSymbol.toString());
								url = Request.Upbit.createUrlByMin(market, coinSymbol, 1, 2);
								list = JsonManager.parse(Request.request(url));
								
								try
								{
									cryptoCurrency = getCryptoCurrency(createName(market, coinSymbol, TermType.minutes));
									cryptoCurrency.addData(list);
								}
								catch (Exception e)
								{
									System.out.println("Failed to getCryptoCurrency, create new CryptoCurrency");
									addCryptoCurrency(new CryptoCurrency(list, market, coinSymbol, TermType.minutes));
								}
							}
						
							try
							{
								gui.updateCoinTable(market);
							}
							catch (Exception e)
							{
								// TODO 자동 생성된 catch 블록
								e.printStackTrace();
							}
							
							System.out.println("Update");
							
							break;
							
						case BTC:
							break;
							
						case ETH:
							break;
						}
					}
				}, 1, updateFrequency, TimeUnit.SECONDS);
	}
	
	public boolean requestData(Market market, CoinSymbol coinSymbol, TermType termType)
	{
		String url = Request.Upbit.createUrl(market, coinSymbol, termType, term, dataAmount)
	}
	
	
	public boolean buy(Market market, CoinSymbol coinSymbol, double quantity)
	{
		double price = getTradePrice(market, coinSymbol);
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
	
	public boolean sell(Market market, CoinSymbol coinSymbol, double quantity)
	{
		double price = getTradePrice(market, coinSymbol);
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
			cryptoCurrency = getCryptoCurrency(createName(market, coinSymbol, TermType.minutes));
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
	
	public String createName(Market market, CoinSymbol coinSymbol, TermType termType)
	{
		return market + "-" + coinSymbol + "-" + TermType.minutes;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void updateData(TermType termType)
	{
		if (termType == TermType.days)
		{
			for (CoinSymbol coinSymbol : CoinSymbol.values())
			{
				String url = Request.Upbit.createUrl(Market.KRW, coinSymbol, termType, 1, 1);
				String data = Request.request(url);
				
				if (data != "RQ_FAIL")
				{
					addJsonArray(JsonManager.parse(data));
					System.out.println("Add " + coinSymbol);
				}
			}
		}
		
	}
	
	public void addJsonArray(JSONArray jsonArray)
	{
		jsonArrayList.add(jsonArray);
	}
	
	public void requestData(TermType termType, int dataAmount)
	{
		requestData(termType, 1, dataAmount);
	}
	
	public void requestData(TermType termType, int term, int dataAmount)
	{
		String url, data;
		
		for (Market market : Market.values())
		{
			if (market != market.KRW)
				continue;
			
			for (CoinSymbol coinSymbol : CoinSymbol.values())
			{
				if (termType == TermType.minutes)
					url = Request.Upbit.createUrlByMin(market, coinSymbol, term, dataAmount);
				else
					url = Request.Upbit.createUrl(market, coinSymbol, termType, term, dataAmount);
				
				data = Request.request(url);
				
				if (data != "RQ_FAIL")
					JsonManager.save(JsonManager.parse(data), market + "-" + coinSymbol + "-" + termType);
				
				
			}
		}
	}
	
	// 다듬기
	public boolean createJsonDirectory()
	{
		File file = new File("json");
		file.mkdirs();
		return true;
	}

	
	
	
	
}
