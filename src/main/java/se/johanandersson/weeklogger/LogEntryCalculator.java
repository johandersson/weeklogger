package se.johanandersson.weeklogger;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class LogEntryCalculator {


    private LogEntryHandler logEntryHandler;


    public LogEntryCalculator(LogEntryHandler logEntryHandler) {
        this.logEntryHandler = logEntryHandler;
    }


    public static Time calculateTotalTimeOfLogEntries(List<LogEntry> logEntries) {
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

    private static int getSumOfAllHours(int sumOfAllHours, Time totalTime) {
        int hours = totalTime.getHours();
        sumOfAllHours += hours;
        return sumOfAllHours;
    }

    private static int getSumOfAllMinutes(int sumOfAllMinutes, Time totalTime) {
        int minutes = totalTime.getMinutes();
        sumOfAllMinutes += minutes;
        return sumOfAllMinutes;
    }

    private static int getSumOfAllSeconds(int sumOfAllSeconds, Time totalTime) {
        int seconds = totalTime.getSeconds();
        sumOfAllSeconds += seconds;
        return sumOfAllSeconds;
    }

    public List<LogEntry> getLogEntriesWithTheSameYearAndWeek(int year, int week) throws IOException {
        return logEntryHandler.getLogEntries()
                .stream()
                .filter(l -> l.getYear() == year)
                .filter(l->l.getWeek()==week)
                .collect(Collectors.toList());

    }

    public List<LogEntry> getLogEntriesWithTheSameWeek(int week) throws IOException {
        return logEntryHandler.getLogEntries()
                .stream()
                .filter(l -> l.getWeek() == week)
                .collect(Collectors.toList());
    }

    public List<LogEntry> getLogEntriesWithTheSameYear(int year) throws IOException {
        return logEntryHandler.getLogEntries()
                .stream()
                .filter(l -> l.getYear() == year)
                .collect(Collectors.toList());
    }


}
