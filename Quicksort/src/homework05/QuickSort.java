package homework05;

public class QuickSort {

	public static void main(String[] args) {
		new QuickSort().start();
	}

	public void start() {

	}

	public void quickSort(double[] array) {
		int pivotIndex = array.length / 2;
		

	}

	public double[] partition(double[] array, int start, int end) {
		double pivot = array[end];
		int left = start;
		int right = end - 1;

		while (true) {
			while (array[left] < pivot)
				left++;

			while (right > left && array[right] >= pivot)
				right--;

			if (left >= right)
				break;

			double temp = array[left];
			array[left] = array[right];
			array[right] = temp;

			left++;
			right--;
		}
		
		double temp = array[left];
		array[left] = array[end];
		array[end] = temp;

		return array;
	}

}
