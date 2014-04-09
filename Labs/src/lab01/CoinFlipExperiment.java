package lab01;

public class CoinFlipExperiment {

	private static int[] counts = new int[201];

	public static void main(String[] args) {

		System.out.println("Likelihood of win/loss amount after 100 flips:");

		System.out.print("Amount");
		System.out.print("\t"); // A tab character
		System.out.print("Probability");
		System.out.println();
		double attempts = 10000.0;
		
		for (int i = 0; i <= attempts; i++) {
			int amount = coinFlipExperiment();
			 counts[amount + 100] = counts[amount + 100] + 1;
		}

		for (int i = 0; i <= 200; i++) {
			System.out.print(i - 100);
			System.out.print("\t");
			System.out.print(counts[i] / attempts);
			System.out.println();
		}
	}

	/**
	 * Returns the amount of money you'd win or lose by flipping an unbalanced
	 * coin 100 times.
	 * 
	 * @return the amount of money won/lost
	 */
	public static int coinFlipExperiment() {
		int winnings = 0;
		for (int i = 0; i < 100; i++) {
			double flip = Math.random();
			winnings += flip < 0.505 ? 1 : -1;
		}

		return winnings;
	}

}
