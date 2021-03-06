package hust.namtran.Array;

import java.util.Scanner;

public class Hex2Bin {

	public static void main(String[] args) {
		String hexStr;
		char hexChar;
		int hexInt;
		String binary = "";
		String[] hexBits = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010",
				"1011", "1100", "1101", "1110", "1111" };

		System.out.print("Enter a Hexadecimal string: ");
		Scanner in = new Scanner(System.in);
		hexStr = in.next().toLowerCase();
		for (int i = 0; i < hexStr.length(); i++) {
			hexChar = hexStr.charAt(i);
			if (hexChar >= '0' && hexChar <= '9') {
				hexInt = hexChar - '0';
				binary += " " + hexBits[hexInt];
			} else if (hexChar >= 'a' && hexChar <= 'f') {
				hexInt = hexChar - 'a' + 10;
				binary += " " + hexBits[hexInt];
			} else {
				System.out.println("error: invalid hexadecimal string \"" + hexStr + "\"");
				System.exit(1); // quit the program
			}
		}

		// display
		System.out.println("The equivalent binary for hexadecimal \"" + hexStr + "\" is:" + binary);
		in.close();
	}

}
