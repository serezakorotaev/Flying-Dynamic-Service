package ru.bmstu.dynamic.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.MathContext;


@Service
public class GraphService {
    private final DynamicService dynamicService;

    public GraphService(DynamicService dynamicService) {
        this.dynamicService = dynamicService;
    }

    public void getAllDraws() {
        drawGraphTemperature();
        drawGraphAccelerationOfGravityOnHigh();
        drawGraphPressure();
        drawGraphDensity();
        drawGraphVelocityOfSound();
    }

    public void baseDraw(XYSeries series , String title, String yValue) {
        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory
                .createXYLineChart(title , "H, м" , yValue ,
                        xyDataset ,
                        PlotOrientation.VERTICAL ,
                        true , true , true);
        JFrame frame =
                new JFrame("MinimalStaticChart");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(400 , 300);
        frame.show();
    }

    private void drawGraphVelocityOfSound() {
        XYSeries series = new XYSeries("a(H)");
        for (double i = -2000; i <= 80000; i += 500) {
            series.add(i ,
                    BigDecimal.valueOf(20.0468 * Math.sqrt(
                            dynamicService.getTemperature(
                                    dynamicService.getGeopotencialHigh(i)))).round(new MathContext(5))
            );
        }
        baseDraw(series, "VelocityOfSound", "a, м/с");
    }


    private void drawGraphDensity() {
        XYSeries series = new XYSeries("p(H)");
        for (double i = -2000; i <= 80000; i += 500) {
            series.add(i , dynamicService.getDensity(i));
        }
        baseDraw(series , "Density", "p, кг/м^3");
    }

    private void drawGraphPressure() {
        XYSeries series = new XYSeries("P(H)");
        for (double i = -2000; i <= 80000; i += 500) {
            series.add(i ,
                    dynamicService.getPressure(
                            dynamicService.getGeopotencialHigh(i) ,
                            dynamicService.getTemperature(
                                    dynamicService.getGeopotencialHigh(i)
                            )
                    )
            );
        }
        baseDraw(series , "Pressure", "P, Па");
    }

    private void drawGraphAccelerationOfGravityOnHigh() {
        XYSeries series = new XYSeries("g(H)");
        for (double i = -2000; i <= 80000; i += 500) {
            series.add(i ,
                    dynamicService.getAccelerationOfGravityOnHigh(
                            dynamicService.getGeopotencialHigh(i)
                    )
            );
        }
        baseDraw(series , "AccelerationOfGravityOnHigh", "g, м,с^2");
    }

    public void drawGraphTemperature() {
        XYSeries series = new XYSeries("T(H)");
        for (double i = -2000; i <= 80000; i += 500) {
            series.add(i ,
                    dynamicService.getTemperature(
                            dynamicService.getGeopotencialHigh(i)
                    )
            );
        }
        baseDraw(series , "Temperature", "T, K");
    }
}
