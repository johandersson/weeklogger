package se.johanandersson.weeklogger;


import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

public class CommentRenderer extends JLabel
        implements TableCellRenderer {

    public CommentRenderer() {
        //setOpaque(false); //MUST do this for background to show up.
    }

    public Component getTableCellRendererComponent(
            JTable table, Object logEntry,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        var comment = (String) table.getModel().getValueAt(row,5);
        setText(comment);
        setToolTipText(comment); //comment
        return this;
    }
}

