package upbit;

import java.io.Serializable;
import java.util.ArrayList;

import upbit.CoinList.CoinSymbol;

@SuppressWarnings("serial")
public class Account implements Serializable
{
	private ArrayList<Wallet> walletList;
	private ArrayList<Order> orderList;
	private ArrayList<TradeHistory> tradeHistoryList;
	
	private Stat stat;
	
	private String id;
	private String password;
	private long KRW;
	
	
	// 로그인 기능 구현
	public Account(String id, String password, long KRW)
	{
		this.setId(id);
		this.password = password;
		this.KRW = KRW;

		this.walletList = new ArrayList<Wallet>();
		this.orderList = new ArrayList<Order>();
		this.tradeHistoryList = new ArrayList<TradeHistory>();
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
	
	@Override
	public String toString()
	{
		return "Account [walletList=" + walletList + ", orderList=" + orderList + ", tradeHistoryList="
				+ tradeHistoryList + ", stat=" + stat + ", id=" + getId() + ", password=" + password + ", KRW=" + KRW + "]";
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

	public ArrayList<Order> getOrderList()
	{
		return orderList;
	}

	public void setOrderList(ArrayList<Order> orderList)
	{
		this.orderList = orderList;
	}

	public ArrayList<TradeHistory> getTradeHistoryList()
	{
		return tradeHistoryList;
	}

	public void setTradeHistoryList(ArrayList<TradeHistory> tradeHistoryList)
	{
		this.tradeHistoryList = tradeHistoryList;
	}

	public Stat getStat()
	{
		return stat;
	}

	public void setStat(Stat stat)
	{
		this.stat = stat;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
