package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program that shows example usage of {@code SmartScriptParser}.
 * The program accepts a single command-line argument that represents
 * the path to file which is then parsed. 
 * 
 * @author Matija Frandolić
 */
public class SmartScriptTester {

	/**
	 * The main method of the program.
	 * 
	 * @param  args the path to file to be parsed (entered from the command-line) 
	 * @throws IOException if reading error occurs
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Expected a single command-line argument.");
			System.exit(-1);
		}
		
		String filepath = args[0];
		String docBody = new String(
                Files.readAllBytes(Paths.get(filepath)), 
                StandardCharsets.UTF_8
		);
		
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = document.toString();
		System.out.println(originalDocumentBody);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		boolean same = document.equals(document2);
		System.out.println(same);
	}
	
}
