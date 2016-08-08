/**
 * Project Euler Problem 1:
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
 * Find the sum of all the multiples of 3 or 5 below 1000.
 * @author mohiman
 * This solution is probably a bit overkill for 1000 numbers
 * But what will happen if you have to do the same problem for first billion numbers.
 * The following solution consistently performs under 3 seconds, and number of threads you can spawn is configurable.
 */
package com.mohit.project.euler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Problem1 extends ProjectEulerBase {
	protected static long SUM = 0;
	private static final int HOW_MANY_THREADS = 10;
	private static final long TOTAL_NUM = 1000;
	public static void main(String[] args) {

		
		Problem1 multiThreadSol= new Problem1();
		multiThreadSol.solutionOneProblem(TOTAL_NUM );
		
		// If we list all the natural numbers below 10 that are multiples of 3
		// or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
		// Find the sum of all the multiples of 3 or 5 below 1000.
	}

	private void solutionOneProblem(long maxNum) {
		long remainder = ( maxNum / HOW_MANY_THREADS);
		long start = 1; 
		long end = 0;
		long startTime = System.currentTimeMillis();
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i=1;i<=HOW_MANY_THREADS;i++){
			
			start = end;
			end +=  remainder;
			
			if (i == HOW_MANY_THREADS) end = maxNum; //takes into account any differences because of off threads...
			es.execute( new workerThread(start, end) );
		}
		es.shutdown();
		try {
			es.awaitTermination(1, TimeUnit.MINUTES);
			log("Problem 1 (Threads Solution)  Find the sum of all the multiples of 3 or 5 below " + maxNum +  ", Total : " + Problem1.SUM+ ", total time taken " + (System.currentTimeMillis() - startTime));
		}
		catch (InterruptedException ie)
		{
			ie.printStackTrace();
		}
	}
}

class workerThread extends Thread
{
	Problem1 parent;
	private long start;
	private long end;

	public workerThread(long start, long end){
		this.start = start;
		this.end = end;
	}
	
	public void run (){
		for (long i = start; i <= end; i++) {
			if (i % 5 == 0 || i % 3 == 0)
				Problem1.SUM += i;
		}
	}
}