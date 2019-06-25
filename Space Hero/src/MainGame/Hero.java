package MainGame;

import java.awt.event.MouseEvent;
import java.util.Calendar;

public class Hero extends Sprite implements Common {
	private int dx;
	private int dy;
	private int level;
	private boolean flickering;
	private boolean shooting;
	private Calendar flickerTime;

	public Hero(int x, int y) {
		super(x, y);
		initHero();
	}

	private void initHero() {
		level = 1;
		health = HERO_MAX_HEALTH;
		flickering = false;
		shooting = false;
		flickerTime = Calendar.getInstance();
		loadImage("images/hero/hero_level_1.png");
		getImageDimention();
	}
 
	public void move() {
		if (x < dx) x += HERO_SPEED;
		if (x > dx) x -= HERO_SPEED;
		
		if (y < dy) y += HERO_SPEED;
		if (y > dy) y -= HERO_SPEED;
		
		if ((Math.abs(x-dx) < HERO_SPEED) && ((Math.abs(y-dy) < HERO_SPEED))) {
			x = dx;
			y = dy;
		}
	}
	
	public void levelUp() {
		if (level < 3) {
			level++;
			loadImage("images/hero/hero_level_" + level + ".png");
		}
		health = HERO_MAX_HEALTH;
	}
	
	public boolean isFlickering() {
		return flickering;
	}
	
	public boolean isShooting() {
		return shooting;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}
	
	public void getHit() {
		flickering = true;
		flickerTime = Calendar.getInstance();
		flickerTime.add(Calendar.SECOND, 3);
		health--;
	}
	
	public void checkFlicker() {
		if (flickerTime.getTimeInMillis() <= Calendar.getInstance().getTimeInMillis()) 
			flickering = false; 
	}
 
	public void mouseMoved(MouseEvent e) {
		dx = e.getX() - width / 2;
		dy = e.getY() - height / 2;
	}
}