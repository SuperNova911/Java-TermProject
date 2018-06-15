package upbit;

public class OrderBookElement
{
	private double price;
	private double quantity;
	private double percentage;
	private boolean buy;
	
	public OrderBookElement(String price, String quantity, String percentage, boolean buy)
	{
		this.setPrice(toDouble(price));
		this.setQuantity(toDouble(quantity));
		this.setPercentage(toDouble(percentage));
		this.setBuy(buy);
	}
	
	private double toDouble(String string)
	{
		double result = 0;

		string = string.replaceAll(",", "");
		string = string.replaceAll("%", "");
		
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
}