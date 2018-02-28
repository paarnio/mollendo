package siima.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static String searchLineByTwoPatternsTextFile(String patternA, String patternB, String filepath) {
		/* 2017-09-17 TODO Test with .Owl file
		 * Returns the last matchlineA that is before the matchlineB.
		 * Example: Searching the line containing ontology uri using patterns:
		 * patternA = "rdf:Description rdf:about="
		 * patternB = "rdf:type rdf:resource='http://www.w3.org/2002/07/owl#Ontology'"
		 * Targeted File (.owl) fragment:
		 * <rdf:Description rdf:about="http://data.ifs.tuwien.ac.at/aml/ontology">
		 * <rdf:type rdf:resource="http://www.w3.org/2002/07/owl#Ontology"/>
		 * 
		 */
		
		String matchlineA = null;
		String matchlineB = null;
		
		try {
			// Does file exist?
			File pfile = new File(filepath);
			if (pfile.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(filepath));
				String sCurrentLine;
				while ((sCurrentLine = br.readLine()) != null) {
					// System.out.println(sCurrentLine + line_end);
					if(sCurrentLine.contains(patternA)){
						matchlineA = sCurrentLine;
					}
					if(sCurrentLine.contains(patternB)){
						matchlineB = sCurrentLine;
						System.out.println("FileUtil: matchlineB" + matchlineB);
						return matchlineA;
					}
				}
			} else {
				System.out.println("- FileUtil:searchLineByTwoPatternsTextFile File does not exist: " + filepath);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static List<String> searchLinesTextFile(String searchStr, String filepath) {
		StringBuffer sbuf = new StringBuffer();
		List<String> matchlines = new ArrayList<String>();
		String jsonString;
		try {
			// Does file exist?
			File pfile = new File(filepath);
			if (pfile.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(filepath));
				String sCurrentLine;
				while ((sCurrentLine = br.readLine()) != null) {
					// System.out.println(sCurrentLine + line_end);
					//sbuf.append(sCurrentLine + line_end);
					if(sCurrentLine.contains(searchStr)){
						matchlines.add(sCurrentLine);
					}
				}
			} else {
				System.out.println("- FileUtil:searchLinesTextFile() File does not exist: " + filepath);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return matchlines;
	}

	
	public static StringBuffer readTextFile(String line_end, String filepath) {
		StringBuffer sbuf = new StringBuffer();
		String jsonString;
		try {
			// Does file exist?
			File pfile = new File(filepath);
			if (pfile.exists()) {

				BufferedReader br = new BufferedReader(new FileReader(filepath));
				String sCurrentLine;
				while ((sCurrentLine = br.readLine()) != null) {
					// System.out.println(sCurrentLine + line_end);
					sbuf.append(sCurrentLine + line_end);
				}
			} else {
				System.out.println("- FileUtil:readTextFile() File does not exist: " + filepath);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sbuf;
	}

	public static void writeTextFile(String text, String filepath) {
		File fl = new File(filepath);

		try {
			// FileOutputStream fout=new FileOutputStream(fl);
			// BufferedOutputStream bfout=new BufferedOutputStream(fout);
			FileWriter fwr = new FileWriter(fl);
			fwr.write(text);
			fwr.flush();
			fwr.close();

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e2) {
			
			e2.printStackTrace();
		}

	}

	
	
	public static void main(String[] args) {
		
		FileUtil.writeTextFile("teksti", "./data/generated/asp_models/asp_testresults.txt");
	}

}
