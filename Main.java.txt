package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj=new JFrame();
		Gameplay gameplay=new Gameplay();
		obj.setBounds(10, 10, 700, 600);//background size i.e setBounds(int x-coordinate, int y-coordinate, int width, int height)
		obj.setTitle("Breakout Ball");
		obj.setResizable(false);//parameter is false then the user cannot re-size the frame
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Exit the application
		obj.add(gameplay);//add object of Gameplay
		
	}

}
