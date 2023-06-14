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

    private double[] x, y, y1, y2, y3;
    private double fx, ln, pn;

    public Graphic(){
    }

    public void init(double[] x, double[] y, double[] y1,  double[] y2,  double[] y3){
        this.x = x;
        this.y = y;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }

    public void show(){
        XYSeries series1 = new XYSeries("Точная");
        for(int i=0;i< x.length;i++){
            series1.add(x[i], y[i]);
        }

        XYSeries series2 = new XYSeries("Эйлера");
        for(int i=0;i< x.length;i++){
            series2.add(x[i], y1[i]);
        }

        XYSeries series3 = new XYSeries("Рунге");
        for(int i=0;i< x.length;i++){
            series3.add(x[i], y2[i]);
        }

        XYSeries series4 = new XYSeries("Милна");
        for(int i=0;i< x.length;i++){
            series4.add(x[i], y3[i]);
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(series1);
        xyDataset.addSeries(series2);
        xyDataset.addSeries(series3);
        xyDataset.addSeries(series4);

        JFreeChart chart = ChartFactory
                .createXYLineChart("Дифференциальное уравнение", "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint (Color.black);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);

        JFrame frame = new JFrame("Graphic");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(1000,800);
        frame.show();
    }

}

