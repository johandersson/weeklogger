package se.johanandersson.weeklogger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 
 * @author Johan Andersson
 * 
 */
public class DateTimeUtils {

	public static int getCurrentWeek() {
		DateTime dt = new DateTime();
		return dt.getWeekOfWeekyear();

	}

	public static String getCurrentDate() {
		DateTimeFormatter dtFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = new DateTime();
		return dt.toString(dtFormatter);
	}

	public static Time getCurrentTime() {

		Calendar c = Calendar.getInstance();
		Time currentTime = new Time(c.get(Calendar.HOUR_OF_DAY),
				c.get(Calendar.MINUTE), c.get(Calendar.SECOND));

		return currentTime;

	}

	public static String getCurrentYear() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		return String.valueOf(year);
	}

	public static boolean dateBeforeTheOther(String d1, String d2)
			throws ParseException {
		DateTime dt1 = new DateTime(d1);
		DateTime dt2 = new DateTime(d2);
		return dt1.isBefore(dt2);
	    
	}

	/**
	 * Creates random date for testing purposes
	 * @return Date-string in yy-mm-dd form
	 */
	public static String genRandomDate() {
		long offset = Timestamp.valueOf("2005-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
		long diff = end - offset + 1;	
		Timestamp rand = new Timestamp(offset + (long) (Math.random() * diff));
		DateTimeFormatter dtFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = new DateTime(rand);
		return dt.toString(dtFormatter);
	}

	public static int genRandomWeek() {
		DateTime dt = new DateTime(genRandomDate());
		return dt.getWeekOfWeekyear();
	}

	public static int genRandomYear() {
		DateTime dt = new DateTime(genRandomDate());
		return dt.getYear();
	}
	
	

}