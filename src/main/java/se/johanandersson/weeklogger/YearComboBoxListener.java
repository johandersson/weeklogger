package se.johanandersson.weeklogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class YearComboBoxListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        List<LogEntry> filteredLogEntries = Collections.emptyList();


        if (!isFilterByAllWeeksSelected()) {

            try {
                final List<Integer> listOfWeeksInAYear = LogEntryWindow.getInstance()
                        .getLogEntryHandler().getListOfWeeksInAYear(LogEntryWindow.getInstance().getSelectedYear());

                LogEntryWindow.getInstance().updateWeeks(listOfWeeksInAYear);


                filteredLogEntries = LogEntryWindow.getInstance()
                        .getLogEntryHandler()
                        .getLogEntriesWithSameYearAndWeek(LogEntryWindow.getInstance().getSelectedYear(), LogEntryWindow.getInstance().getSelectedWeek());
                LogEntryWindow.getInstance().updateLogEntryTableWithEntries(
                        filteredLogEntries);


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            try {
                filteredLogEntries = getLogEntriesWithSameYear(LogEntryWindow
                        .getInstance().getLogEntryHandler(), LogEntryWindow.getInstance().getSelectedYear());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            try {
                LogEntryWindow.getInstance().updateLogEntryTableWithEntries(
                        filteredLogEntries);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            LogEntryWindow.getInstance().updateTotalTimeLabel();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private List<LogEntry> getLogEntriesWithSameYear(
            LogEntryHandler logEntryHandler, int selectedYear) {
        List<LogEntry> logEntriesWithSameYear = null;
        try {
            logEntriesWithSameYear = logEntryHandler
                    .getLogEntriesWithSameYear(selectedYear);
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return logEntriesWithSameYear;
    }

    private boolean isFilterByAllWeeksSelected() {
        JRadioButton filterByAllWeeks = null;
        try {
            filterByAllWeeks = LogEntryWindow.getInstance()
                    .getFilterByAllWeeks();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return filterByAllWeeks.isSelected();
    }
}
