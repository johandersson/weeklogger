package se.johanandersson.weeklogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;

public class WeekComboBoxListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        int year = 0;
        int week = DateTimeUtils.getCurrentWeek();
        try {
            year = LogEntryWindow.getInstance().getSelectedYear();
            week = LogEntryWindow.getInstance().getSelectedWeek();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }


        try {
            updateLogEntries(year, week);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        updateLabel();
    }

    private void updateLogEntries(int year, int week) throws IOException {
        List<LogEntry> entriesWithTheSameWeekAndYear = LogEntryWindow
                .getInstance().getLogEntryHandler()
                .getLogEntriesWithSameYearAndWeek(year, week);
        updateLogEntries(entriesWithTheSameWeekAndYear);
    }

    private void updateLabel() {
        try {
            LogEntryWindow.getInstance().updateTotalTimeLabel();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void updateLogEntries(List<LogEntry> entriesWithTheSameWeek) {
        try {
            LogEntryWindow.getInstance().updateLogEntryTableWithEntries(
                    entriesWithTheSameWeek);
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
    }

}