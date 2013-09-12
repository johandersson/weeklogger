package se.johanandersson.weeklogger.itext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.johanandersson.weeklogger.LogEntry;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFHandler {
	private static final int NUMBER_OF_COLUMNS = 6;
	private static final String FILE_NAME = "report.pdf";
	private Document document = new Document();
	private PdfPTable currentTable = new PdfPTable(NUMBER_OF_COLUMNS);

	public String getFileName() {
		return FILE_NAME;
	}

	public PdfWriter createFile() throws FileNotFoundException, DocumentException {
		return getPDFWriter();
	}
	
	public FileOutputStream getFileOutPutStream() throws FileNotFoundException{
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
		Collections.sort(logEntryList);
		
		for(LogEntry logEntry:logEntryList){
			addLogEntryToCurrentTable(logEntry);
		}
		
		appendTableToDocument(currentTable);
	}

	private void appendTableToDocument(PdfPTable table) throws DocumentException {
		document.add(table);
	}

	private void addLogEntryToCurrentTable(LogEntry logEntry) {
		currentTable.addCell("Vecka "+String.valueOf(logEntry.getWeek()));
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

	

}
