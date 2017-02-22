package test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class lunghezza {
	
	 public static void main(String[] args) throws IOException {
		 
		 Scanner file = new Scanner(new FileReader("C:\\Users\\w1l32\\Desktop\\Capon.txt"));
		 String initial = file.nextLine();
		 file.close();
		 
		 
		 System.out.println (initial.length());
	 }
	 
	 public static void length() {
			List<Integer> prova = new Stack<Integer>();
			
			prova.add(1);
			prova.add(0);
			
			prova.forEach(System.out::println);
	 }

}
