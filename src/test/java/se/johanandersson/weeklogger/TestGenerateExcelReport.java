package se.johanandersson.weeklogger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;


public class TestGenerateExcelReport {
    private ExcelFileGenerator xlsGen;

    @BeforeSuite
    public void foo() throws IOException {
        xlsGen = new ExcelFileGenerator();
    }

    @Test
    public void testGetFileName() {

        assertTrue(xlsGen instanceof ExcelFileGenerator);
        assertEquals(xlsGen.WEEKLOGGER_REPORT_XLS, "weeklogger-report.xlsx");
    }

    @Test
    public void testFileExistsAfterCreation() throws IOException {
        File f = new File(xlsGen.WEEKLOGGER_REPORT_XLS);
        assertTrue(f.exists());
    }

    @Test
    public void testCreateWorkBookAndSheet() throws IOException {
        assertEquals(xlsGen.getSheet().getSheetName(), "veckologgaren");
        assertEquals(xlsGen.getWorkbook().getSheetName(0), "veckologgaren");
    }


    @Test
    public void testCreateTitleRow() throws IOException {
        Sheet sheet = xlsGen.getSheet();
        Row titleRow = xlsGen.createTitleRow();
        assertEquals(titleRow.getSheet(), sheet);
        assertEquals(titleRow.getCell(0).getStringCellValue(), "Vecka");
    }

    @Test
    public void testReadTitleRow() throws IOException {
        xlsGen.createTitleRow();
        Sheet sheet = xlsGen.getSheet();
        Row row = sheet.getRow(0);
        assertEquals(row.getCell(0).getStringCellValue(), "Vecka");
        assertEquals(row.getCell(1).getStringCellValue(), "Datum");
        assertEquals(row.getCell(2).getStringCellValue(), "Start");
        assertEquals(row.getCell(3).getStringCellValue(), "Slut");
        assertEquals(row.getCell(4).getStringCellValue(), "Total");
        assertEquals(row.getCell(5).getStringCellValue(), "Kommentar");
    }


    @Test
    public void testReadLogEntryRow() throws IOException, LogEntryValidationException {
        xlsGen.createLogEntryRow(createTestLogEntry(),1);
        Sheet sheet = xlsGen.getSheet();
        Row row = sheet.getRow(1);
        assertEquals(row.getCell(0).getNumericCellValue(), 21.0);
        assertEquals(row.getCell(1).getStringCellValue(), "2012-12-12");
        assertEquals(row.getCell(2).getStringCellValue(), "00:01:00");
        assertEquals(row.getCell(3).getStringCellValue(), "01:00:00");
        assertEquals(row.getCell(4).getStringCellValue(), "01:01:00");
        assertEquals(row.getCell(5).getStringCellValue(), "foo");
    }

    @Test
    public void testFii() throws LogEntryValidationException, IOException {
        List logEntries = createCorrectTestLogEntries();

        xlsGen.generateTable(logEntries);
    }


    public LogEntry createTestLogEntry() throws LogEntryValidationException {
        LogEntry le1 = new LogEntry();
        le1.setComment("foo");
        le1.setLogDate("2012-12-12");
        le1.setYear(2012);
        le1.setWeek(21);

        // Not used for calculation
        le1.setStartTime("00:01:00");
        le1.setStopTime("01:00:00");
        le1.setTotalTime(new Time(1, 1, 0));
        return le1;
    }


    static List<LogEntry> createCorrectTestLogEntries()
            throws LogEntryValidationException {

        List<LogEntry> logEntries = new ArrayList<LogEntry>();

        for (int i = 0; i < 100; i++) {
            LogEntry logEntry = new LogEntry();
            logEntry.setComment("Test" + i);
            genTestLogDate(logEntry);

            ValidateLogEntry v = new ValidateLogEntry();
            v.validate(logEntry);
            logEntries.add(logEntry);
        }

        return logEntries;

    }

    private static LogEntry genTestLogDate(LogEntry logEntry)
            throws LogEntryValidationException {

        logEntry.setLogDate(DateTimeUtils.getRandomDateString());
        logEntry.setYear(DateTimeUtils.genRandomYear());
        logEntry.setWeek(DateTimeUtils.genRandomWeek());

        logEntry.setStartTime("12:00:01");
        logEntry.setStopTime("13:04:01");
        logEntry.setTotalTime(new Time(1, 4, 0));

        return logEntry;
    }


}
