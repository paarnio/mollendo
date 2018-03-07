package siima.app.gui;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
/*
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
*/
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JEditorPane;
import static javax.swing.ScrollPaneConstants.*;

import siima.app.control.MainAppController;
import siima.app.model.TriptychContent;


public class MainFrame extends JFrame implements ActionListener, ItemListener { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String eraProjectHomeDirectory = ".";
	public String latestOpenedFolder = ".";
	private final static String newline="\n";
	private JFileChooser fileChooser;
	private JSplitPane m_contentPane;
	private JMenuItem mntmOpen;
	private JMenuItem mntmOpenWithout;
	private JMenuItem mntmLoadStudentData;
	private JMenuItem mntmOpenTaskFlow;
	private File mainOpenFile;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveResultsAsXML;
	private JMenuItem mntmShowResults;
	private JButton btnInvokeButton;

	public MainAppController appControl;
	private JScrollPane hierarchyTreeScrollPane;
	private JScrollPane consoleScrollPane;
	private JScrollPane resultScrollPane;
	
	private JTextArea bottomLeftTextArea;
	private JTextArea txtrConsoleOutput;
	private JTextArea txtrResultOutput;
	
	private JTextArea rightTopLeftTextArea;
	private JTextArea rightTopRightTextArea;
	private JTextArea rightmostBottomTextArea;
	private JTabbedPane tabbedPane;
	private JTabbedPane bottomRightTabbedPane;
	
	private JMenuItem mntmExit;
	private JMenuItem mntmInvoke;
	private JMenuItem mntmCheckSolExist;
	private JMenuItem mntmCompareSol;
	private JMenuItem mntmSingleTestCase;
	
	private JCheckBoxMenuItem mntmCbResToExcel;
	private JCheckBoxMenuItem mntmCbMenuItem2;
	
	private JMenuItem mntmInvokeTransform;
	private JMenuItem mntmSetTransformContext;
	private JMenuItem mntmResultsToCsv;
	private JMenuItem mntmResultsToStudentsXml;
    private StudentTablePanel studentTablePanel;
    protected JEditorPane jEditorPane;
	
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
		setBounds(100, 100, 1100, 600); 
		setTitle("XML-Checker");

		this.appControl = new MainAppController(this); 

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
		mntmOpen.addActionListener(this);
		mnFile.add(mntmOpen);
		mntmOpen.setEnabled(true);
		
		mnFile.addSeparator();
		
		mntmOpenWithout = new JMenuItem("Open Project Excel without Student Data");
		mntmOpenWithout.addActionListener(this);
		mnFile.add(mntmOpenWithout);
		mntmOpenWithout.setEnabled(true);	
		
		mntmLoadStudentData = new JMenuItem("Load Student XML Data");
		mntmLoadStudentData.addActionListener(this); 
		mnFile.add(mntmLoadStudentData);
		mntmLoadStudentData.setEnabled(false);
		
		mnFile.addSeparator();
		
		//NOTE:Do not use this (might corrupt the excel)
		mntmSave = new JMenuItem("Save and Close Project Excel ");
		mntmSave.addActionListener(this);
		mnFile.add(mntmSave);
		mntmSave.setEnabled(false); //Not in use
		

		mntmOpenTaskFlow = new JMenuItem("Open TaskFlow File...");
		mntmOpenTaskFlow.addActionListener(this);
		mnFile.add(mntmOpenTaskFlow);

		mnFile.addSeparator();
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//appControl.closeProjectExcel();
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		/*
		 * Processor Menu
		 * 
		 */
		
		JMenu mnPros = new JMenu("Processor");
		menuBar.add(mnPros);

		mntmInvoke = new JMenuItem("Invoke Processor");
		mntmInvoke.addActionListener(this);
		mnPros.add(mntmInvoke);
		mntmInvoke.setEnabled(false);
		
		//a group of check box menu items
		/*mnPros.addSeparator();
		mntmCbMenuItem2 = new JCheckBoxMenuItem("Another CheckBox");
		mntmCbMenuItem2.addItemListener(this);
		mnPros.add(mntmCbMenuItem2);
		*/
		
		/*
		 * Student Menu
		 * 
		 */
		
		JMenu mnStudent = new JMenu("Student");
		menuBar.add(mnStudent);
		
