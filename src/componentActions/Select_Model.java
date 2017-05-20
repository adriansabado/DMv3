package componentActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Select_Model implements ActionListener {
	private JTextField tf = new JTextField();
	
	public Select_Model(JTextField textfield) {
		tf = textfield;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String choosertitle = "Select your model";
		
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(choosertitle);
	    
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("*.rds", "rds");
	    chooser.setFileFilter(filter);
	    
	    
	    chooser.setAcceptAllFileFilterUsed(false);
	    
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	tf.setText(chooser.getSelectedFile().getAbsolutePath());
	    }
	    else {
	    	JOptionPane.showMessageDialog(null, "No model selected");
	    }
	}
	
}
