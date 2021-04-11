package hr.fer.zemris.java.hw05.db;

/**
 * A model of the conditional expression that consists of the {@code IFieldValueGetter}
 * object, the string that represents a string literal, and the {@code IComparisonOperator}
 * object.
 * 
 * @author Matija Frandolić
 */
public class ConditionalExpression {

	/**
	 * Field getter of this expression.
	 */
	private IFieldValueGetter fieldGetter;
	
	/**
	 * String literal of this expression.
	 */
	private String stringLiteral;
	
	/**
	 * Comparison operator of this expression.
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructs a new {@code ConditionalExpression} from the given
	 * {@code IFieldValueGetter} object, string that represents a string literal,
	 * and the {@code IComparisonOperator} object.
	 * 
	 * @param fieldGetter        field getter of this expression
	 * @param stringLiteral      string literal of this expression
	 * @param comparisonOperator comparison operator of this expression
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Returns the field getter of this expression.
	 * 
	 * @return the field getter of this expression
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	
	/**
	 * Returns the string literal of this expression.
	 * 
	 * @return the string literal of this expression
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
	
	/**
	 * Returns comparison operator of this expression.
	 * 
	 * @return comparison operator of this expression
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
}
