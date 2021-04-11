package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FieldValueGettersTest {
	
	@Test
	public void testGetFirstName() {
		StudentRecord record1 = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		StudentRecord record2 = new StudentRecord("0000000002", "Bakamović", "Petra", 3);
		StudentRecord record3 = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);
		
		assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(record1));
		assertEquals("Petra", FieldValueGetters.FIRST_NAME.get(record2));
		assertEquals("Andrea", FieldValueGetters.FIRST_NAME.get(record3));
	}
	
	@Test
	public void testGetLastName() {
		StudentRecord record1 = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		StudentRecord record2 = new StudentRecord("0000000002", "Bakamović", "Petra", 3);
		StudentRecord record3 = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);
		
		assertEquals("Akšamović", FieldValueGetters.LAST_NAME.get(record1));
		assertEquals("Bakamović", FieldValueGetters.LAST_NAME.get(record2));
		assertEquals("Bosnić", FieldValueGetters.LAST_NAME.get(record3));
	}
	
	@Test
	public void testGetJMBAG() {
		StudentRecord record1 = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		StudentRecord record2 = new StudentRecord("0000000002", "Bakamović", "Petra", 3);
		StudentRecord record3 = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);
		
		assertEquals("0000000001", FieldValueGetters.JMBAG.get(record1));
		assertEquals("0000000002", FieldValueGetters.JMBAG.get(record2));
		assertEquals("0000000003", FieldValueGetters.JMBAG.get(record3));
	}

}
