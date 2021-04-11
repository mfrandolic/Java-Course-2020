package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A model of the "pop" command that is used to pop one {@code TurtleState} from
 * the stack of the given {@code Context} object.
 * 
 * @author Matija Frandolić
 */
public class PopCommand implements Command {

	/**
	 * Pops one {@code TurtleState} from the stack of the given {@code Context} 
	 * object.
	 *
	 * @param ctx the {@code Context} object from whose stack the 
	 *            {@code TurtleState} object is popped
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
