package upbit;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

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
	private ExecutorService executorService;
	
	private WebDriver driver;
	private Actions actions;
	
	private boolean isReady = false;
	private boolean headless = false;
	private String baseXPath = "//*[@id=\"root\"]/div/div/div[3]/div/section[1]/div/div[1]/article/span[2]/div/div/div[1]/table/tbody";
	private String upbitAddress = "https://upbit.com/home";
	
	
	public DynamicCrawler(boolean headless)
	{
		setHeadless(headless);
		launchBrowser();
		moveTo(createURL(Market.KRW, CoinSymbol.BTC));
	}
	
	public void initiate()
	{
		executorService.submit(()->
		{
			String selector = "#root > div > div > div:nth-child(4) > div > section.ty01 > div > div.leftB > article > span.askpriceB > div > div > div:nth-child(1) > table > tbody";
			waitUntilLoad(30, By.cssSelector(selector));
			isReady = true;
			
			System.out.println("DynamicCrawler ready");
		});
	}
	
	public boolean launchBrowser()
	{
		try
		{
			File file = new File("chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			
			ChromeOptions options = new ChromeOptions();
			options.setHeadless(isHeadless());
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
		if (driver.getCurrentUrl().equals(url))
			return true;
		
		try
		{
			driver.get(url);
//			System.out.println("Move to: " + url);
			return true;
		}
		catch (Exception e)
		{
			System.out.println("Failed to moveTo: " + url);
			return false;
		}
	}
	
	public boolean waitUntilLoad(int sec, By locator)
	{
		try
		{
//			System.out.println("Wait for load elements");
			new WebDriverWait(driver, sec).until(ExpectedConditions.visibilityOfElementLocated(locator));
//			System.out.println("Load complete");
			return true;	
		}
		catch (Exception e)
		{
			System.out.println("Failed to waitUntilLoad, locator: " + locator);
			return false;
		}
	}
	
	public boolean scrollToElement(WebElement element)
	{
		try
		{
			actions.moveToElement(element).perform();
//			System.out.println("Scroll to element");
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
		OrderBook orderBook = new OrderBook(market, coinSymbol);
		String url = createURL(market, coinSymbol);
		String xpath = createXpath(20, OrderData.price, false);
		
		moveTo(url);
		waitUntilLoad(30, By.xpath(xpath));
		scrollToElement(findElementByXPath(xpath));

		String price;
		String quantity;
		String percentage;
		boolean buy;
		
		for (int i = 1; i <= 20; i++)
		{
			if (i <= 10)
				buy = false;
			else
				buy = true;
			
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
			
			orderBook.addOrderBookElement(price, quantity, percentage, buy);
		}
		
		return orderBook;
	}

	public OrderBook getOrderBook_Fast(Market market, CoinSymbol coinSymbol)
	{
		OrderBook orderBook = new OrderBook(market, coinSymbol);
		String url = createURL(market, coinSymbol);
		String selector = "#root > div > div > div:nth-child(4) > div > section.ty01 > div > div.leftB > article > span.askpriceB > div > div > div:nth-child(1) > table > tbody";
		String selector2 = "#root > div > div > div:nth-child(4) > div > section.ty01 > div > div.leftB > article > span.askpriceB > div > div > div:nth-child(1) > table > tbody > tr:nth-child(20)";
		
		moveTo(url);
		waitUntilLoad(30, By.cssSelector(selector));
		scrollToElement(driver.findElement(By.cssSelector(selector2)));
		
		String data = driver.findElement(By.cssSelector(selector)).getText();
		data = data.replaceAll("-\n",  "").replaceAll(",", "").replaceAll("%", "");

		String[] array = data.split("\n");
		ArrayList<String> dataList = new ArrayList<String>();
		
		for (int index = 0; index < array.length; index++)
		{
			if ((index >= 3 && index <= 21) || (index >= 49 && index <= 71))
				continue;
			
			dataList.add(array[index]);
		}
		
		String price;
		String quantity;
		String percentage;
		boolean buy;
		
		for (int index = 0; index < 20; index++)
		{
			if (index < 10)
			{
				quantity = dataList.get((index * 3) + 0);
				price = dataList.get((index * 3) + 1);
				percentage = dataList.get((index * 3) + 2);
				buy = false;
			}
			else
			{
				price = dataList.get((index * 3) + 0);
				percentage = dataList.get((index * 3) + 1);
				quantity = dataList.get((index * 3) + 2);
				buy = true;
			}
			
			orderBook.addOrderBookElement(price, quantity, percentage, buy);
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

	public ExecutorService getExecutorService()
	{
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService)
	{
		this.executorService = executorService;
	}
	
	public boolean isReady()
	{
		return isReady;
	}

	public void setReady(boolean isReady)
	{
		this.isReady = isReady;
	}

	public boolean isHeadless()
	{
		return headless;
	}

	public void setHeadless(boolean headless)
	{
		this.headless = headless;
	}
}
