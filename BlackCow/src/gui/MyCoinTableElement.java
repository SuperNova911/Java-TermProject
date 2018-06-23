package gui;

import java.text.DecimalFormat;

import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;

public class MyCoinTableElement
{
	private CoinSymbol coinSymbol;
	private String name;
	private double balance;
	private double avgPrice;
	private double changeRate;
	private double changePrice;
	
	public MyCoinTableElement(CoinSymbol coinSymbol, String name, double balance, double avgPrice, double changeRate, double changePrice)
	{
		this.setCoinSymbol(coinSymbol);
		this.name = name;
		this.balance = balance;
		this.avgPrice = avgPrice;
		this.changeRate = changeRate;
		this.changePrice = changePrice;
	}
	
	public Object[] getData()
	{
		Object[] data = new Object[4];
		DecimalFormat decimalFormat;
		
		data[0] = getCoinSymbol().toString();
		
		decimalFormat = new DecimalFormat("#,##0.#");
		data[1] = decimalFormat.format(balance);

		data[2] = decimalFormat.format(avgPrice);

		decimalFormat = new DecimalFormat("#,##0.00");
		data[3] = "<html>";
		data[3] += (changeRate > 0 ? "+" : "") + decimalFormat.format(changeRate) + "%";
		data[3] += "<br/>";

		decimalFormat = new DecimalFormat("#,##0");
		data[3] += (changePrice > 0 ? "+" : "") + decimalFormat.format(changePrice);
		data[3] += "</html>";
		
		return data;
	}

	public CoinSymbol getCoinSymbol()
	{
		return coinSymbol;
	}

	public void setCoinSymbol(CoinSymbol coinSymbol)
	{
		this.coinSymbol = coinSymbol;
	}
}
