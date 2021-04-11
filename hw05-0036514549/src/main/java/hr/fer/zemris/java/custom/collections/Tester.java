package hr.fer.zemris.java.custom.collections;

/**
 * A model of an object capable of performing a test on the 
 * passed object and deciding whether to accept it or not.
 * 
 * @author Matija Frandolić
 * 
 * @param <T> the type of object being tested
 */
public interface Tester<T> {

	/**
	 * Returns {@code true} if this tester accepts the given
	 * object or {@code false} otherwise.
	 * 
	 * @param  obj the object that needs to be tested 
	 * @return     {@code true} if this tester accepts the given object,
	 *             {@code false} otherwise
	 */
	boolean test(T obj);
	
}
