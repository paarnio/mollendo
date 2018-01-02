package siima.app.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import siima.app.control.MainAppController;
import siima.app.model.TriptychContent;

//X import siima.app.control.MainAppController;
//X import siima.app.model.tree.ElementModel;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JEditorPane;


public class MainFrame extends JFrame implements ActionListener { // TreeSelectionListener
																	// {
	public String eraProjectHomeDirectory = ".";
	public String latestOpenedFolder = ".";
	private final static String newline="\n";
	//private JPanel contentPane;
	private JFileChooser fileChooser;
	private JSplitPane m_contentPane;
	private JMenuItem mntmOpen;
	private JMenuItem mntmOpenTaskFlow;
	private JMenu newProjectVersionSubmenu;

	private File mainOpenFile;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveResultsAsXML;
	private JMenuItem mntmShowResults;
	
	private JButton btnInvokeButton;

	public MainAppController appControl;
	private JScrollPane hierarchyTreeScrollPane;
	private JScrollPane hierarchyTreeScrollPane2;
	private JScrollPane hierarchyTreeScrollPane3;
	private JScrollPane hierarchyTreeScrollPane4;
	private JScrollPane hierarchyTreeScrollPane5;
	private JScrollPane consoleScrollPane;
	private JScrollPane resultScrollPane;
	private JScrollPane spinCommandFileScrollPane;
	private JScrollPane svgGraphicsScrollPane1;
	private JScrollPane svgGraphicsScrollPane2;
	private JMenuItem mntmGenerateJmonkey;
	private JTextArea bottomLeftTextArea;
	private JTextArea txtrConsoleOutput;
	private JTextArea txtrResultOutput;
	private JTextArea txtrSpinCommandFileOutput;
	
	private JTextArea rightTopLeftTextArea;
	private JTextArea rightTopRightTextArea;
	private JTextArea rightmostBottomTextArea;
	
	private JMenuItem mntmLoadRules;
	private JMenuItem mntmInvokeReasoner;
	private JMenuItem mntmSaveResultModels;
	private JMenuItem mntmCaexToAsp;
	private JTabbedPane tabbedPane;
	private JTabbedPane bottomRightTabbedPane;
	private JTabbedPane svgGraphicsTabbedPane;
	private JMenuItem mntmConfigureSchema;
	
	private JMenuItem mntmExit;
	private JMenuItem mntmInvoke;
	private JMenuItem mntmCompareSol;
	private JMenuItem mntmSingleTestCase;
	
	private JMenuItem mntmInvokeTransform;
	private JMenuItem mntmSetTransformContext;
	private JMenuItem mntmGenOntologyModel;
	private JMenuItem mntmNewProject;
	private JMenuItem mntmSaveProjectAs;
	private JMenuItem mntmSaveProject;
	private JMenuItem mntmOpenProject;
	private JMenuItem mntmAspSolver;
	private JMenuItem mntmSaveOntologyModel;
	private JMenuItem mntmMergeModels;
	private JMenuItem mntmLoadOntologyModels;
	private JMenuItem mntmLoadSpinCommands;
	private JMenuItem mntmInvokeSpinCommands;
	private JMenuItem mntmSaveSpinCommands;
	private JMenuItem mntmClearPartials;
	private JMenuItem mntmClearCombined;
	private JMenuItem mntmClearMerged;
	private JMenuItem mntmOpenDiagram;
	// private JTree tree;
	private JRadioButtonMenuItem versionMenuItem1;
	private JRadioButtonMenuItem versionMenuItem2;
	
	private JRadioButtonMenuItem rbMenuItem1;
	private JRadioButtonMenuItem rbMenuItem2;
	private JRadioButtonMenuItem rbMenuItem3;
	private JRadioButtonMenuItem rbMenuItem4;
	private JRadioButtonMenuItem rbMenuItem5;
	private JRadioButtonMenuItem rbMenuItem6;
	
	//For Search CSMCommand block
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	//For found CSMCommand block
	private JTextField textField11;
	private JTextField textField12;
	private JTextField textField13;
	private JTextField textField14;
	private JTextField textField15;
	private JTextField textField16;
	
	private JButton btnSearchCommandButton;
	private  JTextArea oneJsonCommandTextArea;
	private JButton btnUpdateCSMCommandButton;
	private JButton btnSequenceRunButton;
	private JTextField idxsequencetext; 
	private Map<String,JTextField> dataDisplayMap;
	//protected JLabel actionLabel;
	
	// The SVG canvas.
    //protected JSVGCanvas svgCanvas;
    // for html
    protected JEditorPane jEditorPane;
    
