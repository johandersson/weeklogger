package se.johanandersson.weeklogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;


public class ClockButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton startButton = getStartButton();
		JButton stopButton = getStopButton();
		
		try {
			Clock clock = getClock();
			LogEntry currentLogEntry = getCurrentLogEntry();
			handleStart(e, startButton, currentLogEntry);
			handleStop(e, stopButton, clock, currentLogEntry);
		} catch (LogEntryValidationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}

	private void handleStop(ActionEvent e, JButton stopButton, Clock clock,
			LogEntry currentLogEntry) throws LogEntryValidationException {
		if (e.getSource() == stopButton) {
			if(clock.isZero()){
				return;
			}
			
			stopTimer();
			handleStop();
			setTimeOfCurrentLogEntry(clock, currentLogEntry);
			log();
		}
	}

	private void log() {
		try {
			WeekLoggerWindow.getInstance().logTheLogEntry();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
	} catch (LogEntryValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setTimeOfCurrentLogEntry(Clock clock, LogEntry currentLogEntry) {
		currentLogEntry.setStopTime(DateTimeUtils.getCurrentTime().toString());
		currentLogEntry.setTotalTime(clock.getTime());
	}

	private void handleStop() throws LogEntryValidationException {
		try {
			handleStopButton();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
	}
	}
	private void stopTimer() {
		try {
			WeekLoggerWindow.getInstance().stopTimer();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LogEntryValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private void handleStart(ActionEvent e, JButton startButton,
			LogEntry currentLogEntry) throws LogEntryValidationException {
		if (e.getSource() == startButton) {
			try {
				handleStartButton();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			currentLogEntry.setStartTime(DateTimeUtils.getCurrentTime().toString());
		}
	}

	private LogEntry getCurrentLogEntry() throws LogEntryValidationException {
		LogEntry currentLogEntry = null;
		try {
			currentLogEntry = WeekLoggerWindow.getInstance().getCurrentLogEntry();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
		return currentLogEntry;
	}

	private Clock getClock() throws LogEntryValidationException {
		Clock clock = null;
		try {
			clock = WeekLoggerWindow.getInstance().getClock();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
		return clock;
	}

	private JButton getStopButton() {
		JButton stopButton = null;
		try {
			stopButton = WeekLoggerWindow.getInstance().getStopButton();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (LogEntryValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return stopButton;
	}

	private JButton getStartButton() {
		JButton startButton = null;
		try {
			startButton = WeekLoggerWindow.getInstance().getStartButton();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (LogEntryValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return startButton;
	}

	private void handleStartButton() throws IOException, LogEntryValidationException{
		WeekLoggerWindow.getInstance().startTimer();
		JButton stopButton = WeekLoggerWindow.getInstance().getStopButton();
		stopButton.setEnabled(true);
	}

	private void handleStopButton() throws IOException, LogEntryValidationException{		
		WeekLoggerWindow.getInstance().stopTimer();
		JButton stopButton = WeekLoggerWindow.getInstance().getStopButton();
		JButton startButton = WeekLoggerWindow.getInstance().getStartButton();
		Clock clock = WeekLoggerWindow.getInstance().getClock();
		clock.setTicking(false);
		stopButton.setEnabled(false);
		startButton.setEnabled(false);
	}

}
