package tr.edu.gsu.sine.ext;

import uk.ac.shef.wit.simmetrics.similaritymetrics.AbstractStringMetric;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

/**
 * Provides a similarity measure using Levenshtein similarity between two
 * smoothed strings. Strings are smoothed by removing non-alphabetic characters
 * and converting it to lower-case.
 */
public class SmoothedLevenshtein extends AbstractStringMetric {
	
	/** */
	private Levenshtein levenshtein;

	/**
	 * 
	 */
	public SmoothedLevenshtein() {
		levenshtein = new Levenshtein();
	}

	/**
	 * Smooth a string by removing all non-alphabetic characters and lowering.
	 * 
	 * @param s
	 *            string to be smoothed
	 * @return the lower-cased string without non-alphabetic characters
	 */
	private static String smooth(String s) {
		return s.replaceAll("\\p{Alpha}", "").toLowerCase();
	}
	
	@Override
	public String getLongDescriptionString() {
		return "Smoothed Levenshtein";
	}

	@Override
	public String getShortDescriptionString() {
		return "Provides a similarity measure using Levenshtein similarity"
				+ " between two smoothed strings";
	}

	@Override
	public float getSimilarity(String arg0, String arg1) {
		String smoothed0 = smooth(arg0);
		String smoothed1 = smooth(arg1);
		return levenshtein.getSimilarity(smoothed0, smoothed1);
	}

	@Override
	public String getSimilarityExplained(String arg0, String arg1) {
		return "Removes non-alphanumeric characters and convert to lower-case,"
		+ " before using Levenshtein similarity";
	}

	@Override
	public float getSimilarityTimingEstimated(String arg0, String arg1) {
		return levenshtein.getSimilarity(arg0, arg1);
	}

	@Override
	public float getUnNormalisedSimilarity(String arg0, String arg1) {
		String smoothed0 = smooth(arg0);
		String smoothed1 = smooth(arg1);
		return levenshtein.getUnNormalisedSimilarity(smoothed0, smoothed1);
	}

}
