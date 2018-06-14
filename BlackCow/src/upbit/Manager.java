package upbit;

import java.util.concurrent.*;

import gui.GUI;

public class Manager
{
	private ScheduledThreadPoolExecutor scheduledExecutor;
	private ExecutorService executorService;
	
	private Account account;
	private Upbit upbit;
	private GUI gui;
	private DynamicCrawler crawler;
	
	
	public Manager()
	{
		initiate();
	}
	
	public void initiate()
	{
		System.out.println("##############################");
		
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		
		scheduledExecutor = new ScheduledThreadPoolExecutor(4);
		executorService = new ThreadPoolExecutor(4, 8, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		
		account = new Account("id", "password", 5000000);
		upbit = new Upbit(account);
		
		Future<?> futureUpbit = executorService.submit(()-> upbit = new Upbit(account));
		Future<?> futureCrawler = executorService.submit(()-> crawler = new DynamicCrawler());
		Future<?> futureGui = executorService.submit(()-> gui = new GUI());

		try
		{
			futureUpbit.get();
			futureCrawler.get();
			futureGui.get();
		}
		catch (InterruptedException | ExecutionException e)
		{
			System.out.println("Failed to initiate");
			e.printStackTrace();
		}

		// Upbit
		upbit.setScheduledExecutor(scheduledExecutor);
		upbit.setExecutorService(executorService);
		upbit.setGui(gui);
		upbit.setCrawler(crawler);
		
		// GUI
		gui.setCrawler(crawler);	
		gui.setUpbit(upbit);
		gui.setExecutorService(executorService);
		
		// DynamicCralwer
		crawler.setExecutorService(executorService);

		gui.initiate();
		upbit.initiate();
		
		// GUI 보여주기
		gui.setVisible(true);
		
		endTime = System.currentTimeMillis();
		
		System.out.println("Initiate complete, " + (endTime - startTime) + "ms");
		System.out.println("##############################");
	}
}
