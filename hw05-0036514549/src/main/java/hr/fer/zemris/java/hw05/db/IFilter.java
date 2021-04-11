package hr.fer.zemris.java.hw05.db;

/**
 * A model of a filter that has the ability to accept the {@code StudentRecord}
 * object or reject it.
 * 
 * @author Matija Frandolić
 */
public interface IFilter {

	/**
	 * Returns {@code true} if this filter accepts the given record or 
	 * {@code false} otherwise.
	 * 
	 * @param record the record for which to determine whether this filter accepts it
	 * @return       {@code true} if this filter accepts the given record, 
	 *               {@code false} otherwise
	 */
	boolean accepts(StudentRecord record);
	
}
