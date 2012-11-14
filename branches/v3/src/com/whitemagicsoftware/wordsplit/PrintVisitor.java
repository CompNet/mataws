package com.whitemagicsoftware.wordsplit;

import java.util.List;

/**
 * Responsible for writing out a list of words.
 */
public class PrintVisitor implements Visitor {
	/**
	 * Default constructor.
	 */
	public PrintVisitor() {
	}

	/**
	 * Writes the given parameter to standard output.
	 *
	 * @param list - The list of values to write to stdout.
	 */
	@SuppressWarnings("rawtypes")
	public SegmentAnalysis visit( List list ) {
		System.out.println( list );
		return null;
	}
}
