package se.johanandersson.weeklogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;


public class RadioButtonListener implements ActionListener {

	private WeekLoggerFileHandler weekLoggerFileHandler;

	public RadioButtonListener() {
		weekLoggerFileHandler = new WeekLoggerFileHandler();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox weekSelector = null;
		JRadioButton filterByAllWeeks = null;
		JRadioButton filterByCertainWeekSelected = null;
		try {
			weekSelector = LogEntryWindow.getInstance().getWeekSelector();
			JComboBox yearSelector = LogEntryWindow.getInstance().getYearSelector();
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
				int week = LogEntryWindow.getInstance().getSelectedWeek();
				String year = LogEntryWindow.getInstance().getSelectedYear();
				
				
				List<LogEntry> logEntries = weekLoggerFileHandler
						.readEntriesWithSameYearAndWeekFromFile(Integer.valueOf(year), week);
				LogEntryWindow.getInstance().updateLogEntryTableWithEntries(logEntries);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void filterByAllWeeks(JComboBox weekSelector) throws IOException {
		weekSelector.setEnabled(false);
		String year = LogEntryWindow.getInstance().getSelectedYear();
		try {
			LogEntryHandler allLogEntriesFromFile = weekLoggerFileHandler
					.getAllLogEntriesFromFile();
			List<LogEntry> logEntries = allLogEntriesFromFile.getLogEntriesWithSameYear(Integer.valueOf(year));
			LogEntryWindow.getInstance().updateLogEntryTableWithEntries(logEntries);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}