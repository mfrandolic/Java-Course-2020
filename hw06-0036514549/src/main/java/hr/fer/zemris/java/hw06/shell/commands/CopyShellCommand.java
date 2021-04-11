package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
 * Represents the "copy" command. This command copies source file into the 
 * destination file or directory.
 * 
 * @author Matija Frandolić
 */
public class CopyShellCommand  implements ShellCommand {

	/**
	 * Copies source file into the destination file or directory and returns 
	 * {@link ShellStatus#CONTINUE}.
	 *
	 * @param arguments path to the source file and path to the destination file
	 *                  or directory
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
		
		if (argumentsSplit.length != 2) {
			env.writeln("Expected source file path and destination file path or"
					  + "destination directory.");
			env.writeln("Usage: copy sourceFilePath destinationFilePath|"
				      + "destinationDirectoryPath");
			return ShellStatus.CONTINUE;
		}
		
		Path source = Paths.get(argumentsSplit[0]);
		Path destination = Paths.get(argumentsSplit[1]);
				
		if (Files.isDirectory(source)) {
			env.writeln("Expected source file but directory was given.");	
		} else if (!Files.exists(source)){
			env.writeln("Source file doesn't exist.");			
		} else {
			if (Files.isDirectory(destination)) {
				destination = destination.resolve(source.getFileName());
			} else if (Files.exists(destination)) {
				env.write("Destination file already exists. Do you want to "
						+ "overwrite it? [y/N] ");
				String input = env.readLine().toLowerCase();
				if (!input.equals("y")) {
					return ShellStatus.CONTINUE;
				}
			}
			try {
				copy(source, destination);			
			} catch (IOException e) {
				env.writeln("I/O error occurred.");
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Copies source file into the destination file or directory.");
		description.add("Usage: copy sourceFilePath destinationFilePath|"
				      + "destinationDirectoryPath");
		return Collections.unmodifiableList(description);
	}
	
	/**
	 * Copies the file from the given source path into a file at the given
	 * destination path.
	 * 
	 * @param  source      the path to the source file
	 * @param  destination the path to the destination file
	 * @throws IOException if I/O error occurs
	 */
	private static void copy(Path source, Path destination) throws IOException {
		InputStream is = new BufferedInputStream(Files.newInputStream(source));
		OutputStream os = new BufferedOutputStream(Files.newOutputStream(destination));
		
		final int BUFFER_SIZE = 4096;
		byte[] buffer = new byte[BUFFER_SIZE];
		int numberOfRead;
		while ((numberOfRead = is.read(buffer)) >= 1) {
			os.write(buffer, 0, numberOfRead);
		}

		is.close();
		os.flush();
		os.close();
	}

}
