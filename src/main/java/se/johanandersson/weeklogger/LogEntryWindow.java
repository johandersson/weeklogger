package se.johanandersson.weeklogger;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

@SuppressWarnings("serial")
/**
 * 
 * Window to show all the entries that you have logged sorted by week.
 * 
 * @author Johan Andersson
 * 
 *
 */
public class LogEntryWindow extends JFrame implements ActionListener {

	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 300;
	private static LogEntryWindow INSTANCE;
	private JComboBox weekSelector;
	private JComboBox yearSelector;
	private JButton generateReportButton;
	private JLabel totalTimeInfo;
	private JLabel totalTimeForCertainWeek;
	private LogEntryTableModel logEntryTableModel;
	private JTable logEntryTable;
	private JRadioButton filterByAllWeeks;
	private JRadioButton filterByCertainWeekSelected;
	private ButtonGroup fiterWeeksRadioButtonGroup;
	private JPopupMenu popup;
	private JMenuItem deleteLogEntry;
	private PopupListener popupListener;
	private JPanel weekAndYearSelectorPanel;
	private JPanel totalTimeInfoPanel;
	private JPanel generateReportButtonPanel;
	private Logger logger;
	private JScrollPane logEntryTableScrollPane;

	public void update() throws IOException {
		updateTotalTimeLabel();
		updateTotalTimeForCertainWeekLabel();
		updateTable();
		updateYearAndWeekSelectors();
	}

	private LogEntryWindow() throws IOException {

		setUpLogger();
		setSizeAndLayout();

		weekAndYearSelectorPanel = new JPanel(new MigLayout("wrap 4"));
		totalTimeInfoPanel = new JPanel(new MigLayout("wrap 2"));
		generateReportButtonPanel = new JPanel(new MigLayout());
		
		addGenereateReportButton();

		buildRadioButtonGroup();
		addWeekAndYearSelectorPanel();

		this.add(weekAndYearSelectorPanel);
		addTotalTimeLabels();
		this.add(totalTimeInfoPanel);

		logEntryTableScrollPane = createLogEntryTable();

		this.add(logEntryTableScrollPane);	
		this.add(generateReportButtonPanel);

		createPopupMenu();

		this.setTitle("Veckologgaren - översikt");
		pack();
		this.setVisible(true);

	}

	private void addGenereateReportButton() {
		generateReportButton= new JButton("Generera rapport");
		generateReportButton.addActionListener(new GenerateReportButtonListener());
		generateReportButtonPanel.add(generateReportButton);
	}

	private void setUpLogger() {
		logger = Logger.getLogger(LogEntryWindow.class);
		BasicConfigurator.configure();
		logger.removeAllAppenders();
		logger.info("Created LogEntryWindow");
	}

	protected JRadioButton getFilterByAllWeeks() {
		return filterByAllWeeks;
	}

	protected JRadioButton getFilterByCertainWeekSelected() {
		return filterByCertainWeekSelected;
	}

	public JComboBox getWeekSelector() {
		return weekSelector;
	}

	protected JPopupMenu getPopup() {
		return popup;
	}

	protected JComboBox getYearSelector() {
		return yearSelector;
	}

	protected JTable getLogEntryTable() {
		return logEntryTable;
	}

	protected static LogEntryWindow getInstance() throws IOException {
		if (INSTANCE == null) {
			INSTANCE = new LogEntryWindow();
		}
		return INSTANCE;
	}

