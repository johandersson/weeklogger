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
 * @author Johan Andersson
 */
public class WeekLoggerFileHandler {

    private BufferedWriter outputStream;
    private Gson jsonObjectHandler;
    private static final String WEEKLOGGER_FILE = "weeklogger.txt";
    private static WeekLoggerFileHandler INSTANCE;


    protected static WeekLoggerFileHandler getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new WeekLoggerFileHandler();
        }
        return INSTANCE;
    }

    public static String getWeekloggerFile() {
        return WEEKLOGGER_FILE;
    }

    private WeekLoggerFileHandler() throws IOException {
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


    public BufferedWriter getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(BufferedWriter bufferedWriter) {
        this.outputStream = bufferedWriter;
    }

    public void deleteCertainLogEntryInFile(LogEntry logEntryToLookFor)
            throws IOException, LogEntryValidationException {
        List<LogEntry> logEntries = readAllLogEntriesFromFile();

        if (logEntries.contains(logEntryToLookFor)) {
            logEntries.remove(logEntryToLookFor);
            writeLogEntriesToFile(logEntries);
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
        List<LogEntry> logEntries = readAllLogEntriesFromFile();
        if (logEntries.contains(testLogEntry))
            return true;

        return false;
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

    public boolean fileHasNoLogEntries() throws IOException {
        return readAllLogEntriesFromFile().isEmpty();
    }

    public void updateCertainLogEntryInFile(LogEntry logEntryToLookFor, LogEntry newLogEntry) throws IOException, LogEntryValidationException {
        List<LogEntry> logEntries = readAllLogEntriesFromFile();
        if (isLogEntryInFile(logEntryToLookFor)) {
            int index = logEntries.indexOf(logEntryToLookFor);
            logEntries.set(index, newLogEntry);
            writeLogEntriesToFile(logEntries);
        }
    }

    public void addLogEntry(LogEntry l) throws IOException {
        writeLogEntryToFileInJSONFormat(l);
    }
}
