package upbit;

import java.util.LinkedList;

public class OrderBookElement
{
	private LinkedList<Double> quantityList;
	
	private double price;
	private double quantity;
	private double percentage;
	private boolean buy;
	private boolean nowTrading;
	
	public OrderBookElement(String price, String quantity, String percentage, boolean buy)
	{
		this.setPrice(toDouble(price));
		this.setQuantity(toDouble(quantity));
		this.setPercentage(toDouble(percentage));
		this.setBuy(buy);
		this.nowTrading = false;

		quantityList = new LinkedList<Double>();
		quantityList.add(this.quantity);
	}
	
	public void updateElement(OrderBookElement element)
	{
		if (this.quantity > element.getQuantity())
		{
			double sell = this.quantity - element.getQuantity();
			double listData;
			
			for (int index = 0; sell > 0; index++)
			{
				listData = quantityList.get(index);
				
				if (listData >= sell)
				{
					listData -= sell;
					break;
				}
				else
				{
					sell -= quantityList.get(index);
					quantityList.set(index, (double) 0);
				}
			}
			
			for (Double q : quantityList)
			{
				if (q == 0)
					quantityList.remove(q);
			}
		}
		else if (this.quantity < element.getQuantity())
		{
			double buy = this.quantity - element.getQuantity();
			
			quantityList.add(buy);
		}
		
		setNowTrading(element.isNowTrading());
	}
	
	private double toDouble(String string)
	{
		double result = 0;

		string = string.replaceAll(",", "").replaceAll("%", "");
		
		try
		{
			result = Double.parseDouble(string);
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			System.out.println("Failed to toDouble: " + string);
		}
		
		return result;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public double getQuantity()
	{
		return quantity;
	}

	public void setQuantity(double quantity)
	{
		this.quantity = quantity;
	}

	public double getPercentage()
	{
		return percentage;
	}

	public void setPercentage(double percentage)
	{
		this.percentage = percentage;
	}

	public boolean isBuy()
	{
		return buy;
	}

	public void setBuy(boolean buy)
	{
		this.buy = buy;
	}

	public boolean isNowTrading()
	{
		return nowTrading;
	}

	public void setNowTrading(boolean nowTrading)
	{
		this.nowTrading = nowTrading;
	}
}