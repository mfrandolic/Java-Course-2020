package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program that allows encryption or decryption of the given file using AES
 * crypto-algorithm with the 128-bit encryption key and allows checking the
 * SHA-256 file digest. In the case of checking the SHA-256 file digest, the 
 * program expects "checksha" command (without quotes) and path to the file. 
 * In the case of AES encryption/decryption, the program expects "encrypt" or 
 * "decrypt" command (without quotes), path to the source file and path to the 
 * destination file.
 * 
 * @author Matija Frandolić
 */
public class Crypto {

	/**
	 * Main method of the program.
	 * 
	 * @param args command line arguments (usage: checksha filePath or 
	 *        encrypt|decrypt sourceFilePath destinationFilePath)
	 */
	public static void main(String[] args) {
		if (args.length != 2 && args.length != 3) {
			System.out.println("Invalid command format.");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		boolean encrypt = false;
		
		switch (args[0]) {
		case "checksha":
			if (args.length != 2) {
				System.out.println("Expected file name.");
				System.exit(1);
			}
			executeCheckshaCommand(args[1], sc);
			break;
		case "encrypt":
			encrypt = true;
			// fall through
		case "decrypt":
			if (args.length != 3) {
				System.out.println("Expected source and destination file names.");
				System.exit(1);
			}
			executeEncryptOrDecryptCommand(args[1], args[2], sc, encrypt);
			break;
		default:
			System.out.println("Unknown command.");
			break;
		}
		
		sc.close();
	}
	
	/**
	 * Compares the SHA-256 digest of the given file with the expected digest
	 * entered through the given {@code Scanner}.
	 * 
	 * @param file the string that represents path to the file
	 * @param sc   the scanner through which to communicate with the user
	 */
	private static void executeCheckshaCommand(String file, Scanner sc) {
		Path inputFilePath = Paths.get(file);
		if (!Files.exists(inputFilePath)) {
			System.out.println("File doesn't exist.");
			System.exit(1);
		}
		
		System.out.printf("Please provide expected sha-256 digest for %s:%n> ", file);
		String expectedDigest = sc.nextLine().trim();
		
		try (InputStream is = new BufferedInputStream(Files.newInputStream(inputFilePath))) {
			final int BUFFER_SIZE = 4096;
			byte[] buffer = new byte[BUFFER_SIZE];
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			
			int numberOfRead;
			while ((numberOfRead = is.read(buffer)) >= 1) {
				digest.update(buffer, 0, numberOfRead);
			}
			String actualDigest = Util.bytetohex(digest.digest());
			
			System.out.print("Digesting completed. Digest of " + file);
			if (expectedDigest.equals(actualDigest)) {
				System.out.println(" matches expected digest.");
			} else {
				System.out.println(" does not match the expected digest. "
						         + "Digest was: " + actualDigest);
			}
		} catch (IOException | NoSuchAlgorithmException e) {
			System.out.println("I/O error occurred.");
		}
	}
	

	/**
	 * Encrypts or decrypts the given source file and creates the destination file.
	 * If the destination file already exists, confirmation for overwriting is
	 * displayed. Password and initialization vector are entered through the given
	 * {@code Scanner} object. Encryption is performed if {@code encrypt} is set to 
	 * {@code true} and decryption is performed if {@code encrypt} is set to {@code false}.
	 * 
	 * @param sourceFile      the file which to encrypt or decrypt
	 * @param destinationFile the resulting file which is encrypted or decrypted 
	 *                        version of the original file
	 * @param sc              the scanner through which to communicate with the user
	 * @param encrypt         if set to {@code true}, encryption is performed,
	 *                        otherwise decryption is performed
	 */
	private static void executeEncryptOrDecryptCommand(String sourceFile, 
			String destinationFile, Scanner sc, boolean encrypt) {
		
		Path sourceFilePath = Paths.get(sourceFile);
		Path destinationFilePath = Paths.get(destinationFile);
		if (!Files.exists(sourceFilePath)) {
			System.out.println("Source file doesn't exist.");
			System.exit(1);
		}
		if (Files.exists(destinationFilePath)) {
			System.out.print("Destination file already exists. Do you want to "
					         + "overwrite it? [y/N] ");
			String input = sc.nextLine().toLowerCase();
			if (!input.equals("y")) {
				System.exit(1);
			}
		}
		
		SecretKeySpec keySpec = new SecretKeySpec(loadKeyOrIv("password", sc), "AES");
		AlgorithmParameterSpec paramSpec = 
			new IvParameterSpec(loadKeyOrIv("initialization vector", sc));
		
		try (InputStream is = new BufferedInputStream(Files.newInputStream(sourceFilePath));
			 OutputStream os = new BufferedOutputStream(Files.newOutputStream(destinationFilePath))) {
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			
			final int BUFFER_SIZE = 4096;
			byte[] buffer = new byte[BUFFER_SIZE];
			
			int numberOfRead;
			while ((numberOfRead = is.read(buffer)) >= 1) {
				os.write(cipher.update(buffer, 0, numberOfRead));
			}
			os.write(cipher.doFinal());
			os.flush();
			
			System.out.printf("%s completed. Generated file %s based on file %s.%n",
				encrypt ? "Encryption" : "Decryption", destinationFile, sourceFile);
		} catch (IOException | GeneralSecurityException e) {
			System.out.println("I/O or encryption/decryption error occurred.");	
		}
	}
	
	/**
	 * Returns the byte array representing either password or initialization 
	 * vector for the AES encryption/decryption. Parameter {@code message} is
	 * used to display message about the expected input which is entered through
	 * the given {@code Scanner} object. 
	 * 
	 * @param message the string that represents message about the expected input
	 * @param sc      the scanner through which to communicate with the user
	 * @return        the byte array representing either password or initialization 
	 *                vector for the AES encryption/decryption
	 */
	private static byte[] loadKeyOrIv(String message, Scanner sc) {
		String keyOrIvText = null;
		
		try {
			System.out.printf("Please provide %s as hex-encoded text (16 bytes, i.e. "
					          + "32 hex-digits):%n> ", message);
			keyOrIvText = sc.nextLine().trim();
			if (keyOrIvText.length() != 32) {
				System.out.println("Invalid " + message + " length.");
				System.exit(1);
			}
		} catch (IllegalArgumentException e) {
			System.out.println("Given " + message + " is not a valid hex string.");
			System.exit(1);
		}
		
		return Util.hextobyte(keyOrIvText);
	}
	
}
