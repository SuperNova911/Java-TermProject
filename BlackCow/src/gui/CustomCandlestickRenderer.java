package gui;

import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;

public class CustomCandlestickRenderer extends CandlestickRenderer
{
	@Override
	public Paint getItemPaint(int row, int column)
	{        
		return Color.BLACK;
	}
}
