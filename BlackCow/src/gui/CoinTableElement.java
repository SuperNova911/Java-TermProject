package gui;

import java.text.DecimalFormat;

import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;

public class CoinTableElement
{
	private Market market;
	private CoinSymbol coinSymbol;
	private String name;
	private double tradePrice;
	private double changeRate;
	private double volume;
	
	
	public CoinTableElement(Market market, CoinSymbol coinSymbol, String name, double tradePrice, double signedChangeRate, double volume)
	{
		this.setMarket(market);
		this.setCoinSymbol(coinSymbol);
		this.setName(name);
		this.setTradePrice(tradePrice);
		this.setChangeRate(changeRate);
		this.setVolume(volume);
	}
	
	public Object[] getData()
	{
		Object[] data = new Object[4];
		DecimalFormat decimalFormat;
		
		data[0] = "<html>";
		data[0] += name;
		data[0] += "<br/>";
		data[0] += coinSymbol.toString();
		data[0] += "</html>";
		
		decimalFormat = new DecimalFormat("#,##0.########");
		data[1] = decimalFormat.format(tradePrice);
		
		decimalFormat = new DecimalFormat("#,##0.00");
		data[2] = (changeRate > 0 ? "+" : "") + decimalFormat.format(changeRate * 100) + "%";
		
		decimalFormat = new DecimalFormat("#,###");
		data[3] = decimalFormat.format(volume / 1000000) + "¹é¸¸";
		
		return data;
	}
	
	
	
	// Getter, Setter
	public Market getMarket()
	{
		return market;
	}

	public void setMarket(Market market)
	{
		this.market = market;
	}	
	
	public CoinSymbol getCoinSymbol()
	{
		return coinSymbol;
	}

	public void setCoinSymbol(CoinSymbol coinSymbol)
	{
		this.coinSymbol = coinSymbol;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getTradePrice()
	{
		return tradePrice;
	}

	public void setTradePrice(double tradePrice)
	{
		this.tradePrice = tradePrice;
	}

	public double getChangeRate()
	{
		return changeRate;
	}

	public void setChangeRate(double changeRate)
	{
		this.changeRate = changeRate;
	}

	public double getVolume()
	{
		return volume;
	}

	public void setVolume(double volume)
	{
		this.volume = volume;
	}
}
