package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * This class is an implementation of a parser which creates a document model
 * of a given document. Document consists of text and tags. Tags are bounded 
 * by "{$" and "$}". Allowed tags are: for-tag, echo-tag and end-tag. For-tag
 * can have children text and tags. Echo-tag and end-tag cannot have children
 * text or tags. Each for-tag must have a corresponding end-tag.
 * 
 * @author Matija Frandolić
 */
public class SmartScriptParser {

	/**
	 * Lexer used for tokenization of input document.
	 */
	private SmartScriptLexer lexer;
	
	/**
	 * Root of the built document model.
	 */
	private DocumentNode documentNode;
	
	/**
	 * Construct a new {@code SmartScriptParser} from the given text.
	 * 
	 * @param  text text to parse 
	 * @throws SmartScriptParserException if a parsing error occurs
	 */
	public SmartScriptParser(String text) {
		lexer = new SmartScriptLexer(text);
		try {
			documentNode = parse();			
		} catch (SmartScriptLexerException ex) {
			throw new SmartScriptParserException(ex);
		}
	}
	
	/**
	 * Returns the {@code DocumentNode} object which is the root
	 * of the built document model.
	 * 
	 * @return root of the built document model
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
	/**
	 * Parses the input document, tries to build a correct document model and
	 * returns {@code DocumentNode} object which is the root of the built document 
	 * model if parsing is successful.
	 * 
	 * @return root of the built document model
	 * @throws SmartScriptParserException if a parsing error occurs
	 */
	private DocumentNode parse() {
		ObjectStack stack = new ObjectStack();
		DocumentNode documentNode = new DocumentNode();
		stack.push(documentNode);
		
		while (true) {
			lexer.nextToken();
			
			if (lexer.getToken().getType() == SmartScriptTokenType.EOF) {
				break;
			}
			
			if (lexer.getToken().getType() == SmartScriptTokenType.TEXT) {
				TextNode textNode = new TextNode((String) lexer.getToken().getValue());
				Node lastOnStack = (Node) stack.peek();
				lastOnStack.addChildNode(textNode);
				continue;
			}
			
			if (lexer.getToken().getType() == SmartScriptTokenType.TAG_OPEN) {
				lexer.setState(SmartScriptLexerState.TAG);
				
				lexer.nextToken();
				if (lexer.getToken().getType() != SmartScriptTokenType.TAG_NAME) {
					throw new SmartScriptParserException("Expected tag name.");
				}
					
				if ("for".equals(lexer.getToken().getValue())) {
					ForLoopNode forLoopNode = parseForLoopNode();
					checkClosingTag();
					Node lastOnStack = (Node) stack.peek();
					lastOnStack.addChildNode(forLoopNode);
					stack.push(forLoopNode);
					continue;
				}
				
				if ("=".equals(lexer.getToken().getValue())) {
					EchoNode echoNode = parseEchoNode();
					Node lastOnStack = (Node) stack.peek();
					lastOnStack.addChildNode(echoNode);
					continue;
				}
				
				if ("end".equals(lexer.getToken().getValue())) {
					lexer.nextToken();
					checkClosingTag();
					stack.pop();
					if (stack.size() == 0) {
						throw new SmartScriptParserException("More end tags than opened non-empty tags.");
					}
					continue;
				}
				
				throw new SmartScriptParserException("Unknown tag.");
			}
		}
		
		Node lastOnStack = (Node) stack.pop();
		if (stack.size() > 0) {
			throw new SmartScriptParserException("More opened non-empty tags than end tags.");
		}
		documentNode = (DocumentNode) lastOnStack;
		return documentNode;
	}
	
	/**
	 * Checks whether the current token of the lexer is a closing tag and
	 * sets the lexer to text mode if it is. Otherwise throws an exception.
	 * 
	 * @throws SmartScriptParserException if current token is not a closing tag
	 */
	private void checkClosingTag() {
		if (lexer.getToken().getType() != SmartScriptTokenType.TAG_CLOSE) {
			throw new SmartScriptParserException("Expected closing tag.");
		}
		lexer.setState(SmartScriptLexerState.TEXT);
	}
	
