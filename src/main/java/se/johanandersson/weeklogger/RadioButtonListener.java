package se.johanandersson.weeklogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class RadioButtonListener implements ActionListener {

    private LogEntryHandler logEntryHandler;

    public RadioButtonListener() throws IOException {
        logEntryHandler = new LogEntryHandler();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JComboBox weekSelector = null;
        JRadioButton filterByAllWeeks = null;
        JRadioButton filterByCertainWeekSelected = null;
        try {
            weekSelector = LogEntryWindow.getInstance().getWeekSelector();
            filterByAllWeeks = LogEntryWindow.getInstance().getFilterByAllWeeks();
            filterByCertainWeekSelected = LogEntryWindow.getInstance().getFilterByCertainWeekSelected();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        if (e.getSource() == filterByAllWeeks) {
            try {
                filterByAllWeeks(weekSelector);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

        if (e.getSource() == filterByCertainWeekSelected) {
            filterByCertainWeek(weekSelector);

        }

    }

    private void filterByCertainWeek(JComboBox weekSelector) {
        weekSelector.setEnabled(true);
        if (weekSelector.getSelectedItem() != null) {
            try {
                final var selectedYear = LogEntryWindow.getInstance().getSelectedYear();
                LogEntryWindow.getInstance().updateWeekSelectorBasedOnYear(selectedYear);
                final var selectedWeek = LogEntryWindow.getInstance().getSelectedWeek();
                List<LogEntry> logEntriesWithSameYearAndWeek = logEntryHandler.getLogEntriesWithSameYearAndWeek(selectedYear, selectedWeek);
                LogEntryWindow.getInstance().updateLogEntryTableWithEntries(logEntriesWithSameYearAndWeek);
                LogEntryWindow.getInstance().setTotalTimeLabelBasedOnWeekAndYear(selectedYear, selectedWeek);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private void filterByAllWeeks(JComboBox weekSelector) throws IOException {
        weekSelector.setEnabled(false);
        int year = LogEntryWindow.getInstance().getSelectedYear();
        try {
            List<LogEntry> logEntriesWithSameYear = logEntryHandler.getLogEntriesWithSameYear(year);
            LogEntryWindow.getInstance().updateLogEntryTableWithEntries(logEntriesWithSameYear);
            LogEntryWindow.getInstance().setTotalTimeLabelBasedOnYear(year);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}