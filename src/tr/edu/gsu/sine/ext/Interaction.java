package tr.edu.gsu.sine.ext;

import tr.edu.gsu.sine.col.Grain;
import tr.edu.gsu.sine.col.Operation;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.col.Service;
import tr.edu.gsu.sine.col.Way;
import tr.edu.gsu.sine.ext.Trait.Category;

/**
 * Provides methods to detect and describe interaction between grains.
 * 
 * @.NOTE Interaction between grains can be an asymmetric relationship.
 */
public class Interaction {
	
	/**
	 * Returns true if the grain {@literal from} is giving access to the grain
	 * {@literal to}, in terms of interaction, according to {@literal profile}.
	 * 
	 * @param from
	 *            the starting grain
	 * @param to
	 *            the incident grain
	 * @param profile
	 *            the profile of interaction
	 * @return true if profile allows access from from to to
	 */
	public static boolean exists(Grain from, Grain to, Profile profile) {
		if (from instanceof tr.edu.gsu.sine.col.Parameter) {
			return exists((Parameter) from, (Parameter) to, profile);
		} else if (from instanceof tr.edu.gsu.sine.col.Operation) {
			return exists((Operation) from, (Operation) to, profile);
		} else if (from instanceof tr.edu.gsu.sine.col.Service) {
			return exists((Service) from, (Service) to, profile);
		}
		return false;
	}
	
