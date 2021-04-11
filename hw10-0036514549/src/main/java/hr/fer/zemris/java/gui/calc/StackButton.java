package hr.fer.zemris.java.gui.calc;

import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Defines functionality of {@link Calculator}s stack buttons.
 * 
 * @author Matija Frandolić
 */
public class StackButton extends CalcButton {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code StackButton} from the given arguments.
	 * 
	 * @param model    {@code CalcModel} object on which to call the given consumer
	 * @param stack    {@link Stack} that is used for storing calculator values
	 * @param label    label of the button
	 * @param consumer {@link Consumer} object that defines action to be performed on
	 *                 the given model
	 */
	public StackButton(CalcModel model, Stack<Double> stack, String label, 
			           BiConsumer<CalcModel, Stack<Double>> consumer) {
		setText(label);
		addActionListener(e -> consumer.accept(model, stack));
	}
	
}
