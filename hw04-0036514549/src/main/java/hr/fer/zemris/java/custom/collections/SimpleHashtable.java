package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implementation of a hash table data structure that allows storage of
 * key-value entries. Each value is accessed by its associated key.
 * Duplicate keys are not allowed and keys cannot be {@code null}. 
 * Values can be {@code null}. The hash table is resized if the load factor
 * becomes greater than the default threshold (0.75).
 * 
 * @author Matija Frandolić
 *
 * @param <K> the type of objects used as keys
 * @param <V> the type of objects used as values
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {

	/**
	 * The underlying array used for the implementation.
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * Current number of key-value entries stored in this hash table.
	 */
	private int size;
	
	/**
	 * Number of modifications to this hash table.
	 */
	private int modificationCount;
	
	/**
	 * Default capacity of the underlying array.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * Default overflow threshold for resizing the underlying array.
	 */
	private static final double DEFAULT_OVERFLOW_THRESHOLD = 0.75;	
	
	/**
	 * Constructs an empty hash table with the given initial number of slots.
	 * The given capacity is rounded to the first power of 2 that is greater
	 * than or equal to the given number. 
	 * 
	 * @param  capacity the initial number of slots in this hash table
	 * @throws IllegalArgumentException if the given capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Initial capacity must not be less than 1.");
		}
		int powerOf2Capacity = (int) Math.pow(2, Math.ceil(Math.log(capacity) / Math.log(2)));
		table = (TableEntry<K, V>[]) new TableEntry[powerOf2Capacity];
		size = 0;
	}
	
	/**
	 * Constructs an empty hash table with the default initial number of slots (16).
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Calculates hash for the the given key and returns index of the
	 * corresponding slot in the given table.
	 * 
	 * @param  key   the key for which to calculate hash  
	 * @param  table the table for which to determine index of the slot in which 
	 *               the key would be inserted
	 * @return       index of the slot in the given table in which the given 
	 *               key would be inserted
	 */
	private int hash(Object key, TableEntry<K, V>[] table) {
		return Math.abs(key.hashCode()) % table.length;
	}
	
	/**
	 * Puts a new key-value entry with the given key and value into this hash 
	 * table. If the entry with the given key already exists, then the original 
	 * value is updated to the given value. Key cannot be {@code null}.
	 * 
	 * @param  key   the object to be used as key
	 * @param  value the object to be used as value
	 * @throws NullPointerException if the given key is {@code null}
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key, "Key cannot be null.");
		
		if (putIntoTable(table, key, value)) {
			size++;
			modificationCount++;
		}
		
		double loadFactor = (double) size / table.length;
		if (loadFactor >= DEFAULT_OVERFLOW_THRESHOLD) {
			doubleCapacity();
		}
	}
	
	/**
	 * Puts a new key-value entry with the given key and value into the given table 
	 * and returns {@code true} if the the entry with the given key doesn't 
	 * already exist. If the entry with the given key already exists, then the 
	 * original value is updated to the given value and {@code false} is returned.
	 * 
	 * @param  key   the object to be used as key
	 * @param  value the object to be used as value
	 * @return       {@code true} if new entry was added, {@code false} otherwise
	 */
	private boolean putIntoTable(TableEntry<K, V>[] table, K key, V value) {		
		int slotIndex = hash(key, table);
		if (table[slotIndex] == null) {
			table[slotIndex] = new TableEntry<>(key, value);
			return true;
		}
		
		TableEntry<K, V> listElement = table[slotIndex];
		TableEntry<K, V> previousElement = listElement;
		
		while (listElement != null) {
			if (key.equals(listElement.key)) {
				listElement.value = value;
				return false;
			}
			previousElement = listElement;
			listElement = listElement.next;
		}
		
		previousElement.next = new TableEntry<>(key, value);
		return true;
	}
	
	/**
	 * Doubles the capacity of this hash table.
	 */
	private void doubleCapacity() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[2 * table.length];
		
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> listElement = table[i];
			while (listElement != null) {
				putIntoTable(newTable, listElement.key, listElement.value);
				listElement = listElement.next;
			}
		}
		
		table = newTable;
		modificationCount++;
	}
	
	/**
	 * Returns the value of the given key if the entry exists, otherwise 
	 * {@code null} is returned.
	 * 
	 * @param  key the key by which to find the value
	 * @return     the value of the key if the entry exists, otherwise {@code null}
	 */
	public V get(Object key) {
		if (key == null) {
			 return null;
		}
		
		int slotIndex = hash(key, table);
		TableEntry<K, V> listElement = table[slotIndex];
		
		while (listElement != null) {
			if (key.equals(listElement.key)) {
				return listElement.value;
			}
			listElement = listElement.next;
		}
		
		return null;
	}
	
	/**
	 * Returns the number of currently stored key-value entries in this hash table.
	 * 
	 * @return the number of currently stored key-value entries in this hash table
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns {@code true} if the entry with the given key exists in this
	 * hash table and {@code false} otherwise.
	 * 
	 * @param  key the key for which to find if it exists in this hash table
	 * @return     {@code true} if the entry with the given key exists in this 
	 *             hash table, {@code false} otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			 return false;
		}
		
		int slotIndex = hash(key, table);
		TableEntry<K, V> listElement = table[slotIndex];
		
		while (listElement != null) {
			if (key.equals(listElement.key)) {
				return true;
			}
			listElement = listElement.next;
		}
		
		return false;
	}
	
	/**
	 * Returns {@code true} if the entry with the given value exists in this
	 * hash table and {@code false} otherwise.
	 * 
	 * @param  value the value for which to find if it exists in this hash table
	 * @return       {@code true} if the entry with the given value exists in this 
	 * 				 hash table, {@code false} otherwise
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) {
				continue;
			}
			
			TableEntry<K, V> listElement = table[i];
			while (listElement != null) {
				if (value == null && listElement.value == null) {
					return true;
				}
				if (value != null && value.equals(listElement.value)) {
					return true;
				}
				listElement = listElement.next;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes the entry with the given key from this hash table.
	 * 
	 * @param key the key by which to find the entry to be removed
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}
		
		int slotIndex = hash(key, table);
		TableEntry<K, V> listElement = table[slotIndex];
		
		if (listElement == null ) {
			return;
		}
		
		if (key.equals(listElement.key)) {
			table[slotIndex] = listElement.next;
			size--;
			modificationCount++;
			return;
		}
		
		TableEntry<K, V> previousElement = listElement;
		listElement = listElement.next;
		while (listElement != null) {
			if (key.equals(listElement.key)) {
				previousElement.next = listElement.next;
				size--;
				modificationCount++;
				return;
			}
			previousElement = listElement;
			listElement = listElement.next;
		}
	}
	
	/**
	 * Returns {@code true} if this hash table contains no entries and 
	 * {@code false} otherwise.
	 * 
	 * @return {@code true} if this hash table contains no entries, 
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Removes all entries from this hash table.
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Returns the string representation of this hash table. The format of the
	 * string is as illustrated: "[key1=value1, key2=value2, key3 = value3]"
	 *
	 * @return the string representation of this hash table
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		
		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> listElement = table[i];
			while (listElement != null) {
				sb.append(listElement.key)
				  .append("=")
				  .append(listElement.value)
				  .append(", ");
				listElement = listElement.next;
			}
		}
		
		String s = sb.toString();
		if (s.lastIndexOf(",") != -1) {
			s = s.substring(0, s.length() - 2);
		}
		
		return s + "]";
	}
	
	/**
	 * Returns a new {@code IteratorImpl} over the entries of this hash table.
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Representation of a hash table entry. The entry contains key-value pair
	 * and a reference to the next entry in the same slot.
	 *
	 * @param <K> the type of the key object
	 * @param <V> the type of the value object
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * Key of this entry.
		 */
		private K key;
		
		/**
		 * Value of this entry.
		 */
		private V value;
		
		/**
		 * Reference to the next entry.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Constructs a new entry from the given key and the given value.
		 * Key cannot be {@code null}.
		 * 
		 * @param  key   the object to be used as key
		 * @param  value the object to be used as value
		 * @throws NullPointerException if the given key is {@code null}
		 */
		public TableEntry(K key, V value) {
			this.key = Objects.requireNonNull(key, "Key cannot be null.");
			this.value = value;
		}
		
		/**
		 * Returns the key of this entry.
		 * 
		 * @return the key of this entry
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Returns the value of this entry.
		 * 
		 * @return the value of this entry
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Sets the value of this entry.
		 * 
		 * @param value a new value of this entry
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	/**
	 * Implementation of {@code Iterator} for {@code SimpleHashtable}. Entries
	 * are returned in order from the first slot to the last one and in each 
	 * slot from the first entry to the last one.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Index of the slot of the current entry.
		 */
		private int currentSlot;
		
		/**
		 * Current entry to be returned by this iterator.
		 */
		private TableEntry<K, V> currentEntry;
		
		/**
		 * Last entry returned by this iterator.
		 */
		private TableEntry<K, V> lastEntry;
		
		/**
		 * Saved modification count of the corresponding hash table.
		 */
		private int savedModificationCount;
		
		/**
		 * Constructs a new iterator for the corresponding hash table.
		 */
		public IteratorImpl() {
			savedModificationCount = modificationCount;
			
			for (int i = 0; i < table.length; i++) {
				if (table[i] != null) {
					currentSlot = i;
					currentEntry = table[i];
					lastEntry = null;
					break;
				}
			}
		}

		/**
		 * Returns {@code true} if the hash table has one more entry or 
		 * {@code false} if it has no more entries.
		 * 
		 * @return {@code true} if the hash table has one more entry,
		 *         {@code false} if there are no more entries
	     * @throws ConcurrentModificationException if the hash table changed while iterating
		 */
		@Override
		public boolean hasNext() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Concurrent modification not allowed.");
			}
			
			return currentEntry != null;
		}

		/**
		 * Returns the next entry of the hash table if it exists or
		 * {@code NoSuchElementException} otherwise.
		 * 
		 * @return next entry of the hash table
		 * @throws NoSuchElementException if next entry doesn't exist
		 * @throws ConcurrentModificationException if the hash table changed while iterating
		 */
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Concurrent modification not allowed.");
			}
			
			if (currentEntry == null) {
				throw new NoSuchElementException("No more elements.");
			}
			
			lastEntry = currentEntry;
			
			if (currentEntry.next != null) {
				currentEntry = currentEntry.next;
				return lastEntry;
			}
			
			for (int i = currentSlot + 1; i < table.length; i++) {
				if (table[i] != null) {
					currentSlot = i;
					currentEntry = table[i];
					return lastEntry;
				}
			}
			
			currentEntry = null;
			return lastEntry;
		}
		
		/**
		 * Removes the last entry returned by the {@code next()} method from
		 * the hash table.
		 *
		 * @throws ConcurrentModificationException if the hash table changed while iterating
		 * @throws IllegalStateException if {@code next()} hasn't been called yet
		 * 								 or {@code remove()} has been called more than once
		 *                               for the same {@code next()} call
		 */
		@Override
		public void remove() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Concurrent modification not allowed.");
			}
			
			if (lastEntry == null) {
				throw new IllegalStateException("Method remove() can only be called after next().");
			}
			
			SimpleHashtable.this.remove(lastEntry.key);
			lastEntry = null;
			savedModificationCount = modificationCount;
		}
		
	}

}
