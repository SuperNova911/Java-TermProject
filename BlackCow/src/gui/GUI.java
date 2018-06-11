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

import upbit.CoinSymbol;
import upbit.CryptoCurrency;
import upbit.JsonManager.JsonKey;
import upbit.Market;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GUI extends JFrame
{
	private Upbit upbit;
	
	private int selectedRow;

	private JPanel contentPane;
	private JTable table_TradeHistoryNotComplete;
	private JTable table_TradeHistoryComplete;
	private JTextField textField_BuyQuantity;
	private JTextField textField_BuyTotal;
	private JTextField textField_SellQuantity;
	private JTextField textField_SellTotal;
	private JTextField textField_SearchKRW;
	private JTable table_KRW;
	private JTextField textField_searchMyCoin;
	private JTable table_MyCoin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GUI frame = new GUI();
					frame.setVisible(true);
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI()
	{
		setTitle("블랙말랑카우");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		
		JPanel panel_Right = new JPanel();
		panel_Right.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_Right.setBounds(975, 21, 300, 669);
		contentPane.add(panel_Right);
		panel_Right.setLayout(null);
		
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
		tabbedPane_BuySell.addTab("매수", null, panel_Buy, null);
		panel_Buy.setLayout(null);
		
		textField_BuyQuantity = new JTextField();
		textField_BuyQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_BuyQuantity.setBounds(41, 38, 134, 21);
		panel_Buy.add(textField_BuyQuantity);
		textField_BuyQuantity.setColumns(10);
		
		JLabel label_BuyQuantity = new JLabel("수량");
		label_BuyQuantity.setBounds(12, 41, 51, 15);
		panel_Buy.add(label_BuyQuantity);
		
		JLabel label_BuyQuantitySymbol = new JLabel("BTC");
		label_BuyQuantitySymbol.setBounds(183, 41, 62, 15);
		panel_Buy.add(label_BuyQuantitySymbol);
		
		JSpinner spinner_BuyPrice = new JSpinner();
		spinner_BuyPrice.setBounds(41, 10, 134, 21);
		panel_Buy.add(spinner_BuyPrice);
		
		JLabel label_BuyPrice = new JLabel("가격");
		label_BuyPrice.setBounds(12, 13, 51, 15);
		panel_Buy.add(label_BuyPrice);
		
		JLabel label_BuyPriceSymbol = new JLabel("KRW");
		label_BuyPriceSymbol.setBounds(183, 11, 40, 15);
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
		textField_BuyTotal.setEditable(false);
		textField_BuyTotal.setBounds(41, 129, 134, 21);
		panel_Buy.add(textField_BuyTotal);
		textField_BuyTotal.setColumns(10);
		
		JButton button_BuyRequest = new JButton("매수");
		button_BuyRequest.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		button_BuyRequest.setForeground(Color.WHITE);
		button_BuyRequest.setBackground(Color.RED);
		button_BuyRequest.setBounds(12, 160, 201, 31);
		panel_Buy.add(button_BuyRequest);
		
		JLabel label_BuyTotal = new JLabel("총액");
		label_BuyTotal.setBounds(12, 132, 57, 15);
		panel_Buy.add(label_BuyTotal);
		
		JLabel label_BuyTotalSymbol = new JLabel("KRW");
		label_BuyTotalSymbol.setBounds(183, 132, 40, 15);
		panel_Buy.add(label_BuyTotalSymbol);
		
		JPanel panel_Sell = new JPanel();
		tabbedPane_BuySell.addTab("매도", null, panel_Sell, null);
		panel_Sell.setLayout(null);
		
		textField_SellQuantity = new JTextField();
		textField_SellQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_SellQuantity.setColumns(10);
		textField_SellQuantity.setBounds(41, 38, 134, 21);
		panel_Sell.add(textField_SellQuantity);
		
		JLabel label_SellQuantity = new JLabel("수량");
		label_SellQuantity.setBounds(12, 41, 51, 15);
		panel_Sell.add(label_SellQuantity);
		
		JLabel label_SellQuantitySymbol = new JLabel("BTC");
		label_SellQuantitySymbol.setBounds(183, 41, 62, 15);
		panel_Sell.add(label_SellQuantitySymbol);
		
		JSpinner spinner_SellPrice = new JSpinner();
		spinner_SellPrice.setBounds(41, 10, 134, 21);
		panel_Sell.add(spinner_SellPrice);
		
		JLabel label_SellPrice = new JLabel("가격");
		label_SellPrice.setBounds(12, 13, 51, 15);
		panel_Sell.add(label_SellPrice);
		
		JLabel label_SellPriceSymbol = new JLabel("KRW");
		label_SellPriceSymbol.setBounds(183, 11, 40, 15);
		panel_Sell.add(label_SellPriceSymbol);
		
		JButton button_Sell25 = new JButton("25%");
		button_Sell25.setBounds(5, 66, 108, 23);
		panel_Sell.add(button_Sell25);
		
		JButton button_Sell50 = new JButton("50%");
		button_Sell50.setBounds(113, 66, 108, 23);
		panel_Sell.add(button_Sell50);
		
		JButton button_Sell75 = new JButton("75%");
		button_Sell75.setBounds(5, 89, 108, 23);
		panel_Sell.add(button_Sell75);
		
		JButton button_Sell100 = new JButton("100%");
		button_Sell100.setBounds(113, 89, 108, 23);
		panel_Sell.add(button_Sell100);
		
		textField_SellTotal = new JTextField();
		textField_SellTotal.setEditable(false);
		textField_SellTotal.setColumns(10);
		textField_SellTotal.setBounds(41, 129, 134, 21);
		panel_Sell.add(textField_SellTotal);
		
		JButton button_SellRequest = new JButton("매도");
		button_SellRequest.setForeground(Color.WHITE);
		button_SellRequest.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		button_SellRequest.setBackground(Color.BLUE);
		button_SellRequest.setBounds(12, 160, 201, 31);
		panel_Sell.add(button_SellRequest);
		
		JLabel label_SellTotal = new JLabel("총액");
		label_SellTotal.setBounds(12, 132, 57, 15);
		panel_Sell.add(label_SellTotal);
		
		JLabel label_SellTotalSymbol = new JLabel("KRW");
		label_SellTotalSymbol.setBounds(183, 132, 40, 15);
		panel_Sell.add(label_SellTotalSymbol);
		
		JTabbedPane tabbedPane_TradeHistory = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_TradeHistory.setBounds(0, 0, 445, 230);
		panel_Bottom.add(tabbedPane_TradeHistory);
		
		JScrollPane scrollPane_TradeNotComplete = new JScrollPane();
		scrollPane_TradeNotComplete.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		tabbedPane_TradeHistory.addTab("미 채결 주문", null, scrollPane_TradeNotComplete, null);
		
		table_TradeHistoryNotComplete = new JTable();
		table_TradeHistoryNotComplete.setModel(new DefaultTableModel(
			new Object[][] {}, new String[] { "주문시간", "구분", "체결가격", "체결수량", "체결금액" }) 
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
		tabbedPane_TradeHistory.addTab("채결 완료 내역", null, scrollPane_TradeComplete, null);
		
		table_TradeHistoryComplete = new JTable();
		table_TradeHistoryComplete.setModel(new DefaultTableModel(
			new Object[][] {}, new String[] { "주문시간", "구분", "체결가격", "체결수량", "체결금액" }) 
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
		
		JTabbedPane tabbedPane_CoinList = new JTabbedPane(JTabbedPane.TOP);
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
		
		JButton button_SearchKRW = new JButton("검색");
		button_SearchKRW.setBounds(226, 0, 65, 25);
		panel_KRW.add(button_SearchKRW);
		
		JScrollPane scrollPane_KRW = new JScrollPane();
		scrollPane_KRW.setBounds(0, 27, 291, 630);
		scrollPane_KRW.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		panel_KRW.add(scrollPane_KRW);
		
		table_KRW = new JTable();
		table_KRW.addMouseListener(new table_Select(table_KRW));
		table_KRW.setShowVerticalLines(false);
		table_KRW.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] { "이름", "현재가", "전일대비", "거래량" }) 
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
		cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		table_KRW.getColumnModel().getColumn(0).setResizable(false);
		table_KRW.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_KRW.getColumnModel().getColumn(1).setResizable(false);
		table_KRW.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
		table_KRW.getColumnModel().getColumn(2).setResizable(false);
		table_KRW.getColumnModel().getColumn(2).setPreferredWidth(70);
		table_KRW.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
		table_KRW.getColumnModel().getColumn(3).setResizable(false);
		table_KRW.getColumnModel().getColumn(3).setPreferredWidth(80);
		table_KRW.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
		table_KRW.setRowHeight(40);
		table_KRW.getTableHeader().setReorderingAllowed(false);
		scrollPane_KRW.setViewportView(table_KRW);
		
		JPanel panel_BTC = new JPanel();
		tabbedPane_CoinList.addTab("BTC", null, panel_BTC, null);
		panel_BTC.setLayout(null);
		
		JPanel panel_ETH = new JPanel();
		tabbedPane_CoinList.addTab("ETH", null, panel_ETH, null);
		
		JPanel panel_MyCoin = new JPanel();
		tabbedPane_CoinList.addTab("보유코인", null, panel_MyCoin, null);
		panel_MyCoin.setLayout(null);
		
		textField_searchMyCoin = new JTextField();
		textField_searchMyCoin.setColumns(10);
		textField_searchMyCoin.setBounds(0, 0, 227, 25);
		panel_MyCoin.add(textField_searchMyCoin);
		
		JButton button_searchMyCoin = new JButton("검색");
		button_searchMyCoin.setBounds(226, 0, 65, 25);
		panel_MyCoin.add(button_searchMyCoin);
		
		JScrollPane scrollPane_MyCoin = new JScrollPane();
		scrollPane_MyCoin.setBounds(0, 27, 291, 630);
		scrollPane_MyCoin.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		panel_MyCoin.add(scrollPane_MyCoin);
		
		table_MyCoin = new JTable();
		table_MyCoin.setShowVerticalLines(false);
		table_MyCoin.setModel(new DefaultTableModel(
				new Object[][] {}, new String[] { "이름", "보유(평가금)", "매수평균가", "수익률" }) 
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
		table_MyCoin.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
		table_MyCoin.getColumnModel().getColumn(2).setResizable(false);
		table_MyCoin.getColumnModel().getColumn(2).setPreferredWidth(100);
		table_MyCoin.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
		table_MyCoin.getColumnModel().getColumn(3).setResizable(false);
		table_MyCoin.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);
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
	}
	
	
	public void updateCoinTable(upbit.CoinList.Market market)
	{
		ArrayList<CryptoCurrency> list = upbit.getCryptoList();
		DefaultTableModel model = null;
		DecimalFormat format = new DecimalFormat("#.##");
		
		switch (market)
		{
		case KRW:
			model = (DefaultTableModel) table_KRW.getModel();
			break;		
			
		case BTC:
			break;
		case ETH:
			break;
		}
		
		model.setNumRows(0);
		
		for (CryptoCurrency cryptoCurrency : list)
		{
			if (cryptoCurrency.getMarket() == market)
			{	
				model.addRow(new Object[] {
						cryptoCurrency.getNameKR(), format.format(Double.parseDouble(cryptoCurrency.getData(JsonKey.tradePrice))),
						"", ""
				});
			}
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
			double balance = upbit.getAccount().getKRW();
			balance *= ratio;
			
			if (isBuy == true)
				textField_BuyQuantity.setText(Double.toString(balance));
			else
				textField_SellQuantity.setText(Double.toString(balance));
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
			int index = this.jTable.getSelectedRow();
			System.out.println(index + "선택");
		}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e)
		{
			// TODO 자동 생성된 메소드 스텁
			
		}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e)
		{
			// TODO 자동 생성된 메소드 스텁
			
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e)
		{
			// TODO 자동 생성된 메소드 스텁
			
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e)
		{
			// TODO 자동 생성된 메소드 스텁
			
		}
	}
}
