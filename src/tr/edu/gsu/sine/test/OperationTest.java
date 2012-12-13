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
 * Provides tests for each of operations comparison methods.
 * 
 * @see tr.edu.gsu.sine.col.Operation
 */
public class OperationTest {

	/** */
	private static Operation AtoA;
	/** */
	private static Operation AtoAn2;
	/** */
	private static Operation AtoAs2;
	/** */
	private static Operation AtoB;
	/** */
	private static Operation AtoC;
	/** */
	private static Operation AtoCB;
	/** */
	private static Operation AtoCBA;
	/** */
	private static Operation CAtoB;
	/** */
	private static Operation CAtoBC;
	/** */
	private static Operation CtoB;
	/** */
	private static Operation CtoBn2;
	/** */
	private static Operation CtoBs2;
	/** */
	private static Operation fromA;
	/** */
	private static Operation fromC;
	/** */
	private static Operation fromABC;
	/** */
	private static Operation toABC;
	/** */
	private static Operation toCBA;
	
	/**
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Create parameters.
		
		Parameter a = new Parameter("A");
		Parameter b = new Parameter("B");
		Parameter c = new Parameter("C");
		
		// Create services.
		
		Service s1 = new Service("s1");
		s1.setLocation("");

		Service s2 = new Service("s2");
		s2.setLocation("");
		
		// Create operations.
		
		AtoA = new Operation("AtoA");
		AtoA.addParameter(a, Way.IN);
		AtoA.addParameter(a, Way.OUT);
		s1.addOperation(AtoA);
		
		// same than AtoA but the name
		AtoAn2 = new Operation("AtoAn2");
		AtoAn2.addParameter(a, Way.IN);
		AtoAn2.addParameter(a, Way.OUT);
		s1.addOperation(AtoAn2);

		// same than AtoA but the service
		AtoAs2 = new Operation("AtoA");
		AtoAs2.addParameter(a, Way.IN);
		AtoAs2.addParameter(a, Way.OUT);
		s1.addOperation(AtoAs2);
		
		AtoB = new Operation("AtoB");
		AtoB.addParameter(a, Way.IN);
		AtoB.addParameter(b, Way.OUT);
		s1.addOperation(AtoB);
		
		AtoC = new Operation("AtoC");
		AtoC.addParameter(a, Way.IN);
		AtoC.addParameter(c, Way.OUT);
		s1.addOperation(AtoC);
		
		AtoCB = new Operation("AtoCB");
		AtoCB.addParameter(a, Way.IN);
		AtoCB.addParameter(c, Way.OUT);
		AtoCB.addParameter(b, Way.OUT);
		s1.addOperation(AtoCB);
		
		AtoCBA = new Operation("AtoCBA");
		AtoCBA.addParameter(a, Way.IN);
		AtoCBA.addParameter(c, Way.OUT);
		AtoCBA.addParameter(b, Way.OUT);
		AtoCBA.addParameter(a, Way.OUT);
		s1.addOperation(AtoCBA);
		
		CAtoB = new Operation("CAtoB");
		CAtoB.addParameter(c, Way.IN);
		CAtoB.addParameter(a, Way.IN);
		CAtoB.addParameter(b, Way.OUT);
		s1.addOperation(CAtoB);

		CAtoBC = new Operation("CAtoBC");
		CAtoBC.addParameter(c, Way.IN);
		CAtoBC.addParameter(a, Way.IN);
		CAtoBC.addParameter(b, Way.OUT);
		CAtoBC.addParameter(c, Way.OUT);
		s1.addOperation(CAtoBC);

		CtoB = new Operation("CtoB");
		CtoB.addParameter(c, Way.IN);
		CtoB.addParameter(b, Way.OUT);
		s1.addOperation(CtoB);

		// same than CtoB but the name
		CtoBn2 = new Operation("CtoBn2");
		CtoBn2.addParameter(c, Way.IN);
		CtoBn2.addParameter(b, Way.OUT);
		s1.addOperation(CtoBn2);
		
		// same than CtoB but the service
		CtoBs2 = new Operation("CtoB");
		CtoBs2.addParameter(c, Way.IN);
		CtoBs2.addParameter(b, Way.OUT);
		s2.addOperation(CtoB);
		
		fromA = new Operation("fromA");
		fromA.addParameter(a, Way.IN);
		s1.addOperation(fromA);
		
		fromC = new Operation("fromC");
		fromC.addParameter(c, Way.IN);
		s1.addOperation(fromC);
		
		fromABC = new Operation("fromABC");
		fromABC.addParameter(a, Way.IN);
		fromABC.addParameter(b, Way.IN);
		fromABC.addParameter(c, Way.IN);
		s1.addOperation(fromABC);
		
		toABC = new Operation("toABC");
		toABC.addParameter(a, Way.OUT);
		toABC.addParameter(b, Way.OUT);
		toABC.addParameter(c, Way.OUT);
		s1.addOperation(toABC);

		toCBA= new Operation("toCBA");
		toCBA.addParameter(c, Way.OUT);
		toCBA.addParameter(b, Way.OUT);
		toCBA.addParameter(a, Way.OUT);
		s1.addOperation(toCBA);
	}

	/**
	 * 
	 */
	@Test
	public void testInteraction() {
		// Full Interaction between operations
		Profile profile = Profile.IFOSE;
		
		Assert.assertTrue(Interaction.exists(AtoC, CtoB, profile));
		Assert.assertFalse(Interaction.exists(AtoC, fromABC, profile));
		Assert.assertFalse(Interaction.exists(AtoC, toABC, profile));

		Assert.assertFalse(Interaction.exists(fromABC, AtoC, profile));
		Assert.assertFalse(Interaction.exists(fromABC, toABC, profile));
		Assert.assertTrue(Interaction.exists(toABC, fromABC, profile));
		
		Assert.assertTrue(Interaction.exists(AtoA, AtoAn2, profile));
		Assert.assertTrue(Interaction.exists(AtoA, AtoAs2, profile));
		
		// Note that isGivingAccess() does not check for identity.
		// Loops are discarded at a higher level.
		Assert.assertTrue(Interaction.exists(AtoA, AtoA, profile));
	}

