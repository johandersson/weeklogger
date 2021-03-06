package se.johanandersson.weeklogger.itext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import se.johanandersson.weeklogger.LogEntry;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import se.johanandersson.weeklogger.LogEntryCalculator;
import se.johanandersson.weeklogger.LogEntryHandler;
import se.johanandersson.weeklogger.Time;

public class PDFCreator {

    private static final int NUMBER_OF_COLUMNS = 6;
    private static final String FILE_NAME = "report.pdf";
    private static Font headerFont = new Font(Font.FontFamily.HELVETICA, 12,
            Font.BOLD);
    private Document document = new Document();

    public String getFileName() {
        return FILE_NAME;
    }

    public PdfWriter createFile() throws FileNotFoundException, DocumentException {
        return getPDFWriter();
    }

    public FileOutputStream getFileOutPutStream() throws FileNotFoundException {
        return new FileOutputStream(getFileName());
    }

    public Document getDocument() {
        return document;
    }

    public PdfWriter getPDFWriter() throws FileNotFoundException, DocumentException {
        return PdfWriter.getInstance(getDocument(), getFileOutPutStream());
    }

    public void addMetaData() {
        document = getDocument();
        document.addTitle("Veckorapport");
        document.addSubject("Tidrapport per vecka");
        document.addKeywords("veckologgaren");
        document.addKeywords("tidrapport");
        document.addAuthor("Weeklogger");

    }

    public void createTables(List<Integer> listOfWeeks, int year) throws DocumentException, IOException {

        LogEntryHandler logEntryHandler = new LogEntryHandler();

        for (Integer w : listOfWeeks) {
            List<LogEntry> logEntriesWithSameYearAndWeek = logEntryHandler.getLogEntriesWithSameYearAndWeek(year, w);
            addLogEntryToCurrentTable(logEntriesWithSameYearAndWeek, w);
        }

    }

    public void createTablesWithOneWeek(int week, int year) throws DocumentException, IOException {
        LogEntryHandler logEntryHandler = new LogEntryHandler();
        List<LogEntry> logEntriesWithSameYearAndWeek = logEntryHandler.getLogEntriesWithSameYearAndWeek(year, week);
        addLogEntryToCurrentTable(logEntriesWithSameYearAndWeek, week);
    }

    private void appendTableToDocument(PdfPTable table) throws DocumentException {
        document.add(table);
    }

    private void addLogEntryToCurrentTable(List<LogEntry> logEntries, Integer w) throws DocumentException {
        float widths[]= {0.75f,1.5f,1,1,1,2.5f};
        PdfPTable table = new PdfPTable(widths);
        table.setSpacingAfter(10);
        table.setKeepTogether(true);
        createTableHeader(table);
        for (LogEntry logEntry : logEntries) {
            String week = String.valueOf(logEntry.getWeek());
            table.addCell(week);
            table.addCell(logEntry.getLogDate());
            table.addCell(logEntry.getStartTime());
            table.addCell(logEntry.getStopTime());
            table.addCell(logEntry.getTotalTime().toString());
            table.addCell(logEntry.getComment());
        }
        addTotalTime(logEntries, table, w);
        appendTableToDocument(table);
    }

    public void openDocument() {
        document.open();
    }


    public void closeDocument() {
        document.close();

    }

    private void addTotalTime(List<LogEntry> logEntryList, PdfPTable table, Integer w) {
        final var totalTime = LogEntryCalculator.calculateTotalTimeOfLogEntries(logEntryList);
        var totalTimeHeader = "Total tid vecka " + w + ": ";
        var totalTimeParagraph = new Paragraph(totalTime.toString(), headerFont);
        var totalTimeHeaderParagraph = new Paragraph(totalTimeHeader);
        var totalTimePhrase = new Phrase();
        totalTimePhrase.add(totalTimeHeaderParagraph);
        totalTimePhrase.add(totalTimeParagraph);

        var totalTimeCell = new PdfPCell(totalTimePhrase);
        totalTimeCell.setColspan(6);
        table.addCell(totalTimeCell);
    }

    private void createTableHeader(PdfPTable table) {
        table.addCell(new Paragraph("Vecka", headerFont));
        table.addCell(new Paragraph("Datum", headerFont));
        table.addCell(new Paragraph("Start", headerFont));
        table.addCell(new Paragraph("Slut", headerFont));
        table.addCell(new Paragraph("Totaltid", headerFont));
        table.addCell(new Paragraph("Kommentar", headerFont));
    }
}
