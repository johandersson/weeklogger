package se.johanandersson.weeklogger;

import java.io.Serializable;
import org.joda.time.DateTime;

/**
 *
 * @author Johan Andersson
 *
 */
public class LogEntry implements Comparable<LogEntry>, Serializable {

    private int week;
    private int year;
    private String startTime;
    private String stopTime;
    private Time totalTime;
    private String logDate;
    private String comment;

    public LogEntry() throws LogEntryValidationException {
        setComment("");
        DateTime dt = new DateTime();
        setLogDate(dt.toString(DateTimeUtils.getDateFormat()));
        setWeek(dt.getWeekOfWeekyear()); // set the week to current
        setYear(dt.getYear());
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
        return "Vecka: " + week + " Start:" + startTime + " Stop:" + stopTime
                + " Total:" + totalTime + " Date:" + logDate + " Comment:"
                + comment;

    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object aThat) {
        if (!(aThat instanceof LogEntry)) {
            return false;
        }

        LogEntry that = (LogEntry) aThat;

        return this.getStartTime().equals(that.getStartTime())
                && this.getStopTime().equals(that.getStopTime())
                && this.getLogDate().equals(that.getLogDate())
                && this.getWeek() == that.getWeek()
                && this.getYear() == that.getYear()
                && this.getTotalTime().toString()
                .equals(that.getTotalTime().toString());

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int compareTo(LogEntry that) {

        DateTime dt1 = new DateTime(this.getLogDate());
        DateTime dt2 = new DateTime(that.getLogDate());

        return dt1.compareTo(dt2);
    }

    @Override
    public int hashCode() {
        int result = HashCodeUtil.SEED;
        // collect the contributions of various fields
        result = HashCodeUtil.hash(result, this.logDate);
        result = HashCodeUtil.hash(result, this.week);
        result = HashCodeUtil.hash(result, this.year);
        result = HashCodeUtil.hash(result, this.startTime);
        result = HashCodeUtil.hash(result, this.stopTime);
        result = HashCodeUtil.hash(result, this.totalTime);
        return result;

    }
}
