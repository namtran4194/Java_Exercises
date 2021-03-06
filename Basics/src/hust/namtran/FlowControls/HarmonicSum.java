package hust.namtran.FlowControls;

public class HarmonicSum {
	/*
	 * Compute the sum of harmonics series from left-to-right and right-to-left.
	 */
	public static void main(String[] args) {
		int maxDenominator = 5000;
		double sumL2R = 0.0;
		double sumR2L = 0.0;
		for (int denominator = 1; denominator <= maxDenominator; denominator++) {
			sumL2R += (double) 1 / denominator;
		}
		System.out.println("The sum from left-to-right is: " + sumL2R);

		for (int denominator = maxDenominator; denominator >= 1; denominator--) {
			sumR2L += (double) 1 / denominator;
		}
		System.out.println("The sum from right-to-left is: " + sumR2L);

		// Find the difference and display
	}

}
