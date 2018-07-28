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

	private void printList() {
		Node x = first;
		while (x.next != null) {
			System.out.println(x.item);
			x = x.next;
		}
		System.out.println(x.item);
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
			Item item = current.item;
			current = current.next;
			return item;
		}
		
		public void remove() {
			throw new UnsupportedOperationException("Method cannot be used.");
		}
	}
	
	public static void main(String[] args) {
		Deque<Integer> deck = new Deque<Integer>();
		/*for (int i = 0; i < 1; i++) {
			deck.addFirst((int)Math.floor(Math.random()*10));
			System.out.println(i+": ");
			deck.forEach(num -> System.out.println(num));
		}*/
		deck.addFirst(4);
		deck.addLast(5);
		deck.addFirst(10);		
		deck.printList();
		System.out.println("last: " + deck.last.item);
		System.out.println("removed last: " + deck.removeLast());
		deck.printList();
		deck.removeFirst();
		deck.printList();
	}

}
