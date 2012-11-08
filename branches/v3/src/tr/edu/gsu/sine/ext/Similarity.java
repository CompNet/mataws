package tr.edu.gsu.sine.ext;

import java.util.Arrays;
import java.util.List;

import tr.edu.gsu.sine.col.Grain;
import tr.edu.gsu.sine.col.Operation;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.col.Way;
import tr.edu.gsu.sine.ext.Trait.Category;

/**
 * Provides methods to detect and describe similarity of grains.
 * 
 * @.NOTE Similarity of grains can be an asymmetric relationship.
 */
public class Similarity {
	
	/**
	 * True if parameters named with stop-words can no match any other.
	 * 
	 * TODO: add this flag to command-line options.
	 */
	private static final boolean blockStopWords = false;
	
	/**
	 * List of known stop-words.
	 * 
	 * TODO: add this list to command-line options.
	 */
	private static final List<String> stopWords = Arrays.asList("Body",
			"Return", "Result", "return", "parameters");
	
	/**
	 * Returns true if {@literal from} is similar to {@literal to},
	 * according to {@literal profile}.
	 * 
	 * @param from
	 *            the starting grain
	 * @param to
	 *            the incident grain
	 * @param profile
	 *            the profile of similarity
	 * @return true if from and to are similar according to profile
	 */
	public static boolean exists(Grain from, Grain to, Profile profile) {
		if (from instanceof tr.edu.gsu.sine.col.Parameter) {
			return exists((Parameter) from, (Parameter) to, profile);
		} else if (from instanceof tr.edu.gsu.sine.col.Operation) {
			return exists((Operation) from, (Operation) to, profile);
		}
		return false;
	}
	
	/**
	 * Returns true if {@literal from} is similar to {@literal to},
	 * according to {@literal profile}.
	 * 
	 * @param from
	 *            the starting parameter
	 * @param to
	 *            the incident parameter
	 * @param p
	 *            the profile of similarity
	 * @return true if from and to are similar according to profile
	 */
	public static boolean exists(Parameter from, Parameter to, Profile p) {
		switch (p.getTrait(Category.DESCRIPTION)) {
		case SYNTACTIC: {
			if (blockStopWords)
				if (stopWords.contains(from.getName())
						|| stopWords.contains(to.getName()))
					return false;
			switch (p.getTrait(Category.MATCHING)) {
			case EQUAL: {
				return from.getName().equals(to.getName());
			}
			case FLEXIBLE: {
				return FlexibleMetric.valueOf(p).isMatching(from.getName(),
						to.getName());
			}
			}
		}
		case SEMANTIC: {
			return from.getConceptURI() != null
					&& to.getConceptURI() != null
					&& OntologyUtils.isMatching(from.getConceptURI(), to
							.getConceptURI(), p);
		}
		}
		return false;
	}

	/**
	 * Returns true if {@literal from} is similar to {@literal to},
	 * according to {@literal profile}.
	 * 
	 * @param from
	 *            the starting operation
	 * @param to
	 *            the incident operation
	 * @param p
	 *            the profile of similarity
	 * @return true if from and to are similar according to profile
	 * @.NOTE It is defined for symmetric matching of parameters only.
	 */
	public static boolean exists(Operation from, Operation to, Profile p) {
		// Operations with empty outputs are consistently discarded.
		if (to.getParameters(Way.OUT).isEmpty()
				|| from.getParameters(Way.OUT).isEmpty()) {
			return false;
		}
		// Select similarity mode.
		switch (p.getTrait(Category.MODE)) {
		case FULL: {
			return ParamListUtils.areIdentical(from.getParameters(Way.OUT), to
					.getParameters(Way.OUT), p)
					&& ParamListUtils.areOverlapping(
							from.getParameters(Way.IN), to
									.getParameters(Way.IN), p);
		}
		case PARTIAL: {
			return ParamListUtils.strictlyIncludes(from.getParameters(Way.OUT),
					to.getParameters(Way.OUT), p)
					&& ParamListUtils.areOverlapping(
							from.getParameters(Way.IN), to
									.getParameters(Way.IN), p);
		}
		case EXCESS: {
			// NB: Parameter.isSimilarTo() is supposed to be symmetric here!
			return ParamListUtils.strictlyIncludes(to.getParameters(Way.OUT),
					from.getParameters(Way.OUT), p)
					&& ParamListUtils
							.looselyIncludes(from.getParameters(Way.IN), to
									.getParameters(Way.IN), p)
					&& !to.getParameters(Way.IN).isEmpty();
		}
		case RELATIONAL: {
			return ParamListUtils.areIdentical(from.getParameters(Way.OUT), to
					.getParameters(Way.OUT), p)
					&& !ParamListUtils
							.areOverlapping(from.getParameters(Way.IN), to
									.getParameters(Way.IN), p)
					&& !to.getParameters(Way.IN).isEmpty()
					&& !from.getParameters(Way.IN).isEmpty();
		}
		}
		return false;
	}

