package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Model of a visitor that can visit {@link TextNode}, {@link ForLoopNode},
 * {@link EchoNode} and {@link DocumentNode}.
 * 
 * @author Matija Frandolić
 */
public interface INodeVisitor {
	
	/**
	 * Action to be performed when the given {@link TextNode} is visited.
	 * 
	 * @param node {code TextNode} to visit
	 */
	void visitTextNode(TextNode node);
	
	/**
	 * Action to be performed when the given {@link ForLoopNode} is visited.
	 * 
	 * @param node {code ForLoopNode} to visit
	 */
	void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Action to be performed when the given {@link EchoNode} is visited.
	 * 
	 * @param node {code EchoNode} to visit
	 */
	void visitEchoNode(EchoNode node);
	
	/**
	 * Action to be performed when the given {@link DocumentNode} is visited.
	 * 
	 * @param node {code DocumentNode} to visit
	 */
	void visitDocumentNode(DocumentNode node);
	
}
