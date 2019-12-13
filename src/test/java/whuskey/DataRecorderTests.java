package whuskey;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
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
        char[] fileContents = new char[7];
        String actualData = "10d 90e";
        char[] actualDataArray = new char[7];
        actualDataArray = actualData.toCharArray();
        boolean identical = true;
        try{
            DataRecorder.recordNewEntry(100, 90);
            FileReader fr = new FileReader(old);
            fr.read(fileContents, 0, fileContents.length);
            for(int i = 0; i < 7; i++){
                if(fileContents[i] != actualDataArray[i]){
                    identical = false;
                }
            }
            assertTrue(identical);
            fr.close();
        }
        catch(Exception e){
            System.out.println(e + " occurred.");
        }
    }

    @Test
    public void recordNewEntryTestCheckDataAdded(){
        try{
            DataRecorder.recordNewEntry(100, 90);
            DataRecorder.recordNewEntry(90, 80);
            DataRecorder.recordNewEntry(80, 70);
            String expectedData = "10d 90e\n10d 80e\n10d 70e\n";
            char[] expectedDataArray = expectedData.toCharArray();
            char[] actualData = new char[27];
            boolean identical = true;
            FileReader fr = new FileReader(old);
            fr.read(actualData, 0, actualData.length);
            for(int i = 0; i < 26; i++){
                if(actualData[i] != expectedDataArray[i]){
                    identical = false;
                }
            }
            assertTrue(identical);
            fr.close();
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