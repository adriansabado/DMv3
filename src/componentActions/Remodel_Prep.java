package componentActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.rosuda.REngine.Rserve.RConnection;

import javaBackend.FileParser;
import javaBackend.Remodelling;
import rBackend.DatalocsValidator;
import rBackend.EnviVarValidator;

public class Remodel_Prep implements ActionListener {
	private RConnection c;
	private JTextField predictors;
	private JTextField datalocs;
	private JTextField factors;
	private JTextField destTF;
	private JButton showContrib;
		
	public Remodel_Prep(RConnection c, JTextField predictors, JTextField datalocs, JTextField factors, JTextField destTF, JButton showContrib) {
		this.c = c;
		this.predictors = predictors;
		this.datalocs = datalocs;
		this.factors = factors;
		this.destTF = destTF;
		this.showContrib = showContrib;
	}
	public static void main(String[] args) {
		String test = "12345";
		System.out.println(test.indexOf("a"));
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		boolean predictorError = false;
		boolean locsError = false;
		boolean factorError = false;
		
		FileParser parser = new FileParser();
		File[] filePredictors = parser.textFieldParser(predictors);
		
		EnviVarValidator evValidator = new EnviVarValidator(c, filePredictors);
		predictorError = !evValidator.areEnviVarValid();
		
		DatalocsValidator dlValidator = new DatalocsValidator(new File(datalocs.getText()), c);
		locsError = !dlValidator.areDatalocsValid();
		
		StringTokenizer st = new StringTokenizer(factors.getText(), ",");
		ArrayList<String> factorString = new ArrayList<String>();
		while(st.hasMoreTokens()) {
			factorString.add(st.nextToken());
		}
		for(int i = 0; i < factorString.size(); i++) {
			boolean hasMatch = false; 
			for(int j = 0; j < filePredictors.length; j++) {
				if(filePredictors[j].getAbsolutePath().indexOf(factorString.get(i)) != -1) hasMatch = true;
			}
			if(!hasMatch) {
				factorError = true;
				break;
			}
		}
		
		if(factorError) {
			JOptionPane.showMessageDialog(null, "A factor doesn't match any of the predictors", "Error", JOptionPane.ERROR_MESSAGE);
		}
		System.out.println(predictorError + "\t" + locsError + "\t" + factorError);
		if(!predictorError && !locsError && !factorError) {
			Remodelling model = new Remodelling(c, predictors, datalocs, factorString, destTF);
			model.model();
			showContrib.setEnabled(true);
			System.out.println("Yey?");
		}
	}
}
