package javaBackend;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.Main;

public class ImageDisplayer {
	private Image imageFile;
	
	public ImageDisplayer(String imagePath, String mode) {
		try {
			imageFile = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error displaying image");
		}
		
		TransformingCanvas canvas = new TransformingCanvas(imagePath);
		
		TranslateHandler translater = new TranslateHandler(canvas);
		canvas.addMouseListener(translater);
		canvas.addMouseMotionListener(translater);
		
		canvas.addMouseWheelListener(new ScaleHandler(canvas));
		
		Window[] windows = Window.getWindows();
		
		if(mode.equals("sp")) {
			boolean firstWindowChecked = false; //This is a ghetto way of handling this. Please reconsider
			for(int i = 0; i < windows.length; i++) {
				if(windows[i].getClass().toString().equals("class javax.swing.JFrame")) {
					if(firstWindowChecked) windows[i].dispose();
					else firstWindowChecked = true;
				}
			}
		}
		
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
		/*panel.setLayout(new BorderLayout());
		panel.add(canvas, BorderLayout.CENTER);
		canvas.repaint();
		panel.repaint();
		panel.revalidate();*/
		
	}
}

