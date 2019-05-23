package se.johanandersson.weeklogger;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;





public class GenerateReportButtonListener implements ActionListener {


    @Override
    public void actionPerformed(ActionEvent e) {


        try {
            if (!WeekLoggerFileHandler.getInstance().fileHasNoLogEntries()) {
                List<LogEntry> logEntries = LogEntryWindow.getInstance().getLogEntryTableModel();
                LogEntryHandler logEntryHandler = new LogEntryHandler();
                int selectedYear = LogEntryWindow.getInstance().getSelectedYear();
                List<Integer> listOfWeeksInAYear = logEntryHandler.getListOfWeeksInAYear(selectedYear);
                if (LogEntryWindow.getInstance().getFilterByCertainWeekSelected().isSelected()) {

                } else {

                }


            } else {
                JOptionPane.showMessageDialog(WeekLoggerWindow.getInstance(), "Det finns inga loggade tider att skapa en rapport av!");
            }


        } catch (IOException |  HeadlessException | LogEntryValidationException | PdfGeneratorException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }


}
