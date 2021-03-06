package hust.namtran.FlowControls;

public class SumAndAverage {
	/*
	 * Compute the sum and average of running numbers from a lowerbound to an
	 * upperbound using loop.
	 */
	public static void main(String[] args) {
		int sum = 0;
		double average;
		int lowerbound = 1;
		int upperbound = 100;
		for (int number = lowerbound; number <= upperbound; number++) {
			sum += number;
		}
		average = sum / (upperbound - lowerbound + 1);
		System.out.println("The sum is " + sum);
		System.out.println("The average is " + average);
	}

}
