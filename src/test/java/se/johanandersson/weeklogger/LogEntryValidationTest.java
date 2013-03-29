package se.johanandersson.weeklogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import se.johanandersson.weeklogger.DateTimeUtils;
import se.johanandersson.weeklogger.LogEntry;
import se.johanandersson.weeklogger.LogEntryValidationException;
import se.johanandersson.weeklogger.Time;
import se.johanandersson.weeklogger.ValidateLogEntry;
import se.johanandersson.weeklogger.WeekLoggerFileHandler;
import se.johanandersson.weeklogger.WeekLoggerWindow;

public class LogEntryValidationTest {
	
	@BeforeSuite
	public void removeFile() {
		File file = new File(WeekLoggerFileHandler.getWeekloggerFile());
		// Delete test file
		if (file.exists())
			file.delete();
	}
	
	@Test(expectedExceptions = LogEntryValidationException.class) 
	public void testCreateLogEntryWithoutLogDate() throws LogEntryValidationException{
		LogEntry logEntry = new LogEntry();
		logEntry.setLogDate(" ");
		ValidateLogEntry v = new ValidateLogEntry();
		v.validateLogDate(logEntry);
	}

	@Test(expectedExceptions = LogEntryValidationException.class) 
	public void testSetComment() throws LogEntryValidationException{
	    LogEntry logEntry = new LogEntry();
	    logEntry.setComment(null);
	    ValidateLogEntry v = new ValidateLogEntry();
	    v.validateLogEntryComment(logEntry);
	}

	@Test(expectedExceptions = LogEntryValidationException.class)
	public void testSetIncorrectStartTime() throws LogEntryValidationException{
	    LogEntry logEntry = new LogEntry();
	    ValidateLogEntry validator = new ValidateLogEntry();
	    validator.validateStartAndStopTime(logEntry);
	    logEntry.setStartTime("10:10:");
	    logEntry.setStopTime("10:09:10");
	    validator.validateStartAndStopTime(logEntry);
	    logEntry.setStartTime("10::");
	    validator.validateStartAndStopTime(logEntry);
	    logEntry.setStartTime("0:0:0");
	    validator.validateStartAndStopTime(logEntry);
	}
	
	@Test(expectedExceptions = LogEntryValidationException.class)
	public void testInCorrectStopTime() throws LogEntryValidationException{
		  LogEntry logEntry = new LogEntry();
		  ValidateLogEntry validator = new ValidateLogEntry();
		  validator.validateStartAndStopTime(logEntry);
	}
	


	@Test
	public void testSetCorrectStartTime() throws LogEntryValidationException{
		LogEntry logEntry = new LogEntry();
		setCorrectStartAndStopTime(logEntry);
	    ValidateLogEntry validator = new ValidateLogEntry();
	    validator.validateStartAndStopTime(logEntry);
	}

	private void setCorrectStartAndStopTime(LogEntry logEntry) {
		logEntry.setStartTime("00:00:00");
		logEntry.setStopTime("23:59:59");
	}

	@Test(expectedExceptions=LogEntryValidationException.class)
	public void testSetInCorrectTotalTime() throws LogEntryValidationException{
		LogEntry logEntry = new LogEntry();
		setCorrectStartAndStopTime(logEntry);
	    ValidateLogEntry validator = new ValidateLogEntry();
	    validator.validateTotalTime(logEntry);
	}
	
	@Test(expectedExceptions=LogEntryValidationException.class)
	public void testSetInCorrectWeek() throws LogEntryValidationException{
		LogEntry le = new LogEntry();
		le.setWeek(0);
		le.setWeek(55);
		Assert.assertTrue(le.getWeek()==0); //should not have been set to 55, since it's an incorrect week
	}
	
	@Test
	public void testSetCorrectWeek() throws LogEntryValidationException{
		LogEntry le = new LogEntry();
		le.setWeek(1);
		le.setWeek(53);
		Assert.assertTrue(le.getWeek()==53); //has not been set
	}
	
	@Test
	public void testLogEntryEquals() throws LogEntryValidationException, IOException{
		List<LogEntry> logEntries = new ArrayList<LogEntry>();
		LogEntry testLogEntry = createTestLogEntry();
		logEntries.add(testLogEntry);
		Assert.assertTrue(logEntries.contains(testLogEntry));
		
		WeekLoggerFileHandler weekLoggerFileHandler = new WeekLoggerFileHandler();
		weekLoggerFileHandler.writeLogEntryToFileInJSONFormat(testLogEntry);
		Assert.assertTrue(weekLoggerFileHandler.isLogEntryInFile(testLogEntry));
		
		weekLoggerFileHandler.deleteCertainLogEntryInFile(testLogEntry);
		Assert.assertFalse(weekLoggerFileHandler.isLogEntryInFile(testLogEntry));
		
		
	}

	private LogEntry createTestLogEntry()
			throws LogEntryValidationException {
		LogEntry le = new LogEntry();
		le.setLogDate("2014-12-11");
		le.setComment("");
		le.setStartTime("09:00:02");
		le.setStopTime("10:00:02");
		le.setTotalTime(new Time(1,0,1));
		le.setWeek(47);
		le.setYear("2014");
		return le;
	}

	


	
	  
	  
}