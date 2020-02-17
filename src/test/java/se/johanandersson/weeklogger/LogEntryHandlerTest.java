package se.johanandersson.weeklogger;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.testng.Assert;

import org.testng.annotations.Test;
public class LogEntryHandlerTest {
    @Test
    public void testGetListOfYears() throws ParseException, IOException, LogEntryValidationException {
        LogEntryHandler logEntryHandler = new LogEntryHandler();

        var logEntry1 = getTestLogEntryWithYear(2019);
        var logEntry2 = getTestLogEntryWithYear(2020);

        logEntryHandler.setLogEntries(List.of(logEntry1, logEntry2));
        Assert.assertEquals(logEntryHandler.getListOfYears(), List.of(2019,2020));
    }

    private LogEntry getTestLogEntryWithYear(int year) throws LogEntryValidationException {
        LogEntry logEntry = new LogEntry();
        logEntry.setYear(year);
        return logEntry;
    }
}
