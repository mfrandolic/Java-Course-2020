package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * An implementation of a resizable array-backed collection of objects.
 * Duplicate elements are allowed. Storage of {@code null} references
 * is not allowed.
 * 
 * @author Matija Frandolić
 * 
 * @param <T> the type of elements stored in this list
 */
public class ArrayIndexedCollection<T> implements List<T> {
	
	/**
	 * Current size of this collection.
	 */
	private int size;
	
	/**
	 * The array of collection elements.
	 */
	private Object[] elements;
	
	/**
	 * Number of modifications to this collection.
	 */
	private long modificationCount;
	
	/**
	 * Default capacity of the underlying array.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Constructs an empty collection with the given capacity for the 
	 * underlying array.
	 * 
	 * @param  initialCapacity the initial capacity for the underlying array
	 * @throws IllegalArgumentException if the given capacity is less than 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity must not be less than 1.");
		}
		
		elements = new Object[initialCapacity];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Constructs an empty collection with the default capacity for the 
	 * underlying array.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructs a new collection by copying elements from the given collection. 
	 * If the given initial capacity is less than the size of the given 
	 * collection, the size of the given collection is used for the capacity 
	 * of the underlying array.
	 * 
	 * @param  other           the collection whose elements are copied into this collection
	 * @param  initialCapacity the initial capacity for the underlying array
	 * @throws NullPointerException     if other collection reference is {@code null}
	 * @throws IllegalArgumentException if the given capacity is less than 1
	 */
	public ArrayIndexedCollection(Collection<? extends T> other, int initialCapacity) {
		Objects.requireNonNull(other, "Collection reference must not be null.");
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity must not be less than 1.");
		}
		
		if (initialCapacity < other.size()) {
			elements = new Object[other.size()];
		} else {
			elements = new Object[initialCapacity];
		}
		
		size = 0;
		modificationCount = 0;
		
		addAll(other);
	}
	
	/**
	 * Constructs a new collection by copying elements from the given collection. 
	 * If the default capacity is less than the size of the given collection, 
	 * the size of the given collection is used for the capacity of the 
	 * underlying array.
	 * 
	 * @param  other the collection whose elements are copied into this collection
	 * @throws NullPointerException if other collection reference is {@code null}
	 */
	public ArrayIndexedCollection(Collection<? extends T> other) {
		this(other, DEFAULT_CAPACITY);
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
	public void insert(T value, int position) {
		Objects.requireNonNull(value, "Null reference cannot be added into this collection.");
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException(position);
		}
		
		if (size == elements.length) {
			Object[] tmp = new Object[2 * elements.length];
			for (int i = 0; i < size; i++) {
				tmp[i] = elements[i];
			}
			elements = tmp;
		}
		
		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		
		elements[position] = value;
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
	public void add(T value) {
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
	@SuppressWarnings("unchecked")
	@Override
	public T get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException(index);
		}
		
		return (T) elements[index];
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
		
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
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
		
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		
		elements[size - 1] = null;
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
		int index = indexOf(value);
		
		if (index >= 0) {
			remove(index);
			return true;
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
		
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		
		return false;
	}	

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		for (int i = 0; i < size; i++) {
			array[i] = elements[i];
		}
		
		return array;
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/**
	 *
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayIndexedCollectionElementsGetter<T>(this); 
	}
	
	/**
	 * Implementation of {@code ElementsGetter} for this collection. Elements
	 * are returned in order from index 0 to index size() - 1.
	 * 
	 * @param <S> the type of elements returned by this {@code ElementsGetter}
	 */
	private static class ArrayIndexedCollectionElementsGetter<S> implements ElementsGetter<S> {

		/**
		 * Reference to the collection.
		 */
		private ArrayIndexedCollection<S> collection;
		
		/**
		 * Index of current element.
		 */
		private int currentIndex;
		
		/**
		 * Saved modification count of the collection.
		 */
		private long savedModificationCount;
		
		/**
		 * Constructs a new {@code ArrayIndexedCollectionElementsGetter}
		 * for the given collection.
		 * 
		 * @param collection the collection for which to create this elements getter
		 */
		public ArrayIndexedCollectionElementsGetter(ArrayIndexedCollection<S> collection) {
			this.collection = collection;
			this.currentIndex = 0;
			this.savedModificationCount = collection.modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException("Concurrent modification not allowed.");
			}
			
			return currentIndex < collection.size;  
		}

		@SuppressWarnings("unchecked")
		@Override
		public S getNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException("Concurrent modification not allowed.");
			}
			
			if (currentIndex == collection.size) {
				throw new NoSuchElementException("No more elements in this collection.");
			}
			
			return (S) collection.elements[currentIndex++];
		}
		
	}

}
