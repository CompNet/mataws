package tr.edu.gsu.sine.out;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tr.edu.gsu.sine.col.Collection;
import tr.edu.gsu.sine.col.Parameter;

/**
 * Provides support for writing parameters names to a file.
 */
public class ParamsWriter extends PrintWriter {

	/**
	 * Returns a sorted list of entries stored in the specified map.
	 * <p>
	 * Entries are sorted by value first, then by key.
	 * 
	 * @param <K>
	 *            the type of the map keys.
	 * @param <V>
	 *            the type of the map values.
	 * @param map
	 *            the map to get sorted entries from.
	 * @return a list of entries sorted by value then by key.
	 */
	private static <K extends Comparable<K>, V extends Comparable<V>> List<Map.Entry<K, V>> sortByValueFirst(
			Map<K, V> map) {
		// Copy map entries into a list.
		List<Map.Entry<K, V>> entries = new ArrayList<Map.Entry<K, V>>(map
				.entrySet());
		// Sort it by count first, then by name.
		Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				int res = o1.getValue().compareTo(o2.getValue());
				return res != 0 ? res : o1.getKey().compareTo(o2.getKey());
			}
		});
		return entries;
	}

	/**
	 * Creates a ParamsWriter, without automatic line flushing, with the
	 * specified target file.
	 * 
	 * @see java.io.PrintWriter#PrintWriter(java.io.File)
	 */
	public ParamsWriter(File outputFile) throws FileNotFoundException {
		super(outputFile);
	}

	/**
	 * Writes the list of parameters names sorted by occurrences count.
	 * 
	 * @param col
	 *            the collection to get parameters from
	 */
	public void writeByCount(Collection col) {
		// Count names occurrences
		Map<String, Integer> namesCount = new HashMap<String, Integer>();
		for (Parameter p : col.getParameters()) {
			String name = p.getName();
			if (!namesCount.containsKey(name)) {
				// first occurrence
				namesCount.put(name, 1);
			} else {
				// other occurrences
				namesCount.put(name, namesCount.get(name) + 1);
			}
		}

		// Sort names by number of occurrences, then alphabetically.
		List<Map.Entry<String, Integer>> sortedCounts = sortByValueFirst(namesCount);

		// Print sorted names and counts.
		for (Map.Entry<String, Integer> nameCount : sortedCounts) {
			println(nameCount.getKey() + " " + nameCount.getValue());
		}
	}

	/**
	 * Writes the list of parameters sorted by name.
	 * <p>
	 * If it fails for any cause, it can still be retried later.
	 * 
	 * @throws IOException
	 *             if there are IO problems opening the parameters list file
	 */
	public void writeByName(Collection collection) throws IOException {
		for (Parameter p : collection.getParameters()) {
			println(p.getUniqueName());
		}
	}
}
