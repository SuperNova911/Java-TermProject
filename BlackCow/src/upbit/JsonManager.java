package upbit;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonManager
{
	public enum JsonKey
	{
		code, candleDateTime, candleDateTimeKst, openingPrice, highPrice, lowPrice, 
		tradePrice, candleAccTradeVolume, candleAccTradePrice, timestamp, prevClosingPrice, 
		change, changePrice, changeRate, signedChangePrice, signedChangeRate
	}
	
	public static JSONArray parse(String data)
	{
		JSONArray jsonArray = new JSONArray();
		JSONParser jsonParser = new JSONParser();
		
		try
		{
			jsonArray = (JSONArray) jsonParser.parse(data);
			
//			for (int i = 0; i < jsonArray.size(); i++)
//			{
//				jsonObj = (JSONObject)jsonArray.get(i);
//				System.out.println(jsonObj.get("candleDateTimeKst"));
//			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return jsonArray;
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
	
	// 임시
	public static CryptoCurrency assign(JSONArray jsonArray)
	{
		JSONObject jsonObject = (JSONObject) jsonArray.get(0);
		
		CryptoCurrency coin = new CryptoCurrency(jsonObject);
		
		return coin;
	}
	
	public static String getData(JSONObject jsonObject, JsonKey jsonKey)
	{
		try
		{
			return jsonObject.get(jsonKey.toString()).toString();
		}
		catch (Exception e)
		{
			System.out.println("ERROR JsonManager.getData(): " + jsonKey.toString());
			return "error";
		}	
	}
	
	// 보완 하기
	public static JSONObject getObject(JSONArray jsonArray)
	{
		return (JSONObject) jsonArray.get(0);
	}
	
	public static JSONArray addJsonObject(JSONArray target, JSONArray input)
	{
		
		
		return target;
	}
}
