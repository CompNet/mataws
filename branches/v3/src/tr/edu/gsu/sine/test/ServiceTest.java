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

/**
 * Provides tests for each of services comparison methods.
 * 
 * @see tr.edu.gsu.sine.col.Operation
 * @.NOTE The combinatorial complexity is such that many cases might be missing.
 */
public class ServiceTest {

	private static Service empty, fromNilToNil, fromNilToABC, fromABCtoNil;

	// For interaction tests only
	private static Service fromAtoB, fromAtoC, fromAtoBC, fromAtoBandC;
	private static Service fromBtoD, fromCtoD, fromCBtoD, fromCandBtoD;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Create parameters.
		
		Parameter a = new Parameter("A");
		Parameter b = new Parameter("B");
		Parameter c = new Parameter("C");
		Parameter d = new Parameter("D");
		
		// Create operations.
		
		Operation nil2nil =  new Operation("nil2nil");
		
		Operation nil2ABC = new Operation("nil2ABC");
		nil2ABC.addParameter(a, Way.OUT);
		nil2ABC.addParameter(b, Way.OUT);
		nil2ABC.addParameter(c, Way.OUT);

		Operation ABC2nil = new Operation("ABC2nil");
		ABC2nil.addParameter(a, Way.IN);
		ABC2nil.addParameter(b, Way.IN);
		ABC2nil.addParameter(c, Way.IN);

		// Create operations for interaction tests.
		
		Operation A2B, A2C, A2BC;
		Operation B2D, C2D, CB2D;

		A2B = new Operation("A2B");
		A2B.addParameter(a, Way.IN);
		A2B.addParameter(b, Way.OUT);
		
		A2C = new Operation("A2C");
		A2C.addParameter(a, Way.IN);
		A2C.addParameter(c, Way.OUT);
		
		A2BC = new Operation("A2BC");
		A2BC.addParameter(a, Way.IN);
		A2BC.addParameter(b, Way.OUT);
		A2BC.addParameter(c, Way.OUT);

		B2D = new Operation("B2D");
		B2D.addParameter(b, Way.IN);
		B2D.addParameter(d, Way.OUT);

		C2D = new Operation("C2D");
		C2D.addParameter(c, Way.IN);
		C2D.addParameter(d, Way.OUT);

		CB2D = new Operation("CB2D");
		CB2D.addParameter(c, Way.IN);
		CB2D.addParameter(b, Way.IN);
		CB2D.addParameter(d, Way.OUT);

		// Create services.
		
		empty = new Service("empty");
		
		fromNilToNil = new Service("fromNiltoNil");
		fromNilToNil.addOperation(nil2nil);
		
		fromNilToABC = new Service("fromNilToABC");
		fromNilToABC.addOperation(nil2ABC);
		
		fromABCtoNil = new Service("fromABCtoNil");
		fromABCtoNil.addOperation(ABC2nil);
		
		// Create services for interaction tests.
		
		fromAtoB = new Service("fromAtoB");
		fromAtoB.addOperation(A2B);
		
		fromAtoC = new Service("fromAtoC");
		fromAtoC.addOperation(A2C);
		
		fromAtoBC = new Service("fromAtoBC");
		fromAtoBC.addOperation(A2BC);
		
		fromAtoBandC = new Service("fromAtoBandC");
		fromAtoBandC.addOperation(A2B);
		fromAtoBandC.addOperation(A2C);
	
		fromBtoD = new Service("fromBtoD");
		fromBtoD.addOperation(B2D);
		
		fromCtoD = new Service("fromCtoD");
		fromCtoD.addOperation(C2D);
		
		fromCBtoD = new Service("fromCBtoD");
		fromCBtoD.addOperation(CB2D);
		
		fromCandBtoD = new Service("fromCandBtoD");
		fromCandBtoD.addOperation(C2D);
		fromCandBtoD.addOperation(B2D);
	}

	@Test
	public void testInteraction() {
		// Full Interaction between operations
		Profile p = Profile.IFSSE;

		Assert.assertTrue(Interaction.exists(fromAtoB, fromBtoD, p));
		Assert.assertTrue(Interaction.exists(fromAtoBC, fromCtoD, p));
		Assert.assertTrue(Interaction.exists(fromAtoBC, fromCBtoD, p));
		Assert.assertTrue(Interaction.exists(fromAtoBC, fromCandBtoD, p));
		Assert.assertTrue(Interaction.exists(fromAtoBandC, fromBtoD, p));
		Assert.assertTrue(Interaction.exists(fromAtoBandC, fromCBtoD, p));
		Assert.assertTrue(Interaction.exists(fromAtoBandC, fromCandBtoD, p));

		Assert.assertFalse(Interaction.exists(fromAtoC, fromBtoD, p));
		Assert.assertFalse(Interaction.exists(fromAtoC, fromCBtoD, p));
		Assert.assertFalse(Interaction.exists(fromAtoC, fromCandBtoD, p));
		
		Assert.assertFalse(Interaction.exists(empty, fromABCtoNil, p));
		Assert.assertFalse(Interaction.exists(empty, fromNilToABC, p));
		Assert.assertFalse(Interaction.exists(fromABCtoNil, fromNilToABC, p));
		Assert.assertFalse(Interaction.exists(fromABCtoNil, empty, p));
		Assert.assertFalse(Interaction.exists(fromNilToABC, empty, p));

		Assert.assertFalse(Interaction.exists(fromNilToNil, fromABCtoNil, p));
		Assert.assertFalse(Interaction.exists(fromNilToNil, fromNilToABC, p));
		Assert.assertFalse(Interaction.exists(fromABCtoNil, fromNilToABC, p));
		Assert.assertFalse(Interaction.exists(fromABCtoNil, fromNilToNil, p));
		Assert.assertFalse(Interaction.exists(fromNilToABC, fromNilToNil, p));
	}
}
