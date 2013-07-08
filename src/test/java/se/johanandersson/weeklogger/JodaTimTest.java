package se.johanandersson.weeklogger;

import java.util.Date;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.annotations.Test;

public class JodaTimTest {
  @Test
  public void testGetDate() {
	  
	  DateTimeFormatter dtFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
	  DateTime dt = new DateTime();
	  Assert.assertEquals(DateTime.now().toString(dtFormatter), dt.toString(dtFormatter));
	  
	  
	  
	  
  }
  
  
}
