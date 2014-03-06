package se.johanandersson.weeklogger;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestLogEntryTableModel {
  @Test
  public void testTableModel() throws LogEntryValidationException{
	  LogEntry le = new LogEntry();
	  le.setLogDate(DateTimeUtils.getCurrentDate());
	  le.setStartTime(DateTimeUtils.getCurrentTime().toString());
	  le.setStopTime(new Time(19,30,30).toString());
	  le.setTotalTime(new Time(1,0,2));
	  le.setWeek(12);
	  
	  List<LogEntry> logEntryList = new ArrayList<LogEntry>();
	  logEntryList.add(le);
	  
	  LogEntryTableModel letm = new LogEntryTableModel(logEntryList);
	  
	  String actualLogDate = (String)letm.getValueAt(0, 0);
	  String expectedLogDate = le.getLogDate();
	  
	  String actualWeek = letm.getValueAt(0, 1);
	  String expectedWeek = String.valueOf(le.getWeek());
	  
	  
	  String actualStartTime = (String)letm.getValueAt(0, 2);
	  String expectedStartTime = le.getStartTime();
	  
	  String actualStopTime = (String)letm.getValueAt(0, 3);
	  String expectedStopTime = le.getStopTime();
	  
	  Assert.assertEquals(actualLogDate, expectedLogDate);
	  Assert.assertEquals(actualStartTime,expectedStartTime);
	  Assert.assertEquals(actualStopTime,expectedStopTime);
	  Assert.assertEquals(actualWeek, expectedWeek);

	   
	  
  }
  
  
}
