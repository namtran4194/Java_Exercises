package graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * The Sudoku game.
 * To solve the number puzzle, each row, each column, and each of the
 * nine 3×3 sub-grids shall contain all of the digits from 1 to 9
 */
public class Sudoku extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final int GRID_SIZE = 9; // Size of the board
	public static final int SUBGRID_SIZE = 3; // Size of the sub-grid

	// Name-constants for UI control (sizes, colors and fonts)
	public static final int CELL_SIZE = 60; // Cell width/height in pixels
	public static final int CANVAS_WIDTH = CELL_SIZE * GRID_SIZE; // Board width/height in pixels
	public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE; // Board width/height in pixels
	public static final Color OPEN_CELL_BGCOLOR = Color.YELLOW;
	public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0); // RGB
	public static final Color OPEN_CELL_TEXT_NO = Color.RED;
	public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
	public static final Color CLOSED_CELL_TEXT = Color.BLACK;
	public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

	// The game board composes of 9x9 JTextFields,
	// each containing String "1" to "9", or empty String
	private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];

	// Puzzle to be solved and the mask (which can be used to control the difficulty level).
	// Hardcoded here. Extra credit for automatic puzzle generation with various difficulty levels.
	private int[][] puzzle = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 }, { 6, 7, 2, 1, 9, 5, 3, 4, 8 },
			{ 1, 9, 8, 3, 4, 2, 5, 6, 7 }, { 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
			{ 7, 1, 3, 9, 2, 4, 8, 5, 6 }, { 9, 6, 1, 5, 3, 7, 2, 8, 4 }, { 2, 8, 7, 4, 1, 9, 6, 3, 5 },
			{ 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
	// For testing, open only 2 cells.
	private static boolean[][] masks = { { true, false, false, false, false, true, false, false, false },
			{ false, false, false, false, false, false, false, false, true },
			{ false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, true, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, true, false },
			{ false, false, false, false, false, false, false, false, false },
			{ false, false, false, false, false, false, false, false, false } };

	/**
	  * Constructor to setup the game and the UI Components
	  */
	public Sudoku() {
		Container container = getContentPane();
		container.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
		getPuzzle(container);

		// Set the size of the content-pane and pack all the components under this container.
		container.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		addMenu();
		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage(getToolkit().getImage(getClass().getClassLoader().getResource("sudoku_icon.png")));
		setTitle("Sudoku");
		setVisible(true);
	}

	public void getPuzzle(Container container) {
		InputListener listener = new InputListener();
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				tfCells[row][col] = new JTextField(); // Allocate element of array
				container.add(tfCells[row][col]);
				if (masks[row][col]) {
					tfCells[row][col].setText("");
					tfCells[row][col].setEditable(true);
					tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
					tfCells[row][col].setDocument(new DigitsDocument());
					tfCells[row][col].addKeyListener(listener);
				} else {
					tfCells[row][col].setText(puzzle[row][col] + "");
					tfCells[row][col].setEditable(false);
					tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
					tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
				}
				// Beautify all the cells
				tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
				tfCells[row][col].setFont(FONT_NUMBERS);
			}
		}
	}

	public void addMenu() {
		JMenuBar menuBar;
		JMenu mFile, mOptions, mHelp;
		JMenuItem newGame, resetGame, exit;
		JMenuItem about;

		menuBar = new JMenuBar();

		mFile = new JMenu("File");
		mOptions = new JMenu("Options");
		mHelp = new JMenu("Help");

		newGame = new JMenuItem("New Game");
		resetGame = new JMenuItem("Reset Game");
		exit = new JMenuItem("Exit");
		about = new JMenuItem("About");

		mFile.add(newGame);
		mFile.add(resetGame);
		mFile.add(exit);
		mHelp.add(about);

		menuBar.add(mFile);
		menuBar.add(mOptions);
		menuBar.add(mHelp);
		setJMenuBar(menuBar);
		// add event listener
		newGame.addActionListener(new MenuListener());
		resetGame.addActionListener(new MenuListener());
		exit.addActionListener(new MenuListener());
		about.addActionListener(new MenuListener());
	}

	public void shuffle() {
		Random random = new Random();
		int randomRow, randomCol;
		boolean temp;
		for (int row = 0; row < GRID_SIZE; row++) {
			for (int col = 0; col < GRID_SIZE; col++) {
				randomRow = random.nextInt(GRID_SIZE);
				randomCol = random.nextInt(GRID_SIZE);
				temp = masks[row][col];
				masks[row][col] = masks[randomRow][randomCol];
				masks[randomRow][randomCol] = temp;
			}
		}
	}

	public void about() {
		String msg = "Chào các bạn, cảm ơn đã dùng thử ứng dụng của mình.\nỨng dụng này mình làm trong quá trình rảnh rỗi, để luyện tập cũng như nâng cao trình độ code của mình.\nNếu có góp ý hay thắc mắc gì liên hệ với mình qua email hoặc fb nhé.\nEmail: namtran4194@gmail.com\nFacebook: fb.com/namtran4194";
		JOptionPane.showMessageDialog(this, msg, "About Sudoku", JOptionPane.INFORMATION_MESSAGE);
	}

	private class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch (command) {
			case "New Game":
				Sudoku.this.dispose();
				shuffle();
				new Sudoku();
				break;
			case "Reset Game":
				Sudoku.this.dispose();
				new Sudoku();
				break;
			case "Exit":
				System.exit(1);
				break;
			case "About":
				about();
			default:
				break;
			}
		}

	}

	private class InputListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			int rowSelected = -1;
			int colSelected = -1;

			// Get the source object that fired the event
			JTextField source = (JTextField) e.getSource();
			// Scan JTextFileds for all rows and columns, and match with the source object
			boolean found = false;
			for (int row = 0; row < GRID_SIZE && !found; row++) {
				for (int col = 0; col < GRID_SIZE && !found; col++) {
					if (tfCells[row][col] == source) {
						rowSelected = row;
						colSelected = col;
						found = true;
					}
				}
			}

			char c = e.getKeyChar();
			int inputNumder = c - '0';
			if (inputNumder == puzzle[rowSelected][colSelected]) {
				tfCells[rowSelected][colSelected].setText(String.valueOf(c));
				tfCells[rowSelected][colSelected].setBackground(Color.GREEN);
				tfCells[rowSelected][colSelected].setEditable(false);
				tfCells[rowSelected][colSelected].transferFocus();
				masks[rowSelected][colSelected] = false;
			} else {
				tfCells[rowSelected][colSelected].setBackground(OPEN_CELL_TEXT_NO);
				tfCells[rowSelected][colSelected].setEditable(true);
			}

			boolean solved = true;
			for (int row = 0; row < GRID_SIZE; row++) {
				for (int col = 0; col < GRID_SIZE; col++) {
					if (masks[row][col]) {
						solved = false;
						break;
					}
				}
			}
			if (solved) {
				JOptionPane.showMessageDialog(Sudoku.this, "Congratulation!", "Notification", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
						
		}
	}

	// Run game
	public static void main(String[] args) {
		new Sudoku();
	}

}
