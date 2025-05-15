import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Menu extends JPanel implements KeyListener {
    int boardHeight;
	int boardWidth;
    JFrame frame;

    public Menu(int boardHeight, int boardWidth, JFrame frame) {
        this.frame = frame;
        this.boardHeight = boardHeight;
		this.boardWidth = boardWidth;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.BLACK);

        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GREEN); 
        g.setFont(new Font("Arial", Font.BOLD, 35));
        g.drawString("Classic", 85, 180);
        g.drawString("Races", 85, 230);
        
        //control
        g.setColor(Color.WHITE); 
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Control", 115, 300);

        g.drawString("<---         --->", 95, 330);

        g.drawString("Press Space to Start", 60, 380);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            frame.remove(Menu.this);

            Races races = new Races(boardHeight, boardWidth);
            frame.add(races);

            frame.revalidate();
            frame.repaint();

            SwingUtilities.invokeLater(() -> {
                races.requestFocusInWindow();
            });
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
