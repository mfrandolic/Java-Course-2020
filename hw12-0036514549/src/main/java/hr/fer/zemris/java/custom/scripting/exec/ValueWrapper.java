package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * Wrapper class for value that is represented by any {@code Object}. Usage of 
 * provided arithmetic and comparison methods is allowed only if the stored value
 * and the given value are {@code null}s or instances of {@link Integer}, 
 * {@link Double} or {@link String} class. If {@code String} is used, it
 * must be parsable to either {@code Integer} or {@code Double}. If either of the 
 * values is {@code null}, it is treated as an integer with value of zero.
 * 
 * @author Matija Frandolić
 */
public class ValueWrapper {
	
	/**
	 * Value that is wrapped.
	 */
	private Object value;
	
	/**
	 * Constructs a new {@code ValueWrapper} from the given object.
	 * 
	 * @param value object that is wrapped
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	/**
	 * Returns the value that was wrapped by this wrapper.
	 * 
	 * @return the value that was wrapped by this wrapper
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Sets the object that will be wrapped by this wrapper.
	 * 
	 * @param value the value that will be wrapped by this wrapper
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Adds the given value to the value kept by this wrapper (therefore modifying
	 * the current value). Usage of this method is allowed only if the stored value
	 * and the given value are {@code null}s or instances of {@link Integer}, 
	 * {@link Double} or {@link String} class. If {@code String} is used, it
	 * must be parsable to either {@code Integer} or {@code Double}. If either of 
	 * the values is {@code null}, it is treated as an integer with value of zero.
	 * 
	 * @param incValue value that is added to the value kept by this wrapper
	 */
	public void add(Object incValue) {
		value = performOperation(value, incValue, (a, b) -> a + b);
	}
	
	/**
	 * Subtracts the given value from the value kept by this wrapper (therefore modifying
	 * the current value). Usage of this method is allowed only if the stored value
	 * and the given value are {@code null}s or instances of {@link Integer}, 
	 * {@link Double} or {@link String} class. If {@code String} is used, it
	 * must be parsable to either {@code Integer} or {@code Double}. If either of the 
	 * values is {@code null}, it is treated as an integer with value of zero.
	 * 
	 * @param decValue value that is subtracted from the value kept by this wrapper
	 */
	public void subtract(Object decValue) {
		value = performOperation(value, decValue, (a, b) -> a - b);
	}
	
	/**
	 * Multiplies value kept by this wrapper by the given value (therefore modifying
	 * the current value). Usage of this method is allowed only if the stored value
	 * and the given value are {@code null}s or instances of {@link Integer}, 
	 * {@link Double} or {@link String} class. If {@code String} is used, it
	 * must be parsable to either {@code Integer} or {@code Double}. If either of 
	 * the values is {@code null}, it is treated as an integer with value of zero.
	 * 
	 * @param mulValue value that is multiplied with the value kept by this wrapper
	 */
	public void multiply(Object mulValue) {
		value = performOperation(value, mulValue, (a, b) -> a * b);
	}
	
	/**
	 * Divides the value kept by this wrapper by the given value (therefore modifying
	 * the current value). Usage of this method is allowed only if the stored value
	 * and the given value are {@code null}s or instances of {@link Integer}, 
	 * {@link Double} or {@link String} class. If {@code String} is used, it
	 * must be parsable to either {@code Integer} or {@code Double}. If either of 
	 * the values is {@code null}, it is treated as an integer with value of zero.
	 * 
	 * @param divValue value that is used to divide the value kept by this wrapper
	 */
	public void divide(Object divValue) {
		value = performOperation(value, divValue, (a, b) -> a / b);
	}
	
	/**
	 * Compares the value kept by this wrapper with the given value and returns
	 * an integer that is less than zero if the currently stored value if lesser
	 * than the given value, an integer greater than zero if the currently stored
	 * value if greater than the given value, or zero if they are equal. Usage of 
	 * this method is allowed only if the stored value and the given value are 
	 * {@code null}s or instances of {@link Integer}, {@link Double} or {@link String} 
	 * class. If {@code String} is used, it must be parsable to either {@code Integer} 
	 * or {@code Double}. If either of the values is {@code null}, it is treated 
	 * as an integer with value of zero.
	 * 
	 * @param divValue value that is used to divide the value kept by this wrapper
	 */
	public int numCompare(Object withValue) {
		return performOperation(value, withValue, Double::compare).intValue();
	}
	
	/**
	 * Performs numeric operation defined by the given function on the given 
	 * arguments and returns {@link Integer} if both arguments were {@code Integer}s,
	 * or {@link Double} if either one of the argument was {@code Double}. Instances
	 * of {@link String} class are also allowed, in which case they must be parsable
	 * into either {@code Integer} or {@code Double}. If either of the given values
	 * is {@code null}, it is treated as an integer with value of zero.
	 * 
	 * @param value      value that is used to perform numeric operation
	 * @param otherValue other value that is used to perform numeric operation
	 * @param function   numeric operation to be performed on the given values
	 * @return           result of the operation
	 * @throws RuntimeException if either of the given values is not an instance of 
	 *                          allowed classes or if the given string is not parsable
	 *                          to a number
	 */
	private static Number performOperation(Object value, Object otherValue, 
			BiFunction<Double, Double, Number> function) {
		
		Number firstOperand = toNumber(value);
		Number secondOperand = toNumber(otherValue);
		Number result = function.apply(firstOperand.doubleValue(), secondOperand.doubleValue());
		
		if (firstOperand instanceof Integer && secondOperand instanceof Integer) {
			return result.intValue();
		} else {
			return result.doubleValue();
		}
	}
	
	/**
	 * Tries to parse the given object into a number. The given object must be 
	 * {@code null} or an instance of {@link Integer}, {@link Double} or {@link String} 
	 * class. If {@code String} is used, it must be parsable to either {@code Integer} 
	 * or {@code Double}. If the value is {@code null}, it is treated 
	 * as an integer with value of zero.
	 * 
	 * @param  value object that needs to be parsed to a number
	 * @return       resulting number
	 * @throws RuntimeException if either of the given values is not an instance of 
	 *                          allowed classes or if the given string is not parsable
	 *                          to a number
	 */
	private static Number toNumber(Object value) {
		if (value == null) {
			return Integer.valueOf(0);
		} else if (value instanceof Double || value instanceof Integer) {
			return (Number) value;
		} else if (value instanceof String) {
			String stringValue = (String) value;
			if (stringValue.contains(".") || stringValue.contains("E")) {
				try {
					return Double.parseDouble(stringValue);					
				} catch (NumberFormatException e) {
					throw new RuntimeException("String is not parsable to number.");
				}
			} else {
				try {
					return Integer.parseInt(stringValue);				
				} catch (NumberFormatException e) {
					throw new RuntimeException("String is not parsable to number.");
				}
			}
		} else {
			throw new RuntimeException("Value must be null or an instance of "
			                         + "Integer, Double or String class.");			
		}
	}
	
}
