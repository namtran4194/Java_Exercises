package hust.namtran.FlowControls;

public class Fibonacci {
	/*
	 * Print first 20 Fibonacci numbers and their average
	 */
	public static void main(String[] args) {
		int n = 3; // the index n for F(n), starting from n=3
		int fn; // F(n) to be computed
		int fnMinus1 = 1; // F(n-1)
		int fnMinus2 = 1; // F(n-2)
		int nMax = 20; // maximum n
		int sum = fnMinus1 + fnMinus2; // need sum to compute average
		double average;
		System.out.println("The first " + nMax + " Fibonacci numbers are:");
		System.out.print(fnMinus1 + " " + fnMinus2);
		while (n <= nMax) {	// n starts from 3
			fn = fnMinus1 + fnMinus2;
			sum += fn;
			System.out.print(" " + fn);
			++n;
			fnMinus2 = fnMinus1;
			fnMinus1 = fn;
		}
		// Compute and display the average
		average = (double) sum / nMax;
		System.out.println("\nThe average is " + average);
	}

}
