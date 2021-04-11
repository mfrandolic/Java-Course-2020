package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Represents the "charsets" command. This command lists names of all supported 
 * charsets for the used Java platform.
 * 
 * @author Matija Frandolić
 */
public class CharsetsShellCommand  implements ShellCommand {

	/**
	 * Lists names of all supported charsets for the used Java platform and
	 * returns {@link ShellStatus#CONTINUE}.
	 *
	 * @param arguments an empty string
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() != 0) {
			env.writeln("Expected 0 arguments.");
			env.writeln("Usage: charsets");
			return ShellStatus.CONTINUE;
		}
		
		Charset.availableCharsets().keySet().forEach(env::writeln);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Lists names of all supported charsets for the used Java platform.");
		description.add("Usage: charsets");
		return Collections.unmodifiableList(description);
	}

}
