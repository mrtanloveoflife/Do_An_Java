package MainGame;

public class Bullet extends Sprite implements Common {
	private int type; // 0 là đạn do hero bắn, 1 là đạn do địch bắn
	private int level;
	
	public Bullet(int x, int y, int type, int heroLevel) {
		super(x, y);
		initBullet(type, heroLevel);
		this.x = x - width / 2;
		this.y = y - height / 2;
		if (type == 0)
			level = heroLevel;
		else
			level = 0;
	}

	private void initBullet(int type, int heroLevel) {
		this.type = type;
		switch (type) {
			case 0: 
				loadImage("images/bullets/hero_bullet_level_" + heroLevel + ".png");
				break;
			case 1:
				loadImage("images/bullets/bullet_blue.png");
				break;
			case 2:
				loadImage("images/bullets/bullet_green.png");
				break;
			case 3:
				loadImage("images/bullets/bullet_purple.png");
				break;
			case 4:
				loadImage("images/bullets/bullet_red.png");
				break;
		}
		health = 1;
		getImageDimention();
	}

	public void move() {
		if (type == 0) {
			y -= BULLET_SPEED + level - 1;
		} else {
			y += BULLET_SPEED;
		}
	}
	
	public int getType() {
		return type;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}