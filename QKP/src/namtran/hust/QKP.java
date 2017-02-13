package namtran.hust;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class QKP {
	public boolean[][] board;
	public boolean[][] queens;
	public boolean[][] knights;
	public boolean[][] pawns;
	public int rows, cols;
	public int numberOfSafeSquares;

	public QKP() {
		getData();
		getSafeLocation();
		System.out.println();
		System.out.println("Result: " + numberOfSafeSquares);
	}

	public void getSafeLocation() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (board[row][col]) {
					continue;
				}
				if (isEmpty(row, col)) {
					numberOfSafeSquares++;
					board[row][col] = true;
				} else if (queens[row][col]) {
					scanForQueens(row, col);
				} else if (knights[row][col]) {
					scanForKnights(row, col);
				} else if (pawns[row][col]) {

				}
			}
		}
	}

	public void scanForQueens(int x, int y) {
		boolean isPawns = false;
		for (int row = -rows + 1; row < rows; row++) {
			if (row + x < 0 || row + x >= rows) {
				continue;
			}
			if (isPawns) {
				numberOfSafeSquares++;
				board[row + x][y] = true;
			} else if (isEmpty(row + x, y)) {
				numberOfSafeSquares--;
				board[row + x][y] = true;
			} else if (knights[row + x][y] || pawns[row + x][y]) {
				isPawns = true;
			}
		}
		isPawns = false;
		for (int col = -cols + 1; col < cols; col++) {
			if (col + y < 0 || col + y >= cols) {
				continue;
			}
			if (isPawns) {
				numberOfSafeSquares++;
				board[x][col + y] = true;
			} else if (isEmpty(x, col + y)) {
				numberOfSafeSquares--;
				board[x][col + y] = true;
			} else if (knights[x][col + y] || pawns[x][col + y]) {
				isPawns = true;
			}
		}
		isPawns = false;
		for (int row = -rows + 1; row < rows; row++) {
			for (int col = -cols + 1; col < cols; col++) {
				if (row + x < 0 || col + y < 0 || row + x >= rows || col + y >= cols) {
					continue;
				}
				if (isPawns) {
					numberOfSafeSquares++;
					board[row + x][col + y] = true;
				} else if (row + x == col + y && isEmpty(row + x, col + y)) {
					numberOfSafeSquares--;
					board[row + x][col + y] = true;
				} else if (knights[row + x][col + y] || pawns[row + x][col + y]) {
					isPawns = true;
				}
			}
		}
	}

	public void scanForKnights(int x, int y) {
		for (int row = -2; row <= 2; row++) {
			for (int col = -2; col <= 2; col++) {
				if (row + x < 0 || col + y < 0 || row + x >= rows || col + y >= cols) {
					continue;
				} else if (row == col) {
					continue;
				} else if (row == 0 || col == 0) {
					continue;
				} else if (row == -col || -row == col) {
					continue;
				}
				if (isEmpty(row + x, col + y)) {
					numberOfSafeSquares--;
					board[row + x][col + y] = true;
				}
			}
		}
	}

	public void scanForPawns(int x, int y) {

	}

	public boolean isEmpty(int x, int y) {
		if (!queens[x][y] && !knights[x][y] && !pawns[x][y]) {
			return true;
		} else {
			return false;
		}
	}

	public void resetLocation() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				board[row][col] = false;
				queens[row][col] = false;
				knights[row][col] = false;
				pawns[row][col] = false;
			}
		}
	}

	public void getData() {
		StringTokenizer tokenizer;
		String line;
		int i, j, numbers, nline = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader("D:\\input.txt"))) {
			while ((line = reader.readLine()) != null) {
				nline++;
				tokenizer = new StringTokenizer(line, " ");
				if (nline == 1) {
					rows = Integer.parseInt(tokenizer.nextToken());
					cols = Integer.parseInt(tokenizer.nextToken());
					board = new boolean[rows][cols];
					queens = new boolean[rows][cols];
					knights = new boolean[rows][cols];
					pawns = new boolean[rows][cols];
					resetLocation();
					System.out.println("Board: " + rows + "x" + cols);
				} else {
					numbers = Integer.parseInt(tokenizer.nextToken());
					while (numbers > 0) {
						i = Integer.parseInt(tokenizer.nextToken()) - 1;
						j = Integer.parseInt(tokenizer.nextToken()) - 1;
						numbers--;
						if (nline == 2) {
							queens[i][j] = true;
							System.out.print(i + "x" + j + " ");
						} else if (nline == 3) {
							System.out.println();
							knights[i][j] = true;
							System.out.print(i + "x" + j + " ");
						} else if (nline == 4) {
							System.out.println();
							pawns[i][j] = true;
							System.out.print(i + "x" + j + " ");
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		new QKP();
	}
}