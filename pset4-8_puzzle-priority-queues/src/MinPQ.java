import java.util.Iterator;
import java.util.NoSuchElementException;

/******************************************************************************
 *  Compilation:  javac MinPQ.java
 *  Execution:    java MinPQ < input.txt
 *  
 *  Generic min priority queue implementation with a binary heap.
 *  Can be used with a comparator instead of the natural order.
 *
 *
 *  Use a one-based array to simplify parent and child calculations.
 *
 *  Can be optimized by replacing full exchanges with half exchanges
 *  (ala insertion sort).
 *
 ******************************************************************************/

public class MinPQ<Key> implements Iterable <Key> {
	private Key[] pq;		// store items at 1 - n.
	private int n;			// size of the priority queue
	
	public MinPQ() {
		this(1);
	}
	
	@SuppressWarnings("unchecked")
	public MinPQ(int capacity) {
		pq = (Key[]) new Object[capacity+1];
		n = 0;
	}
	
	public int size() { return n; }
	
	public void insert (Key item) {
		pq[++n] = item;
		swim(n);
	}
	
	public Key delMin() {
		Key min = pq[1];
		if (greater(2, 3))
	}
	
	/*********************************************
	 * Helper functions to restore heap invariant
	 *********************************************/
		
	private void swim(int i) {
		while(i > 1 && greater(i/2, i)) {
			exch(i, i/2);
			i = i/2;
		}
	}
	
	private void sink(int i) {
		while(2*i <= n) {
			int j = 2*i;
			if (j < n && greater(j, j+1)) j++;
			if (!greater(i, j)) break;
			exch(i, j);
			i = j;
		}
	}
	
	/************************************************
	 * Helper Functions for Comparing and Exchanging 
	 ************************************************/
	
	@SuppressWarnings("unchecked")
	private boolean greater(int i, int j) {
		return ((Comparable <Key>) pq[i]).compareTo(pq[j]) > 0;
	}
	
	private void exch(int i, int j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}
	
	public Iterator<Key> iterator() {
		return new HeapIterator();
	}
	
	private class HeapIterator implements Iterator<Key> {
		
		private MinPQ<Key> copy;
		
		public HeapIterator() {
			copy = new MinPQ<Key>(size());
			for (int i = 1; i <= n; i++) {
				copy.insert(pq[i]);
			}
		}
		
		public boolean hasNext() { return true; }
		public Key next() { 
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return copy.delMin();
		}
		
	}
	
}
