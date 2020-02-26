package se.johanandersson.weeklogger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public class WeekLoggerFileHandlerTest {
	private static final int NUMBER_OF_LINES_IN_TESTFILE = 300;

	@BeforeSuite
	public void removeFile() throws IOException, LogEntryValidationException {
		WeekLoggerFileHandler.getInstance().createOrReadWeekLoggerFile();
		List<LogEntry> logEntries;
		logEntries = createCorrectTestLogEntries();
		WeekLoggerFileHandler.getInstance().writeLogEntriesToFile(logEntries);
	}
	
	

	@Test
	public void createOrReadWeekLoggerFile() throws IOException {
		WeekLoggerFileHandler.getInstance().createOrReadWeekLoggerFile();
		Assert.assertNotNull(WeekLoggerFileHandler.getInstance().getOutputStream());
	}




	@Test
	public void testIfCertainLogEntryIsInFile() throws LogEntryValidationException, IOException {
		LogEntry l = genTestLogDate(new LogEntry());
		l.setComment("foo");
		WeekLoggerFileHandler.getInstance().addLogEntry(l);
		Assert.assertTrue(WeekLoggerFileHandler.getInstance().isLogEntryInFile(l));
	}

	@Test
	public void testUpdateCertainLogEntryInFile() throws LogEntryValidationException, IOException {
		LogEntry oldLogEntry = genTestLogDate(new LogEntry());
		LogEntry updatedLogEntry = oldLogEntry;
		updatedLogEntry.setComment("updated comment");
		WeekLoggerFileHandler.getInstance().addLogEntry(oldLogEntry);
		WeekLoggerFileHandler.getInstance().updateCertainLogEntryInFile(oldLogEntry, updatedLogEntry);
		Assert.assertTrue(WeekLoggerFileHandler.getInstance().isLogEntryInFile(updatedLogEntry));
	}
	

	static List<LogEntry> createCorrectTestLogEntries()
			throws LogEntryValidationException {

		List<LogEntry> logEntries = new ArrayList<>();

		for (int i = 0; i < NUMBER_OF_LINES_IN_TESTFILE; i++) {
			LogEntry logEntry = new LogEntry();
			logEntry.setComment("Test" + i);
			genTestLogDate(logEntry);

			ValidateLogEntry v = new ValidateLogEntry();
			v.validate(logEntry);
			logEntries.add(logEntry);
		}

		return logEntries;

	}

	private static LogEntry genTestLogDate(LogEntry logEntry)
			throws LogEntryValidationException {
	
		logEntry.setLogDate(DateTimeUtils.getRandomDateString());
		logEntry.setYear(DateTimeUtils.genRandomYear());
		logEntry.setWeek(DateTimeUtils.genRandomWeek());

		logEntry.setStartTime("12:00:01");
		logEntry.setStopTime("13:04:01");
		logEntry.setTotalTime(new Time(1, 4, 0));

		return logEntry;
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
