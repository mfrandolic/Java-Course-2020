package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A program that shows example usage of {@code LSystem} and connected classes
 * by creating and displaying the "Koch curve" fractal.
 * 
 * @author Matija Frandolić
 */
public class Glavni1 {
	
	/**
	 * Main method of the program.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}
	
	/**
	 * Returns the {@code LSystem} object created by configuring the
	 * {@code LSystemBuilder} object's parameters through its methods.
	 * 
	 * @param  provider the object used to create {@code LSystemBuilder} object
	 * @return          the resulting {@code LSystem}
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
					.registerCommand('F', "draw 1")
					.registerCommand('+', "rotate 60")
					.registerCommand('-', "rotate -60")
					.setOrigin(0.05, 0.4)
					.setAngle(0)
					.setUnitLength(0.9)
					.setUnitLengthDegreeScaler(1.0/3.0)
					.registerProduction('F', "F+F--F+F")
					.setAxiom("F")
					.build();
	}
	
}
