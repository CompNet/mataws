package tr.edu.gsu.sine.test;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import tr.edu.gsu.sine.col.Parameter;
import tr.edu.gsu.sine.ext.ParamListUtils;
import tr.edu.gsu.sine.ext.Profile;

/**
 * Provides tests for each of parameters list comparison methods.
 * 
 * @see tr.edu.gsu.sine.ext.ParamListUtils
 */
public class ParamListUtilsTest {
	
	/** */
	private static List<List<Parameter>> allLists;
	/** */
	private static List<Parameter> lEmpty;
	/** */
	private static List<Parameter> lAB;
	/** */
	private static List<Parameter> lABC;
	/** */
	private static List<Parameter> lB;
	/** */
	private static List<Parameter> lCA;
	/** */
	private static List<Parameter> lCBA;
	/** */
	private static Profile p;
	
	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// initialize parameters
		Parameter a = new Parameter("a");
		Parameter b = new Parameter("b");
		Parameter c = new Parameter("c");
		
		// initialize list of parameters
		lAB = Arrays.asList(a, b);
		lABC = Arrays.asList(a, b, c);
		lB = Arrays.asList(b);
		lCA = Arrays.asList(c, a);
		lCBA = Arrays.asList(c, b, a);
		lEmpty = Arrays.asList();

		// gather all lists for identity tests
		allLists = Arrays.asList(lAB, lABC, lB, lCA, lCBA, lEmpty);
		Assert.assertNotNull(allLists);
		Assert.assertEquals(6, allLists.size());
		
		// set syntactic equal matching of parameters
		p = Profile.IFOSE;
	}
	
	/**
	 * 
	 */
	@Test
	public void testAreIdentical() {
		for (List<Parameter> l : allLists) {
			Assert.assertTrue(ParamListUtils.areIdentical(l, l, p));
		}
		Assert.assertFalse(ParamListUtils.areIdentical(lAB, lB, p));
		Assert.assertFalse(ParamListUtils.areIdentical(lAB, lCA, p));
		Assert.assertFalse(ParamListUtils.areIdentical(lAB, lEmpty, p));
		Assert.assertTrue(ParamListUtils.areIdentical(lABC, lCBA, p));
	}

	/**
	 * 
	 */
	@Test
	public void testAreOverlapping() {
		for (List<Parameter> l : allLists) {
			Assert.assertEquals(ParamListUtils.areOverlapping(l, l, p),
					lEmpty != l);
		}
		Assert.assertTrue(ParamListUtils.areOverlapping(lCA, lAB, p));
		Assert.assertTrue(ParamListUtils.areOverlapping(lCA, lABC, p));
		Assert.assertFalse(ParamListUtils.areOverlapping(lCA, lB, p));
		Assert.assertFalse(ParamListUtils.areOverlapping(lCA, lEmpty, p));
	}

	/**
	 * 
	 */
	@Test
	public void testLooselyIncludes() {
		for (List<Parameter> l : allLists) {
			Assert.assertTrue(ParamListUtils.looselyIncludes(lABC, l, p));
			Assert.assertTrue(ParamListUtils.looselyIncludes(l, l, p));
			Assert.assertTrue(ParamListUtils.looselyIncludes(l, lEmpty, p));
		}
		Assert.assertFalse(ParamListUtils.looselyIncludes(lAB, lCA, p));
		Assert.assertFalse(ParamListUtils.looselyIncludes(lB, lAB, p));
		Assert.assertFalse(ParamListUtils.looselyIncludes(lEmpty, lABC, p));
	}

	/**
	 * 
	 */
	@Test
	public void testStrictlyIncludes() {
		for (List<Parameter> l : allLists) {
			Assert.assertEquals(ParamListUtils.strictlyIncludes(lABC, l, p),
					lABC != l && lCBA != l);
			Assert.assertFalse(ParamListUtils.strictlyIncludes(l, l, p));
			Assert.assertEquals(ParamListUtils.strictlyIncludes(l, lEmpty, p),
					lEmpty != l);
		}
		Assert.assertFalse(ParamListUtils.strictlyIncludes(lAB, lCA, p));
		Assert.assertFalse(ParamListUtils.strictlyIncludes(lB, lAB, p));
		Assert.assertFalse(ParamListUtils.strictlyIncludes(lEmpty, lABC, p));
	}
}
