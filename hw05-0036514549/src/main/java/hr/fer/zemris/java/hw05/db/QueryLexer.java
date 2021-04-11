package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * The lexer for {@code QueryParser}. Tokens are instances of {@link QueryToken}
 * and token types are defined by {@link QueryTokenType}.
 * 
 * @author Matija Frandolić
 */
public class QueryLexer {

	/**
	 * Input characters.
	 */
	private char[] data;
	
	/**
	 * Current token.
	 */
	private QueryToken token;
	
	/**
	 * Current index of character array.
	 */
	private int currentIndex;
	
	/**
	 * Constructs a new {@code QueryLexer} object from the given
	 * input text.
	 * 
	 * @param text input text to be tokenized by this lexer
	 */
	public QueryLexer(String text) {
		Objects.requireNonNull(text, "Input text cannot be null.");
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
	}
	
	/**
	 * Returns the current token of this lexer.
	 * 
	 * @return the current token of this lexer
	 */
	public QueryToken getToken() {
		return token;
	}
	
	/**
	 * Returns the next token from the input of this lexer.
	 * 
	 * @return the next token from the input of this lexer
	 * @throws QueryLexerExceptions if a lexing error occurs
	 */
	public QueryToken nextToken() {
		if (token != null && token.getType() == QueryTokenType.EOF) {
			throw new QueryLexerException("No more tokens.");
		}
		
		skipWhiteSpace();
		
		if (currentIndex >= data.length) {
			token = new QueryToken(QueryTokenType.EOF, null);
			return token;
		}
		
		if (data[currentIndex] == '"') {
			currentIndex++;
			String stringLiteral = extractStringLiteral();
			token = new QueryToken(QueryTokenType.STRING_LITERAL, stringLiteral);
			return token;
		}
		
		if (Character.isLetter(data[currentIndex])) {
			String characterSequence = extractCharacterSequence();
			
			if (characterSequence.equals("LIKE")) {
				token = new QueryToken(QueryTokenType.OPERATOR, "LIKE");
				return token;
			}
			
			if (characterSequence.toLowerCase().equals("and")) {
				token = new QueryToken(QueryTokenType.AND_OPERATOR, null);
				return token;
			}
			
			token = new QueryToken(QueryTokenType.ATTRIBUTE, characterSequence);
			return token;
		}
		
		String extractedOperator = extractOperator();
		String[] operators = new String[] {">", "<", ">=", "<=", "=", "!="};
		for (String operator : operators) {
			if (extractedOperator.equals(operator)) {
				token = new QueryToken(QueryTokenType.OPERATOR, operator);
				return token;
			}
		}
		
		throw new QueryLexerException("Unknown token type.");
	}
	
	/**
	 * Skips whitespace characters from the input, as determined by 
	 * {@link Character#isWhitespace(char)} method.
	 */
	private void skipWhiteSpace() {
		while (currentIndex < data.length &&
			   Character.isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
	}
	
	/**
	 * Extracts a string literal from the input. String literal starts and 
	 * ends with double quotes. 
	 * 
	 * @return the string that represents extracted string literal
	 */
	private String extractStringLiteral() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length &&
			   data[currentIndex] != '"') {
			
			sb.append(data[currentIndex++]);
		}
		
		currentIndex++;
		return sb.toString();
	}
	
	/**
	 * Extracts characters from the input until whitespace or operator symbol
	 * or double quotes appear and returns the string of those characters.
	 * 
	 * @return the string of characters from the input until the first
	 *         whitespace or operator symbol or double quotes
	 */
	private String extractCharacterSequence() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length &&
			   !Character.isWhitespace(data[currentIndex]) &&
			   data[currentIndex] != '>' &&
			   data[currentIndex] != '<' &&
			   data[currentIndex] != '!' &&
			   data[currentIndex] != '=' &&
			   data[currentIndex] != '"') {
			
			sb.append(data[currentIndex++]);
		}
		
		return sb.toString();
	}
	
	/**
	 * Extracts characters from the input until whitespace or double quotes appear 
	 * and returns the string of those characters.
	 * 
	 * @return the string of characters from the input until the first
	 *         whitespace or double quotes
	 */
	private String extractOperator() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length &&
			   !Character.isWhitespace(data[currentIndex]) &&
			   data[currentIndex] != '"') {
			
			sb.append(data[currentIndex++]);
		}
		
		return sb.toString();
	}
		
}
