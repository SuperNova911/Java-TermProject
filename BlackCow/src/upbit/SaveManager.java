package upbit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SaveManager implements Serializable
{	
	private static final long serialVersionUID = -1287477845992289823L;
	
	private transient Upbit upbit;
	private transient ScheduledThreadPoolExecutor scheduledExecutor;

	private ArrayList<Account> accountList;
	private ArrayList<CryptoCurrency> cryptoList;
	
	public SaveManager()
	{
		setAccountList(new ArrayList<Account>());
		setCryptoList(new ArrayList<CryptoCurrency>());
	}
	
	public void autoSave()
	{
		scheduledExecutor.scheduleAtFixedRate(() -> manualSave(), 0, 60, TimeUnit.SECONDS);
	}
	
	public void manualSave()
	{
		for (int index = 0; index < accountList.size(); index++)
		{
			if (accountList.get(index).getId() == upbit.getAccount().getId())
				accountList.set(index, upbit.getAccount());
		}
		
		cryptoList = upbit.getCryptoList();

		save();
	}

	public void save()
	{
		ObjectIO.save(getAccountList(), ObjectIO.NAME_ACCOUNT, ObjectIO.PATH_DOCUMENT);
		ObjectIO.save(getCryptoList(), ObjectIO.NAME_CRYPTO, ObjectIO.PATH_DOCUMENT);
	}
	
	@SuppressWarnings("unchecked")
	public boolean load()
	{
		try
		{
			setAccountList((ArrayList<Account>) ObjectIO.load(ObjectIO.NAME_ACCOUNT, ObjectIO.PATH_DOCUMENT));
			setCryptoList((ArrayList<CryptoCurrency>) ObjectIO.load(ObjectIO.NAME_CRYPTO, ObjectIO.PATH_DOCUMENT));
			
			return true;
		}
		catch (Exception e)
		{
			setAccountList(new ArrayList<Account>());
			setCryptoList(new ArrayList<CryptoCurrency>());
			
			return false;
		}
	}
	
	public ArrayList<Account> getAccountList()
	{
		return accountList;
	}

	public void setAccountList(ArrayList<Account> accountList)
	{
		this.accountList = accountList;
	}

	public ArrayList<CryptoCurrency> getCryptoList()
	{
		return cryptoList;
	}

	public void setCryptoList(ArrayList<CryptoCurrency> cryptoList)
	{
		this.cryptoList = cryptoList;
	}

	public Upbit getUpbit()
	{
		return upbit;
	}

	public void setUpbit(Upbit upbit)
	{
		this.upbit = upbit;
	}

	public ScheduledThreadPoolExecutor getScheduledExecutor()
	{
		return scheduledExecutor;
	}

	public void setScheduledExecutor(ScheduledThreadPoolExecutor scheduledExecutor)
	{
		this.scheduledExecutor = scheduledExecutor;
	}
}
