package tr.edu.gsu.sine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tr.edu.gsu.sine.ext.Profile;

/**
 * Defines batches of extraction tasks.
 * <p>
 * It helps to keep accounts of missing, on-going and past extractions.
 */
public enum Batch {

	/**
	 * Batch of extraction profiles which have not been used.
	 */
	MISS,

	/**
	 * Batch of extraction profiles which have to be used.
	 */
	TODO,

	/**
	 * Batch of extraction profiles which have been used.
	 */
	DONE;
	
	/**
	 * Map each extraction profile to the batch it belongs to.
	 */
	private static final Map<Profile, Batch> profilesBatches = initMap();
	
	/**
	 * Creates initial map, where all extraction profiles are set as missing.
	 * 
	 * @return a map where all profiles point to {@link Batch#MISS}.
	 */
	private static Map<Profile, Batch> initMap() {
		Profile[] profiles = Profile.values();
		Map<Profile, Batch> map = new HashMap<Profile, Batch>(profiles.length);
		for (Profile profile: profiles) {
			map.put(profile, MISS);
		}
		return map;
	}
	
	/**
	 * Returns the batch to which the specified profile is belonging to.
	 * 
	 * @param profile
	 *            an extraction profile
	 * @return the batch of the specified profile
	 */
	public static Batch valueOf(Profile profile) {
		return profilesBatches.get(profile);
	}
	
	/**
	 * Returns the profiles contained in this batch.
	 * 
	 * @return the profiles contained in this batch
	 */
	public List<Profile> getContent() {
		List<Profile> content = new ArrayList<Profile>();
		for (Profile profile : profilesBatches.keySet()) {
			if (this == profilesBatches.get(profile)) {
				content.add(profile);
			}
		}
		return content;
	}

	/**
	 * Puts the specified profile into this batch.
	 * <p>
	 * If this profile was belonging to another batch, it doesn't anymore.
	 * 
	 * @param profile
	 *            the extraction's profile to be moved to this batch
	 */
	public void put(Profile profile) {
		profilesBatches.put(profile, this);
	}
}