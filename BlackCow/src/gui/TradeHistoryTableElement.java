package gui;

import java.util.Date;

import upbit.Order;

public class TradeHistoryTableElement
{
	private Order order;
	
	public TradeHistoryTableElement(Order order)
	{
		this.order = order;
	}
	
	public Object[] getData()
	{
		Object[] data = new Object[5];
		
		data[0] = (order.getDate().getMonth() + 1) + "/" + order.getDate().getDate() + " " + order.getDate().getHours() + ":" + order.getDate().getMinutes();
		
		data[1] = order.isBuy() ? "매수" : "매도";
		
		data[2] = order.getTradePrice();
		
		data[3] = order.getQuantity_Conclusion();
		
		data[4] = order.getTotalPrice();
		
		return data;
	}
}
