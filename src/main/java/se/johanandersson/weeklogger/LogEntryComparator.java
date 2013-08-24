package se.johanandersson.weeklogger;

import java.text.ParseException;
import java.util.Comparator;

import org.joda.time.DateTime;

public class LogEntryComparator implements Comparator<LogEntry> {

	public int compare(LogEntry l1, LogEntry l2) {	
			 
			 
			 return 0;
	}

	private int compareLogEntriesWeeks(LogEntry l1, LogEntry l2) {

		if (l1.getWeek() < l2.getWeek()) {
			return -1;
		}

		else if (l1.getWeek() > l2.getWeek()) {
			return 1;
		}

		else {
			return 0;
		}
	}

}
