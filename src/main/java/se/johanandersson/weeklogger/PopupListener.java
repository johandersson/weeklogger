package se.johanandersson.weeklogger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JPopupMenu;
import javax.swing.JTable;


public class PopupListener extends MouseAdapter {
	

	public PopupListener() {

	}
	
	
	public void mousePressed(MouseEvent e) {
		maybeShowPopup(e);
		
	}

	public void mouseReleased(MouseEvent e) {
		maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {
		try {
			JTable logEntryTable = LogEntryWindow.getInstance().getLogEntryTable();
			if (e.isPopupTrigger() && logEntryTable.getRowCount()>0) {
				JPopupMenu popup = LogEntryWindow.getInstance().getPopup();
				popup.show(e.getComponent(), e.getX(), e.getY());
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}