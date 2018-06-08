import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class TestWB extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JTextField textField;
	private JTable table_2;
	private JTextField textField_1;
	private JTable table_3;
	private JTextField textField_2;
	private JTable table_4;
	private JTextField textField_3;
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
		setSize(new Dimension(1280, 720));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(300, 451, 681, 230);
		panel.setSize(680,230);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 10, 450, 230);
		panel.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("미 체결 주문", null, panel_1, null);
		panel_1.setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"\uC8FC\uBB38 \uC2DC\uAC04", "\uAD6C\uBD84", "\uCCB4\uACB0 \uAC00\uACA9", "\uCCB4\uACB0 \uC218\uB7C9", "\uCCB4\uACB0\uAE08\uC561"},
				{null, null, null, null, null},
				{null, null, null, null, null},
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
				"New column", "New column", "New column", "New column", "New column"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(85);
		table.getColumnModel().getColumn(1).setPreferredWidth(65);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBounds(12, 10, 421, 181);
		panel_1.add(table);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("체결 완료 내역", null, panel_2, null);
		panel_2.setLayout(null);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"\uC8FC\uBB38 \uC2DC\uAC04", "\uAD6C\uBD84", "\uCCB4\uACB0 \uAC00\uACA9", "\uCCB4\uACB0 \uC218\uB7C9", "\uCCB4\uACB0\uAE08\uC561"},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
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
				"New column", "New column", "New column", "New column", "New column"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(85);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(65);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(80);
		table_1.setColumnSelectionAllowed(true);
		table_1.setCellSelectionEnabled(true);
		table_1.setBounds(12, 10, 421, 181);
		panel_2.add(table_1);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(457, 10, 223, 220);
		panel.add(tabbedPane_1);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("매수", null, panel_3, null);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uC218\uB7C9");
		lblNewLabel.setBounds(12, 15, 57, 15);
		panel_3.add(lblNewLabel);
		
		JLabel label = new JLabel("\uAC00\uACA9");
		label.setBounds(12, 51, 57, 15);
		panel_3.add(label);
		
		JLabel label_1 = new JLabel("\uCD1D\uC561");
		label_1.setBounds(12, 121, 57, 15);
		panel_3.add(label_1);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(47, 15, 121, 21);
		panel_3.add(textPane);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(47, 121, 121, 21);
		panel_3.add(textPane_2);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(47, 51, 121, 19);
		panel_3.add(spinner);
		
		JButton button = new JButton("25%");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button.setBounds(0, 76, 57, 23);
		panel_3.add(button);
		
		JButton button_1 = new JButton("50%");
		button_1.setBounds(50, 76, 57, 23);
		panel_3.add(button_1);
		
		JButton button_2 = new JButton("75%");
		button_2.setBounds(100, 76, 57, 23);
		panel_3.add(button_2);
		
		JButton button_3 = new JButton("100%");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_3.setBounds(150, 76, 68, 23);
		panel_3.add(button_3);
		
		JButton button_4 = new JButton("\uB9E4\uC218");
		button_4.setBounds(12, 146, 183, 23);
		panel_3.add(button_4);
		
		JLabel label_8 = new JLabel("\uC6D0");
		label_8.setBounds(180, 121, 57, 15);
		panel_3.add(label_8);
		
		JLabel label_9 = new JLabel("\uC6D0");
		label_9.setBounds(180, 51, 57, 15);
		panel_3.add(label_9);
		
		JLabel label_10 = new JLabel("\uAC1C");
		label_10.setBounds(180, 15, 57, 15);
		panel_3.add(label_10);
		
		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("매도", null, panel_4, null);
		panel_4.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBounds(0, 0, 218, 191);
		panel_4.add(panel_5);
		
		JLabel label_2 = new JLabel("\uC218\uB7C9");
		label_2.setBounds(12, 15, 57, 15);
		panel_5.add(label_2);
		
		JLabel label_3 = new JLabel("\uAC00\uACA9");
		label_3.setBounds(12, 51, 57, 15);
		panel_5.add(label_3);
		
		JLabel label_4 = new JLabel("\uCD1D\uC561");
		label_4.setBounds(12, 121, 57, 15);
		panel_5.add(label_4);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(47, 15, 121, 21);
		panel_5.add(textPane_1);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setBounds(47, 121, 121, 21);
		panel_5.add(textPane_3);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(47, 51, 121, 19);
		panel_5.add(spinner_1);
		
		JButton button_5 = new JButton("25%");
		button_5.setBounds(0, 76, 57, 23);
		panel_5.add(button_5);
		
		JButton button_6 = new JButton("50%");
		button_6.setBounds(50, 76, 57, 23);
		panel_5.add(button_6);
		
		JButton button_7 = new JButton("75%");
		button_7.setBounds(100, 76, 57, 23);
		panel_5.add(button_7);
		
		JButton button_8 = new JButton("100%");
		button_8.setBounds(150, 76, 68, 23);
		panel_5.add(button_8);
		
		JButton button_9 = new JButton("\uB9E4\uB3C4");
		button_9.setBounds(12, 146, 183, 23);
		panel_5.add(button_9);
		
		JLabel label_5 = new JLabel("\uC6D0");
		label_5.setBounds(180, 121, 57, 15);
		panel_5.add(label_5);
		
		JLabel label_6 = new JLabel("\uC6D0");
		label_6.setBounds(180, 51, 57, 15);
		panel_5.add(label_6);
		
		JLabel label_7 = new JLabel("\uAC1C");
		label_7.setBounds(180, 15, 57, 15);
		panel_5.add(label_7);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(0, 10, 299, 671);
		contentPane.add(panel_6);
		panel_6.setLayout(null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(12, 10, 275, 651);
		panel_6.add(tabbedPane_2);
		
		JPanel panel_9 = new JPanel();
		tabbedPane_2.addTab("KRW", null, panel_9, null);
		panel_9.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 10, 171, 21);
		panel_9.add(textField);
		textField.setColumns(10);
		
		JButton button_10 = new JButton("\uAC80\uC0C9");
		button_10.setBounds(195, 9, 69, 23);
		panel_9.add(button_10);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
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
		table_2.getColumnModel().getColumn(0).setPreferredWidth(100);
		table_2.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_2.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_2.setBounds(12, 41, 246, 571);
		panel_9.add(table_2);
		
		JPanel panel_10 = new JPanel();
		tabbedPane_2.addTab("BTC", null, panel_10, null);
		panel_10.setLayout(null);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(12, 11, 183, 21);
		panel_10.add(textField_1);
		
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
		table_3.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_3.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_3.setBounds(12, 42, 246, 571);
		panel_10.add(table_3);
		
		JButton button_11 = new JButton("\uAC80\uC0C9");
		button_11.setBounds(207, 10, 57, 23);
		panel_10.add(button_11);
		
		JPanel panel_11 = new JPanel();
		tabbedPane_2.addTab("ETH", null, panel_11, null);
		panel_11.setLayout(null);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(12, 11, 183, 21);
		panel_11.add(textField_2);
		
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
		table_4.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_4.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_4.setBounds(12, 42, 246, 571);
		panel_11.add(table_4);
		
		JButton button_12 = new JButton("\uAC80\uC0C9");
		button_12.setBounds(207, 10, 57, 23);
		panel_11.add(button_12);
		
		JPanel panel_12 = new JPanel();
		tabbedPane_2.addTab("보유 코인", null, panel_12, null);
		panel_12.setLayout(null);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(12, 11, 183, 21);
		panel_12.add(textField_3);
		
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
		table_5.getColumnModel().getColumn(1).setPreferredWidth(90);
		table_5.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_5.setBounds(12, 42, 246, 571);
		panel_12.add(table_5);
		
		JButton button_13 = new JButton("\uAC80\uC0C9");
		button_13.setBounds(207, 10, 57, 23);
		panel_12.add(button_13);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(980, 10, 299, 671);
		contentPane.add(panel_7);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBounds(300, 10, 680, 450);
		contentPane.add(panel_8);
		panel_8.setLayout(null);
	}
}
