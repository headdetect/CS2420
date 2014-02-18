/*
 * Here is a starting point for your Matrix tester. You will have to fill in the rest of "main" with more code to
 * sufficiently test your Matrix class. We will be using our own MatrixTester for grading.
 */
package homework02;

import java.util.Random;

/**
 * Application for testing the Matrix class.
 * 
 * @author Brayden Lopez
 * @version Jan 16, 2014
 */
public class MatrixTester
{
	public static void main(String[] args)
	{
		System.out.println("Brayden Lopez");
		System.out.println("Assignment #2");

		System.out.println("\n=== Valid Multiply ====");
		testValidMult();
		System.out.println("\n=======================");

		System.out.println("\n\n=== Borked Multiply ===");
		testBorkedMult();
		System.out.println("\n=======================");

		System.out.println("\n\n====== Valid Add ======");
		testValidAdd();
		System.out.println("\n=======================");

		System.out.println("\n\n===== Borked Add ======");
		testBorkedAdd();
		System.out.println("\n=======================");

		//System.out.println("\n\n===== Test Adds =====");
		//testDifferentAdds();
		//System.out.println("\n=======================");
	}

	private static void testValidMult()
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

		// This statement exercises your toString method.

		System.out.println("Test #1:");

		// This statement exercises your toString method.

		System.out.println("  Computed result for M1 * M2:\n" + computedMultiply);

		// This statement exercises your .equals method, and makes sure that
		// your
		// computed result is the same as the known correct result.

		if (!referenceMultiply.equals(computedMultiply))
			System.out.println("  Should be:\n" + referenceMultiply);
		else
			System.out.println("  Correct");
	}

	private static void testBorkedMult()
	{

		Matrix M1 = new Matrix(new int[][] { { 1, 2, 3 }, { 2, 5, 6 } });

		Matrix M2 = new Matrix(new int[][] { { 4, 5 }, { 3, 2 } });

		Matrix computedMultiply = M1.multiply(M2);

		System.out.println("Test #2:");

		System.out.println("  Invalid rules for matrix multiplication:\n");

		// This statement exercises your .equals method, and makes sure that
		// your
		// computed result is the same as the known correct result.

		if (computedMultiply != null)
			System.out.println("  FAILED");
		else
			System.out.println("  PASSED");
	}

	private static void testValidAdd()
	{
		Matrix M3 = new Matrix(new int[][] { { 1, 2 }, { 3, 2 } });
		Matrix M4 = new Matrix(new int[][] { { 4, 5 }, { 1, 2 } });
		Matrix referenceAdd = new Matrix(new int[][] { { 5, 7 }, { 4, 4 } });

		Matrix computedAdd = M3.add(M4);

		System.out.println("Test #2:");
		System.out.println("  Computed result for M1 + M2:\n" + computedAdd);

		if (!referenceAdd.equals(computedAdd))
			System.out.println("  Should be:\n" + referenceAdd);
		else
			System.out.println("  Correct");
	}

	private static void testBorkedAdd()
	{
		Matrix M1 = new Matrix(new int[][] { { 1, 2, 3 }, { 2, 5, 6 } });

		Matrix M2 = new Matrix(new int[][] { { 4, 5 }, { 3, 2 } });

		Matrix computedAddition = M1.add(M2);

		System.out.println("Test #2:");

		System.out.println("  Invalid rules for matrix multiplication:\n");

		if (computedAddition != null)
			System.out.println("  FAILED");
		else
			System.out.println("  PASSED");
	}

	private static void testDifferentAdds()
	{
		System.out.println("Test #5:");
		System.out.println("  Speed between different adds:\n");

		int[][] m1 = new int[1000][1000];
		int[][] m2 = new int[1000][1000];

		Random rnd = new Random();

		for (int i = 0; i < 1000; i++)
		{
			for (int j = 0; j < 1000; j++)
			{
				m1[i][j] = rnd.nextInt();
				m2[i][j] = rnd.nextInt();
			}
		}

		Matrix M1 = new Matrix(m1);
		Matrix M2 = new Matrix(m2);

		for (int test = 0; test < 10; test++)
		{
			long current = System.currentTimeMillis();
			for (int i = 0; i < 20 * test; i++)
			{
				M1.add(M2);
			}
			long delta = System.currentTimeMillis() - current;

			System.out.println("It took method 1 " + delta + " milliseconds to run " + (20 * test) + " times");
		}

		System.out.println();
		
		for (int test = 0; test < 10; test++)
		{
			long current = System.currentTimeMillis();
			for (int i = 0; i < 20 * test; i++)
			{
				M1.addMethod2(M2);
			}
			long delta = System.currentTimeMillis() - current;

			System.out.println("It took method 2 " + delta + " milliseconds to run " + (20 * test) + " times");
		}

	}
}