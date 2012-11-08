package tr.edu.gsu.sine.col;

/**
 * Provides a common preliminary implementation of parts of the Grain interface.
 */
public abstract class AbstractGrain implements Grain {

	/**
	 * Name separator, for usage in unique name.
	 */
	protected static final String NAME_SEP = "@";

	/**
	 * Escapes "&#092;" and {@value #NAME_SEP} characters from a string.
	 * <p>
	 * It prevents grain names with {@value #NAME_SEP} to corrupt unique name.  
	 * 
	 * @param str the string to escape special characters from
	 * @return an safe string for usage in unique name
	 * @see #NAME_SEP
	 * @see #getUniqueName()
	 */
	protected static String escape(String str) {
		return str.replace("\\", "\\\\").replace(NAME_SEP, "\\" + NAME_SEP);
	}

	/**
	 * Name of this grain.
	 */
	protected String name;
	
	/**
	 * Parent of this grain.
	 */
	protected Grain parent;
	
	/**
	 * Compares to the specified grain by name, then by parent.
	 * 
	 * @see tr.edu.gsu.sine.col.Grain
	 */
	@Override
	public int compareTo(Grain grain) {
		int r = name.compareTo(grain.getName());
		return r == 0 ? parent.compareTo(((AbstractGrain) grain).parent) : r;
	}

	/* (non-Javadoc)
	 * @see sine.collec.Grain#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see sine.col.Grain#getParent()
	 */
	@Override
	public Grain getParent() {
		return parent;
	}
	
	/**
	 * Returns the unique name of this grain.
	 * <p>
	 * Uniqueness is acquired by concatenation of parent's unique name to name.
	 * 
	 * @see tr.edu.gsu.sine.col.Grain#getUniqueName()
	 * @return the unique name of this grain
	 */
	@Override
	public String getUniqueName() {
		return escape(name) + NAME_SEP + parent.getUniqueName();
	}

	/* (non-Javadoc)
	 * @see sine.collec.Grain#setParent(sine.collec.Grain)
	 */
	@Override
	public void setParent(Grain parent) {
		this.parent = parent;
	}
}
