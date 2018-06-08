package upbit;

import java.util.ArrayList;

import upbit.OrderBook.OrderData;

public class OrderBook
{
	private ArrayList<Order> orderList;
	
	public enum OrderData
	{
		price, quantity, percentage
	}
	
	public OrderBook()
	{
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
}

class Order
{
	private double price;
	private double quantity;
	private double percentage;
	
	public Order(String price, String quantity, String percentage)
	{
		this.setPrice(toDouble(price));
		this.setQuantity(toDouble(quantity));
		this.setPercentage(toDouble(percentage));
	}
	
	public double toDouble(String string)
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
		
//		switch (orderData)
//		{
//		case price:
//		case quantity:
//			try
//			{
//				result = Double.parseDouble(string);
//			}
//			catch (NumberFormatException e1)
//			{
//				System.out.println("Failed to toDouble, parseDouble: " + string);
//			}
//			break;
//			
//		case percentage:
//			try
//			{
//				int endIndex = string.indexOf('%');
//				string = string.substring(0, endIndex);
//			}
//			catch (Exception e)
//			{
//				System.out.println("Failed to toDouble, trying alternative method");
//				string = string.substring(0, string.length() - 1);
//			}
//
//			try
//			{
//				result = Double.parseDouble(string);
//			}
//			catch (NumberFormatException e)
//			{
//				System.out.println("Failed to toDouble, parseDouble: " + string);
//			}
//			break;
//		}
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
}