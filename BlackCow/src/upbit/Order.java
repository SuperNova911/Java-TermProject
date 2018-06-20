package upbit;

import java.util.Date;

import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;

public class Order
{
	private Market market;
	private CoinSymbol coinSymbol;
	private Date date;
	private int id;
	private double tradePrice;
	private double quantity;
	private double quantity_Conclusion;
	private double totalPrice;
	private boolean buy;
	private boolean conclusion;

	/**
	 * @param market 거래마켓
	 * @param coinSymbol 코인심볼
	 * @param id 거래ID
	 * @param date 주문시간
	 * @param tradePrice 주문가격
	 * @param quantity 주문수량
	 * @param buy 거래종류
	 */
	public Order(Market market, CoinSymbol coinSymbol, int id, Date date, double tradePrice, double quantity, boolean buy)
	{
		this.market = market;
		this.coinSymbol = coinSymbol;
		this.id = id;
		this.date = date;
		this.tradePrice = tradePrice;
		this.quantity = quantity;
		this.buy = buy;
		
		this.quantity_Conclusion = 0;
		this.totalPrice = quantity * tradePrice;
		this.conclusion = false;
	}

	@Override
	public String toString()
	{
		return "Order [market=" + market + ", coinSymbol=" + coinSymbol + ", id=" + id + ", date=" + date
				+ ", tradePrice=" + tradePrice + ", quantity=" + quantity + ", quantity_Conclusion="
				+ quantity_Conclusion + ", totalPrice=" + totalPrice + ", buy=" + buy + ", conclusion=" + conclusion
				+ "]";
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

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public double getTradePrice()
	{
		return tradePrice;
	}

	public void setTradePrice(double tradePrice)
	{
		this.tradePrice = tradePrice;
	}

	public double getQuantity()
	{
		return quantity;
	}

	public void setQuantity(double quantity)
	{
		this.quantity = quantity;
	}

	public double getQuantity_Conclusion()
	{
		return quantity_Conclusion;
	}

	public void setQuantity_Conclusion(double quantity_Conclusion)
	{
		this.quantity_Conclusion = quantity_Conclusion;
	}

	public double getTotalPrice()
	{
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public boolean isBuy()
	{
		return buy;
	}

	public void setBuy(boolean buy)
	{
		this.buy = buy;
	}

	public boolean isConclusion()
	{
		return conclusion;
	}

	public void setConclusion(boolean conclusion)
	{
		this.conclusion = conclusion;
	}

}
