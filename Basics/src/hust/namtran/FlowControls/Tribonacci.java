package hust.namtran.FlowControls;

public class Tribonacci {
	/*
	 * Print first 20 Tribonacci numbers and their average
	 */
	public static void main(String[] args) {
		int n = 4; // the index n for F(n), starting from n=4
		int nMax = 20; // maximum n
		int fn; // F(n) to be computed
		int fnMinus1 = 2; // F(n-1)
		int fnMinus2 = 1; // F(n-2)
		int fnMinus3 = 1; // F(n-3)
		int sum = fnMinus1 + fnMinus2 + fnMinus3;
		double average;
		System.out.println("The first " + nMax + " Tribonacci numbers are:");
		System.out.print(fnMinus3 + " " + fnMinus2 + " " + fnMinus1);
		while (n <= nMax) { // n starts from 4
			fn = fnMinus1 + fnMinus2 + fnMinus3;
			sum += fn;
			System.out.print(" " + fn);
			++n;
			fnMinus3 = fnMinus2;
			fnMinus2 = fnMinus1;
			fnMinus1 = fn;
		}
		// Compute and display the average
		average = (double) sum / nMax;
		System.out.println("\nThe average is " + average);
	}

}
