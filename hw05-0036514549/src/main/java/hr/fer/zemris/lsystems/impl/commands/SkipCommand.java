package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * A model of the "skip" command that is used to set the position of the current 
 * {@code TurtleState} to the newly calculated position.
 * 
 * @author Matija Frandolić
 */
public class SkipCommand implements Command {

	/**
	 * Scaling factor of the unit length in this skip command.
	 */
	private double step;
	
	/**
	 * Constructs a new {@code SkipCommand} from the given scaling factor
	 * of the unit length.
	 * 
	 * @param step scaling factor of the unit length in this skip command
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Sets the position of the current {@code TurtleState} to the newly 
	 * calculated position.
	 *
	 * @param ctx the {@code Context} object from which the current 
	 *            {@code TurtleState} is determined
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentPosition = currentState.getPosition();
		Vector2D currentDirection = currentState.getDirection();
		
		double scalingFactor = step * currentState.getEffectiveLength();
		Vector2D newPosition = 
			currentPosition.translated(currentDirection.scaled(scalingFactor));
		
		currentState.setPosition(newPosition);
	}

}
