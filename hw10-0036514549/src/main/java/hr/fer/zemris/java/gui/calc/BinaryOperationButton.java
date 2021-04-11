package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;

/**
 * Defines functionality of {@link Calculator}s binary operation buttons with
 * optional inverse operation.
 * 
 * @author Matija Frandolić
 */
public class BinaryOperationButton extends CalcButton {

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
	private DoubleBinaryOperator invOp;
	
	/**
	 * Constructs a new {@code BinaryOperationButton} from the given arguments.
	 * 
	 * @param model   {@code CalcModel} object on whose values to perform the given operation
	 * @param opLabel normal operation label
	 * @param op      normal operation of this button
	 */
	public BinaryOperationButton(CalcModel model, String opLabel, DoubleBinaryOperator op) {
		this.opLabel = opLabel;
		setText(opLabel);
		
		addActionListener(e -> {
			if (model.hasFrozenValue()) {
				throw new CalcLayoutException("Calculator has a frozen value.");
			}
			
			DoubleBinaryOperator currentOp = inverted ? invOp : op;
			
			double value;
			if (!model.isActiveOperandSet()) {
				value = model.getValue();
				model.setActiveOperand(value);	
			} else {
				value = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), 
						                                                model.getValue());
				model.setActiveOperand(value);
			}
			
			model.freezeValue(Double.toString(value));
			model.setPendingBinaryOperation(currentOp);
			model.clear();
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
	public void addInverseOperation(JCheckBox inv, String invOpLabel, DoubleBinaryOperator invOp) {
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
