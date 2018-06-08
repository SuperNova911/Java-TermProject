package upbit;

public class TestManager
{
	public static void main(String[] args)
	{
//		String result, url;
//		
//		//result = upbit.request("minutes", 5, "KRW", "BTC", 1, "2017-12-27%2005:10:00");
//		url = Request.createUpbitURL(Market.KRW, CoinSymbol.BTC, TermType.days, 1);
//		result = Request.request(url);
//		
//		
//		System.out.println(result);
//
//		JsonManager.save(JsonManager.parse(result));
//		JsonManager.save(JsonManager.load());
//
//		CryptoCurrency coin = JsonManager.assign(JsonManager.load());
//		
//		String kappa = coin.getData(JsonKey.signedChangePrice);
//
//		System.out.println(kappa);
//		
//		
//		Account account = new Account("suwhan", "tnghks");
//
//		account.addBalance(CoinSymbol.BTC, 1.5);
//		account.addBalance(CoinSymbol.BCC, 10);
//		account.addBalance(CoinSymbol.EMC2, 352);
//		
//		for (Wallet w : account.getWalletList())
//		{
//			System.out.println(w.getCoinSymbol().toString() + "\t" + w.getBalance());
//		}
//
//		account.subtractBalance(CoinSymbol.DASH, 5);
////		account.subtractBalance(CoinSymbol.BCC, 11);
//		
//		for (Wallet w : account.getWalletList())
//		{
//			System.out.println(w.getCoinSymbol().toString() + "\t" + w.getBalance());
//		}
//
//		account.subtractBalance(CoinSymbol.BCC, 9);
//		
//		System.out.println(account.searchWallet(CoinSymbol.BCC).getBalance());
//
//		account.subtractBalance(CoinSymbol.BCC, 2);
////		System.out.println(account.searchWallet(CoinSymbol.BCC).getBalance());
//		
////		Wallet w = account.searchWallet(CoinSymbol.BCC);
////		System.out.println(w.getCoinSymbol() + " " + w.getBalance());
//		
//		account.addBalance(CoinSymbol.BTC, 1.123123);
//		System.out.println(account.getBalance(CoinSymbol.BTC));
//		System.out.println(account.getBalance(CoinSymbol.ETH));
//		
//		Upbit upbit = new Upbit();
//		upbit.updateData(TermType.days);
//		upbit.test();
		

//		Upbit upbit = new Upbit();
//		
//		upbit.requestData(TermType.minutes, 1, 10);
//		String kappa = JsonManager.getData(JsonManager.getObject(JsonManager.load("KRW-BTC-minutes")), JsonKey.code);
//		System.out.println(kappa);
		
//		String url = "https://www.binance.com/trade.html?symbol=ETH_BTC"; //Request.KappaURL(Market.KRW, CoinSymbol.LTC);
//		String data = Request.Kappa(url);
//		
//		System.out.println(data);
//		

//		DynamicCrawler crawler = new DynamicCrawler();
//		crawler.getOrderBook(Market.KRW, CoinSymbol.BTC);
//		kappa();
		
		
	}
	
	public static void kappa()
	{
		while (true)
		{
			System.out.println("a");
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
			
			System.out.println("b");
			try
			{
				Thread.sleep(500);
			}
			catch (InterruptedException e)
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}
	}
}
