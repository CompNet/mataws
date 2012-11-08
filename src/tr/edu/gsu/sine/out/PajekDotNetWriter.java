package tr.edu.gsu.sine.out;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import tr.edu.gsu.sine.ext.FlexibleMetric;
import tr.edu.gsu.sine.ext.Trait;
import tr.edu.gsu.sine.net.Edge;
import tr.edu.gsu.sine.net.Network;
import tr.edu.gsu.sine.net.Vertex;

/**
 * Provides support for writing network to a file in a simple <a
 * href="http://vlado.fmf.uni-lj.si/pub/networks/pajek/doc/pajekman.pdf">Pajek
 * .net format</a>.
 * <p>
 * This format is simple enough to be <a
 * href="http://igraph.sourceforge.net/igraphbook/igraphbook-foreign.html">read
 * by the igraph package</a> for R software.
 */
public class PajekDotNetWriter extends PrintWriter implements NetworkWriter {
	
	/**
	 * Line separator has to be the DOS line separator in Pajek .net files.
	 */
	private static String LINE_SEP = "\r\n";
	
	/**
	 * Creates a PajekDotNetWriter, without automatic line flushing, opening
	 * the file of specified name.
	 * 
	 * @see java.io.PrintWriter#PrintWriter(java.lang.String)
	 */
	public PajekDotNetWriter(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	/**
	 * Prints a string as a Pajek .net comment line.
	 * 
	 * @param comment
	 *            the string to be printed as a comment
	 */
	public void printComment(String comment) {
		println("/* " + comment + " */");
	}

	/**
	 * Terminates the current line as defined by Pajek .net format.
	 * 
	 * @see java.io.PrintWriter#println()
	 */
	@Override
	public void println() {
		print(LINE_SEP);
	}
	
	/**
	 * Prints a string and terminates the line as defined by Pajek .net format.
	 * 
	 * @param s
	 *            the string to be printed
	 * @see java.io.PrintWriter#println(String)
	 */
	@Override
	public void println(String s) {
		print(s + LINE_SEP);
	}

	/**
	 * Writes a network to file in the Pajek .net format.
	 * 
	 * @param network
	 *            the network to be written to a file
	 * @throws IOException
	 *             file has not been entirely written
	 * @see tr.edu.gsu.sine.out.NetworkWriter#write(tr.edu.gsu.sine.net.Network)
	 */
	@Override
	public void write(Network network) throws IOException {
		
		// Write meta-data into heading comments.
		
		printComment("Collection: " + network.getCollectionName());
		println();
		
		for (Trait t: network.getProfile().getTraits()) {
			printComment(t.getCategory().getName() + ": " + t.getName());
		}
		if (network.getProfile().contains(Trait.FLEXIBLE)) {
			printComment("Flexible matching threshold: "
					+ FlexibleMetric.getThreshold());
		}
		println();
		
		printComment(network.isDirected() ? "Directed" : "Undirected");
		println();
		
		// Write vertices.
	
		println("*Vertices " + network.getNbVertices());
		for (Vertex v : network.getVertices()) {
			println(v.getId() + " \"" + v.getName() + '"');
		}
		// N.B. Pajek does not support blank lines between vertices and edges.
	
		// Write edges.
		
		println(network.isDirected() ? "*Arcs" : "*Edges");
		for (Edge e : network.getEdges()) {
			println(e.getFrom() + " " + e.getTo());
		}
		println();
	}
}
