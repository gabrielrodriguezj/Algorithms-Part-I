import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;

/**
 * Client for the RandomizedQueue and Deque class.
 *
 * @author gabrielrodriguezj
 * @since 03/19
 * @version 1.0
 */
public class Permutation {

	/**
	 * Method that takes an integer k as a command-line argument; reads in a
	 * sequence of strings from standard input using StdIn.readString(); and prints
	 * exactly k of them, uniformly at random; May assume that 0 <= k <= n, where
	 * n is the number of string on standard input.
	 *
	 * @param args Must contains a integer number (k).
	 */
	public static void main(String[] args) {
		int k = Integer.valueOf(args[0]);
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		while(!StdIn.isEmpty()) {
			String s = StdIn.readString();
			rq.enqueue(s);
		}
		
		Iterator<String> iter = rq.iterator();
		for(int i=0; i<k; i++ ) {
			String item = iter.next();
			System.out.println(item);
		}
	}
}
