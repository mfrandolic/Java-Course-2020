package hr.fer.zemris.java.hw05.db;

/**
 * This class offers {@code IComparisonOperator}s for comparison between strings.
 * Implemented comparison operators are: "<", "<=", ">", ">=", "=", "!=" and "LIKE".
 * 
 * @author Matija Frandolić
 */
public class ComparisonOperators {

	/**
	 * Comparison operator "<".
	 */
	public static final IComparisonOperator LESS = 
			(value1, value2) -> value1.compareTo(value2) < 0;
			
	/**
	 * Comparison operator "<=".
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = 
			(value1, value2) -> value1.compareTo(value2) <= 0;
			
	/**
	 * Comparison operator ">".
	 */
	public static final IComparisonOperator GREATER =
			(value1, value2) -> value1.compareTo(value2) > 0;
			
	/**
	 * Comparison operator ">=".
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = 
			(value1, value2) -> value1.compareTo(value2) >= 0;
			
	/**
	 * Comparison operator "=".
	 */
	public static final IComparisonOperator EQUALS = 
			(value1, value2) -> value1.equals(value2);
			
	/**
	 * Comparison operator "!=".
	 */
	public static final IComparisonOperator NOT_EQUALS = 
			(value1, value2) -> !value1.equals(value2);
			
	/**
	 * Comparison operator "LIKE".
	 */
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		if (!value2.contains("*")) {
			return value1.equals(value2);
		}
		
		if (value2.indexOf("*") != value2.lastIndexOf("*")) {
			throw new IllegalArgumentException("Wildcard cannot appear more than once.");
		}
		
		String beforeWildcard = value2.substring(0, value2.indexOf("*"));
		String afterWildcard = value2.substring(value2.indexOf("*") + 1);
		
		if (value1.length() < (beforeWildcard + afterWildcard).length()) {
			return false;
		}
		
		return value1.startsWith(beforeWildcard) && value1.endsWith(afterWildcard);
	};
			
}
