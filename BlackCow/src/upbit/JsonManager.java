package upbit;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonManager
{
	public enum JsonKey
	{
		code, candleDateTime, candleDateTimeKst, openingPrice, highPrice, lowPrice, 
		tradePrice, candleAccTradeVolume, candleAccTradePrice, timestamp, 
		
		prevClosingPrice, change, changePrice, changeRate, signedChangePrice, signedChangeRate
	}
	
	public static LinkedList<JSONObject> parse(String data)
	{
		LinkedList<JSONObject> objectList = new LinkedList<>();
		JSONArray jsonArray = new JSONArray();
		JSONParser jsonParser = new JSONParser();
		
		try
		{
			jsonArray = (JSONArray) jsonParser.parse(data);

			for (int index = 0; index < jsonArray.size(); index++)
			{
				objectList.addLast(getObject(jsonArray, index));
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return objectList;
	}
	
	public static void save(JSONArray array, String name)
	{
		try
		{
			FileWriter file = new FileWriter("./json/" + name + ".json");

			file.write(array.toJSONString());
			file.flush();
			file.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static JSONArray load(String name)
	{
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = new JSONArray();
		
		try
		{
			FileReader file = new FileReader("./json/" + name + ".json");
			
			jsonArray = (JSONArray) jsonParser.parse(file);
		}
		catch (IOException | ParseException e)
		{
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public static String getData(JSONObject jsonObject, JsonKey jsonKey)
	{
		try
		{
			return jsonObject.get(jsonKey.toString()).toString();
		}
		catch (NullPointerException e)
		{
			System.out.println("Failed to getData, jsonKey: " + jsonKey);
			
			return "0";
		}
	}
	
	public static String getData(CryptoCurrency cryptoCurrency, JsonKey jsonKey, int index)
	{
		return getData(cryptoCurrency.getJsonObject(index), jsonKey);
	}
	
	public static JSONObject getObject(JSONArray jsonArray, int index)
	{
		return (JSONObject) jsonArray.get(index);
	}
}
