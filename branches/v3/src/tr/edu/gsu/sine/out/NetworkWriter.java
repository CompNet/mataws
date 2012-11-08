package tr.edu.gsu.sine.out;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

import tr.edu.gsu.sine.net.Network;

/**
 * Defines the methods components required to write a network to a file.
 */
public interface NetworkWriter extends Appendable, Closeable, Flushable {

	/**
	 * Writes a network to a file.
	 * 
	 * @param network
	 *            the network to be written
	 * @throws IOException
	 *             the file has not been entirely written
	 */
	public void write(Network network) throws IOException;
}
