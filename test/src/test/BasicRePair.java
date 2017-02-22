package test;

import java.io.*;
import static java.util.Collections.reverseOrder;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import java.util.stream.IntStream;


/**This class emulates a basic Repair algorithm, compressing and decompressing a bitstring
 * @author: Andrea Giovanni Atzeni, ID 159046992
 * @version 4.0
 * @since 19-08-2016
 * */
public class BasicRePair {
	
	static Integer k=2;
	//this is the map containing in decreased order the number of occurences
	static Map<List<Integer>, Integer> orderedOccurrences = new HashMap<List<Integer>, Integer>();
		
	//this is the map containing the compressing dictionary
	static Map<Integer, List<Integer>> dictionary = new HashMap<Integer, List<Integer>>();
	
	static Map.Entry<List<Integer>, Integer> first;
	static long startTime = System.nanoTime();
	static Integer length = 262144;
	
	
	/**The main method, which outputs every step of the algorithm in a text file
	 * @exception InterruptedException   if some thread is interrupted
	 * @exception IOException            failed or interrupted I/O operations
	 * */
	public static void main (String[] args) throws InterruptedException, IOException {
		
		BinaryFile test = new BinaryFile();
		
		//Fib();
		
		byte[] contents = test.read("C:\\Users\\w1l32\\Desktop\\Capon.txt", length);
		//test.write(contents, "C:\\Users\\w1l32\\Desktop\\YourFile.txt");
		Integer[] array= new Integer[contents.length];
		
		for (int i=0; i<contents.length; i++) {
			//converting from ASCII to int
			array[i]=Character.getNumericValue((char)contents[i]);
			//System.out.println(array[i]);
		}
		/*
		Writer writer = new FileWriter("C:\\Users\\w1l32\\Desktop\\input.txt");
		
		for (int i = 100; i<150; i++)
			writer.write("posizione " + i + " " + array[i] + "\r\n");
		writer.close();*/
		
		//garbage collecting
		contents = null;
		test = null;
			
		System.out.println("Would you like to continue until there is only one symbol left or use the original RePair?");
		System.out.println("Press 1 for one symbol left, press 2 for original RePair:");
		
		int a;
		@SuppressWarnings("resource")
		Scanner choice = new Scanner(System.in);
		a = choice.nextInt();
		
		switch (a) {
		case 1: {
			while (array.length > 1) {
				
				orderedOccurrences = Scan(array);
				//Thread.sleep(1000);
				first = createCompression(orderedOccurrences, array, 2);
				//garbage collecting 
				orderedOccurrences=null;
				array = compress(array, first);
			}
			
			end(array);
			break;
		}
		case 2: {
			while (array.length > 1) {
				
				orderedOccurrences = Scan(array);
				first = createCompression(orderedOccurrences, array, 1);
				//garbage collecting 
				orderedOccurrences=null;
				array = compress(array, first);
			}
			
			end(array);
			break;
		}
		default: {
			System.out.println("You inserted a wrong option.");
			break;
		}
		}		
	}
	
	
	/**This function, giving a value, return the key containing that value
	 * @param map     the map containing the keys and the values
	 * @param value   the value to be searched
	 * @return        the key of that value
	 * */
	public static <K,V> K key(Map<K,V> map, V value) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	
	/**This function takes every pair in the input and counts them, putting the stuff into a Map
	 * @param input                      this is the input from a text file
	 * @param writer                     this is the output text file
	 * @exception InterruptedException   if some thread is interrupted
	 * @exception IOException            failed or interrupted I/O operations
	 * @return                           the ordered map containing the occurrences
	 * */
	public static Map<List<Integer>, Integer> Scan (Integer[] input) throws InterruptedException, IOException {

		Map<List<Integer>, Integer> gr = new HashMap<List<Integer>, Integer>();
		
		
		for (int i=0; i<input.length; i++) {			
			
			try{

				
				Integer count;
				List <Integer> pr = new ArrayList<Integer>();				
				
				pr.add(input[i]);
				pr.add(input[i+1]);
										
				if (gr.containsKey(pr)) {
					count = gr.get(pr);
					gr.replace(pr,count,count+1);
				}
				else {
					gr.put(pr, 1);
				}
				
				}
			catch(Exception e){
				
			}
		}
		/*
		//this prints out and writes in the text file the occurrences
		for (Entry<List<Integer>, Integer> entry : (gr).entrySet())
		{
			
		    System.out.println(entry.getKey() + " appears " + entry.getValue() + " times" );
		}*/
		
		//garbage collecting
		input = null;
		
		return sortByValue(gr);
	}
	
	
	/**This function creates a compression string taking the most frequent entry from the map
	 * @param counter                    this is the map containing the appearance of occurrences in decreased order
	 * @param array                      this is the input from a text file
	 * @param writer                     this is the output text file
	 * @exception InterruptedException   if some thread is interrupted
	 * @exception IOException            failed or interrupted I/O operations
	 * @return 							 the first entry of the map
	 * */	
	public static Entry<List<Integer>, Integer> createCompression(Map<List<Integer>, Integer> counter, Integer[] array, int choice) throws InterruptedException, IOException {
		
		//this is to get the first entry of the map (the one with highest occurrences)
		Entry<List<Integer>, Integer> entry=counter.entrySet().iterator().next();
		
		switch (choice) {
		case 1: { 		
			//this stops the compression if there is only one occurrence of each pair	
			if (entry.getValue().equals(1))
				
				end(array);	
			
			if (!dictionary.containsValue(entry.getKey())) {
				dictionary.put(k++, entry.getKey());
			}
			/*
			System.out.println("I'm printing out the content of the dictionary:");
			
			for (Entry<Integer, List<Integer>> ciao : (dictionary).entrySet())
			{			
			    System.out.println(ciao.getValue() + " -> " + ciao.getKey());
			}*/
			
			//garbage collecting
			counter = null;
			
			return entry;
			
		} 
		case 2: { 
			//populating the compressing dictionary
			if (!dictionary.containsValue(entry.getKey())) {
				dictionary.put(k++, entry.getKey());
			}
			/*
			System.out.println("I'm printing out the content of the dictionary:");
			
			for (Entry<Integer, List<Integer>> ciao : (dictionary).entrySet())
			{			
			    System.out.println(ciao.getValue() + " -> " + ciao.getKey());
			}*/
			
			//garbage collecting
			counter = null;
			
			return entry;
			
		} 
		default: System.out.println ("Something went wrong."); break;
		}
				
		return entry;
	}
	
	
	/**This function compresses the input, using all the previous information
	 * @param input                      this is the input from a text file
	 * @param entry						 this is the most frequent pair in the text
	 * @param writer                     this is the output text file
	 * @exception InterruptedException   if some thread is interrupted
	 * @exception IOException            failed or interrupted I/O operations
	 * @return 						     the compressed array
	 * */	
	public static Integer[] compress (Integer[] input, Entry<List<Integer>, Integer> entry) throws InterruptedException, IOException{
		
		Integer[] array = new Integer[input.length];
					
		System.out.println("I'm inserting " + key(dictionary, entry.getKey()) + " instead of " + entry.getKey());
		//t(1500);
		for (int i=0; i<input.length; i++) {
			try {
				List <Integer> pr = new Stack<Integer>();
				
				pr.add(input[i]);
				pr.add(input[i+1]);
				
			if (entry.getKey().equals(pr)) {
				array[i]= key(dictionary, pr);
				i++;
				}
			else array[i]=input[i];
			} catch (Exception e){
				array[i]=input[i];
			}			
		}
		
		int u = countNull(array);
		Integer[] arrayNoNull = new Integer[u];
		
		arrayNoNull = removeNull(array);
		
		//garbage collecting
		array = null;
		entry = null;
		
		System.out.println("This is the new input: " + Arrays.toString(arrayNoNull));
		return arrayNoNull;
	}
	
	
	/**This function orders the Map by value in decreasing order of appearance
	 * @param map  the map to be ordered
	 * @return     the map ordered
	 * */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
		{
		    Map<K, V> result = new LinkedHashMap<>();
		    Stream<Map.Entry<K, V>> st = map.entrySet().stream();
		
		    st.sorted(reverseOrder(Map.Entry.comparingByValue()) )
		        .forEachOrdered( e -> result.put(e.getKey(), e.getValue()) );
		
		    return result;
		}
	
	
	/**This function counts how many null items are in an array
	 * @param array   the array to be counted
	 * @return        the number of null items
	 * */
	public static int countNull(Integer[] array) {
	    int nullCount = 0;
	    for (int i = 0; i < array.length; i++) {
	        if (array[i] == null) nullCount++;
	    }
	    return nullCount;
	}
	
	
	/**This function prints some useful information such as the compression ratio, the execution times and ends the program
	 * @param array                      the compressed bitstring
	 * @exception InterruptedException   if some thread is interrupted
	 * @exception IOException            failed or interrupted I/O operations
	 * */
	public static void end(Integer[] array) throws InterruptedException, IOException {
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds
		
		System.out.println("Execution time: " + duration + " ms");
		System.out.println("Compression ratio: "+ length/array.length);
		System.out.println("Number of iterations: " + (k-1));
		System.out.println("Size of the output " + array.length);
		System.out.println("Size of the grammar: " + (dictionary.size() + 2));
		System.out.println("");
		
		
		
		for (int i = 1; i<=length; i++) {
			
			//Random randomGenerator = new Random();
			//Integer randomInt = randomGenerator.nextInt(length);
			Object u = access(array, i, preAccess(array));
			//writer2.write("posizione" + (i-1) + " " + u + "\r\n");
			
			//if (u.equals(1)) Thread.sleep(1500);
			System.out.println(u);
		}
		
		//System.out.println(Arrays.toString(array));
		//System.out.println("I'm printing out the content of the dictionary:");
		/*
		Writer writer = new FileWriter("C:\\Users\\w1l32\\Desktop\\dizionario.txt");
		
		for (Entry<Integer, List<Integer>> ciao : (dictionary).entrySet())
		{			
		    writer.write(ciao.getValue() + " -> " + ciao.getKey());
		    writer.write("\r\n");
		}
		
		writer.close();*/
		
		/*
		boolean contents = IntStream.of(length-(array.length)).anyMatch(x -> x == 0); // lunghezza iniziale (30) -  lunghezza attuale (6) è uguale a 0? NO -> false
		

		while (!contents) {
		
			array = decode(removeNull(array));
			contents = IntStream.of(countNull(array)).anyMatch(x -> x == 0);
			System.out.println(Arrays.toString(array));
			//writer.write(Arrays.toString(array));
		}
		
		
		
*/
		//Map<Integer, Integer> test = new HashMap<Integer, Integer>();
		//test = height(dictionary);
		/*
		long start = System.nanoTime();
		//Writer writer2 = new FileWriter("C:\\Users\\w1l32\\Desktop\\test.txt");
		for (int i = 1; i<=length; i++) {
			
			//Random randomGenerator = new Random();
			//Integer randomInt = randomGenerator.nextInt(length);
			Object u = access(array, i, preAccess(array));
			//writer2.write("posizione" + (i-1) + " " + u + "\r\n");
			
			//if (u.equals(1)) Thread.sleep(1500);
			//System.out.println(u);
		}
		//writer2.close();
		long end = System.nanoTime();
		long dur = (end - start)/1000000;  //divide by 1000000 to get milliseconds
		System.out.println("Access time " + dur + " ms for " + 0.5*length);*/
		/*
		long start = System.nanoTime();
		Object u = access(array, 100, preAccess(array));
		long end = System.nanoTime();
		long dur = (end - start)/1000000;  //divide by 1000000 to get milliseconds
		System.out.println("Access time di 100: " + dur);
		start = System.nanoTime();
		Object u1 = access(array, 101, preAccess(array));
		end = System.nanoTime();
		dur = (end - start)/1000000;  //divide by 1000000 to get milliseconds
		System.out.println("Access time di 101: " + dur);
		start = System.nanoTime();
		Object u2 = access(array, 102, preAccess(array));
		end = System.nanoTime();
		dur = (end - start)/1000000;  //divide by 1000000 to get milliseconds
		System.out.println("Access time di 102: " + dur);
		start = System.nanoTime();
		Object u3 = access(array, 103, preAccess(array));
		end = System.nanoTime();
		dur = (end - start)/1000000;  //divide by 1000000 to get milliseconds
		System.out.println("Access time di 103: " + dur);
		
		
		//System.out.println("Access time di 101: " + u1);
	*/
		
		
		long start = System.nanoTime();
		for (int i = 1; i<=1000; i++) {
			
			//Random randomGenerator = new Random();
			//Integer randomInt = randomGenerator.nextInt(length);
			System.out.println(i);
			access(array, i, preAccess(array));
			//writer2.write("posizione" + (i-1) + " " + u + "\r\n");
			
			//if (u.equals(1)) Thread.sleep(1500);
			//System.out.println(u);
		}
		long end = System.nanoTime();
		long dur = (end - start)/1000000;  //divide by 1000000 to get milliseconds
		System.out.println("total access time " + dur + " ms for " + 0.5*length);
		System.out.println("mean access time " + dur/1000);
		
		
		System.exit(0);
	}
	
	
	/**This function prints the height of every grammar component
	 * @param map                        the map to be misured
	 * @exception InterruptedException   if some thread is interrupted
	 * */
	public static Map<Integer, Integer> height (Map<Integer, List<Integer>>  map) throws InterruptedException {
		Map<Integer, Integer> heigth = new HashMap<Integer, Integer>();
		
		for (Integer u : map.keySet()) {
		
			Object[] d = new Object[2];
			d = dictionary.get(u).toArray();
			
			heigth.put(u, Math.max(rec(map, d[0]), rec(map, d[1])));
		}
		
		for (Entry<Integer, Integer> ciao : (heigth).entrySet())
		{			
		    System.out.println("The key " + ciao.getKey() + " has height: " + ciao.getValue());
		}
		
		return heigth;
	}
	
	
	/**This is an auxiliary function to obtain the height
	 * @param map                        the map to be misured
	 * @param valour                     valour is the child of a node
	 * */
	private static int rec(Map<Integer, List<Integer>> map, Object valour) {
		Object[] d = new Object[2];
		int p=1;
		
		try {
			d = dictionary.get(valour).toArray();		
			
			return p + Math.max(rec(map, d[0]), rec(map, d[1]));
			}
		catch(Exception e) {}
		return p;
	}


