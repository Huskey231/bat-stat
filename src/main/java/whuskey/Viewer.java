package whuskey;

import java.awt.GraphicsConfiguration;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;

public class Viewer{

    static GraphicsConfiguration gc;
    static JTextField startPercent, endPercent;
    BatteryCalculator batCalc = new BatteryCalculator();    
    public static void main(String[] args){
        Viewer view = new Viewer();
        view.mainMenu();
    }

    private void mainMenu(){
        JFrame frame = new JFrame(gc);
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
            public void actionPerformed(ActionEvent e){
                if(startPercent.getText().equals("") || endPercent.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Both fields must contain a value!", "Empty Field", JOptionPane.WARNING_MESSAGE);
                }
                else{
                    try{
                        int startLife = Integer.parseInt(startPercent.getText());
                        int endLife = Integer.parseInt(endPercent.getText());
                        calculator(startLife, endLife);
                        try{
                            DataRecorder.recordNewEntry(startLife, endLife);
                        }
                        catch(Exception exception){
                            JOptionPane.showMessageDialog(null, exception+" occurred.");
                        }
                    }
                    catch(NumberFormatException nf){
                        JOptionPane.showMessageDialog(null, "Must enter a number in both fields to continue.", "Illegal Argument Exception", JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
            }
        });
        //TODO Change it so that info is passed to the calculator method that then verifies if data is correct. Also have it so that battery calculator only calls predicted life upon verifying data
        frame.getContentPane().add(submit);

        frame.setVisible(true);
    }

    private void calculator(int start, int end){
        int dayLife = 0;
        dayLife = batCalc.calcDayLife(start, end);
        double predictedLife = batCalc.calcPredictedLife(dayLife);
        JOptionPane.showMessageDialog(null, dayLife+"% consumed today.\nPredicted total battery life " + new DecimalFormat("#.##").format(predictedLife) + " days.");

    }
}