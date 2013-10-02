package se.johanandersson.weeklogger.itext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import se.johanandersson.weeklogger.Time;

public class PDFHandler {

    private static final int NUMBER_OF_COLUMNS = 6;
    private static final String FILE_NAME = "report.pdf";
    private Document document = new Document();
    private PdfPTable currentTable = new PdfPTable(NUMBER_OF_COLUMNS);
    private static Font headerFont = new Font(Font.FontFamily.HELVETICA, 12,
            Font.BOLD);

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
        document.addAuthor("Veckologgaren");
        document.addCreator("Johan Andersson(c)");

    }

    public void createTableForCertainWeek(List<LogEntry> logEntryList) throws DocumentException {
        createTableHeader();
        Collections.sort(logEntryList);

        for (LogEntry logEntry : logEntryList) {
            addLogEntryToCurrentTable(logEntry);
        }

        addTotalTime(logEntryList);
        appendTableToDocument(currentTable);
    }

    private void appendTableToDocument(PdfPTable table) throws DocumentException {
        document.add(table);
    }

    private void addLogEntryToCurrentTable(LogEntry logEntry) {
        final String week = String.valueOf(logEntry.getWeek());
        currentTable.addCell(week);
        currentTable.addCell(logEntry.getLogDate());
        currentTable.addCell(logEntry.getStartTime());
        currentTable.addCell(logEntry.getStopTime());
        currentTable.addCell(logEntry.getTotalTime().toString());
        currentTable.addCell(logEntry.getComment());
    }

    public void openDocument() {
        document.open();
    }

    public PdfPTable getTable() {
        return currentTable;
    }

    public void closeDocument() {
        document.close();

    }

    private void addTotalTime(List<LogEntry> logEntryList) {
        final Time totalTime = LogEntryCalculator.calculateTotalTimeOfLogEntries(logEntryList);
        String totalTimeHeader = "Total tid: ";
        final String totalTimeText = totalTimeHeader + totalTime.toString();
        PdfPCell totalTimeCell = new PdfPCell(new Phrase(totalTimeText));
        totalTimeCell.setColspan(6);
        currentTable.addCell(totalTimeCell);
    }

    private void createTableHeader() {
        currentTable.addCell(new Paragraph("Vecka", headerFont));
        currentTable.addCell(new Paragraph("Datum", headerFont));
        currentTable.addCell(new Paragraph("Start", headerFont));
        currentTable.addCell(new Paragraph("Slut",headerFont));
        currentTable.addCell(new Paragraph("Totaltid",headerFont));
        currentTable.addCell(new Paragraph("Kommentar",headerFont));
    }
}
