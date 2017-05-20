package tests;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import net.miginfocom.swing.MigLayout;

public class GIF_Test extends JPanel implements ActionListener {
	ImageIcon images[];
	int totalImages, currentImage = 0, animationDelay = 300;
	Timer animationTimer;

	public GIF_Test(ArrayList<String> paths) {
		totalImages = paths.size();
		images = new ImageIcon[totalImages];
		for (int i = 0; i < images.length; ++i)
			images[i] = new ImageIcon(paths.get(i));
		startAnimation();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (images[currentImage].getImageLoadStatus() == MediaTracker.COMPLETE) {
			int x = (this.getWidth() - images[currentImage].getIconWidth()) / 2;
		    int y = (this.getHeight() - images[currentImage].getIconHeight()) / 2;
			images[currentImage].paintIcon(this, g, x, y);
			currentImage = (currentImage + 1) % totalImages;
		}
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void startAnimation() {
		if (animationTimer == null) {
			currentImage = 0;
			animationTimer = new Timer(animationDelay, this);
			animationTimer.start();
		} else if (!animationTimer.isRunning())
			animationTimer.restart();
	}

	public void stopAnimation() {
		animationTimer.stop();
	}

	public static void main(String args[]) {
		ArrayList<String> pics = new ArrayList<String>();
		pics.add("pictures/predictionmp0.png");
		pics.add("pictures/predictionmp1.png");
		GIF_Test anim = new GIF_Test(pics);
		JFrame app = new JFrame("Animator test");
		app.setLayout(new MigLayout("", "[grow]", "[grow]"));
		app.add(anim, "cell 0 0");
		app.setSize(300, 300);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
