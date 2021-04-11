package hr.fer.zemris.java.custom.collections;

/**
 * This interface represents a list-like collection.
 * 
 * @author Matija Frandolić
 * 
 * @param <T> the type of elements stored in this list
 */
public interface List<T> extends Collection<T> {

	/**
	 * Returns the object that is stored in this collection at the given
	 * index, if that index is legal.
	 * 
	 * @param  index the index of the wanted object
	 * @return       the object at the given position
	 * @throws IndexOutOfBoundsException if index is not legal
	 */
	T get(int index);
	
	/**
	 * Inserts the given object into this collection at the given position,
	 * if that position is legal.
	 * 
	 * @param  value    the object that is inserted into this collection
	 * @param  position the position at which to insert the value
	 * @throws IndexOutOfBoundsException if position is not legal 
	 */
	void insert(T value, int position);
	
	/**
	 * Returns the index of one occurrence of the given {@code value}
	 * or -1 if the {@code value} is not found, as determined by {@code equals}
	 * method.
	 * 
	 * @param value the object whose index needs to be determined
	 * @return      the index of one occurrence of the given object or
	 * 				-1 if the object is not found
	 */
	int indexOf(Object value); 
	
	/**
	 * Removes the element at the specified index from this collection,
	 * if that index is legal.
	 * 
	 * @param  index the index of the element that is removed
	 * @throws IndexOutOfBoundsException if index is not legal
	 */
	void remove(int index); 
	
}
