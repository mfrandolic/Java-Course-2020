package hr.fer.zemris.java.custom.collections;

/**
 * A model of an object capable of performing some operation on the 
 * passed object.
 * 
 * @author Matija Frandolić
 * 
 * @param <T> the type of object that is processed
 */
public interface Processor<T> {

	/**
	 * Performs some operation on the passed object.
	 * 
	 * @param value the object on which the operation is performed
	 */
	void process(T value);
	
}
