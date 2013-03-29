package se.johanandersson.weeklogger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import se.johanandersson.weeklogger.DateTimeUtils;
import se.johanandersson.weeklogger.LogEntry;
import se.johanandersson.weeklogger.LogEntryValidationException;
import se.johanandersson.weeklogger.RandomDates;
import se.johanandersson.weeklogger.Time;
import se.johanandersson.weeklogger.ValidateLogEntry;
import se.johanandersson.weeklogger.WeekLoggerFileHandler;

public class WeekLoggerFileHandlerTest {
	@BeforeSuite
	public void removeFile() {
		File file = new File(WeekLoggerFileHandler.getWeekloggerFile());
		// Delete test file
		if (file.exists())
			file.delete();
	}

	@Test
	public void createOrReadWeekLoggerFile() {
		WeekLoggerFileHandler wlfh = new WeekLoggerFileHandler();
		wlfh.createOrReadWeekLoggerFile();
		Assert.assertNotNull(wlfh.getOutputStream());
	}

	@Test
	void foo() {

	}

	@Test
	public void testWrite() throws IOException, LogEntryValidationException {
		WeekLoggerFileHandler wlfw = new WeekLoggerFileHandler();
		wlfw.createOrReadWeekLoggerFile();
		List<LogEntry> logEntries = new ArrayList<LogEntry>();
		logEntries = createCorrectTestLogEntries();
		wlfw.writeLogEntriesToFile(logEntries);
		Assert.assertEquals(countNumberOfLinesInFile("weeklogger.txt"), 1000);

	}

	@Test
	public void testEmptyFile() throws IOException {
		removeFile();
		WeekLoggerFileHandler wlfh = new WeekLoggerFileHandler();
		Assert.assertTrue(wlfh.fileHasNoLogEntries());

	}

	static List<LogEntry> createCorrectTestLogEntries()
			throws LogEntryValidationException {

		List<LogEntry> logEntries = new ArrayList<LogEntry>();

		for (int i = 0; i < 1000; i++) {
			LogEntry logEntry = new LogEntry();
			logEntry.setComment("Test" + i);
			genTestLogDate(logEntry);

			ValidateLogEntry v = new ValidateLogEntry();
			v.validate(logEntry);
			logEntries.add(logEntry);
		}

		return logEntries;

	}

	private static void genTestLogDate(LogEntry logEntry)
			throws LogEntryValidationException {
	
		logEntry.setLogDate(RandomDates.INSTANCE.getRandomDate());
		logEntry.setYear(String.valueOf(RandomDates.INSTANCE.getYear()));
		logEntry.setWeek(RandomDates.INSTANCE.getWeek());

		logEntry.setStartTime("12:00:01");
		logEntry.setStopTime("12:00:00");
		logEntry.setTotalTime(new Time(0, 0, 1));

	}

	public int countNumberOfLinesInFile(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n')
						++count;
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}

}
