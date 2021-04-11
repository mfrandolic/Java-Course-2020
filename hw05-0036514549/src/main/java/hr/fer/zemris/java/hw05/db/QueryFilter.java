package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * The {@code IFilter} implementation that represents a composite filter.
 * This filter accepts the given record only if all filters kept by this
 * filter accept it.
 * 
 * @author Matija Frandolić
 */
public class QueryFilter implements IFilter {

	/**
	 * List of expressions to be checked.
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Constructs a new {@code QueryFilter} from the given list of 
	 * {@code ConditionalExpression}s to be checked.
	 * 
	 * @param expressions the list of expressions to be checked
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expression : expressions) {
			IFieldValueGetter fieldGetter = expression.getFieldGetter();
			String stringLiteral = expression.getStringLiteral();
			IComparisonOperator operator = expression.getComparisonOperator();
			
			if (!operator.satisfied(fieldGetter.get(record), stringLiteral)) {
				return false;
			}
		}
		
		return true;
	}
	
}
