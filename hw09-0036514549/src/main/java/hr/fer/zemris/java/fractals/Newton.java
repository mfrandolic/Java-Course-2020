package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program that displays a fractal derived from Newton-Raphson iteration. User is 
 * asked to enter the roots of the polynomial that is used to generate the fractal.
 * Fractal viewer is then started.
 * 
 * @author Matija Frandolić
 */
public class Newton {

	/**
	 * Main method of the program.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. "
				         + "Enter 'done' when done.");
		
		List<Complex> roots = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		int count = 1;
		
		while (true) {
			System.out.print("Root " + count + "> ");
			String input = sc.nextLine().trim();
			if (input.equals("done")) {
				if (roots.size() >= 2) {
					break;
				} else {
					System.out.println("Expected at least two roots. Try again.");
					continue;
				}
			}
			try {
				roots.add(Complex.parseComplex(input));
				count++;
			} catch (NumberFormatException e) {
				System.out.println("Not a valid complex number. Try again.");
			}
		}
		
		sc.close();
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		Complex[] rootsArray = roots.toArray(new Complex[0]);
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(Complex.ONE, rootsArray);
		
		FractalViewer.show(new NewtonRaphsonFractalProducer(crp));
	}
	
}
