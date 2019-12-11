package whuskey;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.CharBuffer;
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
                Files.move(Paths.get("src\\main\\resources\\BatteryData.txt"), Paths.get("src\\main\\resources\\BatteryDataTemp.txt"));
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
        CharBuffer fileContents = CharBuffer.allocate(7);
        try{
            DataRecorder.recordNewEntry(100, 90);
            FileReader fr = new FileReader(old);
            fr.read(fileContents);
        }
        catch(Exception e){
            System.out.println(e + " occurred.");
        }
    }

    @After
    public void tearDown(){
        
        
        if(old != null){
            try{
                if(old.exists()){
                    old.delete();
                }
                Files.move(Paths.get("src\\main\\resources\\BatteryDataTemp.txt"), Paths.get("src\\main\\resources\\BatteryData.txt"));
            }
            catch(IOException io){
                System.out.println(io + " occurred.");
            }
        }
        File oldTest = new File("src\\main\\resources\\BatteryDataTemp.txt");
        if(oldTest.exists()){
            oldTest.delete();
        }
    }
}