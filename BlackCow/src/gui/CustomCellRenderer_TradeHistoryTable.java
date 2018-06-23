package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class CustomCellRenderer_TradeHistoryTable extends DefaultTableCellRenderer
{
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component c = super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
	
		Color color;
		
		if (table.getValueAt(row, 3) == "¸Å¼ö")
			color = Color.RED;
		else
			color = Color.BLUE;
		
		switch (column)
		{
		case 0:
			c.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
			setHorizontalAlignment(SwingConstants.CENTER);
			break;
			
		case 1:
			c.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
			setHorizontalAlignment(SwingConstants.CENTER);
			break;
			
		case 2:
			c.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
			setHorizontalAlignment(SwingConstants.CENTER);
			break;
			
		case 3:
			c.setForeground(color);
			c.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
			setHorizontalAlignment(SwingConstants.CENTER);
			break;
			
		case 4:
			c.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
			setHorizontalAlignment(SwingConstants.RIGHT);
			break;
			
		case 5:
			c.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
			setHorizontalAlignment(SwingConstants.RIGHT);
			break;
			
		case 6:
			c.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
			setHorizontalAlignment(SwingConstants.RIGHT);
			break;
			
		case 7:
			if (table.getValueAt(row, 7) == "Ãë¼Ò")
				c.setBackground(Color.LIGHT_GRAY);
			c.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
			setHorizontalAlignment(SwingConstants.CENTER);
			break;
		}

		return c;
	}
}
