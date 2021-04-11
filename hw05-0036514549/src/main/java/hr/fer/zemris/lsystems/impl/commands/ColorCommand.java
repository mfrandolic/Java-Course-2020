package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * A model of the "color" command that is used to update the color of the
 * current {@code TurtleState}.
 * 
 * @author Matija Frandolić
 */
public class ColorCommand implements Command {

	/**
	 * New color to be set.
	 */
	private Color color;
	
	/**
	 * Constructs a new {@code ColorCommand} from the given color.
	 * 
	 * @param color new color to be set
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * Updates the color of the current {@code TurtleState} to the color
	 * defined by this command.
	 * 
	 * @param ctx the {@code Context} object from which the current 
	 *            {@code TurtleState} is determined
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
