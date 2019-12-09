package whuskey;

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
}

//TODO change this to no longer use exceptions and instead just returns -1