package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * A program that shows an example usage of the class {@code ComplexNumber}.
 * 
 * @author Matija Frandolić
 */
public class ComplexDemo {

	/**
	 * A method that is called when the program starts. 
	 * 
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		                     .div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}
	
}
