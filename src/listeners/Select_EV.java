package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Select_EV implements ActionListener{

	private JTextField tf;
	
	public Select_EV(JTextField tf) {
		this.tf = tf;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String choosertitle = "Select environmental variables";
		
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(choosertitle);
	    chooser.setMultiSelectionEnabled(true);
	    
	    chooser.setAcceptAllFileFilterUsed(false);
	    
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
	    	StringBuilder files = new StringBuilder();
	    	tf.setText(files.toString());
	    }
	    else {
	    	JOptionPane.showMessageDialog(null, "No environmental variables selected");
	    }
	}

}
