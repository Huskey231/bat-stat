package whuskey;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.nio.file.Files; 
import java.nio.file.*;

import whuskey.DataRecorder;

public class DataRecorderTests{

    File old = new File("src\\main\\resources\\BatteryData.txt");

    @Before
    public void setup(){
        boolean prevFileExists;
        prevFileExists = old.exists() ? true : false;
        if(prevFileExists == true){
            try{
                Files.move(Paths.get("src\\main\\resources\\BatteryData.txt"), Paths.get("src\\test\\resources\\BatteryData.txt"));
            }
            catch(IOException io){
                System.out.println(io + " occurred.");
            }
        }
    }

    @Test
    public void recordNewEntryTestCheckFileExists(){
        try{
            DataRecorder.recordNewEntry(100, 90);
            File testFile = new File("src\\main\\resources\\BatteryData.txt");
            boolean fileExists = testFile.exists() ? true : false;
            assertTrue(fileExists);
        }
        catch(Exception e){
            System.out.println(e + " occurred.");
        }
    }

    @Test
    public void recordNewEntryTestCheckDataRecorded(){

    }

    @After
    public void tearDown(){
        if(old != null){
            try{
                Files.move(Paths.get("src\\test\\resources\\BatteryData.txt"), Paths.get("src\\main\\resources\\BatteryData.txt"));
            }
            catch(IOException io){
                System.out.println(io + " occurred.");
            }
        }
        File oldTest = new File("src\\test\\resources\\BatteryData.txt");
        if(oldTest.exists()){
            oldTest.delete();
        }
    }
}