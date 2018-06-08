package upbit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Request
{
	public enum TermType
	{
		minutes, days, weeks, months
	}
	
	public static String request(String urlAddress)
	{
		URL url;
		String result;
		
		try
		{
			url = new URL(urlAddress);
			URLConnection urlConn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

			result = br.readLine();
		} 
		catch (IOException e)
		{
			System.out.println("Failed to request: " + urlAddress);
			result = "RQ_FAIL";
		}
		
		return result;
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

//		public static String createUpbitURL(
//				Market market, CoinSymbol coinSymbol, TermType termType, int dataAmount)
//		{
//			return createUpbitURL(market, coinSymbol, termType, 1, dataAmount, "now");
//		}
//
//		public static String createUpbitURL(
//				Market market, CoinSymbol coinSymbol, TermType termType, int dataAmount, String timeStamp)
//		{
//			return createUpbitURL(market, coinSymbol, termType, 1, dataAmount, timeStamp);
//		}
//		
//		public static String createUpbitURL(
//				Market market, CoinSymbol coinSymbol, TermType termType, int term, int dataAmount)
//		{
//			return createUpbitURL(market, coinSymbol, termType, term, dataAmount, "now");
//		}
//		
//		public static String createUpbitURL(
//				Market market, CoinSymbol coinSymbol, TermType termType, int term, int dataAmount, String timeStamp)
//		{
//			String url = "https://crix-api-endpoint.upbit.com/v1/crix/candles/";
//			
//			url += termType;
//			
//			if (termType == TermType.minutes)
//				url += "/" + Integer.toString(term);		
//			
//			url += "?code=CRIX.UPBIT."
//					+ market + "-"
//					+ coinSymbol;
//			
//			if (dataAmount >= 1)
//				url += "&count=" + dataAmount;
//			
//			if (!timeStamp.equals("now"))
//				url += "&to=" + timeStamp;
//			
//			return url;
//		}
	}
}
