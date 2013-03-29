package se.johanandersson.weeklogger;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import se.johanandersson.weeklogger.DateTimeUtils;
import se.johanandersson.weeklogger.LogEntry;
import se.johanandersson.weeklogger.LogEntryTableModel;
import se.johanandersson.weeklogger.LogEntryValidationException;
import se.johanandersson.weeklogger.Time;

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
	  
	  String actualStartTime = (String)letm.getValueAt(0, 1);
	  String expectedStartTime = le.getStartTime();
	  
	  String actualStopTime = (String)letm.getValueAt(0, 2);
	  String expectedStopTime = le.getStopTime();
	  
	  Assert.assertEquals(actualLogDate, expectedLogDate);
	  Assert.assertEquals(actualStartTime,expectedStartTime);
	  Assert.assertEquals(actualStopTime,expectedStopTime);
	   
	  
  }
  
  
}
