/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.johanandersson.weeklogger;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;



/**
 * @author nossrednanahoj
 */
class WordOpener {

    private File pdfFile;

    /*public void openPDFWithInstalledReader(PDFCreator pdf)
            throws HeadlessException, IOException, LogEntryValidationException {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                openPdfFile(pdf, desktop);
            }
        } else {
            JOptionPane
                    .showMessageDialog(
                            WeekLoggerWindow.getInstance(),
                            "Stöd sakas för att öppna pdf-filen direkt i förvalt program. Försök att öppna filen manuellt från: "
                                    + pdfFile.getAbsolutePath());
        }
    }

    private void openPdfFile(PDFCreator pdf, Desktop desktop)
            throws IOException, LogEntryValidationException {
        pdfFile = new File(pdf.getFileName()); // path to pdf file
        try {
            desktop.open(pdfFile);
        } catch (IOException e) {
            JOptionPane
                    .showMessageDialog(WeekLoggerWindow.getInstance(),
                            "Kunde inte öppna pdf-fil. Se till att du har en pdf-läsare installerad.");
        }
    }*/
}