	/**This function prints the number of bitstrings for each grammar component
	 * @param array                      the uncompressed bitstring
	 * @return                           a map containing for each grammar component, the number of leaves
	 * @throws IOException 
	 * */
	public static Map<Integer, Integer> preAccess(Integer[] array) throws IOException {
		
		Map <Integer, Integer> prova = new ConcurrentHashMap<Integer, Integer>();
		prova.put(0, 1);
		prova.put(1, 1);
		
		for (Integer u : dictionary.keySet()) {
			
			Object[] d = new Object[2];
			d = dictionary.get(u).toArray();
			
			for (Entry<Integer, Integer> ciao : (prova).entrySet()) {
			
				if (d[0].equals(ciao.getKey()))
					prova.put(u, ciao.getValue() + rec2(prova, d[1]));
			}
			
		}
		
		Writer writer = new FileWriter("C:\\Users\\w1l32\\Desktop\\Foglie.txt");
		
		for (Entry<Integer, Integer> ciao : (prova).entrySet())
		{			
		    writer.write("La chiave " + ciao.getKey() + " ha foglie: " + ciao.getValue());
		    writer.write("\r\n");
		}
		writer.close();
		
		return prova;		
	}
	
	
	/**This is an auxiliary function to get the number of leaves of each grammar component
	 * @param map                   the map to be misured
	 * @param object                object is the child of a node
	 * @return                      the number of leaves
	 * */
	private static Integer rec2(Map<Integer, Integer> map, Object object) {
		
		for (Entry<Integer, Integer> ciao : (map).entrySet()) {
		
			if (object.equals(ciao.getKey()))
				return ciao.getValue();
		
		}
		return null;
	}
	
	
	/**This function gets the access to a particular location of a compressed bitstring
	 * @param input                      the compressed bitstring
	 * @param index						 the index to get access
	 * @param leaves					 the map containing the leaves for every grammar component
	 * @return 
	 * */
	public static Object access(Integer[] input, Integer index, Map <Integer, Integer> leaves) throws InterruptedException {
		
		for (int i = 0; i<input.length; i++) {
			
			//if (IntStream.of(index).anyMatch(x -> x == 0)) break; // se l'indice è uguale a 0 abbiamo trovato la posizione cercata!
			
			Integer y = leaves.get(input[i]);
			if (y-index<0) index = index-y; //se numero di foglie - indice è <0, decrementa l'indice e guarda nella posizione successiva
			else if (y-index>0) //se il numero di foglie - indice è > 0, la posizione cercata è contenuta qua e bisogna cercarla 
				try {
					
					Object[] d = new Object[2];
					d = dictionary.get(input[i]).toArray();
					//System.out.println ("SONO DENTRO IL PRIMO IF d[0]: " + d[0] + ", d[1]: " + d[1] + " valore di i: " + i);
					//System.out.println("index-leaves.get(d[0]): " + (index-leaves.get(d[0])));
					if (index-leaves.get(d[0])>0) {
						//System.out.println ("SONO DENTRO IL SECONDO IF");
						index = index - leaves.get(d[0]);
						return rec3(d[1], leaves, index);
					} else {
						//System.out.println ("SONO DENTRO L'ELSE");
						return rec3(d[0], leaves, index);
					}
				}
			catch (Exception e) {}
			else {
				Object[] d = new Object[2];
				try {
					d = dictionary.get(input[i]).toArray();
					
					while (dictionary.containsKey(d[1])) d = dictionary.get(d[1]).toArray();
					/*System.out.println("CASO IN CUI SONO UGUALI: " + d[1]);
					System.exit(0);*/
					return d[1];
				} catch (Exception e) {
					return d[1];
				}
			}
			
			
		}
		return null;
		
	}	


