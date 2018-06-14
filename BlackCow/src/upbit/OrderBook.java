package upbit;

import java.util.ArrayList;

import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;

public class OrderBook
{
	private ArrayList<Order> orderList;
	
	private Market market;
	private CoinSymbol coinSymbol;
	
	public enum OrderData
	{
		price, quantity, percentage
	}
	
	public OrderBook(Market market, CoinSymbol coinSymbol)
	{
		setMarket(market);
		setCoinSymbol(coinSymbol);
		
		orderList = new ArrayList<Order>();
	}
	
	public void addOrder(String price, String quantity, String percentage)
	{
		orderList.add(new Order(price, quantity, percentage));
		System.out.println("Add order: " + orderList.size() + ". " + price + "\t" + quantity + "\t" + percentage);
	}
	
	public Order getOrder(int index)
	{
		try
		{
			return orderList.get(index);
		}
		catch (Exception e)
		{
			System.out.println("Failed to getOrder: " + index);
			return new Order("0", "0", "0");
		}
	}
	
	public ArrayList<Order> getOrderList()
	{
		return orderList;
	}

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
}