package javaBackend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class TransformingCanvas extends JComponent {
	private double translateX;
	private double translateY;
	private double scale;
	private String filename;

	TransformingCanvas(String filename) {
		translateX = 0;
		translateY = 0;
		scale = 1;
		this.filename = filename;
		setOpaque(true);
		setDoubleBuffered(true);
	}

	@Override public void paint(Graphics g) {

		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);
		tx.translate(translateX, translateY);
		System.out.println("Scale:\t" + scale + " TX:\t" + translateX + "TY:\t" + translateY);
		//System.out.println(translateX + "\t" + translateY);
		
		
		Graphics2D ourGraphics = (Graphics2D) g;
		
		ourGraphics.setColor(Color.WHITE);
		ourGraphics.fillRect(0, 0, getWidth(), getHeight());
		ourGraphics.setTransform(tx);
		
		//System.out.println("test");
		
		File image = new File(filename);
		BufferedImage bi;
		try {
			bi = ImageIO.read(image);
			ourGraphics.drawRenderedImage(bi, tx);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error displaying image");
		}
	}

	public double getTranslateX() {
		return translateX;
	}

	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}

	public double getTranslateY() {
		return translateY;
	}

	public void setTranslateY(double translateY) {
		this.translateY = translateY;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
}
