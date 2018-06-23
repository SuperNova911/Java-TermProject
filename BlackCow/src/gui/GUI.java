package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultHighLowDataset;

import upbit.Account;
import upbit.CoinList;
import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;
import upbit.CryptoCurrency;
import upbit.DynamicCrawler;
import upbit.JsonManager.JsonKey;
import upbit.Order;
import upbit.OrderBook;
import upbit.OrderBookElement;
import upbit.Request.TermType;
import upbit.Stat;
import upbit.Upbit;
import upbit.Wallet;

@SuppressWarnings("serial")
public class GUI extends JFrame
{
	private Upbit upbit;
	private DynamicCrawler crawler;
	private ExecutorService executorService;
	
	private ArrayList<CoinTableElement> coinTableElements;
	private ArrayList<MyCoinTableElement> myCoinTableElements; 
	private ArrayList<OrderTableElement> orderTableElements;
	private ArrayList<TradeHistoryTableElement> tradeHistoryTableElements;
	private ArrayList<TradeHistoryTableElement> tradeHistoryTableElements_Complete;

	private int selectedRow;
	private Market currentMarket;
	private CoinSymbol currentCoinSymbol;
	private TermType currentTermType;
	private int currentTerm;
	
	private double buyBalance;
	private double buyPrice;
	private double buyQuantity;
	private double buyTotal;
	private double sellBalance;
	private double sellPrice;
	private double sellQuantity;
	private double sellTotal;
	private double buySellRatio;
	
	DecimalFormat decimalFormat;
	
	private ImageIcon icon;

	private JPanel contentPane;
	private JPanel panel_Chart;
	private JPanel panel_LoadingOrderBook;
	private JScrollPane scrollPane_OrderBook;
	private JLayeredPane layeredPane_OrderBook;
	private JFreeChart chart;
	
	private JTextField textField_SearchKRW;
	private JTextField textField_searchMyCoin;
	private JTextField textField_SearchBTC;
	private JTextField textField_SearchETH;

	private JSpinner spinner_BuyPrice;
	private JSpinner spinner_SellPrice;
	private JTextField textField_BuyQuantity;
	private JTextField textField_SellQuantity;
	private JComboBox<String> comboBox_BuyQuantity;
	private JComboBox<String> comboBox_SellQuantity;
	private JComboBox<String> comboBox_ChartTermType;
	
	private JTable table_KRW;
	private JTable table_BTC;
	private JTable table_ETH;
	private JTable table_MyCoin;
	private JTable table_OrderBook;
	private JTable table_TradeHistoryNotComplete;
	private JTable table_TradeHistoryComplete;
	
	private JLabel label_InfoIcon;
	private JLabel label_InfoHighPrice;
	private JLabel label_InfoLowPrice;
	private JLabel label_InfoTradePrice;
	private JLabel label_InfoName;
	private JLabel label_InfoSymbol;
	private JLabel label_InfoVolume;
	private JLabel label_InfoChangeRate;
	private JLabel label_InfoChangePrice;
	
	private JLabel label_BuyBalance;
	private JLabel label_BuyTotal;
	private JLabel label_BuyBalanceSymbol;
	private JLabel label_BuyPriceSymbol;
	private JLabel label_BuyQuantitySymbol;
	private JLabel label_BuyTotalSymbol;
	private JLabel label_SellBalance;
	private JLabel label_SellTotal;
	private JLabel label_SellBalanceSymbol;
	private JLabel label_SellPriceSymbol;
	private JLabel label_SellQuantitySymbol;
	private JLabel label_SellTotalSymbol;
	
	private JLabel label_ChartInfo;
	
	private JLabel label_AssetValue;
	private JLabel label_Seed;
	private JLabel label_Earnings;
	private JLabel label_EarningsRate;



