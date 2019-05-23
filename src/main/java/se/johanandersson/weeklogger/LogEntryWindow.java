package se.johanandersson.weeklogger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;

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

    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 200;
    private static LogEntryWindow INSTANCE;
    private JComboBox weekSelector;
    private JComboBox yearSelector;
    private JButton generateReportButton;
    private JLabel totalTimeLabel;
    private LogEntryTableModel logEntryTableModel;
    private JTable logEntryTable;
    private JRadioButton filterByAllWeeksInTheSelectedYear;
    private JRadioButton filterByCertainWeekTheSelectedYear;
    private ButtonGroup fiterWeeksRadioButtonGroup;
    private JPopupMenu popup;
    private JMenuItem deleteLogEntry;
    private PopupListener popupListener;
    private JPanel weekSelectorPanel;
    private JPanel totalTimeInfoPanel;
    private JPanel generateReportButtonPanel;
    private Logger logger;
    private JScrollPane logEntryTableScrollPane;
    private LogEntryHandler logEntryHandler;
    private ListSelectionModel listSelectionModel;
    private JPanel yearSelectorPanel;

    public void update() throws IOException {
        logEntryHandler.resetLogEntries();
        updateYearAndWeekSelectors();
        updateTable();
        updateTotalTimeLabel();
    }

    private LogEntryWindow() throws IOException {

        logEntryHandler = new LogEntryHandler();

        setUpLogger();
        setSizeAndLayout();
        yearSelectorPanel = new JPanel(new MigLayout("wrap 4"));
        weekSelectorPanel = new JPanel(new MigLayout("wrap 4"));
        totalTimeInfoPanel = new JPanel(new MigLayout("wrap 2"));
        generateReportButtonPanel = new JPanel(new MigLayout());

        addGenereateReportButton();
        buildRadioButtonGroup();

        addWeekAndYearSelectorPanel();
        this.add(yearSelectorPanel);
        this.add(weekSelectorPanel);


        logEntryTableScrollPane = createLogEntryTable();
        listSelectionModel = logEntryTable.getSelectionModel();
        listSelectionModel.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        this.add(logEntryTableScrollPane);
        addTotalTimeLabels();
        this.add(totalTimeInfoPanel);
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
        setResizable(true);
        setTitle("Resultat");
        setLayout(new MigLayout("wrap 1"));
    }

    private void buildRadioButtonGroup() throws IOException {
        fiterWeeksRadioButtonGroup = new ButtonGroup();
        filterByAllWeeksInTheSelectedYear = new JRadioButton("Alla veckor", true);
        filterByCertainWeekTheSelectedYear = new JRadioButton("Vecka: ");
        filterByAllWeeksInTheSelectedYear.setMnemonic(KeyEvent.VK_A);
        filterByCertainWeekTheSelectedYear.setMnemonic(KeyEvent.VK_V);
        JLabel selectweek = new JLabel();
        selectweek.setText("Välj vecka: ");
        weekSelectorPanel.add(selectweek,"wrap");
        fiterWeeksRadioButtonGroup.add(filterByAllWeeksInTheSelectedYear);
        fiterWeeksRadioButtonGroup.add(filterByCertainWeekTheSelectedYear);
        weekSelectorPanel.add(filterByAllWeeksInTheSelectedYear, "wrap");
        weekSelectorPanel.add(filterByCertainWeekTheSelectedYear);

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
        if(filterByCertainWeekTheSelectedYear.isSelected()){
           updateWeekSelectorBasedOnYear(LogEntryWindow.getInstance().getSelectedYear());
        } else {
            if(yearSelector.getItemCount()!=logEntryHandler.getListOfYears().size()){
                yearSelector.setModel(new DefaultComboBoxModel(logEntryHandler
                        .getListOfYears().toArray()));
            }
        }

    }

    protected void updateWeekSelectorBasedOnYear(int year) throws IOException {
        if(weekSelector.getItemCount()!=logEntryHandler.getListOfWeeksInAYear(year).size()){
            weekSelector.setModel(new DefaultComboBoxModel(logEntryHandler.getListOfWeeksInAYear(year).toArray()));
        }

    }

    private void addWeeksToComboBox(LogEntryHandler logEntryHandler)
            throws IOException {
        Object[] listOfWeeks = logEntryHandler.getListOfWeeks().toArray();
        weekSelector = new JComboBox(listOfWeeks);
        weekSelectorPanel.add(weekSelector);
    }

    protected void updateWeeks(List<Integer> weeks) {
        weekSelector.setModel(new DefaultComboBoxModel(weeks.toArray()));
    }

    private void addYearsToComboBox(LogEntryHandler logEntryHandler)
            throws IOException {
        Object[] listOfWeeks = logEntryHandler.getListOfYears().toArray();
        JLabel selectYear = new JLabel();
        selectYear.setText("Välj år: ");
        yearSelector = new JComboBox(listOfWeeks);
        yearSelectorPanel.add(selectYear,"wrap");
        yearSelectorPanel.add(yearSelector);
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
        logEntryTable.getColumnModel().getColumn(5).setCellEditor(popupEditor);
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
        Dimension dimension = new Dimension(500, 400);
        logEntryTable.setPreferredScrollableViewportSize(dimension);
        logEntryTable.setFillsViewportHeight(true);
        logEntryTable.addMouseListener(new PopupListener());
        logEntryTable.setSelectionMode(0);
        resizeColumnWidth(logEntryTable);

    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount()-1; column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
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
        LogEntry selectedLogEntryFromTable = null;
        for (int i = 0; i < selectedRows.length; i++) {
            selectedLogEntryFromTable = getSelectedLogEntryFromTable(selectedRows[i]);
            logEntryHandler.deleteLogEntry(selectedLogEntryFromTable);
        }

        update();
    }

    private void updateTable() throws IOException {
        List<LogEntry> updatedLogEntries = this
                .updateLogEntryTableBasedOnSelectedWeekAndYear();
        updateLogEntryTableWithEntries(updatedLogEntries);

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
        if (index >= 0) {
            List<LogEntry> logEntries = logEntryTableModel.getLogEntryTable();
            LogEntry logEntry = logEntries.get(index);
            return logEntry;
        }
        return null;
    }

    public LogEntry getSelectedLogEntryFromTable() {
        int index = logEntryTable.getSelectedRow();
        LogEntry logEntry = getSelectedLogEntryFromTable(index);
        return logEntry;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteLogEntry) {
            int answer = JOptionPane.showConfirmDialog(this,
                    "Vill du ta bort " + getSelectedLogEntryRows().length + " rad(er)?");
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
