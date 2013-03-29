package se.johanandersson.weeklogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

/**
 * 
 * @author Johan Andersson
 * 
 */
public class WeekLoggerFileHandler {

	private BufferedWriter outputStream;
	private Gson jsonObjectHandler;
	private static final String WEEKLOGGER_FILE = "weeklogger.txt";

	public static String getWeekloggerFile() {
		return WEEKLOGGER_FILE;
	}

	public WeekLoggerFileHandler() {
		jsonObjectHandler = new Gson();

	}

	public void createOrReadWeekLoggerFile() {
		setOutputStream(null);
		try {
			OutputStreamWriter fileWriter = new OutputStreamWriter(
					new FileOutputStream(WEEKLOGGER_FILE, true), "UTF-8");
			setOutputStream(new BufferedWriter(fileWriter));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Couldn't create or read output file");
		}

	}

	/*
	 * Read logentries from file in json-format and return as list of LogEntry
	 */
	public List<LogEntry> readAllLogEntriesFromFile() throws IOException {
		List<LogEntry> logEntries = new ArrayList<LogEntry>();

		try {
			Reader fileReader = new InputStreamReader(new FileInputStream(
					WEEKLOGGER_FILE), "UTF-8");
			BufferedReader br = new BufferedReader(fileReader);
			addLogEntriesFromFileToListOfLogEntries(logEntries, br);
			fileReader.close();
		} catch (FileNotFoundException fnfe) {
			// TODO Auto-generated catch block
			System.err.println("The file " + WEEKLOGGER_FILE
					+ " couldn't be found");
			createOrReadWeekLoggerFile();
		}

		return logEntries;

	}

	private void addLogEntriesFromFileToListOfLogEntries(
			List<LogEntry> logEntries, BufferedReader br) throws IOException {
		String jsonString;
		while ((jsonString = br.readLine()) != null) {
			LogEntry ts = jsonObjectHandler
					.fromJson(jsonString, LogEntry.class);
			logEntries.add(ts);
		}
	}

	public void writeLogEntryToFileInJSONFormat(LogEntry logEntry)
			throws IOException {
		OutputStreamWriter fileWriter = new OutputStreamWriter(
				new FileOutputStream(WEEKLOGGER_FILE, true), "UTF-8");
		PrintWriter outPutFile = new PrintWriter(fileWriter);
		String jsonString = jsonObjectHandler.toJson(logEntry);
		outPutFile.println(jsonString);
		outPutFile.close();
	}

	public List<LogEntry> readEntriesWithSameWeekFromFile(int week) {
		LogEntryHandler timeLoggerFromFile = new LogEntryHandler();
		try {
			timeLoggerFromFile = getAllLogEntriesFromFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.err.println("Couldn't read log entries from file!!!");
		}
		List<LogEntry> entriesWithTheSameWeek = timeLoggerFromFile
				.getEntriesWithTheSameWeek(week);
		return entriesWithTheSameWeek;
	}

	public List<LogEntry> readEntriesWithSameYearAndWeekFromFile(int year,
			int week) throws IOException {
		LogEntryHandler timeLoggerFromFile = new LogEntryHandler();
		try {
			timeLoggerFromFile = getAllLogEntriesFromFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.err.println("Couldn't read log entries from file!!!");
		}
		List<LogEntry> entriesWithTheSameYear = timeLoggerFromFile
				.getLogEntriesWithSameYearAndWeek(year, week);
		return entriesWithTheSameYear;
	}

	public LogEntryHandler getAllLogEntriesFromFile() throws IOException {
		LogEntryHandler timeLogger = new LogEntryHandler();
		List<LogEntry> readJsonStream = readAllLogEntriesFromFile();
		timeLogger.setLogEntries(readJsonStream);
		return timeLogger;

	}

	public BufferedWriter getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(BufferedWriter bufferedWriter) {
		this.outputStream = bufferedWriter;
	}

	public void deleteCertainLogEntryInFile(LogEntry logEntryToLookFor)
			throws IOException, LogEntryValidationException {
		List<LogEntry> readJsonStreamOfLogEntries = readAllLogEntriesFromFile();

		if (readJsonStreamOfLogEntries.contains(logEntryToLookFor)) {
			readJsonStreamOfLogEntries.remove(logEntryToLookFor);
			writeLogEntriesToFile(readJsonStreamOfLogEntries);
		}
	}

	public void writeLogEntriesToFile(List<LogEntry> logEntries)
			throws IOException, LogEntryValidationException {

		OutputStreamWriter fileWriter = new OutputStreamWriter(
				new FileOutputStream(WEEKLOGGER_FILE, false), "UTF-8");
		PrintWriter outPutFile = new PrintWriter(fileWriter);

		for (LogEntry le : logEntries) {
			String jsonString = jsonObjectHandler.toJson(le);
			outPutFile.println(jsonString);
		}

		outPutFile.close();
	}

	public boolean isLogEntryInFile(LogEntry testLogEntry) throws IOException {
		List<LogEntry> logEntriesFromFileToUpdate = readAllLogEntriesFromFile();
		if (logEntriesFromFileToUpdate.contains(testLogEntry))
			return true;

		return false;

	}

	public boolean fileHasNoLogEntries() throws IOException {
		return readAllLogEntriesFromFile().size()==0;
	}

}
