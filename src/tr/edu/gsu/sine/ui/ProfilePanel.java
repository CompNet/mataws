package tr.edu.gsu.sine.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import tr.edu.gsu.sine.Batch;
import tr.edu.gsu.sine.ext.Profile;

/**
 * Provides the profile panel of the graphical user interface to SINE
 * application.
 */
public class ProfilePanel extends JPanel implements ActionListener {

	/**
	 * Recommended in order to implement {@link Serializable} interface.
	 */
	private static final long serialVersionUID = 4612165564892807955L;

	// Parent panel
	private SinePanel sinePanel;
	
	// Radio buttons
	private JRadioButton[] profileButtons;
	private JRadioButton allProfilesButton;

	/**
	 * Creates a panel for selecting extraction profiles.
	 * 
	 * @param sinePanel
	 *            the parent panel
	 */
	public ProfilePanel(SinePanel sinePanel) {
		super(new GridLayout(0, 2, 5, 2));
		setBorder(BorderFactory.createTitledBorder("Extraction Profiles"));
		this.sinePanel = sinePanel;

		// Create the profile buttons.
		profileButtons = new JRadioButton[Profile.values().length];
		for (Profile p: Profile.values()) {
			int i = p.ordinal();
			profileButtons[i] = new JRadioButton(p.getDescription());
			profileButtons[i].setEnabled(false);
			profileButtons[i].setActionCommand(p.toAbbrev());
			profileButtons[i].addActionListener(this);
			add(profileButtons[i]);
		}
		
		// Create the "all profiles" button.
		allProfilesButton = new JRadioButton("All networks");
		allProfilesButton.setEnabled(false);
		allProfilesButton.setActionCommand("all");
		allProfilesButton.addActionListener(this);
		add(allProfilesButton);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// Select the appropriate handler.
		for (Profile profile : Profile.values()) {
			if (e.getSource() == profileButtons[profile.ordinal()]) {
				handleProfileButton(profile);
				return;
			}
		}
		handleAllProfilesButton();
	}

	/**
	 * Selects or rejects the extraction task of the specified profile. It also
	 * updates the selection of the button "all profiles" and the availability
	 * of the button "extract".
	 * 
	 * @param profile
	 *            the extraction profile which has been clicked
	 */
	private void handleProfileButton(Profile profile) {
		// Select or reject the corresponding extraction task.
		JRadioButton button = profileButtons[profile.ordinal()];
		Batch batch = button.isSelected() ? Batch.TODO : Batch.MISS;
		batch.put(profile);
		
		// Update "all profiles" and "extract" buttons accordingly.
		boolean oneMiss = false;
		boolean oneTodo = false;
		for (Profile p : Profile.values()) {
			oneMiss = oneMiss || (Batch.valueOf(p) == Batch.MISS);
			oneTodo = oneTodo || (Batch.valueOf(p) == Batch.TODO);
		}
		allProfilesButton.setSelected(!oneMiss);
		sinePanel.setExtractEnabled(oneTodo);
	}
	
	/**
	 * Selects or rejects the extraction task of all profiles. It also updates
	 * the selection of each profile button and the availability of the
	 * "extract" button.
	 */
	private void handleAllProfilesButton() {
		boolean selectAll = allProfilesButton.isSelected();
		Batch batch = selectAll ? Batch.TODO : Batch.MISS;
		boolean allDone = true;
		for (Profile p: Profile.values()) {
			if (Batch.valueOf(p) != Batch.DONE) {
				profileButtons[p.ordinal()].setSelected(selectAll);
				batch.put(p);
				allDone = false;
			}
		}
		sinePanel.setExtractEnabled(selectAll && !allDone);
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		boolean allDone = true;
		for (Profile profile : Profile.values()) {
			boolean done = (Batch.valueOf(profile) == Batch.DONE);
			profileButtons[profile.ordinal()].setEnabled(enabled && !done);
			allDone = allDone && done;
		}
		allProfilesButton.setEnabled(enabled && !allDone);
	}

	/**
	 * Updates the selection and availability of each profile button.
	 */
	public void updateInfo() {
		boolean allDone = true;
		for (Profile p: Profile.values()) {
			boolean done = (Batch.valueOf(p) == Batch.DONE);
			profileButtons[p.ordinal()].setSelected(done);
			profileButtons[p.ordinal()].setEnabled(!done);
			allDone = allDone && done;
		}
		allProfilesButton.setSelected(allDone);
		allProfilesButton.setEnabled(!allDone);
	}
}
