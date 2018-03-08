/* StudentTablePanel.java
 * BASED ON: TableSelectionDemo.java
 * https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
 * 
 */

package siima.app.gui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.GridLayout;
import java.awt.Dimension;
 
public class StudentTablePanel extends JPanel 
                                implements ActionListener { 
	private static final long serialVersionUID = 1L;
	private JTable table;
    private JCheckBox rowCheck;
    private JCheckBox columnCheck;
    private JCheckBox cellCheck;
    private ButtonGroup buttonGroup;
    private JTextArea output;
    
    //VPA:
    private int selectedFirstRow=-1;
    private int selectedLastRow=-1;
    private int selectedFirstCol=-1;
    private int selectedLastCol=-1;
    
    public int getSelectedFirstRow() {
		return selectedFirstRow;
	}

	public void setSelectedFirstRow(int selectedFirstRow) {
		this.selectedFirstRow = selectedFirstRow;
	}

	public int getSelectedLastRow() {
		return selectedLastRow;
	}

	public void setSelectedLastRow(int selectedLastRow) {
		this.selectedLastRow = selectedLastRow;
	}

	
	public int getSelectedFirstCol() {
		return selectedFirstCol;
	}

	public void setSelectedFirstCol(int selectedFirstCol) {
		this.selectedFirstCol = selectedFirstCol;
	}

	public int getSelectedLastCol() {
		return selectedLastCol;
	}

	public void setSelectedLastCol(int selectedLastCol) {
		this.selectedLastCol = selectedLastCol;
	}
	
	
	/*
	 * Constructor
	 */

	public StudentTablePanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
 
        table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(400, 300)); //(500, 70)
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.getColumnModel().getSelectionModel().
            addListSelectionListener(new ColumnListener());
        add(new JScrollPane(table));
 
        add(new JLabel("Selection Mode"));
        buttonGroup = new ButtonGroup();
        addRadio("Multiple Interval Selection").setSelected(true);
        addRadio("Single Selection");
        addRadio("Single Interval Selection");
 
        add(new JLabel("Selection Options"));
        rowCheck = addCheckBox("Row Selection");
        rowCheck.setSelected(true);
        columnCheck = addCheckBox("Column Selection");
        cellCheck = addCheckBox("Cell Selection");
        cellCheck.setEnabled(false);
 
        output = new JTextArea(5, 40);
        output.setEditable(false);
        add(new JScrollPane(output));
    }
    
    public void addStudentTableRow(List<Object> newRowData){
    	MyTableModel mytablemodel = (MyTableModel)this.table.getModel();
    	mytablemodel.addRowToTable(newRowData);
    }
    
    public void setOrAddStudentTableRow(int rowidx, List<Object> newRowData){
    	MyTableModel mytablemodel = (MyTableModel)this.table.getModel();
    	mytablemodel.setOrAddRowToTable(rowidx, newRowData);
    }
 
    private JCheckBox addCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.addActionListener(this);
        add(checkBox);
        return checkBox;
    }
 
    private JRadioButton addRadio(String text) {
        JRadioButton b = new JRadioButton(text);
        b.addActionListener(this);
        buttonGroup.add(b);
        add(b);
        return b;
    }
 
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        //Cell selection is disabled in Multiple Interval Selection
        //mode. The enabled state of cellCheck is a convenient flag
        //for this status.
        if ("Row Selection" == command) {
            table.setRowSelectionAllowed(rowCheck.isSelected());
            //In MIS mode, column selection allowed must be the
            //opposite of row selection allowed.
            if (!cellCheck.isEnabled()) {
                table.setColumnSelectionAllowed(!rowCheck.isSelected());
            }
        } else if ("Column Selection" == command) {
            table.setColumnSelectionAllowed(columnCheck.isSelected());
            //In MIS mode, row selection allowed must be the
            //opposite of column selection allowed.
            if (!cellCheck.isEnabled()) {
                table.setRowSelectionAllowed(!columnCheck.isSelected());
            }
        } else if ("Cell Selection" == command) {
            table.setCellSelectionEnabled(cellCheck.isSelected());
        } else if ("Multiple Interval Selection" == command) { 
            table.setSelectionMode(
                    ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //If cell selection is on, turn it off.
            if (cellCheck.isSelected()) {
                cellCheck.setSelected(false);
                table.setCellSelectionEnabled(false);
            }
            //And don't let it be turned back on.
            cellCheck.setEnabled(false);
        } else if ("Single Interval Selection" == command) {
            table.setSelectionMode(
                    ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            //Cell selection is ok in this mode.
            cellCheck.setEnabled(true);
        } else if ("Single Selection" == command) {
            table.setSelectionMode(
                    ListSelectionModel.SINGLE_SELECTION);
            //Cell selection is ok in this mode.
            cellCheck.setEnabled(true);
        }
 
        //Update checkboxes to reflect selection mode side effects.
        rowCheck.setSelected(table.getRowSelectionAllowed());
        columnCheck.setSelected(table.getColumnSelectionAllowed());
        if (cellCheck.isEnabled()) {
            cellCheck.setSelected(table.getCellSelectionEnabled());
        }
        
        //VPA
        setSelectedFirstRow(-1); //clear
        setSelectedLastRow(-1); //clear
        setSelectedFirstCol(-1); //clear
        setSelectedLastCol(-1); //clear
    }
 
    private void outputSelection() {
        output.append(String.format("Lead: %d, %d. ",
                    table.getSelectionModel().getLeadSelectionIndex(),
                    table.getColumnModel().getSelectionModel().
                        getLeadSelectionIndex()));
        output.append("Rows:");       
        boolean firstRow = true;
        for (int c : table.getSelectedRows()) {
            output.append(String.format(" %d", c));
            //VPA:
            if(firstRow) setSelectedFirstRow(c);
            setSelectedLastRow(c);
            firstRow = false;
        }
        output.append(". Columns:");
        boolean firstCol = true;
        for (int c : table.getSelectedColumns()) {
            output.append(String.format(" %d", c));
          //VPA:
            if(firstCol) setSelectedFirstCol(c);
            setSelectedLastCol(c);
            firstCol = false;
        }
        output.append(".\n");
    }
 
    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("ROW SELECTION EVENT. ");
            outputSelection();
        }
    }
 
    private class ColumnListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("COLUMN SELECTION EVENT. ");
            outputSelection();
        }
    }
 
    class MyTableModel extends AbstractTableModel {
    	private static final long serialVersionUID = 1L;
        private String[] columnNames = {"Nr","Surname","First Name","Student ID","E1","E2","E3","E4","E5","E6","Total" };      
        public MyTableModel(){
        	
        	  List<Object> orderList = new ArrayList<Object>();
        	  List<Object> lastNameList = new ArrayList<Object>();
              List<Object> firstNameList = new ArrayList<Object>();
              List<Object> studentIDList = new ArrayList<Object>();
              List<Object> listP1 = new ArrayList<Object>();
              List<Object> listP2 = new ArrayList<Object>();
              List<Object> listP3 = new ArrayList<Object>();
              List<Object> listP4 = new ArrayList<Object>();
              List<Object> listP5 = new ArrayList<Object>();
              List<Object> listP6 = new ArrayList<Object>();
              List<Object> totalList = new ArrayList<Object>();
           
              orderList.add("");
              lastNameList.add("");
              firstNameList.add("");
              studentIDList.add("");
              listP1.add("");
              listP2.add("");
              listP3.add("");
              listP4.add("");
              listP5.add("");
              listP6.add("");
              totalList.add("");
              
              this.listOfColumnLists.add(orderList);
              this.listOfColumnLists.add(lastNameList);
              this.listOfColumnLists.add(firstNameList);
              this.listOfColumnLists.add(studentIDList);
              this.listOfColumnLists.add(listP1);
              this.listOfColumnLists.add(listP2);
              this.listOfColumnLists.add(listP3);
              this.listOfColumnLists.add(listP4);
              this.listOfColumnLists.add(listP5);
              this.listOfColumnLists.add(listP6);
              this.listOfColumnLists.add(totalList);
        }
        
      //My new Table data
        private List<List<Object>> listOfColumnLists = new ArrayList<List<Object>>();
        
        public void addRowToTable(List<Object> newRowData){
        	/*
        	 * NOTE: Row length and cell value type must match with column value type
        	 */

			if (newRowData.size() == this.listOfColumnLists.size()) {
				for (int coli = 0; coli < this.listOfColumnLists.size(); coli++) {
					List<Object> collist = this.listOfColumnLists.get(coli);
					collist.add(newRowData.get(coli));
				}
				fireTableStructureChanged();
			}
		}
        
        public void setOrAddRowToTable(int rowidx, List<Object> newRowData){
        	/* IF rowidx < row count THEN overwrite the row
        	 * OTHERWISE add a new row 
        	 * NOTE: Row length and cell value type must match with column value type
        	 */

			if (newRowData.size() == this.listOfColumnLists.size()) {

				if (rowidx < this.listOfColumnLists.get(0).size()) {
					// Overwrite a row
					for (int coli = 0; coli < this.listOfColumnLists.size(); coli++) {
						List<Object> collist = this.listOfColumnLists.get(coli);
						collist.set(rowidx, newRowData.get(coli));
					}
					fireTableStructureChanged();
				} else {
					// add a new row
					for (int coli = 0; coli < this.listOfColumnLists.size(); coli++) {
						List<Object> collist = this.listOfColumnLists.get(coli);
						collist.add(newRowData.get(coli));
					}
					fireTableStructureChanged();
				}
			}
		}
        
       
        public int getColumnCount() {
            return columnNames.length;
        }
 
        public int getRowCount() {
        	return this.listOfColumnLists.get(0).size();
        }
 
        public String getColumnName(int col) {
            return columnNames[col];
        }
 
        public Object getValueAt(int row, int col) {      	
        	return this.listOfColumnLists.get(col).get(row);       	
        }
 
        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
 
        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }
 
        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {        	
        	this.listOfColumnLists.get(col).set(row, value); 
            fireTableCellUpdated(row, col);
        }
        
    }
 
}