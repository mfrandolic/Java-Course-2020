package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Util;

/**
 * Represents the "help" command. This command lists names of all supported 
 * commands or displays description of the given command.
 * 
 * @author Matija Frandolić
 */
public class HelpShellCommand  implements ShellCommand {

	/**
	 * Lists names of all supported commands or displays description of the given 
	 * command and returns {@link ShellStatus#CONTINUE}.
	 *
	 * @param arguments an empty string or name of the command for which to display help
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
		
		if (argumentsSplit.length != 0 && argumentsSplit.length != 1) {
			env.writeln("Expected 0 arguments or command name.");
			env.writeln("Usage: help [commandName]");
			return ShellStatus.CONTINUE;
		}
		
		if (argumentsSplit.length == 0) {
			env.writeln("List of supported commands:");
			env.commands().keySet().forEach(env::writeln);
		} else {
			String commandName = argumentsSplit[0];
			
			if (env.commands().containsKey(commandName)) {
				env.writeln("Description of \"" + commandName + "\" command:");
				env.commands().get(commandName).getCommandDescription().forEach(env::writeln);
			} else {
				env.writeln("Command \"" + commandName + "\" doesn't exist.");
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Lists names of all supported commands or displays description "
				      + "of the given command.");
		description.add("Usage: help [commandName]");
		return Collections.unmodifiableList(description);
	}

}
