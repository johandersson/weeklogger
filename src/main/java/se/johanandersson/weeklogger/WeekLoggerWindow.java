package se.johanandersson.weeklogger;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("serial")
/**
 * Main timer window
 *
 * @author Johan Andersson
 *
 */
public class WeekLoggerWindow extends JFrame implements ActionListener, WindowListener, WindowStateListener, WindowFocusListener {

    private static WeekLoggerWindow INSTANCE = null;
    private final int WINDOW_WIDTH = 400;
    private final int WINDOW_HEIGHT = 200;
    private JLabel timeLabel;
    private JLabel weekLabel;
    private Timer timer;
    private Clock clock;
    private LogEntry currentLogEntry;
    private JButton startButton;
    private JButton stopButton;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem exitItem;
    private JMenuItem showResultTableItem;
    private JMenuItem aboutMenuItem;
    private PopupDialog popupDialog;

    @Override
    public void actionPerformed(ActionEvent e) {
        // event for the timer
        clock.tick();
        timeLabel.setText(clock.getTime().toString());
    }

    private WeekLoggerWindow() throws LogEntryValidationException {
        popupDialog = new PopupDialog(this);
        buildGUI();

        setCurrentLogEntry(new LogEntry());
        timer = new Timer(1000, this);
        clock = new Clock(new Time(0, 0, 0));
        weekLabel.setName("weekLabel");

        //Don't exit on 'X'-window-click
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        addWindowFocusListener(this);
        addWindowStateListener(this);

    }

    public static WeekLoggerWindow getInstance() throws IOException, LogEntryValidationException {
        if (INSTANCE == null) {
            INSTANCE = new WeekLoggerWindow();
        }
        return INSTANCE;
    }

    protected JButton getStartButton() {
        return startButton;
    }

    protected JButton getStopButton() {
        return stopButton;
    }

    protected Clock getClock() {
        return clock;
    }

    protected LogEntry getCurrentLogEntry() {
        return currentLogEntry;
    }

    private void startTimer(boolean restart) {
        if (clock.isTicking() && restart) { // restart
            clock.setToZero();
        }

        clock.start();
        timer.start();
    }

    protected void startTimer() {
        if (isClockAlreadyTicking()) {
            int answer = JOptionPane.showConfirmDialog(this,
                    "En klocka tickar redan! Vill du starta om?");
            if (answer == 0) {
                startTimer(true);
            }
        } else { //not ticking
            startTimer(true);
        }

    }

    private boolean isClockAlreadyTicking() {
        return timer.isRunning() && clock.isTicking();
    }

    protected void stopTimer() {
        clock.stop();
        timer.stop();
    }

    private void buildGUI() {
        weekLabel = new JLabel();
        timeLabel = new JLabel();
        setUpWeekLoggerWindow();
        setTitlesOfButtons();
        addActionListenersToButtons();
        setTimeAndWeekLabel();
        createPanels();
        createFileMenu();
        this.setJMenuBar(menuBar);
        pack();
        this.setVisible(true);
    }

    private void createFileMenu() {
        new FileMenuCreator().invoke();

    }

    private void setUpWeekLoggerWindow() {
        new WeekLoggerWindowSetup().invoke();

    }

    private void setTimeAndWeekLabel() {
        new TimeAndWeekLabelCreator().invoke();
    }

    private void setTitlesOfButtons() {
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
    }

    public void createPanels() {
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();

        panel1.add(timeLabel);
        panel2.add(weekLabel);

        panel3.add(startButton);
        panel3.add(stopButton);

        this.add(panel1);
        this.add(panel2);
        this.add(panel3);
        this.add(panel4);

    }

    private void addActionListenersToButtons() {
        ClockButtonListener clockButtonListener = new ClockButtonListener();
        startButton.addActionListener(clockButtonListener);
        stopButton.addActionListener(clockButtonListener);
    }

    protected static void showAndResumeLogEntryWindow() throws IOException {
        LogEntryWindow.getInstance().setVisible(true);
        LogEntryWindow.getInstance().setState(Frame.NORMAL);
    }

