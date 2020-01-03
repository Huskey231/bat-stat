package whuskey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class BatteryCalculator {

    public int calcDayLife(int startLife, int endLife){
        
        if(startLife <= endLife){
            JOptionPane.showMessageDialog(null, "Battery life must have increased during the day!\nPlease re-enter values.", "Battery Consumption Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        else if(startLife < 0 || endLife < 0){
            JOptionPane.showMessageDialog(null, "Battery life cannot be negative!\nPlease re-enter values.", "Invalid Battery Percent Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        else if(startLife > 100 || endLife > 100){
            JOptionPane.showMessageDialog(null, "Battery life cannot be over 100%!\nPlease re-enter values.", "Invalid Battery Percent Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        else{
            return startLife - endLife;
        }   
    }

    public double calcPredictedLife(int dayLife){
        
        if(dayLife <= 0){
            JOptionPane.showMessageDialog(null, "Invalid day consumption rate received.", "Data error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        else if(dayLife > 100){
            JOptionPane.showMessageDialog(null, "Invalid day consumption rate received.", "Data Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        else{
            return 100.0/dayLife;
        }
    }

    public double calcStandardDeviation(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\BatteryData.txt"));
            String data;
            double mean;
            LinkedList<Double> values = new LinkedList<Double>();
            String[] temp;
            int value;
            int index = 0;
            while((data = reader.readLine()) != null){
                temp = data.split(" ");
                value = Integer.parseInt(temp[0].substring(0, temp[0].length()-1));
                values.add((double) value);
            }
            mean = meanFinder(values);
            for(Double datum : values){
                datum = datum - mean;
                datum = datum * datum;
                values.set(index, datum);
                index++;
            }
            mean = meanFinder(values);
            reader.close();
            return (double) Math.sqrt(mean);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data file not found. If data has been entered and\nyou are seeing this message an error has occurred.");
        }
        return 0.0;
    }

    public double meanFinder(LinkedList<Double> values){
        double mean;
        double sum = 0.0;
        for(Double datum : values){
            sum += datum;
        }
        mean = sum / values.size();
        return mean;
    }
}