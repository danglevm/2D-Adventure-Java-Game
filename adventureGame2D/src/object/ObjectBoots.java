package object;


import adventureGame2D.GamePanel;


public class ObjectBoots extends GameObject {
	
	GamePanel gp;
	
	public ObjectBoots(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		setDefaultAttributes();
		
	}

	@Override
	public void setDefaultAttributes() {
		name = "Shaggy Soggy Boots";
		objectDescription = "A timeless generational relic from your late father.\nSo unkempt from years of neglect.\nGives 1 Movement Speed.";
		collisionOn = false;
		down1 = setupEntity("boots", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
	}
}

