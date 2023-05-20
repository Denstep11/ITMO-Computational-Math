package com.company;

import com.company.interfaces.Function;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Graphic {

    private double[] x, y;
    private double fx, ln, pn;

    public Graphic(){
    }

    public void init(double[] x, double[] y, double fx, double ln, double pn){
        this.x = x;
        this.y = y;
        this.fx = fx;
        this.ln = ln;
        this.pn = pn;
    }

    public void show(){
        XYSeries series1 = new XYSeries("функция");
        for(int i=0;i< x.length;i++){
            series1.add(x[i], y[i]);
        }

        XYSeries series2 = new XYSeries("Лагранж");
        series2.add(fx, ln);

        XYSeries series3 = new XYSeries("Гаусс");
        series3.add(fx, pn);

        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(series1);
        xyDataset.addSeries(series2);
        xyDataset.addSeries(series3);

        JFreeChart chart = ChartFactory
                .createXYLineChart("Интерполяция", "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint (Color.black);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible (0, false);
        plot.setRenderer(renderer);

        JFrame frame = new JFrame("Graphic");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(1000,800);
        frame.show();
    }

}
