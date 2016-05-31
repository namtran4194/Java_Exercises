package hust.namtran.FlowControls;

public class ExtractDigits {
	/*
	 * extract each digit from an int, in the reverse order
	 */
	public static void main(String[] args) {
		int n = 15423; // the number want to extract
		System.out.println("The number to extract is " + n);
		while (n > 0) {
			int digit = n % 10;
			System.out.print(digit + " ");
			n = n / 10;
		}
	}

}
