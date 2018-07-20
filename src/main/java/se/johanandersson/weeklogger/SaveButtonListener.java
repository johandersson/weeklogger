package se.johanandersson.weeklogger;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;


public class SaveButtonListener implements ActionListener {
   

    @Override
    public void actionPerformed(ActionEvent e) {
		 try {
			 List<LogEntry> logEntries = LogEntryWindow.getInstance().getLogEntryTableModel();
			 WeekLoggerFileHandler.getInstance().writeLogEntriesToFile(logEntries);
		 
		 } catch (IOException | LogEntryValidationException ex){
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }

   
}
