package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;

import upbit.CoinList;
import upbit.CoinList.CoinSymbol;
import upbit.CoinList.Market;
import upbit.CryptoCurrency;
import upbit.DynamicCrawler;
import upbit.OrderBook;
import upbit.JsonManager.JsonKey;
import upbit.Order;
import upbit.Request.TermType;
import upbit.Upbit;

import javax.swing.JScrollPane;
import javax.swing.JLayeredPane;
import javax.swing.Box;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import java.awt.Button;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.JList;
import javax.swing.JDesktopPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.font.NumericShaper;
import java.math.RoundingMode;
import java.security.CryptoPrimitive;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JFormattedTextField;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.Component;
import javax.swing.border.CompoundBorder;

public class GUI extends JFrame
{
	private Upbit upbit;
	private DynamicCrawler crawler;
	private ExecutorService executorService;
	
	private ArrayList<CoinTableElement> coinTableElements;
	private ArrayList<OrderTableElement> orderTableElements;
	
	private int selectedRow;
	private Market currentMarket;
	
	private double buyPrice;
	private double buyQuantity;
	private double buyTotal;
	private double sellPrice;
	private double sellQuantity;
	private double sellTotal;
	private double buySellRatio;
	
	DecimalFormat decimalFormat;

	private JPanel contentPane;
	
	private JTextField textField_BuyQuantity;
	private JTextField textField_BuyTotal;
	private JTextField textField_SellQuantity;
	private JTextField textField_SellTotal;
	private JTextField textField_SearchKRW;
	private JTextField textField_searchMyCoin;
	private JTextField textField_SearchBTC;
	private JTextField textField_SearchETH;

	private JSpinner spinner_SellPrice;
	private JSpinner spinner_BuyPrice;
	
	private JTable table_KRW;
	private JTable table_BTC;
	private JTable table_ETH;
	private JTable table_MyCoin;
	private JTable table_OrderBook;
	private JTable table_TradeHistoryNotComplete;
	private JTable table_TradeHistoryComplete;
	
	private JLabel label_BuyPriceSymbol;
	private JLabel label_BuyQuantitySymbol;
	private JLabel label_BuyTotalSymbol;
	private JLabel label_SellPriceSymbol;
	private JLabel label_SellQuantitySymbol;
	private JLabel label_SellTotalSymbol;
	private JLabel label_InfoHighPrice;
	private JLabel label_InfoLowPrice;
	private JLabel label_InfoTradePrice;
	private JLabel label_InfoName;
	private JLabel label_InfoSymbol;
	private JLabel label_InfoVolume;
	private JLabel label_InfoChangeRate;
	private JLabel label_InfoChangePrice;



