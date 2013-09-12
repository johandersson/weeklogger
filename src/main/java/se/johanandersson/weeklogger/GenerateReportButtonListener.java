package se.johanandersson.weeklogger;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import se.johanandersson.weeklogger.itext.PDFHandler;



import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfFileSpecification;

public class GenerateReportButtonListener implements ActionListener {

	private File pdfFile;

	@Override
	public void actionPerformed(ActionEvent e) {
		PDFHandler pdf = new PDFHandler();
		try {
			pdf.createFile();
			pdf.openDocument();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<LogEntry> readAllLogEntriesFromFile;
		try {
			if (!WeekLoggerFileHandler.getInstance().fileHasNoLogEntries()) {
				List<LogEntry> logEntries= LogEntryWindow.getInstance().getLogEntryTableModel();
				pdf.createTableForCertainWeek(logEntries);
				pdf.closeDocument();
				openPDFWithInstalledReader(pdf);
			}
			
			else{
				JOptionPane.showMessageDialog(WeekLoggerWindow.getInstance(), "Det finns inga loggade tider att skapa en rapport av!");
			}
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LogEntryValidationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (PdfGeneratorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private void openPDFWithInstalledReader(PDFHandler pdf)
			throws HeadlessException, IOException, LogEntryValidationException {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.OPEN)) {
				openPdfFile(pdf, desktop);
			}
		}

		else {
			JOptionPane
					.showMessageDialog(
							WeekLoggerWindow.getInstance(),
							"Stöd sakas för att öppna pdf-filen direkt i förvalt program. Försök att öppna filen manuellt från: "
									+ pdfFile.getAbsolutePath());
		}
	}

	private void openPdfFile(PDFHandler pdf, Desktop desktop)
			throws IOException, LogEntryValidationException {
		pdfFile = new File(pdf.getFileName()); // path to pdf file
		try {
			desktop.open(pdfFile);
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(WeekLoggerWindow.getInstance(),
							"Kunde inte öppna pdf-fil. Se till att du har en pdf-läsare installerad.");
		}
	}

}
