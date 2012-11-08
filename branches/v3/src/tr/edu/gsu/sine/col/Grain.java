package tr.edu.gsu.sine.col;


/**
 * Provides a descriptive element (or grain), eligible for vertex association.
 * <p>
 * This is an abstraction of granularity. It specifies the methods which must be
 * implemented in order to be able to be associated with a vertex by extractor. 
 * <p>
 * It extends the interface Comparable for use with SortedSet.
 * 
 * @see tr.edu.gsu.sine.ext.Trait.Category#GRANULARITY
 * @.NOTE It is named Grain not to confuse with XML DOM Element used by parsers.
 */
public interface Grain extends Comparable<Grain> {

	/**
	 * Returns the name of this grain.
	 * 
	 * @return the name of this grain
	 */
	public String getName();

	/**
	 * Returns the parent of this grain.
	 * 
	 * @return the parent of this grain 
	 */
	public Grain getParent();
	
	/**
	 * Returns the unique name of this grain.
	 * 
	 * @return the unique name of this grain
	 */
	public String getUniqueName();

	/**
	 * Sets the parent of this grain.
	 * 
	 * @param parent
	 *            the parent grain
	 */
	public void setParent(Grain parent);
}
