package hr.fer.zemris.java.hw06.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * Implementation of the {@link Environment} for the {@link MyShell}.
 * Lines are read from {@code System.in} and written to {@code System.out}.
 * 
 * @author Matija Frandolić
 */
public class EnvironmentImpl implements Environment {

	/**
	 * Reader used for reading the input in this environment.
	 */
	private BufferedReader reader;
	
	/**
	 * Sorted map of all supported commands in this environment.
	 */
	private SortedMap<String, ShellCommand> commands;
	
	/**
	 * MULTILINESYMBOL of this environment.
	 */
	private Character multilineSymbol;
	
	/**
	 * PROMPTSYMBOL of this environment.
	 */
	private Character promptSymbol;
	
	/**
	 * MORELINESSYMBOL of this environment.
	 */
	private Character morelinesSymbol;
	
	/**
	 * Constructs a new {@code EnvironmentImpl}.
	 */
	public EnvironmentImpl() {
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		commands = new TreeMap<String, ShellCommand>();
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
		
		multilineSymbol = '|';
		promptSymbol = '>';
		morelinesSymbol = '\\';
	}
	
	/**
	 * Reads a single line from {@code System.in} and returns it as a string.
	 */
	@Override
	public String readLine() throws ShellIOException {
		try {
			return reader.readLine();			
		} catch (IOException e) {
			throw new ShellIOException();
		}
	}

	/**
	 * Writes the given string to {@code System.out} and doesn't add the 
	 * newline character.
	 */
	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	/**
	 * Writes the given string to {@code System.out} and adds the newline 
	 * character.
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

}
