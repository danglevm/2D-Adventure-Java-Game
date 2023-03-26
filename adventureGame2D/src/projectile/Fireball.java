package projectile;

import adventureGame2D.GamePanel;
import enums.Direction;

public class Fireball extends Projectile{

	GamePanel gp;
	
	public Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Fireball";
		speed = 5;
		maxLife = 90;
		attack = 1;
		life = maxLife;
		this.alive = false;
		this.getImage();
		
	}
	
	
	public void getImage() {
		up1 = setupEntity("fireball_up_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		up2 = setupEntity("fireball_up_2", "/projectile/", gp.getTileSize(), gp.getTileSize());
		down1 = setupEntity("fireball_down_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		down2 = setupEntity("fireball_down_2", "/projectile/", gp.getTileSize(), gp.getTileSize());
		left1 = setupEntity("fireball_left_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		left2 = setupEntity("fireball_left_2", "/projectile/", gp.getTileSize(), gp.getTileSize());
		right1 = setupEntity("fireball_right_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		right2 = setupEntity ("fireball_right_2", "/projectile/", gp.getTileSize(), gp.getTileSize());
	}


}
