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
        // TODO Logic for outlier button
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

    private void findOutliers(double mean){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\BatteryData.txt"));
            HashMap<Integer, Integer> differenceValues = new HashMap<Integer, Integer>();
            String line;
            String temp[];
            while((line = reader.readLine()) != null){

            }
        }
        catch(Exception e){

        }
    }
}