package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * A model of the "scale" command that is used to update the unit length of the
 * current {@code TurtleState} by scaling it by the given factor.
 * 
 * @author Matija Frandolić
 */
public class ScaleCommand implements Command {

	/**
	 * Scaling factor of the unit length.
	 */
	private double factor;
	
	/**
	 * Constructs a new {@code ScaleCommand} from the given scaling factor
	 * of the unit length.
	 * 
	 * @param factor scaling factor of the unit length
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * Updates the unit length of the current {@code TurtleState} by scaling it 
	 * by the given factor.
	 *
	 * @param ctx the {@code Context} object from which the current 
	 *            {@code TurtleState} is determined
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		currentState.setEffectiveLength(factor * currentState.getEffectiveLength());
	}

}
