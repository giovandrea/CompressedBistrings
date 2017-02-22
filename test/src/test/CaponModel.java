package test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

/**This class creates a text file containing bits generated from a Capon model
 * @author: Andrea Giovanni Atzeni, ID 159046992
 * @version 1.0
 * @since 07-08-2016
 * */
public class CaponModel {
	
	public static void main (String[] args) throws InterruptedException, IOException {
		
		FileWriter writer = new FileWriter("C:\\Users\\w1l32\\Desktop\\Capon.txt");	
		
		int[] numsToGenerate = new int[] {0,1};
		double[] probabilitiesQW = new double[] {0.9, 0.1};
		double[] probabilitiesQB = new double[] {0.9, 0.1};
		
		EnumeratedIntegerDistribution distributionQW = new EnumeratedIntegerDistribution(numsToGenerate, probabilitiesQW);
		EnumeratedIntegerDistribution distributionQB = new EnumeratedIntegerDistribution(numsToGenerate, probabilitiesQB);
		
		int numSamples=524288/8;
		int p = 0;		
		
		int[] samples0 = distributionQW.sample(numSamples);		
		int[] samples1 = distributionQB.sample(numSamples);
		
	    for (int i = 0; i < numSamples; i++){
	    	boolean contents = IntStream.of(samples0[i]).anyMatch(x -> x == 0);   	
	    	if (contents) {
	    		writer.write(samples0[i]+"");
	    	} else {
	    		writer.write(samples0[i]+"");
	    		
	    		for (int j = p; j < numSamples; j++) {
	    			//System.out.println("j: " + j + " p: " + p);
	    			boolean content = IntStream.of(samples1[j]).anyMatch(x -> x == 1);
	    			
	    			if (content) {
	    				writer.write(samples1[j]+"");
	    				p++;
	    			}
	    			else {
	    				writer.write(samples1[j]+"");
	    				p++;
	    				break;
	    			}
	    		}
	    	}

		    }
	    /*
	    writer.write("\r\n");
	    
	    for (int i = 0; i<numSamples; i++) {
	    	writer.write(samples0[i]+"");

	    }
	    writer.write("\r\n");
	    
	    for (int i = 0; i<numSamples; i++) {
	    	writer.write(samples1[i]+"");
	    }
	    */

		writer.close();
	}
}