    //NEW
    StudentTablePanel studentTablePanel;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 600); //(100, 100, 450, 300);
		setTitle("XML-Checker");

		this.appControl = new MainAppController(this); //(this);

		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(new File("./data"));

		/*
		 * MenuBar
		 * 
		 */

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmOpen = new JMenuItem("Open Project Excel");
		mntmOpen.addActionListener(this); // See: method											// actionPerformed(ActionEvent arg0)
		mnFile.add(mntmOpen);
		mntmOpen.setEnabled(true);	
		
		//TODO: Do not use this (might corrupt the excel)
		mntmSave = new JMenuItem("Save and Close Project Excel ");
		mntmSave.addActionListener(this);// See: method											// actionPerformed(ActionEvent arg0)
		mnFile.add(mntmSave);
		mntmSave.setEnabled(false);
		
		mnFile.addSeparator();

		mntmOpenTaskFlow = new JMenuItem("Open TaskFlow File...");
		mntmOpenTaskFlow.addActionListener(this);
		mnFile.add(mntmOpenTaskFlow);
		
		/*
		//NEW SUBMENU
		newProjectVersionSubmenu = new JMenu("New Project Version");
		newProjectVersionSubmenu.setEnabled(false);
		// Radio buttons: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuLookDemoProject/src/components/MenuLookDemo.java
		ButtonGroup caexVersionGroup = new ButtonGroup();
		
		versionMenuItem1 = new JRadioButtonMenuItem("2.15");
		versionMenuItem1.setSelected(false);
        //versionMenuItem1.setMnemonic(KeyEvent.VK_R);
		versionMenuItem1.addActionListener(this);       
        caexVersionGroup.add(versionMenuItem1);
        newProjectVersionSubmenu.add(versionMenuItem1);
 
        versionMenuItem2 = new JRadioButtonMenuItem("3.0");
		versionMenuItem2.setSelected(false);
        //versionMenuItem2.setMnemonic(KeyEvent.VK_R);
		versionMenuItem2.addActionListener(this);       
        caexVersionGroup.add(versionMenuItem2);
        newProjectVersionSubmenu.add(versionMenuItem2);

		mnFile.add(newProjectVersionSubmenu);

		
		mntmSaveResults = new JMenuItem("Save Results");
		mntmSaveResults.addActionListener(this);
		mnFile.add(mntmSaveResults);
		mntmSaveResults.setEnabled(false);
		*/
		mntmSaveResultsAsXML = new JMenuItem("Save Results As XML...");
		mntmSaveResultsAsXML.addActionListener(this);
		mnFile.add(mntmSaveResultsAsXML);
		mntmSaveResultsAsXML.setEnabled(true);
	
		/*
		mnFile.addSeparator();
		
		mntmConfigureSchema = new JMenuItem("Configure Schema...");
		mntmConfigureSchema.addActionListener(this);
		mnFile.add(mntmConfigureSchema);
		mntmConfigureSchema.setEnabled(false);
		
		mnFile.addSeparator();
		*/
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//appControl.closeProjectExcel();
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		/*
		 * View Menu
		 * 
		 */
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);

		mntmShowResults = new JMenuItem("Display Student info");
		mntmShowResults.addActionListener(this); // See: method											// actionPerformed(ActionEvent arg0)
		mnView.add(mntmShowResults);
		mntmShowResults.setEnabled(false);	
		
		
		/*
		 * Processor Menu
		 * 
		 */
		
		JMenu mnPros = new JMenu("Processor");
		menuBar.add(mnPros);

		mntmInvoke = new JMenuItem("Invoke Processor");
		mntmInvoke.addActionListener(this); // See: method											// actionPerformed(ActionEvent arg0)
		mnPros.add(mntmInvoke);
		mntmInvoke.setEnabled(false);	
		
		/*
		 * Student Menu
		 * TODO:
		 */
		
		JMenu mnStudent = new JMenu("Student");
		menuBar.add(mnStudent);

		mntmCompareSol = new JMenuItem("Compare Solution");
		mntmCompareSol.addActionListener(this); // See: method											// actionPerformed(ActionEvent arg0)
		mnStudent.add(mntmCompareSol);
		mntmCompareSol.setEnabled(true);	
		
		mntmSingleTestCase = new JMenuItem("Run TestCase For Student");
		mntmSingleTestCase.addActionListener(this); // See: method											// actionPerformed(ActionEvent arg0)
		mnStudent.add(mntmSingleTestCase);
		mntmSingleTestCase.setEnabled(true);
		
		
		/* ************************
		 * 
		 *     Main Window
		 *     
		 *     | TopLeft    | RightTopLeft  | RightTopRight | Rightmost 		|
		 *     |			|				|				|					|
		 *     ---------------------------------------------|-------------------|
		 *     | BottomLeft | RightBottom					|RightmostBottom	|
		 *     |			|								|					|
		 *     ----------------------------------------------------------
		 *     
		 *     
		 * 
		 * *************************/

		/*----Main contentPane ----*/
		m_contentPane = new JSplitPane();
		m_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(m_contentPane);

		/*----Left Side JSplitPane----*/
		JSplitPane LeftVerticalSplitPane = new JSplitPane();
		LeftVerticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		m_contentPane.setLeftComponent(LeftVerticalSplitPane);

		/*----Top Left Panel ----*/

		JPanel topLeftPanel = new JPanel();
		LeftVerticalSplitPane.setLeftComponent(topLeftPanel);
		GridBagLayout gbl_topLeftPanel = new GridBagLayout();
		gbl_topLeftPanel.columnWidths = new int[] { 250, 0 };
		gbl_topLeftPanel.rowHeights = new int[] { 10, 300, 0, 0 };//{ 50, 200, 0, 0 };
		gbl_topLeftPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_topLeftPanel.rowWeights = new double[] { 0.5, 1.0, 0.5, Double.MIN_VALUE };//{ 1.0, 1.0, 1.0, Double.MIN_VALUE };
		topLeftPanel.setLayout(gbl_topLeftPanel);

		/* -- New JTabbedPane with 1 tab for taskflow hierarchy jtree */

		tabbedPane = new JTabbedPane();
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		/*
		 * tabbedPane.addChangeListener(new ChangeListener() { public void
		 * stateChanged(ChangeEvent e) {
		 * System.out.println("TEEEST:Tab Changed: " +
		 * tabbedPane.getSelectedIndex()); } });
		 */
		hierarchyTreeScrollPane = new JScrollPane();
		tabbedPane.insertTab("TaskFlow", null, hierarchyTreeScrollPane, "InternalElements", 0);
		/* OLD
		hierarchyTreeScrollPane2 = new JScrollPane();
		tabbedPane.insertTab("SystemUnitCL", null, hierarchyTreeScrollPane2, "SystemUnitClasses", 1);
		
		hierarchyTreeScrollPane3 = new JScrollPane();
		tabbedPane.insertTab("RoleCL", null, hierarchyTreeScrollPane3, "RoleClasses", 2);

		hierarchyTreeScrollPane4 = new JScrollPane();
		tabbedPane.insertTab("InterfaceCL", null, hierarchyTreeScrollPane4, "InterfaceClasses", 3);
		
		hierarchyTreeScrollPane5 = new JScrollPane();
		tabbedPane.insertTab("AttributeTL", null, hierarchyTreeScrollPane5, "AttributeTypes", 4);
		*/
		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		topLeftPanel.add(tabbedPane, gbc_tabbedPane);

		/*----Bottom Left Panel ----*/
		JPanel bottomLeftPanel = new JPanel();
		LeftVerticalSplitPane.setRightComponent(bottomLeftPanel);
		GridBagLayout gbl_bottomLeftPanel = new GridBagLayout();
		gbl_bottomLeftPanel.columnWidths = new int[] { 250, 0 };
		gbl_bottomLeftPanel.rowHeights = new int[] { 100, 20, 0 };
		gbl_bottomLeftPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_bottomLeftPanel.rowWeights = new double[] { 1.0, 0.2, Double.MIN_VALUE };
		bottomLeftPanel.setLayout(gbl_bottomLeftPanel);

		JScrollPane bottomLeftScrollPane = new JScrollPane();
		GridBagConstraints gbc_bottomLeftScrollPane = new GridBagConstraints();
		gbc_bottomLeftScrollPane.fill = GridBagConstraints.BOTH;
		gbc_bottomLeftScrollPane.gridx = 0;
		gbc_bottomLeftScrollPane.gridy = 0;
		bottomLeftPanel.add(bottomLeftScrollPane, gbc_bottomLeftScrollPane);

		bottomLeftTextArea = new JTextArea();
		bottomLeftTextArea.setRows(30);
		bottomLeftTextArea.setColumns(100);
		bottomLeftTextArea.setText("----------bottomLeftTextArea---------\n");
		/*
		 * GridBagConstraints gbc_bottomLeftTextArea = new GridBagConstraints();
		 * gbc_bottomLeftTextArea.insets = new Insets(0, 0, 5, 0);
		 * gbc_bottomLeftTextArea.fill = GridBagConstraints.BOTH;
		 * gbc_bottomLeftTextArea.gridx = 1; gbc_bottomLeftTextArea.gridy = 0;
		 */
		// bottomLeftPanel.add(bottomLeftTextArea, gbc_bottomLeftTextArea);

		bottomLeftScrollPane.setViewportView(bottomLeftTextArea);

		JPanel buttonPanel = new JPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.gridx = 0;
		gbc_buttonPanel.gridy = 1;
		bottomLeftPanel.add(buttonPanel, gbc_buttonPanel);

		JButton btnShowDetailsButton = new JButton("Select");
		btnShowDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//int tabnumber = tabbedPane.getSelectedIndex();
				String info = appControl.getSelectedElementInfo(); 			
				bottomLeftTextArea.setText(info);
				boolean runEnabled = appControl.runConditions();
				if(runEnabled) btnInvokeButton.setEnabled(true);
			}
		});
		buttonPanel.add(btnShowDetailsButton);
		
		btnInvokeButton = new JButton("Run Task");
		btnInvokeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String info = appControl.getSelectedElementInfo(); 			
				bottomLeftTextArea.setText(info + " Checking Submits Started...");
				btnInvokeButton.setEnabled(false);
				appControl.invokeCheckingProcess();
			}
		});
		buttonPanel.add(btnInvokeButton);
		btnInvokeButton.setEnabled(false);
		
		
		/*******
		 * 
		 * NEW: ONE MORE MAIN SPLIT IN HORIZONTAL
		 * 														   NEW
		 * => main window divided into three parts | LEFT  | RIGHT  | RIGHTMOST  |
		 * 
		 */
		/*----Right Side horizontal JSplitPane----*/
		JSplitPane rightHorizontalSplitPane = new JSplitPane();
		rightHorizontalSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		m_contentPane.setRightComponent(rightHorizontalSplitPane);

		/*----Right Side JSplitPane----*/
		JSplitPane rightVerticalSplitPane = new JSplitPane();
		rightVerticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		//X m_contentPane.setRightComponent(rightVerticalSplitPane);
		rightHorizontalSplitPane.setLeftComponent(rightVerticalSplitPane);
		
		/* ===================================== 
		 * 			Right Top side 
		 * ===================================== */
		
		/*----Right Top Side JSplitPane----*/		
		JSplitPane rightTopVerticalSplitPane = new JSplitPane();
		rightTopVerticalSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);		
		rightVerticalSplitPane.setLeftComponent(rightTopVerticalSplitPane);
		
		/* ==================== 
		 * Right top_left_panel 
		 * ==================== */
		JPanel rightTopLeftPanel = new JPanel();
		rightTopVerticalSplitPane.setLeftComponent(rightTopLeftPanel);
		
		GridBagLayout gbl_rightTopLeftPanel = new GridBagLayout();
		gbl_rightTopLeftPanel.columnWidths = new int[] { 250, 0 };
		gbl_rightTopLeftPanel.rowHeights = new int[] { 300, 0 };//{ 50, 200, 0, 0 };
		gbl_rightTopLeftPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_rightTopLeftPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };//{ 1.0, 1.0, 1.0, Double.MIN_VALUE };
		rightTopLeftPanel.setLayout(gbl_rightTopLeftPanel);

		JScrollPane rightTopLeftScrollPane = new JScrollPane();
		GridBagConstraints gbc_rightTopLeftScrollPane = new GridBagConstraints();
		gbc_rightTopLeftScrollPane.fill = GridBagConstraints.BOTH;
		gbc_rightTopLeftScrollPane.gridx = 0;
		gbc_rightTopLeftScrollPane.gridy = 0;
		rightTopLeftPanel.add(rightTopLeftScrollPane, gbc_rightTopLeftScrollPane);

		rightTopLeftTextArea = new JTextArea();
		rightTopLeftTextArea.setRows(30);
		rightTopLeftTextArea.setColumns(250);
		rightTopLeftTextArea.setLineWrap(false); //(true);
		rightTopLeftTextArea.setText("-------------rightTopLeftTextArea-------------\n");
		
		rightTopLeftScrollPane.setViewportView(rightTopLeftTextArea);
	
		
		/* =============================== 
		 * 		Right top_right_panel 
		 * =============================== */
		JPanel rightTopRightPanel = new JPanel();
		rightTopVerticalSplitPane.setRightComponent(rightTopRightPanel);
		
		GridBagLayout gbl_right_top_right_panel = new GridBagLayout();
		gbl_right_top_right_panel.columnWidths = new int[] { 250, 0 };
		gbl_right_top_right_panel.rowHeights = new int[] { 300, 0 };//{ 50, 200, 0, 0 };
		gbl_right_top_right_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_right_top_right_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		rightTopRightPanel.setLayout(gbl_right_top_right_panel);
		
	
		JScrollPane rightTopRightScrollPane = new JScrollPane();
		GridBagConstraints gbc_rightTopRightScrollPane = new GridBagConstraints();
		gbc_rightTopRightScrollPane.fill = GridBagConstraints.BOTH;
		gbc_rightTopRightScrollPane.gridx = 0;
		gbc_rightTopRightScrollPane.gridy = 0;
		rightTopRightPanel.add(rightTopRightScrollPane, gbc_rightTopRightScrollPane);

		rightTopRightTextArea = new JTextArea();
		rightTopRightTextArea.setRows(30);
		rightTopRightTextArea.setColumns(250);
		rightTopRightTextArea.setLineWrap(false);//(true);
		rightTopRightTextArea.setText("-------------rightTopRightTextArea-------------\n");
		
		rightTopRightScrollPane.setViewportView(rightTopRightTextArea);
		
		/* ================================= 
		 * 			Right Bottom side 
		 * ================================== */
        
		/* ==== Right bottom_right_panel ==== */
		JPanel bottom_right_panel = new JPanel();
		rightVerticalSplitPane.setRightComponent(bottom_right_panel);
		GridBagLayout gbl_bottom_right_panel = new GridBagLayout();
		gbl_bottom_right_panel.columnWidths = new int[] {10, 500, 10, 0};
		gbl_bottom_right_panel.rowHeights = new int[] {5, 20, 5, 5};
		gbl_bottom_right_panel.columnWeights = new double[]{0.0, 1.0, 0.1, Double.MIN_VALUE};
		gbl_bottom_right_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		bottom_right_panel.setLayout(gbl_bottom_right_panel);
		
		/* -- New JTabbedPane for bottom_right_panel */

		bottomRightTabbedPane = new JTabbedPane();
		GridBagConstraints gbc_bottomRightTabbedPane = new GridBagConstraints();
		gbc_bottomRightTabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_bottomRightTabbedPane.fill = GridBagConstraints.BOTH;
		gbc_bottomRightTabbedPane.gridx = 1;
		gbc_bottomRightTabbedPane.gridy = 1;
		
		// The following line enables to use scrolling tabs.
		bottomRightTabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		bottom_right_panel.add(bottomRightTabbedPane, gbc_bottomRightTabbedPane);
		
		consoleScrollPane = new JScrollPane();
		bottomRightTabbedPane.insertTab("Console", null, consoleScrollPane, "Console", 0);
		
		txtrConsoleOutput = new JTextArea();
		txtrConsoleOutput.setRows(20);
		txtrConsoleOutput.setColumns(620);
		txtrConsoleOutput.setText("--- CONSOLE LOG ---");
		consoleScrollPane.setViewportView(txtrConsoleOutput);
		
		resultScrollPane = new JScrollPane();
		bottomRightTabbedPane.insertTab("Result", null, resultScrollPane, "Result", 1);
		
		txtrResultOutput = new JTextArea();
		txtrResultOutput.setRows(1000);
		txtrResultOutput.setColumns(620);
		txtrResultOutput.setLineWrap(true);
		txtrResultOutput.setText("--- RESULTS ---"); //"--- RESULTS ---"
		resultScrollPane.setViewportView(txtrResultOutput);
		
		/*
		spinCommandFileScrollPane = new JScrollPane();
		bottomRightTabbedPane.insertTab("SpinCommands", null, spinCommandFileScrollPane, "SpinCommands", 2);
		
		txtrSpinCommandFileOutput = new JTextArea();
		txtrSpinCommandFileOutput.setRows(1000);
		txtrSpinCommandFileOutput.setColumns(600);
		txtrSpinCommandFileOutput.setLineWrap(true);
		txtrSpinCommandFileOutput.setText(""); 
		spinCommandFileScrollPane.setViewportView(txtrSpinCommandFileOutput);
		*/
		
		
		/* ===================================== 
		 * 			Rightmost side 
		 * ===================================== */
		
		/*----Rightmost Side vertical JSplitPane----*/
		JSplitPane RightmostSideVerticalSplitPane = new JSplitPane();
		RightmostSideVerticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		rightHorizontalSplitPane.setRightComponent(RightmostSideVerticalSplitPane);
			
		/*JPanel rightmostTopPanel = new JPanel();		
		RightmostSideVerticalSplitPane.setLeftComponent(rightmostTopPanel);		
		GridBagLayout gbl_rightmostTopPanel = new GridBagLayout();
		gbl_rightmostTopPanel.columnWidths = new int[] { 300, 0 };
		gbl_rightmostTopPanel.rowHeights = new int[] { 300, 0 };//{ 50, 200, 0, 0 };
		gbl_rightmostTopPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_rightmostTopPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		rightmostTopPanel.setLayout(gbl_rightmostTopPanel);
		*/
		this.studentTablePanel = new StudentTablePanel();
		RightmostSideVerticalSplitPane.setLeftComponent(studentTablePanel);	
		
		/*
		 *  Rightmost Bottom
		 *  TODO:
		 */
		
		JPanel rightmostBottomPanel = new JPanel();
		RightmostSideVerticalSplitPane.setRightComponent(rightmostBottomPanel);
		GridBagLayout gbl_rightmostBottomPanel = new GridBagLayout();
		gbl_rightmostBottomPanel.columnWidths = new int[] { 250, 0 };
		gbl_rightmostBottomPanel.rowHeights = new int[] { 100, 0, 0 };
		gbl_rightmostBottomPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_rightmostBottomPanel.rowWeights = new double[] { 1.0, 0.2, Double.MIN_VALUE };
		rightmostBottomPanel.setLayout(gbl_rightmostBottomPanel);

		JScrollPane rightmostBottomScrollPane = new JScrollPane();
		GridBagConstraints gbc_rightmostBottomScrollPane = new GridBagConstraints();
		gbc_rightmostBottomScrollPane.fill = GridBagConstraints.BOTH;
		gbc_rightmostBottomScrollPane.gridx = 0;
		gbc_rightmostBottomScrollPane.gridy = 0;
		rightmostBottomPanel.add(rightmostBottomScrollPane, gbc_rightmostBottomScrollPane);

		rightmostBottomTextArea = new JTextArea();
		rightmostBottomTextArea.setRows(30);
		rightmostBottomTextArea.setColumns(100);
		rightmostBottomTextArea.setText("----------rightmostBottomTextArea---------\n");
		

		rightmostBottomScrollPane.setViewportView(rightmostBottomTextArea);

		JPanel rightmostBottomButtonPanel = new JPanel();
		GridBagConstraints gbc_rightmostBottomButtonPanel = new GridBagConstraints();
		gbc_rightmostBottomButtonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_rightmostBottomButtonPanel.fill = GridBagConstraints.BOTH;
		gbc_rightmostBottomButtonPanel.gridx = 0;
		gbc_rightmostBottomButtonPanel.gridy = 1;
		rightmostBottomPanel.add(rightmostBottomButtonPanel, gbc_rightmostBottomButtonPanel);

		JButton btnShowSelectedStudentButton = new JButton("Select Student");
		btnShowSelectedStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//int tabnumber = tabbedPane.getSelectedIndex();
				//this.studentTablePanel.
				String info = appControl.getSelectedStudentInfo(); 			
				rightmostBottomTextArea.setText(info);
				//boolean runEnabled = appControl.runConditions();
				//if(runEnabled) btnInvokeButton.setEnabled(true);
			}
		});
		rightmostBottomButtonPanel.add(btnShowSelectedStudentButton);
		
		JButton btnUpdateStudentTable = new JButton("Update Table");
		btnUpdateStudentTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<List<Object>> studentRowsList =  appControl.getStudentDataForTableRows();				
				int rowidx = 0;
				for(List<Object> rowData : studentRowsList){
					studentTablePanel.setOrAddStudentTableRow(rowidx,rowData);
					rowidx++;
				}			
				txtrConsoleOutput.append(newline + "LOG: UPDATING RESULTS IN THE STUDENTS TABLE: ");
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			}
		});
		rightmostBottomButtonPanel.add(btnUpdateStudentTable);
		
		
	}
	
	
	public void displaySolutionCompareResults(TriptychContent comparisonResults){
		/*
		 * Displaying Student's string, Reference string and comparison results
		 * at three Triptych windows.
		 * 
		 *  HIGHLIGHTING DELETE PARTS IN STUDENT SOLUTION
		 *  HIGHLIGHTING INSERT PARTS IN REFERENCE SOLUTION
		 *  
		 *  EQUAL PARTS ARE NOT HIGHLIGHTED
		 */
		rightTopLeftTextArea.setText(comparisonResults.getStudentContent());
		rightTopRightTextArea.setText(comparisonResults.getReferenceContent());
		txtrResultOutput.setText(comparisonResults.getCompareResult());
		//highlighting
		//https://stackoverflow.com/questions/20341719/how-to-highlight-a-single-word-in-a-jtextarea
		try {
			Highlighter stuHighlighter = rightTopLeftTextArea.getHighlighter();
			HighlightPainter stuPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
			
			Highlighter refHighlighter = rightTopRightTextArea.getHighlighter();
			HighlightPainter refPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.yellow);

		
			//Going through all DEL Strings
			String stucontent = comparisonResults.getStudentContent();
			String refcontent = comparisonResults.getReferenceContent();
			//String stusubstr =stucontent;
			String results = comparisonResults.getCompareResult();
			String[] diffstrs = results.split("\\)#");//("DEL\\(#");
			int stuJump = 0;
			int stuloc0 = 0;
			int stuloc1 = 0;
			int refJump = 0;
			int refloc0 = 0;
			int refloc1 = 0;
			String fraqstr = "";
			boolean isDelFraq = false;
			boolean isEquFraq = false;
			boolean isInsFraq = false;
			
			System.out.println("???? displaySolutionCompareResults(): diffstrs.length: " + diffstrs.length);
			if(diffstrs.length>0){
				for (int i = 0; i < diffstrs.length; i++) {
					isDelFraq = false;
					isEquFraq = false;
					isInsFraq = false;
					//System.out.println("???? diffstrs.length: " + diffstrs.length);
					if (diffstrs[i].startsWith("DEL#")) {
						isDelFraq = true;
						String[] pred = diffstrs[i].split("DEL#\\(");// ("#\\)");
						if(pred.length>1) fraqstr = pred[1];
						//System.out.println("???? DEL STR: " + fraqstr);
					} else if (diffstrs[i].startsWith("EQU#")) {
						isEquFraq = true;
						String[] pred = diffstrs[i].split("EQU#\\(");// ("#\\)");
						if(pred.length>1) fraqstr = pred[1];
						//System.out.println("???? EQU STR: " + fraqstr);
					} else if (diffstrs[i].startsWith("INS#")) {
						isInsFraq = true;
						String[] pred = diffstrs[i].split("INS#\\(");// ("#\\)");
						if(pred.length>1) fraqstr = pred[1];
						//System.out.println("???? INS STR: " + fraqstr);
					}
					
					/*
					 * HIGHLIGHTING DELETE PARTS IN STUDENT SOLUTION
					 */
					
					
					if (isDelFraq || isEquFraq) { // For Student solution
						int stuP0 = stucontent.indexOf(fraqstr, stuJump);
						//System.out.println("???? P0: " + stuP0);
						if (stuP0 >= 0) {
							stuloc0 = stuP0;
							stuloc1 = stuloc0 + fraqstr.length();
							//System.out.println("???? loc0: " + stuloc0);
							//System.out.println("???? loc1: " + stuloc1);
							if (isDelFraq) { // HIghlighting DEL parts in
												// student solution
								stuHighlighter.addHighlight(stuloc0, stuloc1, stuPainter);
								//System.out.println("????DEL loc0: " + stuloc0);
								//System.out.println("????DEL loc1: " + stuloc1);

							}
							stuJump = stuP0 + fraqstr.length();

						}
					}
					/*
					 * HIGHLIGHTING INSERT PARTS IN REFERENCE SOLUTION
					 */
					
					if(isInsFraq||isEquFraq){//For Reference solution
						int refP0 = refcontent.indexOf(fraqstr, refJump);
						//System.out.println("???? REF P0: " + refP0);
						if (refP0 >= 0) {
							refloc0 = refP0;
							refloc1 = refloc0 + fraqstr.length();
							//System.out.println("???? REF loc0: " + refloc0);
							//System.out.println("???? REF loc1: " + refloc1);
							if (isInsFraq) { // HIghlighting INS parts in
												// reference solution
								refHighlighter.addHighlight(refloc0, refloc1, refPainter);
								//System.out.println("???? REF loc0: " + refloc0);
								//System.out.println("???? REF loc1: " + refloc1);

							}
							refJump = refP0 + fraqstr.length();

						}
						
						
					}
				}			
				
			}

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	
		bottomRightTabbedPane.setSelectedIndex(1);

	}

