package se.johanandersson.weeklogger;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.testng.annotations.Test;

public class RandomDateTest {
  @Test
  public void testGetRandomDate() {
	  Assert.assertNotNull(DateTimeUtils.getRandomDateString());
	  Assert.assertTrue(DateTimeUtils.getRandomDateString().length()>2);
  }
  
  @Test
  public void testGetRandomWeek(){
	  int genRandWeek = DateTimeUtils.genRandomWeek();
	  Assert.assertNotNull(genRandWeek);
	  Assert.assertTrue(genRandWeek>0 && genRandWeek < 53);
  }
  
  @Test
  public void testGetRandomYar(){
	  int genRandYear = DateTimeUtils.genRandomYear();
	  Assert.assertNotNull(genRandYear);
	  Assert.assertTrue(genRandYear > 2004 && genRandYear < 2014);

  }
  
  
}
