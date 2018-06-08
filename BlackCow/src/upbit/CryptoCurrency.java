package upbit;

import java.io.Serializable;
import java.util.LinkedList;

import org.json.simple.JSONObject;

import upbit.JsonManager.JsonKey;
import upbit.Request.TermType;

public class CryptoCurrency implements Serializable
{	
	private LinkedList<JSONObject> objectList;
	
	private String name;
	private boolean byMinute = false;

	private Market market;
	private CoinSymbol coinSymbol;
	private TermType termType;
	
	public CryptoCurrency(LinkedList<JSONObject> list, String name, Market market, CoinSymbol coinSymbol, TermType termType)
	{
		this.setObjecList(list);
		this.setName(name);
		this.setMarket(market);
		this.setCoinSymbol(coinSymbol);
		this.setTermType(termType);
	}

	public void addData(LinkedList<JSONObject> list)
	{
		int index = 0;
		long base, input;
		boolean success = true;
		
		for (JSONObject inputObject : list)
		{
			input = Long.parseLong(JsonManager.getData(inputObject, JsonKey.timestamp));
			
			if (success == true)
			{
				success = false;

				for (; index < objectList.size(); index++)
				{
					base = Long.parseLong(JsonManager.getData(objectList.get(index), JsonKey.timestamp));
					
					if (input < base)
					{
						objectList.add(index, inputObject);
						success = true;
						break;
					}
					
					if (input == base)
					{
						success = true;
						break;
					}
				}
			}
			
			if (success == false)
				objectList.addLast(inputObject);
		}
	}
	
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
}
