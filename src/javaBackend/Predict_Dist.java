package javaBackend;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Predict_Dist {
	private boolean error;
	private RConnection c;
	
	public boolean getError() {
		return this.error;
	}
	
	public void getGraph(int numberOfYears, RConnection c) {
		StringBuilder command = new StringBuilder("");
		command.append("graph <- vector(mode = \"numeric\", length = 0)\n");
		for(int i = 0; i < numberOfYears; i++) {
			command.append("graph <- append(graph, mean(as.vector(predictionmp"+ i +")[!is.na(as.vector(predictionmp"+ i +"))]))\n");
		}
		command.append("png(\"" + System.getProperty("user.dir").replace('\\', '/') + "/pictures/graph.png\")\n");
        command.append("plot(seq(1,length(graph)), graph, xlab = 'Year', ylab = 'Likelihood')\n");
        command.append("lines(seq(1,length(graph)), graph)\n");
        command.append("dev.off()\n");
        try {
        	System.out.println(command.toString());
			c.eval(command.toString());
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Predict_Dist(File[] EV, String pathMod, String filename, RConnection c) {
		this.c = c;
		error = false;
		
		StringBuilder command = new StringBuilder("");
		command.append("model <- readRDS(\"" + pathMod + "\")\n");
		
		try {
			System.out.println(command.toString());
			c.eval(command.toString());
			command = new StringBuilder("");
		} catch (RserveException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Invalid model", "Warning", JOptionPane.WARNING_MESSAGE);
			error = true;
		}
		if(error){}
		else {
			command.append("files <- as.vector(c(");
			for(int i = 0; i < EV.length; i++) {
	        	if(i == 0) {
	        		command.append('"');
		        	command.append(EV[i].getAbsolutePath().replace('\\', '/'));
		        	command.append('"');
	        	}
	        	else {
	        		command.append(',');
	        		command.append('"');
		        	command.append(EV[i].getAbsolutePath().replace('\\', '/'));
		        	command.append('"');
	        	}
	        }
			command.append("))\n");
			command.append("predictors <- stack(files)\n");
			command.append("prediction" + filename + " <- predict(model, predictors, progress = 'window')\n");
			
			command.append("cr <- crop(prediction" + filename + ", extent(ph), snap = \"out\")\n");
			command.append("fr <- rasterize(ph, cr)\n");
			command.append("prediction" + filename + "<- mask(x=cr, mask=fr)\n");
			
			command.append("png(\"" + System.getProperty("user.dir").replace('\\', '/') + "/pictures/prediction"+ filename +".png\")\n");
            command.append("plot(prediction"+ filename +", xlab = 'Longitude', ylab = 'Latitude')\n");
            command.append("plot(ph, add = T)\n");
            command.append("dev.off()\n");
            
//            command.append("png(\"" + System.getProperty("user.dir").replace('\\', '/') + "/pictures/weights"+ filename +".png\")\n");
//            command.append("plot(model)\n");
//            command.append("dev.off()\n");
            
            System.out.println(command.toString());
            try {
				c.eval(command.toString());
			} catch (RserveException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error while predicting", "Error", JOptionPane.ERROR_MESSAGE);
			}
            
            
		}
	}
}
