package se.johanandersson.weeklogger;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import se.johanandersson.weeklogger.LogEntry;
import se.johanandersson.weeklogger.LogEntryHandler;

public class LogEntryHandlerTest {
	@Test
	public void f() {
		LogEntryHandler l = new LogEntryHandler();
		Assert.assertTrue(l.getLogEntries().isEmpty());
	}

	@Test
	public void getLogEntriesWithSameYear() throws IOException{
//		LogEntryHandler logEntryHandler = new LogEntryHandler();
//		List<LogEntry> entriesWithSameYear = logEntryHandler.getEntriesWithSameYear(2013);
//		Assert.assertNotNull(entriesWithSameYear);
	}
	
}
