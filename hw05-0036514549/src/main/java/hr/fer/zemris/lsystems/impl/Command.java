package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * A model of a command that performs some action on the passed {@code Context}
 * and {@code Painter} objects.
 * 
 * @author Matija Frandolić
 */
public interface Command {

	/**
	 * Performs some action on the passed {@code Context} and {@code Painter} objects.
	 * 
	 * @param ctx     the {@code Context} object that is used by this command
	 * @param painter the {@code Painter} object that is used by this command
	 */
	void execute(Context ctx, Painter painter);
	
}