	/**
	 * Returns a descriptive label of similarity between {@literal from} and
	 * {@literal to}, according to {@literal profile}.
	 * @param to
	 *            the incident grain
	 * @param from
	 *            the starting grain
	 * @param profile
	 *            the profile of similarity
	 * 
	 * @return descriptive label of similarity between from and to
	 * @.NOTE If there is no similarity, returned label might be inconsistent.
	 */
	public static String label(Grain to, Grain from, Profile profile) {
		if (from instanceof tr.edu.gsu.sine.col.Parameter) {
			return label((Parameter) from, (Parameter) to, profile);
		} else if (from instanceof tr.edu.gsu.sine.col.Operation) {
			return label((Operation) from, (Operation) to, profile);
		}
		return "";
	}

	/**
	 * Returns a descriptive label of similarity between {@literal from} and
	 * {@literal to}, according to {@literal profile}.
	 * 
	 * @param from
	 *            the starting parameter
	 * @param to
	 *            the incident parameter
	 * @param p
	 *            the profile of similarity
	 * @return descriptive label of similarity between from and to
	 * @.NOTE If there is no similarity, returned label might be inconsistent.
	 */
	public static String label(Parameter from, Parameter to, Profile p) {
		switch (p.getTrait(Category.DESCRIPTION)) {
		case SYNTACTIC: {
			if (blockStopWords)
				if (stopWords.contains(from.getName())
						|| stopWords.contains(to.getName()))
					return Float.toString(0);
			switch (p.getTrait(Category.MATCHING)) {
			case EQUAL: {
				return Float.toString(1);
			}
			case FLEXIBLE: {
				return Float.toString(FlexibleMetric.valueOf(p).isMatching(
						from.getName(), to.getName()) ? FlexibleMetric.valueOf(
						p).getSimilarity(from.getName(), to.getName()) : 0);
			}
			}
		}
		case SEMANTIC: {
			return from.getConceptURI() != null
					&& to.getConceptURI() != null
					&& OntologyUtils.isMatching(from.getConceptURI(), to
							.getConceptURI(), p) ? from.getConceptURI() : "";
		}
		}
		return "";
	}

	/**
	 * Returns a descriptive label of similarity between {@literal from} and
	 * {@literal to}, according to {@literal profile}.
	 * 
	 * @param from
	 *            the starting operation
	 * @param to
	 *            the incident operation
	 * @param p
	 *            the profile of similarity
	 * @return descriptive label of similarity between from and to
	 * @.NOTE If there is no similarity, returned label might be inconsistent.
	 */
	public static String label(Operation from, Operation to, Profile p) {
		// Select similarity mode.
		switch (p.getTrait(Category.MODE)) {
		case FULL: {
			return ParamListUtils.toString(ParamListUtils.getOverlap(from
					.getParameters(Way.IN), to.getParameters(Way.IN), p));
		}
		case PARTIAL: {
			return ParamListUtils.toString(ParamListUtils.getOverlap(from
					.getParameters(Way.IN), to.getParameters(Way.IN), p));
		}
		case EXCESS: {
			return "";
		}
		case RELATIONAL: {
			return "";
		}
		}
		return "";
	}
}
