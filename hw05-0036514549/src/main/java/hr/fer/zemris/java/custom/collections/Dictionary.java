package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Implementation of a key-value pair based collection. It is used
 * for storing values that are accessed by their associated keys.
 * Keys cannot be {@code null}. Values can be {@code null}.
 * 
 * @author Matija Frandolić
 *
 * @param <K> the type of objects used as keys
 * @param <V> the type of objects used as values
 */
public class Dictionary<K, V> {

	/**
	 * The underlying collection used for the implementation.
	 */
	private ArrayIndexedCollection<Pair<K, V>> dictionary;

	/**
	 * Constructs an empty dictionary.
	 */
	public Dictionary() {
		dictionary = new ArrayIndexedCollection<Pair<K, V>>();
	}

	/**
	 * Returns {@code true} if this dictionary contains no pairs and 
	 * {@code false} otherwise.
	 * 
	 * @return {@code true} if this dictionary contains no pairs, 
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return dictionary.isEmpty();
	}

	/**
	 * Returns the number of currently stored key-value pairs in this dictionary.
	 * 
	 * @return the number of currently stored key-value pairs in this dictionary
	 */
	public int size() {
		return dictionary.size();
	}

	/**
	 * Removes all pairs from this dictionary.
	 */
	public void clear() {
		dictionary.clear();
	}

	/**
	 * Puts a new key-value pair with the given key and value into this dictionary. 
	 * If the pair with the given key already exists, then the original value 
	 * is updated to the given value. Key cannot be {@code null}.
	 * 
	 * @param  key   the object to be used as key
	 * @param  value the object to be used as value
	 * @throws NullPointerException if the given key is {@code null}
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key, "Dictionary key cannot be null.");
		
		for (int i = 0, size = dictionary.size(); i < size; i++) {
			Pair<K, V> pair = dictionary.get(i);
			if (key.equals(pair.key)) {
				pair.value = value;
				return;
			}
		}
		
		dictionary.add(new Pair<K, V>(key, value));
	}

	/**
	 * Returns the value of the given key if the pair exists, otherwise 
	 * {@code null} is returned.
	 * 
	 * @param  key the key by which to find the value
	 * @return     the value of the key if the pair exists, otherwise {@code null}
	 */
	public V get(Object key) {
		if (key == null) {
			 return null;
		}
		
		for (int i = 0, size = dictionary.size(); i < size; i++) {
			Pair<K, V> pair = dictionary.get(i);
			if (key.equals(pair.key)) {
				return pair.value;
			}
		}
		
		return null;
	}
	
	/**
	 * A model of a key-value pair.
	 *
	 * @param <K> the type of the key object
	 * @param <V> the type of the value object
	 */
	private static class Pair<K, V> {
		
		/**
		 * Key of this pair.
		 */
		private K key;
		
		/**
		 * Value of this pair.
		 */
		private V value;
		
		/**
		 * Constructs a new pair from the given key and the given value.
		 * Key cannot be {@code null}.
		 * 
		 * @param  key   the object to be used as key
		 * @param  value the object to be used as value
		 * @throws NullPointerException if the given key is {@code null}
		 */
		public Pair(K key, V value) {
			this.key = Objects.requireNonNull(key, "Dictionary key cannot be null.");
			this.value = value;
		}
		
	}

}
