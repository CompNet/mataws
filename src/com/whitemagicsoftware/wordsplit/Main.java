package com.whitemagicsoftware.wordsplit;

import java.io.File;
import java.io.IOException;

/**
 * Splits concatenated text into a sentence.
 */
public class Main {
  /**
   * Default constructor.
   */
  public Main() {
  }

	private static void out( String s ) {
		System.out.println( s );
	}

  /**
   * Main application. Takes a lexicon (with probabilities) and list of
   * concatenated strings. Writes the split strings to standard output.
   * @param args 
   * @throws IOException 
   */
  public static void main( String args[] )
    throws IOException {
    TextSegmenter ts = new TextSegmenter();

    if( args.length == 2 ) {
      try {
        ts.split( new File( args[0] ), new File( args[1] ) );
      }
      catch( Exception e ) {
        System.err.println( "Error: " + e.getMessage() );
        e.printStackTrace();
      }
    }
    else {
      out( "com.whitemagicsoftware.wordsplit.Main <lexicon> <conjoined>" );
      out( "<lexicon>   - CSV file: word,probability" );
      out( "<conjoined> - Text file" );
    }
  }
}

