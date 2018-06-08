package upbit;

import java.util.ArrayList;
import java.util.LinkedList;

public class Chart
{
	private int width;
	private int height;
	
	private int numOfData;
	private int start;
	private int end;
	
	private double min;
	private double max;
	
	private double candleWidth;
	
	private LinkedList<CandleStick> candleList;
	private LinkedList<CandleStick> drawSection;
	
	ArrayList<Double> candlePos;
	
	
	public void Draw()
	{
		for (int i = 0; i < drawSection.size(); i++)
		{
			drawCandle(candlePos.get(i), drawSection.get(i));
		}
	}
	
	public void drawCandle(double x, CandleStick candleStick)
	{
		// DrawLine (x, candleStick.getHighPrice()) to (x, candleStick.getLowPrice())
		
		if (candleStick.getTradePrice() > candleStick.getOpeningPrice())
		{
			//RED
			// DrawCandle (x, candleStick.getTradePrice()) to (x, candleStick.getOpeningPrice()) width, RED
		}
		else if (candleStick.getTradePrice() < candleStick.getOpeningPrice())
		{
			//BLUE
			// DrawCandle (x, candleStick.getTradePrice()) to (x, candleStick.getOpeningPrice()) width, BLUE
		}
		else
		{
			//BLACK
			// DrawLine (x - (width / 2), candleStick.getOpeningPrice()) to (x + (width / 2), candleStick.getOpeningPrice())
		}
	}
	
	public double getY(double value)
	{
		return height * (value / max);
	}
	
	public void setCandlePos()
	{
		candlePos = new ArrayList<Double>(numOfData);
		
		double distance = ((double) width / numOfData);
		double startPos = distance / 2;
		
		for (int i = 0; i < candlePos.size(); i++)
		{
			candlePos.set(i, distance * i + startPos);
		}
	}
	
	public void setCandleWidth()
	{
		this.candleWidth = ((double) width / numOfData) / 5;
	}
	
	public void setMinMax()
	{
		for (CandleStick candleStick : drawSection)
		{
			if (this.min > candleStick.getLowPrice())
				this.min = candleStick.getLowPrice();
			if (this.max < candleStick.getHighPrice())
				this.max = candleStick.getHighPrice();
		}
	}
	
	public void setSection()
	{
		drawSection.clear();
		
		for (int i = start; i <= end; i++)
		{
			drawSection.add(candleList.get(i));
		}
	}
}

class CandleStick
{
	private double openingPrice;
	private double highPrice;
	private double lowPrice;
	private double tradePrice;
	
	
	public double getOpeningPrice()
	{
		return openingPrice;
	}
	public void setOpeningPrice(double openingPrice)
	{
		this.openingPrice = openingPrice;
	}
	public double getHighPrice()
	{
		return highPrice;
	}
	public void setHighPrice(double highPrice)
	{
		this.highPrice = highPrice;
	}
	public double getLowPrice()
	{
		return lowPrice;
	}
	public void setLowPrice(double lowPrice)
	{
		this.lowPrice = lowPrice;
	}
	public double getTradePrice()
	{
		return tradePrice;
	}
	public void setTradePrice(double tradePrice)
	{
		this.tradePrice = tradePrice;
	}
}