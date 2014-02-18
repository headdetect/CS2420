package homework02;

import static org.junit.Assert.*;


import org.junit.Test;

public class MatrixTest
{
	@Test
	public void testValidMult()
	{
		// Note - when you create a new int[][], you can supply initial values,
		// see
		// below for the syntax.

		// These statements exercise the second Matrix constructor.

		Matrix M1 = new Matrix(new int[][] { { 1, 2, 3 }, { 2, 5, 6 } });

		Matrix M2 = new Matrix(new int[][] { { 4, 5 }, { 3, 2 }, { 1, 1 } });

		// This is the known correct result of multiplying M1 by M2.

		Matrix referenceMultiply = new Matrix(new int[][] { { 13, 12 }, { 29, 26 } });

		/*
		 * Note that none of the tests below will be correct until you have
		 * implemented all methods. This is just one example of a test, you must
		 * write more tests and cover all cases.
		 */

		// Get the matrix computed by your times method.
		// This statement exercises your multiply method.

		Matrix computedMultiply = M1.multiply(M2);

		// This statement exercises your .equals method, and makes sure that
		// your
		// computed result is the same as the known correct result.

		assertEquals(referenceMultiply, computedMultiply);
	}

	@Test
	public void testBorkedMult()
	{

		Matrix M1 = new Matrix(new int[][] { { 1, 2, 3 }, { 2, 5, 6 } });

		Matrix M2 = new Matrix(new int[][] { { 4, 5 }, { 3, 2 } });

		Matrix computedMultiply = M1.multiply(M2);


		// This statement exercises your .equals method, and makes sure that
		// your
		// computed result is the same as the known correct result.

		assertNull(computedMultiply);
		
	
	}

	@Test
	public void testValidAdd()
	{
		Matrix M3 = new Matrix(new int[][] { { 1, 2 }, { 3, 2 } });
		Matrix M4 = new Matrix(new int[][] { { 4, 5 }, { 1, 2 } });
		Matrix referenceAdd = new Matrix(new int[][] { { 5, 7 }, { 4, 4 } });

		Matrix computedAdd = M3.add(M4);

		assertEquals(referenceAdd, computedAdd);
	}

	@Test
	public void testBorkedAdd()
	{
		Matrix M1 = new Matrix(new int[][] { { 1, 2, 3 }, { 2, 5, 6 } });

		Matrix M2 = new Matrix(new int[][] { { 4, 5 }, { 3, 2 } });

		Matrix computedAddition = M1.add(M2);

		assertNull(computedAddition);
	}

}
