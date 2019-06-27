package MainGame;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class SpaceHero extends JFrame implements Common{

	public SpaceHero() {
		setTitle("Space Hero 1.0");
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