	private void setSizeAndLayout() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(false);
		setTitle("Resultat");
		setLayout(new MigLayout("wrap 1"));
	}

	private void buildRadioButtonGroup() {
		fiterWeeksRadioButtonGroup = new ButtonGroup();
		filterByAllWeeks = new JRadioButton("Alla veckor", true);
		filterByCertainWeekSelected = new JRadioButton("Välj viss vecka");
		filterByAllWeeks.setMnemonic(KeyEvent.VK_A);
		filterByCertainWeekSelected.setMnemonic(KeyEvent.VK_V);

		fiterWeeksRadioButtonGroup.add(filterByAllWeeks);
		fiterWeeksRadioButtonGroup.add(filterByCertainWeekSelected);
		weekAndYearSelectorPanel.add(filterByAllWeeks, "wrap");
		weekAndYearSelectorPanel.add(filterByCertainWeekSelected);

		RadioButtonListener filterWeeksRadioButtonListener = new RadioButtonListener();
		filterByAllWeeks.addActionListener(filterWeeksRadioButtonListener);
		filterByCertainWeekSelected
				.addActionListener(filterWeeksRadioButtonListener);

	}

	private void createPopupMenu() {
		popup = new JPopupMenu();
		deleteLogEntry = new JMenuItem("Ta bort");
		deleteLogEntry.addActionListener(this);
		popup.add(deleteLogEntry);
		popup.add(deleteLogEntry);
		popupListener = new PopupListener();
	}

	private void addWeekAndYearSelectorPanel() throws IOException {
		LogEntryHandler logEntryHandler = new LogEntryHandler();
		WeekLoggerFileHandler weekLoggerFileHandler = new WeekLoggerFileHandler();
		logEntryHandler = weekLoggerFileHandler.getAllLogEntriesFromFile();
		addWeeksToComboBox(logEntryHandler);
		addYearsToComboBox(logEntryHandler);
		yearSelector.addActionListener(new YearComboBoxListener());
		weekSelector.addActionListener(new WeekComboBoxListener());
		weekSelector.setEnabled(false);

	}
	
	private void updateYearAndWeekSelectors() throws IOException{
		LogEntryHandler logEntryHandler = new LogEntryHandler();
		WeekLoggerFileHandler weekLoggerFileHandler = new WeekLoggerFileHandler();
		logEntryHandler = weekLoggerFileHandler.getAllLogEntriesFromFile();
		Object[] listOfWeeks = logEntryHandler.getListOfWeeks().toArray();
		Object[] listOfYears = logEntryHandler.getListOfYears().toArray();
		weekSelector.removeAllItems();
		weekSelector.setModel(new DefaultComboBoxModel(listOfWeeks));
		yearSelector.removeAllItems();
		yearSelector.setModel(new DefaultComboBoxModel(listOfYears));
	}

	private void addWeeksToComboBox(LogEntryHandler logEntryHandler) {
		Object[] listOfWeeks = logEntryHandler.getListOfWeeks().toArray();
		weekSelector = new JComboBox(listOfWeeks);
		weekAndYearSelectorPanel.add(weekSelector);
	}

	private void addYearsToComboBox(LogEntryHandler logEntryHandler) {
		Object[] listOfWeeks = logEntryHandler.getListOfYears().toArray();
		yearSelector = new JComboBox(listOfWeeks);
		weekAndYearSelectorPanel.add(yearSelector);
	}

	private void addTotalTimeLabels() throws IOException {
		totalTimeInfo = new JLabel("");
		totalTimeForCertainWeek = new JLabel("");
		updateTotalTimeLabel();
		updateTotalTimeForCertainWeekLabel();
		totalTimeInfoPanel.add(totalTimeInfo, "wrap");
		totalTimeInfoPanel.add(totalTimeForCertainWeek);

	}

	private void updateTotalTimeLabel() throws IOException {
		LogEntryHandler logEntryHandler = new LogEntryHandler();
		Time totalTimeOfAllLogEntries = logEntryHandler
				.getTotalTimeOfAllLogEntries();
		totalTimeInfo.setText("Totalt arbetad tid: "
				+ totalTimeOfAllLogEntries.toString());
	}

	public void updateTotalTimeForCertainWeekLabel() throws IOException {
		Integer selectedWeek = getSelectedWeek();

		if (selectedWeek != null) {

			setTotalTimeForCertainWeekLabel(selectedWeek);
		}

	}

	private void setTotalTimeForCertainWeekLabel(Integer selectedWeek)
			throws IOException {
		LogEntryHandler logEntryHandler = new LogEntryHandler();
		
		Time totalTimeOfLogEntriesForCertainWeek = logEntryHandler
				.getTotalTimeOfLogEntriesForCertainWeek(selectedWeek);
		
		totalTimeForCertainWeek.setText("Total arbetad tid för vecka "
				+ selectedWeek + ": "
				+ totalTimeOfLogEntriesForCertainWeek.toString());
	}

	private JScrollPane createLogEntryTable() throws IOException {
		setUpLogEntryTableModel();
		setUpLogEntryTable();
		addLogEntryTableSelectionListener();
		JScrollPane logEntryTableScollPane = new JScrollPane(logEntryTable);
		return logEntryTableScollPane;
	}

	private void addLogEntryTableSelectionListener() {
		ListSelectionModel logEntrySelectionModel = logEntryTable
				.getSelectionModel();
		logEntrySelectionModel
				.addListSelectionListener(new LogEntryTableSelectionListener());
	}

	private void setUpLogEntryTable() {
		logEntryTable = new JTable(logEntryTableModel);
		Dimension dimension = new Dimension(400, 400);
		logEntryTable.setPreferredScrollableViewportSize(dimension);
		logEntryTable.setFillsViewportHeight(true);
		logEntryTable.addMouseListener(new PopupListener());
		logEntryTable.setSelectionMode(0);
	}

	private void setUpLogEntryTableModel() throws IOException {
		List<LogEntry> updateLogEntryTableBasedOnSelectedWeekAndYear = this
				.updateLogEntryTableBasedOnSelectedWeekAndYear();
		logEntryTableModel = new LogEntryTableModel(
				updateLogEntryTableBasedOnSelectedWeekAndYear);
	}

	private void deleteLogEntry() throws IOException,
			LogEntryValidationException {
		WeekLoggerFileHandler weekLoggerFileHandler = new WeekLoggerFileHandler();
		int selectedRow = getSelectedLogEntryRow();
		LogEntry selectedLogEntryFromTable = getSelectedLogEntryFromTable(selectedRow);
		weekLoggerFileHandler
				.deleteCertainLogEntryInFile(selectedLogEntryFromTable);
		update();
	}

	private void updateTable() throws IOException {
		List<LogEntry> updateLogEntryTableBasedOnSelectedWeekAndYear = this
				.updateLogEntryTableBasedOnSelectedWeekAndYear();
		updateLogEntryTableWithEntries(updateLogEntryTableBasedOnSelectedWeekAndYear);
	}

	private List<LogEntry> updateLogEntryTableBasedOnSelectedWeekAndYear()
			throws IOException {
		WeekLoggerFileHandler weekLoggerFileHandler = new WeekLoggerFileHandler();
		LogEntryHandler logEntryHandler = new LogEntryHandler();
		List<LogEntry> updatedLogEntries = weekLoggerFileHandler
				.readAllLogEntriesFromFile();
		if (weekAndYearNotNull()) {
			if (filterByAllWeeks.isSelected()) {

				updatedLogEntries = logEntryHandler
						.getLogEntriesWithSameYear(Integer.valueOf(this
								.getSelectedYear()));
			} else {
				updatedLogEntries = logEntryHandler
						.getLogEntriesWithSameYearAndWeek(
								Integer.valueOf(this.getSelectedYear()),
								this.getSelectedWeek());
			}

		}

		return updatedLogEntries;
	}

	private boolean weekAndYearNotNull() {
		Integer selectedWeek = this.getSelectedWeek();
		String selectedYear = this.getSelectedYear();
		return selectedWeek != null && !StringUtils.isEmpty(selectedYear);
	}

	public void updateLogEntryTableWithEntries(List<LogEntry> logEntries) {
		logEntryTableModel.setLogEntryTable(logEntries);
		logEntryTableModel.fireTableDataChanged();
	}

	public int getSelectedLogEntryRow() {
		int viewRow = logEntryTable.getSelectedRow();
		if (viewRow < 0) {
			return 0;
		} else {
			return viewRow;
		}
	}

	public LogEntry getSelectedLogEntryFromTable(int index) {
		List<LogEntry> logEntries = logEntryTableModel.getLogEntryTable();
		LogEntry logEntry = logEntries.get(index);
		return logEntry;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deleteLogEntry) {
			int answer = JOptionPane.showConfirmDialog(this,
					"Vill du ta bort raden?");
			if (answer == 0)
				try {
					tryToDeleteLogEntry();
				} catch (LogEntryValidationException e1) {
					// TODO Auto-generated catch block
					logger.info("Could not validate deleted logentry!");
					e1.printStackTrace();
				}
		}

	}

	private void tryToDeleteLogEntry() throws LogEntryValidationException {
		try {
			deleteLogEntry();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	protected Integer getSelectedWeek() {
		if(weekSelector.getSelectedItem()!=null)
			return (Integer) weekSelector.getSelectedItem();
		else
			return DateTimeUtils.getCurrentWeek();
	}

	protected String getSelectedYear() {
		if (yearSelector.getSelectedItem() != null)
			return (String) yearSelector.getSelectedItem();
		else
			return DateTimeUtils.getCurrentYear();
	}

	private class LogEntryTableSelectionListener implements
			ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {

		}

	}

	public List<LogEntry> getLogEntryTableModel() {
		return logEntryTableModel.getLogEntryTable();
	}

}
