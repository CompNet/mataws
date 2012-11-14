import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;


public class Test
{

	public static void main(String[] args)
	{
		Levenshtein distance = new Levenshtein();
		float f = distance.getSimilarity("abcd", "azerb");
		System.out.println(f);
		f = distance.getSimilarity("abcd", "abcd");
		System.out.println(f);
	}
}
