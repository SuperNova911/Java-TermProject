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
	
	public MyCoinTableElement(CoinSymbol coinSymbol, String name, double balance, double avgPrice, double changeRate)
	{
		this.coinSymbol = coinSymbol;
		this.name = name;
		this.balance = balance;
		this.avgPrice = avgPrice;
		this.changeRate = changeRate;
	}
	
	public Object[] getData()
	{
		Object[] data = new Object[4];
		DecimalFormat decimalFormat;
		
		data[0] = name;
		
		decimalFormat = new DecimalFormat("#,##0.#");
		data[1] = decimalFormat.format(balance);

		data[2] = decimalFormat.format(avgPrice);

		decimalFormat = new DecimalFormat("#,##0.00");
		data[3] = (changeRate > 0 ? "+" : "") + decimalFormat.format(changeRate * 100) + "%";
		
		return data;
	}
}
