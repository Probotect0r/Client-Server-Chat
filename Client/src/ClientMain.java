import java.awt.Dimension;

import javax.swing.JFrame;


public class ClientMain {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Client");
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Panel());
		Dimension dim = new Dimension(700,700);
		frame.setPreferredSize(dim);
		frame.pack();
	}

}