	public GUI()
	{
		setTitle("ºí·¢¸»¶ûÄ«¿ì");
		setResizable(false);
		setBounds(0, 0, 1440, 810);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
				System.out.println("Closing DynamicCrawler.....");
				crawler.quitBrowser();
				System.out.println("Exit Program");
				System.exit(0);
			}
		});

		
		
		JPanel panel_Right = new JPanel();
		panel_Right.setBackground(Color.WHITE);
		panel_Right.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Right.setBounds(1134, 21, 300, 760);
		contentPane.add(panel_Right);
		panel_Right.setLayout(null);
		
		JPanel panel_Asset = new JPanel();
		panel_Asset.setBackground(Color.WHITE);
		panel_Asset.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Asset.setBounds(0, 0, 300, 160);
		panel_Right.add(panel_Asset);
		panel_Asset.setLayout(null);
		
		JLabel label_AssetValueText = new JLabel("³ªÀÇ ÀÚ»ê °¡Ä¡");
		label_AssetValueText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
		label_AssetValueText.setBounds(12, 10, 162, 15);
		panel_Asset.add(label_AssetValueText);
		
		label_AssetValue = new JLabel("0 KRW");
		label_AssetValue.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 26));
		label_AssetValue.setHorizontalAlignment(SwingConstants.RIGHT);
		label_AssetValue.setBounds(73, 35, 215, 30);
		panel_Asset.add(label_AssetValue);
		
		JLabel label_SeedText = new JLabel("½ÃÀÛ ±Ý¾×");
		label_SeedText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_SeedText.setBounds(12, 90, 57, 15);
		panel_Asset.add(label_SeedText);
		
		JLabel label_EarningsText = new JLabel("¼öÀÍ");
		label_EarningsText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_EarningsText.setBounds(12, 112, 57, 15);
		panel_Asset.add(label_EarningsText);
		
		label_Seed = new JLabel("0");
		label_Seed.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_Seed.setBounds(81, 90, 111, 15);
		panel_Asset.add(label_Seed);
		
		label_Earnings = new JLabel("0");
		label_Earnings.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_Earnings.setBounds(81, 112, 93, 15);
		panel_Asset.add(label_Earnings);
		
		JLabel label_EarningsRateText = new JLabel("¼öÀÍ·ü");
		label_EarningsRateText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_EarningsRateText.setBounds(12, 134, 57, 15);
		panel_Asset.add(label_EarningsRateText);
		
		label_EarningsRate = new JLabel("0.00%");
		label_EarningsRate.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_EarningsRate.setBounds(81, 134, 93, 15);
		panel_Asset.add(label_EarningsRate);
		
		layeredPane_OrderBook = new JLayeredPane();
		layeredPane_OrderBook.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		layeredPane_OrderBook.setBounds(0, 160, 300, 598);
		panel_Right.add(layeredPane_OrderBook);
		
		scrollPane_OrderBook = new JScrollPane();
		scrollPane_OrderBook.setBounds(0, 0, 300, 598);
		layeredPane_OrderBook.add(scrollPane_OrderBook, 0);
		
		table_OrderBook = new JTable();
		table_OrderBook.addMouseListener(new TableListener_OrderBook());
		table_OrderBook.setBackground(Color.WHITE);
		table_OrderBook.setShowVerticalLines(false);
		table_OrderBook.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		table_OrderBook.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "", "", "", "" })
		{
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		});
		table_OrderBook.getColumnModel().getColumn(0).setResizable(false);
		table_OrderBook.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_OrderBook.getColumnModel().getColumn(1).setResizable(false);
		table_OrderBook.getColumnModel().getColumn(1).setPreferredWidth(80);
		table_OrderBook.getColumnModel().getColumn(2).setResizable(false);
		table_OrderBook.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_OrderBook.getColumnModel().getColumn(3).setResizable(false);
		table_OrderBook.getColumnModel().getColumn(3).setPreferredWidth(20);

		table_OrderBook.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer_OrderTable());
		table_OrderBook.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer_OrderTable());
		table_OrderBook.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer_OrderTable());
		table_OrderBook.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer_OrderTable());
		table_OrderBook.setTableHeader(null);
		table_OrderBook.setRowHeight(30);
		scrollPane_OrderBook.setViewportView(table_OrderBook);

		panel_LoadingOrderBook = new JPanel();
		panel_LoadingOrderBook.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_LoadingOrderBook.setBackground(Color.WHITE);
		panel_LoadingOrderBook.setBounds(0, 0, 300, 598);
		layeredPane_OrderBook.add(panel_LoadingOrderBook, new Integer(300));
		panel_LoadingOrderBook.setLayout(null);

		JLabel label_LoadingOrderBook = new JLabel("OrderBook ºÒ·¯¿À´Â Áß");
		label_LoadingOrderBook.setHorizontalAlignment(SwingConstants.CENTER);
		label_LoadingOrderBook.setBounds(12, 161, 276, 21);
		label_LoadingOrderBook.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 15));
		panel_LoadingOrderBook.add(label_LoadingOrderBook);

		JPanel panel_Center = new JPanel();
		panel_Center.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Center.setBounds(300, 21, 834, 760);
		contentPane.add(panel_Center);
		panel_Center.setLayout(null);
		
		JPanel panel_Bottom = new JPanel();
		panel_Bottom.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Bottom.setBounds(0, 490, 834, 270);
		panel_Center.add(panel_Bottom);
		panel_Bottom.setLayout(null);
		
		JTabbedPane tabbedPane_BuySell = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_BuySell.setBackground(Color.WHITE);
		tabbedPane_BuySell.setBorder(null);
		tabbedPane_BuySell.setBounds(564, 0, 270, 270);
		panel_Bottom.add(tabbedPane_BuySell);
		
		JPanel panel_Buy = new JPanel();
		panel_Buy.setBackground(Color.WHITE);
		tabbedPane_BuySell.addTab("¸Å¼ö", null, panel_Buy, null);
		panel_Buy.setLayout(null);
		
		textField_BuyQuantity = new JTextField();
		textField_BuyQuantity.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		textField_BuyQuantity.addActionListener(e ->
		{
			String text = textField_BuyQuantity.getText();
			double quantity;

			try
			{
				quantity = Double.parseDouble(text);
				
				if (quantity < 0)
					quantity = 0;
				
				setBuyQuantity(quantity);
				updateBuySell(false);
			}
			catch (NumberFormatException e1)
			{
				updateBuySell(false);
			}
		});
		textField_BuyQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_BuyQuantity.setBounds(41, 92, 116, 21);
		panel_Buy.add(textField_BuyQuantity);
		textField_BuyQuantity.setColumns(10);
		
		JLabel label_BuyQuantityText = new JLabel("¼ö·®");
		label_BuyQuantityText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_BuyQuantityText.setBounds(12, 94, 51, 15);
		panel_Buy.add(label_BuyQuantityText);
		
		label_BuyQuantitySymbol = new JLabel("Symbol");
		label_BuyQuantitySymbol.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_BuyQuantitySymbol.setBounds(220, 94, 62, 15);
		panel_Buy.add(label_BuyQuantitySymbol);

		spinner_BuyPrice = new JSpinner();
		spinner_BuyPrice.addMouseWheelListener(new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				// Wheel Up
				if (e.getPreciseWheelRotation() < 0)
				{
					double price = spinnerValueControl(getBuyPrice(), true, getCurrentMarket());
					setBuyPrice(price);
					updateBuySell(false);
				}
				//Wheel Down
				else
				{
					double price = spinnerValueControl(getBuyPrice(), false, getCurrentMarket());
					setBuyPrice(price);
					updateBuySell(false);
				}
			}
		});
		// Increase ¹öÆ°
		((JButton) spinner_BuyPrice.getComponent(0)).addActionListener(e ->
		{
			double price = spinnerValueControl(getBuyPrice(), true, getCurrentMarket());
			setBuyPrice(price);
			updateBuySell(false);
		});
		// Decrease ¹öÆ°
		((JButton) spinner_BuyPrice.getComponent(1)).addActionListener(e ->
		{
			double price = spinnerValueControl(getBuyPrice(), false, getCurrentMarket());
			setBuyPrice(price);
			updateBuySell(false);
		});
		spinner_BuyPrice.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(0)));
		spinner_BuyPrice.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		spinner_BuyPrice.setBounds(41, 56, 175, 21);
		panel_Buy.add(spinner_BuyPrice);
		
		JLabel label_BuyPriceText = new JLabel("°¡°Ý");
		label_BuyPriceText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_BuyPriceText.setBounds(12, 58, 51, 15);
		panel_Buy.add(label_BuyPriceText);
		
		label_BuyPriceSymbol = new JLabel("Symbol");
		label_BuyPriceSymbol.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_BuyPriceSymbol.setBounds(220, 58, 62, 15);
		panel_Buy.add(label_BuyPriceSymbol);
		
		JButton button_BuyRequest = new JButton("¸Å¼ö");
		button_BuyRequest.addActionListener(new Button_BuySell(true));
		button_BuyRequest.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		button_BuyRequest.setForeground(Color.WHITE);
		button_BuyRequest.setBackground(Color.RED);
		button_BuyRequest.setBounds(12, 200, 241, 31);
		panel_Buy.add(button_BuyRequest);
		
		JPanel panel_buyTotal = new JPanel();
		panel_buyTotal.setLayout(null);
		panel_buyTotal.setBounds(12, 146, 241, 39);
		panel_Buy.add(panel_buyTotal);
		
		label_BuyTotal = new JLabel("0");
		label_BuyTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		label_BuyTotal.setForeground(Color.RED);
		label_BuyTotal.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		label_BuyTotal.setBounds(69, 9, 130, 15);
		panel_buyTotal.add(label_BuyTotal);
		
		JLabel label_BuyTotalText = new JLabel("\uC8FC\uBB38\uCD1D\uC561");
		label_BuyTotalText.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_BuyTotalText.setBounds(12, 11, 57, 15);
		panel_buyTotal.add(label_BuyTotalText);
		
		label_BuyTotalSymbol = new JLabel("Symbol");
		label_BuyTotalSymbol.setBounds(200, 10, 62, 15);
		panel_buyTotal.add(label_BuyTotalSymbol);
		label_BuyTotalSymbol.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		
		JLabel label_BalanceText = new JLabel("\uC8FC\uBB38\uAC00\uB2A5");
		label_BalanceText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_BalanceText.setBounds(12, 20, 57, 15);
		panel_Buy.add(label_BalanceText);
		
		comboBox_BuyQuantity = new JComboBox<String>();
		comboBox_BuyQuantity.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		comboBox_BuyQuantity.addActionListener(new ComboListener_BuySell(true));
		comboBox_BuyQuantity.setModel(new DefaultComboBoxModel<String>(new String[] {"100%", "75%", "50%", "25%"}));
		comboBox_BuyQuantity.setBounds(156, 92, 60, 21);
		panel_Buy.add(comboBox_BuyQuantity);
		
		label_BuyBalanceSymbol = new JLabel("Symbol");
		label_BuyBalanceSymbol.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_BuyBalanceSymbol.setBounds(220, 20, 57, 15);
		panel_Buy.add(label_BuyBalanceSymbol);
		
		label_BuyBalance = new JLabel("0\r\n");
		label_BuyBalance.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		label_BuyBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		label_BuyBalance.setBounds(62, 20, 154, 15);
		panel_Buy.add(label_BuyBalance);
		
		JPanel panel_Sell = new JPanel();
		panel_Sell.setBackground(Color.WHITE);
		tabbedPane_BuySell.addTab("¸Åµµ", null, panel_Sell, null);
		panel_Sell.setLayout(null);
		
		textField_SellQuantity = new JTextField();
		textField_SellQuantity.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		textField_SellQuantity.addActionListener(e ->
		{
			String text = textField_BuyQuantity.getText();
			double quantity;

			try
			{
				quantity = Double.parseDouble(text);
				
				if (quantity < 0)
					quantity = 0;
				
				setSellQuantity(quantity);
				updateBuySell(false);
			}
			catch (NumberFormatException e1)
			{
				updateBuySell(false);
			}
		});
		textField_SellQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_SellQuantity.setColumns(10);
		textField_SellQuantity.setBounds(41, 92, 116, 21);
		panel_Sell.add(textField_SellQuantity);
		
		JLabel label_SellQuantityText = new JLabel("¼ö·®");
		label_SellQuantityText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_SellQuantityText.setBounds(12, 94, 51, 15);
		panel_Sell.add(label_SellQuantityText);
		
		label_SellQuantitySymbol = new JLabel("Symbol");
		label_SellQuantitySymbol.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_SellQuantitySymbol.setBounds(220, 94, 62, 15);
		panel_Sell.add(label_SellQuantitySymbol);
		
		spinner_SellPrice = new JSpinner();
		spinner_SellPrice.addMouseWheelListener(new MouseWheelListener()
		{
			@Override
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				// Wheel Up
				if (e.getWheelRotation() < 0)
				{
					double price = spinnerValueControl(getSellPrice(), true, getCurrentMarket());
					setSellPrice(price);
					updateBuySell(false);
				}
				// Wheel Down
				else
				{
					double price = spinnerValueControl(getSellPrice(), false, getCurrentMarket());
					setSellPrice(price);
					updateBuySell(false);
				}
			}
		});
		// Increase ¹öÆ°
		((JButton) spinner_SellPrice.getComponent(0)).addActionListener(e ->
		{
			double price = spinnerValueControl(getSellPrice(), true, getCurrentMarket());
			setSellPrice(price);
			updateBuySell(false);
		});
		// Decrease ¹öÆ°
		((JButton) spinner_SellPrice.getComponent(1)).addActionListener(e ->
		{
			double price = spinnerValueControl(getSellPrice(), false, getCurrentMarket());
			setSellPrice(price);
			updateBuySell(false);
		});
		spinner_SellPrice.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(0)));
		spinner_SellPrice.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		spinner_SellPrice.setBounds(41, 56, 175, 21);
		panel_Sell.add(spinner_SellPrice);
		
		JLabel label_SellPriceText = new JLabel("°¡°Ý");
		label_SellPriceText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_SellPriceText.setBounds(12, 58, 51, 15);
		panel_Sell.add(label_SellPriceText);
		
		label_SellPriceSymbol = new JLabel("Symbol");
		label_SellPriceSymbol.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_SellPriceSymbol.setBounds(220, 58, 62, 15);
		panel_Sell.add(label_SellPriceSymbol);
		
		JButton button_SellRequest = new JButton("¸Åµµ");
		button_SellRequest.addActionListener(new Button_BuySell(false));
		button_SellRequest.setForeground(Color.WHITE);
		button_SellRequest.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		button_SellRequest.setBackground(Color.BLUE);
		button_SellRequest.setBounds(12, 200, 241, 31);
		panel_Sell.add(button_SellRequest);
		
		JPanel panel_SellTotal = new JPanel();
		panel_SellTotal.setLayout(null);
		panel_SellTotal.setBounds(12, 146, 241, 39);
		panel_Sell.add(panel_SellTotal);
		
		label_SellTotal = new JLabel("0");
		label_SellTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		label_SellTotal.setForeground(Color.BLUE);
		label_SellTotal.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 16));
		label_SellTotal.setBounds(69, 9, 130, 15);
		panel_SellTotal.add(label_SellTotal);
		
		JLabel label_SellTotalText = new JLabel("\uC8FC\uBB38\uCD1D\uC561");
		label_SellTotalText.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_SellTotalText.setBounds(12, 11, 57, 15);
		panel_SellTotal.add(label_SellTotalText);
		
		label_SellTotalSymbol = new JLabel("Symbol");
		label_SellTotalSymbol.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_SellTotalSymbol.setBounds(200, 10, 62, 15);
		panel_SellTotal.add(label_SellTotalSymbol);
		
		JLabel lable_SellBalanceText = new JLabel("\uC8FC\uBB38\uAC00\uB2A5");
		lable_SellBalanceText.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lable_SellBalanceText.setBounds(12, 20, 57, 15);
		panel_Sell.add(lable_SellBalanceText);
		
		label_SellBalanceSymbol = new JLabel("Symbol");
		label_SellBalanceSymbol.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_SellBalanceSymbol.setHorizontalAlignment(SwingConstants.LEFT);
		label_SellBalanceSymbol.setBounds(220, 20, 57, 15);
		panel_Sell.add(label_SellBalanceSymbol);
		
		label_SellBalance = new JLabel("0");
		label_SellBalance.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		label_SellBalance.setHorizontalAlignment(SwingConstants.RIGHT);
		label_SellBalance.setBounds(70, 20, 146, 15);
		panel_Sell.add(label_SellBalance);
		
		comboBox_SellQuantity = new JComboBox<String>();
		comboBox_SellQuantity.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		comboBox_SellQuantity.addActionListener(new ComboListener_BuySell(false));
		comboBox_SellQuantity.setModel(new DefaultComboBoxModel<String>(new String[] {"100%", "75%", "50%", "25%"}));
		comboBox_SellQuantity.setBounds(156, 92, 60, 21);
		panel_Sell.add(comboBox_SellQuantity);
		
		JTabbedPane tabbedPane_TradeHistory = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_TradeHistory.setBackground(Color.WHITE);
		tabbedPane_TradeHistory.setBounds(0, 0, 564, 270);
		panel_Bottom.add(tabbedPane_TradeHistory);
		
		JScrollPane scrollPane_TradeNotComplete = new JScrollPane();
		scrollPane_TradeNotComplete.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		tabbedPane_TradeHistory.addTab("¹Ì Ã¼°á ÁÖ¹®", null, scrollPane_TradeNotComplete, null);
		
		table_TradeHistoryNotComplete = new JTable();
		table_TradeHistoryNotComplete.addMouseListener(new TableListener_TradeHistory());
		table_TradeHistoryNotComplete.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uB9C8\uCF13", "\uC774\uB984", "\uC8FC\uBB38\uC2DC\uAC04", "\uAD6C\uBD84", "\uCCB4\uACB0 \uAC00\uACA9", "\uCCB4\uACB0 \uC218\uB7C9", "\uCCB4\uACB0 \uAE08\uC561", "\uC8FC\uBB38\uCDE8\uC18C"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, Object.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		for (int i = 0; i < 8; i++)
		{
			table_TradeHistoryNotComplete.getColumnModel().getColumn(i).setResizable(false);
			table_TradeHistoryNotComplete.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer_TradeHistoryTable());
		}
		table_TradeHistoryNotComplete.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(2).setPreferredWidth(75);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(3).setPreferredWidth(45);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(4).setPreferredWidth(95);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(5).setPreferredWidth(95);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(6).setPreferredWidth(95);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(7).setPreferredWidth(60);
		table_TradeHistoryNotComplete.setRowHeight(25);
		table_TradeHistoryNotComplete.getTableHeader().setReorderingAllowed(false);
		scrollPane_TradeNotComplete.setViewportView(table_TradeHistoryNotComplete);
		
		JScrollPane scrollPane_TradeComplete = new JScrollPane();
		scrollPane_TradeComplete.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		tabbedPane_TradeHistory.addTab("Ã¼°á ¿Ï·á ³»¿ª", null, scrollPane_TradeComplete, null);
		
		table_TradeHistoryComplete = new JTable();
		table_TradeHistoryComplete.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uB9C8\uCF13", "\uC774\uB984", "\uC8FC\uBB38\uC2DC\uAC04", "\uAD6C\uBD84", "\uCCB4\uACB0 \uAC00\uACA9", "\uCCB4\uACB0 \uC218\uB7C9", "\uCCB4\uACB0 \uAE08\uC561", ""
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class, String.class, Object.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		for (int i = 0; i < 8; i++)
		{
			table_TradeHistoryComplete.getColumnModel().getColumn(i).setResizable(false);
			table_TradeHistoryComplete.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer_TradeHistoryTable());
		}
		table_TradeHistoryComplete.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_TradeHistoryComplete.getColumnModel().getColumn(1).setPreferredWidth(50);
		table_TradeHistoryComplete.getColumnModel().getColumn(2).setPreferredWidth(75);
		table_TradeHistoryComplete.getColumnModel().getColumn(3).setPreferredWidth(45);
		table_TradeHistoryComplete.getColumnModel().getColumn(4).setPreferredWidth(95);
		table_TradeHistoryComplete.getColumnModel().getColumn(5).setPreferredWidth(95);
		table_TradeHistoryComplete.getColumnModel().getColumn(6).setPreferredWidth(95);
		table_TradeHistoryComplete.getColumnModel().getColumn(7).setPreferredWidth(60);
		table_TradeHistoryComplete.setRowHeight(25);
		table_TradeHistoryComplete.getTableHeader().setReorderingAllowed(false);
		scrollPane_TradeComplete.setViewportView(table_TradeHistoryComplete);
		
		JPanel panel_Top = new JPanel();
		panel_Top.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Top.setBounds(0, 0, 834, 490);
		panel_Center.add(panel_Top);
		panel_Top.setLayout(null);
		
		JPanel panel_Info = new JPanel();
		panel_Info.setBackground(Color.WHITE);
		panel_Info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Info.setBounds(0, 0, 834, 55);
		panel_Top.add(panel_Info);
		panel_Info.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uAC70\uB798\uB300\uAE08 (24H)");
		lblNewLabel.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(650, 10, 86, 15);
		panel_Info.add(lblNewLabel);
		
		label_InfoVolume = new JLabel("0");
		label_InfoVolume.setHorizontalAlignment(SwingConstants.LEFT);
		label_InfoVolume.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 10));
		label_InfoVolume.setBounds(650, 27, 111, 15);
		panel_Info.add(label_InfoVolume);
		
		JLabel lblNewLabel_2 = new JLabel("\uACE0\uAC00");
		lblNewLabel_2.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setBounds(545, 10, 57, 15);
		panel_Info.add(lblNewLabel_2);
		
		JLabel label = new JLabel("\uC800\uAC00");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label.setBounds(545, 26, 57, 15);
		panel_Info.add(label);
		
		label_InfoHighPrice = new JLabel("0");
		label_InfoHighPrice.setForeground(Color.RED);
		label_InfoHighPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		label_InfoHighPrice.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_InfoHighPrice.setBounds(555, 9, 76, 15);
		panel_Info.add(label_InfoHighPrice);
		
		label_InfoLowPrice = new JLabel("0");
		label_InfoLowPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		label_InfoLowPrice.setForeground(Color.BLUE);
		label_InfoLowPrice.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_InfoLowPrice.setBounds(555, 26, 76, 15);
		panel_Info.add(label_InfoLowPrice);
		
		JLabel label_3 = new JLabel("\uC804\uC77C\uB300\uBE44");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_3.setBounds(440, 10, 57, 15);
		panel_Info.add(label_3);
		
		label_InfoChangeRate = new JLabel("0%");
		label_InfoChangeRate.setHorizontalAlignment(SwingConstants.RIGHT);
		label_InfoChangeRate.setForeground(Color.BLACK);
		label_InfoChangeRate.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_InfoChangeRate.setBounds(385, 26, 76, 15);
		panel_Info.add(label_InfoChangeRate);
		
		label_InfoChangePrice = new JLabel("0");
		label_InfoChangePrice.setHorizontalAlignment(SwingConstants.RIGHT);
		label_InfoChangePrice.setForeground(Color.BLACK);
		label_InfoChangePrice.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_InfoChangePrice.setBounds(465, 26, 57, 15);
		panel_Info.add(label_InfoChangePrice);
		
		JPanel panel_InfoLeft = new JPanel();
		panel_InfoLeft.setBackground(Color.WHITE);
		panel_InfoLeft.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_InfoLeft.setBounds(0, 0, 373, 55);
		panel_Info.add(panel_InfoLeft);
		panel_InfoLeft.setLayout(null);
		
		label_InfoName = new JLabel("Name");
		label_InfoName.setBounds(70, 8, 275, 25);
		panel_InfoLeft.add(label_InfoName);
		label_InfoName.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		label_InfoName.setHorizontalAlignment(SwingConstants.LEFT);
		
		label_InfoSymbol = new JLabel("Symbol");
		label_InfoSymbol.setBounds(70, 30, 105, 17);
		panel_InfoLeft.add(label_InfoSymbol);
		label_InfoSymbol.setHorizontalAlignment(SwingConstants.LEFT);
		label_InfoSymbol.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		
		label_InfoTradePrice = new JLabel("0");
		label_InfoTradePrice.setBounds(150, 9, 185, 30);
		panel_InfoLeft.add(label_InfoTradePrice);
		label_InfoTradePrice.setForeground(Color.BLACK);
		label_InfoTradePrice.setHorizontalAlignment(SwingConstants.RIGHT);
		label_InfoTradePrice.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
			
		label_InfoIcon = new JLabel();
		label_InfoIcon.setBounds(15, 8, 40, 40);
		panel_InfoLeft.add(label_InfoIcon);
		
		panel_Chart = new JPanel();
		panel_Chart.setBackground(Color.WHITE);
		panel_Chart.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Chart.setBounds(0, 55, 834, 435);
        panel_Chart.setLayout(null);
		panel_Top.add(panel_Chart);
		
		chart = createChart(null);
        chart.getXYPlot().setOrientation(PlotOrientation.VERTICAL);
        chart.getXYPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().setRenderer(new CustomCandlestickRenderer());
        chart.setAntiAlias(false);
        chart.getTitle().setVisible(false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.addChartMouseListener(new ChartMouseListener()
		{
			@Override
			public void chartMouseMoved(ChartMouseEvent arg0)
			{
				label_ChartInfo.setText(arg0.getEntity().getToolTipText());
			}
			
			@Override
			public void chartMouseClicked(ChartMouseEvent arg0)
			{
				
			}
		});
        chartPanel.setMouseZoomable(false);
        chartPanel.setBounds(2, 24, 830, 409);
        chartPanel.setPreferredSize(new java.awt.Dimension(671, 380));
        panel_Chart.add(chartPanel);
        
        comboBox_ChartTermType = new JComboBox<String>();
        comboBox_ChartTermType.addActionListener(new ComboListener_Chart());
        comboBox_ChartTermType.setBounds(1, 1, 70, 23);
        panel_Chart.add(comboBox_ChartTermType);
        comboBox_ChartTermType.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
        comboBox_ChartTermType.setModel(new DefaultComboBoxModel<String>(new String[] {"1\uBD84", "3\uBD84", "5\uBD84", "10\uBD84", "15\uBD84", "30\uBD84", "1\uC2DC\uAC04", "4\uC2DC\uAC04", "1\uC77C", "1\uC8FC", "1\uB2EC"}));
        
        label_ChartInfo = new JLabel("Information");
        label_ChartInfo.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 14));
        label_ChartInfo.setHorizontalAlignment(SwingConstants.RIGHT);
        label_ChartInfo.setBounds(95, 1, 731, 23);
        panel_Chart.add(label_ChartInfo);
		
		JTabbedPane tabbedPane_CoinList = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_CoinList.setBackground(Color.WHITE);
		tabbedPane_CoinList.addChangeListener(new TabListener(tabbedPane_CoinList));
		tabbedPane_CoinList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tabbedPane_CoinList.setBounds(0, 21, 300, 760);
		contentPane.add(tabbedPane_CoinList);
		
		JPanel panel_KRW = new JPanel();
		panel_KRW.setBackground(Color.WHITE);
		tabbedPane_CoinList.addTab("KRW", null, panel_KRW, "¿øÈ­ ¸¶ÄÏ");
		panel_KRW.setLayout(null);
		
		textField_SearchKRW = new JTextField();
		textField_SearchKRW.setBounds(0, 0, 227, 25);
		panel_KRW.add(textField_SearchKRW);
		textField_SearchKRW.setColumns(10);
		
		JButton button_SearchKRW = new JButton("°Ë»ö");
		button_SearchKRW.setBounds(226, 0, 65, 25);
		panel_KRW.add(button_SearchKRW);
		
		JScrollPane scrollPane_KRW = new JScrollPane();
		scrollPane_KRW.setBounds(0, 27, 291, 700);
		scrollPane_KRW.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		panel_KRW.add(scrollPane_KRW);
		
		table_KRW = new JTable();
		table_KRW.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ÀÌ¸§", "ÇöÀç°¡", "ÀüÀÏ´ëºñ", "°Å·¡·®" }) 
		{
			Class[] columnTypes = new Class[] 
			{
				String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) 
			{
				return columnTypes[columnIndex];
			}
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		});
		table_KRW.getColumnModel().getColumn(0).setResizable(false);
		table_KRW.getColumnModel().getColumn(1).setResizable(false);
		table_KRW.getColumnModel().getColumn(2).setResizable(false);
		table_KRW.getColumnModel().getColumn(3).setResizable(false);
		table_KRW.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_KRW.getColumnModel().getColumn(2).setPreferredWidth(70);
		table_KRW.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_KRW.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_KRW.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_KRW.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_KRW.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer_CoinTable());

		table_KRW.setShowVerticalLines(false);
		table_KRW.setRowHeight(40);
		table_KRW.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		table_KRW.getTableHeader().setReorderingAllowed(false);
		table_KRW.addMouseListener(new TableListener_Coin(table_KRW));
		scrollPane_KRW.setViewportView(table_KRW);
		
		JPanel panel_BTC = new JPanel();
		tabbedPane_CoinList.addTab("BTC", null, panel_BTC, "BTC ¸¶ÄÏ");
		panel_BTC.setLayout(null);
		
		textField_SearchBTC = new JTextField();
		textField_SearchBTC.setColumns(10);
		textField_SearchBTC.setBounds(0, 0, 227, 25);
		panel_BTC.add(textField_SearchBTC);
		
		JButton button_SearchBTC = new JButton("°Ë»ö");
		button_SearchBTC.setBounds(226, 0, 65, 25);
		panel_BTC.add(button_SearchBTC);
		
		JScrollPane scrollPane_BTC = new JScrollPane();
		scrollPane_BTC.setBounds(0, 27, 291, 700);
		panel_BTC.add(scrollPane_BTC);
		
		table_BTC = new JTable();
		table_BTC.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ÀÌ¸§", "ÇöÀç°¡", "ÀüÀÏ´ëºñ", "°Å·¡·®" }) 
		{
			Class[] columnTypes = new Class[] 
			{
				String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) 
			{
				return columnTypes[columnIndex];
			}
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		});
		table_BTC.getColumnModel().getColumn(0).setResizable(false);
		table_BTC.getColumnModel().getColumn(1).setResizable(false);
		table_BTC.getColumnModel().getColumn(2).setResizable(false);
		table_BTC.getColumnModel().getColumn(3).setResizable(false);
		table_BTC.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_BTC.getColumnModel().getColumn(2).setPreferredWidth(70);
		table_BTC.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_BTC.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_BTC.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_BTC.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_BTC.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer_CoinTable());
		
		table_BTC.setShowVerticalLines(false);
		table_BTC.setRowHeight(40);
		table_BTC.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		table_BTC.getTableHeader().setReorderingAllowed(false);
		table_BTC.addMouseListener(new TableListener_Coin(table_BTC));
		scrollPane_BTC.setViewportView(table_BTC);
		
		JPanel panel_ETH = new JPanel();
		tabbedPane_CoinList.addTab("ETH", null, panel_ETH, "ETH ¸¶ÄÏ");
		panel_ETH.setLayout(null);
		
		textField_SearchETH = new JTextField();
		textField_SearchETH.setColumns(10);
		textField_SearchETH.setBounds(0, 0, 227, 25);
		panel_ETH.add(textField_SearchETH);
		
		JButton button_SearchETH = new JButton("°Ë»ö");
		button_SearchETH.setBounds(226, 0, 65, 25);
		panel_ETH.add(button_SearchETH);
		
		JScrollPane scrollPane_ETH = new JScrollPane();
		scrollPane_ETH.setBounds(0, 27, 291, 700);
		panel_ETH.add(scrollPane_ETH);
		
		table_ETH = new JTable();
		table_ETH.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ÀÌ¸§", "ÇöÀç°¡", "ÀüÀÏ´ëºñ", "°Å·¡·®" }) 
		{
			Class[] columnTypes = new Class[] 
			{
				String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) 
			{
				return columnTypes[columnIndex];
			}
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		});
		table_ETH.getColumnModel().getColumn(0).setResizable(false);
		table_ETH.getColumnModel().getColumn(1).setResizable(false);
		table_ETH.getColumnModel().getColumn(2).setResizable(false);
		table_ETH.getColumnModel().getColumn(3).setResizable(false);
		table_ETH.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_ETH.getColumnModel().getColumn(2).setPreferredWidth(70);
		table_ETH.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_ETH.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_ETH.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_ETH.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_ETH.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer_CoinTable());
		
		table_ETH.setShowVerticalLines(false);
		table_ETH.setRowHeight(40);
		table_ETH.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		table_ETH.getTableHeader().setReorderingAllowed(false);
		table_ETH.addMouseListener(new TableListener_Coin(table_ETH));
		scrollPane_ETH.setViewportView(table_ETH);
		
		JPanel panel_MyCoin = new JPanel();
		tabbedPane_CoinList.addTab("º¸À¯ÄÚÀÎ", null, panel_MyCoin, "³ªÀÇ º¸À¯ ÄÚÀÎ");
		panel_MyCoin.setLayout(null);
		
		textField_searchMyCoin = new JTextField();
		textField_searchMyCoin.setColumns(10);
		textField_searchMyCoin.setBounds(0, 0, 227, 25);
		panel_MyCoin.add(textField_searchMyCoin);
		
		JButton button_searchMyCoin = new JButton("°Ë»ö");
		button_searchMyCoin.setBounds(226, 0, 65, 25);
		panel_MyCoin.add(button_searchMyCoin);
		
		JScrollPane scrollPane_MyCoin = new JScrollPane();
		scrollPane_MyCoin.setBounds(0, 27, 291, 700);
		scrollPane_MyCoin.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		panel_MyCoin.add(scrollPane_MyCoin);
		
		table_MyCoin = new JTable();
		table_MyCoin.addMouseListener(new TableListener_MyCoin());
		table_MyCoin.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		table_MyCoin.setShowVerticalLines(false);
		table_MyCoin.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\uC774\uB984",
				"\uBCF4\uC720(\uD3C9\uAC00\uAE08)", "\uB9E4\uC218\uD3C9\uADE0\uAC00", "\uC218\uC775\uB960" })
		{
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, String.class };

			public Class getColumnClass(int columnIndex)
			{
				return columnTypes[columnIndex];
			}
		});
		for (int i = 0; i < 4; i++)
		{
			table_MyCoin.getColumnModel().getColumn(i).setResizable(false);
			table_MyCoin.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer_MyCoinTable());
		}
		table_MyCoin.getColumnModel().getColumn(0).setPreferredWidth(60);
		table_MyCoin.getColumnModel().getColumn(1).setPreferredWidth(80);
		table_MyCoin.getColumnModel().getColumn(2).setPreferredWidth(70);
		table_MyCoin.setRowHeight(40);
		table_KRW.getTableHeader().setReorderingAllowed(false);
		scrollPane_MyCoin.setViewportView(table_MyCoin);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1434, 21);
		contentPane.add(menuBar);
		
		JMenu mnMenu = new JMenu("Menu1");
		menuBar.add(mnMenu);
		
		JMenu mnMenu_1 = new JMenu("Menu2");
		menuBar.add(mnMenu_1);
		
		JMenu mnMenu_2 = new JMenu("Menu3");
		menuBar.add(mnMenu_2);


		coinTableElements = new ArrayList<CoinTableElement>();
		myCoinTableElements = new ArrayList<MyCoinTableElement>();
		orderTableElements = new ArrayList<OrderTableElement>();
		tradeHistoryTableElements = new ArrayList<TradeHistoryTableElement>();
		tradeHistoryTableElements_Complete = new ArrayList<TradeHistoryTableElement>();
		selectedRow = 0;
	}
	
	public void initiate()
	{
		setCurrentMarket(Market.KRW);
		setCurrentCoinSymbol(CoinSymbol.BTC);
		setCurrentTermType(TermType.minutes);
		setCurrentTerm(1);

		clearBuySell();
		updateCoinTable();
		
		updateInfo();
	}
	
	public CoinTableElement getCoinTableElement(Market market, CoinSymbol coinSymbol)
	{
		for (CoinTableElement element : coinTableElements)
		{
			if (element.getMarket() == market && element.getCoinSymbol() == coinSymbol)
				return element;
		}

		CoinTableElement coinTableElement = 
				new CoinTableElement(market, coinSymbol, CoinList.getCoinNameKR(coinSymbol).toString(), 0, 0, 0);
		addCoinTableElement(coinTableElement);
		
		return coinTableElement;
	}
	
	public void addCoinTableElement(CoinTableElement element)
	{
		for (CoinTableElement coinTableElement : coinTableElements)
		{
			if (coinTableElement.getMarket() == element.getMarket() && coinTableElement.getName().equals(element.getName()))
			{
				coinTableElement = element;
				return;
			}
		}

		coinTableElements.add(element);
	}
	
	public void updateCoinTable()
	{
		ArrayList<CryptoCurrency> list = upbit.getCryptoList();
		ArrayList<CoinSymbol> coinSymbolList;
		CryptoCurrency cryptoCurrency;
		DefaultTableModel model;
		CoinTableElement element;
		Market market = getCurrentMarket();

		double volume;
		double signedChangeRate;

		switch (market)
		{
		case KRW:
			model = (DefaultTableModel) table_KRW.getModel();
			coinSymbolList = CoinList.listKRW;
			break;		
			
		case BTC:
			model = (DefaultTableModel) table_BTC.getModel();
			coinSymbolList = CoinList.listBTC;
			break;
			
		case ETH:
			model = (DefaultTableModel) table_ETH.getModel();
			coinSymbolList = CoinList.listETH;
			break;
			
		default:
			System.out.println("updateCoinTable(), Wrong Market input");
			return;
		}
		
		for (CoinSymbol coinSymbol : coinSymbolList)
		{
			element = getCoinTableElement(market, coinSymbol);
			
			try
			{
				cryptoCurrency = upbit.getCryptoCurrency(upbit.createName(market, coinSymbol, TermType.minutes, 1));
				element.setTradePrice(Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice)));
			}
			catch (Exception e)
			{
				continue;
			}
			
			try
			{
				cryptoCurrency = upbit.getCryptoCurrency(upbit.createName(market, coinSymbol, TermType.days, 1));
				element.setChangeRate(Double.parseDouble(cryptoCurrency.getData(JsonKey.signedChangeRate)));
			}
			catch (Exception e)
			{
				continue;
			}
			
			element.setVolume(upbit.getVolumePrice(market, coinSymbol, 24));
		}
		
