package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Represents a general graph node.
 * 
 * @author Matija Frandolić
 */
public class Node {
	
	/**
	 * Collection of this node's children.
	 */
	private ArrayIndexedCollection arrayCollection;

	/**
	 * Adds the given node as a child of this node.
	 * 
	 * @param child the node to be added as a child of this node
	 */
	public void addChildNode(Node child) {
		if (arrayCollection == null) {
			arrayCollection = new ArrayIndexedCollection();
		}
		arrayCollection.add(child);
	}
	
	/**
	 * Returns the number of direct children of this node.
	 * 
	 * @return the number of direct children of this node
	 */
	public int numberOfChildren() {
		if (arrayCollection != null) {
			return arrayCollection.size();			
		}
		return 0;
	}
	
	/**
	 * Returns the child of this node at the given index.
	 * 
	 * @param index index of the wanted child
	 * @return      the child of this node at the given index
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	public Node getChild(int index) {
		if (index < 0 || index > arrayCollection.size() - 1) {
			throw new IndexOutOfBoundsException(index);
		}
		
		return (Node) arrayCollection.get(index);
	}
	
}