    protected void logTheLogEntry() {
        popupDialog.setText("");
        popupDialog.show();
        String commentText = popupDialog.getText();
        getCurrentLogEntry().setComment(commentText);
        getCurrentLogEntry().setTotalTime(clock.getTime());

        if (StringUtils.isBlank(commentText)) {
            int answer = JOptionPane.showConfirmDialog(this,
                    "Du skrev ingen kommentar. Vill du ändå logga?");
            if (answer != 0) {
                resetClockAndTimerButtons();
                return;
            }


        }

        writeCurrentLogEntryToFile();
        updateLogEntryWindow();
        resetClockAndTimerButtons();

    }

    private void resetClockAndTimerButtons() {
        resetTimerButtons();
        resetClock();
    }

    private void resetClock() {
        clock.setToZero();
        clock.setTicking(false);
        timer.restart();
    }

    private void resetTimerButtons() {
        startButton.setEnabled(true);
        stopButton.setEnabled(true);
    }

    private void updateLogEntryWindow() {
        try {
            LogEntryWindow.getInstance().update();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    private void writeCurrentLogEntryToFile() {
        try {
            LogEntryWindow.getInstance().getLogEntryHandler().writeLogEntry(getCurrentLogEntry());
        } catch (IOException | LogEntryValidationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Override
    public void windowActivated(WindowEvent arg0) {

    }

    @Override
    public void windowStateChanged(WindowEvent e) {

    }



    @Override
    public void windowClosed(WindowEvent arg0) {
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
        // TODO Auto-generated method stub

        int answer = JOptionPane.showConfirmDialog(this,
                "Är du säker på att du vill stänga?");
        if (answer == 0) {
            System.exit(0);
        }


    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
        // TODO Auto-generated method stub
    }

    void setCurrentLogEntry(LogEntry currentLogEntry) {
        this.currentLogEntry = currentLogEntry;
    }

    void updateWeekLabel(LogEntry l){
        weekLabel.setText(l.getLogDate() + " vecka: " + l.getWeek());
    }

    @Override
    public void windowGainedFocus(WindowEvent e)  {
         if (!clock.isTicking()) {
            String currentDate = DateTimeUtils.getCurrentDate();
            String logDate = getCurrentLogEntry().getLogDate();
            if (!logDate.equals(currentDate)) { //open window but changed date
                setTimeAndWeekLabel();
                try {
                    setCurrentLogEntry(new LogEntry());
                } catch (LogEntryValidationException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    public void windowLostFocus(WindowEvent e) {

    }

    private class FileMenuCreator {

        public void invoke() {
            menuBar = new JMenuBar();
            fileMenu = new JMenu("Arkiv");

            exitItem = new JMenuItem("Avsluta");
            exitItem.addActionListener(new ExitMenuItemListener());

            showResultTableItem = new JMenuItem("Visa resultat");
            showResultTableItem.addActionListener(new ResultMenuItemListener());

            aboutMenuItem = new JMenuItem("Om Veckologgaren");
            aboutMenuItem.addActionListener(new AboutMenuItemListener());

            fileMenu.add(exitItem);
            fileMenu.add(showResultTableItem);
            fileMenu.add(aboutMenuItem);

            menuBar.add(fileMenu);
        }
    }

    private class WeekLoggerWindowSetup {

        public void invoke() {
            setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Veckologgaren");
            setLayout(new GridLayout(2, 2));
        }
    }

    private class TimeAndWeekLabelCreator {

        public void invoke() {
            timeLabel.setText("00:00:00");
            String currentWeek = String.valueOf(DateTimeUtils.getCurrentWeek());
            String currentDate = DateTimeUtils.getCurrentDate();
            weekLabel.setText(currentDate + " vecka: " + currentWeek);
            weekLabel.setName("weekLabel");
            Font timerFont = new Font("Arial", Font.BOLD, 30);
            Font weekFont = new Font("Arial", Font.ITALIC, 14);
            timeLabel.setFont(timerFont);
            weekLabel.setFont(weekFont);
        }
    }
}
