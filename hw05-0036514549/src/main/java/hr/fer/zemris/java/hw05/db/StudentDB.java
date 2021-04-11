package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A program that acts as a simple database emulator. Program reads the data
 * from the current directory from the file named "database.txt". 
 * 
 * @author Matija Frandolić
 */
public class StudentDB {

	/**
	 * Main method of the program.
	 * 
	 * @param  args        command line arguments (not used)
	 * @throws IOException if I/O exception occurs
	 */
	public static void main(String[] args) throws IOException {
		List<String> databaseFileRows = Files.readAllLines(
		    Paths.get("./database.txt"), 
		    StandardCharsets.UTF_8
		);
		
		StudentDatabase db;
		try {
			db = new StudentDatabase(databaseFileRows);			
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			System.out.print("> ");
			String line = scanner.nextLine().trim();
			
			if (line.startsWith("query")) {
				processQuery(line.substring("query".length()), db);
			} else if (line.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			} else {
				System.out.println("Unknown command.");
			}
			
			System.out.println();
		}
		
		scanner.close();
	}
	
	/**
	 * Processes the given query by filtering the records from the given 
	 * database and displaying the formatted result.
	 * 
	 * @param query the string that represents the query
	 * @param db    the {@code StudentDatabase} object from which to extract the records
	 */
	private static void processQuery(String query, StudentDatabase db) {
		if (query.isBlank()) {
			System.out.println("Query command cannot be empty.");
			return;
		}
		
		QueryParser parser;
		try {
			parser = new QueryParser(query);			
		} catch (QueryParserException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		if (parser.isDirectQuery()) {
			System.out.println("Using index for record retrieval.");
		}
		
		List<StudentRecord> records = filterRecords(db, parser);
		List<String> output = formatOutput(records);
		output.forEach(System.out::println);
		
		System.out.println("Records selected: " + records.size());
	}
	
	/**
	 * Returns the list of {@code StudentRecord}s created by filtering the
	 * records from the given database that match the query from the given parser.
	 * 
	 * @param db     the {@code StudentDatabase} object from which to extract the records
	 * @param parser the {@code QueryParser} object by which to query
	 * @return       the list of filtered {@code StudentRecord}s
	 */
	private static List<StudentRecord> filterRecords(StudentDatabase db, QueryParser parser) {
		List<StudentRecord>	records;
		
		if (parser.isDirectQuery()) {
			records = new ArrayList<StudentRecord>();
			StudentRecord record = db.forJMBAG(parser.getQueriedJMBAG());
			if (record != null) {
				records.add(record);				
			}
		} else {
			records = db.filter(new QueryFilter(parser.getQuery()));
		}
		
		return records;
	}
	
	/**
	 * Returns the list of strings that represents formatted output of the
	 * given list of {@code StudentRecord}s, line by line.
	 * 
	 * @param records the list of {@code StudentRecord}s that needs to be formatted
	 * @return        the list of strings that represents formatted output of the
	 *                given list of records, line by line
	 */
	private static List<String> formatOutput(List<StudentRecord> records) {
		List<String> output = new ArrayList<>();
		if (records.isEmpty()) {
			return output;
		}
		
		int longestJMBAG = 0;
		int longestLastName = 0;
		int longestFirstName = 0;
		
		for (StudentRecord record : records) {
			if (record.getJmbag().length() > longestJMBAG) {
				longestJMBAG = record.getJmbag().length();
			}
			if (record.getLastName().length() > longestLastName) {
				longestLastName = record.getLastName().length();
			}
			if (record.getFirstName().length() > longestFirstName) {
				longestFirstName = record.getFirstName().length();
			}
		}
				
		String border = "+" + "=".repeat(longestJMBAG + 2) +
		                "+" + "=".repeat(longestLastName + 2) +
		                "+" + "=".repeat(longestFirstName + 2) + "+===+";
		output.add(border);
		
		for (StudentRecord record : records) {
			StringBuilder sb = new StringBuilder();
			
			sb.append("| ")
			  .append(record.getJmbag())
			  .append(" ".repeat(longestJMBAG - record.getJmbag().length()))
			  .append(" | ")
			  .append(record.getLastName())
			  .append(" ".repeat(longestLastName - record.getLastName().length()))
			  .append(" | ")
			  .append(record.getFirstName())
			  .append(" ".repeat(longestFirstName - record.getFirstName().length()))
			  .append(" | ")
			  .append(record.getFinalGrade())
			  .append(" |");
			
			output.add(sb.toString());
		}
		
		output.add(border);
		return output;
	}
	
}
