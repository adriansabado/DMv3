package javaBackend;

import java.awt.Dimension;
import java.awt.Window;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import tests.GIF_Test;

public class MP_Displayer {

	private JFrame frame;
	private static ArrayList<String> images;
	
	/**
	 * Create the application.
	 */
	public MP_Displayer(ArrayList<String> images) {
		
		this.images = images;
		
		initialize();
	}
	
	public static void main(String[] args) {
		ArrayList<String> test = new ArrayList<String>();
		MP_Displayer display = new MP_Displayer(test);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Window[] windows = Window.getWindows();
		boolean firstWindowChecked = false; //This is a ghetto way of handling this. Please reconsider
		for(int i = 0; i < windows.length; i++) {
			if(windows[i].getClass().toString().equals("class javax.swing.JFrame")) {
				if(firstWindowChecked) windows[i].dispose();
				else firstWindowChecked = true;
			}
		}
		
		frame = new JFrame();
		frame.setTitle("Dipterocarp Modelling Tool");
		frame.setMinimumSize(new Dimension(500, 500));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 0,grow");
		
		JPanel displayPanel = new JPanel();
		scrollPane.setViewportView(displayPanel);
		displayPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		System.out.println(images.size());
		GIF_Test predictions = new GIF_Test(images);
		displayPanel.removeAll();
		displayPanel.add(predictions, "cell 0 0, grow, align center");
		
		
		predictions.setVisible(true);
		frame.setVisible(true);
	}

}
