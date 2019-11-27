package whuskey;

import javax.swing.JOptionPane;
import exceptions.NumberOutOfRangeException;

public class BatteryCalculator {

    public int calcDayLife(int startLife, int endLife){
        try{
            if(startLife <= endLife){
                throw new NumberFormatException("Battery life must have decreased during the day!");
            }
            else if(startLife < 0 || endLife < 0){
                throw new NumberOutOfRangeException("Battery life cannot be negative!");
            }
            else if(startLife > 100 || endLife > 100){
                throw new NumberOutOfRangeException("Battery life cannot be over 100%!");
            }
            else{
                return startLife - endLife;
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;
        }
    }

    public double calcPredictedLife(int dayLife){
        try{
            if(dayLife <= 0){
                throw new IllegalArgumentException("Invalid day consumption rate received.");
            }
            else if(dayLife > 100){
                throw new IllegalArgumentException("Invalid day consumption rate received.");
            }
            else{
                return 100.0/dayLife;
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
            return -1;
        }
    }
}