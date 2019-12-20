package whuskey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;

import org.jfree.data.xy.XYSeries;

class ChartPlotter{


    File df = new File("src\\main\\resources\\BatteryData.txt");
    LinkedList<String> fileData = new LinkedList<String>();

    public void createChart(){
        listFiller();
        XYSeries highEnd = createHighEnd();
        XYSeries lowEnd = createLowEnd();
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