package se.johanandersson.weeklogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Johan Andersson
 * 
 */
public class LogEntryHandler {

	private List<LogEntry> logEntries;
	private LogEntryCalculator logEntryCalc;

	public LogEntryHandler() throws IOException {
		logEntryCalc = new LogEntryCalculator(this.getLogEntries());
	}

	public void addLogEntryToList(LogEntry logEntry) throws IOException {
		getLogEntries().add(logEntry);
	}

	public List<LogEntry> getLogEntryList() throws IOException {
		return getLogEntries();
	}

	public Time getTotalTimeOfAllLogEntries() throws IOException {
		return getLogEntryCalc().calculateTotalTimeOfLogEntries(this.getLogEntries());
	}

	public Time getTotalTimeOfLogEntriesForCertainWeek(int week)
			throws IOException {

		List<LogEntry> entriesWithSameWeek = WeekLoggerFileHandler
				.getInstance().readEntriesWithSameWeekFromFile(week);
		Time totalTimeOfLogEntries = getLogEntryCalc()
				.calculateTotalTimeOfLogEntries(entriesWithSameWeek);

		if (totalTimeOfLogEntries == null)
			return new Time(0, 0, 0);

		return totalTimeOfLogEntries;
	}

	public List<Integer> getListOfWeeks() throws IOException {
		List<Integer> listOfWeeks = new ArrayList<Integer>();

		for (LogEntry logEntry : this.getLogEntryList()) {
			int week = logEntry.getWeek();
			if (!listOfWeeks.contains(week))
				listOfWeeks.add(week);
		}

		Collections.sort(listOfWeeks);
		return listOfWeeks;
	}

	public List<LogEntry> getLogEntries() throws IOException {
		if (logEntries == null)
			return WeekLoggerFileHandler.getInstance()
					.getAllLogEntriesFromFile();
		else
			return logEntries;
	}
	
	public void update() throws IOException{
		logEntries = WeekLoggerFileHandler.getInstance().getAllLogEntriesFromFile();
	}

	public void setLogEntries(List<LogEntry> logEntries) {
		this.logEntries = logEntries;
	}

	public List<LogEntry> getLogEntriesWithSameYearAndWeek(int year, int week)
			throws IOException {
		return getLogEntryCalc()
				.getLogEntriesWithTheSameYearAndWeek(year, week);

	}

	public List<Integer> getListOfYears() throws IOException {
		List<Integer> listOfWeeks = new ArrayList<Integer>();

		for (LogEntry logEntry : this.getLogEntryList()) {
			int year = logEntry.getYear();
			if (!listOfWeeks.contains(year))
				listOfWeeks.add(year);
		}

		Collections.sort(listOfWeeks);
		return listOfWeeks;
	}

	public List<LogEntry> getLogEntriesWithSameYear(int year)
			throws IOException {
		return getLogEntryCalc().getLogEntriesWithTheSameYear(year);

	}

	private LogEntryCalculator getLogEntryCalc() {
		return logEntryCalc;
	}

	

}
