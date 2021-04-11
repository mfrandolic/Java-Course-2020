package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Implementation of a collection of objects that allows storage of multiple values
 * for each key and provides stack-like abstraction by keeping separate stack of 
 * objects for each key. Keys are instances of {@link String} class and values
 * are instances of {@link ValueWrapper} class.
 * 
 * @author Matija Frandolić
 */
public class ObjectMultistack {
	
	/**
	 * Node of a singly-linked list.
	 */
	private static class MutltistackEntry {
		/**
		 * Value kept by this node.
		 */
		private ValueWrapper value;
		/**
		 * Reference to the next node.
		 */
		private MutltistackEntry next;
	}
	
	/**
	 * Map used for implementation.
	 */
	private Map<String, MutltistackEntry> map = new HashMap<>();
	
	/**
	 * Pushes the given value on the stack that is associated with the given key.
	 * 
	 * @param keyName      key used to determine on which stack to push the given value
	 * @param valueWrapper value that is pushed to the stack that is associated 
	 *                     with the given key
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		MutltistackEntry newEntry = new MutltistackEntry();
		newEntry.value = valueWrapper;
		
		if (!isEmpty(keyName)) {
			newEntry.next = map.get(keyName);
		}
		
		map.put(keyName, newEntry);
	}
	
	/**
	 * Pops the value from the stack that is associated with the given key.
	 * 
	 * @param  keyName key used to determine from which stack to pop the value
	 * @return         value that is popped from the stack that is associated 
	 *                 with the given key
	 * @throws NoSuchElementException if the stack is empty
	 */
	public ValueWrapper pop(String keyName) {
		if (isEmpty(keyName)) {
			throw new NoSuchElementException("Stack for the given key is empty.");
		}
		
		MutltistackEntry topOfStack = map.get(keyName);
		
		if (topOfStack.next != null) {
			map.put(keyName, topOfStack.next);			
		} else {
			map.remove(keyName);
		}
		
		return topOfStack.value;
	}
	
	/**
	 * Peeks the value from the stack that is associated with the given key.
	 * 
	 * @param  keyName key used to determine from which stack to peek the value
	 * @return         value that is peeked from the stack that is associated 
	 *                 with the given key
	 * @throws NoSuchElementException if the stack is empty
	 */
	public ValueWrapper peek(String keyName) {
		if (isEmpty(keyName)) {
			throw new NoSuchElementException("Stack for the given key is empty.");
		}
		
		return map.get(keyName).value;
	}
	
	/**
	 * Returns {@code true} if the stack that is associated with the given key
	 * is empty and {@code false} otherwise. 
	 * 
	 * @param  keyName key used to determine for which stack to check if it is empty
	 * @return {@code true} if the stack that is associated with the given key
	 *         is empty, {@code false} otherwise
	 */
	public boolean isEmpty(String keyName) {
		return !map.containsKey(keyName);
	}
	
}
