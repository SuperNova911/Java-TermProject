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
	public static String PATH_DOCUMENT = System.getProperty("user.home") + "\\Documents\\BlackCow\\";
	public static String NAME_ACCOUNT = "account.BlackCow";
	public static String NAME_CRYPTO = "crypto.BlackCow";

	public static void save(Object object, String name, String path)
	{
		try
		{
			createDirectory(path);
			File file = new File(path + name);
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(object);
			objectOutputStream.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static Object load(String name, String path) throws Exception
	{
		try
		{
			File file = new File(path + name);
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

			Object object = objectInputStream.readObject();
			objectInputStream.close();

			return object;
		}
		catch (ClassNotFoundException | IOException e)
		{
			System.out.println("Failed to load, " + name + ", " + path);
			throw new Exception();
		}
	}

	public static void createDirectory(String name)
	{
		File file = new File(name);
		file.mkdirs();
	}
}
