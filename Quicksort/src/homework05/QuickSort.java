package homework05;

public class QuickSort {

	public static void main(String[] args) {
		new QuickSort().start();
	}

	public void start() {
		double[] fill = createRandomDoubleArray(100000);
		outputArray(fill);
		quickSort(fill, 0, fill.length - 1);
		outputArray(fill);
		System.out.println("is sorted: " + isSorted(fill));
	}

	public double[] quickSort(double[] array, int start, int end) {
		int part = partition(array, start, end);

		if (end - start <= 2)
			return array;

		if (start < part - 1) {
			quickSort(array, start, part - 1);
		}
		if (part < end) {
			quickSort(array, part, end);
		}

		return array;
	}

	public int partition(double[] data, int start, int end) {

		double pivot = data[end];
		int left = start;
		int right = end - 1;

		while (true) {

			while (data[left] < pivot)
				left++;

			while (right > left && data[right] >= pivot)
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

	private void outputArray(double[] data) {
		System.out.print("{ ");
		for (int i = 0; i < data.length; i++) {
			System.out.print(data[i]);
			if (i != data.length - 1) {
				System.out.print(",");
			}
		}
		System.out.println(" }");
	}

	private double[] createRandomDoubleArray(int len) {
		double[] data = new double[len];
		for (int i = 0; i < len; i++)
			data[i] = Math.random();
		return data;
	}

	/** Just a test method **/
	public static boolean isSorted(double[] a) {
		for (int i = 0; i < a.length - 1; i++) { 
			if (a[i] > a[i + 1]) {
				return false;
			}
		}

		return true; 
	}
}
