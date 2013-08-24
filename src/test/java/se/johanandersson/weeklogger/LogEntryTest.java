package se.johanandersson.weeklogger;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import se.johanandersson.weeklogger.DateTimeUtils;
import se.johanandersson.weeklogger.LogEntry;
import se.johanandersson.weeklogger.LogEntryValidationException;
import se.johanandersson.weeklogger.Time;

public class LogEntryTest {
	@Test
	public void dateBeforeTheOther() throws ParseException {
		String d1 = "2012-12-12";
		String d2 = "2013-12-31";

		Assert.assertTrue(DateTimeUtils.dateBeforeTheOther(d1, d2));
		Assert.assertFalse(DateTimeUtils.dateBeforeTheOther(d2, d1));
	}

	@Test
	public void testSort() throws LogEntryValidationException {
		
		List<LogEntry> logEntryList = new ArrayList<LogEntry>();
		
		for(int i=0;i<10;i++){
			LogEntry l = createTestLogEntry();
			logEntryList.add(l);
		}
		
		
		printLogEntryList(logEntryList, ">>>Before sort");
		
		Collections.sort(logEntryList, new LogEntryComparator());
		

		printLogEntryList(logEntryList, ">>>After sort");
		
		

	}

	private void printLogEntryList(List<LogEntry> logEntryList, String s) {
		System.out.println(s);
		
		for(LogEntry l:logEntryList)
			System.out.println(l);
	}

	private LogEntry createTestLogEntry() throws LogEntryValidationException {
		LogEntry le = new LogEntry();
		String logDate = genTestDate();
		le.setLogDate(logDate);
		le.setComment("testing");
		le.setStartTime("09:00:02");
		le.setStopTime("10:00:02");
		le.setTotalTime(new Time(1, 0, 1));
		le.setWeek(RandomGeneratorHelperClass.generateRandomInteger(1, 2));
		le.setYear("2014");
		return le;
	}

	private String genTestDate() {
		return String.valueOf(RandomGeneratorHelperClass.generateRandomInteger(2010, 2014))+"-"+
				String.valueOf(RandomGeneratorHelperClass.generateRandomInteger(10, 12))+"-"+
				String.valueOf(RandomGeneratorHelperClass.generateRandomInteger(10, 31));
	}
}
