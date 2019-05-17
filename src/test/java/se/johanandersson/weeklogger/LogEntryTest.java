package se.johanandersson.weeklogger;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LogEntryTest {

    @Test
    public void dateBeforeTheOther() throws ParseException {
        String d1 = "2012-12-12";
        String d2 = "2013-12-31";
        Assert.assertTrue(DateTimeUtils.dateBeforeTheOther(d1, d2));
        Assert.assertFalse(DateTimeUtils.dateBeforeTheOther(d2, d1));
    }

    @Test
    public void foo() throws IOException{
        final List<LogEntry> allLogEntriesFromFile = WeekLoggerFileHandler.getInstance().readAllLogEntriesFromFile();
        LogEntryHandler l = new LogEntryHandler();
        l.setLogEntries(allLogEntriesFromFile);
        final List<LogEntry> logEntriesWithSameYear = l.getLogEntriesWithSameYear(2005);
        final List<Integer> listOfWeeks = l.getListOfWeeks();
        Assert.assertNotNull(listOfWeeks);
        
        for(Integer i: listOfWeeks){
            final List<LogEntry> logEntriesWithSameYearAndWeek = l.getLogEntriesWithSameYearAndWeek(2005, i);
        }
        
    }
}
