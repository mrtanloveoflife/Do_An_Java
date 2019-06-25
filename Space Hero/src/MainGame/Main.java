package MainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel implements ActionListener, Common {
	 private ArrayList <Item> items;
	 private ArrayList <Enemy> enemies;
	 private ArrayList <Bullet> bullets;
	 private Hero hero;
	 private Timer timer;
	 private boolean inGame;
	 private int point;
	 private int enemiesStatus; // 0 sang phải, 1 sang trái, 2 xuống dưới

	 private void initBoard() {
		 inGame = true;
		 point = 0;
		 addMouseMotionListener(new MMAdapter());
		 addMouseListener(new MAdapter());
		 setFocusable(true);
		 setBackground(Color.lightGray);
		 setDoubleBuffered(true);
		 hero = new Hero(INIT_HERO_X, INIT_HERO_Y);
		 enemies = new ArrayList<Enemy>();
		 items = new ArrayList<Item>();
		 bullets = new ArrayList<Bullet>();
		 initEnemy();
		 timer = new Timer(DELAY, this);
		 timer.start();
		 enemiesStatus = 0;
	 }
	 
	 private class MMAdapter implements MouseMotionListener {
		 @Override
		 public void mouseMoved(MouseEvent e) {
			 hero.mouseMoved(e);
		 }

		@Override
		public void mouseDragged(MouseEvent e) {
			hero.mouseMoved(e);
		}
	 }
	 
	 private class MAdapter implements MouseListener {

		@Override
			public void mousePressed(MouseEvent e) {
				// Nhấp chuột trái sẽ bắn
				if (e.getButton() == MouseEvent.BUTTON1)
					hero.setShooting(true);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	 }
	 
	 private void initEnemy() {
		 for (int i = 0; i < 3; i++) 
				 for (int j = 1; j <= 10 ; j++){
					 Enemy enemy = new Enemy(30 + 50 * j, (-20 + 40 * (3 - i)), i);
					 enemies.add(enemy); 
				 }
	 }
	 
	 private void initItem(int x, int y) {
		 Item item = new Item(x, y);
		 items.add(item);
	 }
	 
	 private void initBullet(int x, int y, int type) {
		 Bullet bullet = new Bullet(x, y, type, hero.getLevel());
		 bullets.add(bullet);
	 }
	 
	 @Override
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 if (inGame) {
			 drawObject(g);
		 } else {
			 drawGameOver(g);
		 }
	 }
	 
	 private void drawObject(Graphics g) {
		  // Draw Enemies
		  for (Enemy enemy : enemies) {
			  g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
		  }
		  // Draw Items
		  for (Item item : items) {
			  g.drawImage(item.getImage(), item.getX(), item.getY(), this);
		  }
		  // Draw Bullets
		  for (Bullet bullet : bullets) {
			  g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
		  }
		  // Draw Hero
		  if (!hero.isFlickering() || (hero.isFlickering() && (Calendar.getInstance().getTimeInMillis() % 2 == 0)))
		  g.drawImage(hero.getImage(), hero.getX(), hero.getY(), this);
		  

		 // Draw Point
		 Font font = new Font("Arial", Font.BOLD, 12);
		 g.setColor(Color.BLACK);
		 g.setFont(font);
		 g.drawString("Your point: " + point, 5, 15);
		 g.drawString("" + hero.isShooting(), 5, 20);
	}
	 
	 private void drawGameOver(Graphics g) {
		 Font font = new Font("Helvetica", Font.BOLD, 20);
		 g.setColor(Color.BLACK);
		 g.setFont(font);
		 g.drawString("Game over!", 350, Common.HEIGHT / 2);
		 g.drawString("Score: " + point, 370, Common.HEIGHT / 2 + 30);
	 }
	 
	 @Override
	 public void actionPerformed(ActionEvent e) {
		 inGame();
		 // Update Object
		 updateHero();
		 updateEnemy();
		 updateItem();
		 updateBullet();
		 
		 // Check collision
		 checkCollision();
		 
		 // Repaint
		 repaint();
	 }
	 
	 public void checkCollision() {
		 // Hero vs Enemies
		 Rectangle heroBound = hero.getBound();
		 
		 if (!hero.isFlickering()) {
			 for (Enemy enemy: enemies) {
				 Rectangle enemyBound = enemy.getBound();
				 if (heroBound.intersects(enemyBound)) {
					 point += (enemy.getType() + 1) * 10;
					 enemy.setHealth(0);
					 hero.getHit();
					 break;
				 }
			 }
		 }
		  
		 // Hero vs Enemy bullets
		 if (!hero.isFlickering()) {
			 for (Bullet bullet: bullets) 
				 if (bullet.getType() > 0) {
					 Rectangle bulletBound = bullet.getBound();
					 if (heroBound.intersects(bulletBound)) {
						 bullet.setHealth(0);
						 hero.getHit();
						 break;
					 }
			 }
		  }
		
		 // Hero vs Items
		 for (Item item: items) {
			 Rectangle itemBound = item.getBound();
			 if(heroBound.intersects(itemBound)) {
				 point += 10;
				 item.setHealth(0);
				 hero.levelUp();
			 }
		 }
		 
		 // Hero bullets vs Enemy
		 for (Bullet bullet: bullets) 
			 if (bullet.getType() == 0) {
				 Rectangle bulletBound = bullet.getBound();
				 for (Enemy enemy: enemies) {
					 if (!enemy.isAlive()) continue;
					 Rectangle enemyBound = enemy.getBound();
					 if (bulletBound.intersects(enemyBound)) {
						 point += Math.min(hero.getLevel(), enemy.health) * 10;
						 enemy.setHealth(enemy.health - hero.getLevel());
						 bullet.setHealth(0);
						 break;
					 }
				 }
		 }
	 }
	 
	 private void inGame() {
		 if (!inGame) {
			 timer.stop();
		 }
	 }
	 
	 private boolean random(double percent) {
		 return new Random().nextInt() % (int)(1.0/(percent/100)) == 0;
	 }
	 
	 private void updateHero() {
		 // kiếm tra xem hero có được lệnh bắn không
		 if (hero.isShooting()) {
			 initBullet(hero.getX() + hero.width / 2, hero.getY(), 0);
			 hero.setShooting(false);
		 }
		 
		 hero.checkFlicker();
		 
		 hero.move();
	 }
	 
	 private void moveDownEnemy() {
		 for (int i = 0; i < enemies.size(); i++) {
			 enemies.get(i).move(2);
		 }
	 }
	 
	 private void updateEnemy() {
		 // Loại bỏ enemy hết máu hoặc xuống dưới màn hình, một số con có xác suất rớt item 10%.
		 for (int i = 0; i < enemies.size(); i++) {
			 Enemy enemy = enemies.get(i);
			 if (!enemy.isAlive() || (enemy.getY() > Common.HEIGHT)) {
				 enemies.remove(i);
				 if (random(10))
					 initItem(enemy.getX() - enemy.width / 2, enemy.getY());
			 }
		  }
		 // Nếu enemy đầu tiên còn ở trên màn hình thì cả đàn sẽ từ từ đi xuống
		 if (!enemies.isEmpty())
			 if (enemies.get(0).getY() + enemies.get(0).height < 0)
				 moveDownEnemy();
		 
		 // Thay đổi hướng đi của cả đàn enemy nếu có enemy chạm phải cạnh màn hình
		 for (int i = 0; i < enemies.size(); i++) {
			 Enemy enemy = enemies.get(i);
			 if ((enemiesStatus == 0) && (enemy.getX() + enemy.width + ENEMY_SPEED > Common.WIDTH)) {
				 enemiesStatus = 1;
				 moveDownEnemy();
			 }
			 else if ((enemiesStatus == 1) && (enemy.getX() - ENEMY_SPEED < 0)) {
				 enemiesStatus = 0;
				 moveDownEnemy();
			 }
		  }
		 
		 // Ngẫu nhiên sẽ có enemy bắn đạn với xác suất 0.2%
		 for (int i = 0; i < enemies.size(); i++) 
			 if (random(0.2)){
				 Enemy enemy = enemies.get(i);
				 initBullet(enemy.getX() - enemy.width / 2, enemy.getY() + enemy.height, enemy.getType() + 1);
		 }
		 
		 // Di chuyển đàn enemy
		 for (int i = 0; i < enemies.size(); i++) {
			 enemies.get(i).move(enemiesStatus);
		 }		 
	 }
	 
	 private void updateItem() {
		 for (int i = 0; i < items.size(); i++) {
			 Item item = items.get(i);
			 if (!item.isAlive()) {
				 items.remove(i);
			 }
		 }
		 for (int i = 0; i < items.size(); i++) {
			 items.get(i).move();
		 }
	 }
	 
	 private void updateBullet() {
		 for (int i = 0; i < bullets.size(); i++) {
			 Bullet bullet = bullets.get(i);
			 if (!bullet.isAlive()) {
				 bullets.remove(i);
			 }
		 }
		 for (int i = 0; i < bullets.size(); i++) {
			 bullets.get(i).move();
		 }
	 }
	 
	 public Main() {
		 initBoard();
	 }
}