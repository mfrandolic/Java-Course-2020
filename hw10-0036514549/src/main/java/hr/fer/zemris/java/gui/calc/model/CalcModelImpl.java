package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Implementation of {@link CalcModel} that is used by {@link Calculator}.
 * 
 * @author Matija Frandolić
 */
public class CalcModelImpl implements CalcModel {
	
	/**
	 * Flag that tells whether the model is currently editable.
	 */
	private boolean editable;
	
	/**
	 * Flag that tells whether the current value is positive.
	 */
	private boolean positive;
	
	/**
	 * Current value in string form.
	 */
	private String stringValue;
	
	/**
	 * Current value in numeric form.
	 */
	private double numericValue;
	
	/**
	 * Value that is currently frozen.
	 */
	private String frozenValue;
	
	/**
	 * Operand that is saved to later be used by pendingOperation.
	 */
	private Double activeOperand;
	
	/**
	 * Operation that is pending to be performed on activeOperand and current value.
	 */
	private DoubleBinaryOperator pendingOperation;
	
	/**
	 * List of listeners.
	 */
	private List<CalcValueListener> listeners;
	
	/**
	 * Constructs a new {@code CalcModelImpl}.
	 */
	public CalcModelImpl() {
		editable = true;
		positive = true;
		stringValue = "";
		numericValue = 0.0;
		frozenValue = null;
		activeOperand = null;
		pendingOperation = null;
		listeners = new ArrayList<CalcValueListener>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l, "Listener cannot be null.");
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
		l.valueChanged(this);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l, "Listener cannot be null.");
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}
	
	/**
	 * Notifies all listeners that value of this model changed.
	 */
	private void notifyListeners() {
		listeners.forEach(l -> l.valueChanged(this));
	}
	
	@Override
	public String toString() {
		if (hasFrozenValue()) {
			return frozenValue;
		}
		if (stringValue.length() >= 2 && 
			stringValue.charAt(0) == '0' &&
			stringValue.charAt(1) != '.') {
			stringValue = stringValue.substring(1);
		}
		String output = stringValue.length() == 0 ? "0" : stringValue;
		return positive ? output : "-" + output;
	}

	@Override
	public double getValue() {
		return positive ? numericValue : -numericValue;
	}

	@Override
	public void setValue(double value) {
		if (value < 0) {
			positive = false;
			stringValue = Double.toString(value).substring(1);
		} else {
			positive = true;
			stringValue = Double.toString(value);			
		}
		numericValue = Math.abs(value);
		editable = false;
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		stringValue = "";
		numericValue = 0.0;
		editable = true;
		positive = true;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		clear();
		activeOperand = null;
		pendingOperation = null;
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable()) {
			throw new CalculatorInputException("Calculator model is not editable.");
		}
		positive = !positive;
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable()) {
			throw new CalculatorInputException("Calculator model is not editable.");
		}
		if (stringValue.length() == 0) {
			throw new CalculatorInputException("No entered digits.");
		}
		if (stringValue.contains(".")) {
			throw new CalculatorInputException("Number already contains decimal point.");
		}
		
		stringValue += ".";
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable()) {
			throw new CalculatorInputException("Calculator model is not editable.");
		}
		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Given number must be a single digit.");
		}
		if (digit == 0 && Math.abs(numericValue - 0) < 1E-10 && 
            !(digit == 0 && stringValue.length() == 0) &&
            !(stringValue.length() > 0 && stringValue.charAt(stringValue.length() - 1) == '.')) {
			return;
		}
		
		String tmpStringValue = stringValue + digit;
		double tmpNumericValue = Double.parseDouble(tmpStringValue);
		
		if (tmpNumericValue > Double.MAX_VALUE) {
			throw new CalculatorInputException("Number is too large.");
		}
		
		stringValue = tmpStringValue;
		numericValue = tmpNumericValue;
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException("Active operand is not set.");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}
	
	@Override
	public void freezeValue(String value) {
		frozenValue = value;
	}
	
	@Override
	public boolean hasFrozenValue() {
		return frozenValue != null;
	}

}
