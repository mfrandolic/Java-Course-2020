package hr.fer.zemris.java.hw06.shell;

/**
 * Implementation of a shell. The list of supported commands can be seen by 
 * calling the "help" command (without quotes). Description and usage of each
 * command can be seen by calling "help commandName" form of the "help" command.
 * The command can span across multiple lines if each line (except the last one)
 * ends with the special MORELINESSYMBOL character ('\' by default). The shell
 * terminates when the "exit" command is called.
 * 
 * @author Matija Frandolić
 */
public class MyShell {

	/**
	 * Main method of the program.
	 * 
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {		
		Environment env = new EnvironmentImpl();
		env.writeln("Welcome to MyShell v 1.0");

		while (true) {
			String input = readLineOrLines(env);
			String commandName = extractCommandName(input);
			if (commandName.equals("")) {
				continue;
			}
			String arguments = extractArguments(input);
		
			ShellCommand command = env.commands().get(commandName);
			if (command == null) {
				env.writeln("Command \"" + commandName + "\" doesn't exist.");
				continue;
			}
			
			ShellStatus status = command.executeCommand(env, arguments);
			if (status == ShellStatus.TERMINATE) {
				break;
			}
		}
	}
	
	/**
	 * Prompts the user with the PROMPTSYMBOL and reads the next line of input. 
	 * If the line ends with the MORELINESSYMBOL, the MULTILINESYMBOL is printed 
	 * to the next line and new line of input is read. This process continues 
	 * until there are no more lines that end with the MORELINESSYMBOL. All
	 * lines are concatenated to a single string which is then returned. The
	 * resulting string doesn't contain PROMPTSYMBOLs, MORELINESSYMBOLs or
	 * MULTILINESYMBOLs.
	 * 
	 * @param env the {@link Environment} from which the input is read
	 * @return    the concatenated input lines
	 */
	private static String readLineOrLines(Environment env) {
		env.write(env.getPromptSymbol() + " ");
		String input = env.readLine().trim();
		while (input.endsWith(env.getMorelinesSymbol().toString())) {
			input = input.substring(0, input.lastIndexOf(env.getMorelinesSymbol()));
			env.write(env.getMultilineSymbol() + " ");
			input += env.readLine().trim();
		}
		return input;
	}
	
	/**
	 * Extracts and returns the command name from the given input string.
	 * Command name is the sequence of characters before the first ' ' character.
	 * 
	 * @param input the input string from which the command name is extracted
	 * @return      the command name which is extracted from the input string
	 */
	private static String extractCommandName(String input) {
		int index = input.indexOf(' ');
		return index == -1 ? input : input.substring(0, index);
	}
	
	/**
	 * Extracts and returns the string that represents arguments of the input
	 * string, i.e. everything that follows after the first ' ' character.
	 * 
	 * @param input the input string from which the arguments string is extracted
	 * @return      the string that represents arguments of the input string
	 */
	private static String extractArguments(String input) {
		int index = input.indexOf(' ');
		return index == -1 ? "" : input.substring(index + 1).trim();
	}
	
}
