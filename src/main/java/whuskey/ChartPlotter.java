package whuskey;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.TickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

class ChartPlotter{


    File df = new File("src\\main\\resources\\BatteryData.txt");
    LinkedList<String> fileData = new LinkedList<String>();

    public void createChart(){
        listFiller();
        XYSeries highEnd = createHighEnd();
        XYSeries lowEnd = createLowEnd();
        XYSeriesCollection rangeSet = new XYSeriesCollection();
        rangeSet.addSeries(highEnd);
        rangeSet.addSeries(lowEnd);

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
        XYSeries series = new XYSeries("Start Life");
        int endValue;
        int day = 1;
        for(String data : fileData){
            String[] temp =data.split(" ");
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

    /*May need to change the XYSeriesCollection to an XYDataSet (should also then cast the data passed in)*/
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
        final DateAxis domainAxis = new DateAxis("Day");
        domainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(30.0);
        plot.setDomainAxis(domainAxis);
        plot.setForegroundAlpha(0.5f);

        

        return chart;
    }


    /*
    DefaultCategoryDataset createDataSet(){
        File df = new File("src\\main\\resources\\BatteryData.txt");
        BufferedReader fr = new BufferedReader(new FileReader(df));
        DefaultCategoryDataset chartData = new DefaultCategoryDataset();
        LinkedList<String> dataSet = new LinkedList<String>();
        String line;
        int day = 1;
        int value;
        
        while((line = fr.readLine()) != null){
            dataSet.add(line);
        }
        for(String data : dataSet){
            String[] temp = dataSet.peek().split(" ");
            value = Integer.parseInt(temp[1].substring(0, temp[1].length()));
            //TODO take the end value and add the difference amount to it, add the high value per date then the low value
            chartData.addValue(value, day, null);


            day++;
        }

        return;
    }

    */
}