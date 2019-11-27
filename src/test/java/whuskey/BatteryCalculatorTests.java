package whuskey;
import org.junit.Test;
import org.junit.Assert;

import whuskey.BatteryCalculator;
import junit.framework.*;

public class BatteryCalculatorTests{

    @Test
    public void calcDayLifeTest(){
        BatteryCalculator bc = new BatteryCalculator();
        final int start1 = 100;    
        final int start2 = 56;
        final int start3 = 0;
        final int start4 = Integer.MAX_VALUE;
        final int start5 = Integer.MIN_VALUE;
        final int end1 = 85;
        final int end2 = 62;
        final int end3 = 0;
        final int end4 = Integer.MIN_VALUE;
        final int end5 = Integer.MAX_VALUE;
        Assert.assertEquals(bc.calcDayLife(start1, end1), 15);
    }

}