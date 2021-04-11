package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output dynamically.
 * 
 * @author Matija Frandolić
 */
public class EchoNode extends Node {

	/**
	 * Parameters of the node.
	 */
	private Element[] elements;
	
	/**
	 * Constructs a new {@code EchoNode} object from the given array
	 * of node parameters.
	 * 
	 * @param elements value of the expression
	 */
	public EchoNode(Element[] elements) {
		this.elements = Objects.requireNonNull(elements, "Elements array cannot be null.");
	}
	
	/**
	 * Returns parameters of this node.
	 * 
	 * @return parameters of this node
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * Returns the string representation of this node. The format of the string is:
	 * "{$= parameters... $}".
	 * 
	 * @return the string representation of this node
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$= ");
		
		for (Element element : elements) {		
			sb.append(element.asText()).append(" ");
		}
		
		sb.append("$}");
		
		return sb.toString();
	}
	
	/**
	 * Indicates whether two {@code EchoNode} objects are equal by 
	 * comparing equality of their parameters.
	 *
	 * @param  the object with which to compare equality
	 * @return {@code true} if objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof EchoNode)) {
			return false;
		}
		
		EchoNode other = (EchoNode) obj;
		
		if (elements.length != other.elements.length) {
			return false;
		}
		
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] == null && other.elements[i] == null) {
				continue;
			} else if (elements[i] != null && other.elements[i] == null ||
				       elements[i] == null && other.elements[i] != null) {
				return false;
			} else if (!elements[i].equals(other.elements[i])) {
				return false;
			} else {
				continue;
			}
		}
		
		return true;
	}
	
}
