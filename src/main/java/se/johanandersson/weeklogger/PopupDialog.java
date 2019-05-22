package se.johanandersson.weeklogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PopupDialog extends JDialog implements ActionListener {
    private JTextArea textArea;
    private TablePopupEditor parentEditor;

    public PopupDialog(TablePopupEditor parentEditor) {
        super((Frame) null, "Ändra kommentar", true);
        this.parentEditor=parentEditor;


        addTextAreaToScrollPane();

        JButton cancel = createCancelButton();
        JButton ok = createOkButton(cancel);

        JPanel buttons = new JPanel();
        addOkAndCancelButtonToJPanel(cancel, ok, buttons);

        getContentPane().add(buttons, BorderLayout.SOUTH);
        pack();
        getRootPane().setDefaultButton(ok);
    }

    public PopupDialog(JFrame parentEditor) {
        super(parentEditor, "Lägg till kommentar", true);
        addTextAreaToScrollPane();

        JButton cancel = createCancelButton();
        JButton ok = createOkButton(cancel);

        JPanel buttons = new JPanel();
        addOkAndCancelButtonToJPanel(cancel, ok, buttons);

        getContentPane().add(buttons, BorderLayout.SOUTH);
        pack();
        getRootPane().setDefaultButton(ok);
    }

    private void addOkAndCancelButtonToJPanel(JButton cancel, JButton ok, JPanel buttons) {
        buttons.add(ok);
        buttons.add(cancel);
    }

    private JButton createOkButton(JButton cancel) {
        JButton ok = new JButton("Ok");
        ok.setPreferredSize(cancel.getPreferredSize());
        ok.addActionListener(this);
        return ok;
    }

    private JButton createCancelButton() {
        JButton cancel = new JButton("Avbryt");
        cancel.addActionListener(this);
        return cancel;
    }

    private void addTextAreaToScrollPane() {
        textArea = new JTextArea(5, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ENTER");
        textArea.getInputMap().put(keyStroke, "none");
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane);
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    /*
     *   Save the changed text before hiding the popup
     */
    public void actionPerformed(ActionEvent e) {
        if ("Ok".equals(e.getActionCommand())) {
            if(parentEditor!=null)
                parentEditor.setCurrentText(textArea.getText());
        }

        textArea.requestFocusInWindow();
        setVisible(false);
    }

    public String getText(){
        return textArea.getText();
    }
}