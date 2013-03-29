package se.johanandersson.weeklogger;

@SuppressWarnings(value = "serial")
public class LogEntryValidationException extends Exception {
	public LogEntryValidationException(String message) {
		super(message);
	}

}
