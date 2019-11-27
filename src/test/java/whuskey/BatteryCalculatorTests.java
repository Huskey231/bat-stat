package whuskey;
import org.junit.Test;
import org.junit.Assert;

import whuskey.BatteryCalculator;
public class BatteryCalculatorTests{

    @Test
    public void calcDayLifeTestSimpleSubtraction(){
        BatteryCalculator bc = new BatteryCalculator();
        final int start = 100;    
        final int end = 85;
        Assert.assertEquals(bc.calcDayLife(start, end), 15);
        bc = null;
    }

    @Test
    public void calcDayLifeTestHigherEndLife(){
        BatteryCalculator bc = new BatteryCalculator();
        Assert.assertEquals(bc.calcDayLife(80, 90), -1);
        bc = null;
    }

    @Test
    public void calcDayLifeTestStartInputOutOfRange(){
        BatteryCalculator bc = new BatteryCalculator();
        Assert.assertEquals(bc.calcDayLife(110, 0), -1);
        bc = null;
    }

    @Test
    public void calcDayLifeTestEndInputOutOfRange(){
        BatteryCalculator bc = new BatteryCalculator();
        Assert.assertEquals(bc.calcDayLife(100, -10), -1);
        bc = null;
    }

    @Test
    public void calcDayLifeTestMaxInput(){
        BatteryCalculator bc = new BatteryCalculator();
        Assert.assertEquals(bc.calcDayLife(Integer.MAX_VALUE, 0), -1);
        bc = null;
    } 

    @Test
    public void calcDayLifeTestMinInput(){
        BatteryCalculator bc = new BatteryCalculator();
        Assert.assertEquals(bc.calcDayLife(100, Integer.MIN_VALUE), -1);
        bc = null;
    } 

    @Test
    public void calcPredictedLifeTestDayLifeMax(){
        BatteryCalculator bc = new BatteryCalculator();
        Assert.assertEquals(bc.calcPredictedLife(Integer.MAX_VALUE), -1, 0);
        bc = null;
    }

    @Test
    public void calcPredictedLifeTestDayLifeMin(){
        BatteryCalculator bc = new BatteryCalculator();
        Assert.assertEquals(bc.calcPredictedLife(Integer.MIN_VALUE), -1, 0);
        bc = null;
    }

    @Test
    public void calcDayLifeTestSimpleInput(){
        BatteryCalculator bc = new BatteryCalculator();
        Assert.assertEquals(bc.calcPredictedLife(15), 6.67, 0.01);
    }

}