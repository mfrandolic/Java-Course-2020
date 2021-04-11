package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * A model of the "draw" command that is used to draw a line from the position 
 * of the current {@code TurtleState} to the newly calculated position. Newly 
 * calculated position is also set as the new position of the current {@code TurtleState}
 * after drawing a line.
 * 
 * @author Matija Frandolić
 */
public class DrawCommand implements Command {

	/**
	 * Scaling factor of the unit length in this draw command.
	 */
	private double step;
	
	/**
	 * Default line width.
	 */
	private static final float DEFAULT_LINE_WIDTH = 1f;
	
	/**
	 * Constructs a new {@code DrawCommand} from the given scaling factor
	 * of the unit length.
	 * 
	 * @param step scaling factor of the unit length in this draw command
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Draws a line from the position of the current {@code TurtleState} to the 
	 * newly calculated position. Newly calculated position is also set as the 
	 * new position of the current {@code TurtleState} after drawing a line.
	 *
	 * @param ctx     the {@code Context} object from which the current 
	 *                {@code TurtleState} is determined
	 * @param painter the {@code Painter} object on which the line is drawn
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentPosition = currentState.getPosition();
		Vector2D currentDirection = currentState.getDirection();
		
		double scalingFactor = step * currentState.getEffectiveLength();
		Vector2D newPosition = 
			currentPosition.translated(currentDirection.scaled(scalingFactor));
		
		painter.drawLine(currentPosition.getX(), 
				         currentPosition.getY(),
				         newPosition.getX(),
				         newPosition.getY(),
				         currentState.getColor(),
				         DEFAULT_LINE_WIDTH);
		
		currentState.setPosition(newPosition);
	}

}
