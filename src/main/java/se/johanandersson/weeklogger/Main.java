package se.johanandersson.weeklogger;

import com.github.weisj.darklaf.LafManager;

import java.io.IOException;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LafManager.install();
                try {
                    WeekLoggerWindow.getInstance().setVisible(true);
                } catch (IOException | LogEntryValidationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
