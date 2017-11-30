package componentActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.rosuda.REngine.Rserve.RConnection;

import javaBackend.FileParser;
import javaBackend.ImageDisplayer;
import javaBackend.Predict_Dist;

public class SP_Predict implements ActionListener{
	private JTextField tfMod;
	private JTextField tfEV;
	private JCheckBox defaultModel;
	private RConnection c;
	private JButton filterBtn;
	private JComboBox filterBox;
	
	
	public SP_Predict(JTextField tfMod, JTextField tfEV,  JCheckBox defaultModel, RConnection c, JButton filterBtn, JComboBox filterBox) {
		this.tfMod = tfMod;
		this.tfEV = tfEV;
		this.defaultModel = defaultModel;
		this.c = c;
		this.filterBox = filterBox;
		this.filterBtn = filterBtn;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(tfEV.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "No environmental variables selected", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		else if(defaultModel.isSelected()) {
			//Predict
			FileParser parser = new FileParser();
			File[] EV = parser.textFieldParser(tfEV);
			Predict_Dist predict = new Predict_Dist(EV, System.getProperty("user.dir").replace('\\', '/') +"/model/model.rds", "sp", c);
			if(!predict.getError()) {
				filterBox.setEnabled(true);
				filterBtn.setEnabled(true);
				ImageDisplayer image = new ImageDisplayer("pictures/prediction" + "sp" + ".png", "sp");
			}
		}
		else {
			if(tfMod.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "No model selected", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			else {
				//Predict
				FileParser parser = new FileParser();
				File[] EV = parser.textFieldParser(tfEV);
				Predict_Dist predict = new Predict_Dist(EV, tfMod.getText().replace('\\', '/'), "sp", c);
				if(!predict.getError()) {
					filterBox.setEnabled(true);
					filterBtn.setEnabled(true);
					ImageDisplayer image = new ImageDisplayer("pictures/prediction" + "sp" + ".png", "sp");
				}
			}
		}
		
	}	
}
