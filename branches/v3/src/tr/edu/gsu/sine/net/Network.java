package tr.edu.gsu.sine.net;

import java.util.SortedSet;
import java.util.TreeSet;

import tr.edu.gsu.sine.ext.Profile;
import tr.edu.gsu.sine.ext.Trait;
import tr.edu.gsu.sine.in.Language;

/**
 * Provides a representation of a network, with vertices, edges and meta-data.
 * <p>
 * This is an intermediate representation which should be easily transformed in
 * any kind of format (edges list, adjacency matrix, etc.). It is the main
 * reason behind the acceptable redundancy of Vertex and Edge classes.
 */
public class Network {
	
	/**
	 * Language describing services from which this network has been extracted.
	 */
	private final Language collectionLanguage;
	
	/**
	 * Name of the collection from which this network has been extracted.
	 */
	private final String collectionName;
	
	/**
	 * Whether the network is directed or undirected.
	 */
	private final boolean directed;
	
	/**
	 * Edges (or arcs) of the network.
	 */
	private final SortedSet<Edge> edges;
	
	/**
	 * Profile of the extraction method.
	 */
	private final Profile profile;
	
	/**
	 * Vertices of the network.
	 */
	private final SortedSet<Vertex> vertices;

	/**
	 * Creates an empty network, with meta-data.
	 * 
	 * @param collecName
	 *            name of the collection the network is extracted from
	 * @param collecLanguage
	 *            language describing services of that collection
	 * @param profile
	 *            extraction method profile
	 */
	public Network(String collecName, Language collecLanguage, Profile profile) {
		this.collectionName = collecName;
		this.collectionLanguage = collecLanguage;
		this.profile = profile;
		this.vertices = new TreeSet<Vertex>();
		this.edges = new TreeSet<Edge>();
		this.directed = !(profile.contains(Trait.SIMILARITY) && (profile
				.contains(Trait.FULL) || profile.contains(Trait.RELATIONAL)));
	}

	/**
	 * Adds an edge to this network.
	 * 
	 * @param edge the edge to be added
	 */
	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	/**
	 * Adds a vertex to this network.
	 * 
	 * @param vertex the vertex to be added
	 */
	public void addVertex(Vertex vertex) {
		vertices.add(vertex);
	}
	
	/**
	 * Returns the service description language used in the collection this
	 * network has been extracted from.
	 * 
	 * @return the description language it originates from
	 */
	public Language getCollectionLanguage() {
		return collectionLanguage;
	}

	/**
	 * Returns the name of the collection this network has been extracted from.
	 * 
	 * @return the name of the collection this network has been extracted from
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * Returns the edges of this network.
	 * 
	 * @return the edges of this network
	 */
	public SortedSet<Edge> getEdges() {
		return edges;
	}

	/**
	 * Returns the number of edges in this network.
	 * 
	 * @return the number of edges in this network
	 */
	public int getNbEdges() {
		return edges.size();
	}

	/**
	 * Returns the number of vertices in this network.
	 * 
	 * @return the number of vertices in this network
	 */
	public int getNbVertices() {
		return vertices.size();
	}

	/**
	 * Returns the extraction method profile used for this network.
	 * 
	 * @return the extraction method profile used for this network
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * Returns the vertices of this network.
	 * 
	 * @return the vertices of this network
	 */
	public SortedSet<Vertex> getVertices() {
		return vertices;
	}

	/**
	 * Returns true if this network is directed, false if it is undirected.
	 * 
	 * @return true if this network is directed, false if it is undirected
	 */
	public boolean isDirected() {
		return directed;
	}
}
