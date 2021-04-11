package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji na temelju unesene širine i visine pravokutnika računa i 
 * ispisuje njegovu površinu i opseg. 
 * 
 * @author Matija Frandolić
 * @version 1.0
 */
public class Rectangle {

	/**
	 * Glavna metoda koja se poziva pri pokretanju programa. Kao argumenti
	 * iz komandne linije očekuju se širina i visina pravokutnika, zadani
	 * kao realni brojevi s decimalnom točkom. Ako se program pokrene bez 
	 * argumenata, program će tražiti korisnika da preko tipkovnice upiše 
	 * širinu i visinu.
	 * 
	 * @param args argumenti iz komandne linije (širina i visina pravokutnika)
	 */
	public static void main(String[] args) {
		switch (args.length) {
			case 2:
				try {
					double width = Double.parseDouble(args[0]);
					double height = Double.parseDouble(args[1]);
					if (width > 0 && height > 0) {
						printAreaAndPerimeter(width, height);						
					} else {
						System.out.println("Širina i/ili visina nisu valjano zadani.");
					}
				} catch (NumberFormatException ex) {
					System.out.println("Predani argumenti nisu realni brojevi.");
				}
				break;	
			case 0:
				Scanner scanner = new Scanner(System.in);
				double width = askForInput("Unesite širinu > ", scanner);
				double height = askForInput("Unesite visinu > ", scanner);
				printAreaAndPerimeter(width, height);
				scanner.close();
				break;
			default:
				System.out.println("Neočekivan broj argumenata.");
				break;
		}
	}
	
	/** 
	 * Ispisuje poruku i čita redak preko skenera sve dok se 
	 * ne pročita pozitivni realni broj. Ako se traži unos od 
	 * korisnika preko tipkovnice, <code>Scanner</code> treba 
	 * omotati oko <code>System.in</code>.
	 * 
	 * @param message poruka koja se ispisuje pri traženju unosa
	 * @param scanner skener preko kojeg se čita unos 
	 * @return		  pročitani realni broj
	 */
	private static double askForInput(String message, Scanner scanner) {
		while (true) {
			System.out.print(message);
			String inputString = scanner.nextLine().trim();
			try {
				double value = Double.parseDouble(inputString);
				if (value < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				} else if (value == 0) {
					System.out.println("Unijeli ste nulu.");
				} else {
					return value;
				}
			} catch (NumberFormatException ex) {
				System.out.printf("'%s' se ne može protumačiti kao broj.%n", inputString);
			}
		}
	}

	/**
	 * Računa površinu pravokutnika na temelju zadane širine i visine.
	 * 
	 * @param width  širina pravokutnika
	 * @param height visina pravokutnika
	 * @return		 površina pravokutnika
	 */
	private static double calculateArea(double width, double height) {
		return width * height;
	}

	/**
	 * Računa opseg pravokutnika na temelju zadane širine i visine.
	 * 
	 * @param width	 širina pravokutnika
	 * @param height visina pravokutnika
	 * @return		 opseg pravokutnika
	 */
	private static double calculatePerimeter(double width, double height) {
		return 2 * width + 2 * height;
	}

	/**
	 * Ispisuje poruku o površini i opsegu pravokutnika koje računa
	 * na temelju zadane širine i visine.
	 * 
	 * @param width	 širina pravokutnika
	 * @param height visina pravokutnika
	 */
	private static void printAreaAndPerimeter(double width, double height) {
		System.out.printf(
			"Pravokutnik širine %s i visine %s ima površinu %s te opseg %s.%n",
			width, height, calculateArea(width, height), calculatePerimeter(width, height)
		);
	}

}