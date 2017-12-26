/*
 * 
 * /https://code.google.com/archive/p/google-diff-match-patch/wikis/LineOrWordDiffs.wiki
 */
package siima.app.control;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

import siima.app.operator.XMLValidationCheck;
import siima.utils.diff_match_patch;
import siima.utils.diff_match_patch.Diff;
import siima.utils.diff_match_patch.Operation;

public class TextCompareController {
	private static final Logger logger=Logger.getLogger(TextCompareController.class.getName());

	private diff_match_patch dmp;
	private diff_match_patch.Operation DELETE = diff_match_patch.Operation.DELETE;
	private diff_match_patch.Operation EQUAL = diff_match_patch.Operation.EQUAL;
	private diff_match_patch.Operation INSERT = diff_match_patch.Operation.INSERT;

	private LinkedList<Diff> textDiffsList;

	protected void setUp() {
		// Create an instance of the diff_match_patch object.
		dmp = new diff_match_patch();
	}

	public StringBuffer getFilteredResults(String filtDiffOper, int minLength, int cutLength) {
		/*
		 * filtDiffOper e.g. DELETE | EQUAL | INSERT | DELETE_INSERT | ALL
		 * NOTE: ignoring space char differences
		 */
		StringBuffer diffResultBuf = new StringBuffer();
		
		//diffResultBuf.append("TEXT COMPARE:");
		for (Diff diff : this.textDiffsList) {
			Operation op = diff.operation;
			String txt = diff.text;
			String prefix="?";
			if("DELETE".equals(op.name()))prefix="DEL#(";
			if("INSERT".equals(op.name()))prefix="INS#(";
			if("EQUAL".equals(op.name()))prefix="EQU#(";
			if (filtDiffOper != null) {
				if ((filtDiffOper.equalsIgnoreCase(op.name())) || (filtDiffOper.equalsIgnoreCase("ALL"))
						|| (filtDiffOper.contains(op.name()))) {
					if (txt.length() >= minLength) {
						String cutText = txt;					
						
						if (!" ".equals(cutText)) {//ignore space
							if (txt.length() > cutLength)
								cutText = txt.substring(0, cutLength);
							
							diffResultBuf.append(prefix + cutText + ")#");

						}
					}
				}
			}
		}

		return diffResultBuf;
	}

	  public boolean compareTextLines(String textlines1, String textlines2){
		  logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: compareTextLines()");
		  boolean isequal = true;
		  
		  isequal = runDiffMain(textlines1, textlines2);
		  
		  return isequal;
	  }
	  
	  
	  private boolean runDiffMain(String textlines1, String textlines2) {		  
		  /* 
		   * textlines1: student text
		   * textlines2: reference text
		   * 
		   * The data structure representing a diff is a Linked list of Diff objects:
		   * {Diff(Operation.DELETE, "Hello"), Diff(Operation.INSERT, "Goodbye"),
		   *  Diff(Operation.EQUAL, " world.")}
		   * which means: delete "Hello", add "Goodbye" and keep " world."
		   */
		  logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: runDiffMain()");
			
		  boolean isequal = true;
		  //System.out.println("\n---TextCompareController: runDiffMain()");
		  LinkedList<Diff> diffslist = dmp.diff_main(textlines1, textlines2);
		  dmp.diff_cleanupSemantic(diffslist);
		  
		  this.textDiffsList = diffslist;
		  
		  for (Diff diff : diffslist){
			  Operation op = diff.operation;
			  String txt = diff.text;
			  if(!"EQUAL".equals(op.name())){
				  isequal=false;
				  System.out.println("runDiffMain(): op: " + op.name() + " diff.text: " + txt);
			  }
		  }
		  
		  int leven = dmp.diff_levenshtein(diffslist);
		  System.out.println("???????diff_levenshtein(): (" + leven + ")");
		  return isequal;
	  }
	  
	  
	  
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
