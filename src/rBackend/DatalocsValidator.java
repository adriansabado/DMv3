package rBackend;

import java.io.File;

import javax.swing.JOptionPane;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class DatalocsValidator {
	private File datalocs;
	private RConnection c;
	
	public DatalocsValidator(File datalocs, RConnection c) {
		this.datalocs = datalocs;
		this.c = c;
	}

	public boolean areDatalocsValid() {
		StringBuilder command;
		
		try {
			command = new StringBuilder("");
			
			command.append("locs <- read.csv(\"" + datalocs.getAbsolutePath().replace('\\', '/') + "\")\n");
			command.append("datalocs <- data.frame(locs$longitude, locs$latitude)\n");
			
			c.eval(command.toString());
		}
		catch(RserveException rs) {
			JOptionPane.showMessageDialog(null, "There is a problem with input data localities. Please recheck the file.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
}
