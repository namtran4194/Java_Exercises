package hust.namtran.Algorithms;

import java.util.Scanner;

public class PrimeList {

	public static void main(String[] args) {
		int upper;
		int i = 0;
		int[] primeList;

		System.out.print("Please enter the upper bound: ");
		Scanner in = new Scanner(System.in);
		upper = in.nextInt();
		primeList = new int[upper];
		for (int number = 2; number <= upper; number++)
			if (isPrime(number)) {
				primeList[i] = number;
				i++;
			}

		for (int j = 0; j < i; j++) {
			System.out.println(primeList[j]);
		}
		double percentage = (double) i / upper * 100;
		System.out.printf("[%d primes found (%.2f%%)]", i, percentage);
		in.close();
	}

	/*
	 * Kiểm tra số nguyên tố
	 */
	protected static boolean isPrime(int n) {
		if (n == 2) {
			return true;
		}
		//check if n is a multiple of 2
		if (n % 2 == 0 || n == 1)
			return false;
		//if not, then just check the odds
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

}
