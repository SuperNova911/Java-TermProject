package upbit;

import java.util.ArrayList;

import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;

public class OrderBook
{
	private ArrayList<OrderBookElement> orderBookElementList;
	
	private Market market;
	private CoinSymbol coinSymbol;
	
	private int showIndex;
	private int tradeIndex;
	
	public enum OrderData
	{
		price, quantity, percentage
	}
	
	public OrderBook(Market market, CoinSymbol coinSymbol)
	{
		setMarket(market);
		setCoinSymbol(coinSymbol);
		
		orderBookElementList = new ArrayList<OrderBookElement>();
		showIndex = 0;
	}
	
//	public void addOrderBookElement(String price, String quantity, String percentage, boolean buy)
//	{
//		orderBookElementList.add(new OrderBookElement(price, quantity, percentage, buy));
//	}
	
	public OrderBookElement getOrderBookElement(int index)
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
	
	public void updateOrderBook(OrderBook input)
	{
//		int startIndex;
//		
//		ArrayList<OrderBookElement> inputList = input.getOrderBookElementList();
//
//		int searchIndex;
//		
//		for (searchIndex = 0; searchIndex < 10; searchIndex++)
//		{
//			for (int i = 0; i < showIndex + 10; i ++)
//			{
//				if (getOrderBookElement(i).getPrice() == input.getOrderBookElement(searchIndex).getPrice())
//				{
//					startIndex = i;
//					
//				}
//			}
//		}
//		
//		// 가격 내려감, 위의 리스트는 그냥 둠
//		orderBookElementList.addAll(inputList);
//		startIndex = showIndex + 10;
		
		ArrayList<OrderBookElement> baseList = getOrderBookElementList();
		ArrayList<OrderBookElement> inputList = input.getOrderBookElementList();
		ArrayList<OrderBookElement> newList = new ArrayList<OrderBookElement>();
		
		int baseIndex, inputIndex, newIndex;
		baseIndex = inputIndex = newIndex = 0;
		
		double basePrice, inputPrice;
		
		while (baseIndex < baseList.size() && inputIndex < inputList.size())
		{
			if (!(baseIndex < baseList.size()))
			{
				for (; inputIndex < inputList.size(); inputIndex++, newIndex++)
				{
					if (inputList.get(inputIndex).isNowTrading())
						tradeIndex = newIndex;
					
					newList.add(inputList.get(inputIndex));
				}
				
				break;
			}
			else if (!(inputIndex < inputList.size()))
			{
				for (; baseIndex < baseList.size(); baseIndex++)
				{
					baseList.get(baseIndex).setNowTrading(false);
					newList.add(baseList.get(baseIndex));
				}
				
				break;
			}
			
			basePrice = baseList.get(baseIndex).getPrice();
			inputPrice = inputList.get(inputIndex).getPrice();
						
			if (basePrice > inputPrice)
			{
				baseList.get(baseIndex).setNowTrading(false);
				newList.add(baseList.get(baseIndex));
				
				baseIndex++;
			}
			else if (basePrice < inputPrice)
			{
				newList.add(inputList.get(inputIndex));
				
				if (inputList.get(inputIndex).isNowTrading())
					tradeIndex = newIndex;
				
				inputIndex++;
			}
			else
			{
				baseList.get(baseIndex).setNowTrading(false);
				newList.add(baseList.get(baseIndex));
				newList.get(newIndex).updateElement(inputList.get(inputIndex));
				
				if (inputList.get(inputIndex).isNowTrading())
					tradeIndex = newIndex;

				baseIndex++;
				inputIndex++;
			}
			
			newIndex++;
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

	public int getShowIndex()
	{
		return showIndex;
	}

	public void setShowIndex(int showIndex)
	{
		this.showIndex = showIndex;
	}

	public int getTradeIndex()
	{
		return tradeIndex;
	}

	public void setTradeIndex(int tradeIndex)
	{
		this.tradeIndex = tradeIndex;
	}
}