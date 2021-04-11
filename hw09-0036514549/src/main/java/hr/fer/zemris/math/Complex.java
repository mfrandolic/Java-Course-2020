package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Model of an immutable complex number All operations on complex numbers of 
 * this class return new complex number as a result of the operation.
 * 
 * @author Matija Frandolić
 */
public class Complex {
	
	/**
	 * Real part of this complex number.
	 */
	private final double re;
	
	/**
	 * Imaginary part of this complex number.
	 */
	private final double im;
	
	/**
	 * Module of this complex number.
	 */
	private final double module;
	
	/**
	 * Angle of this complex number.
	 */
	private final double angle;

	/**
	 * Represents complex number 0+i0.
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * Represents complex number 1+i0.
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * Represents complex number -1+i0.
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * Represents complex number 0+i1.
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * Represents complex number 0-i1.
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Default precision that is used for comparing double values.
	 */
	private static final double PRECISION = 1E-8;
	
	/**
	 * Constructs a new 0+i0 complex number.
	 */
	public Complex() {
		re = 0;
		im = 0;
		module = 0;
		angle = 0;
	}
	
	/**
	 * Constructs a new complex number from the given real and imaginary part.
	 * 
	 * @param re real part of this complex number
	 * @param im imaginary part of this complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
		module = Math.sqrt(re * re + im * im);
		angle = Math.atan2(im, re);
	}
	
	/**
	 * Returns the module of this complex number.
	 * 
	 * @return the module of this complex number
	 */
	public double module() {
		return module;
	}
	
	/**
	 * Multiplies this complex number by the given one and returns a new complex
	 * number as a result.
	 * 
	 * @param  c the complex number that is multiplied with this one
	 * @return   new complex number that is the result of multiplication
	 */
	public Complex multiply(Complex c) {
		return fromModuleAndAngle(module * c.module, angle + c.angle);
	}
	
	/**
	 * Divides this complex number by the given one and returns new complex
	 * number as a result.
	 * 
	 * @param  c the complex number that is used to divide this one
	 * @return   new complex number that is the result of division
	 */
	public Complex divide(Complex c) {
		return fromModuleAndAngle(module / c.module, angle - c.angle);
	}
	
	/**
	 * Adds this complex number and the given one and returns a new complex
	 * number as a result.
	 * 
	 * @param  c the complex number that is added to this one
	 * @return   new complex number that is the result of addition
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}
	
	/**
	 * Subtracts the given complex number from this one and returns a new complex
	 * number as a result.
	 * 
	 * @param  c the complex number that is subtracted from this one
	 * @return   new complex number that is the result of subtraction
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}
	
	/**
	 * Returns a new complex number that is a negated version of this one.
	 * 
	 * @return new complex number that is a negated version of this one
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Raises this complex number to the given n-th power and returns a new 
	 * complex number as a result.
	 * 
	 * @param  n the exponent to which to raise this complex number
	 * @return   new complex number that is the result of exponentiation
	 * @throws IllegalArgumentException if the exponent is negative
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must be non-negative.");
		}
		return fromModuleAndAngle(Math.pow(module, n), n * angle);
	}
	
	/**
	 * Returns the list of n-th roots of this complex number. 
	 * 
	 * @param  n the n-th root which to calculate for this complex number
	 * @return   the list of n-th roots of this complex number
	 * @throws IllegalArgumentException if the exponent is less than 1
	 */
	public List<Complex> root(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("n must be positive.");
		}
		
		List<Complex> roots = new ArrayList<>(n);
		double resultModule = Math.pow(module, 1.0 / n);
		
		for (int i = 0; i < n; i++) {
			double resultAngle = (2 * Math.PI * i + angle) / n;
			roots.add(fromModuleAndAngle(resultModule, resultAngle));
		}
		
		return roots;
	}
	
	/**
	 * Returns a string representation of this complex number in the form
	 * "x+iy", where x is the real part and y is the imaginary part.
	 * 
	 * @return a string representation of this complex number
	 */
	@Override
	public String toString() {
		double real = Math.abs(re - 0) < PRECISION ? 0 : re;
		double imaginary = Math.abs(im - 0) < PRECISION ? 0 : im;
		
		StringBuilder sb = new StringBuilder();
		sb.append(real)
		  .append(imaginary < 0 ? "-" : "+")
		  .append("i")
		  .append(Math.abs(imaginary));
		
		return sb.toString();
	}
	
	/**
	 * Creates a new complex number from the given module and angle.
	 * 
	 * @param  module the module of complex number
	 * @param  angle  the angle of complex number
	 * @return        new complex number created from the given module and angle
	 * @throws IllegalArgumentException if the module is negative
	 */
	private static Complex fromModuleAndAngle(double module, double angle) {
		if (Math.abs(module - 0) < PRECISION) {
			return ZERO;
		} else if (module < 0) {
			throw new IllegalArgumentException("Module cannot be negative.");
		}
		return new Complex(module * Math.cos(angle), module * Math.sin(angle));
	}
	
	/**
	 * Parses the given string into a complex number and returns the resulting
	 * complex number. Allowed formats of the input string are "[+-]x", "[+-]iy",
	 * "[+-]x+iy", "[+-]x-iy", "[+-]x + iy" (with any number of spaces) and
	 * "[+-]x - iy" (with any number of spaces), where x is the real part of the 
	 * complex number and y is the imaginary part.
	 * 
	 * @param  input the string that is parsed into a complex number
	 * @return       new complex number created by parsing the given string
	 * @throws NumberFormatException if the string is not parsable into a complex number
	 */
	public static Complex parseComplex(String input) {
		input = input.trim();
		
		String onlyReal= "[+-]?\\d+(\\.\\d+)?";
		String onlyImaginary = "[+-]?i(\\d+(\\.\\d+)?)?";
		Matcher both = Pattern.compile("([+-]?\\d+(\\.\\d+)?)\\s*([+-]\\s*i(\\d+(\\.\\d+)?)?)")
	                          .matcher(input);
		
		String realString = "0";
		String imaginaryString = "i0";
		
		if (input.matches(onlyReal)) {
			realString = input;
		} else if (input.matches(onlyImaginary)) {
			imaginaryString = input;
		} else if (both.matches()) {
			realString = both.group(1);
			imaginaryString = both.group(3).replaceAll("\\s+", "");
		} else {
			throw new NumberFormatException("String is not parsable into a complex number.");
		}
		
		if (imaginaryString.equals("i") || imaginaryString.equals("+i")) {
			imaginaryString = "i1";
		} else if (imaginaryString.equals("-i")) {
			imaginaryString = "-i1";
		}
		
		int imaginarySign = 1;
		if (imaginaryString.charAt(0) == '+') {
			imaginaryString = imaginaryString.substring(2);			
		} else if (imaginaryString.charAt(0) == '-') {
			imaginarySign = -1;
			imaginaryString = imaginaryString.substring(2);			
		} else {
			imaginaryString = imaginaryString.substring(1);						
		}
		
		double re = Double.parseDouble(realString);
		double im = imaginarySign * Double.parseDouble(imaginaryString);
		
		return new Complex(re, im);
	}
	
}
