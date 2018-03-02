/* TextCompareController.java
 * 
 * TODO: Use the calculated Levenshtein distance value somewhere?
 * /https://code.google.com/archive/p/google-diff-match-patch/wikis/LineOrWordDiffs.wiki
 */
package siima.app.control;

import java.util.LinkedList;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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

	public StringBuffer getFilteredResults(String filtDiffOper, int minLength, int cutLength, String ignore) {
		/*
		 * filtDiffOper e.g. DELETE | EQUAL | INSERT | DELETE_INSERT | ALL
		 * NOTE:  e.g.  if ignore =" " single space char differences ignored
		 * 
		 * Output pattern: "DEL#(delete this text fraq)#EQU#(this text fraq is equal)#INS#(insert this fraq)#..
		 */
		StringBuffer diffResultBuf = new StringBuffer();
		
		//diffResultBuf.append("TEXT COMPARE:");
		if(this.textDiffsList!=null){
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
						
						if((ignore==null)||(!ignore.equals(cutText))) {//ignore e.g. space
							if (txt.length() > cutLength)
								cutText = txt.substring(0, cutLength);							
								diffResultBuf.append(prefix + cutText + ")#");

						}
					}
				}
			}
		}
		} else {
			diffResultBuf.append("*TEXT COMPARE:TEXT_NULL_OR_EMPTY*");
		}
		return diffResultBuf;
	}

	  public boolean compareTextLines(String textlines1, String textlines2){
		  logger.log(Level.INFO, "Entering: method: compareTextLines()");
		  boolean isequal = false;
		  if((textlines1!=null)&&(textlines2!=null)&&(!textlines1.isEmpty())&&(!textlines2.isEmpty())){			 
			  isequal = runDiffMain(textlines1, textlines2);			  
		  } else {
			  this.textDiffsList = null; //new
			  System.out.println("??????compareTextLines() ERROR: textlines1(" + textlines1 + ") textlines2(" + textlines2 + ")");
			  logger.log(Level.ERROR, "method:compareTextLines():One of the textlines is NULL or EMPTY. Cannot be compared!"); 
		  }
	
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
		  logger.log(Level.INFO, "Entering: method: runDiffMain()");
		  this.textDiffsList = null;
		  boolean isequal = true;
		  
		  if((textlines1==null)||(textlines2==null)||(textlines1.isEmpty())||(textlines2.isEmpty())){
			  logger.log(Level.ERROR, "method:runDiffMain():One of the textlines is NULL or EMPTY. Cannot be compared!"); 
			  
		  }
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
		  //TODO: Use this Levenshtein value somewhere
		  int leven = dmp.diff_levenshtein(diffslist);
		  System.out.println("???????diff_levenshtein(): (" + leven + ")");
		  return isequal;
	  }
	  

}
