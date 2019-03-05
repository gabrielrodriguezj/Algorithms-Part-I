import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * A randomized queue is similar to a stack or queue, except that the item
 * removed is chosen uniformly at random from items in the data structure.
 *
 * @author gabrielrodriguezj
 * @since 03/19
 * @version 1.0
 *
 * @param <Item> Data type that will contain the randomized queue.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

	/**
	 * Pointer to the first element in the randomized queue.
	 */
	private Node first;

	/**
	 * Pointer to the last element in the randomized queue.
	 */
	private Node last;

	/**
	 * Counter of the number elements in the randomized queue.
	 */
	private int size;

	/**
	 * Default constructor, construct an empty randomized queue.
	 */
	public RandomizedQueue() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}

	/**
	 * Method for check if the randomized queue is empty.
	 *
	 * @return <tt>True</tt> if the randomized queue is empty, <tt>false</tt> in
	 *         other case.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Method that return the number of items on the randomized queue.
	 *
	 * @return Number of items on the randomized queue.
	 */
	public int size() {
		return size;
	}

	/**
	 * Method for add a element to the randomized queue.
	 *
	 * @param item Element for add to the randomized queue.
	 */
	public void enqueue(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException("The element to add must not be null");
		}

		// Create the new node
		Node newLast = new Node(item);
		newLast.next = null;

		// Is the first element in the randomized queue??
		if (isEmpty()) {
			// Move the last pointer to the first element.
			first = last = newLast;
		} else {
			last.next = newLast;
			last = newLast;
		}

		// Increase the counter
		size++;
	}

	/**
	 * Method for remove a random item of the randomized queue.
	 *
	 * @return Random element removed of the randomized queue.
	 */
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("The randomized queue is empty");
		}
		
		// Generate a random number from 0 to < size
		int random = StdRandom.uniform(size);
		Item item = null;
		
		if (random == 0) {
			// If the random element to dequeue is the first element
			item = first.item;

			// Move the first element to the next position
			first = first.next;
		} else {
			Node pointer = first;
			//Move the pointer to a position before the element to dequeue
			for (int i = 0; i < random - 1; i++) {
				//pointer point to the previous element to dequeue
				pointer = pointer.next;
			}
			
			//Take the item value for the element to dequeue
			item = pointer.next.item;
			
			//If the element to dequeue is the last element, move last node to
			//the position pointer
			if(random == size - 1) {
				last = pointer;
			}
			
			//Link the previous and posterior element
			pointer.next = pointer.next.next;
		}
		
		//Decrease the counter
		size--;
		
		return item;
	}

	/**
	 * Method that return a random item of the randomized queue, but do not remove
	 * it.
	 *
	 * @return Random element of the randomized queue.
	 */
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException("The randomized queue is empty");
		}

		// Generate a random number from 0 to < size
		int random = StdRandom.uniform(size);

		if (random == 0) {
			// If the random element is the first element
			return first.item;
		} else if (random == size - 1) {
			// If the random element is the last element
			return last.item;
		} else {
			// To point to the element in the position "random"
			Node sample = first;
			for (int i = 0; i < random; i++) {
				sample = sample.next;
			}
			return sample.item;
		}
	}

	/**
	 * Return an independent iterator over items in random order.
	 */
	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	/**
	 * Class for representing a element in the deque.
	 * 
	 * @author gabrielrodriguezj
	 * @since 03/19
	 * @version 1.0
	 *
	 */
	private class Node {
		/**
		 * Content of the element in the deque.
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
		Node(Item item) {
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
	private class RandomizedQueueIterator implements Iterator<Item> {
		
		/**
		 * Elements iterated.
		 */
		private Object [] elements; 
		
		/**
		 * Number of elements iterated over
		 */
		private int numElementsIterated;
		
		/**
		 * Constructor that take the randomized queue and insert it into a array
		 */
		public RandomizedQueueIterator() {
			this.elements = new Object[size];
			this.numElementsIterated = 0;
			
			//Copy the randomized queue into an array
			Node node = first;
			for(int i=0; i< size; i++) {
				elements[i] = node.item;
				node = node.next;
			}
		}
		
		/**
		 * Method to determinate if there is a next element for iterate.
		 */
		@Override
		public boolean hasNext() {
			return numElementsIterated != size;
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
			if (!hasNext()) {
				throw new NoSuchElementException("No next element avaliable");
			}
			
			int random = 0;
			Item item = null;
			do {
				// Generate a random number from 0 to < size
				random = StdRandom.uniform(size);
				
				item = (Item)elements[random];
				
				if(item != null) {
					numElementsIterated++;
				}
				elements[random] = null;
			}while(item == null);
			
			return item;
		}
	}
}