/*	public void displaySolutionCompareResults(String studentSolution, String referenceSolution, String differences){
		
		rightTopLeftTextArea.setText(studentSolution);
		rightTopRightTextArea.setText(referenceSolution);
		txtrResultOutput.setText(differences);
	
	} */

	public int getSelectedStudentTableRow(){
		int selectedfirstrow = this.studentTablePanel.getSelectedFirstRow();
		return selectedfirstrow;
	}
	
	
	public String getEraProjectHomeDirectory() {
		return eraProjectHomeDirectory;
	}

	public void setEraProjectHomeDirectory(String eraProjectHomeDirectory) {
		this.eraProjectHomeDirectory = eraProjectHomeDirectory;
	}

	public JScrollPane getHierarchyTreeScrollPane() {
		// For InternalElements hierarchy
		return hierarchyTreeScrollPane;
	}

	public JScrollPane getHierarchyTreeScrollPane2() {
		// For SystemUnitClassLib hierarchy
		return hierarchyTreeScrollPane2;
	}

	
	public JScrollPane getHierarchyTreeScrollPane3() {
		return hierarchyTreeScrollPane3;
	}
	//---- CAEX 3.0 REQUIRED ADDITION
	public JScrollPane getHierarchyTreeScrollPane4() {
		return hierarchyTreeScrollPane4;
	}

	
	/*
	 * actionPerformed() method for Menu and Button actions
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */


	public JScrollPane getHierarchyTreeScrollPane5() {
		return hierarchyTreeScrollPane5;
	}

	//@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == mntmOpen) {
			fileChooser.setDialogTitle("OPEN PROJECT EXCEL FILE");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory));
			int retVal = fileChooser.showOpenDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				mainOpenFile = fileChooser.getSelectedFile();
				System.out.println("-- Opened file: " + mainOpenFile.getPath());
				
				//Application Initialization
				appControl.openProjectExcel(mainOpenFile);
				//Displaying TaskFlow tree
				JTree elementTree = appControl.getTaskFlowsTree();
				if (elementTree != null)
					this.getHierarchyTreeScrollPane().setViewportView(elementTree);
				//Displaying Student Table
				List<List<Object>> studentRowsList =  appControl.getStudentDataForTableRows();				
				int rowidx = 0;
				for(List<Object> rowData : studentRowsList){
					studentTablePanel.setOrAddStudentTableRow(rowidx,rowData);
					rowidx++;
				}
				
				String dir = mainOpenFile.getParent();
				this.eraProjectHomeDirectory = dir;
				this.latestOpenedFolder = dir;
				// -- Enabling other menuitems
				mntmInvoke.setEnabled(true);
				//mntmSave.setEnabled(true); //do not use this
				mntmShowResults.setEnabled(true);
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: PROJECT EXCEL FILE OPENED: " + mainOpenFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				
				

			} else {
				System.out.println("Frame: No Open File Selected!");
			}

		} else	if (arg0.getSource() == mntmOpenTaskFlow) {
			fileChooser.setDialogTitle("OPEN TAKFLOW XML FILE");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory)); // + "/data"));
			int retVal = fileChooser.showOpenDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				File taskFlowFile = fileChooser.getSelectedFile();
				System.out.println("-- Opened file: " + taskFlowFile.getPath());
				
				appControl.openTaskFlowFile(taskFlowFile.getPath());
				JTree elementTree = appControl.getTaskFlowsTree();
				if (elementTree != null)
					this.getHierarchyTreeScrollPane().setViewportView(elementTree);
								
				String dir = taskFlowFile.getParent();
				this.latestOpenedFolder = dir;
				// -- Enabling other menuitems
				//mntmSave.setEnabled(true);
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: TAKFLOW FILE OPENED: " + taskFlowFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

			} else {
				System.out.println("Frame: No Open File Selected!");
			}

		} else	if (arg0.getSource() == mntmInvoke) {
			
			appControl.invokeCheckingProcess();
			
			txtrConsoleOutput.append(newline + "LOG: CHECKING PROCESS STARTED:(REMEMBER TO CLOSE EXCEL FILE!!!) ");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			
		} else	if (arg0.getSource() == mntmSave) {
			
			appControl.closeProjectExcel();
			mntmInvoke.setEnabled(false);
			txtrConsoleOutput.append(newline + "LOG: PROJECT EXCEL FILE CLOSED: : ");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			
		} else if (arg0.getSource() == mntmSaveResultsAsXML) {
			fileChooser.setDialogTitle("SAVE RESULTS AS XML FILE (.xml)");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory));
			

			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File saveFile = fileChooser.getSelectedFile();
				appControl.serializeResultsToXML(saveFile.getPath());
				System.out.println("-- Saved to file: " + saveFile.getName());
				String dir = saveFile.getParent();
				this.latestOpenedFolder = dir;
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: RESULTS SERIALIZED AS XML: " + saveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Save File Selected!");
			}

		} else	if (arg0.getSource() == mntmShowResults) {
			
			List<List<Object>> studentRowsList =  appControl.getStudentDataForTableRows();
		
			int rowidx = 0;
			for(List<Object> rowData : studentRowsList){
				studentTablePanel.setOrAddStudentTableRow(rowidx,rowData);
				rowidx++;
			}
			
			txtrConsoleOutput.append(newline + "LOG: DISPLAYING RESULTS IN THE STUDENTS TABLE: ");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
						
		} else	if (arg0.getSource() == mntmCompareSol) {
			/*TODO: Difference of selected students solution with the reference solution
			// One Student have to be selected from Student table and
			// stuSolution have to be selected from TaskFlow tree
			// Displaying student file content in: rightTopLeftTextArea
			// Displaying reference file content in: rightTopRightTextArea
			// Computing difference
			// Displaying difference results in Result tab
			*/
			appControl.compareSolutionFiles();
			
			txtrConsoleOutput.append(newline + "LOG: COMPARING STUDENT SOLUTION WITH REFERENCE: ");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
						
		} else	if (arg0.getSource() == mntmSingleTestCase) {
		   /* Running a selected testCase for a single selected student
			* One Student have to be selected from Student table and
			* testCase have to be selected from TaskFlow tree
			* Displaying testcase's student flow results in: rightTopLeftTextArea
			* Displaying testcase's reference flow results in: rightTopRightTextArea
			* Displaying merge flow's difference results in Result tab
			*/
			appControl.invokeSelectedTestCaseForStudent();
			
			txtrConsoleOutput.append(newline + "LOG: RUNNING THE SELECTED TESTCASE FOR THE SELECTED STUDENT: ");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
						
		}
		
		
		
		/* OLD From ERAmlHandler project
		 * For File Open and Save action dialogs AND For mntmGenerateJmonkey AND
		 * for mntmLoadRules AND mntmInvokeReasoner AND mntmSaveResultModels AND
		 * mntmCaexToAsp AND mntmConfigureSchema AND mntmSetTransformContext AND
		 * mntmInvokeTransform AND mntmGenOntologyModel AND mntmNewProject AND
		 * mntmSaveProjectAs AND mntmSaveProject AND mntmOpenProject AND
		 * mntmAspSolver AND mntmSaveOntologyModel
		 * rbMenuItem1-6 AND
		 * mntmMergeModels 
		 * mntmLoadOntologyModels AND mntmLoadSpinCommands
		 * mntmInvokeSpinCommands AND mntmSaveSpinCommands
		 * mntmClearPartials AND mntmClearCombined AND mntmClearMerged
		 * versionMenuItem1-2
		 * mntmOpenDiagram
		 
		
		if (arg0.getSource() == mntmOpenDiagram) {
			fileChooser.setDialogTitle("OPEN DIAGRAM FILE FILE (.svg/.html/.htm)");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			int retVal = fileChooser.showOpenDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				File diagramFile = fileChooser.getSelectedFile();
				System.out.println("-- Opened file: " + diagramFile.getPath());
				
				String filename = diagramFile.getName();
				if(filename.endsWith(".svg")){
					//C:\Users\Pekka Aarnio\git\valle-de-luna\ERAmlHandler\data\svg\tank.svg
					svgCanvas.setURI(diagramFile.toURI().toString());// "data/svg/rectangle.svg");
				} else if((filename.endsWith(".html"))||(filename.endsWith(".htm"))){
					//Document doc = kit.createDefaultDocument();
			        //jEditorPane.setDocument(doc);
			        //jEditorPane.setText(htmlString);
					StringBuffer htmltextbuf = appControl.readTextFileContent(diagramFile.getPath().toString());
					if((htmltextbuf!=null)&&(htmltextbuf.length()>0)){
						
						jEditorPane.setText(htmltextbuf.toString());
					}
				}
				
				
				String dir = diagramFile.getParent();
				this.latestOpenedFolder = dir;
				
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: DIAGRAM FILE OPENED: " + diagramFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

			} else {
				System.out.println("Frame: No Open File Selected!");
			}

		} else if (arg0.getSource() == mntmClearPartials) {
			
			appControl.clearRDFModels(true,false,false);
			
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: CLEARING PARTIAL ONTOLOGY MODELS");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			
		} else if (arg0.getSource() == mntmClearCombined) {
			
			appControl.clearRDFModels(false,true,false);
			
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: CLEARING COMBINED ONTOLOGY MODEL");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			
		} else if (arg0.getSource() == mntmClearMerged) {
			
			appControl.clearRDFModels(false,false,true);
			
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: CLEARING MERGED ONTOLOGY MODEL");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			
		} else if (arg0.getSource() == mntmSaveSpinCommands) {
			fileChooser.setDialogTitle("SAVE SPIN CSMCOMMANDS TO JSON FILE (.json)");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File saveFile = fileChooser.getSelectedFile();
				appControl.saveCSMCommandsToJsonFile(saveFile.getPath());
				System.out.println("-- Saved file: " + saveFile.getName());
				String dir = saveFile.getParent();
				this.latestOpenedFolder = dir;
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: SPIN CSMCOMMANDS SAVED INTO FILE: " + saveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Save File Selected!");
			}

		} else if (arg0.getSource() == mntmInvokeSpinCommands) {
			
			StringBuffer resultbuf = appControl.invokeCSMCommandWorkflow();			
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: CSM (SPIN) COMMANDS INVOKED");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			//-- Result Printing
			txtrResultOutput.setText(null); // CLear old text
			txtrResultOutput.append(resultbuf.toString() + newline);
			txtrResultOutput.setCaretPosition(0); //txtrResultOutput.getText().length());
			bottomRightTabbedPane.setSelectedIndex(1);
			
		} else 	if (arg0.getSource() == mntmLoadSpinCommands) {
			fileChooser.setDialogTitle("LOAD SPIN COMMAND FILE");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);			
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				File selectedfile = fileChooser.getSelectedFile();
				System.out.println("-- Selected file: " + selectedfile.getPath());
				StringBuffer sbuf = appControl.initCommandFileSpinMng(selectedfile.getPath());
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: SPIN COMMAND FILE: " + selectedfile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				//-- File Printing
				txtrSpinCommandFileOutput.setText(null); // CLear old text
				txtrSpinCommandFileOutput.append(sbuf.toString() + newline);
				txtrSpinCommandFileOutput.setCaretPosition(0);
				btnSearchCommandButton.setEnabled(true);
				bottomRightTabbedPane.setSelectedIndex(2);
			} else {
				System.out.println("Frame: No SPIN COMMAND File Selected!");
			}
			fileChooser.setSelectedFile(new File(""));
			
		} else if (arg0.getSource() == mntmLoadOntologyModels) {
			fileChooser.setDialogTitle("SELECT ONTOLOGY FILES (.ttl)(the name of the main ontology file (importing others) should contain a substring '_main' OR '_imports' OR be the first in folder file ordering (e.g. alphabetical))");
			//TODO:PROBLEM: files sorted alphabetically, not in selection order???
			//https://stackoverflow.com/questions/29284814/jfilechooser-open-multiple-files-in-the-order-they-are-clicked
			//https://stackoverflow.com/questions/42972059/javafx-filechooser-open-multiple-files-in-order-of-selection
			//fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			//fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setMultiSelectionEnabled(true);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				File[] ontfiles = fileChooser.getSelectedFiles();
				System.out.println("-- Ontology files selected: # " + ontfiles.length);
				this.appControl.preLoadCSMOntologyModel(ontfiles);
				String dir = ontfiles[0].getParent();
				//this.latestOpenedFolder = dir;
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: ONTOLOGY MODEL FILES SELECTED FROM: " + dir);
				for(int i=0; i<ontfiles.length; i++){
					File ontfile = ontfiles[i];
					String pathstr = ontfile.getAbsolutePath(); 
					//https://stackoverflow.com/questions/34434042/convert-windows-path-to-uri-in-java
					String uristr = ontfile.toURI().toString();
					txtrConsoleOutput.append(newline+ "LOG:" + "(" + i + ")" + uristr);
				}
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			
			} else {
				System.out.println("Frame: No Ontology files selected!");
			}
			fileChooser.setSelectedFiles(new File[]{new File("")});
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setMultiSelectionEnabled(false);
			
		} else if (arg0.getSource() == mntmMergeModels) {
			
			appControl.mergeExistingRDFModels();
			
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: MERGE EXISTING ONTOLOGY MODELS");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			//-- Result Printing
			String serialized = appControl.getSerializeRdfModel(null); // if null, use default format
			txtrResultOutput.setText(null); // CLear old text
			txtrResultOutput.append(serialized + newline);
			txtrResultOutput.setCaretPosition(txtrResultOutput.getText().length());
			
		} else if ((arg0.getSource() == rbMenuItem1)||(arg0.getSource() == rbMenuItem2)||(arg0.getSource() == rbMenuItem3)||(arg0.getSource() == rbMenuItem4)||(arg0.getSource() == rbMenuItem5)||(arg0.getSource() == rbMenuItem6)) {
			String radiocommand =arg0.getActionCommand();
			appControl.genereteCaexOntologyModel(radiocommand);
			rbMenuItem1.setSelected(false);
			rbMenuItem2.setSelected(false);
			rbMenuItem3.setSelected(false);
			rbMenuItem4.setSelected(false);
			rbMenuItem5.setSelected(false);
			rbMenuItem6.setSelected(false);
			if(arg0.getSource() == rbMenuItem1) rbMenuItem1.setSelected(true);
			if(arg0.getSource() == rbMenuItem2) rbMenuItem2.setSelected(true);
			if(arg0.getSource() == rbMenuItem3) rbMenuItem3.setSelected(true);
			if(arg0.getSource() == rbMenuItem4) rbMenuItem4.setSelected(true);
			if(arg0.getSource() == rbMenuItem5) rbMenuItem5.setSelected(true);
			if(arg0.getSource() == rbMenuItem6) rbMenuItem6.setSelected(true);
			
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: SELECTED:" + radiocommand);
			txtrConsoleOutput.append(newline + "LOG: ONTOLOGY MODEL GENERATED FROM THE MAIN CAEX MODEL! ");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			//-- Result Printing
			String serialized = appControl.getSerializeRdfModel(null); // if null, use default format
			txtrResultOutput.setText(null); // CLear old text
			txtrResultOutput.append(serialized + newline);
			txtrResultOutput.setCaretPosition(txtrResultOutput.getText().length());
			bottomRightTabbedPane.setEnabledAt(1, true);
			
		} else if (arg0.getSource() == mntmSaveOntologyModel) {
			fileChooser.setDialogTitle("SAVE CAEX SOURCE ONTOLOGY MODEL TO FILE (.ttl, .owl)");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			

			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File saveFile = fileChooser.getSelectedFile();
				appControl.saveCaexOntologyModel(saveFile.getPath());
				System.out.println("-- Saved file: " + saveFile.getName());
				String dir = saveFile.getParent();
				this.latestOpenedFolder = dir;
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: CAEX SOURCE ONTOLOGY MODEL SAVED INTO FILE: " + saveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Save File Selected!");
			}

		} else if (arg0.getSource() == mntmAspSolver) {
			fileChooser.setDialogTitle("SELECT ASP SOLVER EXE (dlv.mingw.exe)");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);			
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				File solverExefile = fileChooser.getSelectedFile();
				System.out.println("-- Selected file: " + solverExefile.getPath());
				appControl.setAspSolverEngine(solverExefile.getPath());
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: ASP SOLVER EXE: " + solverExefile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

			} else {
				System.out.println("Frame: No ASP Solver Exe File Selected!");
			}
			fileChooser.setSelectedFile(new File(""));
			
		} else if (arg0.getSource() == mntmOpenProject) {
			fileChooser.setDialogTitle("OPEN ERA PROJECT");
			fileChooser.setCurrentDirectory((new File(this.eraProjectHomeDirectory)).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);
			
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				File openProjectDirectory = fileChooser.getSelectedFile();
				boolean ok = appControl.openProjectInFolder(openProjectDirectory.getPath());
				if(ok){
				this.eraProjectHomeDirectory = openProjectDirectory.getPath();
				this.latestOpenedFolder = openProjectDirectory.getPath();
				System.out.println("-- Project Folder Opened: " + openProjectDirectory.getName());
				// -- Enabling other menuitems
				mntmOpen.setEnabled(true);
				newProjectVersionSubmenu.setEnabled(true);
				mntmSaveProject.setEnabled(true);
				mntmSaveProjectAs.setEnabled(true);
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: ERA PROJECT OPENED: " + openProjectDirectory.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				} else {
					txtrConsoleOutput.append(newline + "LOG: ERA PROJECT NOT FOUND??? 'project.meta' file does not exist?");
					txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				}
			} else {
				System.out.println("Frame: No Project Folder Selected!");
			}
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			
		} else if (arg0.getSource() == mntmSaveProject) {
			
			appControl.saveProject();
			System.out.println("Frame: Current Project Saved!");
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: CURRENT ERA PROJECT SAVED!");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			
		} else if (arg0.getSource() == mntmSaveProjectAs) {
			fileChooser.setDialogTitle("SAVE PROJECT AS (create a new folder)");
			fileChooser.setCurrentDirectory((new File(this.eraProjectHomeDirectory)).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File newProjectDirectory = fileChooser.getSelectedFile();
				appControl.saveProjectInNewDirectory(newProjectDirectory.getPath());
				this.eraProjectHomeDirectory = newProjectDirectory.getPath();
				this.latestOpenedFolder = newProjectDirectory.getPath();
				System.out.println("-- New Project Folder: " + newProjectDirectory.getName());
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: CURRENT ERA PROJECT SAVED AS: " +newProjectDirectory.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Project Folder Selected!");
			}
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		} else if (arg0.getSource() == mntmNewProject) {
			//NOT IN USE: Replaced with the version selection option (See next else if)
			this.getHierarchyTreeScrollPane().setViewportView(null);
			this.getHierarchyTreeScrollPane2().setViewportView(null);
			this.getHierarchyTreeScrollPane3().setViewportView(null);
			this.getHierarchyTreeScrollPane4().setViewportView(null);			
			this.bottomLeftTextArea.setText("Selected Element:\n");		
			System.out.println("-- createNewProject(); Element Tree Cleared! ");
			
			fileChooser.setDialogTitle("CREATE A NEW PROJECT INTO (create a new folder)");
			fileChooser.setCurrentDirectory((new File(this.eraProjectHomeDirectory)).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File newProjectDirectory = fileChooser.getSelectedFile();
				//appControl.saveProjectInFolder(newProjectDirectory.getPath());
				boolean ok= appControl.createNewProject(newProjectDirectory.getPath(), "2.15");
				if(ok){
				this.eraProjectHomeDirectory = newProjectDirectory.getPath();
				this.latestOpenedFolder = newProjectDirectory.getPath();
				System.out.println("-- New Project Home Directory: " + newProjectDirectory.getName());
				// -- Enabling other menuitems
				mntmOpen.setEnabled(true);
				newProjectVersionSubmenu.setEnabled(true);
				mntmSaveProject.setEnabled(true);
				mntmSaveProjectAs.setEnabled(true);
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: NEW ERA PROJECT HOME DIRECTORY: " +newProjectDirectory.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				} else {
					//-- Console Printing
					txtrConsoleOutput.append(newline + "LOG: PROBLEM: NEW ERA PROJECT COULD NOT BE CREATED INTO DIRECTORY: " +newProjectDirectory.getName());
					txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				}
			} else {
				System.out.println("Frame: No Project Folder Selected!");
			}
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
		} else if ((arg0.getSource() == versionMenuItem1)||(arg0.getSource() == versionMenuItem2)) {
			//Create a new Project with caex version 2.15 or 3.0 (radiocommand)
			String radiocommand =arg0.getActionCommand();
			versionMenuItem1.setSelected(false);
			versionMenuItem2.setSelected(false);				
			if(arg0.getSource() == versionMenuItem1) versionMenuItem1.setSelected(true);
			if(arg0.getSource() == versionMenuItem2) versionMenuItem2.setSelected(true);
		
		//---
			this.getHierarchyTreeScrollPane().setViewportView(null);
			this.getHierarchyTreeScrollPane2().setViewportView(null);
			this.getHierarchyTreeScrollPane3().setViewportView(null);
			this.getHierarchyTreeScrollPane4().setViewportView(null);			
			this.bottomLeftTextArea.setText("Selected Element:\n");		
			System.out.println("-- createNewProject(); Element Tree Cleared! ");
			
			fileChooser.setDialogTitle("CREATE A NEW PROJECT HOME (CAEX VERSION " + radiocommand + ")");
			fileChooser.setCurrentDirectory((new File(this.eraProjectHomeDirectory)).getParentFile());
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File newProjectDirectory = fileChooser.getSelectedFile();
				//appControl.saveProjectInFolder(newProjectDirectory.getPath());
				boolean ok= appControl.createNewProject(newProjectDirectory.getPath(), radiocommand); //"2.15");
				if(ok){
				this.eraProjectHomeDirectory = newProjectDirectory.getPath();
				this.latestOpenedFolder = newProjectDirectory.getPath();
				System.out.println("-- New Project Home Directory: " + newProjectDirectory.getName());
				// -- Enabling other menuitems
				mntmOpen.setEnabled(true);
				newProjectVersionSubmenu.setEnabled(true);
				mntmSaveProject.setEnabled(true);
				mntmSaveProjectAs.setEnabled(true);
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: NEW ERA PROJECT HOME DIRECTORY: " +newProjectDirectory.getName() + " (CAEX version: " + radiocommand + " )");
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				} else {
					//-- Console Printing
					txtrConsoleOutput.append(newline + "LOG: PROBLEM: NEW ERA PROJECT COULD NOT BE CREATED INTO DIRECTORY: " +newProjectDirectory.getName());
					txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				}
			} else {
				System.out.println("Frame: No Project Folder Selected!");
			}
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

	} else if (arg0.getSource() == mntmGenOntologyModel) {
			appControl.genereteCaexOntologyModel("default");;
			System.out.println("-- genereteCaexOntologyModel();! ");
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: ONTOLOGY MODEL GENERATED FROM THE MAIN CAEX MODEL! ");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			//-- Result Printing
			String serialized = appControl.getSerializeRdfModel(null); // if null, use default format
			txtrResultOutput.setText(null); // CLear old text
			txtrResultOutput.append(serialized + newline);
			txtrResultOutput.setCaretPosition(txtrResultOutput.getText().length());
			bottomRightTabbedPane.setEnabledAt(1, true);
	
		} else if (arg0.getSource() == mntmInvokeTransform) {
				appControl.invokeXslContextTransform();
				System.out.println("-- invokeXslContextTransform()! ");
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: XSL TRANSFORM INVOKED!");
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
		
		} else if (arg0.getSource() == mntmSetTransformContext) {
			fileChooser.setDialogTitle("SELECT XSL TRANSFORM CONTEXT FILES (xsl,xml/aml,trout)");
			
			if(!".".equals(eraProjectHomeDirectory))
				fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory));
			else fileChooser.setCurrentDirectory(new File(this.latestOpenedFolder));
			
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setMultiSelectionEnabled(true);
			fileChooser.setSelectedFiles(new File[]{new File("")});
			
			int retVal = fileChooser.showOpenDialog(MainFrame.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				File[] files = fileChooser.getSelectedFiles();
				System.out.println("-- TRANSFORM CONTEXT files selected: # " + files.length);
				this.appControl.initXslContext(files);
				String dir = files[0].getParent();
				System.out.println("-- TRANSFORM CONTEXT parent folder: " + dir);
				this.latestOpenedFolder = dir;
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: XSL TRANSFORM CONTEXT FILES SELECTED: (DIR: " + dir + ")");
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

			} else {
				System.out.println("Frame: No Xsl context files selected!");
			}
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setMultiSelectionEnabled(false);

		} else 	if (arg0.getSource() == mntmConfigureSchema) {
			fileChooser.setDialogTitle("SELECT XML VALIDATIN SCHEMA FILE");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/configure"));
			
			int retVal = fileChooser.showOpenDialog(MainFrame.this);			
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				//mainOpenFile = fileChooser.getSelectedFile();
				File schemafile = fileChooser.getSelectedFile();
				System.out.println("-- Opened file: " + schemafile.getPath());
				appControl.setValidationSchema(schemafile.getPath(), null);
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: CAEX VALIDATIN SCHEMA SELECTED:" + schemafile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

			} else {
				System.out.println("Frame: No Schema File Selected!");
			}
			fileChooser.setSelectedFile(new File(""));
			
		} else if (arg0.getSource() == mntmCaexToAsp) {
			fileChooser.setDialogTitle("GENERATE ASP FACTS FROM CAEX AND SAVE IT TO FILE:");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.latestOpenedFolder)); //"./data/genereted"));
			
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();

				appControl.xslTransform("caex2aspfacts", null, mainSaveFile.getPath());
				System.out.println("-- Asp facts file (.db): " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = dir;
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: GENERATED ASP FACTS FROM CAEX SAVED INTO FILE: " + mainSaveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No File Defined!");
			}

		} else if (arg0.getSource() == mntmSaveResultModels) {
			fileChooser.setDialogTitle("SAVE ASP SOLVER RESULT MODELS TO FILE:");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			

			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();
				appControl.saveAspModel(mainSaveFile.getPath());
				System.out.println("-- Saved file: " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = dir;
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: ASP SOLVER RESULT MODELS SAVED TO FILE:: " +mainSaveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Save File Selected!");
			}

		} else if (arg0.getSource() == mntmInvokeReasoner) {

				StringBuffer strmodels = appControl.invokeAspReasoner();
				System.out.println("-- invokeAspReasoner! ");			
				txtrResultOutput.setText(null); // CLear old text
				if(strmodels!=null)
					txtrResultOutput.append(strmodels.toString() + newline);
				else txtrResultOutput.append("ASP RESULT MODELS: null");
				txtrResultOutput.setCaretPosition(txtrResultOutput.getText().length());
				bottomRightTabbedPane.setEnabledAt(1, true);
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: ASP REASONING INVOKED:: (See RESULTS tab)");
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

		} else if (arg0.getSource() == mntmLoadRules) {
			fileChooser.setDialogTitle("LOAD ASP RULE AND FACT FILES");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/configure")); //"./configure/asp_dlv"));
			fileChooser.setMultiSelectionEnabled(true);
			int retVal = fileChooser.showOpenDialog(MainFrame.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				File[] aspfiles = fileChooser.getSelectedFiles();
				System.out.println("-- Asp files selected: # " + aspfiles.length);
				this.appControl.initAspModel(aspfiles);
				String dir = aspfiles[0].getParent();
				this.latestOpenedFolder = dir;
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: ASP RULE AND FACT FILES LOADED: " + dir);
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			
			} else {
				System.out.println("Frame: No Asp files selected!");
			}
			fileChooser.setSelectedFiles(new File[]{new File("")});
			fileChooser.setMultiSelectionEnabled(false);

		} else if (arg0.getSource() == mntmGenerateJmonkey) {
			fileChooser.setDialogTitle("GENERATE AND SAVE JMONKEY SCRIPT TO FILE:");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data")); //"./data/genereted"));
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();

				appControl.xslTransform("caex2jmonkey", null, mainSaveFile.getPath());
				System.out.println("-- JMonkey file (.jmc): " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = dir;
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: GENERATED JMONKEY SCRIPT SAVED TO FILE: " +  mainSaveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				
			} else {
				System.out.println("Frame: No File Defined!");
			}

		} else if (arg0.getSource() == mntmOpen) {
			fileChooser.setDialogTitle("OPEN CAEX XML FILE");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			int retVal = fileChooser.showOpenDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");

				mainOpenFile = fileChooser.getSelectedFile();
				System.out.println("-- Opened file: " + mainOpenFile.getPath());
				// InternalElements hierarchy
				//JTree elementTree = appControl.buildJaxbModel(mainOpenFile.getPath());
				//appControl.buildJaxbModel(mainOpenFile.getPath());
				boolean ok = appControl.openCaexFile(mainOpenFile.getPath(), "2.15");
				JTree elementTree = appControl.getInstanceHtree();
				if (elementTree != null)
					this.getHierarchyTreeScrollPane().setViewportView(elementTree);
				// SystemUnitClassLib hierarchy
				JTree systemUCLibTree = appControl.getSucltree();
				if (systemUCLibTree != null)
					this.getHierarchyTreeScrollPane2().setViewportView(systemUCLibTree);
				// RoleClassLib hierarchy
				JTree roleCLibTree = appControl.getRolecltree();
				if (roleCLibTree != null)
					this.getHierarchyTreeScrollPane3().setViewportView(roleCLibTree);
				// InterfaceClassLib hierarchy
				JTree interfaceCLibTree = appControl.getInterfacecltree();
				if (interfaceCLibTree != null)
					this.getHierarchyTreeScrollPane4().setViewportView(interfaceCLibTree);
				
				// --- CAEX 3.0 REQUIRED ADDITION
				// AttributeTypeLib hierarchy
				JTree attributeTLibTree = appControl.getAttributetltree();
				if (attributeTLibTree != null)
					this.getHierarchyTreeScrollPane5().setViewportView(attributeTLibTree);
				
				
				String dir = mainOpenFile.getParent();
				this.latestOpenedFolder = dir;
				// -- Enabling other menuitems
				mntmSave.setEnabled(true);
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: CAEX FILE OPENED: " + mainOpenFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

			} else {
				System.out.println("Frame: No Open File Selected!");
			}

		} else if (arg0.getSource() == mntmSave) {
			fileChooser.setDialogTitle("SAVE CAEX FILE:");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory + "/data"));
			int retVal = fileChooser.showSaveDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Save OK pressed");
				File mainSaveFile = fileChooser.getSelectedFile();
				// Write/marshal jaxb model to xml file
				appControl.saveXMLModel(mainSaveFile.getPath());
				System.out.println("-- Saved file: " + mainSaveFile.getName());
				String dir = mainSaveFile.getParent();
				this.latestOpenedFolder = dir;
				//-- Console Printing
				txtrConsoleOutput.append(newline + "LOG: THE MAIN CAEX FILE MARSHALLED INTO FILE: " +  mainSaveFile.getName());
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
			} else {
				System.out.println("Frame: No Save File Selected!");
			}

		} */

	}

	public JTextArea getBottomLeftTextArea() {
		return bottomLeftTextArea;
	}

	/*private void addLabelTextRows(JLabel[] labels, JTextField[] textFields, GridBagLayout gridbag,
			Container container) {
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.EAST;
		int numLabels = labels.length;

		for (int i = 0; i < numLabels; i++) {
			c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
			c.fill = GridBagConstraints.NONE; // reset to default
			c.weightx = 0.0; // reset to default
			container.add(labels[i], c);

			c.gridwidth = GridBagConstraints.REMAINDER; // end row
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			container.add(textFields[i], c);
		}
	}
	*/
	
	/*
	 * Required by TreeSelectionListener interface. See Oracle:TreeDemo
	 * 
	 * public void valueChanged(TreeSelectionEvent e) {
	 * System.out.println("Frame: TreeSelectionEvent:" + e.toString());
	 * //ElementModel node = (ElementModel)tree.getLastSelectedPathComponent();
	 * Object node = tree.getLastSelectedPathComponent(); if (node == null){
	 * System.out.println("Frame: TreeSelectionEvent NODE: " + node); return; }
	 * System.out.println("Frame: TreeSelectionEvent NODE" + node.toString());
	 * //Object nodeInfo = node.getUserObject();
	 * 
	 * }
	 **/

}
