package hr.fer.zemris.java.hw06.shell;

/**
 * Represents all possible states that the {@link ShellCommand} can return to
 * the shell after the execution of the command is finished.
 * 
 * @author Matija Frandolić
 */
public enum ShellStatus {

	/**
	 * Represents that the shell should continue operating.
	 */
	CONTINUE,
	
	/**
	 * Represents that the shell should terminate.
	 */
	TERMINATE
	
}
