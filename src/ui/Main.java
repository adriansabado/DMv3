package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import componentActions.Enable_Own_Model;
import componentActions.MP_Predict;
import componentActions.SP_Predict;
import componentActions.Select_EV;
import componentActions.Select_Model;
import javaBackend.Filter_Results;
import javaBackend.ImageDisplayer;
import javaBackend.Region;
import javaBackend.RegionParser;
import javaBackend.TransformingCanvas;
import net.miginfocom.swing.MigLayout;
import rBackend.RConnector;
import javax.swing.border.EtchedBorder;

public class Main {

	private JFrame frame;
	private JPanel spPanel;
	private JPanel spInputs;
	private JCheckBox spDefModCB;
	private JTextField spModTF;
	private JButton spModBtn;
	private JTextField spEVTF;
	private JButton spEVBtn;
	private JButton spPredict;
	
	private RConnection c;
	private JPanel mpPanel;
	private JPanel mpInputs;
	private JLabel lblInputs;
	private JCheckBox mpDefModCB;
	private JTextField mpModTF;
	private JButton mpModBtn;
	private JLabel lblSelectNumberOf;
	private JSpinner spinner;
	private JPanel mpEVPanel;
	
	private ArrayList<Region> regions;
	
	private JTextField mpYear1EVTF;
	private JTextField mpYear2EVTF;
	
	private ArrayList<JTextField> mpEVTF = new ArrayList<JTextField>();
	private ArrayList<JButton> mpEVBtn = new ArrayList<JButton>();
	
