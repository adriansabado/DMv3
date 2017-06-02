package rBackend;

import java.io.File;

import javax.swing.JOptionPane;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class EnviVarValidator {
	private RConnection c;
	private File[] enviVars;
	
	public EnviVarValidator(RConnection c, File[] enviVars) {
		this.c = c;
		this.enviVars = enviVars;
	}
	
	public boolean areEnviVarValid() {
		StringBuilder command;
		for(int i = 0; i < enviVars.length; i++) {
			try {
				command = new StringBuilder("stack(\"" + enviVars[i].getAbsolutePath() + "\")");
				c.eval(command.toString().replace('\\', '/'));
			}
			catch(RserveException rs) {
				JOptionPane.showMessageDialog(null, "Invalid file: " + enviVars[i].getName(), "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		try {
			command = new StringBuilder("files <- as.vector(c(");
	        
	        for(int i = 0; i < enviVars.length; i++) {
	        	if(i == 0) {
	        		command.append('"');
		        	command.append(enviVars[i].getAbsolutePath().replace('\\', '/'));
		        	command.append('"');
	        	}
	        	else {
	        		command.append(',');
	        		command.append('"');
		        	command.append(enviVars[i].getAbsolutePath().replace('\\', '/'));
		        	command.append('"');
	        	}
	        }
	        command.append("))\n");
	        command.append("stack(files)");
			c.eval(command.toString());
		}
		catch(RserveException rs) {
			JOptionPane.showMessageDialog(null, "Error combining the environmental variables. Check if extent of each file are same", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	public static void main(String[] args) throws RserveException {
		RConnector s = new RConnector();
		RConnection c = s.getRConnection();
		File[] files = new File[2];
		files[0] = new File("C:/Users/adria/Desktop/test.png");
		files[1] = new File("C:/Users/adria/Desktop/test.tif");
		
		EnviVarValidator test = new EnviVarValidator(c, files);
		System.out.println(test.areEnviVarValid());
	}
}
