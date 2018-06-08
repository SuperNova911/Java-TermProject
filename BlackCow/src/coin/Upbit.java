package coin;

import org.json.simple.JSONArray;

public class Upbit extends CryptoCurrency
{
	public enum Market
	{
		KRW, BTC, ETH/*, USDT*/
	}
	public enum CoinSymbol
	{
		BTC, ADA, EOS, TRX, STORM, IGNIS, XRP, GTO, XLM,
		SC, ETH, SNT, QTUM, BCC, NEO, SBD, ETC, ARDR, GRS,
		XMR, STEEM, GNT, ICX, LSK, XEM, POWR, MER, EMC2,
		STRAT, OMG, LTC, TIX, BTG, PIVX, MCO, WAVES, STORJ,
		DASH, KMD, VTC, MTL, REP, ARK, ZEC
	}

	private Market market;
	private CoinSymbol coinSymbol;
	
	
	public Upbit(JSONArray jsonArray, String name, Market market, CoinSymbol coinSymbol)
	{
		super(jsonArray, name);
		setMarket(market);
		setCoinsymbol(coinSymbol);
	}
	
	public Market getMarket()
	{
		return market;
	}
	public void setMarket(Market market)
	{
		this.market = market;
	}
	
	public CoinSymbol getCoinsymbol()
	{
		return coinSymbol;
	}
	public void setCoinsymbol(CoinSymbol coinSymbol)
	{
		this.coinSymbol = coinSymbol;
	}
}
