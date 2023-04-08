package projectile;

import java.awt.Color;

import adventureGame2D.GamePanel;


public class Rock extends Projectile {

	GamePanel gp;
	
	public Rock (GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Rock";
		spellCost = 0;
		speed = 6;
		maxLife = 80;
		attack = 5;
		life = maxLife;
		this.alive = false;
		this.getImage();
		this.collisionOn = false;
		this.pColor = new Color (240, 50, 0);
		this.pSize = 6;
		this.pSpeed = 1;
		this.pDuration = 18;
		
	}
	
	
	public void getImage() {
		up1 = setupEntity("rock_down_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		up2 = setupEntity("rock_down_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		down1 = setupEntity("rock_down_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		down2 = setupEntity("rock_down_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		left1 = setupEntity("rock_down_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		left2 = setupEntity("rock_down_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		right1 = setupEntity("rock_down_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
		right2 = setupEntity ("rock_down_1", "/projectile/", gp.getTileSize(), gp.getTileSize());
	}


}
