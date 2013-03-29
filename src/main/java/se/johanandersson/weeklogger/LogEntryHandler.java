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

	public LogEntryHandler() {
		logEntries = new ArrayList<LogEntry>();

	}

	public void addLogEntryToList(LogEntry logEntry) {
		getLogEntries().add(logEntry);
	}

	public List<LogEntry> getLogEntryList() {
		return getLogEntries();
	}

	public Time getTotalTimeOfAllLogEntries() throws IOException {
		WeekLoggerFileHandler weekLoggerFileHandler = new WeekLoggerFileHandler();
		LogEntryHandler allLogEntriesFromFile = weekLoggerFileHandler.getAllLogEntriesFromFile();
		List<LogEntry> allLogEntries = allLogEntriesFromFile.getLogEntries();
		return calculateTotalTimeOfLogEntries(allLogEntries);
	}
	
	public Time getTotalTimeOfLogEntriesForCertainWeek(int week) throws IOException{
		WeekLoggerFileHandler weekLoggerFileHandler = new WeekLoggerFileHandler();
		List<LogEntry> entriesWithSameWeek = weekLoggerFileHandler.readEntriesWithSameWeekFromFile(week);
		Time totalTimeOfLogEntries = this.calculateTotalTimeOfLogEntries(entriesWithSameWeek);
		
		if(totalTimeOfLogEntries == null)
			return new Time(0,0,0);
		
		return totalTimeOfLogEntries;
	}
	
	private Time calculateTotalTimeOfLogEntries(
			List<LogEntry> logEntries) {
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

	public List<LogEntry> getEntriesWithTheSameWeek(int week) {
		List<LogEntry> logEntriesWithSameWeek = new ArrayList<LogEntry>();
		for (LogEntry logEntry : this.getLogEntryList()) {
			if (logEntry.getWeek() == week)
				logEntriesWithSameWeek.add(logEntry);
		}

		return logEntriesWithSameWeek;
	}
	
	
	public List<LogEntry> getLogEntriesWithSameWeekAndYearFromFile(int year,int week) throws IOException {
		WeekLoggerFileHandler weekLoggerFileHandler = new WeekLoggerFileHandler();
		List<LogEntry> entriesWithTheSameWeek = weekLoggerFileHandler.readEntriesWithSameYearAndWeekFromFile(year,week);
		return entriesWithTheSameWeek;
	}

	public List<Integer> getListOfWeeks() {
		List<Integer> listOfWeeks = new ArrayList<Integer>();
		
		for (LogEntry logEntry : this.getLogEntryList()) {
			int week = logEntry.getWeek();
			if (!listOfWeeks.contains(week))
				listOfWeeks.add(week);
		}
		
		Collections.sort(listOfWeeks); 
		return listOfWeeks;
	}

	public List<LogEntry> getLogEntries() {
		return logEntries;
	}

	public void setLogEntries(List<LogEntry> logEntries) {
		this.logEntries = logEntries;
	}

	public List<LogEntry> getLogEntriesWithSameYearAndWeek(int year, int week) throws IOException {
		WeekLoggerFileHandler weekLoggerFileHandler = new WeekLoggerFileHandler();
		LogEntryHandler allLogEntriesFromFile = weekLoggerFileHandler.getAllLogEntriesFromFile();
		
		List<LogEntry> logEntriesWithSameYear = new ArrayList<LogEntry>();
		
		for(LogEntry logEntry:allLogEntriesFromFile.getLogEntries()){
			if(Integer.valueOf(logEntry.getYear())==year && logEntry.getWeek() == week){
				logEntriesWithSameYear.add(logEntry);
			}
		}
		
		
		return logEntriesWithSameYear;
		
	}

	public List<String> getListOfYears() {
	List<String> listOfWeeks = new ArrayList<String>();
		
		for (LogEntry logEntry : this.getLogEntryList()) {
			String year = logEntry.getYear();
			if (!listOfWeeks.contains(year))
				listOfWeeks.add(year);
		}
		
		Collections.sort(listOfWeeks); 
		return listOfWeeks;
	}

	public List<LogEntry> getLogEntriesWithSameYear(int year) throws IOException {
		WeekLoggerFileHandler weekLoggerFileHandler = new WeekLoggerFileHandler();
		LogEntryHandler allLogEntriesFromFile = weekLoggerFileHandler.getAllLogEntriesFromFile();
		
		List<LogEntry> logEntriesWithSameYear = new ArrayList<LogEntry>();
		
		for(LogEntry logEntry:allLogEntriesFromFile.getLogEntries()){
			if(Integer.valueOf(logEntry.getYear())==year){
				logEntriesWithSameYear.add(logEntry);
			}
		}
		
		
		return logEntriesWithSameYear;
		
	}

}
