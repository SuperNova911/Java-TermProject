package upbit;

import java.io.File;
import java.util.ArrayList;

import org.json.simple.JSONArray;

import upbit.JsonManager.JsonKey;
import upbit.Request.TermType;

public class Upbit
{
	private ArrayList<JSONArray> jsonArrayList;

	public Upbit()
	{
		jsonArrayList = new ArrayList<JSONArray>();
		createJsonDirectory();
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
	
	// ´Ùµë±â
	public boolean createJsonDirectory()
	{
		File file = new File("json");
		file.mkdirs();
		return true;
	}
	
	public boolean buy(Account account, Market market, CoinSymbol coinSymbol, double balance, double buyPrice)
	{
		double totalPrice = balance * buyPrice;
		
		switch (market)
		{
		case KRW:
			int KRW = account.getKRW();
			
			if (KRW > totalPrice)
				account.setKRW(KRW - (int) Math.ceil(totalPrice));
			else
			{
				System.out.println("Failed to buy, not enough KRW: " + coinSymbol);
				return false;
			}
			
			break;
			
		case BTC:
			double BTC = account.getBalance(coinSymbol.BTC);
			
			if (BTC > totalPrice)
				account.subtractBalance(coinSymbol.BTC, totalPrice);
			else
			{
				System.out.println("Failed to buy, not enough BTC: " + coinSymbol);
				return false;
			}
			break;
			
		case ETH:
			double ETH = account.getBalance(coinSymbol.ETH);
			
			if (ETH > totalPrice)
				account.subtractBalance(coinSymbol.ETH, totalPrice);
			else
			{
				System.out.println("Failed to buy, not enough ETH: " + coinSymbol);
				return false;
			}
			break;
		}
		
		account.addBalance(coinSymbol, balance, buyPrice);
		
		return true;
	}
	
//	public boolean sell(Account account, Market market, CoinSymbol coinSymbol, double balance, double sellPrice)
//	{
//		double totalPrice = balance * sellPrice;
//		
//		switch (market)
//		{
//		case KRW:
//			
//		}
//		
//	}
}
