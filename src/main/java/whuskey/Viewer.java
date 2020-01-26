package whuskey;

import java.awt.GraphicsConfiguration;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import org.jfree.ui.RefineryUtilities;

public class Viewer {

    static GraphicsConfiguration gc;
    static JTextField startPercent, endPercent;
    BatteryCalculator batCalc = new BatteryCalculator();
    ChartPlotter chartPlot = new ChartPlotter();

    public void mainMenu() {
        ImageIcon icon = new ImageIcon("src\\main\\images\\BatteryIcon.png");
        JFrame frame = new JFrame(gc);
        frame.setIconImage(icon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Watch Battery Tracker");
        frame.setSize(400, 150);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new FlowLayout());
        startPercent = new JTextField("", 4);
        endPercent = new JTextField("", 4);
        frame.getContentPane().add(new JLabel("Start Percentage"));
        frame.getContentPane().add(startPercent);
        frame.getContentPane().add(new JLabel("End Percentage"));
        frame.getContentPane().add(endPercent);
        JButton submit = new JButton("Save");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (startPercent.getText().equals("") || endPercent.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Both fields must contain a value!", "Empty Field",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        int startLife = Integer.parseInt(startPercent.getText());
                        int endLife = Integer.parseInt(endPercent.getText());
                        calculator(startLife, endLife);
                    } catch (NumberFormatException nf) {
                        JOptionPane.showMessageDialog(null, "Must enter a number in both fields to continue.",
                                "Illegal Argument", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
        JButton chart = new JButton("Chart");
        chart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chartPlot.pack();
                RefineryUtilities.centerFrameOnScreen(chartPlot);
                chartPlot.setVisible(true);
            }
        });
        JButton identifyOutliers = new JButton("Identify Outliers");
        identifyOutliers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                outlierMessageBuilder();
        }});
        frame.getContentPane().add(submit);
        frame.getContentPane().add(chart);
        frame.getContentPane().add(identifyOutliers);
        frame.setVisible(true);
    }

    private void calculator(int start, int end) {
        int dayLife = 0;
        dayLife = batCalc.calcDayLife(start, end);
        if (dayLife == -1) {
            return;
        } else {
            double predictedLife = batCalc.calcPredictedLife(dayLife);
            JOptionPane.showMessageDialog(null, dayLife + "% consumed today.\nPredicted total battery life "
                    + new DecimalFormat("#.##").format(predictedLife) + " days.");
            try {
                DataRecorder.recordNewEntry(start, end);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e + " occurred.");
            }

        }
    }

    private void outlierMessageBuilder(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\BatteryData.txt"));
            HashMap<Integer, Integer> data = new HashMap<Integer, Integer>();
            String datum;
            LinkedList<Double> consumptionValues = new LinkedList<Double>();
            String[] temp;
            int day = 1;
            int weeks;
            LinkedList<Integer> outliersHigh = new LinkedList<>();
            LinkedList<Integer> outliersLow = new LinkedList<>();
            while((datum = br.readLine()) != null){
                temp = datum.split(" ");
                data.put(day, Integer.parseInt(temp[0].substring(0, temp[0].length()-1)));
                consumptionValues.add(Double.parseDouble(temp[0].substring(0, temp[0].length()-1)));
                day++;
            }
            BatteryCalculator bc = new BatteryCalculator();
            double mean = bc.meanFinder(consumptionValues); 
            double standardDev = bc.calcStandardDeviation();
            for(Map.Entry<Integer, Integer> entry : data.entrySet()){
                if(entry.getValue() > mean+standardDev){
                    outliersHigh.add(entry.getKey());
                }
                else if(entry.getValue() < mean-standardDev){
                    outliersLow.add(entry.getKey());
                }
            }
            StringBuilder displayMessage = new StringBuilder(); //Want each line to be about 100 characters long maybe more
            if(outliersHigh.size() == 0){
                displayMessage.append("No abnormally high battery use was found. ");
            }
            else if(outliersHigh.size() == 1){
                weeks = (Integer) outliersHigh.get(0) / 7;
                if(weeks > 1){
                    displayMessage.append("Battery use was abnormally high " + weeks + " weeks and " + outliersHigh.get(0)%7 + " days ago with " + data.get(outliersHigh.get(0)) + "% consumed.");
                }
                else if(weeks == 1){
                    displayMessage.append("Battery use was abnormally high " + weeks + " week and " + outliersHigh.get(0)%7 + " days ago with " + data.get(outliersHigh.get(0)) + "% consumed.");
                }
                else{
                    displayMessage.append("Battery use was abnormally high " + outliersHigh.get(0) + " days ago with " + data.get(outliersHigh.get(0)) + "% consumed.");
                }
            }
            else{
                Integer finalKey = outliersHigh.removeLast();
                displayMessage.append("Battery use was abnormally high ");
                for (Integer outlierKey : outliersHigh) {
                    weeks = outlierKey / 7;
                    if(weeks > 1){
                        displayMessage.append(weeks + " weeks and " + outlierKey%7 + " days ago with " + data.get(outlierKey) + "% consumed, ");
                    }
                    else if(weeks > 0){
                        displayMessage.append(weeks + " week and " + outlierKey%7 + " days ago with " + data.get(outlierKey) + "% consumed, ");
                    }
                    else{
                        displayMessage.append(outliersHigh.get(0) + " days ago with " + data.get(outlierKey) + "% consumed, ");
                    }
                }
                displayMessage.deleteCharAt(displayMessage.length()-2);
                weeks = finalKey / 7;
                if(weeks > 1){
                    displayMessage.append("and " + weeks + " weeks and " + finalKey%7 + " days ago with " + data.get(finalKey) + "% consumed.");
                }
                else if(weeks > 0){
                    displayMessage.append("and " + weeks + " week and " + finalKey%7 + " days ago with " + data.get(finalKey) + "% consumed.");
                }
                else{
                    displayMessage.append("and " + finalKey + " days ago with " + data.get(finalKey) + "% consumed.");
                }
            }
            if(outliersLow.size() == 0){
                displayMessage.append("No abnormally low battery use was found.");
            }
            else if(outliersLow.size() == 1){
                weeks = (Integer) outliersLow.get(0) / 7;
                if(weeks > 1){
                    displayMessage.append(" Battery use was abnormally low " + weeks + " weeks and " + outliersLow.get(0)%7 + " days ago with " + data.get(outliersLow.get(0)) + "% consumed.");
                }
                else if(weeks > 0){
                    displayMessage.append(" Battery use was abnormally low " + weeks + " week and " + outliersLow.get(0)%7 + " days ago with " + data.get(outliersLow.get(0)) + "% consumed.");
                }
                else{
                    displayMessage.append(" Battery use was abnormally low " + outliersLow.get(0) + " days ago with " + data.get(outliersLow.get(0)) + "% consumed.");
                }
            }
            else{
                Integer finalKey = outliersLow.removeLast();
                displayMessage.append(" Battery use was abnormally low ");
                for (Integer outlierKey : outliersLow) {
                    weeks = outlierKey / 7;
                    if(weeks > 1){
                        displayMessage.append(weeks + " weeks and " + outlierKey%7 + " days ago with " + data.get(outlierKey) + "% consumed, ");
                    }
                    else if(weeks > 0){
                        displayMessage.append(weeks + " week and " + outlierKey%7 + " days ago with " + data.get(outlierKey) + "% consumed, ");
                    }
                    else{
                        displayMessage.append(outlierKey + " days ago with " + data.get(outlierKey) + "% consumed, ");
                    }
                }
                displayMessage.deleteCharAt(displayMessage.length()-2);
                weeks = finalKey / 7;
                if(weeks > 1){
                    displayMessage.append(" and " + weeks + " weeks and " + finalKey%7 + " days ago with " + data.get(finalKey) + "% consumed.");
                }
                if(weeks > 0){
                    displayMessage.append(" and " + weeks + " week and " + finalKey%7 + " days ago with " + data.get(finalKey) + "% consumed.");
                }
                else{
                    displayMessage.append(" and " + finalKey + " days ago with " + data.get(finalKey) + "% consumed.");
                }
            }
            int pointer = 100;
            while(pointer < displayMessage.length()){
                if(displayMessage.charAt(pointer) == ' '){
                    displayMessage.deleteCharAt(pointer);
                    displayMessage.insert(pointer, "\n");
                    pointer += 100 - (pointer % 100);
                }
                else{
                    pointer++;
                }
            }
            br.close();
            JOptionPane.showMessageDialog(null, displayMessage.toString(), "Abnormal Values", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "File reading error occurred.", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}