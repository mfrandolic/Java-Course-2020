package hr.fer.zemris.math;

/**
 * Model of an immutable complex polynomial of the form "f(z)=zn*z^n+...+z2*z^2+z1*z+z0",
 * where numbers z0 to zn are coefficients of the corresponding powers in the 
 * polynomial.  All operations on polynomials of this class return new polynomial 
 * as a result of the operation.
 * 
 * @author Matija Frandolić
 */
public class ComplexPolynomial {
	
	/**
	 * Array of factors of this polynomial, from z0 to zn.
	 */
	private final Complex[] factors;

	/**
	 * Constructs a new complex polynomial from the given factors. Factors must
	 * be in the order from z0 to zn.
	 * 
	 * @param factors factors of this polynomial, in order from z0 to zn
	 */
	public ComplexPolynomial(Complex ... factors) {
		this.factors = factors;
	}
	
	/**
	 * Returns the order of this polynomial.
	 * 
	 * @return the order of this polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * Multiplies this polynomial by the given one and returns new complex polynomial 
	 * as a result.
	 * 
	 * @param  p the complex polynomial that is multiplied with this one
	 * @return   new complex polynomial that is the result of the multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] result = new Complex[factors.length + p.factors.length - 1];
		for (int i = 0; i < result.length; i++) {
			result[i] = Complex.ZERO;
		}
		
		for (int i = 0; i < factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				result[i + j] = result[i + j].add(factors[i].multiply(p.factors[j]));
			}
		}
		
		return new ComplexPolynomial(result);
	}
	
	/**
	 * Returns the first derivative of this polynomial as a new complex polynomial.
	 * 
	 * @return new complex polynomial that is the first derivative of this polynomial
	 */
	public ComplexPolynomial derive() {
		Complex[] result = new Complex[factors.length - 1];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
		}
		
		return new ComplexPolynomial(result);
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
		Complex result = factors[factors.length - 1];
		
		for (int i = factors.length - 2; i >= 0; i--) {
			result = result.multiply(z).add(factors[i]);
		}
		
		return result;
	}
	
	/**
	 * Returns a string representation of this complex polynomial in the format 
	 * "(zn)*z^n+...+(z2)*z^2+(z1)*z+(z0)".
	 * 
	 * @return a string representation of this complex polynomial
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = factors.length - 1; i > 0; i--) {
			sb.append("(").append(factors[i]).append(")*z^").append(i).append("+");
		}
		sb.append("(").append(factors[0]).append(")");
		return sb.toString();
	}
	
}
