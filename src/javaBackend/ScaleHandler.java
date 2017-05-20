package javaBackend;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class ScaleHandler implements MouseWheelListener {
	
	private TransformingCanvas canvas;
	
	public ScaleHandler(TransformingCanvas canvas) {
		this.canvas = canvas;
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			canvas.setScale(canvas.getScale() - (.05 * e.getWheelRotation()));
			
			// don't cross negative threshold.
			// also, setting scale to 0 has bad effects
			canvas.setScale(Math.max(0, canvas.getScale())); 
			canvas.repaint();
		}
	}
}
