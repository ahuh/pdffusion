package org.pdffusion;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class GUIApplication {
	protected static JFrame frame;
	
	protected static void createAndShowGUI() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e) {
			// Ignore
		}
		
        //Create and set up the window.
        frame = new JFrame("PdfFusion");
        
        /*frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the menu bar.  Make it have a green background.
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(new Color(154, 165, 127));
        greenMenuBar.setPreferredSize(new Dimension(200, 20));

        //Create a yellow label to put in the content pane.
        JLabel yellowLabel = new JLabel();
        yellowLabel.setOpaque(true);
        yellowLabel.setBackground(new Color(248, 213, 131));
        yellowLabel.setPreferredSize(new Dimension(200, 180));

        //Set the menu bar and add the label to the content pane.
        frame.setJMenuBar(greenMenuBar);
        frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        
        frame.setVisible(false);*/
    }
	
	protected static void terminateGUI() {
		if (frame != null) {
			frame.dispose();
		}
	}
	
	protected static void displayMessagePopup(String message) {
		JOptionPane.showMessageDialog(frame, message, Const.APP_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}
	
	protected static void displayWarningPopup(String message) {
		JOptionPane.showMessageDialog(frame, message, Const.APP_TITLE, JOptionPane.WARNING_MESSAGE);
	}
	
	protected static void displayErrorPopup(String message) {
		JOptionPane.showMessageDialog(frame, message, Const.APP_TITLE, JOptionPane.ERROR_MESSAGE);
	}
}
