package hr.fer.zemris.java.hw05.db;

/**
 * This class represents a record for one student.
 * 
 * @author Matija Frandolić
 */
public class StudentRecord {

	/**
	 * JMBAG of the student.
	 */
	private String jmbag;
	
	/**
	 * Last name of the student.
	 */
	private String lastName;
	
	/**
	 * First name of the student.
	 */
	private String firstName;
	
	/**
	 * Final grade of the student.
	 */
	private int finalGrade;

	/**
	 * Constructs a new {@code StudentRecord} from the given JMBAG, last name,
	 * first name and final grade.
	 * 
	 * @param jmbag      JMBAG of the student
	 * @param lastName   last name of the student
	 * @param firstName  first name of the student
	 * @param finalGrade final grade of the student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns the JMBAG of the student from this record.
	 * 
	 * @return the JMBAG of the student from this record
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns the last name of the student from this record.
	 * 
	 * @return the last name of the student from this record
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the first name of the student from this record.
	 * 
	 * @return the first name of the student from this record
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the final grade of the student from this record.
	 * 
	 * @return the final grade of the student from this record
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Checks whether this record is equal to the given object. Two records
	 * are treated as equal if JMBAGs are equal.
	 * 
	 * @param obj the object for which to compare equality with this record
	 * @return    {@code true} if records are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;			
		}
		if (obj == null) {
			return false;			
		}
		if (getClass() != obj.getClass()) {
			return false;			
		}
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null) {
				return false;				
			}
		} else if (!jmbag.equals(other.jmbag)) {
			return false;			
		}
		return true;
	}

}
