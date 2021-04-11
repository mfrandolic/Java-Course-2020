package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.JButton;

/**
 * Serves as base class for all {@link Calculator} buttons and defines button look.
 * 
 * @author Matija Frandolić
 */
public abstract class CalcButton extends JButton {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs new {@code CalcButton} with defined button look.
	 */
	public CalcButton() {
		setOpaque(true);
		setBackground(new Color(0xDDDDFF));
	}
	
}