		mntmCheckSolExist = new JMenuItem("Check Existence of Solutions");
		mntmCheckSolExist.addActionListener(this); // See: method											// actionPerformed(ActionEvent arg0)
		mnStudent.add(mntmCheckSolExist);
		mntmCheckSolExist.setEnabled(true);
		
		mntmCompareSol = new JMenuItem("Compare One Solution");
		mntmCompareSol.addActionListener(this); // See: method											// actionPerformed(ActionEvent arg0)
		mnStudent.add(mntmCompareSol);
		mntmCompareSol.setEnabled(true);	
		
		mntmSingleTestCase = new JMenuItem("Run TestCase For Student");
		mntmSingleTestCase.addActionListener(this); // See: method											// actionPerformed(ActionEvent arg0)
		mnStudent.add(mntmSingleTestCase);
		mntmSingleTestCase.setEnabled(true);
		
		/*
		 * View/Results Menu
		 * 
		 */
		
		JMenu mnView = new JMenu("Results");
		menuBar.add(mnView);

		mntmShowResults = new JMenuItem("Update Student table");
		mntmShowResults.addActionListener(this); // See: method											// actionPerformed(ActionEvent arg0)
		mnView.add(mntmShowResults);
		mntmShowResults.setEnabled(false);
		
		//a check box menu item
		mnView.addSeparator();
		mntmCbResToExcel = new JCheckBoxMenuItem("Results to Project Excel");
		mntmCbResToExcel.addItemListener(this);
		mnView.add(mntmCbResToExcel);
		
		mnView.addSeparator();
		mntmSaveResultsAsXML = new JMenuItem("Save Results As XML...");
		mntmSaveResultsAsXML.addActionListener(this);
		mnView.add(mntmSaveResultsAsXML);
		mntmSaveResultsAsXML.setEnabled(true);
		
		mnView.addSeparator();
		//NEW SUBMENU
		JMenu submenu = new JMenu("Transform");
		
		mntmResultsToCsv = new JMenuItem("Transform To CSV (.csv/.trout) ...");
		mntmResultsToCsv.addActionListener(this);
		submenu.add(mntmResultsToCsv);
		
		mntmResultsToStudentsXml = new JMenuItem("Transform To Students XML (.trout) ...");
		mntmResultsToStudentsXml.addActionListener(this);
		submenu.add(mntmResultsToStudentsXml);	
		
		submenu.addSeparator();
		
		mntmSetTransformContext = new JMenuItem("Set Context...");
		mntmSetTransformContext.addActionListener(this);
		submenu.add(mntmSetTransformContext);

		mntmInvokeTransform = new JMenuItem("Run Context Transform");
		mntmInvokeTransform.addActionListener(this);
		submenu.add(mntmInvokeTransform);

