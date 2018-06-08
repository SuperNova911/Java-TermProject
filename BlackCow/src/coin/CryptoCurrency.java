package coin;

import org.json.simple.JSONArray;

public class CryptoCurrency
{
	private JSONArray jsonArray;
	
	private String name;
	
	
	public CryptoCurrency(JSONArray jsonArray, String name)
	{
		setJsonArray(jsonArray);
		setName(name);
	}
	
	public JSONArray getJsonArray()
	{
		return jsonArray;
	}
	public void setJsonArray(JSONArray jsonArray)
	{
		this.jsonArray = jsonArray;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
