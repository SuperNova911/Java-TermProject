package gui;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import upbit.CryptoCurrency;
import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;
import upbit.JsonManager.JsonKey;
import upbit.Request.TermType;

public class OrderTableElement
{
	private double price;
	private double changeRate;
	private double quantity;
	private int myOrder;
	
	
	public OrderTableElement(double tradePrice, double changeRate, double quantity, int myOrder)
	{
		setPrice(tradePrice);
		setChangeRate(changeRate);
		setQuantity(quantity);
		setMyOrder(myOrder);
	}
	
	public Object[] getData()
	{
		Object[] data = new Object[4];
		DecimalFormat decimalFormat;

		decimalFormat = new DecimalFormat("#,##0.########");
		data[0] = decimalFormat.format(price) + " ";

		decimalFormat = new DecimalFormat("#,##0.00");
		data[1] = (changeRate > 0 ? "+" : "") + decimalFormat.format(changeRate) + "% ";
		
		decimalFormat = new DecimalFormat("0.000");
		data[2] = decimalFormat.format(getQuantity());
		
		data[3] = myOrder > 0 ? myOrder : "";
		
		return data;
	}
	
	
	// Getter, Setter
	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public double getChangeRate()
	{
		return changeRate;
	}

	public void setChangeRate(double changeRate)
	{
		this.changeRate = changeRate;
	}

	public double getQuantity()
	{
		return quantity;
	}

	public void setQuantity(double quantity)
	{
		this.quantity = quantity;
	}

	public int getMyOrder()
	{
		return myOrder;
	}

	public void setMyOrder(int myOrder)
	{
		this.myOrder = myOrder;
	}
}











