package MainGame;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Sprite {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Image image;
	protected int health;
	 
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		health = 1;
	}
 
	public void loadImage(String fileName) {
		ImageIcon ii = new ImageIcon(fileName);
		image = ii.getImage();
	}
	public void getImageDimention() {
		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	public boolean isAlive() {
		return health > 0;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImage() {
		return image;
	}
 
	public Rectangle getBound() {
		return new Rectangle(x, y, width, height);
	}
}