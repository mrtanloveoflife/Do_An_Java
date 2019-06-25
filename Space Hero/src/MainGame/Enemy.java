package MainGame;

public class Enemy extends Sprite implements Common {
	private boolean shooting;
	private int type; // 0 màu xanh dương, 1 màu xanh lá, 2 màu tím, 3 màu đỏ
	
	public Enemy(int x, int y, int type) {
		super(x, y);
		initEnemy(type);
	}

	private void initEnemy(int type) {
		shooting = false;
		this.type = type;
		switch (type) {
			case 0: 
				loadImage("images/enemies/enemy_blue.png");
				health = 1;
				break;
			case 1: 
				loadImage("images/enemies/enemy_green.png");
				health = 2;
				break;
			case 2: 
				loadImage("images/enemies/enemy_purple.png");
				health = 3;
				break;
			case 3: 
				loadImage("images/enemies/enemy_red.png");
				health = 4;
				break;
		}
		getImageDimention();
	}

	public void move(int status) {
		switch (status) {
			case 0:
				x += ENEMY_SPEED;
				break;
		 	case 1:
		 		x -= ENEMY_SPEED;
		 		break;
		 	case 2:
		 		y += ENEMY_SPEED;
		 		break;
		 }
	}
	
	public int getType() {
		return type;
	}
}