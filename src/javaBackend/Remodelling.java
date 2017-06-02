package javaBackend;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Remodelling {
	private RConnection c;
	private JTextField predictors;
	private JTextField datalocs;
	private ArrayList<String> factors;
	private JTextField destTF;

	public Remodelling(RConnection c, JTextField predictors, JTextField datalocs, ArrayList<String> factors, JTextField destTF) {
		this.c = c;
		this.predictors = predictors;
		this.datalocs = datalocs;
		this.factors = factors;
		this.destTF = destTF;
	}

	public void model() {
		FileParser parser = new FileParser();
		File[] toStack = parser.textFieldParser(predictors);
		StringBuilder command = new StringBuilder("");

		command.append("locs <- read.csv(\"" + datalocs.getText().replace('\\', '/') + "\")\n");
		command.append("datalocs <- data.frame(locs$longitude, locs$latitude)\n");

		command.append("files <- as.vector(c(");
		for (int i = 0; i < toStack.length; i++) {
			if (i == 0) {
				command.append('"');
				command.append(toStack[i].getAbsolutePath().replace('\\', '/'));
				command.append('"');
			} else {
				command.append(',');
				command.append('"');
				command.append(toStack[i].getAbsolutePath().replace('\\', '/'));
				command.append('"');
			}
		}
		command.append("))\n");
		command.append("predictors <- stack(files)\n");
		
		if(factors.size() == 0) command.append("model <- maxent(predictors, datalocs");
		else {
			command.append("model <- maxent(predictors, datalocs,factors = ");
		
			for(int i = 0; i < factors.size(); i++) {
				if(i == 0) {
					command.append("\"");
					command.append(factors.get(i));
					command.append("\"");
				}
				else {
					command.append(", \"");
					command.append(factors.get(i));
					command.append("\"");
				}
			}
		}
		command.append(")\n");
		command.append("saveRDS(model, \"" + destTF.getText().replace('\\', '/') + "\")\n");
		
		command.append("png(\"" + System.getProperty("user.dir").replace('\\', '/') + "/pictures/weights.png\")\n");
        command.append("plot(model, xlab = 'Variable Contribution', ylab = 'Variable')\n");
        command.append("dev.off()\n");
        
		System.out.println(command.toString());
		try {
			c.eval(command.toString());
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Error saving the model. Recheck destination if valid", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
