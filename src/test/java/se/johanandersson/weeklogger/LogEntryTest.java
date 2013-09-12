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

}
