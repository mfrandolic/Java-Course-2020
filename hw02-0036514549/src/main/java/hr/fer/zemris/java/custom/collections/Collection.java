package hr.fer.zemris.java.custom.collections;

/**
 * This class represents a general collection of objects and provides 
 * methods for working with elements of that collection.
 * 
 * @author Matija Frandolić
 */
public class Collection {

	/**
	 * Constructs a new collection.
	 */
	protected Collection() {
		// empty
	}

	/**
	 * Returns {@code true} if this collection contains no objects and 
	 * {@code false} otherwise.
	 * 
	 * @return {@code true} if this collection contains no objects, 
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size() == 0 ? true : false;
	}

	/**
	 * Returns the number of currently stored objects in this collection.
	 * 
	 * @return the number of currently stored objects in this collection
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into this collection.
	 * 
	 * @param value the object that is added into this collection
	 */
	public void add(Object value) {
		// empty
	}

	/**
	 * Returns {@code true} only if this collection contains the given object,
	 * as determined by {@code equals} method.
	 * 
	 * @param value the object whose presence in this collection is checked
	 * @return      {@code true} if this collection contains the given object,
	 *              {@code false} otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Returns {@code true} only if this collection contains the given object,
	 * as determined by {@code equals} method, and successfully
	 * removes one occurrence of it.
	 * 
	 * @param value the object that is removed from this collection
	 * @return      {@code true} if this collection contains the given object
	 *              and it is removed successfully, {@code false} otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates a new array with size equal to the size of this collection,
	 * fills it with collection content and returns the array.
	 * 
	 * @return the array filled with content of this collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Calls {@code processor.process(...)} for each element of this collection.
	 * 
	 * @param processor the processor that performs some operation
	 */
	public void forEach(Processor processor) {
		// empty
	}

	/**
	 * Adds all elements from the given collection into this collection. The
	 * given collection remains unchanged.
	 * 
	 * @param other the collection whose elements are added into this collection
	 */
	public void addAll(Collection other) {
		class AddToCollectionProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new AddToCollectionProcessor());
	}

	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		// empty
	}

}
