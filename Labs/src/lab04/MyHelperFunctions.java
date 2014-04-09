package lab04;

public class MyHelperFunctions {
	/**
	 * This method finds the two integers in the array whose values have the
	 * smallest absolute difference. All pairs of integers are explored, and the
	 * smallest difference magnitude is returned.
	 * 
	 * If the array has less than two items, -1 is returned.
	 * 
	 * @param arr
	 *            -- input array of integers
	 * 
	 * @return The smallest difference (absolute value of subtraction) among
	 *         every pair of integers
	 */
	public static int findSmallestDiff(int[] a) {
		if (a.length < 2)
	        return -1;

	    int diff = Math.abs(a[0] - a[1]);

	    for (int i = 0; i < a.length; i++)
	        for (int j = i + 1; j < a.length; j++)
	        {
	            int tmp_diff = Math.abs(a[i] - a[j]);

	            if (tmp_diff < diff)
	                diff = tmp_diff;
	        }

	    return diff;
	}
}
