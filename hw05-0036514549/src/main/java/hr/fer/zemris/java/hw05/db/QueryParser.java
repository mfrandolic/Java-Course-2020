package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for the query statement. It creates a list of {@code ConditionalExpression}s
 * that are read from the query.
 * 
 * @author Matija Frandolić
 */
public class QueryParser {
	
	/**
	 * Lexer used for tokenization of query.
	 */
	private QueryLexer lexer;
	
	/**
	 * The JMBAG that was given in direct query.
	 */
	private String queriedJMBAG;
	
	/**
	 * List of expressions from the query.
	 */
	private List<ConditionalExpression>	expressions;
	
	/**
	 * Construct a new {@code QueryParser} from the given query.
	 * 
	 * @param  query query to parse 
	 * @throws QueryParserException if a parsing error occurs
	 */
	public QueryParser(String query) {
		lexer = new QueryLexer(query);
		queriedJMBAG = null;
		expressions = new ArrayList<ConditionalExpression>();
		
		try {
			parse();			
		} catch (QueryLexerException e) {
			throw new QueryParserException(e.getMessage());
		}
	}
	
	/**
	 * Returns {@code true} if query is direct, otherwise {@code false}.
	 * 
	 * @return {@code true} if query is direct, otherwise {@code false}
	 */
	public boolean isDirectQuery() {
		return queriedJMBAG != null;
	}
	
	/**
	 * Returns the JMBAG that was given in direct query if the query was direct.
	 * 
	 * @return the JMBAG that was given in direct query
	 * @throws IllegalStateException if query was not direct
	 */
	public String getQueriedJMBAG() {
		if (queriedJMBAG == null) {
			throw new IllegalStateException("Query was not direct.");
		}
		return queriedJMBAG;
	}
	
	/**
	 * Returns the list of {@code ConditionalExpression}s that were read 
	 * from the query.
	 * 
	 * @return the list of {@code ConditionalExpression}s read from the query
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}
	
	/**
	 * Parses the query and adds parsed expressions to the list of expressions.
	 * 
	 * @throws QueryParserException if a parsing error occurs
	 */
	private void parse() {
		// used as help to check if query is direct
		boolean andAppeared = false;
		
		while (true) {
			// query cannot end right after "and" operator, attribute name is expected
			if (lexer.getToken() != null && 
			    lexer.getToken().getType() == QueryTokenType.AND_OPERATOR) {
				lexer.nextToken();
			} else {
				lexer.nextToken();
				if (lexer.getToken().getType() == QueryTokenType.EOF) {
					break;
				}
			}

			if (lexer.getToken().getType() != QueryTokenType.ATTRIBUTE) {
				throw new QueryParserException("Expected attribute name.");
			}
			String attributeName = (String) lexer.getToken().getValue();
			IFieldValueGetter fieldGetter = convertToFieldGetter(attributeName);
			
			lexer.nextToken();
			if (lexer.getToken().getType() != QueryTokenType.OPERATOR) {
				throw new QueryParserException("Expected operator.");
			}
			String operator = (String) lexer.getToken().getValue();
			IComparisonOperator comparisonOperator = convertToComparisonOperator(operator);
			
			lexer.nextToken();
			if (lexer.getToken().getType() != QueryTokenType.STRING_LITERAL) {
				throw new QueryParserException("Expected string literal.");
			}
			String stringLiteral = (String) lexer.getToken().getValue();
			
			if (operator.equals("LIKE") && 
			    stringLiteral.indexOf("*") != stringLiteral.lastIndexOf("*")) {
				throw new QueryParserException("Wildcard cannot appear more than once.");
			}

			expressions.add(new ConditionalExpression(fieldGetter, stringLiteral, comparisonOperator));
			
			// check if it is direct query
			lexer.nextToken();
			if (lexer.getToken().getType() == QueryTokenType.EOF) {
				if (attributeName.equals("jmbag") && operator.equals("=") && !andAppeared) {
					queriedJMBAG = stringLiteral;
				}
				break;
			}
			// "and" operator must follow if it isn't direct query
			if (lexer.getToken().getType() != QueryTokenType.AND_OPERATOR) {
				throw new QueryParserException("Expected \"and\" operator.");
			}
			andAppeared = true;
		}
	}
	
	/**
	 * Returns the {@code IFieldValueGetter} object that corresponds to the
	 * given attribute name.
	 * 
	 * @param attributeName the name of the attribute
	 * @return              the {@code IFieldValueGetter} object that corresponds 
	 *                      to the given attribute name
	 */
	private IFieldValueGetter convertToFieldGetter(String attributeName) {
		switch (attributeName) {
		case "jmbag":
			return FieldValueGetters.JMBAG;
		case "lastName":
			return FieldValueGetters.LAST_NAME;
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
		default:
			throw new QueryParserException("Unknown attribute.");
		}		
	}
	
	/**
	 * Returns the {@code IComparisonOperator} object that corresponds to the
	 * given operator symbol.
	 * 
	 * @param operator the symbol of the operator
	 * @return         the {@code IComparisonOperator} object that corresponds 
	 *                 to the given operator symbol
	 */
	private IComparisonOperator convertToComparisonOperator(String operator) {
		switch (operator) {
		case "<":
			return ComparisonOperators.LESS;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case ">":
			return ComparisonOperators.GREATER;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "=":
			return ComparisonOperators.EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			throw new QueryParserException("Unsupported operator.");
		}		
	}

}
