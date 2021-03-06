package se.johanandersson.weeklogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Johan Andersson
 */
public class LogEntryHandler {

    private List<LogEntry> logEntries;
    private LogEntryCalculator logEntryCalc;

    public LogEntryHandler() throws IOException {
        logEntryCalc = new LogEntryCalculator(this);
    }

    public List<Integer> getListOfWeeksInAYear(int year) throws IOException {
        List<Integer> listOfWeeks = new ArrayList<>();

        this.getLogEntries().forEach(logEntry -> {
            if (logEntry.getYear() == year && !listOfWeeks.contains(logEntry.getWeek()))
                listOfWeeks.add(logEntry.getWeek());
        });

        Collections.sort(listOfWeeks);
        return listOfWeeks;
    }

    public List<LogEntry> getLogEntries() throws IOException {
        if (logEntries == null)
            return WeekLoggerFileHandler.getInstance()
                    .readAllLogEntriesFromFile();
        else
            return logEntries;
    }

    public void resetLogEntries() throws IOException {
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
        List<Integer> listOfYears = new ArrayList<>();

        this.getLogEntries().forEach(logEntry -> {
            int year = logEntry.getYear();
            if (!listOfYears.contains(year))
                listOfYears.add(year);
        });

        Collections.sort(listOfYears);
        return listOfYears;
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

    public void writeLogEntry(LogEntry currentLogEntry) throws IOException, LogEntryValidationException {
        WeekLoggerFileHandler.getInstance().addLogEntry(currentLogEntry);
    }


}
