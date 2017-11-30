/* ZipFileReader.java
 * See project: MiscAndZips ZipFileReader.java
 * from:https://stackoverflow.com/questions/36548755/read-zip-file-content-without-extracting-in-java
 */

package siima.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;


public class ZipFileReader {

	public StringBuffer textcontents = new StringBuffer();
	
	/* NEW */
	
	public String readTxtFile(String zipFilePath, String txtFilePathInZip) {
		//
		String txt = null;
		StringBuilder strbuilder;
		ZipFile zip;
		try {
			zip = new ZipFile(zipFilePath);
			InputStream instream = readInputStreamFromZipFile(txtFilePathInZip, zip, null);
			if (instream != null) {
				strbuilder = getTxtFiles(instream);
				txt = strbuilder.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return txt;
	}

	public InputStream readInputStreamFromZipFile(String fullPathInZip, ZipFile zip, String fileExt) {
		/* Simple way to do it (Searching only the specified path)
		 * Param: fileExt. If not null, checks the file extension 
		 * (e.g.fileExt = "xsl" or "xml") 
		 * (See also: readStreamFromZipFile())
		 **/		
		InputStream inputstream = null;
		try {
			ZipEntry entry = zip.getEntry(fullPathInZip);
			//System.out.println("ZFREADER(): fullPathInZip: " + fullPathInZip + " entry: " + entry);
			if((fileExt==null)||(entry.getName().endsWith(fileExt))) {
				inputstream = zip.getInputStream(zip.getEntry(fullPathInZip));
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return inputstream;
	}
	
	public InputStream readInputStreamFromZipFile(String filename, String dir, ZipFile zip) {
		// Reading stream (e.g textual). Searching everywhere if needed
		Boolean dirOK = false;
		try {
			/* 
			 * If dir == null searches the file from every directory
			*/
			for (Enumeration e = zip.entries(); e.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) e.nextElement();

				if (entry.isDirectory()) { // Directory search
					if (entry.getName().equals(dir)) {
						dirOK = true;
						System.out.println("--+ Directory found: " + entry.getName());
					} else {
						dirOK = false;
					}

				} else if ((dir==null)|| (dirOK)) { 
					// File search from the correct Directory
					if (entry.getName().endsWith(filename)) {

						System.out.println("--+ FOUND FILE " + entry.getName());
						InputStream inputstream = zip.getInputStream(entry);
						return inputstream;
					}
				}
			}

		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		return null;
	}
	
	public void readAndCheckZipFileContent(ZipFile zip) { // orig. readZipFile
		/* Goes through all directories and files
		 * Text files and image files
		 * */
		try {
			for (Enumeration e = zip.entries(); e.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) e.nextElement();
				if (!entry.isDirectory()) {
					if (entry.getName().endsWith("png")) {

						byte[] image = getImage(zip.getInputStream(entry));
						System.out.println("PNG image " + entry.getName() + " byte length:\n" + image.length);
						// do your thing
					} else if (entry.getName().endsWith("txt")) {
						StringBuilder out = getTxtFiles(zip.getInputStream(entry));
						// do your thing
						textcontents.append(out.toString());
						System.out.println("TXT FILE " + entry.getName() +" CONTENTS:\n" + out);
					} else if (entry.getName().endsWith("xml")) {
						StringBuilder out = getTxtFiles(zip.getInputStream(entry));
						// do your thing
						textcontents.append(out.toString());
						System.out.println("XML FILE " + entry.getName() +" CONTENTS:\n" + out);
					} else if (entry.getName().endsWith("xsl")) {
						StringBuilder out = getTxtFiles(zip.getInputStream(entry));
						// do your thing
						textcontents.append(out.toString());
						System.out.println("XSL FILE " + entry.getName() +" CONTENTS:\n" + out);
					}
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public StringBuilder getTxtFiles(InputStream in) {
		StringBuilder out = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
		} catch (IOException e) {
			// do something, probably not a text file
			e.printStackTrace();
		}
		return out;
	}

	private byte[] getImage(InputStream in) {
		try {
			BufferedImage image = ImageIO.read(in); // just checking if the
													// InputStream belongs in
													// fact to an image
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			// do something, it is not a image
			e.printStackTrace();
		}
		return null;
	}

	
	public static void main(String[] args) {
		
			/*ZipFile zip = new ZipFile("./data/zips/test.zip");			
			ZipFileReader zipper = new ZipFileReader();
			zipper.readAndCheckZipFileContent(zip);
			*/
			
			try {
			 
			 String zipFilePath = "./data/zips/RoundU1_sub2_123000.zip";
			 String directoryInZip = "RoundU1_sub2_123000/exU1E1_1/src/";
			 String fileInZip = "plant_catalog.xml";
			 String fullPathInZip = directoryInZip + fileInZip;
			 
			ZipFile zip = new ZipFile("./data/zips/RoundU1_sub2_123000.zip");			
			ZipFileReader zipper = new ZipFileReader();
			InputStream inputstream = zipper.readInputStreamFromZipFile(fullPathInZip, zip, null);
			StringBuilder strb = zipper.getTxtFiles(inputstream);
			System.out.println("FILE CONTENTS:\n" + strb.toString());
			} catch (IOException e) {
			e.printStackTrace();
			}
			

	}

}
