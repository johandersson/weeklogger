package se.johanandersson.weeklogger;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public enum RandomDates {
	
	INSTANCE;

	private static GregorianCalendar gc;

	public static GregorianCalendar getRandomCal() {
		int year = getYear();
		int month = getMonth();
		gc = new GregorianCalendar(year, month, 1);
		int day = getDay(gc);
		gc.set(year, month, day);
		return gc;
	}

	public static String getRandomDate() {
		DateFormat f1 = DateFormat.getDateInstance(DateFormat.SHORT);
		Date now = new Date();
		now = getRandomCal().getTime();
		return f1.format(now);
	}

	public static int getDay(GregorianCalendar gc) {
		int day = RandomDates.randBetween(1,
				gc.getActualMaximum(gc.DAY_OF_MONTH));
		return day;
	}

	public static int getMonth() {
		int month = RandomDates.randBetween(0, 11);
		return month;
	}

	public static int getYear() {
		int year = RandomDates.randBetween(2005, 2010);
		return year;
	}
	
	public static int getWeek(){
		return gc.get(Calendar.WEEK_OF_YEAR);
	}

	private static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}
}