	/**This is an auxiliary function to get the access of a bitstring
	 * @param x                   the child of a node
	 * @param leaves              the map containing the number of leaves for each grammar component
	 * @param index               the index to get access
	 * @return 
	 * */
	private static Object rec3(Object x, Map<Integer, Integer> leaves, Integer index) {		
		
		Object[] d = new Object[2];
		
		try {
		d = dictionary.get(x).toArray();
		
		//System.out.println ("d[0]: " + d[0] + ", d[1]: " + d[1] + " index: " + index);
		
		switch((int)index) {
			case 0: {
				try {
					d = dictionary.get(d[1]).toArray();
					return d[1];
					/*
					System.out.println("case 0 " + d[1]);
					System.exit(0);
					break;
					*/
				} catch (Exception e) {
					return d[1];
					/*
					System.out.println("case 0 " + d[1]);
					System.exit(0);
					*/
				}
			}
			case 1: {
				try {
					d = dictionary.get(d[0]).toArray();
					rec3(d[0], leaves, index);/*
					System.out.println("case 1 " + d[0]);
					//System.exit(0);
					break;*/
				} catch (Exception e) {
					return d[0];/*
					System.out.println("case 1 " + d[0]);
					System.exit(0);*/
				}
			}
			default: {
				//System.out.println("index-leaves.get(d[0]): " + (index-leaves.get(d[0])));
				Integer y = leaves.get(d[0]);
				
				if (y-index>0) return rec3(d[0], leaves, index);
					else if (y-index<0)return rec3(d[1], leaves, index-y);
						else return rec3(d[0], leaves, index);
				
				
				//index = index-leaves.get(d[0]);
				//return rec3(d[1], leaves, index);
				//break;
			}
		}
		}catch (Exception e) {
			return x;
			/*
			System.out.println("last case " + x);
			System.exit(0);*/
		}
		
	}


