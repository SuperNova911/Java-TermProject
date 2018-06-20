package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class CustomCellRenderer_OrderTable extends DefaultTableCellRenderer
{
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component c = super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
		
		String str = table.getValueAt(row, 1).toString().replaceAll("%", "").replaceAll(",", "");
		double num = Double.parseDouble(str);
		Color textColor, cellColor;
		
		if (row < 10)
			cellColor = new Color(224, 246, 255);
		else
			cellColor = new Color(255, 225, 236);
		textColor = getColor(num);
		
		switch (column)
		{
		case 0:
			c.setForeground(textColor);
			c.setBackground(cellColor);
			c.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
			setHorizontalAlignment(SwingConstants.RIGHT);
			break;

		case 1:
			c.setForeground(textColor);
			c.setBackground(cellColor);
			c.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 12));
			setHorizontalAlignment(SwingConstants.RIGHT);
			break;

		case 2:
			c.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
			break;

		case 3:
			c.setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 12));
			setHorizontalAlignment(SwingConstants.CENTER);
			break;
		}
		
		return c;
	}
	
	public Color getColor(double num)
	{
		if (num > 0)
		{
			return Color.RED;
		}
		else if (num < 0)
		{
			return Color.BLUE;
		}
		else
		{
			return Color.BLACK;
		}
	}
}
