package tr.edu.gsu.sine.ext;

import tr.edu.gsu.sine.ext.Trait.Category;
import uk.ac.shef.wit.simmetrics.similaritymetrics.InterfaceStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Jaro;
import uk.ac.shef.wit.simmetrics.similaritymetrics.JaroWinkler;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

/**
 * Provides an enumeration of metrics for flexible matching of parameters names.
 */
public enum FlexibleMetric {

	/**
	 * Measure of similarity using the <a
	 * href="http://www.dcs.shef.ac.uk/~sam/stringmetrics.html#jaro">Jaro</a>
	 * distance.
	 */
	JARO(new Jaro(), Trait.JARO),

	/**
	 * Measure of the <a
	 * href="http://www.dcs.shef.ac.uk/%7Esam/stringmetrics.html#jarowinkler">
	 * Jaro-Winckler</a> similarity.
	 */
	JARO_WINCKLER(new JaroWinkler(), Trait.WINCKLER),

	/**
	 * Measure of similarity using the <a
	 * href="http://www.dcs.shef.ac.uk/~sam/stringmetrics.html#Levenshtein"
	 * >Levenshtein</a> distance.
	 */
	LEVENSHTEIN(new Levenshtein(), Trait.LEVENSHTEIN),
	
	/**
	 * Measure of similarity using the Levenshtein distance on smoothed names.
	 */
	SMOOTHED_LEVENSHTEIN(new SmoothedLevenshtein(), Trait.SMOOTHED);

	public static FlexibleMetric valueOf(Profile profile) {
		Trait metricsTrait = profile.getTrait(Category.METRIC);
		for (FlexibleMetric m : values()) {
			if (m.trait.equals(metricsTrait)) {
				return m;
			}
		}
		return null;
	}

	/**
	 * The maximum distance to consider that two strings are similar.
	 */
	protected static float threshold = 0.90f;

	/**
	 * The underlying string metric implementation.
	 */
	protected final InterfaceStringMetric smetric;

	/**
	 * The corresponding trait of network extractor.
	 */
	private final Trait trait;

	/**
	 * Gets the threshold of acceptable similarity.
	 * 
	 * @return the threshold of acceptable similarity
	 */
	public static float getThreshold() {
		return threshold;
	}

	/**
	 * Gets the threshold of acceptable similarity.
	 * 
	 * @param threshold
	 *            the threshold of acceptable similarity
	 */
	public static void setThreshold(float threshold) {
		FlexibleMetric.threshold = threshold;
	}

	/**
	 * Creates a flexible matching of specified similarity metric and trait.
	 * <p>
	 * This constructor is private, as required for an enumerated class.
	 * 
	 * @param smetric
	 *            the underlying similarity metric
	 * @param trait
	 *            the trait which is making use of it
	 */
	private FlexibleMetric(InterfaceStringMetric smetric, Trait trait) {
		this.smetric = smetric;
		this.trait = trait;
	}

	/**
	 * Returns the similarity score of a and b.
	 * <p>
	 * It is only available for the purpose of testing.
	 * 
	 * @param a
	 *            the left-side string
	 * @param b
	 *            the right-side string
	 * @return the similarity score of a and b. 
	 */
	public float getSimilarity(String a, String b) {
		return smetric.getSimilarity(a, b);
	}

	/**
	 * Returns true if a has a similarity score to b of at least threshold.
	 * 
	 * @param a
	 *            the left-side string
	 * @param b
	 *            the right-side string
	 * @return true if a is similar to b
	 */
	public boolean isMatching(String a, String b) {
		return smetric.getSimilarity(a, b) >= threshold;
	}
}
