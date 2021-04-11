package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing an entire document.
 * 
 * @author Matija Frandolić
 */
public class DocumentNode extends Node {

	/**
	 * Returns the string representation of this node.
	 *
	 * @return the string representation of this node
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0, last = numberOfChildren(); i < last; i++) {
			sb.append(getChild(i).toString());
		}
		
		return sb.toString();
	}
	
	/**
	 * Indicates whether two {@code DocumentNode} objects
	 * are equal by comparing equality of their children.
	 *
	 * @param  the object with which to compare equality
	 * @return {@code true} if objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof DocumentNode)) {
			return false;
		}
		
		DocumentNode other = (DocumentNode) obj;
		
		if (numberOfChildren() != other.numberOfChildren()) {
			return false;
		}
		
		for (int i = 0, last = numberOfChildren(); i < last; i++) {
			if (!getChild(i).equals(other.getChild(i))) {
				return false;
			}
		}
		
		return true;
	}
	
}
