package homework05;

import java.util.Random;

/**
 * Several tests to run for quick sort.
 * 
 * @author Brayden Lopez.
 * @version v1b, Feb 21, 2014.
 */
public class QuickSort
{

	// ===========================================================
	// Constants
	// ===========================================================

	/** The size of the sample. Number of tests to run for each sample set. */
	private static final int NUM_OF_TESTS = 100;

	// ===========================================================
	// Fields
	// ===========================================================

	/** The current number of comparisons. */
	private long compares = 0;

	/** The "random" generator. */
	private Random random;

	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * The main method.
	 * Is basically the constructor for the whole program
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
		new QuickSort().start();
	}

	/**
	 * Instantiates a new quick sort.
	 */
	public QuickSort()
	{
		random = new Random();
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Starts the tests.
	 */
	public void start()
	{
		// You can change the size of the sample by changing NUM_OF_TESTS //
		// Higher = more accurate results //
		// Lower = faster results //
		
		test0v20QuickSort();
		test0v65536QuickSort();
		
		test0v20QuickSortWithInts();
		test0v65536QuickSortWithInts();

		test0v20QuickSortWithIntsAndGoodPartition();
		test0v65536QuickSortWithIntsAndGoodPartition();
		
		testForBestCutoff();
	}

	// ----------------------------------------
	// Tests
	// ----------------------------------------

	// --- Question 1 --- //
	/**
	 * A test for question 1. <br />
	 * <b>Properties</b>
	 * - Sorts for size [0, 20] increases by 1. <br />
	 * - Uses array of doubles <br />
	 * - Uses &lt;= in the quick sort partition code (the bad partition) <br />
	 */
	private void test0v20QuickSort()
	{
		System.out.println("\n[0, 20]");
		System.out.println("n\tsort(n)");

		for (int i = 1; i <= 20; i++)
		{
			for (int test = 0; test < NUM_OF_TESTS; test++)
			{
				double[] fill = createRandomDoubleArray(i);
				quickSortWithBadPartition(fill, 0, fill.length - 1);
				if (!isSorted(fill))
				{
					System.out.println("NOT SORTED");
					return;
				}

			}

			System.out.println(i + "\t" + ((double) compares / NUM_OF_TESTS));
			compares = 0;
		}
	}

	/**
	 * A test for question 1. <br />
	 * <b>Properties</b>
	 * - Sorts for size [0, 2^16] increases by 2^x. <br />
	 * - Uses array of doubles <br />
	 * - Uses &lt;= in the quick sort partition code (the bad partition) <br />
	 */
	private void test0v65536QuickSort()
	{
		System.out.println("\n[0, 2^16]");
		System.out.println("n\tsort(n)");

		for (int i = 1; i <= 65536; i *= 2)
		{
			for (int test = 0; test < NUM_OF_TESTS; test++)
			{
				double[] fill = createRandomDoubleArray(i);
				quickSortWithBadPartition(fill, 0, fill.length - 1);
				if (!isSorted(fill))
				{
					System.out.println("NON SORTED");
					return;
				}

			}

			// Output the average. Converting compares to double so the average
			// will be a double //
			System.out.println(i + "\t" + ((double) compares / NUM_OF_TESTS));
			compares = 0;
		}
	}
	// --- End Question 1 --- //

	// --- Question 2 --- //
	/**
	 * A test for question 2. <br />
	 * <b>Properties</b>
	 * - Sorts for size [0, 20] increases by 1. <br />
	 * - Uses array of integers <br />
	 * - Uses &lt;= in the quick sort partition code (the bad partition) <br />
	 */
	private void test0v20QuickSortWithInts()
	{
		System.out.println("\n[0, 20]");
		System.out.println("n\tsort(n)");

		for (int i = 1; i <= 20; i++)
		{
			for (int test = 0; test < NUM_OF_TESTS; test++)
			{
				double[] fill = createRandomIntArray(i);
				quickSortWithBadPartition(fill, 0, fill.length - 1);
				if (!isSorted(fill))
				{
					System.out.println("NOT SORTED");
					return;
				}

			}

			System.out.println(i + "\t" + ((double) compares / NUM_OF_TESTS));
			compares = 0;
		}
	}

	/**
	 * A test for question 2. <br />
	 * <b>Properties</b>
	 * - Sorts for size [0, 2^16] increases by 2^x. <br />
	 * - Uses array of integers <br />
	 * - Uses &lt;= in the quick sort partition code (the bad partition) <br />
	 */
	private void test0v65536QuickSortWithInts()
	{
		System.out.println("\n[0, 2^16]");
		System.out.println("n\tsort(n)");

		for (int i = 1; i <= 65536; i *= 2)
		{
			for (int test = 0; test < NUM_OF_TESTS; test++)
			{
				double[] fill = createRandomIntArray(i);
				quickSortWithBadPartition(fill, 0, fill.length - 1);
				if (!isSorted(fill))
				{
					System.out.println("NON SORTED");
					return;
				}

			}

			// Output the average. Converting compares to double so the average
			// will be a double //
			System.out.println(i + "\t" + ((double) compares / NUM_OF_TESTS));
			compares = 0;
		}
	}
	// --- End Question 2 --- //

	
	// --- Question 3 --- //
	/**
	 * A test for question 3. <br />
	 * <b>Properties</b>
	 * - Sorts for size [0, 20] increases by 1. <br />
	 * - Uses array of integers <br />
	 * - Uses &lt; in the quick sort partition code (the good partition) <br />
	 */
	private void test0v20QuickSortWithIntsAndGoodPartition()
	{
		System.out.println("\n[0, 20]");
		System.out.println("n\tsort(n)");

		for (int i = 1; i <= 20; i++)
		{
			for (int test = 0; test < NUM_OF_TESTS; test++)
			{
				double[] fill = createRandomIntArray(i);
				quickSort(fill, 0, fill.length - 1);
				if (!isSorted(fill))
				{
					System.out.println("NOT SORTED");
					return;
				}

			}

			System.out.println(i + "\t" + ((double) compares / NUM_OF_TESTS));
			compares = 0;
		}
	}

	/**
	 * A test for question 3. <br />
	 * <b>Properties</b>
	 * - Sorts for size [0, 2^16] increases by 2^x. <br />
	 * - Uses array of integers <br />
	 * - Uses &lt; in the quick sort partition code (the good partition) <br />
	 */
	private void test0v65536QuickSortWithIntsAndGoodPartition()
	{
		System.out.println("\n[0, 2^16]");
		System.out.println("n\tsort(n)");

		for (int i = 1; i <= 65536; i *= 2)
		{
			for (int test = 0; test < NUM_OF_TESTS; test++)
			{
				double[] fill = createRandomIntArray(i);
				quickSort(fill, 0, fill.length - 1);
				if (!isSorted(fill))
				{
					System.out.println("NON SORTED");
					return;
				}

			}

			// Output the average. Converting compares to double so the average
			// will be a double //
			System.out.println(i + "\t" + ((double) compares / NUM_OF_TESTS));
			compares = 0;
		}
	}
	// --- End Question 3 --- //

	// --- Question 5 --- //
	/**
	 * A test for question 5. <br />
	 * Objective of the test is to find the best cutoff.
	 * Then test it against a sort using pure quick sort, then test it against a sort using a hybrid sort of quick/insertion sort.
	 * The cutoff is found by testing the number of comparisons with and without the cutoff. The one with the highest margin is chosen.
	 */
	private void testForBestCutoff()
	{
		System.out.println("\nFinding the cutoff");
		System.out.println("cutoff\tqsort\thybrid");
		double highestMargin = Double.MIN_VALUE;
		int bestCutoff = -1;
		for (int cutoff = 3; cutoff < 20; cutoff++)
		{
			compares = 0;
			double comparesWith = 0;
			double comparesWithout = 0;

			// Without //

			double[] fill = createRandomDoubleArray(1000000);
			quickSort(fill, 0, fill.length - 1);

			comparesWithout = compares;
			compares = 0;

			// With //
			fill = createRandomDoubleArray(1000000);
			quickSortAndInsert(fill, 0, fill.length - 1, cutoff);

			comparesWith = compares;

			System.out.println(cutoff + "\t" + comparesWithout + "\t" + comparesWith);

			if (comparesWithout > comparesWith)
			{
				double margin = comparesWithout - comparesWith;
				if (margin > highestMargin)
				{
					bestCutoff = cutoff;
					highestMargin = margin;
				}
			}
		}
		
		compares = 0;

		System.out.println("Highest margin: " + highestMargin);
		System.out.println("Best cutoff: " + bestCutoff);

		System.out.println("\n[0, 20]");
		System.out.println("n\tqsort(n)\thybridsort(n)");

		for (int i = 1; i <= 20; i++)
		{
			long qsortCompares = 0;
			long hybridsortCompares = 0;

			for (int test = 0; test < NUM_OF_TESTS; test++)
			{
				double[] fill = createRandomIntArray(i);
				quickSort(fill, 0, fill.length - 1);
				qsortCompares = compares;
				compares = 0;

				fill = createRandomIntArray(i);
				quickSortAndInsert(fill, 0, fill.length - 1, bestCutoff);
				hybridsortCompares = compares;
				compares = 0;

			}

			System.out.println(i + "\t" + ((double) qsortCompares / NUM_OF_TESTS) + "\t" + ((double) hybridsortCompares / NUM_OF_TESTS));
			compares = 0;
		}
		
		compares = 0;

		System.out.println("\n[0, 2^16]");
		System.out.println("\nn\tsort(n)\thybridsort(n)");
		for (int i = 1; i <= 65536; i *= 2)
		{
			long qsortCompares = 0;
			long hybridsortCompares = 0;

			for (int test = 0; test < NUM_OF_TESTS; test++)
			{
				double[] fill = createRandomIntArray(i);
				quickSort(fill, 0, fill.length - 1);
				qsortCompares = compares;
				compares = 0;

				fill = createRandomIntArray(i);
				quickSortAndInsert(fill, 0, fill.length - 1, bestCutoff);
				hybridsortCompares = compares;
				compares = 0;

			}

			System.out.println(i + "\t" + ((double) qsortCompares / NUM_OF_TESTS) + "\t" + ((double) hybridsortCompares / NUM_OF_TESTS));
			compares = 0;
		}
	}
	// --- End Question 5 --- //

	// ----------------------------------------
	// Sorts
	// ----------------------------------------
	
	/**
	 * Quick sort.
	 * <b>Properties</b>
	 * <b>Properties</b>
	 *	- Good partition
	 *	- non hybrid sort
	 *
	 * @param array the array of data
	 * @param start the start position
	 * @param end the end position
	 * @param cutoff the cutoff to switch from quick sort to insertion sort
	 * @return the sorted data. (Is already sorted, you don't need to assign it)
	 */
	public double[] quickSort(double[] array, int start, int end)
	{
		int part = partition(array, start, end);

		if (end - start < 2 || start > end)
			return array;

		if (start < part - 1)
		{
			quickSort(array, start, part - 1);
		}
		if (part < end)
		{
			quickSort(array, part + 1, end);
		}

		return array;
	}

	/**
	 * Quick sort and insert sort.
	 * <b>Properties</b>
	 *	- Good partition
	 *	- hybrid sort
	 *
	 * @param array the array of data
	 * @param start the start position
	 * @param end the end position
	 * @param cutoff the cutoff to switch from quick sort to insertion sort
	 * @return the sorted data. (Is already sorted, you don't need to assign it)
	 */
	public double[] quickSortAndInsert(double[] array, int start, int end, int cutoff)
	{

		int sub = end - start;

		if (sub < 2)
		{
			return array;
		}
		else if (sub < cutoff)
		{
			return insertionSort(array, start, end);
		}
		else
		{
			int part = partition(array, start, end);

			if (start < part - 1)
			{
				return quickSortAndInsert(array, start, part - 1, cutoff);
			}
			if (part < end)
			{
				return quickSortAndInsert(array, part + 1, end, cutoff);
			}
		}

		return array;
	}

	/**
	 * Quick sort with bad partition.
	 * <b>Properties</b>
	 *	- bad partition
	 *	- non hybrid sort
	 *
	 * @param array the array of data
	 * @param start the start position
	 * @param end the end position
	 * @param cutoff the cutoff to switch from quick sort to insertion sort
	 * @return the sorted data. (Is already sorted, you don't need to assign it)
	 */
	public double[] quickSortWithBadPartition(double[] array, int start, int end)
	{
		int part = badPartition(array, start, end);

		if (end - start < 2 || start > end)
			return array;

		if (start < part - 1)
		{
			quickSort(array, start, part - 1);
		}
		if (part < end)
		{
			quickSort(array, part + 1, end);
		}

		return array;
	}
	
	/**
	 * From: http://www.learning2.eng.utah.edu/pluginfile.php/15637/mod_resource/content/0/SortExperiment.java
	 * 
	 * Sorts the input array using insertion sort, then returns a reference to the input array.
	 *
	 * @param d            an array of doubles
	 * @param start the start
	 * @param end the end
	 * @return the sorted array of doubles
	 */
	public double[] insertionSort(double[] d, int start, int end)
	{
		for (int curr_pos = start; curr_pos < end; curr_pos++)
		{
			double temp = d[curr_pos];
			int insertion_pos = curr_pos;
			while (insertion_pos > 0 && compare(temp < d[insertion_pos - 1]))
			{
				d[insertion_pos] = d[insertion_pos - 1];
				insertion_pos--;
			}
			d[insertion_pos] = temp;
		}

		return d;
	}

	// ----------------------------------------
	// Helper Methods
	// ----------------------------------------


	/**
	 * Partition.
	 * Uses &lt; when comparing elements from the right. Is the good partition
	 *
	 * @param data the data to partition
	 * @param start the start of the data partition
	 * @param end the end of the data partition
	 * @return the position
	 */
	public int partition(double[] data, int start, int end)
	{

		double pivot = data[end];
		int left = start;
		int right = end - 1;

		while (true)
		{

			while (compare(data[left] < pivot))
				left++;

			while (right > left && compare(data[right] > pivot))
				right--;

			// Break just in case we updated the index //
			if (left >= right)
				break;

			// Swapping left and right //
			double temp = data[left];
			data[left] = data[right];
			data[right] = temp;

			left++;
			right--;
		}

		// Swapping elements //
		double temp = data[left];
		data[left] = pivot;
		data[end] = temp;

		return left;
	}

	/**
	 * Partition.
	 * Uses &lt;= when comparing elements from the right. Is the bad partition
	 *
	 * @param data the data to partition
	 * @param start the start of the data partition
	 * @param end the end of the data partition
	 * @return the position
	 */
	public int badPartition(double[] data, int start, int end)
	{

		double pivot = data[end];
		int left = start;
		int right = end - 1;

		while (true)
		{

			while (compare(data[left] < pivot))
				left++;

			while (right > left && compare(data[right] >= pivot))
				right--;

			// Break just in case we updated the index //
			if (left >= right)
				break;

			// Swapping left and right //
			double temp = data[left];
			data[left] = data[right];
			data[right] = temp;

			left++;
			right--;
		}

		// Swapping elements //
		double temp = data[left];
		data[left] = pivot;
		data[end] = temp;

		return left;
	}


	/**
	 * Compare.
	 * Returns what is given, also increments "compares"
	 *
	 * @param output the output
	 * @return the output
	 */
	private boolean compare(boolean output)
	{
		compares++;
		return output;
	}

	/**
	 * Creates an array of elements from size <i>len</i>
	 * Is of type integer.
	 *
	 * @param len the len
	 * @return the double[]
	 */
	private double[] createRandomIntArray(int len)
	{
		double[] data = new double[len];
		for (int i = 0; i < len; i++)
			data[i] = random.nextInt(11);
		return data;
	}

	/**
	 * Creates an array of elements from size <i>len</i>
	 * Is of type double.
	 *
	 * @param len the len
	 * @return the double[]
	 */
	private double[] createRandomDoubleArray(int len)
	{
		double[] data = new double[len];
		for (int i = 0; i < len; i++)
			data[i] = random.nextDouble();
		return data;
	}

	// -------------------------
	// Utils not used in sorts 
	// -------------------------
	
	/**
	 * Checks if is sorted.
	 *
	 * @param a the a
	 * @return true, if is sorted
	 */
	public static boolean isSorted(double[] a)
	{
		for (int i = 0; i < a.length - 1; i++)
		{
			if (a[i] > a[i + 1])
			{
				return false;
			}
		}

		return true;
	}
	
	/**
	 * Output array to system output stream.
	 *
	 * @param data the data
	 */
	public static void outputArray(double[] data)
	{
		System.out.print("{ ");
		for (int i = 0; i < Math.min(data.length, 100); i++)
		{
			System.out.print(data[i]);
			if (i != data.length - 1)
			{
				System.out.print(",");
			}
		}
		System.out.println(" }");
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}
