package se.johanandersson.weeklogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogEntryCalculator {

	

	private LogEntryHandler logEntryHandler;


	public LogEntryCalculator(LogEntryHandler logEntryHandler) {
		this.logEntryHandler = logEntryHandler;
	}


	public Time calculateTotalTimeOfLogEntries(List<LogEntry> logEntries) {
		int sumOfAllSeconds = 0;
		int sumOfAllMinutes = 0;
		int sumOfAllHours = 0;

		for (LogEntry logEntry : logEntries) {
			Time totalTime = logEntry.getTotalTime();
			sumOfAllSeconds = getSumOfAllSeconds(sumOfAllSeconds, totalTime);
			sumOfAllMinutes = getSumOfAllMinutes(sumOfAllMinutes, totalTime);
			sumOfAllHours = getSumOfAllHours(sumOfAllHours, totalTime);
		}

		return new Time(sumOfAllHours, sumOfAllMinutes, sumOfAllSeconds);
	}

	private int getSumOfAllHours(int sumOfAllHours, Time totalTime) {
		int hours = totalTime.getHours();
		sumOfAllHours += hours;
		return sumOfAllHours;
	}

	private int getSumOfAllMinutes(int sumOfAllMinutes, Time totalTime) {
		int minutes = totalTime.getMinutes();
		sumOfAllMinutes += minutes;
		return sumOfAllMinutes;
	}

	private int getSumOfAllSeconds(int sumOfAllSeconds, Time totalTime) {
		int seconds = totalTime.getSeconds();
		sumOfAllSeconds += seconds;
		return sumOfAllSeconds;
	}

	public List<LogEntry> getLogEntriesWithTheSameYearAndWeek(int year, int week) throws IOException {
		List<LogEntry> logEntriesWithSameYearAndWeek = new ArrayList<LogEntry>();

		for (LogEntry logEntry : logEntryHandler.getLogEntries()) {
			if (logEntry.getYear() == year
					&& logEntry.getWeek() == week) {
				logEntriesWithSameYearAndWeek.add(logEntry);
			}
		}
		return logEntriesWithSameYearAndWeek;

	}

	public List<LogEntry> getLogEntriesWithTheSameWeek(int week) throws IOException {
		List<LogEntry> logEntriesWithSameWeek = new ArrayList<LogEntry>();
		for (LogEntry logEntry : logEntryHandler.getLogEntries()) {
			if (logEntry.getWeek() == week)
				logEntriesWithSameWeek.add(logEntry);
		}

		return logEntriesWithSameWeek;
	}

	public List<LogEntry> getLogEntriesWithTheSameYear(int year) throws IOException {
		List<LogEntry> logEntriesWithSameYear = new ArrayList<LogEntry>();

		for (LogEntry logEntry : logEntryHandler.getLogEntries()) {
			if (logEntry.getYear() == year) {
				logEntriesWithSameYear.add(logEntry);
			}
		}
		return logEntriesWithSameYear;

	}




}