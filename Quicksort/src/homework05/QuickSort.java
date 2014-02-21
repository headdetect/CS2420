package homework05;

import java.util.Random;

public class QuickSort
{

	public static void main(String[] args)
	{
		new QuickSort().start();
	}

	private long compares = 0;
	private Random random;

	private static final int NUM_OF_TESTS = 100;

	public QuickSort()
	{
		random = new Random();
	}

	public void start()
	{

		/*System.out.println("\n[0, 20]");
		System.out.println("n\tsort(n)");

		for (int i = 1; i <= 20; i++)
		{
			for (int test = 0; test < NUM_OF_TESTS; test++)
			{
				double[] fill = createRandomIntArray(i);
				// outputArray(fill);
				quickSort(fill, 0, fill.length - 1);
				// outputArray(fill);
				// System.out.println("is sorted: " + isSorted(fill));
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

		
		System.out.println("\n[0, 2^16]");
		System.out.println("\nn\tsort(n)");
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
		}*/
		

		// Find the best cutoff //
		// I decided to pick the best of lower numbers, because insert. sort
		// gets "heavy" when it has a bunch of elements //
		
		System.out.println("\nFinding the cutoff");

		double highestMargin = Double.MIN_VALUE;
		int bestCutoff = -1;
		for (int cutoff = 3; cutoff < 30; cutoff++)
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

			if (comparesWithout > comparesWith)
			{
				// System.out.println("Tested: " + cutoff);
				double margin = comparesWithout - comparesWith;
				// System.out.println("Margin: " + margin);
				if (margin > highestMargin)
				{
					bestCutoff = cutoff;
					highestMargin = margin;
				}
			}
		}

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
	 * 
	 * From: http://www.learning2.eng.utah.edu/pluginfile.php/15637/mod_resource/ content/0/SortExperiment.java
	 * 
	 * Sorts the input array using insertion sort, then returns a reference to the input array.
	 * 
	 * @param d
	 *            an array of doubles
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

	private boolean compare(boolean output)
	{
		compares++;
		return output;
	}

	private void outputArray(double[] data)
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

	private double[] createRandomIntArray(int len)
	{
		double[] data = new double[len];
		for (int i = 0; i < len; i++)
			data[i] = random.nextInt(11);
		return data;
	}

	private double[] createRandomDoubleArray(int len)
	{
		double[] data = new double[len];
		for (int i = 0; i < len; i++)
			data[i] = random.nextDouble();
		return data;
	}

	/** Just a test method **/
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
}
