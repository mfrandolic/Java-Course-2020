package hr.fer.zemris.java.hw05.db;

/**
 * This class offers {@code IFieldValueGetter}s for the first name, last name
 * and JMBAG of {@code StudentRecord}. 
 * 
 * @author Matija Frandolić
 */
public class FieldValueGetters {

	/**
	 * First name field getter.
	 */
	public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;
	
	/**
	 * Last name field getter.
	 */
	public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;
	
	/**
	 * JMBAG field getter
	 */
	public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
	
}
