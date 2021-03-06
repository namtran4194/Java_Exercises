package adapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import security.EncrypterDecrypter;

/**
 * Read and write top player in a file
 */
public class HighScores {
	private List<Player> listTopPlayer = new ArrayList<>();
	protected File file;
	public EncrypterDecrypter ed = new EncrypterDecrypter();

	public HighScores() {
		if (setPath())
			readData();
	}

	/**
	 * Read list player from file
	 */
	public boolean readData() {
		Player player;
		String name, path;
		int score;
		String line;
		StringTokenizer tokenizer;
		path = System.getProperty("user.home") + "\\.minesweeper\\highscore";
		File file = new File(path);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while ((line = reader.readLine()) != null) {
				line = ed.decrypt(line);
				tokenizer = new StringTokenizer(line, "|");
				name = tokenizer.nextToken();
				score = Integer.parseInt(tokenizer.nextToken());
				player = new Player(name, score);
				listTopPlayer.add(player);
			}
			sort();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Write list player from List to file
	 */
	public boolean writeData() {
		Player player;
		String line;
		try (PrintWriter writer = new PrintWriter(new FileWriter(file, false), true)) {
			for (int i = 0; i < listTopPlayer.size(); i++) {
				player = listTopPlayer.get(i);
				line = player.getName() + "|" + player.getScore();
				line = ed.encrypt(line);
				writer.println(line);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Set path to locate file
	 */
	public boolean setPath() {
		String path = System.getProperty("user.home");
		path += File.separator + ".minesweeper" + File.separator + "highscore";
		file = new File(path);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Sort List by decreasing
	 */
	public void sort() {
		Collections.sort(listTopPlayer, new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				int score1 = o1.getScore();
				int score2 = o2.getScore();
				if (score1 > score2)
					return -1;
				else if (score1 == score2)
					return 0;
				else
					return 1;
			}

		});
	}

	/**
	 * Removes all of the elements from this list
	 */
	public void removeAll() {
		listTopPlayer.clear();
	}

	public List<Player> getListTopPlayer() {
		return listTopPlayer;
	}

	public void setListTopPlayer(List<Player> listTopPlayer) {
		this.listTopPlayer = listTopPlayer;
	}

}
