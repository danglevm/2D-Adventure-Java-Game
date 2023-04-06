package projectile;

import java.awt.Color;

import adventureGame2D.GamePanel;

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
		spellCost = 2;
		this.alive = false;
		this.getImage();
		this.pColor = new Color (195, 82, 50);
		this.pSize = 6;
		this.pSpeed = 1;
		this.pDuration = 18;
		
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
