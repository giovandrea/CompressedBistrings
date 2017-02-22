package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**This class permits to read and write binary files
 * @author: Andrea Giovanni Atzeni, ID 159046992
 * @version 1.0
 * @since 23-07-2016
 * */
public class BinaryFile {

	public static void main(String[] args) throws IOException {
		//SmallBinaryFiles binary = new BinaryFiles();
		
		//byte[] fileContents = binary.read("C:\\Users\\Andrea\\Desktop\\dblp_100.txt", 1024);
		//binary.write(fileContents, "C:\\Users\\Andrea\\Desktop\\YourText.txt");
	}
	  
	 /**This function reads a binary file
	  * @param inputFile                   the file to be read
	  * @param length                      the length to be read
	  * @exception FileNotFoundException   if a text file is missing
	  * @exception IOException             if a text file is missing (?)
	  * @return                            a byte array containing part of the file
	  * */
	  byte[] read(String inputFile, int length) throws FileNotFoundException, IOException {
	    //log("Reading in binary file named : " + aInputFileName);
	    File file = new File(inputFile);
	    //log("File size: " + file.length());
	    byte[] result = new byte[length]; //checked it until 20k
	    
	    try {
	      InputStream input = null;
	      try {
	        int totalBytesRead = 0;
	        input = new BufferedInputStream(new FileInputStream(file));
	        while(totalBytesRead < result.length){
	          int bytesRemaining = result.length - totalBytesRead;
	          int bytesRead = input.read(result, totalBytesRead, bytesRemaining);	          
	          if (bytesRead > 0){
	            totalBytesRead = totalBytesRead + bytesRead;
	          }
	        }
	      }
	      finally {
	        input.close();
	      }

	    }
	    catch (FileNotFoundException ex) {
	      log("File not found.");
	    }
	    catch (IOException ex) {
	      log(ex);
	    }
	    return result;
	  }
	  
	  
	 /**This function writes a binary file
	  * @param input                      byte array to be written
	  * @param outputFile                 the file where the information will be stored
	  * @exception InterruptedException   if a text file is missing
	  * @exception IOException            if a text file is missing
	  * */
	  void write(byte[] input, String outputFile) throws FileNotFoundException, IOException {
	    try {
	      OutputStream output = null;
	      try {
	        output = new BufferedOutputStream(new FileOutputStream(outputFile));
	        output.write(input);
	      }
	      finally {
	        output.close();
	      }
	    }
	    catch(FileNotFoundException ex){
	      log("File not found.");
	    }
	    catch(IOException ex){
	      log(ex);
	    }
	  }
	  
	  private static void log(Object aThing){
		    System.out.println(String.valueOf(aThing));
		  }
	}