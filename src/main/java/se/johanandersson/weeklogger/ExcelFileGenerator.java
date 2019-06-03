package se.johanandersson.weeklogger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelFileGenerator {
    public static final String WEEKLOGGER_REPORT_XLS = "weeklogger-report.xlsx";
    private String fileName;
    private Workbook workbook;
    private Sheet sheet;
    private OutputStream fileOut;


    public ExcelFileGenerator() throws IOException {
        fileOut = new FileOutputStream(WEEKLOGGER_REPORT_XLS);
        createWorkBook();
    }

    public void createWorkBook() throws IOException {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet();
        workbook.setSheetName(0, "veckologgaren");
    }


    public Row createTitleRow() throws IOException {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Vecka");
        row.createCell(1).setCellValue("Datum");
        row.createCell(2).setCellValue("Start");
        row.createCell(3).setCellValue("Slut");
        row.createCell(4).setCellValue("Total");
        row.createCell(5).setCellValue("Kommentar");
        writeToWorkBook();
        return row;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public void closeWorkBookAndFile() throws IOException {
        workbook.close();
        fileOut.close();
    }

    public void writeToWorkBook() throws IOException {
        workbook.write(fileOut);
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void createLogEntryRow(LogEntry logEntry, int index) throws IOException {
        Row row = getSheet().createRow(index);
        System.out.println(logEntry+":"+index);

        row.createCell(0).setCellValue(logEntry.getWeek());
        row.createCell(1).setCellValue(logEntry.getLogDate());
        row.createCell(2).setCellValue(logEntry.getStartTime());
        row.createCell(3).setCellValue(logEntry.getStopTime());
        row.createCell(4).setCellValue(logEntry.getTotalTime().toString());
        row.createCell(5).setCellValue(logEntry.getComment());

    }

    public List<LogEntry> getLogEntriesWithSameWeekAndYear(List<LogEntry> logEntries, int week, int year){
        return logEntries
                .stream()
                .filter(l -> l.getYear() == year)
                .filter(l->l.getWeek()==week)
                .collect(Collectors.toList());
    }


    public void generateTable(List<LogEntry> logEntries) throws IOException {
        Collections.sort(logEntries);
        for(LogEntry l:logEntries){
            createLogEntryRow(l, logEntries.indexOf(l)+1);

        }

        writeToWorkBook();
    }
}
