package test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**This class creates a text file containing random bits
 * @author: Andrea Giovanni Atzeni, ID 159046992
 * @version 1.0
 * @since 23-07-2016
 * */
public class RandomString {
	
	 public static void main(String[] args) throws IOException {
		 
		 FileWriter writer = new FileWriter("C:\\Users\\w1l32\\Desktop\\Ran.txt");
		 final int max = 1048576;

		 Random randomGenerator = new Random();
		    for (int i = 0; i < max ; i++){
		      Integer randomInt = randomGenerator.nextInt(2);
		      writer.write((randomInt).toString());
		    }		 

		 writer.close();
	 }

}