	public GUI()
	{
		setTitle("ºí·¢¸»¶ûÄ«¿ì");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		
		JPanel panel_Right = new JPanel();
		panel_Right.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Right.setBounds(975, 21, 300, 669);
		contentPane.add(panel_Right);
		panel_Right.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(0, 0, 300, 150);
		panel_Right.add(panel_3);
		
		JScrollPane scrollPane_OrderBook = new JScrollPane();
		scrollPane_OrderBook.setBounds(0, 150, 300, 519);
		panel_Right.add(scrollPane_OrderBook);
		
		table_OrderBook = new JTable();
		table_OrderBook.setShowVerticalLines(false);
		table_OrderBook.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		table_OrderBook.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
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
		
		JPanel panel_Center = new JPanel();
		panel_Center.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Center.setBounds(300, 21, 675, 669);
		contentPane.add(panel_Center);
		panel_Center.setLayout(null);
		
		JPanel panel_Bottom = new JPanel();
		panel_Bottom.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Bottom.setBounds(0, 439, 675, 230);
		panel_Center.add(panel_Bottom);
		panel_Bottom.setLayout(null);
		
		JTabbedPane tabbedPane_BuySell = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_BuySell.setBorder(null);
		tabbedPane_BuySell.setBounds(445, 0, 230, 230);
		panel_Bottom.add(tabbedPane_BuySell);
		
		JPanel panel_Buy = new JPanel();
		tabbedPane_BuySell.addTab("¸Å¼ö", null, panel_Buy, null);
		panel_Buy.setLayout(null);
		
		textField_BuyQuantity = new JTextField();
		textField_BuyQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_BuyQuantity.setBounds(41, 38, 134, 21);
		panel_Buy.add(textField_BuyQuantity);
		textField_BuyQuantity.setColumns(10);
		
		JLabel label_BuyQuantity = new JLabel("¼ö·®");
		label_BuyQuantity.setBounds(12, 41, 51, 15);
		panel_Buy.add(label_BuyQuantity);
		
		label_BuyQuantitySymbol = new JLabel("BTC");
		label_BuyQuantitySymbol.setBounds(183, 41, 62, 15);
		panel_Buy.add(label_BuyQuantitySymbol);
		
		spinner_BuyPrice = new JSpinner();
		spinner_BuyPrice.setBounds(41, 10, 134, 21);
		panel_Buy.add(spinner_BuyPrice);
		
		JLabel label_BuyPrice = new JLabel("°¡°Ý");
		label_BuyPrice.setBounds(12, 13, 51, 15);
		panel_Buy.add(label_BuyPrice);
		
		label_BuyPriceSymbol = new JLabel("KRW");
		label_BuyPriceSymbol.setBounds(183, 11, 62, 15);
		panel_Buy.add(label_BuyPriceSymbol);
		
		JButton button_Buy25 = new JButton("25%");
		button_Buy25.setBounds(5, 66, 108, 23);
		button_Buy25.addActionListener(new button_BuySellRatio(true, 0.25));
		panel_Buy.add(button_Buy25);
		
		JButton button_Buy50 = new JButton("50%");
		button_Buy50.setBounds(113, 66, 108, 23);
		button_Buy50.addActionListener(new button_BuySellRatio(true, 0.50));
		panel_Buy.add(button_Buy50);
		
		JButton button_Buy75 = new JButton("75%");
		button_Buy75.setBounds(5, 89, 108, 23);
		button_Buy75.addActionListener(new button_BuySellRatio(true, 0.75));
		panel_Buy.add(button_Buy75);
		
		JButton button_Buy100 = new JButton("100%");
		button_Buy100.setBounds(113, 89, 108, 23);
		button_Buy100.addActionListener(new button_BuySellRatio(true, 1.0));
		panel_Buy.add(button_Buy100);
		
		textField_BuyTotal = new JTextField();
		textField_BuyTotal.setFont(new Font("±¼¸²", Font.BOLD, 12));
		textField_BuyTotal.setForeground(Color.RED);
		textField_BuyTotal.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				updateBuySell();
			}
		});
		textField_BuyTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_BuyTotal.setEditable(false);
		textField_BuyTotal.setBounds(41, 129, 134, 21);
		panel_Buy.add(textField_BuyTotal);
		textField_BuyTotal.setColumns(10);
		
		JButton button_BuyRequest = new JButton("¸Å¼ö");
		button_BuyRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (Runnable runnable : getUpbit().getScheduledExecutor().getQueue())
				{
					System.out.println(runnable);
				}
			}
		});
		button_BuyRequest.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		button_BuyRequest.setForeground(Color.WHITE);
		button_BuyRequest.setBackground(Color.RED);
		button_BuyRequest.setBounds(12, 160, 201, 31);
		panel_Buy.add(button_BuyRequest);
		
		JLabel label_BuyTotal = new JLabel("ÃÑ¾×");
		label_BuyTotal.setBounds(12, 132, 57, 15);
		panel_Buy.add(label_BuyTotal);
		
		label_BuyTotalSymbol = new JLabel("KRW");
		label_BuyTotalSymbol.setBounds(183, 132, 62, 15);
		panel_Buy.add(label_BuyTotalSymbol);
		
		JPanel panel_Sell = new JPanel();
		tabbedPane_BuySell.addTab("¸Åµµ", null, panel_Sell, null);
		panel_Sell.setLayout(null);
		
		textField_SellQuantity = new JTextField();
		textField_SellQuantity.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				updateBuySell();
			}
		});
		textField_SellQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_SellQuantity.setColumns(10);
		textField_SellQuantity.setBounds(41, 38, 134, 21);
		panel_Sell.add(textField_SellQuantity);
		
		JLabel label_SellQuantity = new JLabel("¼ö·®");
		label_SellQuantity.setBounds(12, 41, 51, 15);
		panel_Sell.add(label_SellQuantity);
		
		label_SellQuantitySymbol = new JLabel("BTC");
		label_SellQuantitySymbol.setBounds(183, 41, 62, 15);
		panel_Sell.add(label_SellQuantitySymbol);
		
		spinner_SellPrice = new JSpinner();
		spinner_SellPrice.setBounds(41, 10, 134, 21);
		panel_Sell.add(spinner_SellPrice);
		
		JLabel label_SellPrice = new JLabel("°¡°Ý");
		label_SellPrice.setBounds(12, 13, 51, 15);
		panel_Sell.add(label_SellPrice);
		
		label_SellPriceSymbol = new JLabel("KRW");
		label_SellPriceSymbol.setBounds(183, 11, 62, 15);
		panel_Sell.add(label_SellPriceSymbol);
		
		JButton button_Sell25 = new JButton("25%");
		button_Sell25.setBounds(5, 66, 108, 23);
		button_Sell25.addActionListener(new button_BuySellRatio(false, 0.25));
		panel_Sell.add(button_Sell25);
		
		JButton button_Sell50 = new JButton("50%");
		button_Sell50.setBounds(113, 66, 108, 23);
		button_Sell50.addActionListener(new button_BuySellRatio(false, 0.50));
		panel_Sell.add(button_Sell50);
		
		JButton button_Sell75 = new JButton("75%");
		button_Sell75.setBounds(5, 89, 108, 23);
		button_Sell75.addActionListener(new button_BuySellRatio(false, 0.75));
		panel_Sell.add(button_Sell75);
		
		JButton button_Sell100 = new JButton("100%");
		button_Sell100.setBounds(113, 89, 108, 23);
		button_Sell100.addActionListener(new button_BuySellRatio(false, 1.0));
		panel_Sell.add(button_Sell100);
		
		textField_SellTotal = new JTextField();
		textField_SellTotal.setForeground(Color.BLUE);
		textField_SellTotal.setFont(new Font("±¼¸²", Font.BOLD, 12));
		textField_SellTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_SellTotal.setEditable(false);
		textField_SellTotal.setColumns(10);
		textField_SellTotal.setBounds(41, 129, 134, 21);
		panel_Sell.add(textField_SellTotal);
		
		JButton button_SellRequest = new JButton("¸Åµµ");
		button_SellRequest.setForeground(Color.WHITE);
		button_SellRequest.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		button_SellRequest.setBackground(Color.BLUE);
		button_SellRequest.setBounds(12, 160, 201, 31);
		panel_Sell.add(button_SellRequest);
		
		JLabel label_SellTotal = new JLabel("ÃÑ¾×");
		label_SellTotal.setBounds(12, 132, 57, 15);
		panel_Sell.add(label_SellTotal);
		
		label_SellTotalSymbol = new JLabel("KRW");
		label_SellTotalSymbol.setBounds(183, 132, 62, 15);
		panel_Sell.add(label_SellTotalSymbol);
		
		JTabbedPane tabbedPane_TradeHistory = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_TradeHistory.setBounds(0, 0, 445, 230);
		panel_Bottom.add(tabbedPane_TradeHistory);
		
		JScrollPane scrollPane_TradeNotComplete = new JScrollPane();
		scrollPane_TradeNotComplete.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		tabbedPane_TradeHistory.addTab("¹Ì Ã¤°á ÁÖ¹®", null, scrollPane_TradeNotComplete, null);
		
		table_TradeHistoryNotComplete = new JTable();
		table_TradeHistoryNotComplete.setModel(new DefaultTableModel(
			new Object[][] {}, new String[] { "ÁÖ¹®½Ã°£", "±¸ºÐ", "Ã¼°á°¡°Ý", "Ã¼°á¼ö·®", "Ã¼°á±Ý¾×" }) 
		{
			Class[] columnTypes = new Class[] 
			{
				String.class, String.class, String.class, String.class, String.class
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
		table_TradeHistoryNotComplete.getColumnModel().getColumn(0).setResizable(false);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(0).setPreferredWidth(70);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(1).setResizable(false);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(1).setPreferredWidth(40);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(2).setResizable(false);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(3).setResizable(false);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(4).setResizable(false);
		table_TradeHistoryNotComplete.getColumnModel().getColumn(4).setPreferredWidth(100);
		table_TradeHistoryNotComplete.setRowHeight(25);
		table_TradeHistoryNotComplete.getTableHeader().setReorderingAllowed(false);
		scrollPane_TradeNotComplete.setViewportView(table_TradeHistoryNotComplete);
		
		JScrollPane scrollPane_TradeComplete = new JScrollPane();
		scrollPane_TradeComplete.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		tabbedPane_TradeHistory.addTab("Ã¤°á ¿Ï·á ³»¿ª", null, scrollPane_TradeComplete, null);
		
		table_TradeHistoryComplete = new JTable();
		table_TradeHistoryComplete.setModel(new DefaultTableModel(
			new Object[][] {}, new String[] { "ÁÖ¹®½Ã°£", "±¸ºÐ", "Ã¼°á°¡°Ý", "Ã¼°á¼ö·®", "Ã¼°á±Ý¾×" }) 
		{
			Class[] columnTypes = new Class[] 
			{
				String.class, String.class, String.class, String.class, String.class
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
		table_TradeHistoryComplete.getColumnModel().getColumn(0).setResizable(false);
		table_TradeHistoryComplete.getColumnModel().getColumn(0).setPreferredWidth(70);
		table_TradeHistoryComplete.getColumnModel().getColumn(1).setResizable(false);
		table_TradeHistoryComplete.getColumnModel().getColumn(1).setPreferredWidth(40);
		table_TradeHistoryComplete.getColumnModel().getColumn(2).setResizable(false);
		table_TradeHistoryComplete.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_TradeHistoryComplete.getColumnModel().getColumn(3).setResizable(false);
		table_TradeHistoryComplete.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_TradeHistoryComplete.getColumnModel().getColumn(4).setResizable(false);
		table_TradeHistoryComplete.getColumnModel().getColumn(4).setPreferredWidth(100);
		table_TradeHistoryComplete.setRowHeight(25);
		table_TradeHistoryComplete.getTableHeader().setReorderingAllowed(false);
		scrollPane_TradeComplete.setViewportView(table_TradeHistoryComplete);
		
		JPanel panel_Chart = new JPanel();
		panel_Chart.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Chart.setBounds(0, 0, 675, 439);
		panel_Center.add(panel_Chart);
		panel_Chart.setLayout(null);
		
		JPanel panel_InfoLeft = new JPanel();
		panel_InfoLeft.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_InfoLeft.setBounds(0, 0, 675, 55);
		panel_Chart.add(panel_InfoLeft);
		panel_InfoLeft.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uAC70\uB798\uB300\uAE08 (24H)");
		lblNewLabel.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(560, 10, 86, 15);
		panel_InfoLeft.add(lblNewLabel);
		
		label_InfoVolume = new JLabel("123,123,123,123KRW");
		label_InfoVolume.setHorizontalAlignment(SwingConstants.LEFT);
		label_InfoVolume.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 10));
		label_InfoVolume.setBounds(560, 27, 111, 15);
		panel_InfoLeft.add(label_InfoVolume);
		
		JLabel lblNewLabel_2 = new JLabel("\uACE0\uAC00");
		lblNewLabel_2.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setBounds(445, 10, 57, 15);
		panel_InfoLeft.add(lblNewLabel_2);
		
		JLabel label = new JLabel("\uC800\uAC00");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label.setBounds(445, 26, 57, 15);
		panel_InfoLeft.add(label);
		
		label_InfoHighPrice = new JLabel("9,123,123");
		label_InfoHighPrice.setForeground(Color.RED);
		label_InfoHighPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		label_InfoHighPrice.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_InfoHighPrice.setBounds(455, 9, 76, 15);
		panel_InfoLeft.add(label_InfoHighPrice);
		
		label_InfoLowPrice = new JLabel("8,413,246");
		label_InfoLowPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		label_InfoLowPrice.setForeground(Color.BLUE);
		label_InfoLowPrice.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_InfoLowPrice.setBounds(455, 26, 76, 15);
		panel_InfoLeft.add(label_InfoLowPrice);
		
		JLabel label_3 = new JLabel("\uC804\uC77C\uB300\uBE44");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		label_3.setBounds(340, 10, 57, 15);
		panel_InfoLeft.add(label_3);
		
		label_InfoChangeRate = new JLabel("1.32%");
		label_InfoChangeRate.setHorizontalAlignment(SwingConstants.RIGHT);
		label_InfoChangeRate.setForeground(Color.RED);
		label_InfoChangeRate.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_InfoChangeRate.setBounds(285, 26, 76, 15);
		panel_InfoLeft.add(label_InfoChangeRate);
		
		label_InfoChangePrice = new JLabel("242,000");
		label_InfoChangePrice.setHorizontalAlignment(SwingConstants.RIGHT);
		label_InfoChangePrice.setForeground(Color.RED);
		label_InfoChangePrice.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
		label_InfoChangePrice.setBounds(365, 26, 57, 15);
		panel_InfoLeft.add(label_InfoChangePrice);
		
		JPanel panel_Info = new JPanel();
		panel_Info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Info.setBounds(0, 0, 310, 55);
		panel_InfoLeft.add(panel_Info);
		panel_Info.setLayout(null);
		
		label_InfoName = new JLabel("\uC774\uC624\uC2A4");
		label_InfoName.setBounds(15, 8, 275, 25);
		panel_Info.add(label_InfoName);
		label_InfoName.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		label_InfoName.setHorizontalAlignment(SwingConstants.LEFT);
		
		label_InfoSymbol = new JLabel("BTC / KRW");
		label_InfoSymbol.setBounds(15, 30, 105, 17);
		panel_Info.add(label_InfoSymbol);
		label_InfoSymbol.setHorizontalAlignment(SwingConstants.LEFT);
		label_InfoSymbol.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		
		label_InfoTradePrice = new JLabel("21,325 KRW");
		label_InfoTradePrice.setBounds(105, 9, 185, 30);
		panel_Info.add(label_InfoTradePrice);
		label_InfoTradePrice.setForeground(Color.RED);
		label_InfoTradePrice.setHorizontalAlignment(SwingConstants.RIGHT);
		label_InfoTradePrice.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(0, 55, 675, 314);
		panel_Chart.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(0, 369, 675, 70);
		panel_Chart.add(panel_2);
		
		JTabbedPane tabbedPane_CoinList = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_CoinList.addChangeListener(new tabListener(tabbedPane_CoinList));
		tabbedPane_CoinList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		tabbedPane_CoinList.setBounds(0, 21, 300, 669);
		contentPane.add(tabbedPane_CoinList);
		
		JPanel panel_KRW = new JPanel();
		tabbedPane_CoinList.addTab("KRW", null, panel_KRW, null);
		panel_KRW.setLayout(null);
		
		textField_SearchKRW = new JTextField();
		textField_SearchKRW.setBounds(0, 0, 227, 25);
		panel_KRW.add(textField_SearchKRW);
		textField_SearchKRW.setColumns(10);
		
		JButton button_SearchKRW = new JButton("°Ë»ö");
		button_SearchKRW.setBounds(226, 0, 65, 25);
		panel_KRW.add(button_SearchKRW);
		
		JScrollPane scrollPane_KRW = new JScrollPane();
		scrollPane_KRW.setBounds(0, 27, 291, 609);
		scrollPane_KRW.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		panel_KRW.add(scrollPane_KRW);
		
		table_KRW = new JTable();
		table_KRW.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		table_KRW.addMouseListener(new table_Select(table_KRW));
		table_KRW.setShowVerticalLines(false);
		table_KRW.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] { "ÀÌ¸§", "ÇöÀç°¡", "ÀüÀÏ´ëºñ", "°Å·¡·®" }) 
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
		table_KRW.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_KRW.getColumnModel().getColumn(1).setResizable(false);
		table_KRW.getColumnModel().getColumn(2).setResizable(false);
		table_KRW.getColumnModel().getColumn(2).setPreferredWidth(70);
		table_KRW.getColumnModel().getColumn(3).setResizable(false);
		table_KRW.getColumnModel().getColumn(3).setPreferredWidth(80);
		
		table_KRW.getColumnModel().getColumn(0).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_KRW.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_KRW.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_KRW.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer_CoinTable());
		table_KRW.setRowHeight(40);
		table_KRW.getTableHeader().setReorderingAllowed(false);

		
		scrollPane_KRW.setViewportView(table_KRW);
		
		JPanel panel_BTC = new JPanel();
		tabbedPane_CoinList.addTab("BTC", null, panel_BTC, null);
		panel_BTC.setLayout(null);
		
		textField_SearchBTC = new JTextField();
		textField_SearchBTC.setColumns(10);
		textField_SearchBTC.setBounds(0, 0, 227, 25);
		panel_BTC.add(textField_SearchBTC);
		
		JButton button_SearchBTC = new JButton("\uAC80\uC0C9");
		button_SearchBTC.setBounds(226, 0, 65, 25);
		panel_BTC.add(button_SearchBTC);
		
		JScrollPane scrollPane_BTC = new JScrollPane();
		scrollPane_BTC.setBounds(0, 27, 291, 609);
		panel_BTC.add(scrollPane_BTC);
		
		table_BTC = new JTable();
		table_BTC.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		));
		table_BTC.setShowVerticalLines(false);
		table_BTC.setRowHeight(40);
		table_BTC.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		scrollPane_BTC.setViewportView(table_BTC);
		
		JPanel panel_ETH = new JPanel();
		tabbedPane_CoinList.addTab("ETH", null, panel_ETH, null);
		panel_ETH.setLayout(null);
		
		textField_SearchETH = new JTextField();
		textField_SearchETH.setColumns(10);
		textField_SearchETH.setBounds(0, 0, 227, 25);
		panel_ETH.add(textField_SearchETH);
		
		JButton button_SearchETH = new JButton("\uAC80\uC0C9");
		button_SearchETH.setBounds(226, 0, 65, 25);
		panel_ETH.add(button_SearchETH);
		
		JScrollPane scrollPane_ETH = new JScrollPane();
		scrollPane_ETH.setBounds(0, 27, 291, 609);
		panel_ETH.add(scrollPane_ETH);
		
		table_ETH = new JTable();
		table_ETH.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		));
		table_ETH.setShowVerticalLines(false);
		table_ETH.setRowHeight(40);
		table_ETH.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		scrollPane_ETH.setViewportView(table_ETH);
		
		JPanel panel_MyCoin = new JPanel();
		tabbedPane_CoinList.addTab("º¸À¯ÄÚÀÎ", null, panel_MyCoin, null);
		panel_MyCoin.setLayout(null);
		
		textField_searchMyCoin = new JTextField();
		textField_searchMyCoin.setColumns(10);
		textField_searchMyCoin.setBounds(0, 0, 227, 25);
		panel_MyCoin.add(textField_searchMyCoin);
		
		JButton button_searchMyCoin = new JButton("°Ë»ö");
		button_searchMyCoin.setBounds(226, 0, 65, 25);
		panel_MyCoin.add(button_searchMyCoin);
		
		JScrollPane scrollPane_MyCoin = new JScrollPane();
		scrollPane_MyCoin.setBounds(0, 27, 291, 609);
		scrollPane_MyCoin.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		panel_MyCoin.add(scrollPane_MyCoin);
		
		table_MyCoin = new JTable();
		table_MyCoin.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
		table_MyCoin.setShowVerticalLines(false);
		table_MyCoin.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] { "ÀÌ¸§", "º¸À¯(Æò°¡±Ý)", "¸Å¼öÆò±Õ°¡", "¼öÀÍ·ü" }) 
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
		table_MyCoin.getColumnModel().getColumn(0).setResizable(false);
		table_MyCoin.getColumnModel().getColumn(1).setResizable(false);
		table_MyCoin.getColumnModel().getColumn(1).setPreferredWidth(100);
		table_MyCoin.getColumnModel().getColumn(2).setResizable(false);
		table_MyCoin.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_MyCoin.getColumnModel().getColumn(3).setResizable(false);
		table_MyCoin.setRowHeight(40);
		table_KRW.getTableHeader().setReorderingAllowed(false);
		scrollPane_MyCoin.setViewportView(table_MyCoin);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1275, 21);
		contentPane.add(menuBar);
		
		JMenu mnMenu = new JMenu("Menu1");
		menuBar.add(mnMenu);
		
		JMenu mnMenu_1 = new JMenu("Menu2");
		menuBar.add(mnMenu_1);
		
		JMenu mnMenu_2 = new JMenu("Menu3");
		menuBar.add(mnMenu_2);


		coinTableElements = new ArrayList<CoinTableElement>();
		orderTableElements = new ArrayList<OrderTableElement>();
		selectedRow = 0;
	}
	
	public void initiate()
	{
		currentMarket = Market.KRW;

		clearBuySell();
		updateCoinTable(Market.KRW);
		table_KRW.setRowSelectionInterval(0, 0);
		
		updateInfo(currentMarket, coinTableElements.get(selectedRow).getCoinSymbol());
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
	
	public void updateCoinTable(Market market)
	{
		ArrayList<CryptoCurrency> list = upbit.getCryptoList();
		DefaultTableModel model = null;
		CoinTableElement element;

		double volume;
		double signedChangeRate;

		switch (market)
		{
		case KRW:
			model = (DefaultTableModel) table_KRW.getModel();
			break;		
			
		case BTC:
			model = (DefaultTableModel) table_BTC.getModel();
			break;
			
		case ETH:
			model = (DefaultTableModel) table_ETH.getModel();
			break;
		}
		
		for (CryptoCurrency cryptoCurrency : list)
		{
			if (cryptoCurrency.getMarket() != market)
				continue;
			
			element = getCoinTableElement(cryptoCurrency.getMarket(), cryptoCurrency.getCoinSymbol());
			
			if (cryptoCurrency.getName().equals(upbit.createName(market, cryptoCurrency.getCoinSymbol(), TermType.minutes, 60)))
			{
				volume = 0;
				
				for (int index = 0; index < 24; index++)
				{
					volume += Double.parseDouble(cryptoCurrency.getData(JsonKey.candleAccTradePrice, index));
				}
				
				element.setTradePrice(Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice)));
				element.setVolume(volume);
				
				continue;
			}
			
			if (cryptoCurrency.getName().equals(upbit.createName(market, cryptoCurrency.getCoinSymbol(), TermType.days, 1)))
			{
				element.setChangeRate(Double.parseDouble(cryptoCurrency.getData(JsonKey.signedChangeRate)));
				
				continue;
			}
		}
		
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
		ArrayList<Order> list;
		Order order;
		OrderBook orderBook;
		OrderTableElement orderTableElement;
		DefaultTableModel model = (DefaultTableModel) table_OrderBook.getModel();

		try
		{
			orderBook = getUpbit().getOrderBook(market, coinSymbol);
			list = orderBook.getOrderList();
		}
		catch (Exception e1)
		{
			return false;
		}
		
		for (int index = 0; index < list.size(); index++)
		{
			order = orderBook.getOrder(index);
			orderTableElement = getOrderTableElement(index);
			
			orderTableElement.setPrice(order.getPrice());
			orderTableElement.setChangeRate(order.getPercentage());
			orderTableElement.setQuantity(order.getQuantity());
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
	
	public void updateBuySell()
	{
		if (getBuyPrice() == 0 || getBuyQuantity() ==0 || getSellQuantity() == 0)
			setBuyTotal(0);
		
		if (getSellPrice() == 0 || getSellQuantity() == 0)
			setSellTotal(0);

		setBuyTotal(getBuyPrice() * getBuyQuantity());
		setSellTotal(getSellPrice() * getSellQuantity());

		if (currentMarket == Market.KRW)
		{
			setBuyTotal(Math.round(getBuyTotal()));
			setSellTotal(Math.round(getSellTotal()));
		}
		
		decimalFormat = new DecimalFormat("#,###.########");
		
		spinner_BuyPrice.setValue(getBuyPrice());
		spinner_SellPrice.setValue(getSellPrice());
		textField_BuyQuantity.setText(decimalFormat.format(getBuyQuantity()));
		textField_SellQuantity.setText(decimalFormat.format(getSellQuantity()));
		textField_BuyTotal.setText(decimalFormat.format(getBuyTotal()));
		textField_SellTotal.setText(decimalFormat.format(getSellTotal()));
	}
	
	public void clearBuySell()
	{
		setBuyPrice(0);
		setBuyQuantity(0);
		setBuyTotal(0);
		setSellPrice(0);
		setSellQuantity(0);
		setSellTotal(0);

		updateBuySell();
	}
	
	public void updateInfo(Market market, CoinSymbol coinSymbol)
	{
		CryptoCurrency cryptoCurrency;
		
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
		label_InfoVolume.setText(decimalFormat.format(Math.round(upbit.getVolume(market, coinSymbol, 24))) + market);

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
	}
	
	
	


	class button_BuySellRatio implements ActionListener
	{
		private boolean isBuy;
		private double ratio;
		
		public button_BuySellRatio(boolean isBuy, double ratio)
		{
			setBuy(isBuy);
			setRatio(ratio);
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			double balance = 0, price, quantity;
			
			if (isBuy == true)
			{
				switch (currentMarket)
				{
				case KRW:
					balance = upbit.getAccount().getKRW();
					break;
					
				case BTC:
					balance = upbit.getAccount().getBalance(CoinSymbol.BTC);
					break;
					
				case ETH:
					balance = upbit.getAccount().getBalance(CoinSymbol.ETH);
				}
				
				if (balance == 0 || getBuyPrice() == 0)
					setBuyQuantity(0);
				else
				{
					quantity = (balance * getRatio()) / getBuyPrice();
					setBuyQuantity(quantity);
				}
				
				updateBuySell();
			}
			else
			{
				CoinSymbol coinSymbol = coinTableElements.get(selectedRow).getCoinSymbol();
				
				balance = upbit.getAccount().getBalance(coinSymbol);
				
				price = Double.parseDouble(spinner_BuyPrice.getValue().toString());
				quantity = balance * getRatio();
				
				setSellQuantity(quantity);
				
				updateBuySell();
			}
		}

		public boolean isBuy()
		{
			return isBuy;
		}

		public void setBuy(boolean isBuy)
		{
			this.isBuy = isBuy;
		}

		public double getRatio()
		{
			return ratio;
		}

		public void setRatio(double ratio)
		{
			this.ratio = ratio;
		}
	}
	
	class button_BuySell implements ActionListener
	{
		private boolean isBuy;
		
		public button_BuySell(boolean isBuy)
		{
			this.setBuy(isBuy);
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			double price, quantity, total; 
			
			if (isBuy)
			{
				price = Double.parseDouble(spinner_BuyPrice.getValue().toString());
				quantity = Double.parseDouble(textField_BuyQuantity.getText());
				total = Double.parseDouble(textField_BuyTotal.getText());
			}
			else
			{
				
			}
		}
		
		public boolean isBuy()
		{
			return isBuy;
		}

		public void setBuy(boolean isBuy)
		{
			this.isBuy = isBuy;
		}
	}
	
	class table_Select implements MouseListener
	{
		private JTable jTable;
		
		public table_Select(JTable jTable)
		{
			this.jTable = jTable;
		}
		
		@Override
		public void mouseClicked(java.awt.event.MouseEvent e)
		{
			selectedRow = jTable.getSelectedRow();
			
			double price;
			price = coinTableElements.get(selectedRow).getTradePrice();
			
			CoinTableElement element = coinTableElements.get(selectedRow);
			label_BuyQuantitySymbol.setText(element.getCoinSymbol().toString());
			label_SellPriceSymbol.setText(element.getCoinSymbol().toString());
			
			setBuyPrice(price);
			setSellPrice(price);

			updateBuySell();
			getExecutorService().submit(()-> updateInfo(element.getMarket(), element.getCoinSymbol()));
			getExecutorService().submit(()-> upbit.updateOrderBook(element.getMarket(), element.getCoinSymbol()));
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

	class tabListener implements ChangeListener
	{
		private JTabbedPane tab;
		
		public tabListener(JTabbedPane tab)
		{
			this.tab = tab;
		}
		
		@Override
		public void stateChanged(ChangeEvent e)
		{
			switch (tab.getSelectedIndex())
			{
			case 0:
				currentMarket = Market.KRW;
				setSelection(table_KRW, 0, 0);
				break;
				
			case 1:
				currentMarket = Market.BTC;
				setSelection(table_BTC, 0, 0);
				break;
				
			case 2:
				currentMarket = Market.ETH;
				setSelection(table_ETH, 0, 0);
				break;
				
			case 3:
				currentMarket = Market.KRW;
				setSelection(table_MyCoin, 0, 0);
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
	
	public void sort(int column)
	{
		// ¸®½ºÆ® ÀÌ¸§ : coinTableElements
		
		// 1ÀÌ¸é getTradePrice()
		// 2ÀÌ¸é getChangeRate()
		// 3ÀÌ¸é getVolume()

		
		switch (column)
		{
		case 1:
			
			break;
			
		case 2:
			
			break;
			
		case 3:
			
			break;
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
		
		this.buyQuantity = buyQuantity;
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
}
