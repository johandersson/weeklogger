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


        popup = new PopupDialog();
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

    /*
     *   Simple dialog containing the actual editing component
     */
    class PopupDialog extends JDialog implements ActionListener {
        private JTextArea textArea;

        public PopupDialog() {
            super((Frame) null, "Ändra kommentar", true);

            textArea = new JTextArea(5, 20);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            KeyStroke keyStroke = KeyStroke.getKeyStroke("ENTER");
            textArea.getInputMap().put(keyStroke, "none");
            JScrollPane scrollPane = new JScrollPane(textArea);
            getContentPane().add(scrollPane);

            JButton cancel = new JButton("Cancel");
            cancel.addActionListener(this);
            JButton ok = new JButton("Ok");
            ok.setPreferredSize(cancel.getPreferredSize());
            ok.addActionListener(this);

            JPanel buttons = new JPanel();
            buttons.add(ok);
            buttons.add(cancel);
            getContentPane().add(buttons, BorderLayout.SOUTH);
            pack();

            getRootPane().setDefaultButton(ok);
        }

        public void setText(String text) {
            textArea.setText(text);
        }

        /*
         *   Save the changed text before hiding the popup
         */
        public void actionPerformed(ActionEvent e) {
            if ("Ok".equals(e.getActionCommand())) {
                currentText = textArea.getText();
            }

            textArea.requestFocusInWindow();
            setVisible(false);
        }
    }
}