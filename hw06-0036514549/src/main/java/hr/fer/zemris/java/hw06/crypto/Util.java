package hr.fer.zemris.java.hw06.crypto;

/**
 * Utility class that contains methods for converting hex-string to byte array
 * and converting byte array to hex-string.
 * 
 * @author Matija Frandolić
 */
public class Util {

	/**
	 * Converts the given hex-encoded string to byte array and returns the
	 * resulting array.
	 * 
	 * @param  keyText the hex-encoded string that needs to be converted
	 * @return         the byte array that is the result of conversion
	 * @throws IllegalArgumentException if input string is odd-sized or contains
	 *                                  invalid characters
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Input string must have even length.");
		}
		
		byte[] result = new byte[keyText.length() / 2];
		
		for (int i = 0; i < result.length; i++) {
			int upperBits = hexDigitToInt(keyText.charAt(2 * i)) << 4;
			int lowerBits = hexDigitToInt(keyText.charAt(2 * i + 1));
			
			result[i] = (byte) (upperBits + lowerBits);
		}
		
		return result;
	}
	
	/**
	 * Converts the given byte array to hex-encoded string and returns the 
	 * resulting string.
	 * 
	 * @param bytearray the byte array that needs to be converted
	 * @return          the hex-encoded string that is the result of conversion
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();
		
		for (byte b : bytearray) {
			int upperBits = (b & 0xF0) >>> 4;
			int lowerBits = b & 0x0F;
			
			sb.append(intToHexDigit(upperBits));
			sb.append(intToHexDigit(lowerBits));
		}
		
		return sb.toString();
	}
	
	/**
	 * Converts the given hex digit to its integer value in decimal system and
	 * returns the resulting integer.
	 * 
	 * @param  hexDigit hex digit that needs to be converted
	 * @return          integer value in decimal system of the given hex digit
	 * @throws IllegalArgumentException if the given hex digit is invalid
	 */
	private static int hexDigitToInt(char hexDigit) {
		if (Character.isDigit(hexDigit)) {
			return hexDigit - '0';
		}
		
		switch (Character.toLowerCase(hexDigit)) {
		case 'a': return 10;
		case 'b': return 11;
		case 'c': return 12;
		case 'd': return 13;
		case 'e': return 14;
		case 'f': return 15;
		default:
			throw new IllegalArgumentException("Invalid hex digit.");
		}
	}
	
	/**
	 * Converts the given integer in decimal system to hex digit and returns the
	 * resulting hex digit. The given integer must be in range [0, 15].
	 * 
	 * @param  number integer that need to be converted
	 * @return        hex digit that is the hexadecimal representation of the 
	 *                given integer
	 * @throws IllegalArgumentException if the given integer cannot be represented
	 *                                  by a single hex digit, i.e. if it is outside
	 *                                  of the range [0, 15]
	 */
	private static char intToHexDigit(int number) {
		if (number < 0 || number > 15) {
			throw new IllegalArgumentException("Cannot convert " + number +
					                           " to hex digit.");
		}
		
		switch (number) {
		case 10: return 'a';
		case 11: return 'b';
		case 12: return 'c';
		case 13: return 'd';
		case 14: return 'e';
		case 15: return 'f';
		default: 
			return (char) (number + '0');
		}
	}

}
