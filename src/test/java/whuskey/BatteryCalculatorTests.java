package whuskey;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import whuskey.BatteryCalculator;
public class BatteryCalculatorTests{

    BatteryCalculator bc = new BatteryCalculator();

    @Test
    public void calcDayLifeTestSimpleSubtraction(){
        final int start = 100;    
        final int end = 85;
        Assert.assertEquals(bc.calcDayLife(start, end), 15);
        bc = null;
    }

    @Test
    public void calcDayLifeTestHigherEndLife(){
        Assert.assertEquals(bc.calcDayLife(80, 90), -1);
        bc = null;
    }

    @Test
    public void calcDayLifeTestStartInputOutOfRange(){
        Assert.assertEquals(bc.calcDayLife(110, 0), -1);
        bc = null;
    }

    @Test
    public void calcDayLifeTestEndInputOutOfRange(){
        Assert.assertEquals(bc.calcDayLife(100, -10), -1);
        bc = null;
    }

    @Test
    public void calcDayLifeTestMaxInput(){
        Assert.assertEquals(bc.calcDayLife(Integer.MAX_VALUE, 0), -1);
        bc = null;
    } 

    @Test
    public void calcDayLifeTestMinInput(){
        Assert.assertEquals(bc.calcDayLife(100, Integer.MIN_VALUE), -1);
        bc = null;
    } 

    @Test
    public void calcPredictedLifeTestDayLifeMax(){
        Assert.assertEquals(bc.calcPredictedLife(Integer.MAX_VALUE), -1, 0);
        bc = null;
    }

    @Test
    public void calcPredictedLifeTestDayLifeMin(){
        Assert.assertEquals(bc.calcPredictedLife(Integer.MIN_VALUE), -1, 0);
        bc = null;
    }

    @Test
    public void calcDayLifeTestSimpleInput(){
        BatteryCalculator bc = new BatteryCalculator();
        Assert.assertEquals(bc.calcPredictedLife(15), 6.67, 0.01);
    }

    @Test
    public void calcStandardDeviationTest(){
        boolean prevFileExists;
        Double correctValue = 2.28035085; //TODO replace with the correct final standard deviation value
        Double testedValue;
        File old = new File("src\\main\\resources\\BatteryData.txt");
        prevFileExists = old.exists() ? true : false;
        if(prevFileExists == true){
            try{
                Files.move(Paths.get("src\\main\\resources\\BatteryData.txt"), Paths.get("src\\main\\resources\\BatteryDataTemp.txt"));
                Files.move(Paths.get("src\\test\\java\\whuskey\\resources\\StandardDeviationTest.txt"), Paths.get("src\\main\\resources\\BatteryData.txt"));
                testedValue = bc.calcStandardDeviation();
                boolean valuesEqual = (testedValue == correctValue) ? true : false;
                Files.move(Paths.get("src\\main\\resources\\BatteryData.txt"), Paths.get(("src\\test\\java\\whuskey\\resources\\StandardDeviationTest.txt")));
                Files.move(Paths.get("src\\main\\resources\\BatteryDataTemp.txt"), Paths.get("src\\main\\resources\\BatteryData.txt"));
                assertTrue("TestedValue " + testedValue, valuesEqual);
            }
            catch(IOException io){
                System.out.println(io + " occurred.");
            }
        }

    }

}