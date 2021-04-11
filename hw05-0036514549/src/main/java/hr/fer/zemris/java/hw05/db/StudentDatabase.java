package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a database of {@code StudentRecord}s. 
 * 
 * @author Matija Frandolić
 */
public class StudentDatabase {

	/**
	 * List of records.
	 */
	private List<StudentRecord> records;
	
	/**
	 * Map of records with JMBAG as key.
	 */
	private Map<String, StudentRecord> jmbagIndex;
	
	/**
	 * Constructs a new {@code StudentDatabase} from the given list of strings
	 * which represent lines of the database file.
	 * 
	 * @param databaseFileRows the list of strings which represent lines of the
	 *                         database file
	 * @throws IllegalArgumentException if there are duplicate JMBAGs or if the
	 *                                  grade or line format is invalid
	 */
	public StudentDatabase(List<String> databaseFileRows) {
		records = new ArrayList<StudentRecord>();
		jmbagIndex = new HashMap<String, StudentRecord>();
		
		int currentRow = 1;
		for (String row : databaseFileRows) {
			String[] rowElements = row.split("\\s+");
			
			if (rowElements.length != 4 && rowElements.length != 5) {
				throw new IllegalArgumentException("Invalid number of columns in row " + currentRow);
			}
			
			String jmbag = rowElements[0];
			if (jmbagIndex.containsKey(jmbag)) {
				throw new IllegalArgumentException("Duplicate JMBAG in row " + currentRow);
			}
			
			String lastName = rowElements[1];
			String firstName = rowElements[2];
			String finalGradeString = rowElements[3];
			
			if (rowElements.length == 5) {
				lastName = rowElements[1] + " " + rowElements[2];
				firstName = rowElements[3];
				finalGradeString = rowElements[4];
			}
			
			int finalGrade;	
			try {
				finalGrade = Integer.parseInt(finalGradeString);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid grade in row " + currentRow);
			}
			
			if (finalGrade < 1 || finalGrade > 5) {
				throw new IllegalArgumentException("Invalid grade in row " + currentRow);
			}
			
			StudentRecord newRecord = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			records.add(newRecord);
			jmbagIndex.put(jmbag, newRecord);
			currentRow++;
		}
	}
	
	/**
	 * Returns the record that matches the given JMBAG or {@code null} if that 
	 * record doesn't exist. This method operates in O(1) complexity.
	 * 
	 * @param jmbag the JMBAG of the record that is searched for in the database
	 * @return      the record that matches the given JMBAG or {@code null} if
	 *              that record doesn't exist
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return jmbagIndex.get(jmbag);
	}
	
	/**
	 * Returns the list of {@code StudentRecord}s that are filtered from the 
	 * database based on the acceptance of the {@code IFilter} object.
	 * 
	 * @param filter the {@code IFilter} object that is used to filter the records
	 * @return       the list of {@code StudentRecord}s that were accepted by the filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredRecords = new ArrayList<>();
		
		for (StudentRecord record : records) {
			if (filter.accepts(record)) {
				filteredRecords.add(record);
			}
		}
		
		return filteredRecords;
	}
	
}
