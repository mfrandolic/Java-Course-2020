package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Util;

/**
 * Represents the "hexdump" command. This command displays file contents in 
 * hexadecimal format, byte by byte. Output in ascii is also displayed, with bytes 
 * whose value is less than 32 or greater than 127 replaced by '.' character.
 * 
 * @author Matija Frandolić
 */
public class HexdumpShellCommand  implements ShellCommand {

	/**
	 * Displays file contents in hexadecimal format, byte by byte. Output in ascii 
	 * is also displayed, with bytes whose value is less than 32 or greater than 
	 * 127 replaced by '.' character. Returns {@link ShellStatus#CONTINUE}.
	 *
	 * @param arguments path to the file
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
			env.writeln("Expected file path.");
			env.writeln("Usage: hexdump filePath");
			return ShellStatus.CONTINUE;
		}
		
		Path file = Paths.get(argumentsSplit[0]);
		
		if (Files.isDirectory(file)) {
			env.writeln("Expected file but directory was given.");	
		} else if (!Files.exists(file)){
			env.writeln("File doesn't exist.");			
		} else {
			dumpFileContents(file, env);
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Displays file contents in hexadecimal format, byte by byte. "
				      + "Output in ascii is also displayed, with bytes whose value is "
				      + "less than 32 or greater than 127 replaced by '.' character.");
		description.add("Usage: hexdump filePath");
		return Collections.unmodifiableList(description);
	}
	
	/**
	 * Dumps the file contents in hexadecimal format, byte by byte, and displays
	 * output in ascii with bytes whose value is less than 32 or greater than 
	 * 127 replaced by '.' character.
	 * 
	 * @param file the path to the file
	 * @param env  the environment in which this command is executed
	 */
	private static void dumpFileContents(Path file, Environment env) {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(file))) {
			int rowNumber = 0;
			final int BUFFER_SIZE = 16;
			byte[] buffer = new byte[BUFFER_SIZE];
			int numberOfRead;
			
			while ((numberOfRead = is.read(buffer)) >= 1) {
				env.write(String.format("%08X: ", rowNumber));
				env.write(hexOutput(buffer, 0, BUFFER_SIZE / 2, numberOfRead));
				env.write("|");
				env.write(hexOutput(buffer, BUFFER_SIZE / 2, BUFFER_SIZE, 
						            numberOfRead - BUFFER_SIZE / 2));
				env.write(" | ");
				
				for (int i = 0; i < BUFFER_SIZE; i++) {
					if (buffer[i] < 32 || buffer[i] > 127) {
						buffer[i] = '.';
					}
				}
				
				env.writeln(new String(buffer, StandardCharsets.US_ASCII)
						    .substring(0, numberOfRead));
				rowNumber += BUFFER_SIZE;
			}
		} catch (IOException e) {
			env.writeln("I/O error occurred.");
		}
	}
	
	/**
	 * Returns the string that is the hexadecimal representation of bytes in the 
	 * {@code bytes} array, from {@code fromIndex} to {@code toIndex}, with 
	 * maximum of {@code maxBytes} (after which only spaces are written). 
	 * 
	 * @param bytes     the array of bytes that needs to be converted to a hex-string
	 * @param fromIndex the starting index from which to start reading bytes in the array
	 * @param toIndex   the ending index until which to read bytes in the array (not included)
	 * @param maxBytes  the maximum number of bytes to read from the array
	 * @return          the string that is the hexadecimal representation of bytes in the array
	 */
	private static String hexOutput(byte[] bytes, int fromIndex, int toIndex, int maxBytes) {
		StringJoiner joiner = new StringJoiner(" ");
		
		for (int i = fromIndex; i < toIndex; i++) {
			if (i < fromIndex + maxBytes) {
				joiner.add(String.format("%02X", bytes[i]));				
			} else {
				joiner.add("  ");
			}
		}
		
		return joiner.toString();
	}

}
