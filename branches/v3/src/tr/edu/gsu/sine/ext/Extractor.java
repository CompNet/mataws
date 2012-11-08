package tr.edu.gsu.sine.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.col.Grain;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.ext.Trait.Category;
import tr.edu.gsu.sine.net.Edge;
import tr.edu.gsu.sine.net.Network;
import tr.edu.gsu.sine.net.Vertex;

/**
 * Provides a network extractor.
 * <p>
 * It can extract a network, given a collection and a profile of extraction.
 */
public class Extractor {

	/**
	 * The collection from which network is extracted.
	 */
	private Collection collection;

	/**
	 * A mapping between grains of the collection and vertices of the network.
	 */
	private Map<Grain, Vertex> grainToVertex;

	/**
	 * The network to be extracted.
	 */
	private Network network;

	/**
	 * The profile of the extraction method.
	 */
	private Profile profile;

	/**
	 * Creates an extractor for a collection of web services.
	 * 
	 * @param collection
	 *            the collection from which network has to be extracted
	 */
	public Extractor(Collection collection) {
		this.collection = collection;
	}

	/**
	 * Populates the network with edges, depending on the network model.
	 */
	private void extractEdges() {
		Trait model = profile.getTrait(Category.MODEL);
		switch (model) {
		case INTERACTION: {
			extractInteractionEdges();
			break;
		}
		case SIMILARITY: {
			extractSimilarityEdges();
			break;
		}
		}
	}

	/**
	 * Extracts edges according to the interaction model.
	 */
	private void extractInteractionEdges() {
		Vertex from;
		Vertex to;
		Edge edge;
		for (Grain src : grainToVertex.keySet()) {
			for (Grain dest : grainToVertex.keySet()) {
				if (src != dest && Interaction.exists(src, dest, profile)) {
					from = grainToVertex.get(src);
					to = grainToVertex.get(dest);
					edge = new Edge(from, to);
					network.addEdge(edge);
				}
			}
		}
	}

	/**
	 * Extracts edges, according to the similarity model.
	 */
	private void extractSimilarityEdges() {
		Vertex from;
		Vertex to;
		Edge edge;
		boolean isSymmetric = !network.isDirected();
		List<Grain> targets = new ArrayList<Grain>(grainToVertex.keySet());
		for (Grain src : grainToVertex.keySet()) {
			if (isSymmetric) {
				targets.remove(src);
			}
			for (Grain dest : targets) {
				if (src != dest && Similarity.exists(src, dest, profile)) {
					from = grainToVertex.get(src);
					to = grainToVertex.get(dest);
					edge = new Edge(from, to);
					network.addEdge(edge);
				}
			}
		}
	}

	/**
	 * Populates the network with vertices, according to selected granularity.
	 */
	private void extractVertices() {
		Trait granularity = profile.getTrait(Category.GRANULARITY);
		switch (granularity) {
		case SERVICE: {
			extractVertices(collection.getServices());
			break;
		}
		case OPERATION: {
			extractVertices(collection.getOperations());
			break;
		}
		case PARAMETER: {
			Vertex v;
			int vcount = 0;
			for (Parameter p : collection.getParameters()) {
				v = null;
				// Similar parameters point to the same vertex
				for (Grain g : grainToVertex.keySet()) {
					if (Similarity.exists(g, p, profile)) {
						v = grainToVertex.get(g);
						break;
					}
				}
				if (v == null) {
					vcount++;
					v = new Vertex(vcount, p.getName());
				}
				grainToVertex.put(p, v);
				network.addVertex(v);
			}
			break;
		}
		}
	}

	/**
	 * Populates the network with vertices, from a sorted set of grains.
	 * 
	 * @param <GT>
	 *            class of considered grains
	 * @param grains
	 *            a sorted set of grains representing the collection
	 */
	private <GT extends Grain> void extractVertices(SortedSet<GT> grains) {
		Vertex v;
		int vcount = 0;
		for (GT g : grains) {
			vcount++;
			v = new Vertex(vcount, g.getName());
			grainToVertex.put(g, v);
			network.addVertex(v);
		}
	}

	/**
	 * Proceeds to the network extraction using specified profile.
	 * 
	 * @param profile
	 *            the extraction method's profile
	 * @return the extracted network
	 */
	public Network proceed(Profile profile) {
		this.profile = profile;
		this.grainToVertex = new HashMap<Grain, Vertex>();
		this.network = new Network(collection.getName(), collection
				.getLanguage(), profile);

		extractVertices();
		extractEdges();

		return network;
	}

	/**
	 * Sets the collection from which network has to be extracted.
	 * 
	 * @param collection
	 *            the collection from which network has to be extracted
	 */
	public void setCollection(Collection collection) {
		this.collection = collection;
	}
}
