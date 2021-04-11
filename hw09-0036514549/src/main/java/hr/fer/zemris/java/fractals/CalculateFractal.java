package hr.fer.zemris.java.fractals;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Implementation of {@link Runnable} that represents a job of calculating the 
 * data for the fractal derived from the Newton-Raphson iteration on portion of 
 * the window, from yMin to yMax in full width.
 * 
 * @author Matija Frandolić
 */
public class CalculateFractal implements Runnable {
	
	/**
	 * Minimum value of the real part.
	 */
	private double reMin;
	
	/**
	 * Maximum value of the real part.
	 */
	private double reMax;
	
	/**
	 * Minimum value of the imaginary part.
	 */
	private double imMin;
	
	/**
	 * Maximum value of the imaginary part.
	 */
	private double imMax;
	
	/**
	 * Width of the window on which the fractal is displayed.
	 */
	private int width;
	
	/**
	 * Height of the window on which the fractal is displayed.
	 */
	private int height;
	
	/**
	 * Minimum y-coordinate that is calculated.
	 */
	private int yMin;
	
	/**
	 * Maximum y-coordinate that is calculated
	 */
	private int yMax;
	
	/**
	 * Maximum number of iterations for each point on the screen.
	 */
	private int m;
	
	/**
	 * Array in which the calculated data for each point on the screen is stored.
	 */
	private short[] data;
	
	/**
	 * Flag used to indicate that computation should be cancelled 
	 * (when set to {@code true}).
	 */
	private AtomicBoolean cancel;
	
	/**
	 * Complex rooted polynomial that is used to generate a fractal.
	 */
	private ComplexRootedPolynomial crp;
	
	/**
	 * Default convergence treshold.
	 */
	private static final double CONVERGENCE_TRESHOLD = 1E-3;
	
	/**
	 * Default root treshold.
	 */
	private static final double ROOT_TRESHOLD = 2E-3;
	
	/**
	 * Constructs a new {@code CalculateFractal} from the given arguments.
	 * 
	 * @param reMin  minimum value of the real part
	 * @param reMax  maximum value of the real part
	 * @param imMin  minimum value of the imaginary part
	 * @param imMax  maximum value of the imaginary part
	 * @param width  width of the window on which the fractal is displayed
	 * @param height height of the window on which the fractal is displayed
	 * @param yMin   minimum y-coordinate that is calculated
	 * @param yMax   maximum y-coordinate that is calculated
	 * @param m      maximum number of iterations for each point on the screen
	 * @param data   array in which the calculated data for each point on the screen is stored
	 * @param cancel flag used to indicate that computation should be cancelled
	 *               (when set to {@code true})
	 * @param crp    complex rooted polynomial that is used to generate a fractal
	 */
	public CalculateFractal(double reMin, double reMax, double imMin, double imMax, int width, int height, 
			int yMin, int yMax, int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial crp) {
		
		this.reMin = reMin;
		this.reMax = reMax;
		this.imMin = imMin;
		this.imMax = imMax;
		this.width = width;
		this.height = height;
		this.yMin = yMin;
		this.yMax = yMax;
		this.m = m;
		this.data = data;
		this.cancel = cancel;
		this.crp = crp;
	}

	/**
	 * Calculates the data needed for the fractal for each point on the window, 
	 * from yMin to yMax in full width.
	 */
	@Override
	public void run() {
		ComplexPolynomial polynomial = crp.toComplexPolynom();
		ComplexPolynomial derived = polynomial.derive();
		int offset = width * yMin;
		
		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x <= width - 1; x++) {
				if (cancel.get()) {
					return;
				}
				
				double cRe = (double) x / (width - 1) * (reMax - reMin) + reMin;
				double cIm = (double) (height - 1 - y) / (height - 1) * (imMax - imMin) + imMin;
				Complex c = new Complex(cRe, cIm);
				Complex zn = c;
				Complex znold;
				int i = 0;
				double module;
				
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = derived.apply(zn);
					Complex fraction = numerator.divide(denominator);
					znold = zn;
					zn = zn.sub(fraction);
					module = znold.sub(zn).module();
				} while (module > CONVERGENCE_TRESHOLD && ++i < m);
				
				int index = crp.indexOfClosestRootFor(zn, ROOT_TRESHOLD);
				data[offset++] = (short) (index + 1);
			}
		}
		
		return;
	}
	
}
