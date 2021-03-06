package hust.namtran.StringandChar;

import java.util.Scanner;

public class Bin2Dec {
	/*
	 * convert an input binary string into its equivalent decimal number
	 */
	public static void main(String[] args) {
		String binaryStr;
		int decimal = 0;
		int order;
		char binChar;

		System.out.print("Enter a Binary string: ");
		Scanner in = new Scanner(System.in);
		binaryStr = in.next();

		for (int i = 0; i < binaryStr.length(); i++) {
			order = binaryStr.length() - 1 - i;
			binChar = binaryStr.charAt(i);
			if (binChar == '1') {
				decimal += (int) Math.pow(2, order); // 2^oder
			} else if (binChar != '0') {
				System.out.println("error: invalid binary string \"" + binaryStr + "\"");
				System.exit(1);
			} else {
				// do nothing
			}
		}
		//display
		System.out.println("The equivalent decimal number for binary \"" + binaryStr + "\" is: " + decimal);
		in.close();
	}

}
