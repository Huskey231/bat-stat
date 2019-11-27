package whuskey;

import javax.swing.JOptionPane;

public class BatteryCalculator {

    public int calcDayLife(int startLife, int endLife){
        try{
            if(startLife <= endLife){
                JOptionPane.showMessageDialog(null, "Battery life must have decreased during the day!");
                throw new IllegalArgumentException("Battery life must have decreased during the day!");
            }
            else if(startLife < 0 || endLife < 0){
                JOptionPane.showMessageDialog(null, "Battery life cannot be negative!", "Negative Value Error", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Battery life cannot be negative!");
            }
            else if(startLife > 100 || endLife > 100){
                JOptionPane.showMessageDialog(null, "Battery life cannot be over 100%!", "Negative Value Error", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Battery life cannot be over 100%!");
            }
            else{
                return startLife - endLife;
            }
        }
        catch(Exception e){
            //JOptionPane.showMessageDialog(null, e+"error occurred.");
            return 0;
        }
    }

    public double calcPredictedLife(int dayLife){
        try{
            if(dayLife <= 0){
                JOptionPane.showMessageDialog(null, "Battery life can not increase during the day.");
                return -1;
            }
            else{
                return 100.0/dayLife;
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"error occurred.");
            return 0;
        }
    }
}