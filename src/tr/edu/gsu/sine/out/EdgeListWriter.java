package tr.edu.gsu.sine.out;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import tr.edu.gsu.sine.net.Edge;
import tr.edu.gsu.sine.net.Network;

/**
 * Provides support for writing network to a file in the Edge List format.
 */
public class EdgeListWriter extends PrintWriter implements NetworkWriter {

	/**
     * Creates an EdgeListWriter, without automatic line flushing, with the
     * specified file name.
     * 
	 * @see java.io.PrintWriter#PrintWriter(java.lang.String)
	 */
	public EdgeListWriter(String fileName) throws FileNotFoundException {
		super(fileName);
	}

	/**
	 * Writes a network to a file in the Edge List format.
	 * 
	 * @param network
	 *            network to be written
	 * @throws IOException
	 *             if file can not be entirely written
	 * @see tr.edu.gsu.sine.out.NetworkWriter#write(tr.edu.gsu.sine.net.Network)
	 */
	@Override
	public void write(Network network) throws IOException {
		for (Edge e: network.getEdges()) {
			println(e.getFrom() + " " + e.getTo());
		}
	}

}
