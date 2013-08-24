package se.johanandersson.weeklogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.joda.time.DateTime;

@SuppressWarnings("serial")
/**
 * Model class for the table where all LogEntries for every week is presented.
 * @author Johan Andersson
 *
 */
public class LogEntryTableModel extends AbstractTableModel {

	private List<LogEntry> logEntryTable;

	public LogEntryTableModel(List<LogEntry> logEntryTable) {
		this.setLogEntryTable(logEntryTable);

	}

	private String[] columnNames = { "Datum", "Vecka", "Från", "Till", "Total",
			"Kommentar" };

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (getLogEntryTable() != null) {
			return getLogEntryTable().size();
		} else
			return 0;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public String getValueAt(int row, int col) {
		String logEntryObjectToReturn;
		switch (col) {
		case 0:
			logEntryObjectToReturn = getSelectedRowEntry(row).getLogDate();
			break;

		case 1:
			logEntryObjectToReturn = String.valueOf(getSelectedRowEntry(row)
					.getWeek());
			break;

		case 2:
			logEntryObjectToReturn = getSelectedRowEntry(row).getStartTime();
			break;

		case 3:
			logEntryObjectToReturn = getSelectedRowEntry(row).getStopTime();
			break;

		case 4:
			logEntryObjectToReturn = getSelectedRowEntry(row).getTotalTime()
					.toString();
			break;

		case 5:
			logEntryObjectToReturn = getSelectedRowEntry(row).getComment();
			break;

		default:
			logEntryObjectToReturn = "Invalid row!";
			break;

		}

		return logEntryObjectToReturn;
	}

	private LogEntry getSelectedRowEntry(int row) {
		return getLogEntryTable().get(row);
	}

	public List<LogEntry> getLogEntryTable() {
		return logEntryTable;
	}

	public void setLogEntryTable(List<LogEntry> logEntryTable) {
		Collections.sort(logEntryTable);
		// sortLogEntryTable(logEntryTable);
		this.logEntryTable = logEntryTable;
	}

	private void sortLogEntryTable(List<LogEntry> let) {
		ArrayList<DateTime> dtList = new ArrayList<DateTime>();

		for (LogEntry l : let) {
			DateTime dt = new DateTime(l.getLogDate());
			dtList.add(dt);
		}

		Collections.sort(dtList);

		for (DateTime dt : dtList) {
			System.out.println(String.valueOf(dt.getWeekOfWeekyear()) + ":"
					+ String.valueOf(dt.getYear()) + "\n");
		}

	}

}
