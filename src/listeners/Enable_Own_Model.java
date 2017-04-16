package listeners;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Enable_Own_Model implements ChangeListener{
	private JCheckBox chckbx;
	private JButton btn;
	
	
	public Enable_Own_Model(JCheckBox chckbx, JButton btn) {
		this.chckbx = chckbx;
		this.btn = btn;
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		if(!chckbx.isSelected()) {
			btn.setEnabled(true);
		}
		else {
			btn.setEnabled(false);
		}
	}
	
	
}
