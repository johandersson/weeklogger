package se.johanandersson.weeklogger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date1 = sdf.parse(d1);
		Date date2 = sdf.parse(d2);

		if (date1.compareTo(date2) > 0) {
			return false;
		} else if (date1.compareTo(date2) < 0) {
			return true;
		} else if (date1.compareTo(date2) == 0) {
			return false;
		} else {
			return false;
		}
	}

}