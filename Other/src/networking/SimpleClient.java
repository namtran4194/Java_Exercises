package networking;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * A simple client used to connect to the server
 * Make sure run server first
 */
public class SimpleClient extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	Socket client = null;
	String serverAddr = "localhost";
	int serverPort = 8888;
	PrintWriter out;
	JTextField tf;

	public SimpleClient() {
		try {
			client = new Socket(serverAddr, serverPort);
			System.out.println("Client: " + client);
			out = new PrintWriter(client.getOutputStream());
			out.println("Hello");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set up the UI
		Container container = this.getContentPane();
		container.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
		container.add(new JLabel("Enter your message or \"quit\""));
		tf = new JTextField(40);
		tf.addActionListener(this);
		container.add(tf);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Simple Client");
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = tf.getText();
		if (message.equals("quit")) {
			try {
				out.close();
				client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		} else {
			out.println(message);
			out.flush();
			tf.setText("");
		}
	}

	public static void main(String[] args) {
		new SimpleClient();
	}
}
