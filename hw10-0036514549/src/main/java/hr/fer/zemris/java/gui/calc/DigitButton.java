package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Defines functionality of {@link Calculator}s digit buttons.
 * 
 * @author Matija Frandolić
 */
public class DigitButton extends CalcButton {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a new {@code DigitButton} from the given model and digit.
	 * 
	 * @param model {@code CalcModel} object to which to insert digit
	 * @param digit digit represented by this button
	 */
	public DigitButton(CalcModel model, int digit) {
		setFont(getFont().deriveFont(30f));
		setText(Integer.toString(digit));
		addActionListener(e -> {
			model.freezeValue(null);
			model.insertDigit(digit);
		});
	}
	
}
