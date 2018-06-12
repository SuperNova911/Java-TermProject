package upbit;

import java.util.ArrayList;
import java.util.Arrays;

import upbit.CoinList.CoinNameKR;

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
		DASH, KMD, VTC, MTL, REP, ARK, ZEC,XVG,PART,SRN,DGB,
		NXT,POLY,RDD,DCR,TUSD,ADDR,ZRX,ZEN,DOGE,OCN,PAY,GAME,
		NXS,WAX,VEE,EDG,XZC,RLC,SYS,TUBE,BAT,BLOCK,FCT,MONA,
		VIB,SALT,MANA,ADX,DCT,BURST,GUP,RVR,LBC,CLOAK,GBYTE,MUE,
		VIA,CVC,SLS,ENG,DMT,LRC,DNT,UKG,IOP,BAY,ADT,LUN,OK,HMQ,
		XEL,XDN,UBQ,NGC,SHIFT,ANT,BITB,UP,NAV,CFI,RCN,GNO,FTC,BLK,
		DYN,BCPT,ION,RADS,VRC,NBT,AMP,NMR,WINGS,PTOY,BNT,SYNX,KORE,
		EXP,AID,BLT,EXCL,BSD,SWT,TX,QRL,SPHR,PRO,MEME,SIB,UNB
	
	}
	
	public enum CoinNameKR
	{
		비트코인, 에이다, 이오스, 트론, 스톰, 이그니스, 리플, 기프토, 스텔라루멘,
		시아코인, 이더리움, 스테이터스네트워크토큰, 퀀텀, 비트코인캐시, 네오, 스팀달러, 이더리움클래식, 아더, 그로스톨코인,
		모네로, 스팀, 골렘, 아이콘, 리스크, 뉴이코노미무브먼트, 파워렛저, 머큐리, 아인스타이늄,
		스트라티스, 오미세고, 라이트코인, 블록틱스, 비트코인골드, 피벡스, 모나코, 웨이브, 스토리지,
		대시, 코모도, 버트코인, 메탈, 어거, 아크, 지캐시 ,버지,파티클,시린토큰,디지바이트,엔엑스티,폴리매쓰,레드코인,디크레드,
		트루USD, 제로엑스,젠캐시,도지코인,오디세이,텐엑스페이토큰,게임크레딧,넥서스,왁스,블록브이,엣지리스,지코인,아이젝,
		시스코인,비트튜브,베이직어텐션토큰,블록넷,팩텀,모나코인,바이버레이트,솔트,디센트럴랜드,애드엑스,
		디센트,버스트코인,구피,레볼루션VR,엘비알와이크레딧,클록코인,바이트볼,모네터리유닛,비아코인,
		시빅,살루스,이니그마,디마켓,루프링,디스트릭0x,유니코인골드,인터넷오브피플,비트베어,애드코인,
		루너,오케이캐시,엘라스틱,디지털노트,유빜,나가코인,쉬프트,아라곤,비트빈,업토큰,나브코인,
		코파운드잇,리피오크레딧네트워크,노시스,페더코인,블랙코인,다이내믹,블록메이슨,아이온,라디움,베리코인,누비츠,에이엠피,
		뉴메레르,윙스다오,페이션토리,뱅코르,신디케이트,코어코인,익스펜스,에이드코인,블룸,익스클루시브코인,비트센드,스웜시티토큰,
		트랜스퍼코인,퀀텀리지스턴트렛저,스피어,프로피,메메틱,시베리안체르보츠네츠,언브레이커블코인
		
	}
	
	public enum CoinNameUS
	{
		
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
	
		setListBTC(new ArrayList<CoinSymbol>(Arrays.asList(
				CoinSymbol.DGB, CoinSymbol.POLY, CoinSymbol.NXT, CoinSymbol.RDD, CoinSymbol.DCR,CoinSymbol.TUSD, 
	            CoinSymbol.ZRX, CoinSymbol.ZEN, CoinSymbol.DOGE, CoinSymbol.OCN, CoinSymbol.PAY,
	            CoinSymbol.GAME, CoinSymbol.NXS, CoinSymbol.WAX, CoinSymbol.EDG, CoinSymbol.VEE, CoinSymbol.XZC,
	            CoinSymbol.RLC, CoinSymbol.TUBE, CoinSymbol.SYS, CoinSymbol.BAT, CoinSymbol.BLOCK, CoinSymbol.FCT,
	            CoinSymbol.MONA, CoinSymbol.VIB, CoinSymbol.SALT, CoinSymbol.ADX, CoinSymbol.MANA, CoinSymbol.DCT,
	            CoinSymbol.GUP, CoinSymbol.BURST, CoinSymbol.RVR, CoinSymbol.LBC, CoinSymbol.GBYTE, CoinSymbol.CLOAK,
	            CoinSymbol.MUE, CoinSymbol.CVC, CoinSymbol.VIA, CoinSymbol.ANT, CoinSymbol.ENG, CoinSymbol.SLS, CoinSymbol.DMT,
	            CoinSymbol.LRC, CoinSymbol.DNT, CoinSymbol.UKG, CoinSymbol.ADT, CoinSymbol.IOP, CoinSymbol.BAY, CoinSymbol.LUN,
	            CoinSymbol.XEL, CoinSymbol.UBQ, CoinSymbol.HMQ, CoinSymbol.XDN, CoinSymbol.SHIFT, CoinSymbol.NGC, CoinSymbol.OK,
	            CoinSymbol.BITB, CoinSymbol.UP, CoinSymbol.NAV, CoinSymbol.GNO, CoinSymbol.CFI, CoinSymbol.RCN,CoinSymbol.FTC, 
	            CoinSymbol.BLK, CoinSymbol.BCPT, CoinSymbol.DYN, CoinSymbol.ION, CoinSymbol.RADS, CoinSymbol.VRC, CoinSymbol.PTOY,
	            CoinSymbol.AMP, CoinSymbol.NMR, CoinSymbol.WINGS, CoinSymbol.BNT, CoinSymbol.NBT, CoinSymbol.SYNX, CoinSymbol.EXP,
	            CoinSymbol.KORE, CoinSymbol.AID, CoinSymbol.BSD, CoinSymbol.BLT, CoinSymbol.EXCL, CoinSymbol.SWT, CoinSymbol.TX,
	            CoinSymbol.PRO, CoinSymbol.SIB, CoinSymbol.QRL, CoinSymbol.SPHR, CoinSymbol.UNB, CoinSymbol.MEME
				)));
		
		setListETH(new ArrayList<CoinSymbol>(Arrays.asList(	
				)));

	}
	
	public static CoinNameKR getCoinNameKR(CoinSymbol coinSymbol)
	{
		int index = 0;
		int target = coinSymbol.ordinal();
		
		for (CoinNameKR coinNameKR : CoinNameKR.values())
		{
			if (index == target)
				return coinNameKR;
			
			index++;
		}
		
		return null;
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

	public ArrayList<CoinSymbol> getListBTC()
	{
		return listBTC;
	}

	public void setListBTC(ArrayList<CoinSymbol> listBTC)
	{
		this.listBTC = listBTC;
	}

	public ArrayList<CoinSymbol> getListETH()
	{
		return listETH;
	}

	public void setListETH(ArrayList<CoinSymbol> listETH)
	{
		this.listETH = listETH;
	}
}
