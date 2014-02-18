/*
	@Author Brayden.Lopez
	@Year	2014
*/

package homework01;

public class SelectionSortExperiment {

	// ===========================================================
	// Constants
	// ===========================================================
	
	/** How many times should we collect counts from sorts */
	private static final int TEST_COUNT 	= 20;
	
	/** How many tests will be ran */
	private static final int SAMPLE_SIZE 	= 15;

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	/** First executed function **/
	public static void main(String[] args) {
		System.out.println("n\tcount(n)");
		System.out.println("------------------");
		
		for (int i = 0; i < SAMPLE_SIZE; i++) {
			int arrSize = i * 100 + 500; /* Somewhat random */
			int total = 0;
			
			for (int j = 0; j < TEST_COUNT; j++) {
				double[] toSort = makeRandomArray(arrSize);
				int count = selectionSortExperiment(toSort);
				total += count;
			}
			
			/* Format:
			 * Sample Size		Average Calculations
			 * 500				2424
			 * 521				2498
			 */
			System.out.println(arrSize + "\t" + (total / TEST_COUNT));
		}

	}
	
	// ===========================================================
	// Getters & Setters
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * Returns an array of doubles filled with random values in the range
	 * [0...1).
	 * 
	 * The caller specifies a non-negative n to indicate the size of the array.
	 * 
	 * This function does not print anything to the console.
	 * 
	 * @param n
	 *            The desired size of an array.
	 * @return An array of n doubles filled with random values
	 */
	static public double[] makeRandomArray(int n) {
		double[] arr = new double[n];
		for (int i = 0; i < n; i++) {
			arr[i] = Math.random();
		}
		return arr;
	}

	/**
	 * Returns a count of how many times selection sort finds a 'better' value
	 * during the sorting process. The input array is sorted as a side effect.
	 * 
	 * This function does not print anything to the console.
	 * 
	 * @param data
	 *            An array of double values
	 * @return A count of how many times 'better' values were found
	 */
	static public int selectionSortExperiment(double[] data) {
		int count = 0;
		int len = data.length;
		for (int i = 0; i <= len - 2; i++) {
			int best = i;
			for (int j = i; j <= len - 2; j++) {
				if (data[j] < data[best]) {
					best = j;
					count++;
				}
			}

			// Really wanted to do a XOR swap, but it just had to be doubles //
			double temp = data[i];
			data[i] = data[best];
			data[best] = temp;
		}

		return count;
	}
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}