package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Represents the environment in which the shell is operating. The environment
 * provides methods for reading input from the console in which the shell is
 * currently running, writing output to that same console, getting the map of 
 * all supported commands and getting and setting the special PROMPTSYMBOL, 
 * MORELINESSYMBOL and MULTILINESYMBOL characters.
 * 
 * @author Matija Frandolić
 */
public interface Environment {

	/**
	 * Reads a single line from the console in which the shell is operating and 
	 * returns it as a string.
	 * 
	 * @return                  the string that represents the line that was read
	 * @throws ShellIOException if reading error occurs
	 */
	String readLine() throws ShellIOException;
	  
	/**
	 * Writes the given string to the console in which the shell is operating 
	 * and doesn't add the newline character.
	 * 
	 * @param  text             the string to be written
	 * @throws ShellIOException if writing error occurs
	 */
	void write(String text) throws ShellIOException;
	  
	/**
	 * Writes the given string to the console in which the shell is operating 
	 * and adds the newline character.
	 * 
	 * @param  text             the string to be written
	 * @throws ShellIOException if writing error occurs
	 */
	void writeln(String text) throws ShellIOException;
	  
	/**
	 * Returns the read-only sorted map that contains command names as keys
	 * and corresponding {@link ShellCommand} objects as values.
	 * 
	 * @return the read-only sorted map of supported commands
	 */
	SortedMap<String, ShellCommand> commands();
	  
	/**
	 * Returns the MULTILINESYMBOL used to indicate the beginning of the line
	 * when there are multiple lines of input.
	 * 
	 * @return the MULTILINESYMBOL character of this environment
	 */
	Character getMultilineSymbol();
	  
	/**
	 * Sets the MULTILINESYMBOL used to indicate the beginning of the line
	 * when there are multiple lines of input to the given character.
	 * 
	 * @param symbol the character to be used as a new MULTILINESYMBOL
	 */
	void setMultilineSymbol(Character symbol);
	  
	/**
	 * Returns the PROMPTSYMBOL used to indicate the beginning of the first
	 * line of input.
	 * 
	 * @return the PROMPTSYMBOL character of this environment
	 */
	Character getPromptSymbol();
	  
	/**
	 * Sets the PROMPTSYMBOL used to indicate the beginning of the first
	 * line of input.
	 * 
	 * @param symbol the character to be used as a new PROMPTSYMBOL
	 */
	void setPromptSymbol(Character symbol);
	  
	/**
	 * Returns the MORELINESSYMBOL used to indicate the end of a line after which
	 * more lines of input should follow.
	 * 
	 * @return the MORELINESSYMBOL character of this environment
	 */
	Character getMorelinesSymbol();
	  
	/**
	 * Sets the MORELINESSYMBOL used to indicate the end of a line after which
	 * more lines of input should follow.
	 * 
	 * @param symbol the character to be used as a new MORELINESSYMBOL
	 */
	void setMorelinesSymbol(Character symbol);
	
}
