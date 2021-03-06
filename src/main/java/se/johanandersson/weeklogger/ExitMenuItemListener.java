package se.johanandersson.weeklogger;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Listener for the file menu item exit
 *
 * @author Johan Andersson
 */
public class ExitMenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            if (WeekLoggerWindow.getInstance().getClock().isTicking()) {
                int answer = JOptionPane.showConfirmDialog(WeekLoggerWindow.getInstance(), "En klocka tickar! Vill du verkligen avsluta?");
                if (answer == 0) {
                    WeekLoggerWindow.getInstance().setDefaultCloseOperation(WeekLoggerWindow.EXIT_ON_CLOSE);
                } else {
                    return;
                }
            }

            System.exit(0);

        } catch (IOException | HeadlessException | LogEntryValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
