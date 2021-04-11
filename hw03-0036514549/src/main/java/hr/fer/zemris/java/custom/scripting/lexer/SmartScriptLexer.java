package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * The lexer for {@code SmartScriptParser}. This lexer can generate different 
 * tokens depending on state it is currently in. Allowed states are defined
 * by {@link SmartScriptLexerState}. Tokens are instances of {@link SmartScriptToken}
 * and token types are defined by {@link SmartScriptTokenType}.
 * 
 * @author Matija Frandolić
 */
public class SmartScriptLexer {
	
	/**
	 * Input characters.
	 */
	private char[] data;
	
	/**
	 * Current token.
	 */
	private SmartScriptToken token;
	
	/**
	 * Current index of character array.
	 */
	private int currentIndex;
	
	/**
	 * Current state.
	 */
	private SmartScriptLexerState state;

	/**
	 * Constructs a new {@code SmartScriptLexer} object from the given
	 * input text.
	 * 
	 * @param text input text to be tokenized by this lexer
	 */
	public SmartScriptLexer(String text) {
		Objects.requireNonNull(text, "Input text cannot be null.");
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
		state = SmartScriptLexerState.TEXT;
	}
	
	/**
	 * Sets the state of this lexer. State can only be one of
	 * {@link SmartScriptLexerState} defined values.
	 * 
	 * @param state new state of this lexer
	 */
	public void setState(SmartScriptLexerState state) {
		this.state = Objects.requireNonNull(state, "State cannot be null.");
	}
	
	/**
	 * Returns the current token of this lexer.
	 * 
	 * @return the current token of this lexer
	 */
	public SmartScriptToken getToken() {
		return token;
	}
	
	/**
	 * Returns the next token from the input of this lexer.
	 * 
	 * @return the next token from the input of this lexer
	 * @throws SmartScriptLexerException if a lexing error occurs
	 */
	public SmartScriptToken nextToken() {
		if (token != null && token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException("No more tokens.");
		}
		
		if (currentIndex >= data.length) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return token;
		}
		
		if (state == SmartScriptLexerState.TEXT) {
			tokenizeByTextRules();				
		} else {
			tokenizeByTagRules();
		} 
		
