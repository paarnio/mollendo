/*
 * based on: diff_match_patch_test.java
 * loaded: C:\programs\java\google_code\diff_match_patch_20121119\diff_match_patch_20121119\java
 * from:
 * https://code.google.com/archive/p/google-diff-match-patch/wikis/LineOrWordDiffs.wiki
 * 
 * See demos:
 * file:///C:/programs/java/google_code/diff_match_patch_20121119/diff_match_patch_20121119/demos/demo_diff.html 
 */
package siima.utils;

import java.util.LinkedList;

import siima.utils.diff_match_patch.Diff;
import siima.utils.diff_match_patch.Operation;

public class Testing_diff_match_patch {
	
	public static String textlines1= "I am the very model of a modern Major-General,"
			+ "\n I've information vegetable, animal, and mineral,"
			+ "\n in order categorical.";
	public static String textlines2= "I am the very model of a cartoon individual,"
			+ "\n My animation's comical, unusual, and whimsical,"
			+ "\n in order categorical.";
	
	private diff_match_patch dmp;
	  private diff_match_patch.Operation DELETE = diff_match_patch.Operation.DELETE;
	  private diff_match_patch.Operation EQUAL = diff_match_patch.Operation.EQUAL;
	  private diff_match_patch.Operation INSERT = diff_match_patch.Operation.INSERT;

	  protected void setUp() {
	    // Create an instance of the diff_match_patch object.
	    dmp = new diff_match_patch();
	  }

	  public void testingDiffMain() {
		  //https://code.google.com/archive/p/google-diff-match-patch/wikis/LineOrWordDiffs.wiki
		  /**
		   * The data structure representing a diff is a Linked list of Diff objects:
		   * {Diff(Operation.DELETE, "Hello"), Diff(Operation.INSERT, "Goodbye"),
		   *  Diff(Operation.EQUAL, " world.")}
		   * which means: delete "Hello", add "Goodbye" and keep " world."
		   */
		  System.out.println("\n-------testingDiffMain:-------");
		  LinkedList<Diff> diffslist = dmp.diff_main(textlines1, textlines2);
		  dmp.diff_cleanupSemantic(diffslist); //TEST: ilman tätä tulee pitkä lista diff:ä
		  
		  for (Diff diff : diffslist){
			  Operation op = diff.operation;
			  String txt = diff.text;
			  System.out.println("testingDiffMain: op: " + op.name() + " diff.text: " + txt);
		  }
		  
	  }
	  
	  public int testingMatchMain() {
		  /**
		   * Locate the best instance of 'pattern' in 'text' near 'loc'.
		   * Returns -1 if no match found.
		   * @param text The text to search.
		   * @param pattern The pattern to search for.
		   * @param loc The location to search around.
		   * @return Best match index or -1.
		   */
		System.out.println("\n-------testingMatchMain:-------");
		String pattern = "modern";
		// int match_main(String text, String pattern, int loc)
		int idx = dmp.match_main(textlines1, pattern, 20);

		System.out.println("testingMatchMain: location idx: " + idx);

		int len = pattern.length();
		System.out.println("testingMatchMain: found word: " + textlines1.substring(idx, idx + len - 1));

		return idx;
	}
	  
	  //TODO:  DIFF TEST FUNCTIONS (from diff_match_patch_test.java .. -Copy.txt)


	  public void testDiffCommonPrefix() {
	    // Detect any common prefix.
	    System.out.println("diff_commonPrefix: Null case., 0," + dmp.diff_commonPrefix("abc", "xyz"));

	    System.out.println("diff_commonPrefix: Non-null case., 4," + dmp.diff_commonPrefix("1234abcdef", "1234xyz"));

	    System.out.println("diff_commonPrefix: Whole case., 4," + dmp.diff_commonPrefix("1234", "1234xyz"));
	  }
	  
	  
	  
	public static void main(String[] args) {
		Testing_diff_match_patch mydmp = new Testing_diff_match_patch();
		mydmp.setUp();
		mydmp.testDiffCommonPrefix();
		
		mydmp.testingDiffMain();
		
		int idx = mydmp.testingMatchMain();
		String pattern = "modern";
		int len = pattern.length();
		System.out.println("--MAIN: " + mydmp.textlines1.substring(idx, idx+len-1));
	}

}
