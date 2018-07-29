import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue <Item> implements Iterable <Item>{
	private Item[] q;
	private int N;
	
	
	public RandomizedQueue() {
		q = (Item[]) new Object[1];
		N = 0;
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return N;
	}
	
	public void enqueue(Item item) {
		if (item == null) throw new IllegalArgumentException("Item to add cannot be null.");
		if (N == q.length) resize (2 * q.length);
		q[N++] = item;
	}
	
	public Item dequeue() {
		if (N == 0) throw new NoSuchElementException("The queue is empty");
		if (N > 0 && N == q.length/4) resize(q.length / 2);
		int randIndex = StdRandom.uniform(N);
		Item removed = q[randIndex];
		for (int i = randIndex; i < N; i++) {
			if (i+1 < N) {
				q[i] = q[i+1];
			} else {
				q[i] = null;
			}
		}
		N--;
		return removed;
	}
	
	private void resize(int length) {
		Item[] copy = (Item[]) new Object[length];
		for (int i = 0; i < N; i++) {
			copy[i] = q[i];
		}
		q = copy;
	}
	
	public Item sample() {
		if (N == 0) throw new NoSuchElementException("The queue is empty");
		int randIndex = StdRandom.uniform(N);
		return q[randIndex];
	}
	
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		private int i;
		private RandomizedQueue<Item> qUnique;
		
		ListIterator() {
			RandomizedQueue<Item> tempQ = new RandomizedQueue<>();
			qUnique = new RandomizedQueue<>();
			
			for (int ind = 0; ind < N; ind++) {
				tempQ.enqueue(q[ind]);
			}
			
			for (int ind = 0; ind < N; ind++) {
				qUnique.enqueue(tempQ.dequeue());
			}
		}

		public boolean hasNext() {
			return i < N;
		}

		public Item next() {
			if (N == 0) { 
				throw new NoSuchElementException("The queue is empty");
			} else if (i == N) {
				throw new NoSuchElementException("The queue is exhausted.)");
			}
			return qUnique.q[i++];
		}
		
		public void remove() {
			throw new UnsupportedOperationException("remove() is an unsupported method");
		}
		
	}
	
	public static void main (String[] args) {
	}
}
