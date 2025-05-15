import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

public class Races extends JPanel implements ActionListener, KeyListener {
	int boardHeight;
	int boardWidth;
	int tileSize = 30;

	Timer gameLoop;
	Timer produceCarsTimer;
	Timer speedUp;

	LinkedList<Integer> leftCurbs = new LinkedList<>();
	LinkedList<Integer> rightCurbs = new LinkedList<>();

	List<int[]> mainCar = new ArrayList<>();
	List<List<int[]>> cars = new ArrayList<>();

	int productionDelay = 1000;	
	int score = 0;
	int bestScore = 0;
	int speedCar = 34;
	boolean gameStop = false;

	Races(int boardHeight, int boardWidth) {
		this.boardHeight = boardHeight;
		this.boardWidth = boardWidth;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.BLACK);
		
		// creating of сurbs (створення бордюрі)
		Curbs.makeCurbs(leftCurbs, rightCurbs, boardHeight, tileSize);

		// creation of the main car (створення основної машини)
		mainCar = Cars.mainCar();

		setFocusable(true);
		addKeyListener(this);		

		gameLoop = new Timer(speedCar, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Curbs.placeCurbs(leftCurbs, rightCurbs);
				Cars.moveCars(cars);				

				gameStop = Cars.accident(mainCar, cars);
				if (gameStop) {
					repaint();	
					gameLoop.stop();
					produceCarsTimer.stop();	
				}	

				score += 1;
				repaint(); 
			}			
		});

		produceCarsTimer = new Timer(productionDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Cars.produceCars(cars);
			}
		});

		speedUp = new Timer(60_000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameLoop.setDelay(--speedCar);

				if (speedCar < 31) {
					speedUp.stop();
				}
			}
		});		

		// gameLoop.setInitialDelay(1000);
		gameLoop.start();
		// produceCarsTimer.setInitialDelay(1000);
		produceCarsTimer.start();
		
		speedUp.start();		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawRaces(g);	
	}

	public void drawRaces(Graphics g) {
		// Curbs
		for (int i = 1; i < boardHeight / tileSize; i++) {
			if (leftCurbs.get(i) == 1) {
				g.setColor(Color.green);
				g.fillRect(0, i * tileSize, tileSize, tileSize);
				g.setColor(Color.green);
				g.fillRect(boardWidth - tileSize, i * tileSize, tileSize, tileSize);
			} else {
				g.setColor(getBackground());
				g.fillRect(0, i * tileSize, tileSize, tileSize);
				g.setColor(getBackground());
				g.fillRect(boardWidth - tileSize, i * tileSize, tileSize, tileSize);
			}
		}	

		// Net (сітка)
		// g.setColor(Color.red);
		// for (int i = 0; i < boardHeight / tileSize; i++) {
		// 	g.drawLine(0, i * tileSize, boardWidth, i * tileSize);
		// }
		// for (int i = 0; i < boardWidth / tileSize; i++) {
		// 	g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
		// }
		
		// Main car		
		g.setColor(Color.green);
		for (int i = 0; i < mainCar.size(); i++) {
			int[] array = mainCar.get(i);
			g.fillRect(array[0] * tileSize, array[1] * tileSize, tileSize, tileSize);
		}
		
		// Another cars
		for (List<int[]> i : cars) {
			for (int j = 0; j < i.size(); j++) {
				int[] position = i.get(j);
				g.fillRect(position[0] * tileSize, position[1] * tileSize, tileSize, tileSize);
			} 
		}
		
		// Score
		if (!gameStop) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("Score: " + score, 10, 20);
		} else {
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString("Game Over", 45, 170);

			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 24));

			// if (bestScore < score) bestScore = score;

			Graphics2D g2d = (Graphics2D) g;

			String textScore = "Score: " + score;
			String textBestScore = "";

			try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\rura0\\Desktop\\Practice\\java\\Races\\best-result.txt"))) {
				String line = reader.readLine();

				if (line != null) {
					bestScore = Integer.parseInt((line));
				}
				
				if (score > bestScore) {
					try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\rura0\\Desktop\\Practice\\java\\Races\\best-result.txt"))) {
						writer.write(String.valueOf(score));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}					
			} catch (IOException e) {
					e.printStackTrace();
			}

			try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\rura0\\Desktop\\Practice\\java\\Races\\best-result.txt"))) {
				textBestScore = "Best score: " + reader.readLine(); 
			} catch (IOException  e) {	
				System.out.println("Problem");			
			} 			

			FontMetrics fm = g2d.getFontMetrics();
			
			int textScoreWidth = fm.stringWidth(textScore);
			int textBestSrcoreWidth = fm.stringWidth(textBestScore);

			int xScore = getWidth() / 2 - textScoreWidth / 2;
			int xBestScore = getWidth() / 2 - textBestSrcoreWidth / 2;

			g2d.drawString(textScore, xScore, 195);
			g2d.drawString(textBestScore, xBestScore, 220);

			g.setFont(new Font("Arial", Font.PLAIN, 18));
			g.drawString("Press Space to Continue", 50, 250);
		}
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

    @Override
    public void keyTyped(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT && !gameStop) {
			Cars.moveCar(-1, mainCar);
		} else if (key == KeyEvent.VK_RIGHT && !  gameStop) {
			Cars.moveCar(1, mainCar);
		} else if (key == KeyEvent.VK_SPACE && gameStop) {
			score = 0;
			gameStop = false;
			productionDelay = 400;
			speedCar = 34;
			mainCar = Cars.mainCar(); 
			cars = new ArrayList<>();
			leftCurbs = new LinkedList<>();
			rightCurbs = new LinkedList<>();
			Curbs.makeCurbs(leftCurbs, rightCurbs, boardHeight, tileSize);
			gameLoop.start();
			produceCarsTimer.start();
			speedUp.start();		
			repaint();
		}

		gameStop = Cars.accident(mainCar, cars);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }
}
