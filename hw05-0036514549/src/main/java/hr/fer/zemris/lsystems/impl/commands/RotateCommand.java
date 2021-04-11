package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * A model of the "rotate" command that is used to update the direction of the
 * current {@code TurtleState} by rotating it by the given angle.
 * 
 * @author Matija Frandolić
 */
public class RotateCommand implements Command {

	/**
	 * Angle by which to rotate.
	 */
	private double angle;
	
	/**
	 * Constructs a new {@code RotateCommand} from the given angle by which to rotate.
	 * 
	 * @param angle angle by which to rotate
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Updates the direction of the current {@code TurtleState} by rotating it 
	 * by the given angle.
	 *
	 * @param ctx the {@code Context} object from which the current 
	 *            {@code TurtleState} is determined
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentDirection = currentState.getDirection();
		currentDirection.rotate(angle * Math.PI / 180);
	}

}
