package com.company;

import com.company.interfaces.Function;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Graphic {

    private String fname;
    private Function f;
    private double a,b;

    public Graphic(){
    }

    public void init(double a, double b, String fname, Function f){
        this.a = a;
        this.b = b;
        this.fname = fname;
        this.f = f;
    }

    public void show(){
        XYSeries series = new XYSeries(fname);
        for(double i = a-3; i < b+3; i+=0.1){
            series.add(i, f.calculate(i));
        }


        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart("y = "+fname, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint (Color.black);

        JFrame frame = new JFrame("Graphic");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(1000,800);
        frame.show();
    }

}
