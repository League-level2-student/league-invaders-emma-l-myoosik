import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager implements ActionListener {
	Rocketship rocketship;
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	ArrayList<Alien> aliens = new ArrayList<Alien>();
	
	int score = 0;
	
	ObjectManager(Rocketship rocketship) {
		this.rocketship = rocketship;
	}
	
	void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}
	
	void addAlien() {
		Random random = new Random();
		aliens.add(new Alien(random.nextInt(LeagueInvaders.WIDTH),0,50,50));
	}
	
	void update() {
		for (Alien alien: aliens) {
			alien.update();
			
			if(alien.y == LeagueInvaders.HEIGHT) {
				alien.isActive = false;
			}
		}
		
		for (Projectile projectile: projectiles) {
			projectile.update();
			
			if(projectile.y == LeagueInvaders.HEIGHT) {
				projectile.isActive = false;
			}
		}
		
		rocketship.update();
		
		checkCollision();
		purgeObjects();
	}
	
	void draw(Graphics g) {
		rocketship.draw(g);
		for (Alien alien: aliens) {
			alien.draw(g);
		}
		for (Projectile projectile: projectiles) {
			projectile.draw(g);
		}
	}
	
	void purgeObjects() {
		if (!rocketship.isActive) {
			return;
		}
		
		for (int i = 0; i < aliens.size(); i++) {
			if (!aliens.get(i).isActive) {
				aliens.remove(i);
			}
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			if (!projectiles.get(i).isActive) {
				projectiles.remove(i);
			}
		}
		
	}
	
	void checkCollision() {
		for (Alien alien: aliens) {
			if(rocketship.collisionBox.intersects(alien.collisionBox)) {
				rocketship.isActive = false;
				alien.isActive = false;
				score++;
				break;
			}
			
			for (Projectile projectile: projectiles) {
				if(projectile.collisionBox.intersects(alien.collisionBox)) {
					projectile.isActive = false;
					alien.isActive = false;
					score++;
				}
			}
		}
	}
	
	int getScore() {
		return score;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		addAlien();
	}
}