	/**
	 * Returns true if the parameter {@literal from} is giving access to the
	 * parameter {@literal to}, in terms of interaction, according to
	 * {@literal profile}.
	 * 
	 * @param from
	 *            the starting parameter
	 * @param to
	 *            the incident parameter
	 * @param p
	 *            the profile of interaction
	 * @return true if profile allows access from from to to
	 */
	public static boolean exists(Parameter from, Parameter to, Profile p) {
		// Dismiss the special case of self-access and backward-access.
		if (Similarity.exists(from, to, p) || from.getWay() != Way.IN) {
			return false;
		}
		Operation opFrom = (Operation) from.getParent();
		for (Parameter outParam : opFrom.getParameters(Way.OUT)) {
			if (Similarity.exists(outParam, to, p)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the operation {@literal from} is giving access to the
	 * operation {@literal to}, in terms of interaction, according to
	 * {@literal profile}.
	 * 
	 * @param from
	 *            the starting operation
	 * @param to
	 *            the incident operation
	 * @param p
	 *            the profile of interaction
	 * @return true if profile allows access from from to to
	 */
	public static boolean exists(Operation from, Operation to, Profile p) {
		// Discard the case of operations without input or output parameter.
		if (to.getParameters(Way.IN).isEmpty()
				|| from.getParameters(Way.OUT).isEmpty()) {
			return false;
		}
		switch (p.getTrait(Category.MODE)) {
		case FULL: {
			// Full access is given if each in parameter has a similar one.
			boolean match;
			for (Parameter inParam : to.getParameters(Way.IN)) {
				match = false;
				for (Parameter outParam : from.getParameters(Way.OUT)) {
					if (Similarity.exists(outParam, inParam, p)) {
						match = true;
						break;
					}
				}
				if (!match) {
					return false;
				}
			}
			return true;
		}
		case PARTIAL: {
			// Partial access is given if at least one parameter is similar.
			for (Parameter inParam : to.getParameters(Way.IN)) {
				for (Parameter outParam : from.getParameters(Way.OUT)) {
					if (Similarity.exists(outParam, inParam, p)) {
						return true;
					}
				}
			}
			return false;
		}
		}
		return false;
	}

	/**
	 * Returns true if the service{@literal from} is giving access to the
	 * service {@literal to}, in terms of interaction, according to
	 * {@literal profile}.
	 * 
	 * @param from
	 *            the starting service
	 * @param to
	 *            the incident service
	 * @param p
	 *            the profile of interaction
	 * @return true if profile allows access from from to to
	 */
	public static boolean exists(Service from, Service to, Profile p) {
		// Discard the case of services without input or output parameter.
		if (to.getParameters(Way.IN).isEmpty()
				|| from.getParameters(Way.OUT).isEmpty()) {
			return false;
		}
		switch (p.getTrait(Category.MODE)) {
		case FULL: {
			// Full access is given if each in parameter has a similar one.
			boolean match;
			for (Parameter inParam : to.getParameters(Way.IN)) {
				match = false;
				for (Parameter outParam : from.getParameters(Way.OUT)) {
					if (Similarity.exists(outParam, inParam, p)) {
						match = true;
						break;
					}
				}
				if (!match) {
					return false;
				}
			}
			return true;
		}
		case PARTIAL: {
			// Partial access is given if at least one parameter is similar.
			for (Parameter inParam : to.getParameters(Way.IN)) {
				for (Parameter outParam : from.getParameters(Way.OUT)) {
					if (Similarity.exists(outParam, inParam, p)) {
						return true;
					}
				}
			}
			return false;
		}
		}
		return false;
	}

	/**
	 * Returns a descriptive label of the interaction between {@literal from}
	 * and {@literal to}, according to {@literal profile}.
	 * 
	 * @param from
	 *            the starting grain
	 * @param to
	 *            the incident grain
	 * @param profile
	 *            the profile of interaction
	 * @return descriptive label of interaction between from and to
	 * @.NOTE If there is no interaction, returned label might be inconsistent.
	 */
	public static String label(Grain to, Grain from, Profile profile) {
		if (from instanceof tr.edu.gsu.sine.col.Parameter) {
			return label((Parameter) from, (Parameter) to, profile);
		} else if (from instanceof tr.edu.gsu.sine.col.Operation) {
			return label((Operation) from, (Operation) to, profile);
		} else if (from instanceof tr.edu.gsu.sine.col.Service) {
			return label((Service) from, (Service) to, profile);
		}
		return "";
	}

	/**
	 * Returns a descriptive label of the interaction between {@literal from}
	 * and {@literal to}, according to {@literal profile}.
	 * 
	 * @param from
	 *            the starting parameter
	 * @param to
	 *            the incident parameter
	 * @param p
	 *            the profile of interaction
	 * @return descriptive label of interaction between from and to
	 * @.NOTE If there is no interaction, returned label might be inconsistent.
	 */
	public static String label(Parameter from, Parameter to, Profile p) {
		return from.getParent().getUniqueName();
	}

	/**
	 * Returns a descriptive label of the interaction between {@literal from}
	 * and {@literal to}, according to {@literal profile}.
	 * 
	 * @param from
	 *            the starting operation
	 * @param to
	 *            the incident operation
	 * @param p
	 *            the profile of interaction
	 * @return descriptive label of interaction between from and to
	 * @.NOTE If there is no interaction, returned label might be inconsistent.
	 */
	public static String label(Operation from, Operation to, Profile p) {
		switch (p.getTrait(Category.MODE)) {
		case FULL: {
			return "";
		}
		case PARTIAL: {
			return ParamListUtils.toString(ParamListUtils.getOverlap(from
					.getParameters(Way.OUT), to.getParameters(Way.IN), p));
		}
		}
		return "";
	}

	/**
	 * Returns a descriptive label of the interaction between {@literal from}
	 * and {@literal to}, according to {@literal profile}.
	 * 
	 * @param from
	 *            the starting service
	 * @param to
	 *            the incident service
	 * @param p
	 *            the profile of interaction
	 * @return descriptive label of interaction between from and to
	 * @.NOTE If there is no interaction, returned label might be inconsistent.
	 */
	public static String label(Service from, Service to, Profile p) {
		switch (p.getTrait(Category.MODE)) {
		case FULL: {
			// Full access is given if each in parameter has a similar one.
			boolean match;
			String label = "";
			for (Parameter inParam : to.getParameters(Way.IN)) {
				match = false;
				for (Parameter outParam : from.getParameters(Way.OUT)) {
					if (Similarity.exists(outParam, inParam, p)) {
						match = true;
						if (!label.isEmpty())
							label += ", ";
						label += outParam.getName();
						break;
					}
				}
				if (!match) {
					return "";
				}
			}
			return label;
		}
		case PARTIAL: {
			// Partial access is given if at least one parameter is similar.
			String label = "";
			for (Parameter inParam : to.getParameters(Way.IN)) {
				for (Parameter outParam : from.getParameters(Way.OUT)) {
					if (Similarity.exists(outParam, inParam, p)) {
						if (!label.isEmpty())
							label += ", ";
						label += inParam.getName();
						break;
					}
				}
			}
			return label;
		}
		}
		return "";
	}
}
