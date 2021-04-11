package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * An implementation of a stack data structure. 
 * Storage of {@code null} references is not allowed.
 * 
 * @author Matija Frandolić
 */
public class ObjectStack {
	
	/**
	 * The underlying collection used for the implementation.
	 */
	private ArrayIndexedCollection arrayCollection;

	/**
	 * Constructs an empty stack.
	 */
	public ObjectStack() {
		arrayCollection = new ArrayIndexedCollection();
	}
	
	/**
	 * Returns {@code true} if this stack contains no objects and 
	 * {@code false} otherwise.
	 * 
	 * @return {@code true} if this stack contains no objects, 
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return arrayCollection.isEmpty();
	}
	
	/**
	 * Returns the number of currently stored objects on this stack.
	 * 
	 * @return the number of currently stored objects on this stack
	 */
	public int size() {
		return arrayCollection.size();
	}
	
	/**
	 * Pushes the given object onto this stack.
	 * 
	 * @param  value the object that is pushed onto the stack
	 * @throws NullPointerException if the given value is {@code null}
	 */
	public void push(Object value) {
		Objects.requireNonNull(value, "Null reference cannot be placed on the stack.");
		arrayCollection.add(value);
	}
	
	/**
	 * Returns the last object pushed on this stack and removes it 
	 * from the stack.
	 * 
	 * @return the last pushed object on this stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public Object pop() {
		if (arrayCollection.size() == 0) {
			throw new EmptyStackException("Stack is empty.");
		}
		
		int indexOfLastElement = arrayCollection.size() - 1;
		Object obj = arrayCollection.get(indexOfLastElement);
		arrayCollection.remove(indexOfLastElement);
		
		return obj;
	}
	
	/**
	 * Returns the last object pushed on this stack.
	 * 
	 * @return the last pushed object on this stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public Object peek() {
		if (arrayCollection.size() == 0) {
			throw new EmptyStackException("Stack is empty.");
		}
		
		int indexOfLastElement = arrayCollection.size() - 1;
		return arrayCollection.get(indexOfLastElement);
	}
	
	/**
	 * Removes all elements from this stack.
	 */
	public void clear() {
		arrayCollection.clear();
	}
	
}
