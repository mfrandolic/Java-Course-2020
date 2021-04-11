package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Util;

/**
 * Represents the "mkdir" command. This command creates directory in the given 
 * path. Parent directories are also created if they don't yet exist.
 * 
 * @author Matija Frandolić
 */
public class MkdirShellCommand  implements ShellCommand {

	/**
	 * Creates directory in the given path. Parent directories are also created 
	 * if they don't yet exist. Returns {@link ShellStatus#CONTINUE}.
	 *
	 * @param arguments path to the directory
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
		
		if (argumentsSplit.length != 1) {
			env.writeln("Expected directory path.");
			env.writeln("Usage: mkdir directoryPath");
			return ShellStatus.CONTINUE;
		}
		
		Path directory = Paths.get(argumentsSplit[0]);
		
		if (Files.exists(directory)) {			
			env.writeln("File or directory with the same name already exists.");
		} else {
			try {
				Files.createDirectories(directory);
			} catch (IOException e) {
				env.writeln("I/O error occurred.");
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Creates directory in the given path. Parent directories "
				      + "are also created if they don't yet exist.");
		description.add("Usage: mkdir directoryPath");
		return Collections.unmodifiableList(description);
	}

}
