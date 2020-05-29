import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener, KeyListener {
	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;
	
	Rocketship rocketship = new Rocketship(250, 700, 50, 50);
	
	int currentState = MENU;
	
	Font titleFont;
	Font pressEnter;
	Font instructions;
	
	Timer frameDraw;
	Timer alienSpawn;
	
	ObjectManager manager = new ObjectManager(rocketship);
	
	public static BufferedImage image;
	public static boolean needImage = true;
	public static boolean gotImage = false;	
	
	GamePanel(){
		titleFont = new Font("Arial", Font.PLAIN, 48);
		pressEnter = new Font("Arial", Font.PLAIN, 20);
		instructions = new Font("Arial", Font.PLAIN, 20);
		
		frameDraw = new Timer(1000/60, this);
		frameDraw.start();
		
		if (needImage) {
		    loadImage ("space.png");
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		if(currentState == MENU) {
			drawMenuState(g);
		} else if (currentState == GAME) {
			drawGameState(g);
		} else if (currentState == END) {
			drawEndState(g);
		}
	}
	
	void updateMenuState() {
		
	}
	
	void updateGameState() {
		manager.update();
		
		if (!rocketship.isActive) {
			currentState = END;
		}
	}
	
	void updateEndState() {
		
	}
	
	void drawMenuState(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		
		g.setFont(titleFont);
		g.setColor(Color.WHITE);
		g.drawString("League Invaders", LeagueInvaders.WIDTH/7, LeagueInvaders.HEIGHT/10);
		
		g.setFont(pressEnter);
		g.setColor(Color.WHITE);
		g.drawString("Press ENTER to start", LeagueInvaders.WIDTH/3 - 10, LeagueInvaders.HEIGHT/2 - 20);
		
		g.setFont(instructions);
		g.setColor(Color.WHITE);
		g.drawString("Press Space for instructions", LeagueInvaders.WIDTH/4, LeagueInvaders.HEIGHT/2 + 100);
	}
	
	void drawGameState(Graphics g) {
		if (gotImage) {
			g.drawImage(image, 0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT, null);
		} else {
			g.setColor(Color.BLUE);
			g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		}
		manager.draw(g);
		g.drawString("Score: " + manager.getScore(), LeagueInvaders.WIDTH - 100, 25);
	}
	
	void drawEndState(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, LeagueInvaders.WIDTH, LeagueInvaders.HEIGHT);
		
		g.setFont(titleFont);
		g.setColor(Color.BLACK);
		g.drawString("Game Over", LeagueInvaders.WIDTH/4, LeagueInvaders.HEIGHT/10);
	
		g.setFont(pressEnter);
		g.setColor(Color.BLACK);
		int score = manager.getScore();
		g.drawString("You killed " + score + " enemies", LeagueInvaders.WIDTH/3 - 10, LeagueInvaders.HEIGHT/2 - 20);
		
		g.setFont(instructions);
		g.setColor(Color.BLACK);
		g.drawString("Press ENTER to restart", LeagueInvaders.WIDTH/4 + 20, LeagueInvaders.HEIGHT/2 + 100);
	}
	
	void startGame() {
		alienSpawn = new Timer(1000, manager);
		alienSpawn.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (currentState == MENU) {
			updateMenuState();
		} else if (currentState == GAME) {
			updateGameState();
		} else if (currentState == END) {
			updateEndState();
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
		    if (currentState == END) {
		        currentState = MENU;
		        rocketship = new Rocketship(250, 700, 50, 50);
		        manager = new ObjectManager(rocketship);
		        
		    } else {
		    	if (currentState == MENU) {
		    		startGame();
		    	}
		    	if (currentState == GAME) {
		    		alienSpawn.stop();
		    	}
		        currentState++;
		    }
		}
		
		if (e.getKeyCode()==KeyEvent.VK_UP && rocketship.y > 0) {
		    rocketship.up();
		} else if (e.getKeyCode()==KeyEvent.VK_DOWN && rocketship.y < 715) {
			rocketship.down();
		} else if (e.getKeyCode()==KeyEvent.VK_RIGHT && rocketship.x < 435) {
			rocketship.right();
		} else if (e.getKeyCode()==KeyEvent.VK_LEFT && rocketship.x > 0) {
			rocketship.left();
		}
		
		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			manager.addProjectile(rocketship.getProjectile());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	void loadImage(String imageFile) {
	    if (needImage) {
	        try {
	            image = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
		    gotImage = true;
	        } catch (Exception e) {
	            
	        }
	        needImage = false;
	    }
	}
	
}
