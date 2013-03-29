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
		LogEntryHandler logEntryHandler = new LogEntryHandler();
		List<LogEntry> logEntriesWithSameYear = Collections.emptyList();
		
		try {
			JComboBox yearSelector = LogEntryWindow.getInstance().getYearSelector();
			JComboBox weekSelector = LogEntryWindow.getInstance().getWeekSelector();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String selectedYear = null;
		int week = DateTimeUtils.getCurrentWeek();
		try {
			selectedYear = LogEntryWindow.getInstance().getSelectedYear();
			week = LogEntryWindow.getInstance().getSelectedWeek();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		if(!isFilterByAllWeeksSelected()){
		
		try {
			logEntriesWithSameYear = logEntryHandler.getLogEntriesWithSameYearAndWeek(Integer.valueOf(selectedYear),week);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			LogEntryWindow.getInstance().updateLogEntryTableWithEntries(logEntriesWithSameYear);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
		else{
			List<LogEntry> logEntriesWithSameYearAndAllWeeks = getLogEntriesWithSameYearAndAllWeeks(logEntryHandler, selectedYear);
			try {
				LogEntryWindow.getInstance().updateLogEntryTableWithEntries(logEntriesWithSameYearAndAllWeeks);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}

	private List<LogEntry> getLogEntriesWithSameYearAndAllWeeks(
			LogEntryHandler logEntryHandler, String selectedYear) {
		List<LogEntry> logEntriesWithSameYear = null;
		try {
			logEntriesWithSameYear = logEntryHandler.getLogEntriesWithSameYear(Integer.valueOf(selectedYear));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logEntriesWithSameYear;
	}

	private boolean isFilterByAllWeeksSelected() {
		JRadioButton filterByAllWeeks = null;
		try {
			filterByAllWeeks = LogEntryWindow.getInstance().getFilterByAllWeeks();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return filterByAllWeeks.isSelected();
	}

}
