package gui;

import java.text.DecimalFormat;
import java.util.Date;

import upbit.Order;
import upbit.CoinList.Market;

public class TradeHistoryTableElement
{
	private Order order;
	
	public TradeHistoryTableElement(Order order)
	{
		this.setOrder(order);
	}
	
	public Object[] getData()
	{
		Object[] data = new Object[8];
		DecimalFormat decimalFormat;
		
		
		data[0] = getOrder().getMarket() == Market.KRW ? "원화" : getOrder().getMarket().toString();
		
		data[1] = getOrder().getCoinSymbol().toString();
		
		data[2] = (getOrder().getDate().getMonth() + 1) + "/" + getOrder().getDate().getDate() + " " + getOrder().getDate().getHours() + ":" + getOrder().getDate().getMinutes();
		
		data[3] = getOrder().isBuy() ? "매수" : "매도";
		
		decimalFormat = new DecimalFormat("#,###.#");
		data[4] = decimalFormat.format(getOrder().getTradePrice());
		data[6] = decimalFormat.format(getOrder().getTotalPrice());

		decimalFormat = new DecimalFormat("#,###.########");
		data[5] = decimalFormat.format(getOrder().getQuantity_Conclusion());
		
		data[7] = getOrder().isConclusion() ? "체결됨" : "취소";
		
		return data;
	}

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}
}
