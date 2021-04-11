package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * A node representing a piece of textual data.
 * 
 * @author Matija Frandolić
 */
public class TextNode extends Node {

	/**
	 * Textual data kept by this node.
	 */
	private String text;
	
	/**
	 * Constructs a new {@code TextNode} object from the given
	 * textual data.
	 * 
	 * @param text textual data to be stored in this node
	 */
	public TextNode(String text) {
		this.text = Objects.requireNonNull(text, "Text cannot be null.");
	}
	
	/**
	 * Returns the textual data kept by this node.
	 * 
	 * @return the textual data kept by this node
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Returns the string representation of this node. The following 
	 * character sequences are replaced: "\\" by "\\\\", "{$" by "\\{$".
	 * 
	 * @return the string representation of this node
	 */
	@Override
	public String toString() {
		return text.replace("\\", "\\\\").replace("{$", "\\{$");
	}
	
	/**
	 * Indicates whether two {@code TextNode} objects are equal by 
	 * comparing equality of their textual data.
	 *
	 * @param  the object with which to compare equality
	 * @return {@code true} if objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof TextNode)) {
			return false;
		}
		
		TextNode other = (TextNode) obj;
		
		return text.equals(other.text);
	}
}
