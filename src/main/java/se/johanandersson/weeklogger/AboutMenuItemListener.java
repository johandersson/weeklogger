package se.johanandersson.weeklogger;

import javax.swing.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AboutMenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            JOptionPane.showMessageDialog(WeekLoggerWindow.getInstance(), "Om Veckologgaren:" + "\n" + "(c) Johan Andersson"
                    + "\n" + "Licens: GPL3");
            // TODO Auto-generated catch block

        } catch (IOException | LogEntryValidationException e1) {
            e1.printStackTrace();
        }

    }
}
