package upbit;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

import org.jfree.date.DateUtilities;
import org.json.simple.JSONObject;

import upbit.CoinList.CoinNameKR;
import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;
import upbit.JsonManager.JsonKey;
import upbit.Request.TermType;

/**
 * @author suwha
 *
 */
public class CryptoCurrency implements Serializable
{	
	private LinkedList<JSONObject> objectList;
	
	private String name;
	private String nameKR;
	private String nameUS;
	private boolean byMinute = false;

	private Market market;
	private CoinSymbol coinSymbol;
	private TermType termType;
	private int term;
	
	public CryptoCurrency(LinkedList<JSONObject> list, Market market, CoinSymbol coinSymbol, TermType termType, int term)
	{
		this.setObjecList(list);
		this.setMarket(market);
		this.setCoinSymbol(coinSymbol);
		this.setTermType(termType);
		this.setTerm(term);
		
		this.name = market + "-" + coinSymbol + "-" + termType + "-" + term;
		this.setLocalName();
	}

	public void addData(LinkedList<JSONObject> list)
	{		
		int index = 0;
		long base, input;
		boolean success = true;
		
		for (JSONObject inputObject : list)
		{
			//System.out.println("새로운 값");
			input = Long.parseLong(JsonManager.getData(inputObject, JsonKey.timestamp));
			
			if (success == true)
			{
				success = false;

				for (; index < objectList.size(); index++)
				{
					//System.out.println("새로운 인덱스");
					base = Long.parseLong(getData(JsonKey.timestamp, index));

					//System.out.println(getData(JsonKey.candleDateTime, index) + " " + getData(JsonKey.timestamp, index));
					//System.out.println(JsonManager.getData(inputObject, JsonKey.candleDateTime) + " " + JsonManager.getData(inputObject, JsonKey.timestamp));
					
					if (input == base)
					{
						//System.out.println("same");
						success = true;
						index++;
						break;
					}
					
					if (getData(JsonKey.candleDateTime, index).equals(JsonManager.getData(inputObject, JsonKey.candleDateTime)))
					{
						//System.out.println("시간만 같음, 업데이트!");
						objectList.set(index, inputObject);
						success = true;
						index++;
						break;
					}
					
					if (input > base)
					{
						//System.out.println("add front");
						objectList.add(index, inputObject);
						success = true;
						index++;
						break;
					}
					
					//System.out.println("해당없슴");
				}
			}
			
			if (success == false)
			{

				objectList.addLast(inputObject);
				//System.out.println("add last");
			}
		}
	}
	
	public void addData(CryptoCurrency cryptoCurrency)
	{
		addData(cryptoCurrency.getObjectList());
	}
	
	public void setLocalName()
	{
		setNameKR(CoinList.getCoinNameKR(coinSymbol).toString());
//		int index = 0;
//		int target = this.coinSymbol.ordinal();
//		
//		for (CoinNameKR coinNameKR : CoinNameKR.values())
//		{
//			if (index == target)
//			{
//				setNameKR(coinNameKR.toString());
//				break;
//			}
//			
//			index++;
//		}
	}
	
	public Date getDate(int index)
	{
		String timeKst = getData(JsonKey.candleDateTimeKst, index);
		timeKst = timeKst.replaceAll("-", "").replaceAll("T", "").replaceAll(":", "");
		
		int year = Integer.parseInt(timeKst.substring(0, 4));
		int month = Integer.parseInt(timeKst.substring(4, 6));
		int day = Integer.parseInt(timeKst.substring(6, 8));
		int hour = Integer.parseInt(timeKst.substring(8, 10));
		int min = Integer.parseInt(timeKst.substring(10, 12));
		
		return DateUtilities.createDate(year, month, day, hour, min);
	}
	
	
	/**
	 * @param startIndex
	 * @param endIndex
	 * @return [0] = 최소, [1] = 최대
	 */
	public double[] getMinMaxPrice(int start, int end)
	{
		int startIndex, endIndex;
		int cryptoEndIndex = getSize() - 1;
		int requestSize = end - start + 1;
		
		if (getSize() >= requestSize)
		{
			if (cryptoEndIndex >= end)
			{
				startIndex = start;
				endIndex = end;
			}
			else
			{
				startIndex = getSize() - requestSize;
				endIndex = cryptoEndIndex;
			}
		}
		else
		{
			startIndex = 0;
			endIndex = getSize() - 1;
			
			requestSize = getSize();
		}
		
		double min, max;
		double highPrice, lowPrice;

		min = Double.parseDouble(getData(JsonKey.lowPrice, startIndex));
		max = Double.parseDouble(getData(JsonKey.highPrice, startIndex));
		
		for (; startIndex <= endIndex; startIndex++)
		{
			lowPrice = Double.parseDouble(getData(JsonKey.lowPrice, startIndex));
			highPrice = Double.parseDouble(getData(JsonKey.highPrice, startIndex));
			
			if (min > lowPrice)
				min = lowPrice;
			
			if (max < highPrice)
				max = highPrice;
		}
		double kappa = ((max - min) * 1.25) / 10;
		double[] result = new double[2];
		
		result[0] = min - kappa;
		result[1] = max + kappa;
		
		return result;
	}
	
	
	// Getter, Setter
	public String getData(JsonKey jsonKey)
	{
		return getData(jsonKey, 0);
	}
	
	public String getData(JsonKey jsonKey, int index)
	{
		return JsonManager.getData(this, jsonKey, index);
	}
	
	public JSONObject getJsonObject(int index)
	{
		return objectList.get(index);
	}
	
	public int getSize()
	{
		return objectList.size();
	}
	
	public LinkedList<JSONObject> getObjectList()
	{
		return objectList;
	}
	
	public void setObjecList(LinkedList<JSONObject> list)
	{
		this.objectList = list;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isByMinute()
	{
		return byMinute;
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

	public TermType getTermType()
	{
		return termType;
	}
	
	public void setTermType(TermType termType)
	{
		this.termType = termType;
		
		if (termType == TermType.minutes)
			byMinute = true;
		else
			byMinute = false;
	}

	public int getTerm()
	{
		return term;
	}

	public void setTerm(int term)
	{
		this.term = term;
	}
	
	public String getNameKR()
	{
		return nameKR;
	}

	public void setNameKR(String nameKR)
	{
		this.nameKR = nameKR;
	}

	public String getNameUS()
	{
		return nameUS;
	}

	public void setNameUS(String nameUS)
	{
		this.nameUS = nameUS;
	}
}
