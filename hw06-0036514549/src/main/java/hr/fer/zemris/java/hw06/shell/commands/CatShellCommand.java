package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
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
 * Represents the "cat" command. This command outputs contents of the file using 
 * the default or specified charset.
 * 
 * @author Matija Frandolić
 */
public class CatShellCommand  implements ShellCommand {

	/**
	 * Outputs contents of the file using the default or specified charset and
	 * returns {@link ShellStatus#CONTINUE}.
	 *
	 * @param arguments path to the file and optional charset
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
			env.writeln("Expected file path with optional charset.");
			env.writeln("Usage: cat filePath [charset]");
			return ShellStatus.CONTINUE;
		}

		Path file = Paths.get(argumentsSplit[0]);
		Charset charset = Charset.defaultCharset();
		
		if (argumentsSplit.length == 2) {
			try {
				charset = Charset.forName(argumentsSplit[1]);				
			} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
				env.writeln("Charset name is invalid or given charset is unsupported.");
				return ShellStatus.CONTINUE;
			}
		}
		
		if (Files.isDirectory(file)) {
			env.writeln("Expected file but directory was given.");	
		} else if (!Files.exists(file)){
			env.writeln("File doesn't exist.");			
		} else {
			try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
				String line;
				while ((line = reader.readLine()) != null) {
					env.writeln(line);
				}
			} catch (IOException e) {
				env.writeln("I/O error occurred.");
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Outputs contents of the file. Default platform charset "
				      + "is used unless charset is explicitly given.");
		description.add("Usage: cat filePath [charset]");
		return Collections.unmodifiableList(description);
	}

}
