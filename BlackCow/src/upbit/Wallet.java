package upbit;

import upbit.CoinList.CoinSymbol;

public class Wallet
{
	private CoinSymbol coinSymbol;
	private double balance;
	private double averagePrice;
	
	public Wallet(CoinSymbol coinSymbol)
	{
		this(coinSymbol, 0, 0);
	}
	
	public Wallet(CoinSymbol coinSymbol, double balance, double buyPrice)
	{
		setCoinSymbol(coinSymbol);
		setBalance(balance);
		setAveragePrice(balance, buyPrice);
	}
	
	public boolean addBalance(double balance, double buyPrice)
	{
		if (balance >= 0)
		{
			this.balance += balance;
			return true;
		}
		else
			System.out.println("Failed to addBalance: " + balance);
		
		return false;
	}
	
	public boolean subtractBalance(double balance)
	{
		if (balance >= 0 && this.balance >= balance)
		{
			this.balance -= balance;
			return true;
		}
		else
			System.out.println("Failed to subtractBalance: " + this.coinSymbol + " " + this.balance + " - " + balance);
		
		return false;
	}
	
	public void setAveragePrice(double balance, double buyPrice)
	{
		this.averagePrice = (this.balance * this.averagePrice + balance * buyPrice) / (this.balance + balance);
	}

	
	public CoinSymbol getCoinSymbol()
	{
		return coinSymbol;
	}
	public void setCoinSymbol(CoinSymbol coinSymbol)
	{
		this.coinSymbol = coinSymbol;
	}

	public double getBalance()
	{
		return balance;
	}
	public boolean setBalance(double balance)
	{
		if (balance >= 0)
		{
			this.balance = balance;
			return true;
		}
		else
			System.out.println("Failed to setBalance: " + balance);
		
		return false;
	}
	
	public double getAveragePrice()
	{
		return averagePrice;
	}
}
