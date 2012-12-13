package tr.edu.gsu.sine.test;

import tr.edu.gsu.sine.ext.FlexibleMetric;
import junit.framework.TestCase;

/**
 * Provides tests for each of flexible metrics.
 * <p>
 * The purpose of this class is not to test the efficiency of each metric,
 * but to verify that metrics are effectively used for matching parameters.
 * 
 * @see tr.edu.gsu.sine.ext.FlexibleMetric
 */
public class FlexibleMetricTest extends TestCase {

	/**
	 * 
	 */
	public void testAnyMatch() {
		FlexibleMetric.setThreshold(0);
		String s[] = {"test", "tst", "test_", "_test", "te_st", "wrong"};
		for (String a : s) {
			for (String b : s) {
				for (FlexibleMetric fm : FlexibleMetric.values()) {
					assertTrue("("+a+"?"+b+")", fm.isMatching(a, b));
				}
			}
		}
	}

	/**
	 * 
	 */
	public void testEqualMatch() {
		FlexibleMetric.setThreshold(1);
		String s[] = {"test", "tst", "test_", "_test", "te_st", "wrong"};
		for (String a : s) {
			for (String b : s) {
				for (FlexibleMetric fm : FlexibleMetric.values()) {
					assertEquals("(" + a + "?" + b + ")", a.equals(b)
							|| fm.isMatching(a, b), fm.isMatching(a, b));
				}
			}
		}
	}
}
