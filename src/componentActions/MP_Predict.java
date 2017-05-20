package componentActions;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.rosuda.REngine.Rserve.RConnection;

import javaBackend.FileParser;
import javaBackend.MP_Displayer;
import javaBackend.Predict_Dist;
import net.miginfocom.swing.MigLayout;

public class MP_Predict implements ActionListener {
	private JTextField tfMod;
	private ArrayList<JTextField> tfEV;
	private JCheckBox defaultModel;
	private RConnection c;
	private JComboBox filterBox;
	private JButton display;
	private Predict_Dist predict;
	private JPanel graphPanel;
	
	public MP_Predict(JTextField tfMod, ArrayList<JTextField> tfEV, JCheckBox defaultModel, RConnection c, JComboBox filterBox, JButton display, JPanel graphPanel) {
		this.tfMod = tfMod;
		this.tfEV = tfEV;
		this.defaultModel = defaultModel;
		this.c = c;
		this.filterBox = filterBox;
		this.display = display;
		this.graphPanel = graphPanel;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		boolean error = false;
		for(JTextField tf : tfEV) {
			if(tf.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "No environmental variables selected for one of the years", "Warning", JOptionPane.WARNING_MESSAGE);
				error = true;
			}
		}
		if(!error) {
			if(defaultModel.isSelected()) {
				ArrayList<String> imagePaths = new ArrayList<String>();
				for(int i = 0; i < tfEV.size(); i++) {
					FileParser parser = new FileParser();
					File[] EV = parser.textFieldParser(tfEV.get(i));
					predict = new Predict_Dist(EV, System.getProperty("user.dir").replace('\\', '/') +"/model/model.rds", "mp" + i, c);
					imagePaths.add("pictures/predictionmp" + i + ".png");
				}
				MP_Displayer displayer = new MP_Displayer(imagePaths);
				predict.getGraph(tfEV.size(), c);
				try {
					BufferedImage graph = ImageIO.read(new File("pictures/graph.png"));
					JLabel picLabel = new JLabel(new ImageIcon(graph));
					
					graphPanel.removeAll();
					graphPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
					graphPanel.add(picLabel, "cell 0 0, grow, align center");
					graphPanel.repaint();
					graphPanel.revalidate();
					
					filterBox.setEnabled(true);
					display.setEnabled(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "graph.png is missing", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			else {
				if(tfMod.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "No model selected", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				else {
					//Predict
					ArrayList<String> imagePaths = new ArrayList<String>();
					for(int i = 0; i < tfEV.size(); i++) {
						FileParser parser = new FileParser();
						File[] EV = parser.textFieldParser(tfEV.get(i));
						predict = new Predict_Dist(EV, tfMod.getText().replace('\\', '/'), "mp" + i, c);
						imagePaths.add("pictures/predictionmp" + i + ".png");
					}
					MP_Displayer displayer = new MP_Displayer(imagePaths);
					predict.getGraph(tfEV.size(), c);
					try {
						BufferedImage graph = ImageIO.read(new File("pictures/graph.png"));
						JLabel picLabel = new JLabel(new ImageIcon(graph));
						
						graphPanel.removeAll();
						graphPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
						graphPanel.add(picLabel, "cell 0 0, grow, align center");
						graphPanel.repaint();
						graphPanel.revalidate();
						
						filterBox.setEnabled(true);
						display.setEnabled(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "graph.png is missing", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
}