		return token;
	}
	
	/**
	 * Tokenizes the input text by using rules for text mode. Each token in 
	 * this mode contains text from the previous closing tag to the next
	 * opening tag. Only text tokens and opening tag tokens are generated in
	 * this mode. Following escape sequences are allowed: "\\\\" (interpreted
	 * as "\\") and "\\{" (interpreted as "{"). 
	 * 
	 * @throws SmartScriptLexerException if a lexing error occurs
	 */
	private void tokenizeByTextRules() {
		if (isOpeningTag()) {
			token = new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null);
			currentIndex += 2;
			return;
		}
		
		StringBuilder sb = new StringBuilder();
		
		while (currentIndex < data.length && !isOpeningTag()) {
			if (data[currentIndex] == '\\') {
				if (currentIndex >= data.length - 1) {
					throw new SmartScriptLexerException("Invalid escape sequence in text.");
				}
				
				switch (data[currentIndex + 1]) {
				case '\\':
					sb.append("\\");
					break;
				case '{':
					sb.append("{");
					break;
				default:
					throw new SmartScriptLexerException("Invalid escape sequence in text.");
				}
				
				currentIndex += 2;
				continue;
			}
			
			sb.append(data[currentIndex++]);
		}
		
		token = new SmartScriptToken(SmartScriptTokenType.TEXT, sb.toString());
	}
	
	/**
	 * Tokenizes the input text by using rules for tag mode. Tokens are
	 * generated until the next closing tag is found or until the end
	 * of the input. Whitespace between tokens is ignored. All tokens
	 * except text and opening tag tokens can be generated in this mode.
	 * 
	 * @throws SmartScriptLexerException if a lexing error occurs
	 */
	private void tokenizeByTagRules() {
		skipWhiteSpace();
		
		if (currentIndex >= data.length) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return;
		}
		
		if (isClosingTag()) {
			token = new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null);
			currentIndex += 2;
			return;
		}
		
		if (token != null && token.getType() == SmartScriptTokenType.TAG_OPEN) {
			if (data[currentIndex] == '=') {
				token = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "=");
				currentIndex++;
				return;
			}
			
			if (Character.isLetter(data[currentIndex])) {
				String tagName = extractVariableName();
				token = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, tagName.toLowerCase());
				return;
			}
		}
		
		if (Character.isLetter(data[currentIndex])) {
			String variableName = extractVariableName();
			token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, variableName);
			return;
		}
		
		if (currentIndex < data.length - 1 &&
		    data[currentIndex] == '@' &&
			Character.isLetter(data[currentIndex + 1])) {
			
			currentIndex++;
			String functionName = extractVariableName();
			token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, functionName);
			return;
		}
		
		if (data[currentIndex] == '"') {
			currentIndex++;
			String stringName = extractString();
			token = new SmartScriptToken(SmartScriptTokenType.STRING, stringName);
			return;
		}
		
		if (data[currentIndex] == '+' ||
			data[currentIndex] == '*' ||
			data[currentIndex] == '/' ||
			data[currentIndex] == '^') {
			
			token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, Character.toString(data[currentIndex++]));
			return;
		}
		
		if (data[currentIndex] == '-') {
			if (currentIndex < data.length - 1 && Character.isDigit(data[currentIndex + 1])) {
				tokenizeNumber();
			} else {
				token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, "-");
				currentIndex++;
			}
			return;
		}
		
		if (Character.isDigit(data[currentIndex])) {
			tokenizeNumber();
			return;
		}

		throw new SmartScriptLexerException("Invalid token format.");
	}
	
	/**
	 * Checks whether the next token is the opening tag and returns
	 * {@code true} if it is.
	 * 
	 * @return {@code true} if the next token is the opening tag
	 */
	private boolean isOpeningTag() {
		if (currentIndex < data.length - 1 &&
			data[currentIndex] == '{' &&
			data[currentIndex + 1] == '$') {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether the next token is the closing tag and returns
	 * {@code true} if it is.
	 * 
	 * @return {@code true} if the next token is the closing tag
	 */
	private boolean isClosingTag() {
		if (currentIndex < data.length - 1 &&
			data[currentIndex] == '$' &&
			data[currentIndex + 1] == '}') {
			return true;
		}
		return false;
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
	 * Extracts a valid variable name from the input and returns it
	 * as a string. Valid variable name begins with a letter after
	 * which follows zero or more letters, numbers or underscores.
	 * 
	 * @return the string that contains valid variable name extracted
	 *         from the input of this lexer
	 */
	private String extractVariableName() {
		StringBuilder sb = new StringBuilder();
		
		while (currentIndex < data.length &&
			  (Character.isLetter(data[currentIndex]) ||
			   Character.isDigit(data[currentIndex]) ||
			   data[currentIndex] == '_')) {
			
			sb.append(data[currentIndex++]);
		}
		
		return sb.toString();
	}
	
	/**
	 * Extracts a valid string from the input and returns it as a string. 
	 * Valid string is enclosed in double quotes and following escape
	 * sequences are allowed: "\\\\" (interpreted as "\\"), "\\\""
	 * (interpreted as "\""), "\\n" (interpreted as "\n"), "\\r" 
	 * (interpreted as "\r") and "\\t" (interpreted as "\t").
	 * 
	 * @return the string that contains valid string extracted from the
	 *         input of this lexer
	 * @throws SmartScriptLexerException if a lexing error occurs
	 */
	private String extractString() {
		StringBuilder sb = new StringBuilder();
		
		while (currentIndex < data.length && data[currentIndex] != '"') {
			if (data[currentIndex] == '\\') {
				if (currentIndex >= data.length - 1) {
					throw new SmartScriptLexerException("Invalid escape sequence in string.");
				}
				
				switch (data[currentIndex + 1]) {
				case '\\':
					sb.append("\\");
					break;
				case '\"':
					sb.append("\"");
					break;
				case 'n':
					sb.append("\n");
					break;
				case 'r':
					sb.append("\r");
					break;
				case 't':
					sb.append("\t");
					break;
				default:
					throw new SmartScriptLexerException("Invalid escape sequence in string.");
				}
				
				currentIndex += 2;
				continue;
			}
			
			sb.append(data[currentIndex++]);
		}
		
		currentIndex++;
		return sb.toString();
	}
	
	/**
	 * Sets the current token to be the next valid integer or real number
	 * from the input of this lexer. Real number are recognized only in
	 * digits-dot-digits format.
	 * 
	 * @throws SmartScriptLexerException if a lexing error occurs
	 */
	private void tokenizeNumber() {
		boolean isInteger = true;
		StringBuilder sb = new StringBuilder();
		
		if (data[currentIndex] == '-') {
			sb.append("-");
			currentIndex++;
		}
		
		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex++]);
		}
		
		if (currentIndex < data.length && data[currentIndex] == '.') {
			isInteger = false;
			sb.append(data[currentIndex++]);
			
			while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex++]);
			}
		}	
		
		if (isInteger) {
			try {
				int number = Integer.parseInt(sb.toString());
				token = new SmartScriptToken(SmartScriptTokenType.INTEGER, number);
			} catch (NumberFormatException ex) {
				throw new SmartScriptLexerException("Invalid number format.");
			}
		} else {
			try {
				double number = Double.parseDouble(sb.toString());
				token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, number);
			} catch (NumberFormatException ex) {
				throw new SmartScriptLexerException("Invalid number format.");
			}
		}
	}
	
}
