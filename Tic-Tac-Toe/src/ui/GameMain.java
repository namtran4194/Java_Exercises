package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import adapter.Board;
import adapter.GameState;
import adapter.Seed;

/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class GameMain extends JPanel {
	private static final long serialVersionUID = 1L;
	// Named-constants for the game board
	public static final int ROWS = 3; // ROWS by COLS cells
	public static final int COLS = 3;
	public static final String TITLE = "Tic Tac Toe";

	// Name-constants for the various dimensions used for graphics drawing
	public static final int CELL_SIZE = 100; // cell width and height (square)
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS; // the drawing canvas
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	public static final int GRID_WIDTH = 8; // Grid-line's width
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width
	// Symbols (cross/nought) are displayed inside a cell, with padding from border
	public static final int CELL_PADDING = CELL_SIZE / 6;
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
	public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width

	private Board board; // the game board
	private GameState currentState; // the current state of the game
	private Seed currentPlayer; // the current player
	private JLabel statusBar; // for displaying status message

	Clip soundClipMove;
	Clip soundClipGameOver;

	public GameMain() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				// Get the row and column clicked
				int rowSelected = mouseY / CELL_SIZE;
				int colSelected = mouseX / CELL_SIZE;

				if (currentState == GameState.PLAYING) {
					if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
							&& board.cells[rowSelected][colSelected].content == Seed.EMPTY) {
						board.cells[rowSelected][colSelected].content = currentPlayer; //move
						updateGame(currentPlayer, rowSelected, colSelected);
						currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
						if (soundClipMove.isRunning()) {
							soundClipMove.stop();
						}
						soundClipMove.setFramePosition(0);
						soundClipMove.start();
					}
				} else { // game over
					if (soundClipGameOver.isRunning()) {
						soundClipGameOver.stop();
					}
					soundClipGameOver.setFramePosition(0);
					soundClipGameOver.start();
					initGame(); // restart the game
				}
				repaint(); // Call-back paintComponent()
			}
		});
		try {
			URL url = this.getClass().getClassLoader().getResource("game over.WAV");
			if (url == null) {
				System.err.println("Couldn't find file: " + "game over.WAV");
			} else {
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
				soundClipGameOver = AudioSystem.getClip();
				soundClipGameOver.open(audioIn);
			}
			url = this.getClass().getClassLoader().getResource("move.wav");
			if (url == null) {
				System.err.println("Couldn't find file: " + "move.wav");
			} else {
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
				soundClipMove = AudioSystem.getClip();
				soundClipMove.open(audioIn);
			}
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			System.out.println(e.toString());
		}
		// Setup the status bar (JLabel) to display status message
		statusBar = new JLabel();
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
		statusBar.setOpaque(true);
		statusBar.setBackground(Color.LIGHT_GRAY);

		setLayout(new BorderLayout());
		add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));
		// account for statusBar in height

		board = new Board(); // allocate the game-board
		initGame(); // Initialize the game variables
	}

	/** Initialize the game-board contents and the current-state */
	public void initGame() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				board.cells[row][col].content = Seed.EMPTY; // all cells empty
			}
		}
		currentState = GameState.PLAYING; // ready to play
		currentPlayer = Seed.CROSS; // cross plays first
	}

	public void updateGame(Seed theSeed, int row, int col) {
		if (board.hasWon(theSeed, row, col)) {
			currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
		} else if (board.isDraw()) {
			currentState = GameState.DRAW;
		}
	}

	/** Custom painting codes on this JPanel */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // fill background
		setBackground(Color.WHITE);
		board.paint(g);
		// Print status-bar message
		if (currentState == GameState.PLAYING) {
			statusBar.setForeground(Color.BLACK);
			if (currentPlayer == Seed.CROSS) {
				statusBar.setText("X's Turn");
			} else {
				statusBar.setText("O's Turn");
			}
		} else if (currentState == GameState.DRAW) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("It's a Draw! Click to play again.");
		} else if (currentState == GameState.CROSS_WON) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("'X' Won! Click to play again.");
		} else if (currentState == GameState.NOUGHT_WON) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("'O' Won! Click to play again.");
		}
	}

	/** The entry "main" method */
	public static void main(String[] args) {
		// Run GUI construction codes in Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame(TITLE);
				// Set the content-pane of the JFrame to an instance of main JPanel
				frame.setContentPane(new GameMain());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null); // center the application window
				frame.setVisible(true); // show it
			}
		});
	}
}