	private int envDataAdded = 2;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JComboBox filterBox;
	private JButton filterBtn;
	private JLabel lblFilterByRegion;
	private JTabbedPane displayPane;
	private JButton mpPredict;
	private JComboBox mpFilterBox;
	private JButton btnDisplay;
	private JPanel graphPanel;
	

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
		RConnector newConnection = new RConnector();
		RegionParser rp = new RegionParser();
		regions = rp.regionParser();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		        try {
					c.shutdown();
					System.out.println("Rserve terminated");
				} catch (RserveException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Rserve didn't shutdown properly. Shut it down manually to deallocate resources", "Notice", JOptionPane.WARNING_MESSAGE);
				}
		    }
		}));
		this.c = newConnection.getRConnection();
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Dipterocarp Modelling Tool");
		frame.setMinimumSize(new Dimension(600, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		
		displayPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(displayPane, "cell 0 0,grow");
		
			spPanel = new JPanel();
			displayPane.addTab("Single Year Prediction", null, spPanel, null);
			spPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
			
				spInputs = new JPanel();
				spPanel.add(spInputs, "cell 0 0,grow");
				spInputs.setLayout(new MigLayout("", "[grow][]", "[][][][10%][][][10%][][grow]"));
					
					spModTF = new JTextField();
					spModTF.setBackground(Color.WHITE);
					spModTF.setEditable(false);
					spInputs.add(spModTF, "flowx,cell 0 1 2 1,growx");
					spModTF.setColumns(10);
					
					spModBtn = new JButton("Input your own model");
					spInputs.add(spModBtn, "cell 0 2 2 1,grow");
					spModBtn.setEnabled(false);
					spModBtn.addActionListener(new Select_Model(spModTF));
					
					spEVTF = new JTextField();
					spEVTF.setBackground(Color.WHITE);
					spEVTF.setEditable(false);
					spInputs.add(spEVTF, "cell 0 4 2 1,growx");
					spEVTF.setColumns(10);
					
					spEVBtn = new JButton("Input layers");
					spInputs.add(spEVBtn, "cell 0 5 2 1,grow");
					spEVBtn.addActionListener(new Select_EV(spEVTF));
					
					spDefModCB = new JCheckBox("Use default model");
					spInputs.add(spDefModCB, "cell 0 0");
					spDefModCB.setSelected(true);
					spDefModCB.addChangeListener(new Enable_Own_Model(spDefModCB, spModBtn));
					
					panel = new JPanel();
					spInputs.add(panel, "cell 0 8 2 1,grow");
					panel.setLayout(new MigLayout("", "[grow][]", "[15%][]"));
					
					lblFilterByRegion = new JLabel("Filter by region:");
					panel.add(lblFilterByRegion, "cell 0 1");
					
					filterBox = new JComboBox();
					filterBox.setEnabled(false);
					filterBox.setModel(new DefaultComboBoxModel(new String[] {"- Whole Philippines -", "Negros Island Region", "National Capital Region", "Cordillera Administrative Region", "I - Ilocos Region", "II - Cagayan Valley", "III - Central Luzon", "IV-A - CALABARZON", "MIMAROPA Region", "V - Bicol", "VI - Western Visayas", "VII - Central Visayas", "VIII - Eastern Visayas", "IX - Zamboanga Peninsula", "X - Northern Mindanao", "XI - Davao Region", "XII - Soccsksargen", "XIII - Caraga", "ARMM"}));
					panel.add(filterBox, "cell 0 2,growx");
					
					filterBtn = new JButton("Filter");
					filterBtn.setEnabled(false);
					panel.add(filterBtn, "cell 1 2");
					filterBtn.addActionListener(new SP_Filter_Results());
					
					spPredict = new JButton("Predict");
					spInputs.add(spPredict, "cell 0 7 2 1,alignx center");
					spPredict.addActionListener(new SP_Predict(spModTF, spEVTF, spDefModCB, c, filterBtn, filterBox));
					
			mpPanel = new JPanel();
			displayPane.addTab("Multi-Year Prediction", null, mpPanel, null);
			mpPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
			
				mpInputs = new JPanel();
				mpPanel.add(mpInputs, "cell 0 0,grow");
				mpInputs.setLayout(new MigLayout("", "[grow][grow]", "[grow][][][][grow][]"));
				
					lblInputs = new JLabel("INPUTS");
					mpInputs.add(lblInputs, "cell 0 0");
					
					graphPanel = new JPanel();
					graphPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
					mpInputs.add(graphPanel, "cell 1 0 1 6,grow");
					
					mpModTF = new JTextField();
					mpModTF.setEditable(false);
					mpInputs.add(mpModTF, "flowx,cell 0 2,growx");
					mpModTF.setColumns(10);
					
					mpModBtn = new JButton("Select model");
					mpModBtn.setEnabled(false);
					mpModBtn.addActionListener(new Select_Model(mpModTF));
					mpInputs.add(mpModBtn, "cell 0 2,wmin 100");
					
					mpDefModCB = new JCheckBox("Use default model");
					mpDefModCB.setSelected(true);
					mpDefModCB.addChangeListener(new Enable_Own_Model(mpDefModCB, mpModBtn));
					mpInputs.add(mpDefModCB, "cell 0 1");
					
					lblSelectNumberOf = new JLabel("Select number of years to predict:");
					mpInputs.add(lblSelectNumberOf, "flowx,cell 0 3");
					
					spinner = new JSpinner();
					spinner.setModel(new SpinnerNumberModel(new Integer(2), new Integer(2), null, new Integer(1)));
					mpInputs.add(spinner, "cell 0 3");
					
					scrollPane = new JScrollPane();
					mpInputs.add(scrollPane, "cell 0 4,grow");
					
					mpEVPanel = new JPanel();
					scrollPane.setViewportView(mpEVPanel);
					mpEVPanel.setLayout(new MigLayout("", "[grow]", "[][]"));
					
					mpYear1EVTF = new JTextField();
					mpEVPanel.add(mpYear1EVTF, "flowx,cell 0 0,growx");
					mpYear1EVTF.setColumns(10);
					mpYear1EVTF.setEditable(false);
					
					JButton mpYear1EVBtn = new JButton("Select layers for year 1");
					mpEVPanel.add(mpYear1EVBtn, "cell 0 0,wmin 200");
					mpYear1EVBtn.addActionListener(new Select_EV(mpYear1EVTF));
					
					mpYear2EVTF = new JTextField();
					mpEVPanel.add(mpYear2EVTF, "flowx,cell 0 1,growx");
					mpYear2EVTF.setColumns(10);
					mpYear2EVTF.setEditable(false);
					
					JButton mpYear2EVBtn = new JButton("Select layers for year 2");
					mpEVPanel.add(mpYear2EVBtn, "cell 0 1,wmin 200");
					mpYear2EVBtn.addActionListener(new Select_EV(mpYear2EVTF));
					
					mpEVTF.add(mpYear1EVTF);
					mpEVTF.add(mpYear2EVTF);
					mpEVBtn.add(mpYear2EVBtn);
					mpEVBtn.add(mpYear1EVBtn);
					
					mpFilterBox = new JComboBox();
					mpFilterBox.setEnabled(false);
					mpInputs.add(mpFilterBox, "flowx,cell 0 5,growx");
					mpFilterBox.setModel(new DefaultComboBoxModel(new String[] {"- Whole Philippines -", "Negros Island Region", "National Capital Region", "Cordillera Administrative Region", "I - Ilocos Region", "II - Cagayan Valley", "III - Central Luzon", "IV-A - CALABARZON", "MIMAROPA Region", "V - Bicol", "VI - Western Visayas", "VII - Central Visayas", "VIII - Eastern Visayas", "IX - Zamboanga Peninsula", "X - Northern Mindanao", "XI - Davao Region", "XII - Soccsksargen", "XIII - Caraga", "ARMM"}));
					
					btnDisplay = new JButton("Display");
					btnDisplay.setEnabled(false);
					mpInputs.add(btnDisplay, "cell 0 5,alignx right");

					mpPredict = new JButton("Predict");
					mpInputs.add(mpPredict, "cell 0 2,wmin 100");
					mpPredict.addActionListener(new MP_Predict(mpModTF, mpEVTF, mpDefModCB, c, mpFilterBox, btnDisplay, graphPanel));
					
					SpinnerListener cl = new SpinnerListener();
					spinner.addChangeListener(cl);
					
					/*File f = new File("wmodel1.png");
					Desktop dt = Desktop.getDesktop();
					try {
						dt.open(f);
						System.out.println("Done");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Error opening image");
					}*/
		//frame.addComponentListener( );
	}
	
	public class SpinnerListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub
			int newVal = (int) spinner.getValue();
			if(newVal > envDataAdded) {
				JTextField newTf = new JTextField();
				newTf.setEditable(false);
				newTf.setColumns(10);
				
				JButton newButt = new JButton("Select layers for year " + Integer.toString(envDataAdded + 1));
				
				mpEVPanel.add(newTf, "flowx, cell 0 " + Integer.toString(envDataAdded) + ", growx");
				mpEVPanel.add(newButt, "wmin 200, cell 0 " + Integer.toString(envDataAdded));
				
				newButt.addActionListener(new Select_EV(newTf));	
				
				envDataAdded++;
				
				mpEVTF.add(newTf);
				mpEVBtn.add(newButt);
				
				//System.out.println(newVal + "\t" + currSpinnerValue + " add " + envDataAdded);
			}
			else if(newVal < envDataAdded) {
				mpEVTF.get(envDataAdded - 1).setVisible(false);
				mpEVBtn.get(envDataAdded - 1).setVisible(false);
				mpEVPanel.remove(mpEVTF.get(envDataAdded - 1));
				mpEVPanel.remove(mpEVBtn.get(envDataAdded - 1));
				
				mpEVTF.remove(envDataAdded - 1);
				mpEVBtn.remove(envDataAdded - 1);
				
				envDataAdded--;
				
				//System.out.println(newVal + "\t" + currSpinnerValue + " remove" + envDataAdded);
			}
			mpEVPanel.validate();
			mpInputs.validate();
			mpPanel.validate();
			
			envDataAdded = newVal;
			scrollPane.getVerticalScrollBar().setValue((int)mpEVPanel.getPreferredSize().getHeight());
		}
	}
	
	public class SP_Filter_Results implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String filename = "sp";
			String region = (String) filterBox.getSelectedItem();
			
			if(region.equals("- Whole Philippines -")) {
				ImageDisplayer display = new ImageDisplayer("pictures/predictionsp.png", "sp");
			}
			else {
				for(int i = 0; i < regions.size(); i++) {
					if(regions.get(i).getRegionName().equals(region)) {
						Filter_Results filter = new Filter_Results(filename, region, regions.get(i).getProvinces(), c);
						break;
					}
				}
			}
		}
	}
}