//		for (CryptoCurrency cryptoCurrency : list)
//		{
//			if (cryptoCurrency.getMarket() != market)
//				continue;
//			
//			element = getCoinTableElement(cryptoCurrency.getMarket(), cryptoCurrency.getCoinSymbol());
//			
//			if (cryptoCurrency.getName().equals(upbit.createName(market, cryptoCurrency.getCoinSymbol(), TermType.minutes, 60)))
//			{
//				volume = 0;
//				
//				for (int index = 0; index < 24; index++)
//				{
//					volume += Double.parseDouble(cryptoCurrency.getData(JsonKey.candleAccTradePrice, index));
//				}
//				
//				element.setTradePrice(Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice)));
//				element.setVolume(volume);
//				
//				continue;
//			}
//			
//			if (cryptoCurrency.getName().equals(upbit.createName(market, cryptoCurrency.getCoinSymbol(), TermType.days, 1)))
//			{
//				element.setChangeRate(Double.parseDouble(cryptoCurrency.getData(JsonKey.signedChangeRate)));
//				
//				continue;
//			}
//		}
		
		try
		{
			for (int index = 0; index < coinTableElements.size(); index++)
			{
				model.setValueAt(coinTableElements.get(index).getData()[0], index, 0);
				model.setValueAt(coinTableElements.get(index).getData()[1], index, 1);
				model.setValueAt(coinTableElements.get(index).getData()[2], index, 2);
				model.setValueAt(coinTableElements.get(index).getData()[3], index, 3);
			}
		}
		catch (Exception e)
		{
			model.setNumRows(0);
			for (CoinTableElement coinTableElement : coinTableElements)
			{
				model.addRow(coinTableElement.getData());
			}
		}
		
		sort(3);
	}
	

	
	public OrderTableElement getOrderTableElement(int index)
	{
		try
		{
			return orderTableElements.get(index);
		}
		catch (Exception e)
		{
			OrderTableElement element = new OrderTableElement(0, 0, 0, 0);
			addOrderTableElement(element, index);
			
			return element; 
		}
	}
	
	public void addOrderTableElement(OrderTableElement element, int index)
	{
		try
		{
			orderTableElements.add(index, element);
		}
		catch (Exception e)
		{
			orderTableElements.add(element);
		}
	}
	
	public boolean updateOrderTable(Market market, CoinSymbol coinSymbol)
	{
		ArrayList<OrderBookElement> list;
		OrderBookElement orderBookElement;
		OrderBook orderBook;
		OrderTableElement orderTableElement;
		DefaultTableModel model = (DefaultTableModel) table_OrderBook.getModel();

		try
		{
			orderBook = getUpbit().getOrderBook(market, coinSymbol);
			list = orderBook.getOrderBookElementList();
		}
		catch (Exception e1)
		{
			return false;
		}
		
		for (int index = 0; index < list.size(); index++)
		{
			orderBookElement = orderBook.getOrderBookElement(index);
			orderTableElement = getOrderTableElement(index);
			orderTableElement.setPrice(orderBookElement.getPrice());
			orderTableElement.setChangeRate(orderBookElement.getPercentage());
			orderTableElement.setQuantity(orderBookElement.getQuantity());
			orderTableElement.setMyOrder(0);
		}

		try
		{
			for (int index = 0; index < orderTableElements.size(); index++)
			{
				model.setValueAt(orderTableElements.get(index).getData()[0], index, 0);
				model.setValueAt(orderTableElements.get(index).getData()[1], index, 1);
				model.setValueAt(orderTableElements.get(index).getData()[2], index, 2);
				model.setValueAt(orderTableElements.get(index).getData()[3], index, 3);
			}
		}
		catch (Exception e)
		{
			model.setNumRows(0);
			
			for (OrderTableElement element : orderTableElements)
			{
				model.addRow(element.getData());
			}
		}
		
		return true;
	}
	
	public void updateBuySell(boolean byRatio)
	{
		Account account = upbit.getAccount();
		Market market = getCurrentMarket();
		CoinSymbol coinSymbol = getCurrentCoinSymbol();
		double balance, price, quantity, total, ratio;
		
		
		// Buy
		switch (market)
		{
		case KRW:
			balance = account.getKRW();
			break;
			
		case BTC:
			balance = account.getBalance(CoinSymbol.BTC);
			break;
			
		case ETH:
			balance = account.getBalance(CoinSymbol.ETH);
			break;
		
		default:
			balance = 0;
		}
		
		price = getBuyPrice();
		
		if (byRatio)
		{
			ratio = getBuySellRatio();
			
			if (balance == 0 || price == 0)
			{
				quantity = 0;
				total = 0;
			}
			else
			{
				quantity = balance * ratio / price;
				total = market == Market.KRW ? Math.round(quantity * price) : Math.round(quantity * price * 100000000d) / 100000000d;
			}
		}
		else
		{
			quantity = getBuyQuantity();
			
			total = market == Market.KRW ? Math.round(quantity * price) : Math.round(quantity * price * 100000000d) / 100000000d;
		}
		
		setBuyBalance(balance);
		setBuyQuantity(quantity);
		setBuyTotal(total);
				
		
		// Sell
		balance = account.getBalance(coinSymbol);
		price = getSellPrice();
		
		if (byRatio)
		{
			ratio = getBuySellRatio();
			
			if (balance == 0 || price == 0)
			{
				quantity = 0;
				total = 0;
			}
			else
			{
				quantity = balance * ratio;
				total = market == Market.KRW ? Math.round(quantity * price) : Math.round(quantity * price * 1000000000d / 100000000d);
			}
		}
		else
		{
			quantity = getSellQuantity();

			total = market == Market.KRW ? Math.round(quantity * price) : Math.round(quantity * price * 1000000000d / 100000000d);
		}
		
		setSellBalance(balance);
		setSellQuantity(quantity);
		setSellTotal(total);
		
		
		// GUI ¾÷µ¥ÀÌÆ®
		if (market == Market.KRW)
			decimalFormat = new DecimalFormat("#,###.###");
		else
			decimalFormat = new DecimalFormat("#,###.########");
		
		label_BuyBalance.setText(decimalFormat.format(getBuyBalance()));
		spinner_BuyPrice.setValue(getBuyPrice());
		textField_BuyQuantity.setText(decimalFormat.format(getBuyQuantity()));
		label_BuyTotal.setText(decimalFormat.format(getBuyTotal()));

		label_SellBalance.setText(decimalFormat.format(getSellBalance()));
		spinner_SellPrice.setValue(getSellPrice());
		textField_SellQuantity.setText(decimalFormat.format(getSellQuantity()));
		label_SellTotal.setText(decimalFormat.format(getSellTotal()));
		

		label_BuyBalanceSymbol.setText(market.toString());
		label_BuyPriceSymbol.setText(market.toString());
		label_BuyQuantitySymbol.setText(coinSymbol.toString());
		label_BuyTotalSymbol.setText(market.toString());
		
		label_SellBalanceSymbol.setText(coinSymbol.toString());
		label_SellPriceSymbol.setText(market.toString());
		label_SellQuantitySymbol.setText(coinSymbol.toString());
		label_SellTotalSymbol.setText(market.toString());
	}
	
	public void clearBuySell()
	{
		setBuyBalance(0);
		setBuyPrice(0);
		setBuyQuantity(0);
		setBuyTotal(0);
		setSellBalance(0);
		setSellPrice(0);
		setSellQuantity(0);
		setSellTotal(0);
		setBuySellRatio(1);

		updateBuySell(true);
	}
	
	public void updateInfo()
	{
		CryptoCurrency cryptoCurrency;
		Market market = getCurrentMarket();
		CoinSymbol coinSymbol = getCurrentCoinSymbol();
		
		try
		{
			cryptoCurrency = upbit.requestData(market, coinSymbol, TermType.days, 1, 1);
		}
		catch (Exception e)
		{
			try
			{
				cryptoCurrency = getUpbit().getCryptoCurrency(getUpbit().createName(market, coinSymbol, TermType.days, 1));
			}
			catch (Exception e1)
			{
				return;
			}
		}

		double changeRate = Double.parseDouble(cryptoCurrency.getData(JsonKey.signedChangeRate));
		double changePrice = Double.parseDouble(cryptoCurrency.getData(JsonKey.signedChangePrice));
		
		decimalFormat = new DecimalFormat("#,###.##");

		label_InfoName.setText(cryptoCurrency.getNameKR());
		label_InfoSymbol.setText(market + "/" + coinSymbol);
		label_InfoTradePrice.setText(decimalFormat.format(Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice))) + " " + market);
		label_InfoHighPrice.setText(decimalFormat.format(Double.parseDouble(cryptoCurrency.getData(JsonKey.highPrice))));
		label_InfoLowPrice.setText(decimalFormat.format(Double.parseDouble(cryptoCurrency.getData(JsonKey.lowPrice))));
		label_InfoChangeRate.setText((changeRate > 0 ? "+" : "") + decimalFormat.format(changeRate * 100) + "%");
		label_InfoChangePrice.setText((changePrice > 0 ? "+" : "") + decimalFormat.format(changePrice));
		label_InfoVolume.setText(decimalFormat.format(Math.round(upbit.getVolumePrice(market, coinSymbol, 24))) + market);

		if (cryptoCurrency.getData(JsonKey.change).equals("RISE"))
		{
			label_InfoTradePrice.setForeground(Color.RED);
			label_InfoChangeRate.setForeground(Color.RED);
			label_InfoChangePrice.setForeground(Color.RED);
		}
		else if (cryptoCurrency.getData(JsonKey.change).equals("FALL"))
		{
			label_InfoTradePrice.setForeground(Color.BLUE);
			label_InfoChangeRate.setForeground(Color.BLUE);
			label_InfoChangePrice.setForeground(Color.BLUE);
		}
		else
		{
			label_InfoTradePrice.setForeground(Color.BLACK);
			label_InfoChangeRate.setForeground(Color.BLACK);
			label_InfoChangePrice.setForeground(Color.BLACK);
		}
		
		icon = new ImageIcon(".\\resource\\" + coinSymbol.toString() + ".png");
		label_InfoIcon.setIcon(icon);
	}
	
	public void swap(int a, int b)
	   {
	      CoinTableElement temp = coinTableElements.get(a);
	      
	      coinTableElements.set(a, coinTableElements.get(b));
	      coinTableElements.set(b, temp);
	   }
	
	
	public void sort(int column)
	{
		switch (column)
		{
		case 1:
			for (int i = 1; i < coinTableElements.size(); i++)
			{
				double kappa = coinTableElements.get(i).getTradePrice();

				int j = i;
				while ((0 < j) && (kappa > coinTableElements.get(j - 1).getTradePrice()))
				{
					swap(j, j - 1);
					j--;
				}
			}

			break;
		case 2:

			for (int i = 1; i < coinTableElements.size(); i++)
			{
				double kappa = coinTableElements.get(i).getChangeRate();

				int j = i;
				while ((0 < j) && (kappa > coinTableElements.get(j - 1).getChangeRate()))
				{
					swap(j, j - 1);
					j--;
				}
			}

			break;

		case 3:

			for (int i = 1; i < coinTableElements.size(); i++)
			{
				double kappa = coinTableElements.get(i).getVolume();

				int j = i;
				while ((0 < j) && (kappa > coinTableElements.get(j - 1).getVolume()))
				{
					swap(j, j - 1);
					j--;
				}
			}

			break;
		}
		double endTime = System.currentTimeMillis();
	}
	
	public DefaultHighLowDataset createCandleStickDataset(CryptoCurrency cryptoCurrency, int start, int end)
	{
		int startIndex, endIndex;
		int cryptoEndIndex = cryptoCurrency.getSize() - 1;
		int requestSize = end - start + 1;

		if (cryptoCurrency.getSize() >= requestSize)
		{
			if (cryptoEndIndex >= end)
			{
				startIndex = start;
				endIndex = end;
			}
			else
			{
				startIndex = cryptoCurrency.getSize() - requestSize;
				endIndex = cryptoEndIndex;
			}
		}
		else
		{
			startIndex = 0;
			endIndex = cryptoCurrency.getSize() - 1;
			
			requestSize = cryptoCurrency.getSize();
		}
		
		
		Date[] date = new Date[requestSize];
		double[] high = new double[requestSize];
		double[] low = new double[requestSize];
		double[] open = new double[requestSize];
		double[] close = new double[requestSize];
		double[] volume = new double[requestSize];
		
		for (int index = 0; startIndex <= endIndex; index++, startIndex++)
		{
			date[index] = cryptoCurrency.getDate(startIndex);
			high[index]  = Double.parseDouble(cryptoCurrency.getData(JsonKey.highPrice, startIndex));
	        low[index]   = Double.parseDouble(cryptoCurrency.getData(JsonKey.lowPrice, startIndex));
	        open[index]  = Double.parseDouble(cryptoCurrency.getData(JsonKey.openingPrice, startIndex));
	        close[index] = Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice, startIndex));
	        volume[index] = Double.parseDouble(cryptoCurrency.getData(JsonKey.candleAccTradeVolume, startIndex));
		}
		
		return new DefaultHighLowDataset(cryptoCurrency.getNameKR(), date, high, low, open, close, volume);
	}
	
	public JFreeChart createChart(DefaultHighLowDataset dataset)
	{
		JFreeChart chart = ChartFactory.createCandlestickChart("", "", "", dataset, false);
		
		return chart;
	}
	
	public boolean updateChart(Market market, CoinSymbol coinSymbol, TermType termType, int term, int startIndex, int endIndex)
	{
		CryptoCurrency cryptoCurrency;
		DefaultHighLowDataset dataset;
		
		try
		{
			cryptoCurrency = upbit.getCryptoCurrency(upbit.createName(market, coinSymbol, termType, term));
		}
		catch (Exception e)
		{
			try
			{
				cryptoCurrency = upbit.requestData(market, coinSymbol, termType, term, endIndex - startIndex + 1);
				upbit.addCryptoCurrency(cryptoCurrency);
			}
			catch (Exception e1)
			{
				return false;
			}
		}
		
		dataset = createCandleStickDataset(cryptoCurrency, startIndex, endIndex);
		chart.getXYPlot().setDataset(dataset); 
		
		double[] range = cryptoCurrency.getMinMaxPrice(startIndex, endIndex);
		chart.getXYPlot().getRangeAxis().setRange(range[0], range[1]);

		((NumberAxis) chart.getXYPlot().getRangeAxis()).setTickUnit(createTickUnit((range)[1] - range[0]));
		
		return true;
	}
	
	public NumberTickUnit createTickUnit(double range)
	{
		double key, count;
		key = 0.00000001;
		count = range / key;
				
		while(count > 6)
		{
			if (count <= 6)
				break;
			
			key *= 2.5;
			count = range / key;
			
			if (count <= 6)
				break;
			
			key *= 2;
			count = range / key;
			
			if (count <= 6)
				break;
			
			key *= 2;
			count = range / key;
		}
		
		return new NumberTickUnit(key);
	}
	
	public double spinnerValueControl(double input, boolean increase, Market market)
	{
		if (increase)
		{
			if (input < 10)
				return input += 0.01;
			else if (input < 100)
				return input += 0.1;
			else if (input < 1000)
				return input += 1;
			else if (input < 10000)
				return input += 5;
			else if (input < 100000)
				return input += 10;
			else if (input < 500000)
				return input += 50;
			else if (input < 1000000)
				return input += 100;
			else if (input < 2000000)
				return input += 500;
			else
				return input += 1000;
		}
		else
		{
			if (input <= 10)
				return input -= 0.01;
			else if (input <= 100)
				return input -= 0.1;
			else if (input <= 1000)
				return input -= 1;
			else if (input <= 10000)
				return input -= 5;
			else if (input <= 100000)
				return input -= 10;
			else if (input <= 500000)
				return input -= 50;
			else if (input <= 1000000)
				return input -= 100;
			else if (input <= 2000000)
				return input -= 500;
			else
				return input -= 1000;
		}
	}
	
	public double spinnerFixValue(double input)
	{
		double kappa;
		
		if (input < 10)
			kappa = Math.floor(input * 100) / 100;
		else if (input < 100)
			kappa = Math.floor(input * 10) / 10;
		else if (input < 1000)
			kappa = Math.floor(input);
		else if (input < 10000)
			kappa = Math.floor(input / 10) * 10;
		else if (input < 100000)
			kappa = Math.floor(input / 100) * 100;
		else if (input < 500000)
			kappa = Math.floor(input / 100) * 100;
		else if (input < 2000000)
			kappa = Math.floor(input / 1000) * 1000;
		else
			kappa = Math.floor(input / 10000) * 10000;
		
		while (input > kappa)
		{
			kappa = spinnerValueControl(kappa, true, getCurrentMarket());
			
			if (input < kappa)
				break;
		}
		
		return spinnerValueControl(kappa, false, getCurrentMarket());
	}
	
	public void updateTradeHistoryTable()
	{
		tradeHistoryTableElements = new ArrayList<TradeHistoryTableElement>();
		tradeHistoryTableElements_Complete = new ArrayList<TradeHistoryTableElement>();
		
		for (Order order : upbit.getAccount().getOrderList())
		{
			if (order.isConclusion())
			{
				tradeHistoryTableElements_Complete.add(new TradeHistoryTableElement(order));
			}
			else
			{
				tradeHistoryTableElements.add(new TradeHistoryTableElement(order));
			}
		}
		
		DefaultTableModel model = (DefaultTableModel) table_TradeHistoryComplete.getModel();
		
		model.setNumRows(0);
		
		for (TradeHistoryTableElement element : tradeHistoryTableElements_Complete)
		{
			model.addRow(element.getData());
		}
		
		model = (DefaultTableModel) table_TradeHistoryNotComplete.getModel();
		
		model.setNumRows(0);
		
		for (TradeHistoryTableElement element : tradeHistoryTableElements)
		{
			model.addRow(element.getData());
		}
	}
	
	public void updateMyCoinTable()
	{
		myCoinTableElements = new ArrayList<MyCoinTableElement>();
		CryptoCurrency cryptoCurrency;
		double tradePrice = 0;
		
		for (Wallet wallet : upbit.getAccount().getWalletList())
		{
			try
			{
				cryptoCurrency = upbit.getCryptoCurrency(upbit.createName(Market.KRW, wallet.getCoinSymbol(), TermType.minutes, 1));
				tradePrice = Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice));
			} 
			catch (Exception e)
			{
			}
			
			myCoinTableElements.add(new MyCoinTableElement(
					wallet.getCoinSymbol(),
					CoinList.getCoinNameKR(wallet.getCoinSymbol()).toString(),
					tradePrice * wallet.getBalance(),
					wallet.getAveragePrice(), 
					(((tradePrice * wallet.getBalance()) - (wallet.getAveragePrice() * wallet.getBalance())) / (wallet.getAveragePrice() * wallet.getBalance())) * 100,
					(tradePrice * wallet.getBalance()) - (wallet.getAveragePrice() * wallet.getBalance())));
		}
		
		DefaultTableModel model = (DefaultTableModel) table_MyCoin.getModel();
		
		model.setNumRows(0);
		
		for (MyCoinTableElement element : myCoinTableElements)
		{
			model.addRow(element.getData());
		}
	}
	
	public void showOrderBook(boolean show)
	{
		if (show)
			panel_LoadingOrderBook.setVisible(false);
		else
			panel_LoadingOrderBook.setVisible(true);
	}
	
	public void updateAsset()
	{	
		upbit.updateStat();

		Stat stat = upbit.getAccount().getStat();
		
		decimalFormat = new DecimalFormat("#,##0");
		label_AssetValue.setText(decimalFormat.format(stat.getAssetValue()) + " KRW");
		
		label_Seed.setText(decimalFormat.format(stat.getSeed()));

		label_Earnings.setText(stat.getEarnings() > 0 ? "+" + decimalFormat.format(stat.getEarnings()) : decimalFormat.format(stat.getEarnings()));

		decimalFormat = new DecimalFormat("#,##0.00");
		label_EarningsRate.setText(stat.getEarningsRate() > 0 ? "+" + decimalFormat.format(stat.getEarningsRate()) + "%" : decimalFormat.format(stat.getEarningsRate()) + "%");
		
		Color color;
		
		if (stat.getEarnings() > 0)
			color = Color.RED;
		else if (stat.getEarnings() < 0)
			color = Color.BLUE;
		else
			color = Color.BLACK;

		label_Earnings.setForeground(color);
		label_EarningsRate.setForeground(color);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	class Button_BuySell implements ActionListener
	{
		private boolean buy;
		
		public Button_BuySell(boolean buy)
		{
			this.setBuy(buy);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			double tradePrice, quantity; 
			
			if (buy)
			{
				if (getBuyBalance() < getBuyTotal() || getBuyBalance() == 0)
				{
					// ÀÜ°í ºÎÁ·
					return;
				}

				tradePrice = getBuyPrice();
				quantity = getBuyQuantity();
			}
			else
			{
				if (getSellBalance() < getSellQuantity() || getSellBalance() == 0)
				{
					// ÀÜ°í ºÎÁ·
					return;
				}
				
				tradePrice = getSellPrice();
				quantity = getSellQuantity();
			}

			upbit.createOrder(getCurrentMarket(), getCurrentCoinSymbol(), tradePrice, quantity, buy);
			updateTradeHistoryTable();
		}
		
		public boolean getBuy()
		{
			return buy;
		}

		public void setBuy(boolean buy)
		{
			this.buy = buy;
		}
	}
	
	class TableListener_Coin implements MouseListener
	{
		private JTable jTable;
		
		public TableListener_Coin(JTable jTable)
		{
			this.jTable = jTable;
		}
		
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e)
		{			
			selectedRow = jTable.getSelectedRow();
			CoinTableElement element = coinTableElements.get(selectedRow);
			
			double tradePrice = element.getTradePrice();

			setCurrentMarket(element.getMarket());
			setCurrentCoinSymbol(element.getCoinSymbol());
			
			setBuyPrice(tradePrice);
			setSellPrice(tradePrice);

			updateBuySell(true);

			updateInfo();
			updateChart(getCurrentMarket(), getCurrentCoinSymbol(), getCurrentTermType(), getCurrentTerm(), 0, 99);
			
			crawler.setReady(false);
			showOrderBook(false);
			getExecutorService().submit(() -> upbit.updateOrderBook(getCurrentMarket(), getCurrentCoinSymbol()));
		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e)
		{
		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e)
		{
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e)
		{
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e)
		{
		}
	}
	
	class TableListener_MyCoin implements MouseListener
	{
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e)
		{
			int select = table_MyCoin.getSelectedRow();
			
			MyCoinTableElement element = myCoinTableElements.get(select);
			CoinTableElement coinTableElement = null;
			
			setCurrentCoinSymbol(element.getCoinSymbol());
			
			for (CoinTableElement coinTableElement2 : coinTableElements)
			{
				if (coinTableElement2.getCoinSymbol() == element.getCoinSymbol())
				{
					coinTableElement = coinTableElement2;
					break;
				}
			}
			
			double tradePrice = coinTableElement.getTradePrice();

			setBuyPrice(tradePrice);
			setSellPrice(tradePrice);

			updateBuySell(true);
			
			updateInfo();
			updateChart(getCurrentMarket(), getCurrentCoinSymbol(), getCurrentTermType(), getCurrentTerm(), 0, 99);
			
			crawler.setReady(false);
			showOrderBook(false);
			getExecutorService().submit(() -> upbit.updateOrderBook(getCurrentMarket(), getCurrentCoinSymbol()));
		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e)
		{
		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e)
		{	
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e)
		{	
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e)
		{
		}
	}
	
	class TableListener_OrderBook implements MouseListener
	{

		@Override
		public void mouseClicked(java.awt.event.MouseEvent e)
		{
			int select = table_OrderBook.getSelectedRow();
			OrderTableElement element = orderTableElements.get(select);
			
			double tradePrice = element.getPrice();
			
			setBuyPrice(tradePrice);
			setSellPrice(tradePrice);
			
			updateBuySell(false);
		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e)
		{
			// TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e)
		{
			// TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e)
		{
			// TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e)
		{
			// TODO ÀÚµ¿ »ý¼ºµÈ ¸Þ¼Òµå ½ºÅÓ

		}

	}
	
	class TableListener_TradeHistory implements MouseListener
	{

		@Override
		public void mouseClicked(java.awt.event.MouseEvent e)
		{
			JTable table = (JTable) e.getSource();
			
			if (table.getSelectedColumn() == 7)
			{
				int select = table.getSelectedRow();
				
				TradeHistoryTableElement element = tradeHistoryTableElements.get(select);
				
				upbit.cancelOrder(element.getOrder().getId());
				updateTradeHistoryTable();
			}
		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e)
		{
		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e)
		{
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e)
		{
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e)
		{
		}	
	}

	class TabListener implements ChangeListener
	{
		private JTabbedPane tab;
		
		public TabListener(JTabbedPane tab)
		{
			this.tab = tab;
		}
		
		@Override
		public void stateChanged(ChangeEvent e)
		{
			switch (tab.getSelectedIndex())
			{
			case 0:
				setCurrentMarket(Market.KRW);
				setSelection(table_KRW, 0, 0);
				break;
				
			case 1:
//				setCurrentMarket(Market.BTC);
//				setSelection(table_BTC, 0, 0);
				break;
				
			case 2:
//				setCurrentMarket(Market.ETH);
//				setSelection(table_ETH, 0, 0);
				break;
				
			case 3:
				setCurrentMarket(Market.KRW);
//				setSelection(table_MyCoin, 0, 0);
				break;
			}
		}	
		
		private void setSelection(JTable table, int row, int column)
		{
			try
			{
				table.setRowSelectionInterval(row, column);
			}
			catch (Exception e)
			{

			}
		}
	}
	
	class ComboListener_BuySell implements ActionListener
	{
		private boolean buy;
		
		/**
		 * @param buy TRUE: ¸Å¼ö, FALSE: ¸Åµµ
		 * 
		 */
		public ComboListener_BuySell(boolean buy)
		{
			this.buy = buy;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JComboBox<String> comboBox;
			
			if (buy)
				comboBox = comboBox_BuyQuantity;
			else
				comboBox = comboBox_SellQuantity;
			
			// 0: 100%, 1: 75%, 2: 50%, 3: 25%
			switch (comboBox.getSelectedIndex())
			{
			case 0:
				setBuySellRatio(1);
				break;
				
			case 1:
				setBuySellRatio(0.75);
				break;
				
			case 2:
				setBuySellRatio(0.5);
				break;
				
			case 3:
				setBuySellRatio(0.25);
				break;
			}
			
			updateBuySell(true);
		}
	}
	
	class ComboListener_Chart implements ActionListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
			
			switch (comboBox.getSelectedIndex())
			{
			case 0:
				setCurrentTermType(TermType.minutes);
				setCurrentTerm(1);
				break;
			case 1:
				setCurrentTermType(TermType.minutes);
				setCurrentTerm(3);
				break;
			case 2:
				setCurrentTermType(TermType.minutes);
				setCurrentTerm(5);
				break;
			case 3:
				setCurrentTermType(TermType.minutes);
				setCurrentTerm(10);
				break;
			case 4:
				setCurrentTermType(TermType.minutes);
				setCurrentTerm(15);
				break;
			case 5:
				setCurrentTermType(TermType.minutes);
				setCurrentTerm(30);
				break;
			case 6:
				setCurrentTermType(TermType.minutes);
				setCurrentTerm(60);
				break;
			case 7:
				setCurrentTermType(TermType.minutes);
				setCurrentTerm(240);
				break;
			case 8:
				setCurrentTermType(TermType.days);
				setCurrentTerm(1);
				break;
			case 9:
				setCurrentTermType(TermType.weeks);
				setCurrentTerm(1);
				break;
			case 10:
				setCurrentTermType(TermType.months);
				setCurrentTerm(1);
				break;
			}
			
			updateChart(getCurrentMarket(), getCurrentCoinSymbol(), getCurrentTermType(), getCurrentTerm(), 0, 99);
		}
	}
	

	// Getter, Setter
	public Upbit getUpbit()
	{
		return upbit;
	}
	
	public void setUpbit(Upbit upbit)
	{
		this.upbit = upbit;
	}

	public double getBuyPrice()
	{
		return buyPrice;
	}
	
	public void setBuyPrice(double buyPrice)
	{
		if (buyPrice < 0)
			buyPrice = 0;
		
		this.buyPrice = buyPrice;
	}

	public double getBuyQuantity()
	{
		return buyQuantity;
	}

	public void setBuyQuantity(double buyQuantity)
	{
		if (buyQuantity < 0)
			buyQuantity = 0;
		
		this.buyQuantity = Math.round(buyQuantity * 100000000d) / 100000000d;
	}

	public double getBuyTotal()
	{
		return buyTotal;
	}

	public void setBuyTotal(double buyTotal)
	{
		if (buyTotal < 0)
			buyTotal = 0;
		
		this.buyTotal = buyTotal;
	}

	public double getSellPrice()
	{
		return sellPrice;
	}

	public void setSellPrice(double sellPrice)
	{
		if (sellPrice < 0)
			sellPrice = 0;
		
		this.sellPrice = sellPrice;
	}

	public double getSellQuantity()
	{
		return sellQuantity;
	}

	public void setSellQuantity(double sellQuantity)
	{
		if (sellQuantity < 0)
			sellQuantity = 0;
		
		this.sellQuantity = sellQuantity;
	}

	public double getSellTotal()
	{
		return sellTotal;
	}

	public void setSellTotal(double sellTotal)
	{
		if (sellTotal < 0)
			sellTotal = 0;
		
		this.sellTotal = sellTotal;
	}

	public double getBuyBalance()
	{
		return buyBalance;
	}

	public void setBuyBalance(double buyBalance)
	{
		if (buyBalance < 0)
			buyBalance = 0;
		
		this.buyBalance = buyBalance;
	}

	public double getSellBalance()
	{
		return sellBalance;
	}

	public void setSellBalance(double sellBalance)
	{
		if (sellBalance < 0)
			sellBalance = 0;
		
		this.sellBalance = sellBalance;
	}
	
	public double getBuySellRatio()
	{
		return buySellRatio; 
	}
	
	public void setBuySellRatio(double ratio)
	{
		this.buySellRatio = ratio;
	}

	public ExecutorService getExecutorService()
	{
		return executorService;
	}

	public void setExecutorService(ExecutorService executorService)
	{
		this.executorService = executorService;
	}

	public DynamicCrawler getCrawler()
	{
		return crawler;
	}

	public void setCrawler(DynamicCrawler crawler)
	{
		this.crawler = crawler;
	}

	public CoinSymbol getCurrentCoinSymbol()
	{
		return currentCoinSymbol;
	}

	public void setCurrentCoinSymbol(CoinSymbol currentCoinSymbol)
	{
		this.currentCoinSymbol = currentCoinSymbol;
	}

	public Market getCurrentMarket()
	{
		return currentMarket;
	}

	public void setCurrentMarket(Market currentMarket)
	{
		this.currentMarket = currentMarket;
	}

	public JPanel getPanel_Chart()
	{
		return panel_Chart;
	}

	public void setPanel_Chart(JPanel panel_Chart)
	{
		this.panel_Chart = panel_Chart;
	}

	public TermType getCurrentTermType()
	{
		return currentTermType;
	}

	public void setCurrentTermType(TermType currentTermType)
	{
		this.currentTermType = currentTermType;
	}

	public int getCurrentTerm()
	{
		return currentTerm;
	}

	public void setCurrentTerm(int currentTerm)
	{
		this.currentTerm = currentTerm;
	}
}
