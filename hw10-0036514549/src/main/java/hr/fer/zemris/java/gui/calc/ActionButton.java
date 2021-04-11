package hr.fer.zemris.java.gui.calc;

import java.util.function.Consumer;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Defines functionality of {@link Calculator}s different action buttons.
 * 
 * @author Matija Frandolić
 */
public class ActionButton extends CalcButton {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code ActionButton} from the given arguments.
	 * 
	 * @param model    {@code CalcModel} object on which to call the given consumer
	 * @param label    label of the button
	 * @param consumer {@link Consumer} object that defines action to be performed on
	 *                 the given model
	 */
	public ActionButton(CalcModel model, String label, Consumer<CalcModel> consumer) {
		setText(label);
		addActionListener(e -> consumer.accept(model));
	}
}
