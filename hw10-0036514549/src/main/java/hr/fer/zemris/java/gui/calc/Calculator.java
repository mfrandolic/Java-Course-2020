package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.Stack;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Calculator with graphical user interface that resembles Windows XP calculator
 * by functionality.
 * 
 * @author Matija Frandolić
 */
public class Calculator extends JFrame {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Content pane.
	 */
	private Container cp;
	
	/**
	 * Model of the calculator.
	 */
	private CalcModel model;
	
	/**
	 * Constructs the GUI.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		
		cp = getContentPane();
		final int GAP = 5;
		cp.setLayout(new CalcLayout(GAP));
		model = new CalcModelImpl();
		
		initGUI();
		setSize(650, 350);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Initializes the GUI layout and components.
	 */
	private void initGUI() {
		CalcDisplay display = new CalcDisplay(model);
		cp.add(display, "1,1");
		
		JCheckBox inv = new JCheckBox("Inv");
		cp.add(inv, "5,7");
		
		addDigitButtons();
		addOperationButtons(inv);
		addActionButtons();
		addStackButtons(display);
	}
	
	/**
	 * Adds digit buttons to the layout.
	 */
	private void addDigitButtons() {
		cp.add(new DigitButton(model, 0), "5,3");
		cp.add(new DigitButton(model, 1), "4,3");
		cp.add(new DigitButton(model, 2), "4,4");
		cp.add(new DigitButton(model, 3), "4,5");
		cp.add(new DigitButton(model, 4), "3,3");
		cp.add(new DigitButton(model, 5), "3,4");
		cp.add(new DigitButton(model, 6), "3,5");
		cp.add(new DigitButton(model, 7), "2,3");
		cp.add(new DigitButton(model, 8), "2,4");
		cp.add(new DigitButton(model, 9), "2,5");
	}
	
	/**
	 * Adds operation buttons to the layout.
	 */
	private void addOperationButtons(JCheckBox inv) {
		cp.add(new BinaryOperationButton(model, "/", (x, y) -> x / y), "2,6");
		cp.add(new BinaryOperationButton(model, "*", (x, y) -> x * y), "3,6");
		cp.add(new BinaryOperationButton(model, "-", (x, y) -> x - y), "4,6");
		cp.add(new BinaryOperationButton(model, "+", (x, y) -> x + y), "5,6");
		
		cp.add(new UnaryOperationButton(model, "1/x", x -> 1 / x), "2,1");
		
		UnaryOperationButton log = new UnaryOperationButton(model, "log", Math::log10);
		log.addInverseOperation(inv, "10^x", x -> Math.pow(10, x));
		cp.add(log, "3,1");
		
		UnaryOperationButton ln = new UnaryOperationButton(model, "ln", Math::log);
		ln.addInverseOperation(inv, "e^x", Math::exp);
		cp.add(ln, "4,1");
		
		BinaryOperationButton power = new BinaryOperationButton(model, "x^n", (x, n) -> Math.pow(x, n));
		power.addInverseOperation(inv, "x^(1/n)", (x, n) -> Math.pow(x, 1 / n));
		cp.add(power, "5,1");
		
		UnaryOperationButton sin = new UnaryOperationButton(model, "sin", Math::sin);
		sin.addInverseOperation(inv, "arcsin", Math::asin);
		cp.add(sin, "2,2");
		
		UnaryOperationButton cos = new UnaryOperationButton(model, "cos", Math::cos);
		cos.addInverseOperation(inv, "arccos", Math::acos);
		cp.add(cos, "3,2");
		
		UnaryOperationButton tan = new UnaryOperationButton(model, "tan", Math::tan);
		tan.addInverseOperation(inv, "arctan", Math::atan);
		cp.add(tan, "4,2");
		
		UnaryOperationButton ctg = new UnaryOperationButton(model, "ctg", x -> 1 / Math.tan(x));
		ctg.addInverseOperation(inv, "arcctg", x -> Math.PI / 2 - Math.atan(x));
		cp.add(ctg, "5,2");
	}
	
	/**
	 * Adds different action buttons to the layout.
	 */
	private void addActionButtons() {
		cp.add(new ActionButton(model, "+/-", CalcModel::swapSign), "5,4");
		cp.add(new ActionButton(model, ".", CalcModel::insertDecimalPoint), "5,5");
		cp.add(new ActionButton(model, "clr", CalcModel::clear), "1,7");
		cp.add(new ActionButton(model, "reset", CalcModel::clearAll), "2,7");
		cp.add(new ActionButton(model, "=", m -> {
			m.setValue(m.getPendingBinaryOperation().applyAsDouble(m.getActiveOperand(), m.getValue()));
			m.clearActiveOperand();
			m.setPendingBinaryOperation(null);
		}), "1,6");
	}
	
	/**
	 * Adds stack buttons to the layout.
	 */
	private void addStackButtons(CalcDisplay display) {
		Stack<Double> stack = new Stack<Double>();
		
		cp.add(new StackButton(model, stack, "push", (m, s) -> {
			stack.push(m.getValue());
		}), "3,7");
		
		cp.add(new StackButton(model, stack, "pop", (m, s) -> {
			if (!stack.isEmpty()) {
				if (m.hasFrozenValue()) {
					m.freezeValue(null);
				}
				m.setValue(stack.pop());
			} else {
				display.setText("Stack is empty.");
			}
		}), "4,7");
	}

	/**
	 * Main method of the program that is responsible for starting the GUI.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);;
		});
	}
	
}
