package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji od korisnika traži unos cijelog broja iz intervala [3,20] 
 * i ispisuje fakorijelu unesenog broja te ponavlja taj postupak sve dok 
 * se ne upiše "kraj".
 * 
 * @author Matija Frandolić
 * @version 1.0
 */
public class Factorial {

	/**
	 * Glavna metoda koja se poziva pri pokretanju programa.
	 * 
	 * @param args argumenti iz komandne linije
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			System.out.print("Unesite broj > ");
			if (scanner.hasNextLong()) {
				long number = scanner.nextLong();
				if (number >= 3 && number <= 20) {
					System.out.printf("%d! = %d%n", number, calculateFactorial(number));
				} else {
					System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", number);
				}
			} else {
				String inputString = scanner.next();
				if (inputString.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				} else {
					System.out.printf("'%s' nije cijeli broj.%n", inputString);					
				}
			}
		} 
		
		scanner.close();
	}

	/**
	 * Računa faktorijelu zadanog cijelog broja. Broj mora biti iz
	 * intervala [0,20]. Za brojeve iz nedozvoljenog intervala
	 * baca se iznimka {@link IllegalArgumentException}.
	 * 
	 * @param number broj za koji se računa faktorijela 
	 * @return		 faktorijela zadanog broja
	 */
	private static long calculateFactorial(long number) {
		if (number < 0 || number > 20) {
			throw new IllegalArgumentException("Broj nije iz dozvoljenog intervala.");
		}

		long result = 1;
		for (int i = 2; i <= number; i++) {
			result *= i;
		}

		return result;
	}

}