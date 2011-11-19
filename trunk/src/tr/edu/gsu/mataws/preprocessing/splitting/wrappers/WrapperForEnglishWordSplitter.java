package tr.edu.gsu.mataws.preprocessing.splitting.wrappers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import de.abelssoft.wordtools.jwordsplitter.impl.EnglishWordSplitter;

public class WrapperForEnglishWordSplitter extends EnglishWordSplitter{

	private static Set<String> words = null;
	
	public WrapperForEnglishWordSplitter() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WrapperForEnglishWordSplitter(boolean withoutConnectingCharacters) throws IOException
	{
		super(withoutConnectingCharacters);
	}
	
	@Override
	protected Set<String> getWordList() throws IOException
	{
	if (words == null) {
        words = loadWords();
    }
		return words;
	}
	
	private static Set<String> loadWords() throws IOException
	{
		return (HashSet<String>)WrapperForFastObjectSaver.load("/wordsEnglish.ser");
	}
}
