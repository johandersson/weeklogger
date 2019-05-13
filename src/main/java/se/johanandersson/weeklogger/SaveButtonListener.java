package se.johanandersson.weeklogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;


public class SaveButtonListener implements ActionListener {
   

    @Override
    public void actionPerformed(ActionEvent e) {
		 try {
			 List<LogEntry> logEntries = WeekLoggerFileHandler.getInstance().getLogEntries();
			 WeekLoggerFileHandler.getInstance().writeLogEntriesToFile(logEntries);
		 } catch (IOException | LogEntryValidationException ex){
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
    }
   
}
