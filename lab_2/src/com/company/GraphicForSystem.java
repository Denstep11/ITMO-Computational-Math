package com.company;

import com.company.interfaces.Function;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GraphicForSystem {
    private String fname = "system";
    private FSystem f;
    private double a,b;

    public void init(double a, double b, FSystem f){
        this.a = a;
        this.b = b;
        this.f = f;
    }

    public void show(){


        XYSeries series1 = new XYSeries("f1", false);
        for(double x = -10; x < 10; x+=0.001)
            for(double y = -10; y < 10; y+=0.001){
                if(Math.round(f.getF1().calculate(x,y)*1000)==0){
                    series1.add(x, y);
                }
            }

        XYSeries series2 = new XYSeries("f2", false);
        for(double x = -10; x < 10; x+=0.001)
            for(double y = -10; y < 10; y+=0.001){
                if(Math.round(f.getF2().calculate(x,y)*1000)==0){
                    series2.add(x, y);
                }
            }


        XYSeriesCollection xyDataset1 = new XYSeriesCollection();
            xyDataset1.addSeries(series1);
            xyDataset1.addSeries(series2);

        JFreeChart chart = ChartFactory
                .createXYLineChart("Graph", "x", "y",
                        xyDataset1,
                        PlotOrientation.VERTICAL,
                        true, true, true);


        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint (Color.black);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible (0, false);
        renderer.setSeriesLinesVisible (1, false);
        plot.setRenderer(renderer);

        JFrame frame = new JFrame("Graphic");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(1000,800);
        frame.show();
    }
}
