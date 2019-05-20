package se.johanandersson.weeklogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ResultMenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            WeekLoggerWindow.showAndResumeLogEntryWindow();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

}
