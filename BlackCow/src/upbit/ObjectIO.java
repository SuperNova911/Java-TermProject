package upbit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectIO
{
	static class Coin
	{
		public static String COIN_PATH = "./crypto/";
		
		public static void save(CryptoCurrency cryptoCurrency, String name)
		{
			try
			{
				File file = new File(name);
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				
				objectOutputStream.writeObject(cryptoCurrency);
				objectOutputStream.flush();
				objectOutputStream.close();
			}
			catch (FileNotFoundException e)
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
		}
		
		public static CryptoCurrency load(String name)
		{
			CryptoCurrency cryptoCurrency = null;
			
			try
			{
				File file = new File(COIN_PATH + name);
				ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
				
				cryptoCurrency = (CryptoCurrency) objectInputStream.readObject();
				objectInputStream.close();
			}
			catch (FileNotFoundException e)
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
			catch (ClassNotFoundException e)
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();
			}
			
			return cryptoCurrency;
		}
	}
	
	
	public static void createDirectory(String name)
	{
		File file = new File(name);
		file.mkdirs();
	}
}
