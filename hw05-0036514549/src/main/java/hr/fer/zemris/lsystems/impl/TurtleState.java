package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Represents the state of the "turtle" used for drawing. State includes
 * current position, direction, color used for drawing and effective length 
 * of the next move.
 * 
 * @author Matija Frandolić
 */
public class TurtleState {

	/**
	 * Current position of the turtle.
	 */
	private Vector2D position;
	
	/**
	 * Current direction of the turtle.
	 */
	private Vector2D direction;
	
	/**
	 * Color used for drawing.
	 */
	private Color color;
	
	/**
	 * Effective length of the next move.
	 */
	private double effectiveLength;
		
	/**
	 * Constructs a new {@code TurtleState} from the given position, direction,
	 * color and effective length.
	 * 
	 * @param position        current position of the turtle
	 * @param direction       current direction of the turtle
	 * @param color           color used for drawing
	 * @param effectiveLength effective length of the next move
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveLength) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.effectiveLength = effectiveLength;
	}

	/**
	 * Returns a new {@code TurtleState} that is a copy of this state.
	 * 
	 * @return new {@code TurtleState} that is a copy of this state
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), color, effectiveLength);
	}

	/**
	 * Returns the position in this state.
	 * 
	 * @return the position in this state
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Sets the current position in this state to the given position.
	 * 
	 * @param position position to be used as current position in this state
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Returns the direction in this state.
	 * 
	 * @return the direction in this state
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Sets the current direction in this state to the given direction.
	 * 
	 * @param direction direction to be used as current direction in this state
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Returns the color used for drawing in this state.
	 * 
	 * @return the color used for drawing in this state
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color used for drawing in this state to the given color.
	 * 
	 * @param color color to be used as color for drawing in this state
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns the effective length of the next move in this state.
	 * 
	 * @return the effective length of the next move in this state
	 */
	public double getEffectiveLength() {
		return effectiveLength;
	}

	/**
	 * Sets the effective length of the next move in this state to the given
	 * length.
	 * 
	 * @param effectiveLength length to be used as effective length of the next
	 *                        move in this state
	 */
	public void setEffectiveLength(double effectiveLength) {
		this.effectiveLength = effectiveLength;
	}
	
}
