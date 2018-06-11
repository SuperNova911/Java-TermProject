package upbit;

import java.util.ArrayList;
import java.util.Arrays;

public class CoinList
{
	public enum Market
	{
		KRW, BTC, ETH, /*USDT*/
	}
	
	public enum CoinSymbol
	{
		BTC, ADA, EOS, TRX, STORM, IGNIS, XRP, GTO, XLM,
		SC, ETH, SNT, QTUM, BCC, NEO, SBD, ETC, ARDR, GRS,
		XMR, STEEM, GNT, ICX, LSK, XEM, POWR, MER, EMC2,
		STRAT, OMG, LTC, TIX, BTG, PIVX, MCO, WAVES, STORJ,
		DASH, KMD, VTC, MTL, REP, ARK, ZEC
	}
	
	public enum CoinNameKR
	{
		비트코인, 에이다, 이오스, 트론, 스톰, 이그니스, 리플, 기프토, 스텔라루멘,
		시아코인, 이더리움, 스테이터스네트워크토큰, 퀀텀, 비트코인캐시, 네오, 스팀달러, 이더리움클래식, 아더, 그로스톨코인,
		모네로, 스팀, 골렘, 아이콘, 리스크, 뉴이코노미무브먼트, 파워렛저, 머큐리, 아인스타이늄,
		스트라티스, 오미세고, 라이트코인, 블록틱스, 비트코인골드, 피벡스, 모나코, 웨이브, 스토리지,
		대시, 코모도, 버트코인, 메탈, 어거, 아크, 지캐시
	}
	
	private ArrayList<CoinSymbol> listKRW;
	private ArrayList<CoinSymbol> listBTC;
	private ArrayList<CoinSymbol> listETH;
	
	public CoinList()
	{
		setListKRW(new ArrayList<CoinSymbol>(Arrays.asList(
				CoinSymbol.ADA, CoinSymbol.ARDR, CoinSymbol.ARK, CoinSymbol.BCC, CoinSymbol.BTC, CoinSymbol.BTG,
				CoinSymbol.DASH, CoinSymbol.EMC2, CoinSymbol.EOS, CoinSymbol.ETC, CoinSymbol.ETH, CoinSymbol.GNT, 
				CoinSymbol.GRS, CoinSymbol.GTO, CoinSymbol.ICX, CoinSymbol.IGNIS, CoinSymbol.KMD, CoinSymbol.LSK, 
				CoinSymbol.LTC, CoinSymbol.MCO, CoinSymbol.MER, CoinSymbol.MTL, CoinSymbol.NEO, CoinSymbol.OMG, 
				CoinSymbol.PIVX, CoinSymbol.POWR, CoinSymbol.QTUM, CoinSymbol.REP, CoinSymbol.SBD,CoinSymbol.SC,
				CoinSymbol.SNT, CoinSymbol.STEEM, CoinSymbol.STORJ, CoinSymbol.STORM,CoinSymbol.STRAT, CoinSymbol.TIX,
				CoinSymbol.TRX, CoinSymbol.VTC, CoinSymbol.WAVES, CoinSymbol.XEM,CoinSymbol.XLM, CoinSymbol.XMR, 
				CoinSymbol.XRP, CoinSymbol.ZEC
				)));
		
		
		listBTC = new ArrayList<CoinSymbol>();
		listETH = new ArrayList<CoinSymbol>();

	}

	
	//Getter, Setter
	public ArrayList<CoinSymbol> getListKRW()
	{
		return listKRW;
	}

	public void setListKRW(ArrayList<CoinSymbol> listKRW)
	{
		this.listKRW = listKRW;
	}
}