	/**This function decodes the compressed input end ends the program
	 * @param input                      the compressed bitstring
	 * @exception InterruptedException   if some thread is interrupted
	 * @exception IOException            failed or interrupted I/O operations
	 * @return                           the uncompressed bitstring
	 * */
	public static Integer[] decode(Integer[] input) throws InterruptedException, IOException {
		
		Object[] t = new Object[length];
		int m = 0;
		
		for (int i=0; i<input.length; i++) {
			
			try {
				
				Object[] d = new Object[2];				

				if (dictionary.containsKey(input[i])) {
									
					d = dictionary.get(input[i]).toArray();

				t[m++] = d[0];
				t[m++] = d[1];
				} else {
					if (m<length)
					t[m++] = input[i];
				}

			} catch (Exception e) {
				System.out.println("i: " + i);
				System.out.println("m: " + m);
				System.out.println(e);
				
			}
		}
		
		Integer[] test = Arrays.copyOf(t, t.length, Integer[].class);

		return test;
	}
	

	/**This function creates a "null-free" array from another one
	 * @param array  the array that must be shorten
	 * @return       the array without null entries
	 * */
	public static Integer[] removeNull(Integer[] array) {
	    int nullCount = 0;
	    for (int i = 0; i < array.length; i++) {
	        if (array[i] == null) nullCount++;
	    }
	    Integer[] noNullArray = new Integer[array.length-nullCount];
	    int j = 0;
	    for (int i = 0; i < array.length; i++) {
	        if (array[i] != null) noNullArray[j++] = array[i];
	    }
	    return noNullArray;
	}	
}