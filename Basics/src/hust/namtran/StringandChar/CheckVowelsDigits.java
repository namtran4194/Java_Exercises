package hust.namtran.StringandChar;

import java.util.Scanner;

public class CheckVowelsDigits {

	public static void main(String[] args) {
		String vowels = "aeiouAEIOU";
		int numberOfVowel = 0;
		int numberOfDigit = 0;
		double percentageOfVowel;
		double percentageOfDigit;
		String input;

		Scanner in = new Scanner(System.in);
		System.out.print("Enter a string: ");
		input = in.next();
		for (int i = 0; i < input.length(); i++) {
			if (Character.isDigit(input.charAt(i))) {
				numberOfDigit++;
			}
			if (vowels.contains(String.valueOf(input.charAt(i)))) {
				numberOfVowel++;
			}
		}
		percentageOfVowel = (double) numberOfVowel / input.length() * 100;
		percentageOfDigit = (double) numberOfDigit / input.length() * 100;
		System.out.printf("Number of vowels: %d (%.00f%%)%n", numberOfVowel, percentageOfVowel);
		System.out.printf("Number of digits: %d (%.00f%%)", numberOfDigit, percentageOfDigit);
		in.close();
	}

}
