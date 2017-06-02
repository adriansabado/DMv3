package javaBackend;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Filter_Results {
	
	public Filter_Results(String filename, String region, ArrayList<String> provinces, RConnection c) {
		StringBuilder command = new StringBuilder("");
		
		command.append("filter <- ph[");
		
		for(int i = 0; i < provinces.size(); i++) {
			if(i == 0) {
				command.append("ph$NAME_1 == \"" + provinces.get(i) + "\"");
			}
			else {
				command.append(" | ph$NAME_1 == \"" + provinces.get(i) + "\"");
			}
		}
		
		command.append(",]");
		try {
			System.out.println(command.toString());
			c.eval(command.toString());
			
			command = new StringBuilder("");
			command.append("cr <- crop(prediction" + filename + ", extent(filter), snap = \"out\")");
			command.append("\n");
			command.append("fr <- rasterize(filter, cr)");
			command.append("\n");
			command.append("filtersp <- mask(cr, fr)");
			command.append("\n");
			
			command.append("png(\"" + System.getProperty("user.dir").replace('\\', '/') + "/pictures/filter"+ filename +".png\")\n");
            command.append("plot(filter"+ filename +", xlab = 'Longitude', ylab = 'Latitude')\n");
            command.append("plot(filter, add = T)\n");
            command.append("dev.off()\n");
			
			c.eval(command.toString());
			
			ImageDisplayer image = new ImageDisplayer("pictures/filter" + filename + ".png", filename);
		} catch (RserveException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error", "There is an error in project file - assets/regions.txt", JOptionPane.ERROR_MESSAGE);
		}
		
		System.out.println(command.toString());
	}
	
	public static void main(String[] args) {
//		ArrayList<String> p = new ArrayList<String>();
//		p.add("Prov 1");
//		p.add("Prov 2");
//		String region = "Test Region";
//		String filename = "sp";
//		
//		Filter_Results test = new Filter_Results(filename, region, p);
	}
	
}
