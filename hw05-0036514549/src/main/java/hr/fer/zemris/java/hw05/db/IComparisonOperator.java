package hr.fer.zemris.java.hw05.db;

/**
 * A model of a comparison operator that can compare two strings and return 
 * boolean value depending on whether this operator is satisfied or not.
 * 
 * @author Matija Frandolić
 */
public interface IComparisonOperator {

	/**
	 * Returns {@code true} if this operator is satisfied by the comparison
	 * of the given strings or {@code false} otherwise.
	 * 
	 * @param value1 the string to be checked with the other given string
	 * @param value2 the string to be checked with the other given string
	 * @return       {@code true} if this operator is satisfied by the comparison
	 *               of the given strings, {@code false} otherwise
	 */
	boolean satisfied(String value1, String value2);
	
}
