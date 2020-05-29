import java.awt.Dimension;
import javax.swing.JFrame;

public class LeagueInvaders {
	JFrame frame;
	GamePanel gamePanel;
	
	public static final int WIDTH = 500;
	public static final int HEIGHT = 800;
	
	LeagueInvaders() {
		frame = new JFrame();
		gamePanel = new GamePanel();
	}
	
	void setup() {
		frame.add(gamePanel);
		frame.addKeyListener(gamePanel);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setSize(new Dimension(WIDTH, HEIGHT));

	}
	
	public static void main(String[] args) {
		LeagueInvaders game = new LeagueInvaders();
		game.setup();
	}
}
