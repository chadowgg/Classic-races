import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int  boardHeight = 600;
		int  boardWidth = boardHeight / 2;

		JFrame frame = new JFrame("Races");
		
		Menu menu = new Menu(boardHeight, boardWidth, frame);
		frame.add(menu);

		
		frame.setSize(boardWidth, boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
		frame.pack();
		
		menu.requestFocusInWindow();
    }
}
