package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A model of the "push" command that is used to copy the {@code TurtleState} 
 * from the top of the stack of the given {@code Context} and push it to the stack.
 * 
 * @author Matija Frandolić
 */
public class PushCommand implements Command {

	/**
	 * Copies the {@code TurtleState} from the top of the stack of the given 
	 * {@code Context} and pushes it to the stack.
	 *
	 * @param ctx the {@code Context} object from whose stack the top
	 *            {@code TurtleState} object is copied and pushed
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
