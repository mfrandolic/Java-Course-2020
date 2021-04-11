package hr.fer.zemris.java.hw06.shell;

import java.util.Arrays;

/**
 * Utility class that contains method for splitting the command arguments.
 * 
 * @author Matija Frandolić
 */
public class Util {

	/**
	 * Splits the input string that represents command arguments and returns
	 * the resulting array. If the input string is empty (""), the array with
	 * size 0 is returned. Otherwise, arguments are split by whitespace unless
	 * written inside double quotes. Following escape sequences are allowed only
	 * inside quoted arguments: \\ (interpreted as \) and \" (interpreted as ").
	 * 
	 * @param  arguments the string that contains arguments that need to be split
	 * @return           the string array of arguments
	 * @throws IllegalArgumentException if ending double quote is missing or
	 *                                  non-whitespace character follows immediately
	 *                                  after the ending double quote
	 */
	public static String[] splitArguments(String arguments) {
		if (arguments.length() == 0) {
			return new String[0];
		}
		
		String[] argumentsSplit = new String[1];
		int argumentsSplitIndex = 0;
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0, length = arguments.length(); i < length; i++) {
			sb.setLength(0);
			
			if (argumentsSplitIndex == argumentsSplit.length) {
				argumentsSplit = Arrays.copyOf(argumentsSplit, argumentsSplit.length + 1);
			}
			
			while (i < length && Character.isWhitespace(arguments.charAt(i))) {
				i++;
			}
			
			if (i < length && arguments.charAt(i) == '"') {
				i++;
				while (true) {
					if (i >= length) {
						throw new IllegalArgumentException("Missing the ending double quote.");
					}
					if (arguments.charAt(i) == '"') {
						break;
					}
					if (i < length - 1 && arguments.charAt(i) == '\\') {
						switch (arguments.charAt(i + 1)) {
						case '\\':
							sb.append('\\');
							break;
						case '"':
							sb.append('"');
							break;
						default:
							sb.append("\\" + arguments.charAt(i + 1));
							break;
						}
						i += 2;
					} else {
						sb.append(arguments.charAt(i++));
					}
				}
				if (i < length - 1 && !Character.isWhitespace(arguments.charAt(i + 1))) {
					throw new IllegalArgumentException("Non-whitespace character must not "
							+ "follow immediately after the ending double quote.");
				}
				argumentsSplit[argumentsSplitIndex++] = sb.toString();
				continue;
			}
			
			while (i < length && !Character.isWhitespace(arguments.charAt(i)) && 
				   arguments.charAt(i) != '"') {
				sb.append(arguments.charAt(i++));
			}
			i--;
			argumentsSplit[argumentsSplitIndex++] = sb.toString();
		}
		
		return argumentsSplit;
	}
	
}
