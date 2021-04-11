package hr.fer.zemris.math;

/**
 * Model of an immutable complex rooted polynomial of the form "f(z)=z0*(z-z1)*...*(z-zn)",
 * where numbers z1 to zn are roots of the polynomial and z0 is a constant. All operations 
 * on polynomials of this class return new polynomial as a result of the operation.
 * 
 * @author Matija Frandolić
 */
public class ComplexRootedPolynomial {
	
	/**
	 * Constant of this polynomial.
	 */
	private final Complex constant;
	
	/**
	 * Array of roots of this polynomial.
	 */
	private final Complex[] roots;

	/**
	 * Constructs a new complex rooted polynomial from the given constant and roots.
	 * 
	 * @param constant the constant of this polynomial
	 * @param roots    the roots of this polynomial
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * Computes polynomial value for the given complex number and returns the
	 * resulting complex number.
	 * 
	 * @param  z the complex number for which to compute value of the polynomial
	 * @return   new complex number that represents value of the polynomial for the
	 *           given complex number
	 */
	public Complex apply(Complex z) {
		Complex result = constant;
		for (int i = 0; i < roots.length; i++) {
			result = result.multiply(z.sub(roots[i]));
		}
		return result;
	}
	
	/**
	 * Converts this polynomial to the corresponding {@link ComplexPolynomial}.
	 * 
	 * @return the corresponding {@code ComplexPolynomial} of this polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] factors = new Complex[roots.length + 1];
		
		factors[0] = constant;
		for (int i = 1; i < factors.length; i++) {
			factors[i] = Complex.ZERO;
		}

		for (int i = 0; i < factors.length - 1; i++) { 
			for (int j = factors.length - 1; j > 0; j--) { 
				factors[j] = factors[j - 1].sub(factors[j].multiply(roots[i]));
			}
			factors[0] = Complex.ZERO.sub(factors[0].multiply(roots[i]));
		}
		
		return new ComplexPolynomial(factors);
	}
	
	/**
	 * Returns a string representation of this polynomial in the format 
	 * "(z0)*(z-(z1))*...*(z-(zn))", where z0 is the constant of the polynomial
	 * and z1 to zn are roots of the polynomial.
	 * 
	 * @return a string representation of this polynomial
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("(").append(constant).append(")");
		for (int i = 0; i < roots.length; i++) {
			sb.append("*(z-(").append(roots[i]).append("))");
		}
		
		return sb.toString();
	}
	
	/**
	 * Returns the index of the closest root for the given complex number {@code z}
	 * that is within treshold, or -1 if there is no such root. First root has
	 * index 0.
	 * 
	 * @param z        the complex number for which to find the closest root within
	 *                 the given treshold
	 * @param treshold the treshold within which to find the root
	 * @return         the index of the closest root for the given complex number
	 *                 that is within treshold
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int indexOfClosestRoot = -1;
		double closestDistance = treshold;
		
		for (int i = 0; i < roots.length; i++) {
			double distance = Math.abs(z.sub(roots[i]).module());
			if (distance <= closestDistance) {
				indexOfClosestRoot = i;
				closestDistance = distance;
			}
		}
		
		return indexOfClosestRoot;
	}
	
}
