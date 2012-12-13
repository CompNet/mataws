package tr.edu.gsu.sine.ext;

import java.util.ArrayList;
import java.util.List;

import tr.edu.gsu.sine.col.Parameter;

/**
 * Provides facilities to compare lists of parameters for similarities.
 */
public class ParamListUtils {

	/**
	 * Returns true if the two lists of parameters are identical.
	 * <p>
	 * Each parameter of list1 is similar to at least one parameter of list2.
	 * And for each parameter of list2, a parameter of list1 is similar to it.
	 * 
	 * @param list1
	 *            left-side list of parameters
	 * @param list2
	 *            right-side list of parameters
	 * @param profile
	 *            profile that defines the kind of similarity
	 * @return true if the two lists are identical
	 * @see Similarity#exists(Parameter, Parameter, Profile)
	 * @.NOTE This method is no more symmetric than isSimilarTo() could be.
	 */
	public static boolean areIdentical(List<Parameter> list1,
			List<Parameter> list2, Profile profile) {
		List<Parameter> loners = new ArrayList<Parameter>(list1);
		boolean hasAlike;
		for (Parameter p2 : list2) {
			hasAlike = false;
			for (Parameter p1 : list1) {
				if (Similarity.exists(p1, p2, profile)) {
					hasAlike = true;
					loners.remove(p1);
				}
			}
			if (!hasAlike) {
				return false;
			}
		}
		return loners.isEmpty();
	}

	/**
	 * Returns true if the two lists of parameters are overlapping by at least
	 * one parameter.
	 * <p>
	 * At least one parameter of list1 is similar to a parameter of list2.
	 * 
	 * @param list1
	 *            left-side list of parameters
	 * @param list2
	 *            right-side list of parameters
	 * @param profile
	 *            profile that defines the kind of similarity
	 * @return true if a grain of list1 is similar to a grain of list2
	 * @see Similarity#exists(Parameter, Parameter, Profile)
	 * @.NOTE This method is no more symmetric than isSimilarTo() could be.
	 */
	public static boolean areOverlapping(List<Parameter> list1,
			List<Parameter> list2, Profile profile) {
		for (Parameter p1 : list1) {
			for (Parameter p2 : list2) {
				if (Similarity.exists(p1, p2, profile)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if list1 includes list2 or the two lists are identical.
	 * <p>
	 * For each parameter of list2, a parameter of list1 is similar to it.
	 * 
	 * @param list1
	 *            left-side list of parameters
	 * @param list2
	 *            right-side list of parameters
	 * @param profile
	 *            profile that defines the kind of similarity
	 * @return true if list1 includes list2 or the two lists are identical
	 * @see Similarity#exists(Parameter, Parameter, Profile)
	 * @.NOTE This method is no more symmetric than isSimilarTo() could be.
	 */
	public static boolean looselyIncludes(List<Parameter> list1,
			List<Parameter> list2, Profile profile) {
		boolean hasAlike;
		for (Parameter p2 : list2) {
			hasAlike = false;
			for (Parameter p1 : list1) {
				if (Similarity.exists(p1, p2, profile)) {
					hasAlike = true;
					break;
				}
			}
			if (!hasAlike) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns true if list1 includes list2 and the two lists are not similar.
	 * <p>
	 * For each parameter of list2, a parameter of list1 is similar to it. And
	 * at least one parameter of list1 is not similar to any of list2.
	 * 
	 * @param list1
	 *            left-side list of parameters
	 * @param list2
	 *            right-side list of parameters
	 * @param profile
	 *            profile that defines the kind of similarity
	 * @return true if list1 includes list2 and the two lists are not similar
	 * @see Similarity#exists(Parameter, Parameter, Profile)
	 * @.NOTE This method is no more symmetric than isSimilarTo() could be.
	 */
	public static boolean strictlyIncludes(List<Parameter> list1,
			List<Parameter> list2, Profile profile) {
		List<Parameter> loners = new ArrayList<Parameter>(list1);
		boolean hasAlike;
		for (Parameter p2 : list2) {
			hasAlike = false;
			for (Parameter p1 : list1) {
				if (Similarity.exists(p1, p2, profile)) {
					hasAlike = true;
					loners.remove(p1);
				}
			}
			if (!hasAlike) {
				return false;
			}
		}
		return !loners.isEmpty();
	}
	
	/**
	 * Returns the parameters of list2 which are in both lists.
	 * <p>
	 * For each returned parameter of list2, a parameter of list1 is similar to
	 * it.
	 * 
	 * @param list1
	 *            left-side list of parameters
	 * @param list2
	 *            right-side list of parameters
	 * @param profile
	 *            profile that defines the kind of similarity
	 * @return the parameters of list2 which are in both lists.
	 * @see Similarity#exists(Parameter, Parameter, Profile)
	 * @.NOTE This method is no more symmetric than Similarity could be.
	 */
	public static List<Parameter> getOverlap(List<Parameter> list1,
			List<Parameter> list2, Profile profile) {
		List<Parameter> overlap = new ArrayList<Parameter>();
		for (Parameter p1 : list1)
			for (Parameter p2 : list2)
				if (Similarity.exists(p1, p2, profile))
					overlap.add(p2);
		return overlap;
	}

	/**
	 * 
	 * @param paramList
	 * @return ?
	 */
	public static String toString(List<Parameter> paramList) {
		String s = "";
		for (Parameter p : paramList) {
			if (!s.isEmpty())
				s += ", ";
			s += p.getName();
		}
		return s;
	}
}
