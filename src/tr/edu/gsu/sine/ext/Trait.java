package tr.edu.gsu.sine.ext;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides traits of the method used to extract a network.
 * <p>
 * Traits are sorted by categories. Traits of the same category are mutually
 * exclusive between them.
 * 
 * @see Profile
 * @see Trait.Category
 */
public enum Trait {
	/**
	 * Relationship model is based on interactions.
	 */
	INTERACTION(Category.MODEL),

	/**
	 * Relationship model is based on similarities.
	 */
	SIMILARITY(Category.MODEL),
	
	/**
	 * Full mode of interaction or similarity.
	 */
	FULL(Category.MODE),
	
	/**
	 * Partial mode of interaction or similarity.
	 */
	PARTIAL(Category.MODE),
	
	/**
	 * Mode of similarity by excess.
	 */
	EXCESS(Category.MODE),
	
	/**
	 * Relational mode of similarity.
	 */
	RELATIONAL(Category.MODE),
	
	/**
	 * Vertices are web services.
	 */
	SERVICE(Category.GRANULARITY),
	
	/**
	 * Vertices are operations.
	 */
	OPERATION(Category.GRANULARITY),
	
	/**
	 * Vertices are parameters.
	 */
	PARAMETER(Category.GRANULARITY),
	
	/**
	 * Syntactic description is used for matching.
	 */
	SYNTACTIC(Category.DESCRIPTION),
	
	/**
	 * Semantic description is used for matching.
	 */
	SEMANTIC(Category.DESCRIPTION),
	
	/**
	 * Parameters names must be equal to match.
	 */
	EQUAL(Category.MATCHING),
	
	/**
	 * Parameters names must match within a threshold.
	 * 
	 * @see Trait.Category#METRIC
	 */
	FLEXIBLE(Category.MATCHING),
	
	/**
	 * Ontological concepts must be exactly the same.
	 */
	EXACT(Category.MATCHING),
	
	/**
	 * Ontological concept is of lower or same level.
	 */
	FITIN(Category.MATCHING),

	/**
	 * Ontological concept is of strictly lower level.
	 */
	PLUGIN(Category.MATCHING),
	
	/**
	 * Ontological concept is strictly higher level.
	 */
	SUBSUME(Category.MATCHING),

	/**
	 * Parameters names must match according to Jaro metric.
	 */
	JARO(Category.METRIC),

	/**
	 * Parameters names must match according to Levenshtein metric.
	 */
	LEVENSHTEIN(Category.METRIC),

	/**
	 * Smoothed parameters names must match according to Levenshtein metric.
	 * @see sine.ext.SmoothedLevenshtein
	 */
	SMOOTHED(Category.METRIC),
	
	/**
	 * Parameters names must match according to Jaro-Winckler metric.
	 */
	WINCKLER(Category.METRIC);

	/**
	 * Category of a trait.
	 * 
	 * @see Profile
	 * @see Trait
	 */
	public enum Category {
		
		/**
		 * Model of relationship: interaction or similarity.
		 */
		MODEL,
		
		/**
		 * Mode, depends on the model:
		 * <ul>
		 * <li>interaction: full or partial</li>
		 * <li>similarity: full, partial, excess or relational</li>
		 * </ul>
		 */
		MODE,
		
		/**
		 * Granularity of the network: service, operation or parameter.
		 * @see sine.col.Grain
		 */
		GRANULARITY,
		
		/**
		 * Description compared: syntactic (parameters names)
		 * or semantic (parameters types ontological concepts).
		 */
		DESCRIPTION,
		
		/**
		 * Matching, depends on the description:
		 * <ul>
		 * <li>syntactic: equal or flexible</li>
		 * <li>semantic: exact, fit-in, plug-in or subsume</li>
		 * </ul>  
		 */
		MATCHING,
		
		/**
		 * Metric, for syntactic flexible matching only.
		 * 
		 * @see Trait#FLEXIBLE
		 */
		METRIC;
		
		/**
		 * Returns user-friendly name: first-letter capped, all others lowered.
		 * 
		 * @return user-friendly name of this category
		 */
		public String getName() {
			return name().charAt(0) + name().substring(1).toLowerCase();
		}
		
		/**
		 * Returns the list of all traits of this category.
		 * 
		 * @return the list of all traits of this category
		 */
		public List<Trait> getTraitValues() {
			List<Trait> traits = new ArrayList<Trait>();
			for (Trait t: Trait.values()) {
				if (t.category.equals(this)) {
					traits.add(t);
				}
			}
			return traits;
		}
	}

	/**
	 * Returns the trait corresponding to an abbreviation, null otherwise.
	 * 
	 * @param abbrev
	 *            the trait's abbreviation
	 * @return the trait matching abbrev, null otherwise
	 */
	public static Trait valueOfAbbrev(String abbrev) {
		for (Trait trait: values()) {
			if (trait.abbreviation.equals(abbrev)) {
				return trait;
			}
		}
		return null;
	}

	/**
	 * Abbreviation, which is the four first letters of the lower-cased name.
	 */
	private final String abbreviation;
	
	/**
	 * Category.
	 */
	private final Category category;

	/**
	 * Creates a trait of specified category.
	 * <p>
	 * This constructor is private, as required for an enumerated class.
	 * 
	 * @param category
	 *            the category of the trait
	 */
	private Trait(Category category) {
		this.category = category;
		this.abbreviation = name().substring(0, 4).toLowerCase();
	}
	
	/**
	 * Returns abbreviation, which is lower-cased and suitable for filenames and
	 * command-line arguments.
	 * 
	 * @return lower-case abbreviation
	 */
	public String getAbbreviation() {
		return abbreviation;
	}

	/**
	 * Returns category of this trait.
	 * 
	 * @return the category of this trait
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Returns user-friendly name: first-letter capped, all others lower-cased.
	 * 
	 * @return user-friendly name
	 */
	public String getName() {
		return name().charAt(0) + name().substring(1).toLowerCase();
	}
}
