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
public class Glavni2 {
	
	/**
	 * Main method of the program.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}
	
	/**
	 * Returns the {@code LSystem} object created by configuring the
	 * {@code LSystemBuilder} object's parameters through the string instructions.
	 * 
	 * @param  provider the object used to create {@code LSystemBuilder} object
	 * @return          the resulting {@code LSystem}
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
					"origin                 0.05 0.4",
					"angle                  0",
					"unitLength             0.9",
					"unitLengthDegreeScaler 1.0 / 3.0",
					"",
					"command F draw 1",
					"command + rotate 60",
					"command - rotate -60",
					"",
					"axiom F",
					"",
					"production F F+F--F+F"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
}