	/**
	 * 
	 */
	@Test
	public void testIsFullySimilarTo() {
		// Full Similarity between operations
		Profile profile = Profile.SFOSE;
		
		Assert.assertTrue(Similarity.exists(AtoB, CAtoB, profile));
		Assert.assertTrue(Similarity.exists(CAtoB, AtoB, profile));
		
		Assert.assertTrue(Similarity.exists(CtoB, CtoB, profile));
		Assert.assertTrue(Similarity.exists(CtoB, CtoBn2, profile));
		Assert.assertTrue(Similarity.exists(CtoB, CtoBs2, profile));

		Assert.assertFalse(Similarity.exists(AtoB, CtoB, profile));		
		Assert.assertFalse(Similarity.exists(AtoCBA, toABC, profile));
		Assert.assertFalse(Similarity.exists(toABC, AtoCBA, profile));
		
		// Note that isSimilarTo() does not check for identity.
		// Loops are discarded at a higher level.
		Assert.assertTrue(Similarity.exists(CtoB, CtoB, profile));
	}

	/**
	 * 
	 */
	@Test
	public void testIsSimilarWithExcessTo() {
		// Similarity with excess between operations
		Profile profile = Profile.SEOSE;
		
		Assert.assertTrue(Similarity.exists(AtoC, AtoCB, profile));
		Assert.assertTrue(Similarity.exists(AtoCB, AtoCBA, profile));

		Assert.assertFalse(Similarity.exists(AtoCBA, AtoC, profile));
		Assert.assertFalse(Similarity.exists(AtoC, AtoC, profile));

		Assert.assertFalse(Similarity.exists(AtoC, AtoB, profile));
		Assert.assertFalse(Similarity.exists(AtoB, CtoB, profile));
		Assert.assertFalse(Similarity.exists(CtoB, CtoBn2, profile));

		Assert.assertFalse(Similarity.exists(AtoCB, toABC, profile));
		Assert.assertFalse(Similarity.exists(AtoCBA, toABC, profile));
		Assert.assertFalse(Similarity.exists(fromABC, toABC, profile));
		Assert.assertFalse(Similarity.exists(fromA, fromABC, profile));
	}

	/**
	 * 
	 */
	@Test
	public void testIsPartlySimilarTo() {
		// Partial Similarity between operations
		Profile profile = Profile.SPOSE;

		Assert.assertTrue(Similarity.exists(AtoCBA, AtoB, profile));
		Assert.assertFalse(Similarity.exists(AtoCBA, fromABC, profile));
		
		Assert.assertFalse(Similarity.exists(AtoCBA, toABC, profile));
		Assert.assertFalse(Similarity.exists(AtoCBA, AtoCBA, profile));

		Assert.assertTrue(Similarity.exists(CAtoBC, CtoB, profile));
		Assert.assertTrue(Similarity.exists(CAtoBC, CAtoB, profile));
		Assert.assertFalse(Similarity.exists(CAtoBC, AtoCB, profile));
	}

	/**
	 * 
	 */
	@Test
	public void testIsRelatedTo() {
		// Relational Similarity between operations
		Profile profile = Profile.SROSE;

		Assert.assertTrue(Similarity.exists(AtoB, CtoB, profile));
		Assert.assertTrue(Similarity.exists(AtoB, CtoBn2, profile));
		Assert.assertTrue(Similarity.exists(AtoB, CtoBs2, profile));
		
		Assert.assertFalse(Similarity.exists(AtoC, CtoB, profile));
		Assert.assertFalse(Similarity.exists(AtoB, CAtoB, profile));
		Assert.assertFalse(Similarity.exists(AtoB, AtoC, profile));
		
		Assert.assertFalse(Similarity.exists(fromA, fromC, profile));
		Assert.assertFalse(Similarity.exists(fromA, AtoC, profile));
		Assert.assertFalse(Similarity.exists(fromA, CtoB, profile));
		
		Assert.assertFalse(Similarity.exists(toABC, toCBA, profile));
		Assert.assertFalse(Similarity.exists(toABC, AtoCBA, profile));
		// Note that isSimilarTo() does not check for identity.
		// Loops are discarded at a higher level.
		Assert.assertFalse(Similarity.exists(toABC, toABC, profile));
	}
}
