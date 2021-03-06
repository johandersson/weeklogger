package se.johanandersson.weeklogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.testng.Assert;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class LogEntryCalculatorTest {
	private LogEntryHandler logEntryHandler;
	private LogEntryCalculator logCalc;

	@BeforeSuite
	public void setUp() throws IOException, LogEntryValidationException{
		logEntryHandler = new LogEntryHandler();
		logEntryHandler.setLogEntries(genTestLogEntries());
		logCalc = new LogEntryCalculator(logEntryHandler);
	}
	
	@Test
	public void testCalculateTotalTime() throws LogEntryValidationException, IOException {
		
		
		Assert.assertNotNull(logCalc);
		Assert.assertNotNull(logCalc
				.calculateTotalTimeOfLogEntries(new ArrayList<LogEntry>()));

		Time t1 = new Time(8, 45, 30);
		Assert.assertEquals(t1, logCalc.calculateTotalTimeOfLogEntries(genTestLogEntries()));

	}
        
        @Test
        public void foo() throws LogEntryValidationException{
            final List<LogEntry> genTestLogEntries = genTestLogEntries();
            

            
      
        }
	
	@Test
	public void testGetLogEntriesWithSameWeekAndYear() throws LogEntryValidationException, IOException{
		logEntryHandler.setLogEntries(genTestLogEntries());
		LogEntryCalculator logCalc = new LogEntryCalculator(logEntryHandler);
		Assert.assertEquals(2, logCalc.getLogEntriesWithTheSameYearAndWeek(2012, 12).size());
	}
	
	@Test
	public void testGetLogEntriesWithSameWeek() throws LogEntryValidationException, IOException{
		Assert.assertEquals(3, logCalc.getLogEntriesWithTheSameWeek(12).size());
		
	}
	
	@Test
	public void testGetLogEntriesWithSameYear() throws LogEntryValidationException, IOException{
		Assert.assertEquals(2, logCalc.getLogEntriesWithTheSameYear(2014).size());
		
	}

	private List<LogEntry> genTestLogEntries()
			throws LogEntryValidationException {
		LogEntry test1 = getTestLogEntry(2, 34, 22, 2012, 12);
		LogEntry test2 = getTestLogEntry(1, 34, 22, 2012, 12);
		LogEntry test3 = getTestLogEntry(2, 34, 22, 2013, 1);
		LogEntry test4 = getTestLogEntry(1, 1, 2, 2014, 1);
		LogEntry test5 = getTestLogEntry(1, 1, 22, 2014, 12);
		List<LogEntry> logEntryList = new ArrayList<LogEntry>();
		logEntryList.add(test1);
		logEntryList.add(test2);
		logEntryList.add(test3);
		logEntryList.add(test4);
		logEntryList.add(test5);
		return logEntryList;
	}

	private LogEntry getTestLogEntry(int h, int m, int s, int year, int week)
			throws LogEntryValidationException {
		LogEntry le1 = new LogEntry();
		le1.setComment("");
		le1.setLogDate(DateTimeUtils.getRandomDateString());
		le1.setYear(year);
		le1.setWeek(week);
		
		// Not used for calculation
		le1.setStartTime("00:00:00");
		le1.setStopTime("00:00:00");
		le1.setTotalTime(new Time(h, m, s));
		return le1;
	}
}
