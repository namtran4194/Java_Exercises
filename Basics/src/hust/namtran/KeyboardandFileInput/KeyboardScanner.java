package hust.namtran.KeyboardandFileInput;

import java.util.Scanner;

public class KeyboardScanner {
	static Scanner in;

	public static void main(String[] args) {
		int num1;
		double num2;
		String name;
		double sum;

		in = new Scanner(System.in);
		System.out.print("Enter an integer: ");
		num1 = in.nextInt(); // use nextInt() to read int
		System.out.print("Enter a floating point number: ");
		num2 = in.nextDouble(); // use nextDouble() to read double

		in = new Scanner(System.in);
		System.out.print("Enter your name: ");
		name = in.nextLine(); // use next() to read String

		// Display
		sum = (num1 + num2) / 2;
		System.out.println("Hi! " + name + ", the sum of " + num1 + " and " + num2 + " is " + sum);
		// Close the input stream
		in.close();
	}

}
