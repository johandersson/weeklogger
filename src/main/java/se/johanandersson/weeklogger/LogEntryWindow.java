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
	private JButton saveButton;
    private JLabel totalTimeLabel;
    private LogEntryTableModel logEntryTableModel;
    private JTable logEntryTable;
    private JRadioButton filterByAllWeeksInTheSelectedYear;
    private JRadioButton filterByCertainWeekTheSelectedYear;
    private ButtonGroup fiterWeeksRadioButtonGroup;
    private JPopupMenu popup;
    private JMenuItem deleteLogEntry;
    private PopupListener popupListener;
    private JPanel weekAndYearSelectorPanel;
    private JPanel totalTimeInfoPanel;
    private JPanel generateReportButtonPanel;
    private Logger logger;
    private JScrollPane logEntryTableScrollPane;
    private LogEntryHandler logEntryHandler;
	private ListSelectionModel listSelectionModel;

    public void update() throws IOException {
        logEntryHandler.resetLogEntries();
        updateTable();
        updateTotalTimeLabel();
        updateYearAndWeekSelectors();
    }

    private LogEntryWindow() throws IOException {

        logEntryHandler = new LogEntryHandler();

        setUpLogger();
        setSizeAndLayout();

        weekAndYearSelectorPanel = new JPanel(new MigLayout("wrap 4"));
        totalTimeInfoPanel = new JPanel(new MigLayout("wrap 2"));
        generateReportButtonPanel = new JPanel(new MigLayout());

        addGenereateReportButton();
        addSaveButton();
        buildRadioButtonGroup();

        addWeekAndYearSelectorPanel();

        this.add(weekAndYearSelectorPanel);
        addTotalTimeLabels();
        this.add(totalTimeInfoPanel);

        logEntryTableScrollPane = createLogEntryTable();
		listSelectionModel = logEntryTable.getSelectionModel();
		 listSelectionModel.setSelectionMode(
                        ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
  
        this.add(logEntryTableScrollPane);
        this.add(generateReportButtonPanel);

        createPopupMenu();

        this.setTitle("Veckologgaren - Översikt");
        pack();
        this.setVisible(true);



    }

    private void addGenereateReportButton() {
        generateReportButton = new JButton("Generera rapport");
        generateReportButton
                .addActionListener(new GenerateReportButtonListener());
        generateReportButtonPanel.add(generateReportButton);
    }
	
	private void addSaveButton() {
        saveButton = new JButton("Spara");
        saveButton
                .addActionListener(new SaveButtonListener());
				saveButton.setEnabled(false);
        generateReportButtonPanel.add(saveButton);
    }
	
	protected JButton getSaveButton() {
        return this.saveButton;
    }

    private void setUpLogger() {
        logger = Logger.getLogger(LogEntryWindow.class);
        BasicConfigurator.configure();
        getLogger().removeAllAppenders();
        getLogger().info("Created LogEntryWindow");
    }

    protected JRadioButton getFilterByAllWeeks() {
        return filterByAllWeeksInTheSelectedYear;
    }

    protected JRadioButton getFilterByCertainWeekSelected() {
        return filterByCertainWeekTheSelectedYear;
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

    private void buildRadioButtonGroup() throws IOException {
        fiterWeeksRadioButtonGroup = new ButtonGroup();
        filterByAllWeeksInTheSelectedYear = new JRadioButton("Alla veckor", true);
        filterByCertainWeekTheSelectedYear = new JRadioButton("Välj viss vecka");
        filterByAllWeeksInTheSelectedYear.setMnemonic(KeyEvent.VK_A);
        filterByCertainWeekTheSelectedYear.setMnemonic(KeyEvent.VK_V);

        fiterWeeksRadioButtonGroup.add(filterByAllWeeksInTheSelectedYear);
        fiterWeeksRadioButtonGroup.add(filterByCertainWeekTheSelectedYear);
        weekAndYearSelectorPanel.add(filterByAllWeeksInTheSelectedYear, "wrap");
        weekAndYearSelectorPanel.add(filterByCertainWeekTheSelectedYear);

        RadioButtonListener filterWeeksRadioButtonListener = new RadioButtonListener();
        filterByAllWeeksInTheSelectedYear.addActionListener(filterWeeksRadioButtonListener);
        filterByCertainWeekTheSelectedYear
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
        addWeeksToComboBox(logEntryHandler);
        addYearsToComboBox(logEntryHandler);
        yearSelector.addActionListener(new YearComboBoxListener());
        weekSelector.addActionListener(new WeekComboBoxListener());
        weekSelector.setEnabled(false);

    }

    protected void updateYearAndWeekSelectors() throws IOException {
        weekSelector.setModel(new DefaultComboBoxModel(logEntryHandler
                .getListOfWeeks().toArray()));
        yearSelector.setModel(new DefaultComboBoxModel(logEntryHandler
                .getListOfYears().toArray()));
    }

    protected void updateWeekSelectorBasedOnYear(int year) throws IOException {
        weekSelector.setModel(new DefaultComboBoxModel(logEntryHandler.getListOfWeeksInAYear(year).toArray()));
    }

    private void addWeeksToComboBox(LogEntryHandler logEntryHandler)
            throws IOException {
        Object[] listOfWeeks = logEntryHandler.getListOfWeeks().toArray();
        weekSelector = new JComboBox(listOfWeeks);
        weekAndYearSelectorPanel.add(weekSelector);
    }
    
    protected void updateWeeks(List<Integer> weeks){
        weekSelector.setModel(new DefaultComboBoxModel(weeks.toArray()));
    }

    private void addYearsToComboBox(LogEntryHandler logEntryHandler)
            throws IOException {
        Object[] listOfWeeks = logEntryHandler.getListOfYears().toArray();
        yearSelector = new JComboBox(listOfWeeks);
        weekAndYearSelectorPanel.add(yearSelector);
    }

    private void addTotalTimeLabels() throws IOException {
        totalTimeLabel = new JLabel("");
        updateTotalTimeLabel();
        totalTimeInfoPanel.add(totalTimeLabel);

    }

    public void updateTotalTimeLabel() throws IOException {
        Integer selectedYear = getSelectedYear();
        Integer selectedWeek = getSelectedWeek();

        if (filterByAllWeeksInTheSelectedYear.isSelected()) {
            setTotalTimeLabelBasedOnYear(selectedYear);
        } else {
            setTotalTimeLabelBasedOnWeekAndYear(selectedYear, selectedWeek);
        }

    }

    protected void setTotalTimeLabelBasedOnWeekAndYear(int year, int week)
            throws IOException {

        Time totalTimeOfCertainWeek = LogEntryCalculator.calculateTotalTimeOfLogEntries(logEntryHandler
                .getLogEntriesWithSameYearAndWeek(year, week));

        totalTimeLabel.setText("Total arbetad tid för vecka "
                + week + ", " + year + ": "
                + totalTimeOfCertainWeek.toString());
    }

    protected void setTotalTimeLabelBasedOnYear(int year)
            throws IOException {

        Time totalTimeOfCertainWeek = LogEntryCalculator.calculateTotalTimeOfLogEntries(logEntryHandler
                .getLogEntriesWithSameYear(year));

        totalTimeLabel.setText("Total arbetad tid för alla veckor "
                + year + ": "
                + totalTimeOfCertainWeek.toString());
    }

    private JScrollPane createLogEntryTable() throws IOException {
        setUpLogEntryTableModel();
        setUpLogEntryTable();
        addLogEntryTableSelectionListener();
        TablePopupEditor popupEditor = new TablePopupEditor();
        logEntryTable.getColumnModel().getColumn(5).setCellEditor( popupEditor );
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
        int[] selectedRows = getSelectedLogEntryRows();
		LogEntry selectedLogEntryFromTable=null;
		for(int i=0;i<selectedRows.length;i++){
			selectedLogEntryFromTable = getSelectedLogEntryFromTable(selectedRows[i]);
			logEntryHandler.deleteLogEntry(selectedLogEntryFromTable);
		}

        update();
    }

    private void updateTable() throws IOException {
        List<LogEntry> updateLogEntryTableBasedOnSelectedWeekAndYear = this
                .updateLogEntryTableBasedOnSelectedWeekAndYear();
        updateLogEntryTableWithEntries(updateLogEntryTableBasedOnSelectedWeekAndYear);

    }

    private List<LogEntry> updateLogEntryTableBasedOnSelectedWeekAndYear()
            throws IOException {
        List<LogEntry> updatedLogEntries = logEntryHandler.getLogEntries();
        if (weekAndYearNotNull()) {
            if (filterByAllWeeksInTheSelectedYear.isSelected()) {
                updatedLogEntries = logEntryHandler
                        .getLogEntriesWithSameYear(this.getSelectedYear());
            } else {
                updatedLogEntries = logEntryHandler
                        .getLogEntriesWithSameYearAndWeek(
                        this.getSelectedYear(), this.getSelectedWeek());
            }

        }

        return updatedLogEntries;
    }

    private boolean weekAndYearNotNull() {
        Integer selectedWeek = this.getSelectedWeek();
        int selectedYear = this.getSelectedYear();
        return selectedWeek != null && selectedYear > 0;
    }

    public void updateLogEntryTableWithEntries(List<LogEntry> logEntries) {
        logEntryTableModel.setLogEntryTable(logEntries);
        logEntryTableModel.fireTableDataChanged();
    }

    public int[] getSelectedLogEntryRows() {
        int[] viewRows = logEntryTable.getSelectedRows();
        return viewRows;
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
                    "Vill du ta bort "+ getSelectedLogEntryRows().length+ " rad(er)?");
            if (answer == 0) {
                try {
                    tryToDeleteLogEntry();
                } catch (LogEntryValidationException e1) {
                    getLogger().info("Could not validate deleted logentry!");
                }
            }
        }

    }

    private void tryToDeleteLogEntry() throws LogEntryValidationException {
        try {
            deleteLogEntry();
        } catch (IOException e1) {
            getLogger().info("Could not delete logentry!");
        }
    }

    protected Integer getSelectedWeek() {
        if (weekSelector.getSelectedItem() != null) {
            return (Integer) weekSelector.getSelectedItem();
        } else {
            return DateTimeUtils.getCurrentWeek();
        }
    }

    protected int getSelectedYear() {
        if (yearSelector.getSelectedItem() != null) {
            return (Integer) yearSelector.getSelectedItem();
        } else {
            return DateTimeUtils.getCurrentYear();
        }
    }


    public Logger getLogger() {
        return logger;
    }

    private static class LogEntryTableSelectionListener implements
            ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
        }
    }

    public List<LogEntry> getLogEntryTableModel() {
        return logEntryTableModel.getLogEntryTable();
    }

    public LogEntryHandler getLogEntryHandler() {
        return this.logEntryHandler;
    }
	
}
