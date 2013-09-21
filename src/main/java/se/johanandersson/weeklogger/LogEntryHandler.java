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
		logEntryCalc = new LogEntryCalculator(this);
	}

	public void addLogEntryToList(LogEntry logEntry) throws IOException {
		getLogEntries().add(logEntry);
	}

	public Time getTotalTimeOfAllLogEntries() throws IOException {
		return LogEntryCalculator.calculateTotalTimeOfLogEntries(this.getLogEntries());
	}

	public Time getTotalTimeOfLogEntriesForCertainWeek(int week)
			throws IOException {

		List<LogEntry> entriesWithSameWeek = readEntriesWithSameWeekFromFile(week);
		Time totalTimeOfLogEntries = LogEntryCalculator
				.calculateTotalTimeOfLogEntries(entriesWithSameWeek);

		if (totalTimeOfLogEntries == null)
			return new Time(0, 0, 0);

		return totalTimeOfLogEntries;
	}

	private List<LogEntry> readEntriesWithSameWeekFromFile(int week) throws IOException {
		List<LogEntry> entriesWithTheSameWeek = logEntryCalc.getLogEntriesWithTheSameWeek(week);
		return entriesWithTheSameWeek;
	}

	public List<Integer> getListOfWeeks() throws IOException {
		List<Integer> listOfWeeks = new ArrayList<>();

		for (LogEntry logEntry : this.getLogEntries()) {
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
	
	public void resetLogEntries() throws IOException{
		logEntries = null;
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
		List<Integer> listOfWeeks = new ArrayList<>();

		for (LogEntry logEntry : this.getLogEntries()) {
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

	public void deleteLogEntry(LogEntry logEntry) throws IOException, LogEntryValidationException {
		WeekLoggerFileHandler.getInstance().deleteCertainLogEntryInFile(
				logEntry);
	}

	public void writeLogEntry(LogEntry currentLogEntry) throws IOException{
		WeekLoggerFileHandler.getInstance().writeLogEntryToFileInJSONFormat(currentLogEntry);
	}

	

}
