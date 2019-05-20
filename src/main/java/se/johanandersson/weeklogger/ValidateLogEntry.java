package se.johanandersson.weeklogger;

import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class ValidateLogEntry {

    public void validateLogDate(LogEntry logEntry)
            throws LogEntryValidationException {
        String logDate = logEntry.getLogDate();
        if (StringUtils.isBlank(logDate)) {
            throw new LogEntryValidationException("Incorrect log date " + logDate);
        }
    }

    public void validateLogEntryComment(LogEntry logEntry) throws LogEntryValidationException {
        if (logEntry.getComment() == null) {
            throw new LogEntryValidationException("Comment can't be null");
        }

    }

    public void validateStartAndStopTime(LogEntry logEntry) throws LogEntryValidationException {

        String startTime = logEntry.getStartTime();
        String stopTime = logEntry.getStopTime();

        validateTimeParts(startTime);
        validateTimeParts(stopTime);

    }

    private void validateTimeParts(String time)
            throws LogEntryValidationException {
        String[] timeParts = null;

        if (time != null)
            timeParts = StringUtils.split(time, ":");
        else
            throw new LogEntryValidationException("Incorrect time string");

        if (timeParts.length < 3 || StringUtils.isBlank(time)) {
            throw new LogEntryValidationException("Incorrect time string");
        }
    }

    public void validate(List<LogEntry> logEntries) throws LogEntryValidationException {
        for (LogEntry l : logEntries) {
            this.validate(l);
        }

    }

    public void validate(LogEntry logEntry) throws LogEntryValidationException {
        validateLogDate(logEntry);
        validateStartAndStopTime(logEntry);
        validateTotalTime(logEntry);

    }

    public void validateTotalTime(LogEntry logEntry) throws LogEntryValidationException {
        if (logEntry.getTotalTime() == null)
            throw new LogEntryValidationException("Incorrect total time");

    }

}
