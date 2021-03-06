package se.johanandersson.weeklogger;


import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;


public class DayRenderer extends JLabel
        implements TableCellRenderer {

    public DayRenderer() {
        //setOpaque(false); //MUST do this for background to show up.
    }

    public Component getTableCellRendererComponent(
            JTable table, Object logEntry,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        var date = (String) table.getModel().getValueAt(row,0);
        setText(date);
        var day = date.split("-");
        LocalDate localDate = LocalDate.of(Integer.valueOf(day[0]), Integer.valueOf(day[1]), Integer.valueOf(day[2]));
        var formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM");
        localDate.format(formatter);
        Locale locale=new Locale("sv","SE");
        //Getting the day of week for a given date
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        setToolTipText(localDate.format(formatter));
        this.setFont(this.getFont().deriveFont(Font.PLAIN));
        return this;
    }
}

