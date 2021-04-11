package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents the "exit" command. This command terminates the shell.
 * 
 * @author Matija Frandolić
 */
public class ExitShellCommand  implements ShellCommand {

	/**
	 * Terminates the shell by returning {@link ShellStatus#TERMINATE}.
	 *
	 * @param arguments an empty string
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() != 0) {
			env.writeln("Expected 0 arguments.");
			env.writeln("Usage: exit");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Terminates the shell.");
		description.add("Usage: exit");
		return Collections.unmodifiableList(description);
	}

}
