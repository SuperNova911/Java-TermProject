package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;

public class CustomCandlestickRenderer extends CandlestickRenderer
{
	public CustomCandlestickRenderer()
	{
		setDrawVolume(true);
	}
	
	@Override
	public Paint getItemPaint(int row, int column)
	{
		return Color.BLACK;
	}

	@Override
	public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info,
			XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item,
			CrosshairState crosshairState, int pass)
	{
		plot.setRangeGridlineStroke(new BasicStroke(1));
		plot.setDomainGridlineStroke(new BasicStroke(1));
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		plot.setDomainGridlinePaint(Color.LIGHT_GRAY);

		setUpPaint(Color.RED);
		setDownPaint(Color.BLUE);

		// call parent method
		super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis, dataset, series, item, crosshairState,
				pass);
	}
}
