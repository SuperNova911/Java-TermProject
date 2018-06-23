package upbit;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Stat implements Serializable
{
	private double seed;
	private double assetValue;
	private double earnings;
	private double earningsRate;

	
	@Override
	public String toString()
	{
		return "Stat [seed=" + seed + ", assetValue=" + assetValue + ", earnings=" + earnings + ", earningsRate="
				+ earningsRate + "]";
	}

	public Stat(long seed)
	{
		setSeed(seed);
	}

	public double getSeed()
	{
		return seed;
	}

	public void setSeed(double seed)
	{
		this.seed = seed;
	}

	public double getAssetValue()
	{
		return assetValue;
	}

	public void setAssetValue(double assetValue)
	{
		this.assetValue = assetValue;
	}

	public double getEarnings()
	{
		return earnings;
	}

	public void setEarnings(double earnings)
	{
		this.earnings = earnings;
	}

	public double getEarningsRate()
	{
		return earningsRate;
	}

	public void setEarningsRate(double earningsRate)
	{
		this.earningsRate = earningsRate;
	}
}
