package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Util;

/**
 * Represents the "ls" command. This command lists directory contents and 
 * displays the following information: permissions, size (in bytes), creation 
 * date/time, file name.
 * 
 * @author Matija Frandolić
 */
public class LsShellCommand  implements ShellCommand {

	/**
	 * Lists directory contents and displays the following information: 
	 * permissions, size (in bytes), creation date/time, file name. Returns 
	 * {@link ShellStatus#CONTINUE}.
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
			env.writeln("Usage: ls directoryPath");
			return ShellStatus.CONTINUE;
		}
		
		Path directory = Paths.get(argumentsSplit[0]);
		
		if (Files.isDirectory(directory)) {
			listContents(directory, env);
		} else if (Files.exists(directory)) {
			env.writeln("Expected directory but file was given.");
		} else {
			env.writeln("Directory doesn't exist.");		
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Lists directory contents and displays the following information:");
		description.add("permissions, size (in bytes), creation date/time, file name.");
		description.add("Usage: ls directoryPath");
		return Collections.unmodifiableList(description);
	}
	
	/**
	 * Lists directory contents and displays the following information: 
	 * permissions, size (in bytes), creation date/time, file name.
	 * 
	 * @param directory the path to the directory
	 * @param env       the environment in which this command is executed
	 */
	private static void listContents(Path directory, Environment env) {
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(directory)) {
			for (Path path : ds) {
				String permissions = "";
				permissions += Files.isDirectory(path) ? "d" : "-";
				permissions += Files.isReadable(path) ? "r" : "-";
				permissions += Files.isWritable(path) ? "w" : "-";
				permissions += Files.isExecutable(path) ? "x" : "-";
				
				String size = String.format("%10d", Files.size(path));
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(
					path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
				);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				
				String name = path.getFileName().toString();
				
				env.writeln(String.join(" ", permissions, size, formattedDateTime, name));
			}
		} catch (IOException e) {
			env.writeln("I/O error occurred.");
		}
	}

}
