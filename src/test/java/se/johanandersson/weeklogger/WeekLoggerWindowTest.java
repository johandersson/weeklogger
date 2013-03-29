package se.johanandersson.weeklogger;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import se.johanandersson.weeklogger.LogEntryValidationException;
import se.johanandersson.weeklogger.WeekLoggerWindow;

public class WeekLoggerWindowTest {
	@Test
	public void testFoo() throws IOException, LogEntryValidationException{
		Assert.assertNotNull(WeekLoggerWindow.getInstance());
		
		
	}
}
