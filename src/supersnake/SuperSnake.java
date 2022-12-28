package supersnake;

import javax.swing.JFrame;

public class SuperSnake {

	public static void main(String[] args) {
		// create the game window frame
		JFrame frame = new JFrame();
		// define the size of the frame: x,y, width, height
		frame.setBounds(10, 10, 900, 720);
		// 能不能手动拖动去改变它的大小
		frame.setResizable(false);
		// 点击工具栏条上的x按键时，exit the game
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new GamePanel());
		
		// make the frame show up
		frame.setVisible(true);
	}

}
