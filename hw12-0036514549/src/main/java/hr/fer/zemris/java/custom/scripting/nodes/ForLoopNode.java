package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 * 
 * @author Matija Frandolić
 */
public class ForLoopNode extends Node {

	/**
	 * Variable of the for-loop.
	 */
	private ElementVariable variable;
	
	/**
	 * Start expression of the for-loop.
	 */
	private Element startExpression;
	
	/**
	 * End expression of the for-loop.
	 */
	private Element endExpression;
	
	/**
	 * Step expression of the for-loop.
	 */
	private Element stepExpression;
	
	/**
	 * Constructs a new {@code ForLoopNode} object from the given
	 * for-loop parameters. Fourth parameter can be {@code null}.
	 * 
	 * @param variable        variable of the for-loop
	 * @param startExpression start expression of the for-loop
	 * @param endExpression   end expression of the for-loop
	 * @param stepExpression  optional step expression of the for-loop
	 */
	public ForLoopNode(ElementVariable variable, 
			           Element startExpression, 
			           Element endExpression, 
			           Element stepExpression) {
		
		this.variable = Objects.requireNonNull(variable, "Variable cannot be null.");
		this.startExpression = Objects.requireNonNull(startExpression, "Start expression cannot be null.");
		this.endExpression = Objects.requireNonNull(endExpression, "End expression cannot be null.");;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Returns the variable of the for-loop.
	 * 
	 * @return the variable of the for-loop
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Returns the start expression of the for-loop.
	 * 
	 * @return the start expression of the for-loop
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Returns the end expression of the for-loop.
	 * 
	 * @return the end expression of the for-loop
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Returns the step expression of the for-loop or {@code null} if
	 * step expression is not defined.
	 * 
	 * @return the step expression of the for-loop or 
	 *         {@code null} if not defined
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
	
	/**
	 * Returns the string representation of this node. The format of the string is:
	 * "{$ FOR parameters... $} children... {$ END $}".
	 * 
	 * @return the string representation of this node
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$ FOR ")
		  .append(variable.asText())
		  .append(" ")
		  .append(startExpression.asText())
		  .append(" ")
		  .append(endExpression.asText())
		  .append(" ");
		
		if (stepExpression != null) {
			sb.append(stepExpression.asText())
			  .append(" ");
		}
		
		sb.append("$}");  
		
		for (int i = 0, last = numberOfChildren(); i < last; i++) {
			sb.append(getChild(i).toString());
		}
		
		sb.append("{$ END $}");
		
		return sb.toString();
	}
	
	/**
	 * Indicates whether two {@code ForLoopNode} objects are equal by 
	 * comparing equality of their parameters and their children.
	 *
	 * @param  the object with which to compare equality
	 * @return {@code true} if objects are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (!(obj instanceof ForLoopNode)) {
			return false;
		}
		
		ForLoopNode other = (ForLoopNode) obj;
		
		if (numberOfChildren() != other.numberOfChildren()) {
			return false;
		}
		
		if (!variable.equals(other.variable) ||
			!startExpression.equals(other.startExpression) ||
			!endExpression.equals(other.endExpression)) {
			
			return false;
		}
		
		if (stepExpression != null && 
			other.stepExpression != null && 
			!stepExpression.equals(other.stepExpression)) {
			
			return false;
		} else if (stepExpression != null && other.stepExpression == null ||
				   stepExpression == null && other.stepExpression != null) {
			
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
