package brickBreaker;

import java.awt.Color;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;//The JPanel is a simplest container class

public class Gameplay extends JPanel implements KeyListener,ActionListener //interface KeyListner for moving the slider and Actionlistener to move the ball
{
	private int[] previousScores;
	private Clip clip;
	

    private boolean play= false;
    private int score=0;//initial score is zero
    
    private int totalBricks=21;//No of bricks
    private Timer timer;//timer class to set timer of ball
    private int delay=8;//speed of timer 
    
    private int playerX=310;//starting position of slider on X axis
   
    private int ballposX=120;//starting position of ball
    private int ballposY=350;
    private int ballXdir= -1; //direction of ball 
    private int ballYdir= -2;
    private MapGenerator map;
    
    
   /*private void playMusic()
    {
        try {
            // Load the audio file
            File musicFile = new File("C:\\Users\\prakruti biradar\\Desktop");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);

            // Create a Clip and open the audio stream
           Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            // Start playing the music on a loop
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/

    
    
    
    
    
    
    
    
    
    
    
    public Gameplay()
    {
    	previousScores = new int[10]; // Assuming you want to store 10 previous scores
    	map= new MapGenerator(3,7);
    	addKeyListener(this);
    	setFocusable(true);
    	setFocusTraversalKeysEnabled(false);
    	timer = new Timer(delay, this);
    	timer.start();
    	setPreferredSize(new Dimension(694, 594)); // Set the preferred size of the panel
    	
    	 
    	   // playMusic();
    	


    }
    
    public void paintComponent(Graphics g) {
    	//background
    	super.paintComponent(g);//calling superclass paint method//
    	g.setColor(Color.black);
    	g.fillRect(1, 1, 692, 592);//dim of rectangle
    	
    	//drawing map
    	map.draw((Graphics2D)g);
    	
    	//borders
    	g.setColor(Color.yellow);
    	g.fillRect(0,0,3,592);
    	g.fillRect(0,0,692,3);
    	g.fillRect(691,0,3,592);
    	

    	// Previous scores
    	g.setFont(new Font("serif", Font.BOLD, 20));
    	g.setColor(Color.white);
    	g.drawString("Previous Scores:", 10, 30);

    	for (int i = 0; i < previousScores.length; i++) 
    	{
    	    g.drawString(String.valueOf(previousScores[i]), 180 + i * 40, 30);
    	}



    	//scores
    	g.setColor(Color.white);
    	g.setFont(new Font("serif",Font.BOLD,25));
    	g.drawString(""+score, 590,30);
    	
    	
    	//the paddle
    	g.setColor(Color.green);
    	g.fillRect(playerX, 550, 150, 20);
    	
    	
    	//The ball
    	g.setColor(Color.yellow);
    	g.fillOval(ballposX,ballposY, 20, 20);
    	
    	if(totalBricks<=0)         //if game is over
    	{
    		play=false;
    		ballXdir=0;
    		ballYdir=0;
    		g.setColor(Color.red);
    		g.setFont(new Font("serif",Font.BOLD,35));
    		g.drawString("YOU WON", 260,300);
    	
    		g.setFont(new Font("serif",Font.BOLD,20));
    		g.drawString("Press enter to restart", 280,350);
    	}
    	
    	if(ballposY>570)            //to check if ball goes out of frame
    	{
    		play=false;
    		ballXdir=0;
    		ballYdir=0;
    		g.setColor(Color.red);
    		g.setFont(new Font("serif",Font.BOLD,35));
    		g.drawString("Game over, Your Score:"+score, 190,300);
    	
    		g.setFont(new Font("serif",Font.BOLD,20));
    		g.drawString("Press enter to restart", 280,350);
    	}
    	g.dispose();
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play)
        {
        	if(new Rectangle(ballposX, ballposY,20,20).intersects(new Rectangle(playerX, 550,100,8)))//to detect the intersection between ball and slider
        	{
        		ballYdir=-ballYdir;
        	}
        	
        A:	for(int i=0;i<map.map.length;i++)
        	{
        		for(int j=0;j<map.map[0].length;j++) {
        			if(map.map[i][j]>0) {
        				int brickX=j*map.brickWidth+80;
        				int brickY=i*map.brickHeight+50;
        				int brickWidth=map.brickWidth;
        				int brickHeight=map.brickHeight;
        				
        				Rectangle rect=new Rectangle(brickX,brickY,brickWidth,brickHeight);
        				Rectangle ballRect=new Rectangle(ballposX, ballposY, 20, 20);//creating rectangle around ball to detect the intersection
                        Rectangle brickRect=rect;
                        
                        if(ballRect.intersects(brickRect))
                        {
                        	 map.setBrickValue(0, i, j);
                        	 totalBricks--;
                        	 score+=5;
                        	 
                        	 if(ballposX+19<=brickRect.x||ballposX+1>=brickRect.x+brickRect.width)
                        	 {
                        		 ballXdir=-ballXdir;
                        	 }else {
                        		 ballYdir=-ballYdir;
                        	 }
                        	 break A;//to break of label A of for loop
                        }
                        
        			}
        		}
        	}
        	
        	ballposX += ballXdir;//to detect if the ball is touching top,left,right
        	ballposY += ballYdir;
        	if(ballposX<0) {
        		ballXdir =-ballXdir;
        	}
        	if(ballposY<0) {
        		ballYdir =-ballYdir;
        	}
        	if(ballposX>670) {
        		ballXdir =-ballXdir;//To change direction
        	}
        	
        }
  

            if (totalBricks <= 0) {
                // ...

                // Store the current score
                for (int i = previousScores.length - 1; i > 0; i--) {
                    previousScores[i] = previousScores[i - 1];
                }
                previousScores[0] = score;
                // Debugging output
                System.out.println("Current Score: " + score);
                System.out.println("Previous Scores:");
                for (int i = 0; i < previousScores.length; i++) {
                    System.out.println(previousScores[i]);}

                repaint(); // Redraw the panel to update the scores
            }

      repaint();
		
	}
	
	private void pauseMusic() {
	    if (clip != null && clip.isRunning()) {
	        clip.stop();
	    }
	}

	private void resumeMusic() {
	    if (clip != null && !clip.isRunning()) {
	        clip.start();
	    }
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		   Object keyEvent;
	if(e.getKeyCode() ==KeyEvent.VK_RIGHT){                     //detect right key
		  if(playerX >=600)
		  {
			   playerX=600;//to keep it inside the border
		  }
		  else
		  {
			   moveRight();
		  }
	   }
	   if(e.getKeyCode()==KeyEvent.VK_LEFT){                     //detect left key
		   if(playerX <=10)
			  {
				   playerX=10;//to keep it inside the border
			  }
			  else
			  {
				   moveLeft();
			  }
	   }
	   if(e.getKeyCode()==KeyEvent.VK_ENTER) //when we restart the game
	   {
		   if(!play) {
			   play=true;
			   ballposX=120;
			   ballposY=350;
			   ballXdir=-1;
			   ballYdir=-2;
			   playerX=310;
			   score=0;
			   totalBricks=21;
			   map=new MapGenerator(3,7);
			   
			   repaint();
		   }
			 
	   }
	   
	   if (e.getKeyCode() == KeyEvent.VK_P) { // Pause music on 'P' key press
	        pauseMusic();
	    }

	    if (e.getKeyCode() == KeyEvent.VK_R) { // Resume music on 'R' key press
	        resumeMusic();
	    }
	   
	   
	   
	   
	}

	public void moveRight()
	{
		 play=true;
		 playerX+=20;//it will move 20 pixels to right side
		 
	}
	public void moveLeft()
	{
		 play=true;
		 playerX-=20;//it will move 20 pixels to left side
		 
	}
}
//keylistener id used to detect if we have pressed right arrow key or left arrow key 
