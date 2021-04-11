package hr.fer.zemris.java.hw02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents an unmodifiable complex number. It provides methods
 * for working with complex numbers and performing calculations with them.
 * 
 * @author Matija Frandolić
 */
public class ComplexNumber {

	/**
	 * Real part of this complex number.
	 */
	private final double real;
	
	/**
	 * Imaginary part of this complex number.
	 */
	private final double imaginary;
	
	/**
	 * Magnitude of this complex number.
	 */
	private final double magnitude;
	
	/**
	 * Angle of this complex number.
	 */
	private final double angle;
	
	/**
	 * Precision when comparing two double values.
	 */
	private static final double PRECISION = 1E-8;
	
	/**
	 * Constructs a new complex number from its real part and imaginary part.
	 * 
	 * @param real      the real part of this complex number
	 * @param imaginary the imaginary part of this complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		
		magnitude = Math.sqrt(real * real + imaginary * imaginary);
		double angleFromMinusPiToPi = Math.atan2(imaginary, real);

		if (angleFromMinusPiToPi < 0) {
			angle = angleFromMinusPiToPi + 2 * Math.PI;
		} else {
			angle = angleFromMinusPiToPi;
		}
	}
	
	/**
	 * Returns a new complex number consisting only of the real part.
	 * 
	 * @param real the real part of the complex number
	 * @return     the complex number created from the given real part
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Returns a new complex number consisting only of the imaginary part.
	 * 
	 * @param imaginary the imaginary part of the complex number
	 * @return          the complex number created from the given imaginary part
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Returns a new complex number defined by its magnitude and angle.
	 * 
	 * @param  magnitude the magnitude of the complex number
	 * @param  angle     the angle of the complex number
	 * @return           the complex number created from the given magnitude
	 *                   and angle
	 * @throws IllegalArgumentException if magnitude is negative
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if (Math.abs(magnitude) < PRECISION) {
			return new ComplexNumber(0, 0);
		} else if (magnitude < 0) {
			throw new IllegalArgumentException("Magnitude cannot be negative.");
		}
		
		double real = magnitude * Math.cos(angle);
		double imaginary = magnitude * Math.sin(angle);
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Returns a new complex number created by parsing the given string.
	 * The string must be in one of the following formats: "x+yi", "x", "yi",
	 * "i", "+i" or "-i", where x is a real number that represents the real
	 * part and y is a real number that represents the imaginary part. Decimal
	 * symbol is always a dot ('.'). Leading plus sign is allowed.
	 * 
	 * @param  s the string that is parsed
	 * @return   the complex number created by parsing the given string
	 * @throws NumberFormatException if the string is not parsable
	 */
	public static ComplexNumber parse(String s) {
		String onlyRealRegex = "[+-]?\\d+(\\.\\d+)?";
		String onlyImaginaryRegex = "[+-]?(\\d+(\\.\\d+)?)?i";
		Matcher bothMatcher = Pattern.compile("([+-]?\\d+(\\.\\d+)?)([+-](\\d+(\\.\\d+)?)?i)")
	                                 .matcher(s);
		String realString = "0";
		String imaginaryString = "0i";
		
		if (s.matches(onlyRealRegex)) {
			realString = s;
		} else if (s.matches(onlyImaginaryRegex)) {
			imaginaryString = s;
		} else if (bothMatcher.matches()) {
			realString = bothMatcher.group(1);
			imaginaryString = bothMatcher.group(3);
		} else {
			throw new NumberFormatException("String is not parsable.");
		}
		
		if (imaginaryString.equals("i") || imaginaryString.equals("+i")) {
			imaginaryString = "1i";
		} else if (imaginaryString.equals("-i")) {
			imaginaryString = "-1i";
		}
		
		imaginaryString = imaginaryString.substring(0, imaginaryString.length() - 1);
		
		double real = Double.parseDouble(realString);
		double imaginary = Double.parseDouble(imaginaryString);
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Returns the real part of this complex number.
	 * 
	 * @return the real part of this complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns the imaginary part of this complex number.
	 * 
	 * @return the imaginary part of this complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns the magnitude of this complex number.
	 * 
	 * @return the magnitude of this complex number
	 */
	public double getMagnitude() {
		return magnitude;
	}
	
	/**
	 * Returns the angle of this complex number. The angle is in radians,
	 * in range from 0 to 2pi.
	 * 
	 * @return the angle of this complex number
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * Adds this complex number and the given one and returns the result
	 * as a new complex number.
	 * 
	 * @param c the complex number used for addition
	 * @return	the complex number that is the result of addition
	 */
	public ComplexNumber add(ComplexNumber c) {
		double resultReal = real + c.real;
		double resultImaginary = imaginary + c.imaginary;
		return new ComplexNumber(resultReal, resultImaginary);
	}
	
	/**
	 * Subtracts the given complex number from this one and returns the
	 * result as a new complex number.
	 * 
	 * @param c the complex number used for subtraction
	 * @return  the complex number that is the result of subtraction
	 */
	public ComplexNumber sub(ComplexNumber c) {
		double resultReal = real - c.real;
		double resultImaginary = imaginary - c.imaginary;
		return new ComplexNumber(resultReal, resultImaginary);
	}
	
	/**
	 * Multiplies this complex number by the given one and returns the result
	 * as a new complex number.
	 * 
	 * @param c the complex number used for multiplication
	 * @return  the complex number that is the result multiplication
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double resultMagnitude = magnitude * c.magnitude;
		double resultAngle = angle + c.angle;
		return ComplexNumber.fromMagnitudeAndAngle(resultMagnitude, resultAngle);
	}
	
	/**
	 * Divides this complex number by the given one and returns the result
	 * as a new complex number.
	 * 
	 * @param  c the complex number used for division
	 * @return   the complex number that is the result of division
	 * @throws IllegalArgumentException if the given complex number is zero
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (Math.abs(c.magnitude) < PRECISION) {
			throw new IllegalArgumentException("Cannot divide by zero.");
		}
		
		double resultMagnitude = magnitude / c.magnitude;
		double resultAngle = angle - c.angle;
		return ComplexNumber.fromMagnitudeAndAngle(resultMagnitude, resultAngle);
	}
	
	/**
	 * Raises this complex number to the given power and returns the result
	 * as a new complex number.
	 * 
	 * @param  n the power to which to raise this complex number
	 * @return   the complex number that is the result of exponentiation
	 * @throws IllegalArgumentException if the given power is negative
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Exponent cannot be negative.");
		}
		
		double resultMagnitude = Math.pow(magnitude, n);
		double resultAngle = n * angle;
		return ComplexNumber.fromMagnitudeAndAngle(resultMagnitude, resultAngle);
	}
	
	/**
	 * Calculates all the {@code n}-th roots of this complex number returns 
	 * the array of resulting complex numbers.
	 * 
	 * @param  n the root to be calculated
	 * @return the array of resulting complex numbers
	 * @throws IllegalArgumentException if the given root is not positive
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Root must be positive.");
		}
		
		ComplexNumber[] roots = new ComplexNumber[n];
		double resultMagnitude = Math.pow(magnitude, 1.0 / n);
		
		for (int i = 0; i < n; i++) {
			double resultAngle = (2 * Math.PI * i + angle) / n;
			roots[i] = ComplexNumber.fromMagnitudeAndAngle(resultMagnitude, resultAngle);
		}
		
		return roots;
	}

	/**
	 * Returns the string representation of this complex number in the format
	 * "x+yi", where x is the real part and y is the imaginary part.
	 * 
	 * @return the string representation of this complex number
	 */
	@Override
	public String toString() {
		if (imaginary > 0 || Math.abs(imaginary) < PRECISION) {
			return Double.toString(real) + "+" + Double.toString(imaginary) + "i";			
		} else {
			return Double.toString(real) + Double.toString(imaginary) + "i";
		}
	}
	
}
