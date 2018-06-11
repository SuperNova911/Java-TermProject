package upbit;

import java.util.ArrayList;

import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;

public class UpbitManager
{
	private Account user;

	private ArrayList<CryptoCurrency> tokenKRW;
	private ArrayList<CryptoCurrency> tokenBTC;
	private ArrayList<CryptoCurrency> tokenETH;
	
	private OrderBook orderBook;
	
	private DynamicCrawler dynamicCrawler;
	
	
	public UpbitManager()
	{
		
	}
	
	public boolean Initiate()
	{
		tokenKRW = new ArrayList<CryptoCurrency>();
		tokenBTC = new ArrayList<CryptoCurrency>();
		tokenETH = new ArrayList<CryptoCurrency>();
		
		updateTokenList(Market.KRW);
		
		orderBook = new OrderBook();
		
		dynamicCrawler = new DynamicCrawler();
		
		return true;
	}
	
	public boolean addToken(ArrayList<CryptoCurrency> list, CryptoCurrency cryptoCurrency)
	{
		for (CryptoCurrency currency : list)
		{
			if (currency.getCoinSymbol() == cryptoCurrency.getCoinSymbol())
			{
				System.out.println("Failed to addToken, already exists: " + cryptoCurrency.getCoinSymbol());
				return false;
			}
		}
		
		list.add(cryptoCurrency);
		return true;
	}
	
	public boolean updateTokenList(Market market)
	{
		try
		{
			switch (market)
			{
			case KRW:
				for (CoinSymbol coinSymbol : CoinSymbol.values())
				{
					String url = Request.Upbit.createUrlByMin(Market.KRW, coinSymbol, 1, 1);
					CryptoCurrency cryptoCurrency = new CryptoCurrency(JsonManager.getObject(JsonManager.parse(Request.request(url))));
					
					addToken(tokenKRW, cryptoCurrency);
				}
				break;
				
			case BTC:
				break;
				
			case ETH:
				break;
			}
			
			return true;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	public void setUser(Account user)
	{
		this.user = user;
	}
	public Account getUser()
	{
		return user;
	}
}
