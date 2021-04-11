package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A program that shows example usage of {@code LSystem} and connected classes
 * by displaying a GUI interface through which a custom "L-system" configuration
 * file can be loaded.
 * 
 * @author Matija Frandolić
 */
public class Glavni3 {
	
	/**
	 * Main method of the program.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
	
}
