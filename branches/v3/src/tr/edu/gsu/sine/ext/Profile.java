package tr.edu.gsu.sine.ext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tr.edu.gsu.sine.ext.Trait.Category;

/**
 * Provides consistent sets of traits, defining supported extraction methods.
 * 
 * @see Trait
 */
public enum Profile {
	IFSSE("inte full serv synt equa"),
	IFSSFJ("inte full serv synt flex jaro"),
	IFSSFL("inte full serv synt flex leve"),
	IFSSFS("inte full serv synt flex smoo"),
	IFSSFW("inte full serv synt flex winc"),
	IFSME("inte full serv sema exac"),
	IFSMF("inte full serv sema fiti"),
	IFSMP("inte full serv sema plug"),
	IFSMS("inte full serv sema subs"),
	IFOSE("inte full oper synt equa"),
	IFOSFJ("inte full oper synt flex jaro"),
	IFOSFL("inte full oper synt flex leve"),
	IFOSFS("inte full oper synt flex smoo"),
	IFOSFW("inte full oper synt flex winc"),
	IFOME("inte full oper sema exac"),
	IFOMF("inte full oper sema fiti"),
	IFOMP("inte full oper sema plug"),
	IFOMS("inte full oper sema subs"),
	IFPSE("inte full para synt equa"),
	IFPME("inte full para sema exac"),
	IPSSE("inte part serv synt equa"),
	IPSSFJ("inte part serv synt flex jaro"),
	IPSSFL("inte part serv synt flex leve"),
	IPSSFS("inte part serv synt flex smoo"),
	IPSSFW("inte part serv synt flex winc"),
	IPSME("inte part serv sema exac"),
	IPSMF("inte part serv sema fiti"),
	IPSMP("inte part serv sema plug"),
	IPSMS("inte part serv sema subs"),
	IPOSE("inte part oper synt equa"),
	IPOSFJ("inte part oper synt flex jaro"),
	IPOSFL("inte part oper synt flex leve"),
	IPOSFS("inte part oper synt flex smoo"),
	IPOSFW("inte part oper synt flex winc"),
	IPOME("inte part oper sema exac"),
	IPOMF("inte part oper sema fiti"),
	IPOMP("inte part oper sema plug"),
	IPOMS("inte part oper sema subs"),
	IPPSE("inte part para synt equa"),
	IPPME("inte part para sema exac"),
	SFOSE("simi full oper synt equa"),
	SFOME("simi full oper sema exac"),
	SPOSE("simi part oper synt equa"),
	SPOME("simi part oper sema exac"),
	SROSE("simi rela oper synt equa"),
	SROME("simi rela oper sema exac"),
	SEOSE("simi exce oper synt equa"),
	SEOME("simi exce oper sema exac");

	/**
	 * Returns the list of all profiles that contains all traits listed in the
	 * string abbreviation.
	 * 
	 * @param abbrev
	 *            an abbreviation of one or some traits
	 * @return the list of profiles matching the string abbreviation
	 */
	private static List<Trait> parseString(String abbrev) {
		List<Trait> traits = new ArrayList<Trait>();
		int index = 0;
		String traitAbbrev;
		Trait trait;
		// 4 characters per abbreviation
		while (index + 4 <= abbrev.length()) {
			traitAbbrev = abbrev.substring(index, index + 4);
			trait = Trait.valueOfAbbrev(traitAbbrev);
			if (trait != null) {
				traits.add(trait);
			}
			// 1 abbreviation + 1 separator = 5 characters
			index += 5;
		}
		return traits;
	}

	/**
	 * Returns the profile corresponding to string abbrev, null otherwise.
	 * 
	 * @param abbrev
	 *            the profile's abbreviation
	 * @return the profile corresponding to string abbrev, null otherwise
	 */
	public static Profile valueOfAbbrev(String abbrev) {
		List<Trait> lt = Profile.parseString(abbrev);
		List<Profile> lts = valuesWith(lt);
		return lts.size() == 1 ? lts.get(0) : null;
	}

	/**
	 * Returns the list of profiles that feature a subset of traits.
	 * 
	 * @param subset
	 *            a subset of extraction method traits
	 * @return the list of profiles that feature each trait of subset
	 */
	public static List<Profile> valuesWith(List<Trait> subset) {
		List<Profile> lts = new ArrayList<Profile>();
		boolean containsSubset;
		for (Profile ts : values()) {
			containsSubset = true;
			for (Trait t : subset) {
				containsSubset = containsSubset
						&& ts.getTrait(t.getCategory()).equals(t);
			}
			if (containsSubset) {
				lts.add(ts);
			}
		}
		return lts;
	}

	/**
	 * Returns the list of profiles that feature trait.
	 * 
	 * @param trait
	 *            an extraction method trait.
	 * @return the list of profiles that feature trait.
	 */
	public static List<Profile> valuesWith(Trait trait) {
		List<Profile> lts = new ArrayList<Profile>();
		for (Profile p : values()) {
			if (trait.equals(p.getTrait(trait.getCategory()))) {
				lts.add(p);
			}
		}
		return lts;
	}

	/**
	 * List of traits indexed by category ordinal.
	 */
	private final List<Trait> traits;

	/**
	 * Creates a profile described by the specified abbreviation.
	 * <p>
	 * This constructor is private, as required for an enumerated class.
	 * 
	 * @param abbrev
	 *            the profile's abbreviated notation
	 */
	private Profile(String abbrev) {
		traits = parseString(abbrev);
	}

	/**
	 * Returns true if this profile contains the trait.
	 * 
	 * @param trait
	 *            the trait to look for
	 * @return true if this profile contains the trait
	 */
	public boolean contains(Trait trait) {
		return traits.contains(trait);
	}

	/**
	 * Returns an user-friendly string description of this profile.
	 * 
	 * @return an user-friendly string description of this profile
	 */
	public String getDescription() {
		String s = "";
		s += getTrait(Category.MODE).getName() + " ";
		s += getTrait(Category.MODEL).getName() + " ";
		s += "of ";
		s += getTrait(Category.DESCRIPTION).getName() + " ";
		s += getTrait(Category.GRANULARITY).getName() + " ";
		s += "with ";
		s += getTrait(Category.MATCHING).getName() + " matching";
		if (traits.size() > Category.METRIC.ordinal()) {
			s += " and ";
			s += getTrait(Category.METRIC).getName() + " metrics";
		}
		return s;
	}

	/**
	 * Returns the trait of given category, null otherwise.
	 * 
	 * @param c
	 *            the category of the requested trait
	 * @return the trait of given category, null otherwise
	 */
	public Trait getTrait(Category c) {
		return c.ordinal() >= traits.size() ? null : traits.get(c.ordinal());
	}

	/**
	 * Returns the list of its traits, indexed by their categories ordinal.
	 * 
	 * @return the list of its traits, indexed by their categories ordinal
	 */
	public List<Trait> getTraits() {
		return traits;
	}

	/**
	 * Returns the string abbreviation of this profile.
	 * <p>
	 * This is suitable for network's filenames and command-line arguments.
	 * 
	 * @return the string abbreviation of this profile
	 */
	public String toAbbrev() {
		String s = "";
		Iterator<Trait> it = traits.iterator();
		while (it.hasNext()) {
			s += it.next().getAbbreviation();
			if (it.hasNext()) {
				s += "_";
			}
		}
		return s;
	}
}
