package lab04;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyHelperFunctionsTest {

	private int[] arr1, arr2, arr3, arr4, arr5;

	@Before
	public void setUp() throws Exception {
		arr1 = new int[0];
		arr2 = new int[] { 3, 3, 3 };
		arr3 = new int[] { 52, 4, -8, 0, -17 };
		arr4 = new int[] { -3, 9, 100, 45, 99, 105 };
		arr5 = new int[] { 3, -9, -100, -45, -99, -105 };
	}

	@After
	public void tearDown() throws Exception {
		arr1 = arr2 = arr3 = arr4 = null;
	}

	@Test
	public void testFindSmallestDiff1() {
		// Call the method.

		int result;
		result = MyHelperFunctions.findSmallestDiff(arr1);

		// Test the result.

		assertEquals(result, -1);

		// If the assert fails, the test fails.
		// If we get this far, the test passes.
	}

	/**
	 * Test method for {@link lab04.MyHelperFunctions#findSmallestDiff(int[])}.
	 */
	@Test
	public void testFindSmallestDiff2() {
		// Call the method.

		int result;
		result = MyHelperFunctions.findSmallestDiff(arr2);

		// Test the result.

		assertEquals(result, 0);

		// If the assert fails, the test fails.
		// If we get this far, the test passes.
	}

	/**
	 * Test method for {@link lab04.MyHelperFunctions#findSmallestDiff(int[])}.
	 */
	@Test
	public void testFindSmallestDiff3() {
		// Call the method.

		int result;
		result = MyHelperFunctions.findSmallestDiff(arr3);

		// Test the result.

		assertEquals(result, 4);

		// If the assert fails, the test fails.
		// If we get this far, the test passes.
	}
	
	/**
	 * Test method for {@link lab04.MyHelperFunctions#findSmallestDiff(int[])}.
	 */
	@Test
	public void testFindSmallestDiff4() {
		// Call the method.

		int result;
		result = MyHelperFunctions.findSmallestDiff(arr4);

		// Test the result.

		assertEquals(result, 1);

		// If the assert fails, the test fails.
		// If we get this far, the test passes.
	}
	
	/**
	 * Test method for {@link lab04.MyHelperFunctions#findSmallestDiff(int[])}.
	 */
	@Test
	public void testFindSmallestDiff5() {
		// Call the method.

		int result;
		result = MyHelperFunctions.findSmallestDiff(arr5);

		// Test the result.

		assertEquals(result, 1);

		// If the assert fails, the test fails.
		// If we get this far, the test passes.
	}
}
