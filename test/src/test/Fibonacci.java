package test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**This class emulates a basic Repair algorithm, compressing and decompressing a bitstring
 * @author: Andrea Giovanni Atzeni, ID 159046992
 * @version 3.0
 * @since 15-08-2016
 * */
public class Fibonacci {
	
	public static Integer fibIteration(Integer n) {
		Integer x = 0, y = 1, z = 1;
        for (int i = 0; i < n; i++) {
            x = y;
            y = z;
            z = x + y;
        }
        return x;
    }
	
	 public static void main(String[] args) throws IOException {
		 
		 FileWriter writer = new FileWriter("C:\\Users\\w1l32\\Desktop\\Fib.txt");
			 
		 //about 502k character at this point
		 //printing Fibonacci series up to number 26k
		 for(int i=1; i<=10; i++){
			
		        //System.out.print(" " + fibIteration(i) +" ");
		        writer.write(fibIteration(i) + "");
		        
		 } 
		 //System.out.print(" " + fibIteration(89) +" ");
		 writer.close();
		 buildFibonacciDictionary(BasicRePair.dictionary);
	 }
	 
		public static void buildFibonacciDictionary(Map<Integer, List<Integer>> dictionary) {
			
			int n = 10;
			
			for (int i = 2; i<n; i++) {
				
				List<Integer> list = new ArrayList<Integer>();
				
				list.add((i-2));
				list.add((i-1));
				
				dictionary.put(i, list);
	
			}
			
			for (Entry<Integer, List<Integer>> ciao : (dictionary).entrySet())
			{			
			    System.out.println(ciao.getValue() + " -> " + ciao.getKey());
			}
			
		}
}