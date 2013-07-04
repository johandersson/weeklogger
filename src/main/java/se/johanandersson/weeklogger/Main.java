package se.johanandersson.weeklogger;

import java.io.IOException;

import javax.swing.SwingUtilities;

public class Main {

	
	
	 public static void main(String[] args) {
		 handleMacOS();
		 
	        SwingUtilities.invokeLater(new Runnable() {
					public void run() {
	                	try {
							WeekLoggerWindow.getInstance().setVisible(true);
						}  catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (LogEntryValidationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	        });
	}

	private static void handleMacOS() {
		if(isUserComputerMac()){
			 System.setProperty("apple.laf.useScreenMenuBar", "false");
			 // set the name of the application menu item
			 System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WeekLogger");
		 }
	}

	private static boolean isUserComputerMac() {
		return System.getProperty("os.name").startsWith("Mac");
	}


}
