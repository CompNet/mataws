package tr.edu.gsu.sine.ui;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;

import tr.edu.gsu.sine.Batch;
import tr.edu.gsu.sine.Option;
import tr.edu.gsu.sine.Path;
import tr.edu.gsu.sine.SecondaryTask;
import tr.edu.gsu.sine.Sine;
import tr.edu.gsu.sine.ext.FlexibleMetric;
import tr.edu.gsu.sine.ext.Profile;

/**
 * Provides the main panel of the graphical user interface to SINE application.
 */
public class SinePanel extends JPanel implements ActionListener {

	/**
	 * Recommended in order to implement {@link Serializable} interface.
	 */
	private static final long serialVersionUID = -2213100593410325813L;

	// first-step: parse
	private SetupPanel setupPanel;
	private JButton parseButton;
	
	// second-step: extract
	private ProfilePanel profilePanel;
	private JButton extractButton;

	/**
	 * Creates a panel for SINE controls.
	 */
	public SinePanel() {
		super(new BorderLayout());
		
		// Children of this panel
		final JPanel topPanel;
		final JScrollPane logPanel;
		
		// Children of the top panel
		JPanel topSetupPanel;
		JPanel topProfilePanel;
		
		// Children of the log panel
		final JTextArea logArea;
		Handler loggerHandler;

		// Create the log area.
		logArea = new JTextArea(5, 80);
		logArea.setMargin(new Insets(5, 5, 5, 5));
		logArea.setEditable(false);
		logArea.setFocusable(false);
		logArea.setLineWrap(true);
		logArea.setWrapStyleWord(true);
		
		// Create the log panel.
		logPanel = new JScrollPane(logArea);
		logPanel.setBorder(BorderFactory.createTitledBorder("Log Records"));
		logPanel.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		// Handle SINE logger with the log area.
		loggerHandler = new Handler() {
			@Override
			public void publish(LogRecord record) {
				logArea.append(record.getMessage() + Sine.NEW_LINE);
				flush();
			}
			@Override
			public void flush() {
				logArea.setCaretPosition(logArea.getText().length());
			}
			@Override
			public void close() throws SecurityException {
				flush();
			}
		};
		loggerHandler.setLevel(Level.INFO);
		Sine.getLogger().addHandler(loggerHandler);
		
		// Create the top-level profile panel.
		topProfilePanel = new JPanel(new BorderLayout());
		profilePanel = new ProfilePanel(this);
		topProfilePanel.add(profilePanel, BorderLayout.PAGE_START);
		extractButton = new JButton("Extract networks");
		extractButton.addActionListener(this);
		extractButton.setEnabled(false);
		JPanel extractButtonPanel = new JPanel();
		extractButtonPanel.add(extractButton);
		topProfilePanel.add(extractButtonPanel, BorderLayout.PAGE_END);
		
		// Create the top-level setup panel.
		topSetupPanel = new JPanel(new BorderLayout());
		setupPanel = new SetupPanel(this);
		topSetupPanel.add(setupPanel, BorderLayout.PAGE_START);
		parseButton = new JButton("Parse collection");
		parseButton.addActionListener(this);
		parseButton.setEnabled(Path.areValid());
		JPanel parseButtonPanel = new JPanel();
		parseButtonPanel.add(parseButton);
		topSetupPanel.add(parseButtonPanel, BorderLayout.PAGE_END);
		
		// Create the top panel with both setup and profiles.
		topPanel = new JPanel(new BorderLayout());
		topPanel.add(topSetupPanel, BorderLayout.PAGE_START);
		topPanel.add(topProfilePanel, BorderLayout.PAGE_END);
		
		// Add the top panel and the log panel to this panel.
		add(topPanel, BorderLayout.PAGE_START);
		add(logPanel, BorderLayout.CENTER);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// Dispatch to the corresponding action handler.
		if (e.getSource() == parseButton) {
			handleParseButton();
			return;
		}
		if (e.getSource() == extractButton) {
			handleExtractButton();
			return;
		}
	}
	
	/**
	 * Proceeds to end setup and parse collection. If it is successful, then
	 * it switches forward to the extraction step, else user can still retry
	 * with different settings.
	 * 
	 * @.NOTE It disables the setup panel and "parse" button in the meantime.
	 */
	private void handleParseButton() {
		setupPanel.setEnabled(false);
		parseButton.setEnabled(false);

		// Run it as a background job, so user can still use the application.
		SwingWorker<Boolean, Integer> setupAndParseWorker;
		setupAndParseWorker = new SwingWorker<Boolean, Integer>() {

			@Override
			protected Boolean doInBackground() throws Exception {
				try {
					Sine.endSetup();
					Sine.loadCollection();
					// Set flexible matching threshold
					Float threshold = Float.valueOf(Option.FLEX.getValue());
					FlexibleMetric.setThreshold(threshold.floatValue());
					// Write some reports (parameters, names, collection, ...)
					SecondaryTask.executeAll();
					return true;
				} catch (Exception e) {
					Sine.getLogger().severe(e.getMessage());
					e.printStackTrace();
					return false;
				}
			}

			@Override
			protected void done() {
				boolean success;
				
				try {
					success = get().booleanValue();
				} catch (Exception e) {
					success = false;
				}
				
				if (success) {
					profilePanel.setEnabled(true);
				} else {
					setupPanel.updateInfo();
					setupPanel.setEnabled(true);
					parseButton.setEnabled(true);
				}
			}
		};

		setupAndParseWorker.execute();
	}

	/**
	 * Proceeds to networks extraction. It disables profile buttons of extracted
	 * networks. It also disables the "extract" button if all networks have been
	 * extracted.
	 * 
	 * @.NOTE It disables the profile panel and "extract" button in between.
	 */
	private void handleExtractButton() {
		profilePanel.setEnabled(false);
		extractButton.setEnabled(false);

		// Run it as a background job, so user can still use the panel.
		SwingWorker<Void, Integer> extractionWorker;
		extractionWorker = new SwingWorker<Void, Integer>() {

			@Override
			protected Void doInBackground() throws Exception {
				Sine.extractNetworks(Sine.getCollection());
				return null;
			}

			@Override
			protected void done() {
				boolean oneTodo = false;
				boolean allDone = true;
				for (Profile p : Profile.values()) {
					oneTodo = oneTodo || (Batch.valueOf(p) == Batch.TODO);
					allDone = allDone && (Batch.valueOf(p) == Batch.DONE);
				}
				if (!allDone) {
					profilePanel.updateInfo();
					extractButton.setEnabled(oneTodo);
				}
			}
		};
		
		extractionWorker.execute();
	}

	/**
	 * Updates the availability of the "parse" button.
	 * 
	 * @param enable whether to enable or disable it.
	 */
	public void setParseEnabled(boolean enable) {
		parseButton.setEnabled(enable);
	}

	/**
	 * Updates the availability of the "extract" button.
	 * 
	 * @param enabled whether to enable or disable it.
	 */
	public void setExtractEnabled(boolean enabled) {
		extractButton.setEnabled(enabled);
	}
}
