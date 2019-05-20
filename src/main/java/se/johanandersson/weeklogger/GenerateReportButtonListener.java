package se.johanandersson.weeklogger;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import se.johanandersson.weeklogger.itext.PDFCreator;


import com.itextpdf.text.DocumentException;

public class GenerateReportButtonListener implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {
        PDFCreator pdf = new PDFCreator();
        try {
            pdf.createFile();
            pdf.openDocument();
        } catch (FileNotFoundException | DocumentException e1) {
            e1.printStackTrace();
        }

        try {
            if (!WeekLoggerFileHandler.getInstance().fileHasNoLogEntries()) {
                List<LogEntry> logEntries = LogEntryWindow.getInstance().getLogEntryTableModel();
                LogEntryHandler logEntryHandler = new LogEntryHandler();
                int selectedYear = LogEntryWindow.getInstance().getSelectedYear();
                List<Integer> listOfWeeksInAYear = logEntryHandler.getListOfWeeksInAYear(selectedYear);
                if (LogEntryWindow.getInstance().getFilterByCertainWeekSelected().isSelected()) {
                    pdf.createTablesWithOneWeek(LogEntryWindow.getInstance().getSelectedWeek(), selectedYear);
                } else {
                    pdf.createTables(listOfWeeksInAYear, selectedYear);
                }
                pdf.closeDocument();
                PDFOpener pdfOpener = new PDFOpener();
                pdfOpener.openPDFWithInstalledReader(pdf);
            } else {
                JOptionPane.showMessageDialog(WeekLoggerWindow.getInstance(), "Det finns inga loggade tider att skapa en rapport av!");
            }


        } catch (IOException | DocumentException | HeadlessException | LogEntryValidationException | PdfGeneratorException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }


}
