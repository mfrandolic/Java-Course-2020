package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Represents a single shell command. Each command is capable of performing some
 * action when executed in {@link Environment} with some arguments.
 * 
 * @author Matija Frandolić
 */
public interface ShellCommand {

	/**
	 * Performs some action in the given {@link Environment} with the given
	 * arguments and returns {@link ShellStatus}.
	 * 
	 * @param env       the environment in which this command is executed
	 * @param arguments arguments of this command
	 * @return          the status of the command after action is performed
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Returns the string that represents this command's name.
	 * 
	 * @return the string that represents this command's name
	 */
	String getCommandName();
	
	/**
	 * Returns the list of strings which represent description of this command.
	 * 
	 * @return the list of strings which represent description of this command
	 */
	List<String> getCommandDescription();
	
}
