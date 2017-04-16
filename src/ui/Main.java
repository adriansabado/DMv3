package ui;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.rosuda.REngine.Rserve.RConnection;

import listeners.Enable_Own_Model;
import listeners.Select_EV;
import net.miginfocom.swing.MigLayout;
import rBackend.StartConnection;

public class Main {

	private JFrame frame;
	private JPanel spPanel;
	private JPanel spInputs;
	private JCheckBox spDefModCB;
	private JTextField spModTF;
	private JButton spModBtn;
	private JTextField spEVTF;
	private JButton spEVBtn;
	private JButton btnPredict;
	
	private RConnection c;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Main window = new Main();
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public Main() {
		StartConnection newConnection = new StartConnection(c);
		newConnection.startRConnection();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Dipterocarp Modelling Tool");
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		
		JTabbedPane displayPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(displayPane, "cell 0 0,grow");
		
			spPanel = new JPanel();
			displayPane.addTab("Single Year Prediction", null, spPanel, null);
			spPanel.setLayout(new MigLayout("", "[grow]", "[grow][grow]"));
			
				spInputs = new JPanel();
				spPanel.add(spInputs, "cell 0 0,grow");
				spInputs.setLayout(new MigLayout("", "[grow][]", "[][][]"));
					
					spModTF = new JTextField();
					spModTF.setEnabled(false);
					spModTF.setEditable(false);
					spInputs.add(spModTF, "flowx,cell 0 1,growx");
					spModTF.setColumns(10);
					
					spModBtn = new JButton("Input your own model");
					spInputs.add(spModBtn, "cell 0 1,wmin 250");
					spModBtn.setEnabled(false);
					
					btnPredict = new JButton("Predict");
					spInputs.add(btnPredict, "cell 1 1 1 2,growy");
					
					spEVTF = new JTextField();
					spEVTF.setEditable(false);
					spInputs.add(spEVTF, "flowx,cell 0 2,growx");
					spEVTF.setColumns(10);
					
					spEVBtn = new JButton("Input your environmental variables");
					spInputs.add(spEVBtn, "cell 0 2,wmin 250");
					spEVBtn.addActionListener(new Select_EV(spEVTF));
					
					spDefModCB = new JCheckBox("Use default model");
					spInputs.add(spDefModCB, "cell 0 0");
					spDefModCB.addChangeListener(new Enable_Own_Model(spDefModCB, spModBtn));
					
					/*File f = new File("wmodel1.png");
					Desktop dt = Desktop.getDesktop();
					try {
						dt.open(f);
						System.out.println("Done");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Error opening image");
					}*/
	}
}
