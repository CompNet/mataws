package tr.edu.gsu.sine.net;

/**
 * Represents a vertex of a network.
 */
public class Vertex implements Comparable<Vertex> {
	
	/**
	 * Identifier of this vertex.
	 */
	private final int id;
	
	/**
	 * Name of this vertex.
	 */
	private final String name;
	
	/**
	 * Creates a vertex.
	 * 
	 * @param id
	 *            the identifier of the vertex
	 * @param name
	 *            the name of the vertex
	 */
	public Vertex(int id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Compares to vertex by identifier.
	 * <p>
	 * This method is required by SortedSet.
	 *  
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Vertex vertex) {
		return id - vertex.id;
	}
	
	/**
	 * Returns the identifier of this vertex.
	 * 
	 * @return the identifier of this vertex
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the name of this vertex.
	 * 
	 * @return the name of this vertex
	 */
	public String getName() {
		return name;
	}
}
