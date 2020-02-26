package se.johanandersson.weeklogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Johan Andersson
 */
public class WeekLoggerFileHandler {

    private BufferedWriter outputStream;
    private Gson gson;
    private static final String WEEKLOGGER_FILE = "weeklogger.json";
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
        gson = new Gson();
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
        List<LogEntry> logEntries = new ArrayList<>();

        try {
            logEntries = gson.fromJson(new FileReader(WEEKLOGGER_FILE),
                    new TypeToken<ArrayList<LogEntry>>() {}.getType());


        } catch (FileNotFoundException fnfe) {
            createOrReadWeekLoggerFile();
        }

        return logEntries;

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

        String json = gson.toJson(logEntries);
        outPutFile.println(json);
        outPutFile.close();
    }

    public boolean isLogEntryInFile(LogEntry testLogEntry) throws IOException {
        List<LogEntry> logEntries = readAllLogEntriesFromFile();
        if (logEntries.contains(testLogEntry))
            return true;

        return false;
    }

    public void writeLogEntryToFile(LogEntry logEntry)
            throws IOException, LogEntryValidationException {
        List<LogEntry> logEntries = readAllLogEntriesFromFile();
        logEntries.add(logEntry);
        writeLogEntriesToFile(logEntries);
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

    public void addLogEntry(LogEntry l) throws IOException, LogEntryValidationException {
        writeLogEntryToFile(l);
    }
}
