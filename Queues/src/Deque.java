import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque (pronounced "deck") is a generalization of a
 * stack and a queue that supports adding and removing items from either the
 * front or the back of the data structure.
 *
 * @author gabrielrodriguezj
 * @since 03/19
 * @version 1.0
 *
 * @param <Item> Data type that will contain the deque.
 */
public class Deque<Item> implements Iterable<Item> {
	
	/**
	 * Pointer to the first element in the deque.
	 */
	private Node first;
	
	/**
	 * Pointer to the last element in the deque.
	 */
	private Node last;
	
	/**
	 * Counter of the number elements in the deque.
	 */
	private int size;

	/**
	 * Construct an empty deque.
	 */
	public Deque() {
		first = last = null;
		size = 0;
	}

	/**
	 * Method for determinate if the deque is empty.
	 *
	 * @return <tt>True</tt> if the deque is empty, <tt>false</tt> if is not empty.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Method for get the number of items on the deque.
	 *
	 * @return number of items on the deque.
	 */
	public int size() {
		return size;
	}

	/**
	 * Add the item to the front of the deque.
	 *
	 * @param item Element to add at deque.
	 */
	public void addFirst(Item item) {
		if(item == null){
			throw new java.lang.IllegalArgumentException("The element to add must not be null");
		}
		
		//Create the new node
		Node front = new Node(item);
		front.next = first;
		first = front;
		
		//Is the first element in the deque??
		if(isEmpty()) {
			//Move the last pointer to the first element.
			last = first;
		}
		
		//Increase the counter
		size++;
	}

	/**
	 * Add the item to the end of the deque.
	 *
	 * @param item Element to add at deque.
	 */
	public void addLast(Item item) {
		if(item == null){
			throw new IllegalArgumentException("The element to add must not be null");
		}
		
		if(isEmpty()) {
			//The same process that add a first element.
			addFirst(item);
		}
		else
		{
			//Creating the new node.
			Node end = new Node(item);
			end.next = null;
			
			//Move the pointers.
			last.next = end;
			last = end;
			
			//Increment the counter
			size++;
		}
	}

	/**
	 * Remove and return the item from the front of the deque.
	 *
	 * @return Element removed of the deque.
	 */
	public Item removeFirst(){
		if(isEmpty()) {
			throw new NoSuchElementException("The deque is empty");
		}
		
		//Move the pointers
		Node removed = first;
		first = first.next;
		
		//Decrease the counter;
		size--;
		
		return removed.item;
	}

	/**
	 * Remove and return the item from the end of the deque.
	 *
	 * @return Element removed of the deque.
	 */
	public Item removeLast() {
		if(isEmpty()) {
			throw new NoSuchElementException("The deque is empty");
		}
		
		Node removed = last;
		if(size() == 1) {
			first = null;
			last = null;
		}
		else if(size() == 2) {
			//Remove the last element and first and last points the same element
			first.next = null;
			last = first;
		}
		else {
			Node cursor = first;
			//Moves the last pointer to a previous element
			while(cursor.next.next != null) {
				cursor = cursor.next;
			}
			//when the while stops, cursor points to the penultimate element
			//Move the last element to the previous element.
			last = cursor;
			last.next = null;
		}
		
		//Decrease the counter
		size--;
		
		return removed.item;
	}

	/**
	 * Return an iterator over items in order from front to end.
	 */
	public Iterator<Item> iterator(){
		return new DequeIterator();
	}
	
	/**
	 * Class for representing a element in the deque.
	 * 
	 * @author gabrielrodriguezj
	 * @since 03/19
	 * @version 1.0
	 *
	 */
	private class Node{
		/**
		 * Content  of the element in the deque.
		 */
		Item item;
		
		/**
		 * Link to the next element in the deque.
		 */
		Node next;
		
		/**
		 * Constructor by default.
		 * 
		 * @param item Content of the element in the deque.
		 */
		Node(Item item){
			this.item = item;
		}
	}
	
	/**
	 * Implementation of the iterator class.
	 * 
	 * @author gabrielrodriguezj
	 * @since 03/18
	 * @version 1.0
	 *
	 */
	private class DequeIterator implements Iterator<Item>{
		
		/**
		 * Current element for iteration process.
		 */
		private Node current = first;
		
		/**
		 * Method to determinate if there is a next element for iterate.
		 */
		@Override
		public boolean hasNext() {
			return current != null;
		}
		
		/**
		 * Remove the current element of the iterator; not supported.
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Opetarion not supported");
		}
		
		/**
		 * Return the next element for iterate.
		 */
		@Override
		public Item next() {
			if(!hasNext()) {
				throw new NoSuchElementException("No next element avaliable");
			}
			
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

}
