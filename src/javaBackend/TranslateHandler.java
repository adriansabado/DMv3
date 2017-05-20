package javaBackend;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TranslateHandler implements MouseListener,
MouseMotionListener {
	private int lastOffsetX;
	private int lastOffsetY;
	private TransformingCanvas canvas;
	
	public TranslateHandler(TransformingCanvas canvas) {
		this.canvas = canvas;
	}
	
	public void mousePressed(MouseEvent e) {
		// capture starting point
		lastOffsetX = e.getX();
		lastOffsetY = e.getY();
	}
	
	public void mouseDragged(MouseEvent e) {
		// new x and y are defined by current mouse location subtracted
		// by previously processed mouse location
		int newX = e.getX() - lastOffsetX;
		int newY = e.getY() - lastOffsetY;
		
	
		// increment last offset to last processed by drag event.
		lastOffsetX += newX;
		lastOffsetY += newY;
	
		// update the canvas locations
		canvas.setTranslateX(canvas.getTranslateX() + newX);
		canvas.setTranslateY(canvas.getTranslateY() + newY);
		
		// schedule a repaint.
		canvas.repaint();
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
