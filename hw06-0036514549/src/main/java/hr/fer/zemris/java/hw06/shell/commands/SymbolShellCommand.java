package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Util;

/**
 * Represents the "symbol" command. This command displays current symbol for the
 * given option or sets it to a new symbol.
 * 
 * @author Matija Frandolić
 */
public class SymbolShellCommand  implements ShellCommand {

	/**
     * Displays current symbol for the given option or sets it to a new symbol 
     * and returns {@link ShellStatus#CONTINUE}.
	 *
	 * @param arguments option for the type of symbol and optional new symbol
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentsSplit;
		try {
			argumentsSplit = Util.splitArguments(arguments);
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		if (argumentsSplit.length != 1 && argumentsSplit.length != 2) {
			env.writeln("Invalid number of arguments.");
			env.writeln("Usage: symbol PROMPT|MORELINES|MULTILINE [newSymbol]");
			return ShellStatus.CONTINUE;
		}
		
		if (argumentsSplit.length == 2 && argumentsSplit[1].length() != 1) {
			env.writeln("Symbol must be a single character.");
			return ShellStatus.CONTINUE;
		}
		
		Character currentSymbol;
		String option = argumentsSplit[0];
		switch (option) {
		case "PROMPT":
			currentSymbol = env.getPromptSymbol();
			break;
		case "MORELINES":
			currentSymbol = env.getMorelinesSymbol();
			break;
		case "MULTILINE":
			currentSymbol = env.getMultilineSymbol();
			break;
		default:
			env.writeln("Expected PROMPT, MORELINES or MULTILINE option.");
			return ShellStatus.CONTINUE;
		}
		
		if (argumentsSplit.length == 1) {
			env.writeln(String.format("Symbol for %s is '%c'", option, currentSymbol));
		} else {
			Character newSymbol = argumentsSplit[1].charAt(0);
			switch (option) {
			case "PROMPT":
				env.setPromptSymbol(newSymbol);
				break;
			case "MORELINES":
				env.setMorelinesSymbol(newSymbol);
				break;
			case "MULTILINE":
				env.setMultilineSymbol(newSymbol);
				break;
			}
			env.writeln(String.format("Symbol for %s changed from '%c' to '%c'", 
					                   option, currentSymbol, newSymbol));
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Displays current symbol for the given option or sets "
				      + "it to a new symbol.");
		description.add("Usage: symbol PROMPT|MORELINES|MULTILINE [newSymbol]");
		return Collections.unmodifiableList(description);
	}

}
