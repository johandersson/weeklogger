package se.johanandersson.weeklogger;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


public class TablePopupEditor extends DefaultCellEditor
        implements TableCellEditor {
    private PopupDialog popup;
    private String currentText = "";
    private JButton editorComponent;

    public TablePopupEditor() {
        super(new JTextField());

        setClickCountToStart(2);

        editorComponent = new JButton();
        editorComponent.setBackground(Color.white);
        editorComponent.setBorderPainted(false);
        editorComponent.setContentAreaFilled(false);


        popup = new PopupDialog(this);
    }

    public Object getCellEditorValue() {
        return currentText;
    }

    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                popup.setText(currentText);
                Point p = editorComponent.getLocationOnScreen();
                popup.setLocation(p.x, p.y + editorComponent.getSize().height);
                popup.show();
                fireEditingStopped();
            }
        });

        currentText = value.toString();
        editorComponent.setText(currentText);
        return editorComponent;
    }

    public void setCurrentText(String text){
        currentText=text;
    }

    /*
     *   Simple dialog containing the actual editing component
     */

}