		mnView.add(submenu);
		
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
		 * System.out.println("TEST:Tab Changed: " +
		 * tabbedPane.getSelectedIndex()); } });
		 */
		hierarchyTreeScrollPane = new JScrollPane();
		tabbedPane.insertTab("TaskFlow", null, hierarchyTreeScrollPane, "InternalElements", 0);
		
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
		
		//"----------bottomLeftTextArea---------\n"
		bottomLeftTextArea.setText("---------- TaskFlow Node Info ---------\n");
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
		gbl_rightTopLeftPanel.columnWidths = new int[] {5, 250, 0 }; 
		gbl_rightTopLeftPanel.rowHeights = new int[] { 300, 0 };
		gbl_rightTopLeftPanel.columnWeights = new double[] {0.05, 1.0, Double.MIN_VALUE };
		gbl_rightTopLeftPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };//{ 1.0, 1.0, 1.0, Double.MIN_VALUE };
		rightTopLeftPanel.setLayout(gbl_rightTopLeftPanel);
		
		//Line number ruler text area
		JScrollPane lineNrRulerScrollPane = new JScrollPane();
		lineNrRulerScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_lineNrRulerScrollPane = new GridBagConstraints();
		gbc_lineNrRulerScrollPane.fill = GridBagConstraints.BOTH;
		gbc_lineNrRulerScrollPane.gridx = 0;
		gbc_lineNrRulerScrollPane.gridy = 0;
		rightTopLeftPanel.add(lineNrRulerScrollPane, gbc_lineNrRulerScrollPane);
		
		JTextArea lineRulerTextArea = new JTextArea();
		lineRulerTextArea.setRows(30);
		lineRulerTextArea.setColumns(5);
		lineRulerTextArea.setLineWrap(false); //(true);
		lineRulerTextArea.setText("  1:\n  2:\n  3:\n  4:\n  5:\n  6:\n  7:\n  8:\n  9:\n 10:\n"
				+ " 11:\n 12:\n 13:\n 14:\n 15:\n 16:\n 17:\n 18:\n 19:\n 20:\n"
				+ " 21:\n 22:\n 23:\n 24:\n 25:\n 26:\n 27:\n 28:\n 29:\n 30:\n");
				//+ " 31:\n 32:\n 33:\n 34:\n 35:\n 36:\n 37:\n 38:\n 39:\n 40:\n");
		lineRulerTextArea.setCaretPosition(HORIZONTAL_SCROLLBAR_NEVER);	
		lineNrRulerScrollPane.setViewportView(lineRulerTextArea);
				
		// Student text scroll pane
		JScrollPane rightTopLeftScrollPane = new JScrollPane();
		GridBagConstraints gbc_rightTopLeftScrollPane = new GridBagConstraints();
		gbc_rightTopLeftScrollPane.fill = GridBagConstraints.BOTH;
		gbc_rightTopLeftScrollPane.gridx = 1; 
		gbc_rightTopLeftScrollPane.gridy = 0;
		rightTopLeftPanel.add(rightTopLeftScrollPane, gbc_rightTopLeftScrollPane);

		rightTopLeftTextArea = new JTextArea();
		rightTopLeftTextArea.setRows(30);
		rightTopLeftTextArea.setColumns(250);
		rightTopLeftTextArea.setLineWrap(false);
		
		//"-------------rightTopLeftTextArea-------------\n"
		rightTopLeftTextArea.setText("------------- Student Text -------------\n");		
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
		rightTopRightTextArea.setLineWrap(false);
		//"-------------rightTopRightTextArea-------------\n"
		rightTopRightTextArea.setText("------------- Reference Text -------------\n");
		
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
		txtrResultOutput.setText("--- RESULTS ---"); 
		resultScrollPane.setViewportView(txtrResultOutput);
		
				
		/* ===================================== 
		 * 			Rightmost side 
		 * ===================================== */
		
		/*----Rightmost Side vertical JSplitPane----*/
		JSplitPane RightmostSideVerticalSplitPane = new JSplitPane();
		RightmostSideVerticalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		rightHorizontalSplitPane.setRightComponent(RightmostSideVerticalSplitPane);
			
		this.studentTablePanel = new StudentTablePanel();
		RightmostSideVerticalSplitPane.setLeftComponent(studentTablePanel);	
		
		/*
		 *  Rightmost Bottom
		 *  
		 */
		
		JPanel rightmostBottomPanel = new JPanel();
		RightmostSideVerticalSplitPane.setRightComponent(rightmostBottomPanel);
		GridBagLayout gbl_rightmostBottomPanel = new GridBagLayout();
		gbl_rightmostBottomPanel.columnWidths = new int[] { 200, 0 };
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
		//"----------rightmostBottomTextArea---------\n"
		rightmostBottomTextArea.setText("----------Selected Student's Results Info---------\n");
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
				String info = appControl.getSelectedStudentInfo(); 			
				rightmostBottomTextArea.setText(info);
			}
		});
		rightmostBottomButtonPanel.add(btnShowSelectedStudentButton);
		
		JButton btnSaveFeedbackTextButton = new JButton("Save Feedback Msg");
		btnSaveFeedbackTextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String stuText ="";
				stuText = rightmostBottomTextArea.getText();
				String[] txtparts = stuText.split("FEEDBACK");
				appControl.setStudentExFeedback(txtparts[txtparts.length-1]);
				System.out.println("??????? Contains Feedback Msg" + txtparts[txtparts.length-1] );
			}
		});
		rightmostBottomButtonPanel.add(btnSaveFeedbackTextButton);
		
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
					
					if(stucontent!=null){
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
					} else {
						System.out.println("???? displaySolutionCompareResults(): stucontent is NULL ");
					}
					/*
					 * HIGHLIGHTING INSERT PARTS IN REFERENCE SOLUTION
					 */
					if(refcontent!=null){
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
					} else {
						System.out.println("???? displaySolutionCompareResults(): refcontent is NULL ?? ");
					}
				}			
				
			}

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	
		bottomRightTabbedPane.setSelectedIndex(1);

	}


	public int getSelectedStudentTableRow(){
		int selectedfirstrow = this.studentTablePanel.getSelectedFirstRow();
		return selectedfirstrow;
	}
	
	public int getSelectedStudentTableCol(){
		int selectedfirstcol = this.studentTablePanel.getSelectedFirstCol();
		return selectedfirstcol;
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

	/*
	 * actionPerformed() method for Menu and Button actions
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */

	@Override
	public void itemStateChanged(ItemEvent e) {
		// NEW
		boolean selected = false;
		if(e.getStateChange() == ItemEvent.SELECTED) selected = true;
		else selected = false;
		
		if (e.getSource() == mntmCbResToExcel) {		
			appControl.setResultsWriteOption(selected);;			
			txtrConsoleOutput.append(newline + "LOG: CHECKBOX mntmCbResToExcel: STATE CHANGED TO: SELECTED=" + selected);
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());		
		} else 	if (e.getSource() == mntmCbMenuItem2) {			
			//appControl.TODO();			
			txtrConsoleOutput.append(newline + "LOG: CHECKBOX mntmCbMenuItem2: STATE CHANGED TO: SELECTED=" + selected);
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());		
		}
		
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
				//reading also student data from project excel
				appControl.openProjectExcel(mainOpenFile, true); 
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
				txtrConsoleOutput.append(newline + "--- WITH: STUDENT BASE INFO" );
				txtrConsoleOutput.append(newline + "--- NEXT: RUN STUDENT ZIP EXISTENCE CHECK! (Menu/Student/Existence check" );
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
				
				

			} else {
				System.out.println("Frame: No Open File Selected!");
			}

		} else if (arg0.getSource() == mntmOpenWithout) {
			fileChooser.setDialogTitle("OPEN PROJECT EXCEL FILE: WITHOUT STUDENT DATA");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory));
			int retVal = fileChooser.showOpenDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				mainOpenFile = fileChooser.getSelectedFile();
				System.out.println("-- Opened file: " + mainOpenFile.getPath());				
				//Application Initialization
				//NO student data from project excel
				appControl.openProjectExcel(mainOpenFile, false); 
				//Displaying TaskFlow tree
				JTree elementTree = appControl.getTaskFlowsTree();
				if (elementTree != null)
					this.getHierarchyTreeScrollPane().setViewportView(elementTree);
				
				String dir = mainOpenFile.getParent();
				this.eraProjectHomeDirectory = dir;
				this.latestOpenedFolder = dir;
				// -- Enabling other menuitems
				mntmLoadStudentData.setEnabled(true);
				//mntmInvoke.setEnabled(true);
				//mntmShowResults.setEnabled(true);
				//mntmSave.setEnabled(true); //do not use this
				
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: PROJECT EXCEL FILE OPENED: " + mainOpenFile.getName() + " - NO STUDENT DATA LOADED");
				txtrConsoleOutput.append(newline + "--- NEXT: Select File/Load Student XML Data - TO LOAD EXISTING STUDENT DATA WITH RESULTS (results.xml)");
				txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());				

			} else {
				System.out.println("Frame: No Open File Selected!");
			}

		} else if (arg0.getSource() == mntmLoadStudentData) {
			fileChooser.setDialogTitle("OPEN STUDENT DATA XML FILE");
			fileChooser.setSelectedFile(new File(""));
			fileChooser.setCurrentDirectory(new File(this.eraProjectHomeDirectory)); // + "/data"));
			int retVal = fileChooser.showOpenDialog(MainFrame.this);

			if (retVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("GUIFrame: Open OK pressed");
				File studentXMLFile = fileChooser.getSelectedFile();
				System.out.println("-- Opened file: " + studentXMLFile.getPath());		
				boolean ok = appControl.loadStudentDataFromXMLFile(studentXMLFile);
				
				/*  NOW: Displaying Student Table */
				if (ok) {
					List<List<Object>> studentRowsList = appControl.getStudentDataForTableRows();
					int rowidx = 0;
					for (List<Object> rowData : studentRowsList) {
						studentTablePanel.setOrAddStudentTableRow(rowidx, rowData);
						rowidx++;
					}
				}
		
				String dir = studentXMLFile.getParent();
				this.latestOpenedFolder = dir;
				mntmInvoke.setEnabled(true);
				mntmShowResults.setEnabled(true);
				// -- Console Printing ---				
				txtrConsoleOutput.append(newline + "LOG: STUDENT XML DATA LOADED: " + studentXMLFile.getName());
				//txtrConsoleOutput.append(newline + "--- NEXT: RUN STUDENT ZIP EXISTENCE CHECK! (Menu/Student/Existence check" );
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
						
		} else	if (arg0.getSource() == mntmCheckSolExist) {
			/* Checking existence of zips and files in zips for all students
			 * NOTE: StuSolution of some TaskFlows must be selected (Select Button) before running this command
			 */
			txtrConsoleOutput.append(newline + "LOG: CHECKING EXISTENCE OF SOLUTION ZIPS AND FILES OF ALL STUDENTS");
			txtrConsoleOutput.append(newline + "LOG: --- NOTE: stuSolution has to be selected before this command!");
			List<Integer> zipProblems = appControl.checkFileExistenceInZip();			
			
			if((zipProblems!=null)&&(!zipProblems.isEmpty())) {
				txtrConsoleOutput.append(newline + "LOG: --- ??? SOME SOLUTIONS MISSING ??? ---");
				txtrConsoleOutput.append(newline + "LOG: --- ??? OR stuSolution NOT SELECTED ??? ---" + newline + "PROBLEMS WITH [");
				for(Integer zipn : zipProblems) txtrConsoleOutput.append(" " + zipn);
				txtrConsoleOutput.append("]" + newline + "LOG: --- ??? DO NOT RUN TASKFLOWS ??? ---");
			} else {
				txtrConsoleOutput.append(newline + "LOG: --- ALL SOLUTIONS EXIST!! ---");
			}
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());
						
		} else	if (arg0.getSource() == mntmCompareSol) {
			/* Difference of selected students solution with the reference solution
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

	} else if (arg0.getSource() == mntmResultsToCsv) {
		fileChooser.setDialogTitle("SELECT XSL TRANSFORM SOURCE XML FILE (.xml) AND OUTPUT FILE (.trout/.csv)");
		
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
			System.out.println("-- TRANSFORM SOURCE AND OUTPUT files selected: # " + files.length);
			boolean ok = this.appControl.invokeSpecificTransform("results2csv",files);
			String dir = files[0].getParent();
			System.out.println("-- TRANSFORM CONTEXT parent folder: " + dir);
			this.latestOpenedFolder = dir;
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: TRANSFORM RESULTS2CSV SUCCESS(" + ok + ") FILES IN DIR:" + dir + "");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

		} else {
			System.out.println("Frame: No XML/CSV/TROUT files selected!");
		}
		fileChooser.setSelectedFile(new File(""));
		fileChooser.setMultiSelectionEnabled(false);

	} else if (arg0.getSource() == mntmResultsToStudentsXml) {
		fileChooser.setDialogTitle("SELECT XSL TRANSFORM SOURCE XML FILE (.xml) AND OUTPUT FILE (.trout)");
		
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
			System.out.println("-- TRANSFORM SOURCE AND OUTPUT files selected: # " + files.length);
			boolean ok = this.appControl.invokeSpecificTransform("results2students",files);
			String dir = files[0].getParent();
			System.out.println("-- TRANSFORM CONTEXT parent folder: " + dir);
			this.latestOpenedFolder = dir;
			//-- Console Printing
			txtrConsoleOutput.append(newline + "LOG: TRANSFORM RESULTS2STUDENTS SUCCESS(" + ok + ") FILES IN DIR:" + dir + "");
			txtrConsoleOutput.setCaretPosition(txtrConsoleOutput.getText().length());

		} else {
			System.out.println("Frame: No XML/CSV/TROUT files selected!");
		}
		fileChooser.setSelectedFile(new File(""));
		fileChooser.setMultiSelectionEnabled(false);

	}
		

	}

	public JTextArea getBottomLeftTextArea() {
		return bottomLeftTextArea;
	}

	
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
