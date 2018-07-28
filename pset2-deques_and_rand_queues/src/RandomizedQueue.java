import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue <Item> implements Iterable <Item>{
	private Item[] q;
	private int N;
	
	
	public RandomizedQueue() {
		q = (Item[]) new Object[1];
		N = 0;
	}
	
	public boolean isEmpty() {
		if (q == null) {
			return true;
		}
		return false;
	}
	
	public int size() {
		return q.length;
	}
	
	public void enqueue(Item item) {
		if (item == null) throw new IllegalArgumentException("Item to add cannot be null.");
		if (N == q.length) resize (2 * q.length);
		q[N++] = item;
	}
	
	public Item dequeue() {
		if (N == 0) throw new NoSuchElementException("The queue is empty");
		if (N > 0 && N == q.length/4) resize(q.length / 2);
		int randIndex = (int) Math.floor(Math.random()*N);
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
		int randIndex = (int) Math.floor(Math.random()*N);
		return q[randIndex];
	}
	
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		private int i;

		public boolean hasNext() {
			return i < N;
		}

		public Item next() {
			return q[i++];
		}
		
		public void remove() {
			throw new UnsupportedOperationException("remove() is an unsupported method");
		}
		
	}
	
	public static void main (String[] args) {
		RandomizedQueue<Integer> q = new RandomizedQueue<>();
		q.enqueue(1);
		q.enqueue(4);
		q.enqueue(3);
		int removed = q.dequeue();
		System.out.println("sample: " + q.sample());
		System.out.println("removed: " + removed);
		
		for (int el : q) {
			System.out.println(el);
		}
		q.dequeue();
		q.dequeue();
		q.dequeue();
		q.dequeue();
	}
}
