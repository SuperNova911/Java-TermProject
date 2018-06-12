package upbit;

import java.io.Serializable;
import java.util.LinkedList;

import org.json.simple.JSONObject;

import upbit.CoinList.CoinNameKR;
import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;
import upbit.JsonManager.JsonKey;
import upbit.Request.TermType;

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
