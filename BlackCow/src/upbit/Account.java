package upbit;

import java.util.ArrayList;

import upbit.CoinList.CoinSymbol;

public class Account
{
	private String id;
	private String password;
	private long KRW;
	
	private ArrayList<Wallet> walletList;
	
	
	// 로그인 기능 구현
	public Account(String id, String password, long KRW)
	{
		this.id = id;
		this.password = password;
		this.walletList = new ArrayList<Wallet>();
		this.KRW = KRW;
	}
	
	public boolean addWallet(Wallet wallet)
	{
		if (checkWallet(wallet.getCoinSymbol()) == false)
		{
			walletList.add(wallet);
			return true;
		}
		
		System.out.println("Failed to addWallet, already exist: " + wallet.getCoinSymbol());
		return false;
	}
	
	public boolean removeWallet(CoinSymbol coinSymbol)
	{
		Wallet wallet = searchWallet(coinSymbol);
		
		if (wallet != null)
		{
			walletList.remove(wallet);
			return true;
		}
		else
			System.out.println("Failed to remove wallet: " + coinSymbol);
		
		return false;
	}
	
	public Wallet searchWallet(CoinSymbol coinSymbol)
	{
		for (Wallet wallet : walletList)
		{
			if (wallet.getCoinSymbol() == coinSymbol)
				return wallet;
		}
		
		System.out.println("Failed to searchWallet: " + coinSymbol);
		
		return null;
	}
	
	public boolean checkWallet(CoinSymbol coinSymbol)
	{
		for (Wallet wallet : walletList)
		{
			if (wallet.getCoinSymbol() == coinSymbol)
				return true;
		}
		
		return false;
	}

	public void addBalance(CoinSymbol coinSymbol, double balance, double buyPrice)
	{
		Wallet wallet = searchWallet(coinSymbol);
		
		if (wallet != null)
		{
			wallet.addBalance(balance, buyPrice);
		}
		else
		{
			System.out.println("Create new wallet :" + coinSymbol);
			addWallet(new Wallet(coinSymbol, balance, buyPrice));
		}
	}
	
	public boolean subtractBalance(CoinSymbol coinSymbol, double balance)
	{
		Wallet wallet = searchWallet(coinSymbol);
		
		if (wallet != null)
			wallet.subtractBalance(balance);
		else
		{
			System.out.println("Failed to subtractBalance, not exist: " + coinSymbol);
			return false;
		}
		
		if (wallet.getBalance() == 0)
			walletList.remove(wallet);
		
		return true;
	}
	
	public void addKRW(long KRW)
	{
		this.KRW += KRW;
	}
	
	public void subtractKRW(long KRW)
	{
		this.KRW -= KRW;
	}
	
	
	//Getter, Setter
	public double getBalance(CoinSymbol coinSymbol)
	{
		Wallet wallet = searchWallet(coinSymbol);
		
		if (wallet != null)
			return wallet.getBalance();
		else
			return 0;
	}
	
	public ArrayList<Wallet> getWalletList()
	{
		return walletList;
	}
	
	public long getKRW()
	{
		return KRW;
	}
	
	public void setKRW(long KRW)
	{
		if (KRW < 0)
			KRW = 0;
		
		this.KRW = KRW;
	}
}
