package tr.edu.gsu.sine.ui;

import java.awt.event.WindowAdapter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import tr.edu.gsu.sine.Sine;

/**
 * Provides facility to run the graphical user interface to SINE application.
 */
public class GraphicalUI implements Runnable {
	
	/**
	 * Starts the graphical user interface in another thread at the background.
	 */
	public static void start() {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new GraphicalUI());
	}
	
	/**
	 * Initializes and runs the graphical user interface.
	 * 
	 * @see #start()
	 * @see Runnable#run()
	 * @.NOTE This method must not be called directly.
	 */
	@Override
	public void run() {
		// Turn off metal's use of bold fonts.
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		// Use native look and feel.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// Do nothing, this is harmless.
		}

		// Create and set up the window.
		JFrame frame = new JFrame("SINE " + Sine.VERSION
				+ " - Similarity and Interaction Networks Extractor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
	
		// Add content to the window through a scroll pane.
		frame.add(new JScrollPane(new SinePanel()));
		
		// Add a listener to close resources when the program is shutting down.
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {
				Sine.closeLogHandlers();
			}
		});
	
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
