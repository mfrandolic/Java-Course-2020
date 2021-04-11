package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * An implementation of a linked list-backed collection of objects.
 * Duplicate elements are allowed. Storage of {@code null} references
 * is not allowed.
 * 
 * @author Matija Frandolić
 */
public class LinkedListIndexedCollection implements List {
	
	/**
	 * A node of the linked list.
	 */
	private static class ListNode {	
		/**
		 * Pointer to the previous list node.
		 */
		private ListNode previous;
		/**
		 * Pointer to the next list node.
		 */
		private ListNode next;
		/**
		 * Reference to the value that is stored.
		 */
		private Object value;
	}
	
	/**
	 * Current size of this collection.
	 */
	private int size;
	
	/**
	 * Reference to the first node of the linked list.
	 */
	private ListNode first;
	
	/**
	 * Reference to the last node of the linked list.
	 */
	private ListNode last;
	
	/**
	 * Number of modifications to this collection.
	 */
	private long modificationCount;
	
	/**
	 * Constructs an empty collection.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Constructs a new collection by copying elements from the given collection.
	 * 
	 * @param other the collection whose elements are copied into this collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		first = last = null;
		size = 0;
		modificationCount = 0;
		addAll(other);
	}
	
	/**
	 * Inserts the given object into this collection at the given position.
	 * Legal positions are 0 to {@code size()}. 
	 * 
	 * @param  value    the object that is inserted into this collection
	 * @param  position the position at which to insert the value 
	 * @throws NullPointerException      if the given value is {@code null}
	 * @throws IndexOutOfBoundsException if {@code position < 0 || position > size()} 
	 */
	@Override
	public void insert(Object value, int position) {
		Objects.requireNonNull(value, "Null reference cannot be added into this collection.");
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException(position);
		}
		
		ListNode node = new ListNode();
		node.value = value;
		
		if (first == null) {
			first = last = node;
			node.previous = null;
			node.next = null;
		} else if (position == 0) {
			node.previous = null;
			node.next = first;
			first.previous = node;
			first = node;
		} else if (position == size) {
			node.previous = last;
			node.next = null;
			last.next = node;
			last = node;
		} else {
			ListNode nodeAfter;
			
			if (position < size / 2) {
				nodeAfter = first;
				for (int i = 0; i < position; i++) {
					nodeAfter = nodeAfter.next;
				}
			} else {
				nodeAfter = last;
				for (int i = size - 1; i > position; i--) {
					nodeAfter = nodeAfter.previous;
				}
			}
			
			node.previous = nodeAfter.previous;
			node.next = nodeAfter;
			nodeAfter.previous.next = node;
			nodeAfter.previous = node;
		}
		
		size++;
		modificationCount++;
	}

	/**
	 * Adds the given object to the end of this collection.
	 * 
	 * @param  value the object that is added into this collection
	 * @throws NullPointerException if the given value is {@code null}
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}
	
	/**
	 * Returns the object that is stored in this collection at the given
	 * index. Valid indexes are 0 to {@code size()} - 1.
	 * 
	 * @param  index the index of the wanted object
	 * @return       the object at the given position
	 * @throws IndexOutOfBoundsException if {@code index < 0 || index > size() - 1} 
	 */
	@Override
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException(index);
		}
		
		ListNode node;
		
		if (index < size / 2) {
			node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
		} else {
			node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.previous;
			}
		}
		
		return node.value;
	}
	
	/**
	 * Returns the index of the first occurrence of the given {@code value}
	 * or -1 if the {@code value} is not found, as determined by {@code equals}
	 * method.
	 * 
	 * @param value the object whose index needs to be determined
	 * @return      the index of the first occurrence of the given object or
	 * 				-1 if the object is not found
	 */
	@Override
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		
		int i = 0;
		for (ListNode node = first; node != null; node = node.next) {
			if (value.equals(node.value)) {
				return i;
			}
			i++;
		}
		
		return -1;
	}
	
	/**
	 * Removes the element at the specified index from this collection.
	 * Legal indexes are 0 to {@code size()} - 1.
	 * 
	 * @param  index the index of the element that is removed
	 * @throws IndexOutOfBoundsException if {@code index < 0 || index > size() - 1} 
	 */
	@Override
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException(index);
		}
		
		ListNode node;
		
		if (index < size / 2) {
			node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
		} else {
			node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.previous;
			}
		}
		
		if (node == first && node == last) {
			first = last = null;
		} else if (node == first) {
			first = node.next;
			node.next.previous = null;
		} else if (node == last) {
			last = node.previous;
			node.previous.next = null;
		} else {
			node.next.previous = node.previous;
			node.previous.next = node.next;
		}
		
		size--;
		modificationCount++;
	}
	
	/**
	 * Returns {@code true} only if this collection contains the given object,
	 * as determined by {@code equals} method, and successfully removes first 
	 * occurrence of it.
	 * 
	 * @param value the object that is removed from this collection
	 * @return      {@code true} if this collection contains the given object
	 *              and its first occurrence is removed successfully, 
	 *              {@code false} otherwise
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}
		
		for (ListNode node = first; node != null; node = node.next) {
			if (value.equals(node.value)) {
				if (node == first && node == last) {
					first = last = null;
				} else if (node == first) {
					first = node.next;
					node.next.previous = null;
				} else if (node == last) {
					last = node.previous;
					node.previous.next = null;
				} else {
					node.next.previous = node.previous;
					node.previous.next = node.next;
				}
				size--;
				modificationCount++;
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Object value) {
		if (value == null) {
			return false;
		}
		
		for (ListNode node = first; node != null; node = node.next) {
			if (value.equals(node.value)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		int i = 0;
		for (ListNode node = first; node != null; node = node.next) {
			array[i++] = node.value;
		}
		
		return array;
	}

	@Override
	public void clear() {
		first = last = null;
		size = 0;
		modificationCount++;
	}
	
	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListIndexedCollectionElementsGetter(this); 
	}
	
	/**
	 * Implementation of {@code ElementsGetter} for this collection. Elements
	 * are returned in order from index 0 to index size() - 1.
	 */
	private static class LinkedListIndexedCollectionElementsGetter implements ElementsGetter {

		/**
		 * Reference to the collection.
		 */
		private LinkedListIndexedCollection collection;
		
		/**
		 * Index of current element.
		 */
		private ListNode currentNode;
		
		/**
		 * Saved modification count of the collection.
		 */
		private long savedModificationCount;
		
		/**
		 * Constructs a new {@code LinkedListIndexedCollectionElementsGetter}
		 * for the given collection.
		 * 
		 * @param collection the collection for which to create this elements getter
		 */
		public LinkedListIndexedCollectionElementsGetter(LinkedListIndexedCollection collection) {
			this.collection = collection;
			this.currentNode = collection.first;
			this.savedModificationCount = collection.modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException("Concurrent modification not allowed.");
			}
			
			return currentNode != null;
		}

		@Override
		public Object getNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException("Concurrent modification not allowed.");
			}
			
			if (currentNode == null) {
				throw new NoSuchElementException("No more elements in this collection.");
			}
			
			Object element = currentNode.value;
			currentNode = currentNode.next;
			return element;
		}
		
	}
	
}
