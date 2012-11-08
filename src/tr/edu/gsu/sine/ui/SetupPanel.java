package tr.edu.gsu.sine.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tr.edu.gsu.sine.Option;
import tr.edu.gsu.sine.Path;
import tr.edu.gsu.sine.Sine;
import tr.edu.gsu.sine.ext.FlexibleMetric;
import tr.edu.gsu.sine.in.Language;

/**
 * Provides the setup panel of the graphical user interface to SINE application.
 */
public class SetupPanel extends JPanel implements ActionListener {

	/**
	 * Recommended in order to implement {@link Serializable} interface.
	 */
	private static final long serialVersionUID = -291783425100865131L;

	/**
	 * Error color code to point out that a field contains an erroneous value.
	 * 
	 * @.NOTE It is sometimes ignored, depending of the underlying renderer.
	 */
	private static Color ERROR_COLOR = new Color(255, 95, 95);
	
	// Parent panel
	private SinePanel sinePanel;
	
	// Paths settings
	private JTextField[] pathsFields;
	private JButton[] pathsButtons;

	// Options settings
	private JTextField nameField;
	private JComboBox langList;
	private JFormattedTextField fMaxField;
	
	// Not displayed in the main panel
	private JFileChooser fileChooser;
	
	/**
	 * Creates a panel for setting options and paths up.
	 * 
	 * @param sinePanel
	 *            the parent panel
	 */
	public SetupPanel(SinePanel sinePanel) {
		super(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Parsing Setup"));
		this.sinePanel = sinePanel;

		// Children of setup panel
		JPanel pathsPanel;
		JPanel optionsPanel;
		
		// Create a file chooser.
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Create the paths panel.
		pathsPanel = new JPanel(new GridLayout(0, 1));
		JPanel pathPanel;
		JLabel pathLabel;
		int pathIndex;
		int nbPaths = Path.values().length;
		pathsFields = new JTextField[nbPaths];
		pathsButtons = new JButton[nbPaths];
		for (Path path: Path.values()) {
			pathIndex = path.ordinal();
			
			pathLabel = new JLabel(path.getDescription() + ": ");
			
			pathsFields[pathIndex] = new JTextField(new File(path.getValue())
					.getAbsolutePath(), 50);
			pathsFields[pathIndex].setAutoscrolls(true);
			pathsFields[pathIndex].setBorder(null);
			pathsFields[pathIndex].setEditable(false);
			pathsFields[pathIndex].setFocusable(false);
			pathsFields[pathIndex].setBackground(path.isValid() ? Color.WHITE
					: ERROR_COLOR);
			
			pathsButtons[pathIndex] = new JButton("...");
			pathsButtons[pathIndex].addActionListener(this);
			
			pathPanel = new JPanel();
			pathPanel.add(pathLabel, BorderLayout.LINE_START);
			pathPanel.add(pathsFields[pathIndex], BorderLayout.CENTER);
			pathPanel.add(pathsButtons[pathIndex], BorderLayout.LINE_END);
			
			pathsPanel.add(pathPanel);
		}
		add(pathsPanel, BorderLayout.PAGE_START);
		
		// Create the options panel.
		optionsPanel = new JPanel();
		JLabel nameLabel = new JLabel("Collection name: ");
		optionsPanel.add(nameLabel);
		nameField = new JTextField(20);
		nameField.setText(Option.NAME.getValue());
		nameField.addActionListener(this);
		nameField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				handleNameField();
				super.focusLost(e);
			}
		});
		optionsPanel.add(nameField);
		JLabel languageLabel = new JLabel("Description language: ");
		optionsPanel.add(languageLabel);
		langList = new JComboBox();
		for (Language lang : Language.values()) {
			langList.addItem(lang.name());
		}
		Language optLang = Language.valueOf(Option.LANG.getValue());
		langList.setSelectedIndex(optLang.ordinal());
		langList.addActionListener(this);
		langList.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				handleLangList();
				super.focusLost(e);
			}
		});
		optionsPanel.add(langList);
		JLabel fMaxLabel = new JLabel("Flexible matching's threshold: ");
		optionsPanel.add(fMaxLabel);
		NumberFormat numFormat = NumberFormat.getNumberInstance(Locale.ROOT);
		fMaxField = new JFormattedTextField(numFormat);
		fMaxField.setColumns(3);
		fMaxField.setText(Float.toString(FlexibleMetric.getThreshold()));
		fMaxField.addActionListener(this);
		fMaxField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				handleFMaxField();
				super.focusLost(e);
			}
		});
		optionsPanel.add(fMaxField);
		add(optionsPanel, BorderLayout.PAGE_END);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// Select the appropriated path handler.
		for (Path path : Path.values()) {
			if (e.getSource() == pathsButtons[path.ordinal()]) {
				handlePathButton(path);
				return;
			}
		}
		
		// Handle options fields.
		if (e.getSource() == nameField) {
			handleNameField();
		}
		if (e.getSource() == langList) {
			handleLangList();
		}
		if (e.getSource() == fMaxField) {
			handleFMaxField();
		}
	}

	/**
	 * Sets flexible matching threshold from FMax text field.
	 */
	private void handleFMaxField() {
		String fMaxString = fMaxField.getText();
		try {
			Option.FLEX.setValue(fMaxString);
		} catch (Exception e) {
			Sine.getLogger().warning(e.getMessage());
		}
	}

	/**
	 * Sets collection language from Language list.
	 */
	private void handleLangList() {
		int langIndex = langList.getSelectedIndex();
		Language selectedLang = Language.values()[langIndex];
		try {
			Option.LANG.setValue(selectedLang.name());
		} catch (Exception e) {
			Sine.getLogger().warning(e.getMessage());
		}
	}

	/**
	 * Sets collection name from Name text field.
	 */
	private void handleNameField() {
		try {
			Option.NAME.setValue(nameField.getText());
		} catch (Exception e) {
			Sine.getLogger().warning(e.getMessage());
		}
	}
	
	/**
	 * Saves the selected path and updates availability of the "parse" button
	 * accordingly.
	 * 
	 * @param path
	 *            the path selected through the file chooser
	 */
	private void handlePathButton(Path path) {
		fileChooser.setDialogTitle(path.getDescription());
		int returnVal = fileChooser.showOpenDialog(SetupPanel.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File dir = fileChooser.getSelectedFile();
			path.setValue(dir.getAbsolutePath());
			pathsFields[path.ordinal()].setText(dir.getAbsolutePath());
			pathsFields[path.ordinal()]
					.setBackground(path.isValid() ? Color.WHITE : ERROR_COLOR);
			sinePanel.setParseEnabled(Path.areValid());
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		for (JButton button: pathsButtons) {
			button.setEnabled(enabled);
		}
		fMaxField.setEnabled(enabled);
		langList.setEnabled(enabled);
		nameField.setEnabled(enabled);
	}

	/**
	 * Updates the values contained in each field of the panel.
	 */
	public void updateInfo() {
		for (Path path: Path.values()) {
			pathsFields[path.ordinal()].setText(path.getValue());
		}
		fMaxField.setText(Float.toString(FlexibleMetric.getThreshold()));
		langList.setSelectedIndex(Sine.getCollection().getLanguage().ordinal());
		nameField.setText(Sine.getCollection().getName());
	}
}
