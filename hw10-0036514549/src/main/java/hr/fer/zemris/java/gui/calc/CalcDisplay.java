package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Defines look and functionality of {@link Calculator} display.
 * 
 * @author Matija Frandolić
 */
public class CalcDisplay extends JLabel {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new {@code CalcDisplay}.
	 * 
	 * @param model {@code CalcModel} object whose value to display
	 */
	public CalcDisplay(CalcModel model) {
		setOpaque(true);
		setBackground(Color.YELLOW);
		setFont(getFont().deriveFont(30f));
		setHorizontalAlignment(SwingConstants.RIGHT);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		model.addCalcValueListener(m -> setText(m.toString()));
	}
	
}