	/**
	 * Tries to parse {@code EchoNode} and returns the resulting node if
	 * successful.
	 * 
	 * @return the resulting {@code EchoNode} if parsing is successful
	 * @throws SmartScriptParserException if a parsing error occurs
	 */
	private EchoNode parseEchoNode() {
		Element[] elements = new Element[1];
		int index = 0;
		
		while (true) {
			lexer.nextToken();
			
			if (lexer.getToken().getType() == SmartScriptTokenType.EOF) {
				throw new SmartScriptParserException("Ecpected element or closing tag.");
			}
			
			if (lexer.getToken().getType() == SmartScriptTokenType.TAG_CLOSE) {
				break;
			}
			
			if (index == elements.length) {
				elements = Arrays.copyOf(elements, elements.length + 1);
			}
			
			elements[index++] = loadEchoNodeParameters();
		}
		
		if (index == 0) {
			throw new SmartScriptParserException("Echo tag cannot be empty.");
		}
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		EchoNode echoNode = new EchoNode(elements);
		return echoNode;
	}
	
	/**
	 * Tries to load one correct {@code Element} which is a parameter
	 * of {@code EchoNode} and returns the resulting element if successful.
	 * 
	 * @return the resulting {@code Element} if loading is successful
	 * @throws SmartScriptParserException if a parsing error occurs
	 */
	private Element loadEchoNodeParameters() {
		switch(lexer.getToken().getType()) {
		case VARIABLE:
			return new ElementVariable((String) lexer.getToken().getValue());
		case STRING:
			return new ElementString((String) lexer.getToken().getValue());
		case OPERATOR:
			return new ElementOperator((String) lexer.getToken().getValue());
		case FUNCTION:
			return new ElementFunction((String) lexer.getToken().getValue());
		case INTEGER:
			return new ElementConstantInteger((Integer) lexer.getToken().getValue());
		case DOUBLE:
			return new ElementConstantDouble((Double) lexer.getToken().getValue());
		default:
			throw new SmartScriptParserException("Unexpected element.");
		}
	}
	
	/**
	 * Tries to parse {@code ForLoopNode} and returns the resulting node if
	 * successful.
	 * 
	 * @return the resulting {@code ForLoopNode} if parsing is successful
	 * @throws SmartScriptParserException if a parsing error occurs
	 */
	private ForLoopNode parseForLoopNode() {
		ElementVariable variable;
		Element[] forLoopParameters = new Element[] {null, null, null};
		
		lexer.nextToken();
		if (lexer.getToken().getType() != SmartScriptTokenType.VARIABLE) {
			throw new SmartScriptParserException("Expected variable name.");
		}
		variable = new ElementVariable((String) lexer.getToken().getValue());
		
		forLoopParameters[0] = loadForLoopParameter(false);
		forLoopParameters[1] = loadForLoopParameter(false);
		forLoopParameters[2] = loadForLoopParameter(true);
		
		if (forLoopParameters[2] != null) {
			lexer.nextToken();
		}
		
		return new ForLoopNode(variable, forLoopParameters[0], forLoopParameters[1], forLoopParameters[2]);
	}
	
	/**
	 * Tries to load one correct {@code Element} which is a parameter
	 * of {@code ForLoopNode} and returns the resulting element if successful.
	 * If parameter optional is {@code true} and correct token type is 
	 * not found, {@code null} is returned.
	 * 
	 * @param  optional indicates whether the parameter is optional
	 * @return the resulting {@code Element} if loading is successful
	 * @throws SmartScriptParserException if a parsing error occurs
	 */
	private Element loadForLoopParameter(boolean optional) {
		lexer.nextToken();
		switch (lexer.getToken().getType()) {
		case VARIABLE:
			return new ElementVariable((String) lexer.getToken().getValue());
		case STRING:
			return new ElementString((String) lexer.getToken().getValue());
		case INTEGER:
			return new ElementConstantInteger((Integer) lexer.getToken().getValue());
		case DOUBLE:
			return new ElementConstantDouble((Double) lexer.getToken().getValue());
		default:
			if (optional) {
				return null;
			} else {
				throw new SmartScriptParserException("Expected variable, string, integer or double.");				
			}
		}
	}
	
}
