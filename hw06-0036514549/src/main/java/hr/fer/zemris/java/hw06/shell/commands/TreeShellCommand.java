package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
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
 * Represents the "tree" command. This command prints a tree from the contents 
 * of the given root directory.
 * 
 * @author Matija Frandolić
 */
public class TreeShellCommand  implements ShellCommand {

	/**
     * Prints a tree from the contents of the given root directory and returns 
     * {@link ShellStatus#CONTINUE}.
	 *
	 * @param arguments path to the root directory
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
			env.writeln("Expected root directory path.");
			env.writeln("Usage: tree rootDirectoryPath");
			return ShellStatus.CONTINUE;
		}
		
		Path rootDirectory = Paths.get(argumentsSplit[0]);
		
		if (Files.isDirectory(rootDirectory)) {
			printTree(rootDirectory, 0, env);
		} else if (Files.exists(rootDirectory)) {
			env.writeln("Expected directory but file was given.");
		} else {
			env.writeln("Directory doesn't exist.");		
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Prints a tree from the contents of the given root directory.");
		description.add("Usage: tree rootDirectoryPath");
		return Collections.unmodifiableList(description);
	}
	
	/**
	 * Prints a tree from the contents of the given directory by printing all files
	 * from the directory and recursively visiting all directories in the current
	 * directory.
	 * 
	 * @param directory the root directory for which to print a tree
	 * @param level     current depth of the tree (0 for the root directory)
	 * @param env       the environment in which this command is executed
	 */
	private static void printTree(Path directory, int level, Environment env) {
		final int INDENT = 2;
		
		if (level == 0) {
			env.writeln(directory.toAbsolutePath().normalize().toString());
		} else {
			env.writeln(" ".repeat(INDENT * level) + directory.getFileName());
		}
		
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(directory)) {
			for (Path path : ds) {
				if (Files.isDirectory(path)) {
					printTree(path, level + 1, env);
				} else {
					env.writeln(" ".repeat(INDENT * (level + 1)) + path.getFileName());
				}
			}
		} catch (IOException e) {
			env.writeln("I/O error occurred.");
		}
	}

}
