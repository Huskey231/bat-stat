package whuskey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

class ChartPlotter extends ApplicationFrame{

    File df = new File("src\\main\\resources\\BatteryData.txt");
    LinkedList<String> fileData = new LinkedList<String>();

    public ChartPlotter(){
        
        super("Battery Statistics");

        listFiller();
        XYSeries highEnd = createHighEnd();
        XYSeries lowEnd = createLowEnd();
        XYSeriesCollection dataSet = new XYSeriesCollection();
        dataSet.addSeries(highEnd);
        dataSet.addSeries(lowEnd);

        final JFreeChart chart = chartRenderer(dataSet);        
        final ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new java.awt.Dimension(800, 400));
        setContentPane(panel);
    }

    private XYSeries createHighEnd(){
        XYSeries series = new XYSeries("Start Life");
        int endValue;
        int difference;
        int day = 1;
        for(String data : fileData){
            String[] temp = data.split(" ");
            endValue = Integer.parseInt(temp[1].substring(0, temp[1].length()-1));
            difference = Integer.parseInt(temp[0].substring(0, temp[0].length()-1));
            series.add(day, endValue + difference);
            day++;
        }
        return series;
    }

    private XYSeries createLowEnd(){
        XYSeries series = new XYSeries("End Life");
        int endValue;
        int day = 1;
        for(String data : fileData){
            String[] temp = data.split(" ");
            endValue = Integer.parseInt(temp[1].substring(0, temp[1].length()-1));
            series.add(day, endValue);
            day++;
        }
        return series;
    }

    private void listFiller(){
        try{
            String line;
            BufferedReader fr = new BufferedReader(new FileReader(df));
            while((line = fr.readLine()) != null){
                fileData.add(line);
            }
            fr.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private JFreeChart chartRenderer(XYSeriesCollection data){
        JFreeChart chart = ChartFactory.createXYBarChart("Battery Usage", "Day", false, "Percentage", data);
        chart.setBackgroundPaint(Color.white);
        XYDifferenceRenderer xyRenderer = new XYDifferenceRenderer(Color.yellow, Color.yellow, false);
        xyRenderer.setSeriesStroke(0, new BasicStroke(2.0f));
        xyRenderer.setSeriesStroke(1, new BasicStroke(2.0f));
        xyRenderer.setSeriesPaint(0, Color.green);
        xyRenderer.setSeriesPaint(1, Color.red);
        final XYPlot plot = chart.getXYPlot();
        plot.setRenderer(xyRenderer);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        final ValueAxis domainAxis = plot.getDomainAxis();
        domainAxis.setAutoRange(false);
        domainAxis.setRange(0, 30);
        plot.setDomainAxis(domainAxis);
        plot.setForegroundAlpha(0.5f);
        final ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setAutoRange(false);
        rangeAxis.setRange(0, 100);
        plot.setRangeAxis(rangeAxis);
        
        return chart;
    }
}   