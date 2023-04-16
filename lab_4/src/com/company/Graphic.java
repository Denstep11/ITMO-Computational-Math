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

public class Graphic {

    private String fname;
    private Function apf;
    private double[][] functionTable;
    //private double a,b;

    public Graphic(){
    }

    public void init(String fname, double[][] functionTable, Function apf){
        this.fname = fname;
        this.functionTable = functionTable;
        this.apf = apf;
    }

    public void show(){
        XYSeries series1 = new XYSeries("Апрохимируемая функция");
        for(int i=0;i< functionTable.length;i++){
            series1.add(functionTable[i][0], functionTable[i][1]);
        }

        XYSeries series2 = new XYSeries(fname);
        for(double i = functionTable[0][0]; i < functionTable[functionTable.length-1][0]+2; i+=0.1){
            series2.add(i, apf.calculate(i));
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(series1);
        xyDataset.addSeries(series2);

        JFreeChart chart = ChartFactory
                .createXYLineChart(fname, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint (Color.black);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible (0, false);
        renderer.setSeriesLinesVisible (1, true);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        JFrame frame = new JFrame("Graphic");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(1000,800);
        frame.show();
    }

}
