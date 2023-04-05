package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import adventureGame2D.GamePanel;

public class Particle extends Entity {

	//Entity that produces the particles
	Entity generator;
	Color color;
	int size;
	
	//Direction the particle moves to
	int xd;
	int yd;
	
	public Particle(GamePanel gp,
					Entity generator,
					Color color,
					int size,
					int speed,
					int maxLife,
					int xd,
					int yd) {
		super(gp);
		this.color = color;
		this.size = size;
		this.speed = speed;
		this.maxLife = maxLife;
		this.xd = xd;
		this.yd = yd;
		
		life = maxLife;
		this.pOffset = gp.getTileSize()/2 - this.size/2;
		this.WorldX = generator.getWorldX() + this.pOffset;
		this.WorldY = generator.getWorldY() + this.pOffset;
	}

	@Override
	public void update() {
		this.WorldX += xd * speed;
		this.WorldY += yd * speed;
		
		--this.life;
		
		if (life < maxLife/3) ++ yd;
		
		if (life < 1) this.alive = false;
		
	}
	
	@Override
	public void draw(Graphics2D g2, GamePanel gp) {
		int entityScreenX = this.WorldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
		int entityScreenY = this.WorldY - gp.getPlayer().WorldY + gp.getPlayer().getScreenY();
		
		g2.setColor(this.color);
		g2.fillRect(entityScreenX, entityScreenY, this.size, this.size);
	}
}
