package supersnake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
	ImageIcon title = new ImageIcon("title.png");
	ImageIcon body = new ImageIcon("body.png");
	ImageIcon right = new ImageIcon("right.png");
	ImageIcon down = new ImageIcon("down.png");
	ImageIcon left = new ImageIcon("left.png");
	ImageIcon up = new ImageIcon("up.png");
	ImageIcon food = new ImageIcon("food.png");
	
	// define the initial value for length of the snake
	int len = 3;
	int score = 0;
	// define an array to store the snake's position at x and y
	int[] snakex = new int[750];
	int[] snakey = new int[750];
	// define a string 
	String fx = "R"; // direction: R, L, U, D
	boolean isStarted = false;
	boolean isFailed = false; 
	// set a timer with 100ms, so every 100ms, it will refresh
	Timer timer = new Timer(100, this);
	int foodx;
	int foody;
	Random rand = new Random();
	

	// constructor构造函数：when we create a new GamePanel object, the code in below method will run
	public GamePanel()   {
		// call the method to initialize the snake 
		initSnake();
		this.setFocusable(true);
		this.addKeyListener(this);
		timer.start(); // start the timer in the initial state
	}
	
	// 画笔来画组件	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		
		title.paintIcon(this, g, 25, 11);
		
		g.fillRect(25, 72, 850, 600);
		g.setColor(Color.WHITE);
		g.setFont(new Font("ariel", Font.BOLD, 15));
		g.drawString("Len: " + len, 750, 35);
		g.drawString("Score: " + score, 750, 50);
		
		// print out the head according to the key pressed 
		if (fx == "R" ) {
			right.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (fx == "L") {
			left.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (fx == "D") {
			down.paintIcon(this, g, snakex[0], snakey[0]);
		} else if (fx == "U") {
			up.paintIcon(this, g, snakex[0], snakey[0]);
		}

		// create a for-loop to iterate and print out the snake body
		for(int i = 1; i < len; i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		
		food.paintIcon(this, g, foodx, foody);
		
		if (isStarted == false) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("ariel", Font.BOLD, 40));
			g.drawString("Press Space to Start", 300, 300);
		}
		if (isFailed) {
			g.setColor(Color.RED);
			g.setFont(new Font("ariel", Font.BOLD, 40));
			g.drawString("Press Space to Restart", 200, 300);
		}
	}
	
	// define a method to initialize the snake
	public void initSnake() {
		len = 3;
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
		foodx = 25 + 25 * rand.nextInt(34);
		foody = 75 + 25 * rand.nextInt(24);
		fx = "R";
		score = 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_SPACE) {
			if(isFailed) {
				isFailed = false;
				initSnake();
			} else {
				isStarted = !isStarted;
			}
			repaint(); // re-call the method
		} else if (keyCode == KeyEvent.VK_LEFT) {
			fx = "L";
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			fx = "R";
		} else if (keyCode == KeyEvent.VK_UP) {
			fx = "U";
		} else if (keyCode == KeyEvent.VK_DOWN) {
			fx = "D";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	// when the times up for the timer, this method will be called
	@Override
	public void actionPerformed(ActionEvent e) {
		if (isStarted && !isFailed) {
			// the snake body moves toward the body part before it
			for (int i = len-1; i > 0; i--) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			if (fx == "R") {
				snakex[0] = snakex[0] + 25;
				// if the snake head moves over the panel, make it return to position at 25px
				if (snakex[0] > 850)snakex[0] = 25;
			} else if (fx == "L") {
				snakex[0] = snakex[0] - 25;
				if (snakex[0] < 25)snakex[0] = 850;
			} else if (fx == "U") {
				snakey[0] = snakey[0] - 25;
				if (snakey[0] < 75)snakey[0] = 650;
			} else if (fx == "D") {
				snakey[0] = snakey[0] + 25;
				if (snakey[0] > 655)snakey[0] = 75;
			}
			if (snakex[0] == foodx && snakey[0] == foody) {
				len++;
				score += 50; 
				foodx = 25 + 25 * rand.nextInt(34);
				foody = 75 + 25 * rand.nextInt(24);
			}	
			
			// if body at any i touched the head, isFailed is true
			for (int i = 1; i < len; i++) {
				if (snakex[i] == snakex[0] && snakey[i] == snakey[0]) {
					isFailed = true;
				}
			}
			repaint();
		}
		timer.start(); // restart the timer so the game keeps on going
	}
}
