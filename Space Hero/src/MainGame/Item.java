package MainGame;

public class Item extends Sprite implements Common {

	public Item(int x, int y) {
		super(x, y);
		initItem();
	}

	private void initItem() {
		 loadImage("images/item/item.png");
		 getImageDimention();
	}
	
	public void move() {
		y += ITEM_SPEED;
	}
	 
}