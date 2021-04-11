package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program that shows example usage of {@link SmartScriptParser}. The program 
 * accepts a single command-line argument that represents the path to a file 
 * which is then parsed and written to the standard output in its reproduced
 * (approximate) original form.
 * 
 * @author Matija Frandolić
 */
public class TreeWriter {
	
	/**
	 * Main method of the program.
	 * 
	 * @param  args        command-line arguments (path to a file) 
	 * @throws IOException if I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Expected path to a file.");
			System.exit(1);
		}
		
		String path = args[0];
		String docBody = new String(
                Files.readAllBytes(Paths.get(path)), 
                StandardCharsets.UTF_8
		);
		
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document.");
			System.exit(1);
		}
		
		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
	}
	
	/**
	 * Implementation of {@link INodeVisitor} that writes content of each node
	 * to {@code System.out}.
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText().replace("\\", "\\\\").replace("{$", "\\{$"));
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print("{$ FOR ");
			System.out.print(node.getVariable().asText() + " ");
			System.out.print(node.getStartExpression().asText() + " ");
			System.out.print(node.getEndExpression().asText() + " ");
			
			if (node.getStepExpression() != null) {
				System.out.print(node.getStepExpression().asText() + " ");
			}
			
			System.out.print("$}"); 
			
			for (int i = 0, last = node.numberOfChildren(); i < last; i++) {
				node.getChild(i).accept(this);
			}
			
			System.out.print("{$ END $}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print("{$= ");
			
			for (Element element : node.getElements()) {		
				System.out.print(element.asText() + " ");
			}
			
			System.out.print("$}");
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, last = node.numberOfChildren(); i < last; i++) {
				node.getChild(i).accept(this);
			}
		}
		
	}

}
