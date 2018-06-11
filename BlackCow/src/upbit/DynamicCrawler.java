package upbit;

import java.io.File;

import org.jsoup.Connection.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;
import upbit.OrderBook.OrderData;

public class DynamicCrawler 
{
	private WebDriver driver;
	private Actions actions;
	
	private boolean headless = false;
	private String baseXPath = "//*[@id=\"root\"]/div/div/div[3]/div/section[1]/div/div[1]/article/span[2]/div/div/div[1]/table/tbody";
	
	
	public DynamicCrawler()
	{
		launchBrowser();
	}

	public boolean launchBrowser()
	{
		try
		{
			File file = new File("chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			
			ChromeOptions options = new ChromeOptions();
			options.setHeadless(headless);
			options.addArguments("disable-gpu");
			
			driver = new ChromeDriver(options);
			actions = new Actions(driver);
			
			return true;
		}
		catch (Exception e)
		{
			System.out.println("Failed to launchBrower");
			return false;
		}
	}
	
	public void quitBrowser()
	{
		driver.close();
		driver.quit();
	}
	
	public String createURL(Market market, CoinSymbol coinSymbol)
	{
		String url = "https://www.upbit.com/exchange?code=CRIX.UPBIT.";
		
		url += market + "-" + coinSymbol;
		
		return url;
	}
	
	public String createXpath(int n, OrderData orderData, boolean selected)
	{
		if (!(1 <= n && n <= 20))
		{
			System.out.println("Wrong input: " + n + ", " + orderData);
			n = 1;
		}
		
		String xpath = baseXPath + "/tr";
		xpath += "[" + n + "]";
		
		int kappa;
		if (n <= 10)
			kappa = 3;
		else
			kappa = selected ? 2 : 1;
		
		int fappa = selected ? 3 : 2;
		
		switch (orderData)
		{
		case price:
			xpath += "/td[" + kappa + "]/a/div[1]/strong";
			break;
			
		case quantity:
			xpath += "/td[" + fappa + "]/a/p";
			break;
				
		case percentage:
			xpath += "/td[" + kappa + "]/a/div[2]";
			break;
		}
		
		return xpath;
	}
	
	public boolean moveTo(String url)
	{
		try
		{
			driver.get(url);
			System.out.println("Move to: " + url);
			return true;
		}
		catch (Exception e)
		{
			System.out.println("Failed to moveTo: " + url);
			return false;
		}
	}
	
	public boolean waitUntilLoad(int sec, String xpath)
	{
		try
		{
			System.out.println("Wait for load elements");
			new WebDriverWait(driver, sec).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			System.out.println("Load complete");
			return true;	
		}
		catch (Exception e)
		{
			System.out.println("Failed to waitUntilLoad, xpath: " + xpath);
			return false;
		}
	}
	
	public boolean scrollToElement(WebElement element)
	{
		try
		{
			actions.moveToElement(element).perform();
			System.out.println("Scroll to element");
			return true;
		}
		catch (Exception e)
		{
			System.out.println("Failed to scrollToElement: " + element.getText());
			return false;
		}
	}
	
	public OrderBook getOrderBook(Market market, CoinSymbol coinSymbol)
	{
		OrderBook orderBook = new OrderBook();
		String url = createURL(market, coinSymbol);
		String xpath = createXpath(20, OrderData.price, false);
		
		moveTo(url);
		waitUntilLoad(30, xpath);
		scrollToElement(findElementByXPath(xpath));
		
		String price;
		String quantity;
		String percentage;
		
		for (int i = 1; i <= 20; i++)
		{
			try
			{
				price = findElementByXPath(createXpath(i, OrderData.price, false)).getText();
				quantity = findElementByXPath(createXpath(i, OrderData.quantity, false)).getText();
				percentage = findElementByXPath(createXpath(i, OrderData.percentage, false)).getText();
			}
			catch (Exception e)
			{
				price = findElementByXPath(createXpath(i, OrderData.price, true)).getText();
				quantity = findElementByXPath(createXpath(i, OrderData.quantity, true)).getText();
				percentage = findElementByXPath(createXpath(i, OrderData.percentage, true)).getText();
			}
			
			orderBook.addOrder(price, quantity, percentage);
		}
		
		return orderBook;
	}
	
	public WebElement findElementByXPath(WebElement element, String xpath)
	{
		try
		{
			return element.findElement(By.xpath(xpath));
		} 
		catch (NoSuchElementException e)
		{
			System.out.println("Failed to findElementByXPath, xpath: " + xpath);
			
			return null;
		}
	}
	
	public WebElement findElementByXPath(String xpath)
	{
		try
		{
			return driver.findElement(By.xpath(xpath));
		} 
		catch (NoSuchElementException e)
		{
			System.out.println("Failed to findElementByXPath, xpath: " + xpath);
			
			return null;
		}
	}
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public Actions getActions()
	{
		return actions;
	}
	
	public String getBaseXPath()
	{
		return baseXPath;
	}
	
	
	public void test()
	{
		String xpath = "//*[@id=\"PM_ID_ct\"]/div[1]/div[1]/div/div[2]/a[1]";
		moveTo("https://www.naver.com/");
		new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		
		WebElement kappa;
		
		kappa = findElementByXPath(xpath);

		System.out.println(kappa.getAttribute("id"));
		System.out.println(kappa.getText());
	}
}
