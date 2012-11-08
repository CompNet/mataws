package tr.edu.gsu.sine.net;

/**
 * Represents an edge (or arc) of a network.
 */
public class Edge implements Comparable<Edge> {
	
	/**
	 * Start vertex.
	 */
	private final Vertex from;
	
	/**
	 * End vertex.
	 */
	private final Vertex to;
	
	/**
	 * Creates an edge between two vertices.
	 * 
	 * @param from
	 *            the vertex this edge starts from
	 * @param to
	 *            the vertex this edge ends to
	 */
	public Edge(Vertex from, Vertex to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * Compares to edge by source then destination vertices.
	 * <p>
	 * This method is required by SortedSet.
	 *  
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Edge edge) {
		int res = from.compareTo(edge.from);
		return res == 0 ? to.compareTo(edge.to) : res ;
	}

	/**
	 * Returns the vertex this edge starts from.
	 * 
	 * @return the vertex this edge starts from
	 */
	public int getFrom() {
		return from.getId();
	}

	/**
	 * Returns the vertex this edge ends to.
	 * 
	 * @return the vertex this edge ends to
	 */
	public int getTo() {
		return to.getId();
	}
}
