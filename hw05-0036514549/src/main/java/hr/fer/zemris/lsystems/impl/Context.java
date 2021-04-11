package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Represents context of the drawing. It allows storage of {@code TurtleState}
 * objects on the stack.
 * 
 * @author Matija Frandolić
 */
public class Context {

	/**
	 * Stack used for the implementation.
	 */
	private ObjectStack<TurtleState> stack;
	
	/**
	 * Constructs a new {@code Context} with an empty stack.
	 */
	public Context() {
		stack = new ObjectStack<TurtleState>();
	}
	
	/**
	 * Returns the current state that is kept in this context. Current state is 
	 * the last one pushed to the stack.
	 * 
	 * @return the current state that is kept in this context
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Pushes the given {@code TurtleState} to the stack where it will be kept
	 * in this context.
	 * 
	 * @param state {@code TurtleState} to be kept by this context
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Pops the last {@code TurtleState} that was pushed to the stack and was
	 * kept in this context.
	 */
	public void popState() {
		stack.pop();
	}
	
}
