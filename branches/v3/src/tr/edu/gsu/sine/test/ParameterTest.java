package tr.edu.gsu.sine.test;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import tr.edu.gsu.sine.col.Operation;
import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.col.Service;
import tr.edu.gsu.sine.col.Way;
import tr.edu.gsu.sine.ext.Interaction;
import tr.edu.gsu.sine.ext.Profile;
import tr.edu.gsu.sine.ext.Similarity;

/**
 * Provides tests for each of parameters comparison methods.
 * 
 * @see tr.edu.gsu.sine.col.Parameter
 */
public class ParameterTest {
	
	private static Parameter pAin1, pAout1, pBin1, pBout1;
	private static Parameter pAin2, pBout2, pCin2, pCout2;
	private static Profile profile;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// Parameters do not exist in the wild,
		// they are taking part in operations of web services,
		// It is why we have to create both of them.
		
		Service s = new Service("s");
		s.setLocation("");

		Operation o1 = new Operation("o1");
		o1.addParameter(pAin1 = new Parameter("pA"), Way.IN);
		o1.addParameter(pAout1 = new Parameter("pA"), Way.OUT);
		o1.addParameter(pBin1 = new Parameter("pB"), Way.IN);
		o1.addParameter(pBout1 = new Parameter("pB"), Way.OUT);
		s.addOperation(o1);

		Operation o2 = new Operation("o2");
		o2.addParameter(pAin2 = new Parameter("pA"), Way.IN);
		o2.addParameter(pBout2 = new Parameter("pB"), Way.OUT);
		o2.addParameter(pCin2 = new Parameter("pC"), Way.IN);
		o2.addParameter(pCout2 = new Parameter("pC"), Way.OUT);
		s.addOperation(o2);
		
		// set syntactic equal matching of parameters
		profile = Profile.IFPSE;
	}

	@Test
	public void testInteraction() {
		Assert.assertFalse(Interaction.exists(pAin1, pAin1, profile));
		Assert.assertFalse(Interaction.exists(pAin1, pAout1, profile));
		Assert.assertTrue(Interaction.exists(pAin1, pBin1, profile));
		Assert.assertTrue(Interaction.exists(pAin1, pBout1, profile));
		Assert.assertTrue(Interaction.exists(pAin1, pBout2, profile));
		Assert.assertFalse(Interaction.exists(pAin1, pCout2, profile));
		Assert.assertFalse(Interaction.exists(pAin2, pAout1, profile));
		Assert.assertTrue(Interaction.exists(pAin2, pBout1, profile));
		Assert.assertTrue(Interaction.exists(pAin2, pCout2, profile));
		Assert.assertTrue(Interaction.exists(pAin2, pCin2, profile));
		Assert.assertFalse(Interaction.exists(pAout1, pAout1, profile));
		Assert.assertFalse(Interaction.exists(pAout1, pBout1, profile));
	}

	@Test
	public void testSimilarity() {
		Assert.assertTrue(Similarity.exists(pAin1, pAin1, profile));
		Assert.assertTrue(Similarity.exists(pAin1, pAout1, profile));
		Assert.assertTrue(Similarity.exists(pAin1, pAin2, profile));
		Assert.assertTrue(Similarity.exists(pAout1, pAin2, profile));
		Assert.assertFalse(Similarity.exists(pAin1, pBin1, profile));
		Assert.assertFalse(Similarity.exists(pAout1, pBout1, profile));
	}

}
