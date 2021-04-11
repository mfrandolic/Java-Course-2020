package hr.fer.zemris.java.hw05.db;

/**
 * A model of an object that can retrieve a string value from the given record.
 * 
 * @author Matija Frandolić
 */
public interface IFieldValueGetter {

	/**
	 * Returns the string value retrieved from the given record.
	 * 
	 * @param record the {@code StudentRecord} from which to retrieve the string value
	 * @return       the string value retrieved from the given record
	 */
	String get(StudentRecord record);
	
}
