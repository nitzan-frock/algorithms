import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque <Item> implements Iterable<Item>{
	
	private Node first;
	private Node last;
	private int size;
	
	private class Node {
		Item item;
		Node next;
		
		Node(Item data){
			item = data;
			next = null;
		}
	}
	
	public Deque() {
		first = null;
		last = null;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public int size() {
		return size;
	}
	
	public void addFirst (Item item) {
		if (item == null) throw new IllegalArgumentException ("Cannot add a null item.");
		Node newNode = new Node(item);
		if (isEmpty()) {
			first = newNode;
			last = first;
		} else {
			newNode.next = first;
			first = newNode;
		}
	}
	
	public void addLast (Item item) {
		if (item == null) throw new IllegalArgumentException ("Cannot add a null item.");
		Node oldLast = last;
		last = new Node(item);
		if (isEmpty()) {
			first = last;
		}
		else oldLast.next = last;
	}
	
	public Item removeFirst () {
		if (isEmpty()) throw new NoSuchElementException("The deque is empty.");
		Item item = first.item;
		first = first.next;
		if (isEmpty()) last = null;
		return item;
	}
	
	public Item removeLast () {
		System.out.println("[removeLast()]");
		if (isEmpty()) throw new NoSuchElementException("The deque is empty.");
		Item item = last.item;
		Node x = first;
		while (x.next.next != null) {
			x = x.next;
		}
		last = x;
		last.next = null;
		return item;
	}
	
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public Item next() {
			if (current.next == null) { throw new NoSuchElementException("There is not a next element."); }
			Item item = current.item;
			current = current.next;
			return item;
		}
		
		public void remove() {
			throw new UnsupportedOperationException("Method cannot be used.");
		}
	}
	
	public static void main(String[] args) {
		
	}

}
