package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * A program that shows an example usage of the class {@code ObjectStack} by
 * implementing a simple postfix notation calculator. The program accepts
 * exactly one command-line argument. That argument should be a valid
 * expression in postfix notation enclosed with quotation marks. Numbers and
 * operators must be separated by one or more spaces. Supported operators are 
 * "+, -, /, *, %" and all operators work with and produce integer results.
 * 
 * @author Matija Frandolić
 */
public class StackDemo {

	/**
	 * A method that is called when the program starts.
	 * 
	 * @param args the expression to be evaluated, entered from the command-line
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expression not provided.");
			return;
		}
		
		String expression[] = args[0].trim().split("\\s+");
		ObjectStack stack = new ObjectStack();
		
		for (String element : expression) {
			try {
				Integer number = Integer.parseInt(element);
				stack.push(number);
				continue;
			} catch (NumberFormatException e) {
				// do nothing
			}
	
			if (stack.size() < 2) {
				System.out.println("Not a valid postfix expression.");
				return;
			}
			
			Integer operand2 = (Integer) stack.pop();
			Integer operand1 = (Integer) stack.pop();
			
			switch (element) {
			case "+":
				stack.push(operand1 + operand2);
				break;
			case "-":
				stack.push(operand1 - operand2);
				break;
			case "/":
				if (operand2 == 0) {
					System.out.println("Cannot divide by zero.");
					return;
				}
				stack.push(operand1 / operand2);
				break;
			case "*":
				stack.push(operand1 * operand2);
				break;
			case "%":
				stack.push(operand1 % operand2);
				break;
			default:
				System.out.println("Unsupported operation: " + element);
				return;
			}
		}
		
		if (stack.size() != 1) {
			System.out.println("Not a valid postfix expression.");
		} else {
			System.out.println("Expression evaluates to " + stack.pop() + ".");
		}
	}
	
}
