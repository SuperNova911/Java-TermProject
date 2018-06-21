package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomCellRenderer_MyCoinTable extends DefaultTableCellRenderer
{
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component c = super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
		
		String str;
		double num;
		Color color;
		
		switch (column)
		{
		case 0:
			c.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
			break;
			
		case 1:
			c.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
			setHorizontalAlignment(SwingConstants.RIGHT);
			break;
			
		case 2:
			c.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
			setHorizontalAlignment(SwingConstants.RIGHT);
			break;
			
		case 3:
			c.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
			setHorizontalAlignment(SwingConstants.RIGHT);
			break;
		}

		return c;
	}
}
