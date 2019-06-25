package MainGame;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class SpaceHero extends JFrame implements Common{

	public SpaceHero() {
		setTitle("My first game");
		add(new Main());
		setSize(Common.WIDTH, Common.HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
	   
			@Override
			public void run() {
				new SpaceHero().setVisible(true);
			}
		});
	 }

}