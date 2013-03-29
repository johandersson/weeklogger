package se.johanandersson.weeklogger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Johan Andersson
 * 
 */
public class DateTimeUtils {
	private static Calendar cal;

	public static int getCurrentWeek() {
		cal = Calendar.getInstance();
		int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);
		return currentWeek;
	}

	public static String getCurrentDate() {
		cal = Calendar.getInstance();
		DateFormat f1 = DateFormat.getDateInstance(DateFormat.SHORT);
		Date now = new Date();
		now = cal.getTime();
		return f1.format(now);
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

	public static boolean dateBeforeTheOther(String d1, String d2) throws ParseException {
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(d1);
			Date date2 = sdf.parse(d2);
			System.out.println(sdf.format(date1));
			System.out.println(sdf.format(date2));

			if (date1.compareTo(date2) > 0) {
				return false;
			} else if (date1.compareTo(date2) < 0) {
				return true;
			} else if (date1.compareTo(date2) == 0) {
				return false;
			}
			else{
				return false;
			}
}
	



	
	
}
