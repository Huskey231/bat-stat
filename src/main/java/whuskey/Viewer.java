package whuskey;

import java.awt.GraphicsConfiguration;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;

public class Viewer{

    static GraphicsConfiguration gc;
    static JTextField startPercent, endPercent;
    BatteryCalculator batCalc = new BatteryCalculator();    

    public void mainMenu(){
        //Icon stuff sets everything on fire
        //URL iconPath = getClass().getResource("src\\main\\images\\BatteryIcon.png"); //Fire starter
        //ImageIcon icon = new ImageIcon(iconPath); //More fire
        JFrame frame = new JFrame(gc);
        //frame.setIconImage(icon.getImage()); //Last of the fire
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
                    }
                    catch(NumberFormatException nf){
                        JOptionPane.showMessageDialog(null, "Must enter a number in both fields to continue.", "Illegal Argument", JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
            }
        });
        frame.getContentPane().add(submit);
        frame.setVisible(true);
    }

    private void calculator(int start, int end){
        int dayLife = 0;
        dayLife = batCalc.calcDayLife(start, end);
        if(dayLife == -1){
            return;
        }
        else{
            double predictedLife = batCalc.calcPredictedLife(dayLife);
            JOptionPane.showMessageDialog(null, dayLife+"% consumed today.\nPredicted total battery life " + new DecimalFormat("#.##").format(predictedLife) + " days.");
            try{
                DataRecorder.recordNewEntry(start, end);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e + " occurred.");
            }

        }
    }

    //createIcon method needed
}