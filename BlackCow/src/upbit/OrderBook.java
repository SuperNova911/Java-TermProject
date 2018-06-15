package upbit;

import java.util.ArrayList;

import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;

public class OrderBook
{
	private ArrayList<OrderBookElement> orderBookElementList;
	
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
		
		orderBookElementList = new ArrayList<OrderBookElement>();
	}
	
	public void addOrderBookElement(String price, String quantity, String percentage, boolean buy)
	{
		orderBookElementList.add(new OrderBookElement(price, quantity, percentage, buy));
//		System.out.println("Add order: " + orderList.size() + ". " + price + "\t" + quantity + "\t" + percentage);
	}
	
	public OrderBookElement getOrder(int index)
	{
		try
		{
			return orderBookElementList.get(index);
		}
		catch (Exception e)
		{
			System.out.println("Failed to getOrder: " + index);
			return new OrderBookElement("0", "0", "0", true);
		}
	}
	
	public ArrayList<OrderBookElement> getOrderBookElementList()
	{
		return orderBookElementList;
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