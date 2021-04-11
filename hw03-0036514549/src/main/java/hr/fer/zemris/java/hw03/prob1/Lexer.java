package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * An implementation of a simple lexer. This lexer can generate different 
 * tokens depending on state it is currently in. Allowed states are defined
 * by {@link LexerState}. Tokens are instances of {@link Token} and token types 
 * are defined by {@link TokenType}.
 * 
 * @author Matija Frandolić
 */
public class Lexer {

	/**
	 * Input characters.
	 */
	private char[] data;
	
	/**
	 * Current token.
	 */
	private Token token;
	
	/**
	 * Current index of character array.
	 */
	private int currentIndex;
	
	/**
	 * Current state.
	 */
	private LexerState state;
	
	/**
	 * Constructs a new {@code Lexer} object from the given input text.
	 * 
	 * @param text input text to be tokenized by this lexer
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text, "Input string cannot be null.");
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	/**
	 * Sets the state of this lexer. State can only be one of
	 * {@link LexerState} defined values.
	 * 
	 * @param state new state of this lexer
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state, "State cannot be null.");
		this.state = state;
	}
	
	/**
	 * Returns the current token of this lexer.
	 * 
	 * @return the current token of this lexer
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Returns the next token from the input of this lexer.
	 * 
	 * @return the next token from the input of this lexer
	 * @throws LexerException if a lexing error occurs
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("No more tokens.");
		}
		
		skipWhiteSpace();
		
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if (state == LexerState.BASIC) {
			tokenizeByBasicRules();
		} else {
			tokenizeByExtendedRules();
		}
		
		return token;
	}
	
	/**
	 * Tokenizes the input text by using rules for the basic mode.
	 * Generated tokens in this mode are word, number and symbol tokens.
	 * 
	 * @throws LexerException if a lexing error occurs
	 */
	private void tokenizeByBasicRules() {
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			String word = extractWord();
			token = new Token(TokenType.WORD, word);
			return;
		}
			
		if (Character.isDigit(data[currentIndex])) {
			Long number = extractNumber();
			token = new Token(TokenType.NUMBER, number);
			return;
		}
		
		token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex++]));
	}
	
	/**
	 * Tokenizes the input text by using rules for the extended mode.
	 * Only word tokens and symbol token with value "#" are generated
	 * in this mode.
	 */
	private void tokenizeByExtendedRules() {
		if (data[currentIndex] != '#') {
			
			StringBuilder sb = new StringBuilder();
			
			while (currentIndex < data.length &&
				   data[currentIndex] != '\r' && 
				   data[currentIndex] != '\n' && 
				   data[currentIndex] != '\t' &&
				   data[currentIndex] != ' '  &&
				   data[currentIndex] != '#') {
				
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			
			String word = sb.toString();
			
			token = new Token(TokenType.WORD, word);
			return;
		}	
			
		token = new Token(TokenType.SYMBOL, Character.valueOf(data[currentIndex++]));
		return;
	}
	
	/**
	 * Skips whitespace characters from the input. Ignored whitespace
	 * characters are: '\r', '\n', '\t' and ' '.
	 */
	private void skipWhiteSpace() {
		while (currentIndex < data.length &&
			  (data[currentIndex] == '\r' || 
			   data[currentIndex] == '\n' || 
			   data[currentIndex] == '\t' ||
			   data[currentIndex] == ' ')) {
			currentIndex++;
		}
	}
	
	/**
	 * Extracts a valid word from the input and returns it as 
	 * a string. Valid word contains letters and escaped digits.
	 * Escape "\\\\" is used to escape the escape character itself.
	 * 
	 * @return the string that contains valid word extracted
	 *         from the input of this lexer
	 */
	private String extractWord() {
		StringBuilder sb = new StringBuilder();
		
		while (currentIndex < data.length && 
			  (Character.isLetter(data[currentIndex]) ||
			   data[currentIndex] == '\\')) {
			
			if (data[currentIndex] == '\\') {
				if (currentIndex < data.length - 1 &&
				   (Character.isDigit(data[currentIndex + 1]) ||
					data[currentIndex + 1] == '\\')) {
					
					sb.append(data[currentIndex + 1]);
					currentIndex += 2;
					continue;
				} 
				
				throw new LexerException("Invalid escape sequence.");
			}
			
			sb.append(data[currentIndex++]);
		}
		
		return sb.toString();
	}
	
	/**
	 * Extracts a number from the input and returns it as {@code Long}. 
	 * 
	 * @return number extracted from the input of this lexer
	 */
	private Long extractNumber() {
		StringBuilder sb = new StringBuilder();
		
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex++]);
		}

		Long number;
		try {
			number = Long.parseLong(sb.toString());
		} catch (NumberFormatException ex) {
			throw new LexerException("Not a parsable number.");
		}
		
		return number;
	}
	
}
