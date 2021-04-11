package hr.fer.zemris.java.custom.collections;

/**
 * This interface represents a model of an object capable of
 * iterating through a collection.
 * 
 * @author Matija Frandolić
 */
public interface ElementsGetter {

	/**
	 * Returns {@code true} if the collection has one more
	 * element or {@code false} if it has no more elements.
	 * 
	 * @return {@code true} if the collection has one more element,
	 *         {@code false} if there are no more elements
     * @throws ConcurrentModificationException if collection changed
	 */
	boolean hasNextElement();
	
	/**
	 * Returns the next element of the collection if it exists or
	 * {@code NoSuchElementException} otherwise.
	 * 
	 * @return next element of the collection
	 * @throws NoSuchElementException if next element doesn't exist
	 * @throws ConcurrentModificationException if collection changed
	 */
	Object getNextElement();
	
	/**
	 * Processes the remaining elements of the collection by using
	 * the the given {@link Processor}.
	 * 
	 * @param p the processor used for processing the remaining elements
	 *          of the collection
	 */
	default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
	
}
