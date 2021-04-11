package hr.fer.zemris.java.custom.collections;

/**
 * This interface represents a general collection of objects and provides 
 * methods for working with elements of that collection.
 * 
 * @author Matija Frandolić
 * 
 * @param <T> the type of elements in this collection
 */
public interface Collection<T> {

	/**
	 * Returns {@code true} if this collection contains no objects and 
	 * {@code false} otherwise.
	 * 
	 * @return {@code true} if this collection contains no objects, 
	 *         {@code false} otherwise
	 */
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of currently stored objects in this collection.
	 * 
	 * @return the number of currently stored objects in this collection
	 */
	int size();

	/**
	 * Adds the given object into this collection.
	 * 
	 * @param value the object that is added into this collection
	 */
	void add(T value);

	/**
	 * Returns {@code true} only if this collection contains the given object,
	 * as determined by {@code equals} method.
	 * 
	 * @param value the object whose presence in this collection is checked
	 * @return      {@code true} if this collection contains the given object,
	 *              {@code false} otherwise
	 */
	boolean contains(Object value);

	/**
	 * Returns {@code true} only if this collection contains the given object,
	 * as determined by {@code equals} method, and successfully
	 * removes one occurrence of it.
	 * 
	 * @param value the object that is removed from this collection
	 * @return      {@code true} if this collection contains the given object
	 *              and it is removed successfully, {@code false} otherwise
	 */
	boolean remove(Object value);

	/**
	 * Allocates a new array with size equal to the size of this collection,
	 * fills it with collection content and returns the array.
	 * 
	 * @return the array filled with content of this collection
	 */
	Object[] toArray();

	/**
	 * Calls {@code processor.process(...)} for each element of this collection.
	 * 
	 * @param processor the processor that performs some operation
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = createElementsGetter();
		
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}

	/**
	 * Adds all elements from the given collection into this collection. The
	 * given collection remains unchanged.
	 * 
	 * @param other the collection whose elements are added into this collection
	 */
	default void addAll(Collection<? extends T> other) {
		class AddToCollectionProcessor implements Processor<T> {
			@Override
			public void process(T value) {
				add(value);
			}
		}
		other.forEach(new AddToCollectionProcessor());
	}

	/**
	 * Removes all elements from this collection.
	 */
	void clear();
	
	/**
	 * Returns new {@code ElementsGetter} for this collection.
	 * 
	 * @return new {@code ElementsGetter} for this collection
	 */
	ElementsGetter<T> createElementsGetter();
	
	/**
	 * Adds all satisfying elements to this collection from the 
	 * given collection by using the given {@code Tester} to
	 * check whether to add element.
	 * 
	 * @param col    the collection from which to add elements
	 * @param tester the {@code Tester} to check whether to add
	 *               element to this collection
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> getter = col.createElementsGetter();
		
		while (getter.hasNextElement()) {
			T obj = getter.getNextElement();
			if (tester.test(obj)) {
				add(obj);
			}
		}
	}

}
