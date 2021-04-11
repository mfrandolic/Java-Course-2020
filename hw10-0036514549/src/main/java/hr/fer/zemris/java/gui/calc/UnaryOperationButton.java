package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;

/**
 * Defines functionality of {@link Calculator}s unary operation buttons with
 * optional inverse operation.
 * 
 * @author Matija Frandolić
 */
public class UnaryOperationButton extends CalcButton {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Flag that tells whether currently active operation is the inverse.
	 */
	private boolean inverted;
	
	/**
	 * Normal operation label.
	 */
	private String opLabel;
	
	/**
	 * Inverse operation.
	 */
	private DoubleUnaryOperator invOp;
	
	/**
	 * Constructs a new {@code UnaryOperationButton} from the given arguments.
	 * 
	 * @param model   {@code CalcModel} object on whose values to perform the given operation
	 * @param opLabel normal operation label
	 * @param op      normal operation of this button
	 */
	public UnaryOperationButton(CalcModel model, String opLabel, DoubleUnaryOperator op) {
		this.opLabel = opLabel;
		setText(opLabel);
		
		addActionListener(e -> {
			if (model.hasFrozenValue()) {
				throw new CalcLayoutException("Calculator has a frozen value.");
			}
			DoubleUnaryOperator currentOp = inverted ? invOp : op;
			model.setValue(currentOp.applyAsDouble(model.getValue()));
		});
	}
	
	/**
	 * Adds the inverse operation to this button using the given {@link JCheckBox}
	 * to determine whether normal or inverse action is currently active.
	 * 
	 * @param inv        {@code JCheckBox} which is used to determine whether normal 
	 *                   or inverse action is currently active
	 * @param invOpLabel inverse operation label
	 * @param invOp      inverse operation of this button
	 */
	public void addInverseOperation(JCheckBox inv, String invOpLabel, DoubleUnaryOperator invOp) {
		this.invOp = invOp;
		
		inv.addActionListener(e -> {
			if (inv.isSelected()) {
				setText(invOpLabel);
				inverted = true;
			} else {
				setText(opLabel);
				inverted = false;
			}
		});
	}
	
}
