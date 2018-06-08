import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestWB extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JTabbedPane tabbedPane_1;
	private JPanel panel;
	private JPanel panel_1;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTable table_2;
	private JTable table_3;
	private JTable table_4;
	private JTable table_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestWB frame = new TestWB();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestWB() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 0, 300, 720);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(0, 0, 300, 720);
		panel_3.add(tabbedPane_2);
		
		JPanel panel_7 = new JPanel();
		tabbedPane_2.addTab("KRW", null, panel_7, null);
		panel_7.setLayout(null);
		
		textField_4 = new JTextField();
		textField_4.setBounds(0, 10, 196, 33);
		panel_7.add(textField_4);
		textField_4.setColumns(10);
		
		JButton btnNewButton_9 = new JButton("\uAC80\uC0C9");
		btnNewButton_9.setBounds(200, 15, 95, 23);
		panel_7.add(btnNewButton_9);
		
		table_5 = new JTable();
		table_5.setModel(new DefaultTableModel(
			new Object[][] {
				{"\uC774\uB984", "\uD604\uC7AC\uAC00", "\uC804\uC77C\uB300\uBE44"},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table_5.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_5.getColumnModel().getColumn(1).setPreferredWidth(100);
		table_5.getColumnModel().getColumn(2).setPreferredWidth(80);
		table_5.setBounds(0, 53, 300, 720);
		panel_7.add(table_5);
		
		JPanel panel_8 = new JPanel();
		tabbedPane_2.addTab("BTC", null, panel_8, null);
		panel_8.setLayout(null);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(0, 10, 196, 33);
		panel_8.add(textField_5);
		
		JButton button_1 = new JButton("\uAC80\uC0C9");
		button_1.setBounds(200, 15, 95, 23);
		panel_8.add(button_1);
		
		table_4 = new JTable();
		table_4.setModel(new DefaultTableModel(
			new Object[][] {
				{"\uC774\uB984", "\uD604\uC7AC\uAC00", "\uC804\uC77C\uB300\uBE44"},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table_4.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_4.getColumnModel().getColumn(1).setPreferredWidth(100);
		table_4.getColumnModel().getColumn(2).setPreferredWidth(80);
		table_4.setBounds(0, 51, 300, 720);
		panel_8.add(table_4);
		
		JPanel panel_9 = new JPanel();
		tabbedPane_2.addTab("ETH", null, panel_9, null);
		panel_9.setLayout(null);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(0, 10, 196, 33);
		panel_9.add(textField_6);
		
		JButton button_2 = new JButton("\uAC80\uC0C9");
		button_2.setBounds(200, 15, 95, 23);
		panel_9.add(button_2);
		
		table_3 = new JTable();
		table_3.setModel(new DefaultTableModel(
			new Object[][] {
				{"\uC774\uB984", "\uD604\uC7AC\uAC00", "\uC804\uC77C\uB300\uBE44"},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table_3.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_3.getColumnModel().getColumn(1).setPreferredWidth(100);
		table_3.getColumnModel().getColumn(2).setPreferredWidth(80);
		table_3.setBounds(-5, 48, 300, 720);
		panel_9.add(table_3);
		
		JPanel panel_10 = new JPanel();
		tabbedPane_2.addTab("보유코인", null, panel_10, null);
		panel_10.setLayout(null);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(0, 10, 196, 33);
		panel_10.add(textField_7);
		
		JButton button_3 = new JButton("\uAC80\uC0C9");
		button_3.setBounds(200, 15, 95, 23);
		panel_10.add(button_3);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
				{"이름", "현재가", "전일대비"},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		table_2.setBounds(0, 55, 300, 720);
		panel_10.add(table_2);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(300, 0, 680, 490);
		contentPane.add(panel_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(300, 490, 680, 230);
		contentPane.add(panel_5);
		panel_5.setLayout(null);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 680, 222);
		panel_5.add(panel_2);
		panel_2.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(22, 10, 390, 175);
		panel_2.add(tabbedPane);
		
		JPanel conclusion = new JPanel();
		tabbedPane.addTab("미 채결 주문", null, conclusion, null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"\uC8FC\uBB38\uC2DC\uAC04", "\uAD6C\uBD84", "\uCCB4\uACB0 \uAC00\uACA9", "\uCCB4\uACB0\uC218\uB7C9", "\uCCB4\uACB0\uAE08\uC561"},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"\uC8FC\uBB38\uC2DC\uAC04", "\uAD6C\uBD84", "\uCCB4\uACB0 \uAC00\uACA9", "\uCCB4\uACB0\uC218\uB7C9", "\uCCB4\uACB0\uAE08\uC561"
			}
		));
		
		conclusion.add(table);
		
		JPanel unconclusion = new JPanel();
		tabbedPane.addTab("채결 완료 내역", null, unconclusion, null);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"\uC8FC\uBB38\uC2DC\uAC04", "\uAD6C\uBD84", "\uCCB4\uACB0 \uAC00\uACA9", "\uCCB4\uACB0\uC218\uB7C9", "\uCCB4\uACB0\uAE08\uC561"},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"\uC8FC\uBB38\uC2DC\uAC04", "\uAD6C\uBD84", "\uCCB4\uACB0 \uAC00\uACA9", "\uCCB4\uACB0\uC218\uB7C9", "\uCCB4\uACB0\uAE08\uC561"
			}
		));
		unconclusion.add(table_1);
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(410, 0, 270, 210);
		panel_2.add(tabbedPane_1);
		tabbedPane_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		JPanel buy = new JPanel();
		tabbedPane_1.addTab("매수", null, buy, null);
		buy.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uC218 \uB7C9");
		lblNewLabel.setBounds(17, 5, 50, 36);
		buy.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(61, 13, 106, 21);
		buy.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("\uAC00\uACA9");
		label.setBounds(17, 44, 60, 30);
		buy.add(label);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(71, 51, 96, 22);
		buy.add(spinner);
		
		JButton btnNewButton = new JButton("25%");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(4, 84, 63, 23);
		buy.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("50%");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(64, 84, 60, 23);
		buy.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("75%");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_2.setBounds(123, 84, 59, 23);
		buy.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("100%");
		btnNewButton_3.setBounds(181, 84, 84, 23);
		buy.add(btnNewButton_3);
		
		JLabel lblNewLabel_1 = new JLabel("총액");
		lblNewLabel_1.setBounds(14, 117, 52, 15);
		buy.add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(85, 117, 106, 21);
		buy.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("매수");
		btnNewButton_4.setBounds(72, 148, 95, 23);
		buy.add(btnNewButton_4);
		
		JPanel sold = new JPanel();
		tabbedPane_1.addTab("매도", null, sold, null);
		sold.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("수량");
		lblNewLabel_2.setBounds(22, 10, 63, 24);
		sold.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(94, 12, 106, 21);
		sold.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("가격");
		lblNewLabel_3.setBounds(22, 55, 52, 15);
		sold.add(lblNewLabel_3);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(94, 51, 106, 24);
		sold.add(spinner_1);
		
		JButton btnNewButton_6 = new JButton("25%");
		btnNewButton_6.setBounds(0, 80, 63, 23);
		sold.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("50%");
		btnNewButton_7.setBounds(64, 80, 63, 23);
		sold.add(btnNewButton_7);
		
		JButton btnNewButton_5 = new JButton("75%");
		btnNewButton_5.setBounds(128, 80, 63, 23);
		sold.add(btnNewButton_5);
		
		JButton button = new JButton("100%");
		button.setBounds(190, 80, 63, 23);
		sold.add(button);
		
		JLabel label_1 = new JLabel("총액");
		label_1.setBounds(22, 113, 63, 24);
		sold.add(label_1);
		
		textField_3 = new JTextField();
		textField_3.setBounds(108, 115, 106, 21);
		sold.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton_8 = new JButton("매수");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_8.setBounds(81, 147, 95, 23);
		sold.add(btnNewButton_8);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(980, 0, 300, 720);
		contentPane.add(panel_6);

				
		
	}
}
