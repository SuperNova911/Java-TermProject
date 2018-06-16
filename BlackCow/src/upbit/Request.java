package upbit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;

public class Request
{
	public enum TermType
	{
		minutes, days, weeks, months
	}
	
	public static String request(String urlAddress) throws Exception
	{
		URL url;

		url = new URL(urlAddress);
		
		try
		{
			URLConnection urlConn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

			return br.readLine();
		} 
		catch (IOException e)
		{
//			System.out.println("Failed to request, try again: " + urlAddress);
			
			try
			{
				Thread.sleep(100);
				URLConnection urlConn = url.openConnection();
				BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

				return br.readLine();
			}
			catch (IOException e1)
			{
				System.out.println("Failed to request: " + urlAddress);
			}
		}
		
		throw new Exception();
	}
	
	
	static class Upbit
	{
		public static String createUrlByMin(
				Market market, CoinSymbol coinSymbol, int term, int dataAmount)
		{
			return createUrl(market, coinSymbol, TermType.minutes, term, dataAmount);
		}
		
		public static String createUrl(
				Market market, CoinSymbol coinSymbol, TermType termType, int term, int dataAmount)
		{
			return createUrlWithTimestamp(market, coinSymbol, termType, term, dataAmount, "now");
		}
		
		public static String createUrlWithTimestamp(
				Market market, CoinSymbol coinSymbol, TermType termType, int term, int dataAmount, String timeStamp)
		{
			String url = "https://crix-api-endpoint.upbit.com/v1/crix/candles/";
			
			if (term < 1)
				term = 1;
			
			if (dataAmount < 1)
				dataAmount = 1;
			
			url += termType;
			
			if (termType == TermType.minutes)
				url += "/" + Integer.toString(term);		
			
			url += "?code=CRIX.UPBIT."
					+ market + "-"
					+ coinSymbol;
			
			if (dataAmount >= 1)
				url += "&count=" + dataAmount;
			
			if (!timeStamp.equals("now"))
				url += "&to=" + timeStamp;
			
			return url;
		}
	}
}
