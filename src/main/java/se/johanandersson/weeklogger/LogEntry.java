package se.johanandersson.weeklogger;

import java.text.ParseException;
import java.util.Random;

/**
 * 
 * @author Johan Andersson
 * 
 */
public class LogEntry implements Comparable<LogEntry> {
	private int week;
	private String startTime;
	private String stopTime;
	private Time totalTime;
	private String logDate;
	private String comment;
	private String year;

	public LogEntry() throws LogEntryValidationException {
		setComment("");
		setLogDate(DateTimeUtils.getCurrentDate());
		setWeek(DateTimeUtils.getCurrentWeek()); // set the week to current
		setYear(DateTimeUtils.getCurrentYear());
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) throws LogEntryValidationException {
		if (week >= 1 && week <= 54) {
			this.week = week;
		} else {
			throw new LogEntryValidationException("Incorrect week: " + week);
		}
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;

	}

	public Time getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Time totalTime) {
		this.totalTime = totalTime;
	}

	@Override
	public String toString() {
		return "Vecka: " + week + " Start:" + startTime + " Stop:" + stopTime + " Total:"
				+ totalTime + " Date:" + logDate + " Comment:" + comment;

	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public boolean equals(Object aThat) {
		if (!(aThat instanceof LogEntry))
			return false;

		LogEntry that = (LogEntry) aThat;

		return this.getStartTime().equals(that.getStartTime())
				&& this.getStopTime().equals(that.getStopTime())
				&& this.getLogDate().equals(that.getLogDate())
				&& this.getWeek() == that.getWeek()
				&& this.getYear().equals(that.getYear())
				&& this.getTotalTime().toString()
						.equals(that.getTotalTime().toString());

	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public int compareTo(LogEntry that) {
		if (this.week < that.week)
			return -1;
		if (this.week > that.week)
			return 1;
		
		try {
			if (DateTimeUtils.dateBeforeTheOther(this.getLogDate(),
					that.getLogDate())) {
				return 1;
			}
			else
				return -1;
		} catch (ParseException e) {
			System.err.println("Can't parse dates when comparing two logentries");
			e.printStackTrace();
		}
		
		return 0;
	}

}
