package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {
	
	@Test
	public void testForJMBAG() {
		List<String> recordList = new ArrayList<>();
		recordList.add("0000000001	Akšamović	Marin	2");
		recordList.add("0000000002	Bakamović	Petra	3");
		recordList.add("0000000003	Bosnić	Andrea	4");
		recordList.add("0000000004	Božić	Marin	5");
		recordList.add("0000000005	Brezović	Jusufadis	2");
	
		StudentDatabase db = new StudentDatabase(recordList);
		
		StudentRecord actual = db.forJMBAG("0000000002");
		StudentRecord expected = new StudentRecord("0000000002", "Bakamović", "Petra", 3);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFilterWhenAlwaysTrue() {
		List<String> recordList = new ArrayList<>();
		recordList.add("0000000001	Akšamović	Marin	2");
		recordList.add("0000000002	Bakamović	Petra	3");
		recordList.add("0000000003	Bosnić	Andrea	4");
		recordList.add("0000000004	Božić	Marin	5");
		recordList.add("0000000005	Brezović	Jusufadis	2");
	
		StudentDatabase db = new StudentDatabase(recordList);
		
		List<StudentRecord> actual = db.filter((record) -> true);
		List<StudentRecord> expected = new ArrayList<>();
		expected.add(new StudentRecord("0000000001", "Akšamović", "Marin", 2));
		expected.add(new StudentRecord("0000000002", "Bakamović", "Petra", 3));
		expected.add(new StudentRecord("0000000003", "Bosnić", "Andrea", 4));
		expected.add(new StudentRecord("0000000004", "Božić", "Marin", 5));
		expected.add(new StudentRecord("0000000005", "Brezović", "Jusufadis", 2));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testFilterWhenAlwaysFalse() {
		List<String> recordList = new ArrayList<>();
		recordList.add("0000000001	Akšamović	Marin	2");
		recordList.add("0000000002	Bakamović	Petra	3");
		recordList.add("0000000003	Bosnić	Andrea	4");
		recordList.add("0000000004	Božić	Marin	5");
		recordList.add("0000000005	Brezović	Jusufadis	2");
	
		StudentDatabase db = new StudentDatabase(recordList);
		
		List<StudentRecord> actual = db.filter((record) -> false);
		assertEquals(0, actual.size());
	}
	
	@Test
	public void testTooManyColumns() {
		List<String> recordList = new ArrayList<>();
		recordList.add("0000000001	Akšamović	Marin	2  abc   abc");
		recordList.add("0000000002	Bakamović	Petra	3");
		recordList.add("0000000003	Bosnić	Andrea	4");
		recordList.add("0000000004	Božić	Marin	5");
		recordList.add("0000000005	Brezović	Jusufadis	2");
				
		assertThrows(IllegalArgumentException.class, () -> {
			new StudentDatabase(recordList);
		});
	}
	
	@Test
	public void testGradeNotInteger() {
		List<String> recordList = new ArrayList<>();
		recordList.add("0000000001	Akšamović	Marin	2");
		recordList.add("0000000002	Bakamović	Petra	3");
		recordList.add("0000000003	Bosnić	Andrea	4");
		recordList.add("0000000004	Božić	Marin	5.8");
		recordList.add("0000000005	Brezović	Jusufadis	2");
				
		assertThrows(IllegalArgumentException.class, () -> {
			new StudentDatabase(recordList);
		});
	}
	
	@Test
	public void testInvalidGrade() {
		List<String> recordList = new ArrayList<>();
		recordList.add("0000000001	Akšamović	Marin	2");
		recordList.add("0000000002	Bakamović	Petra	3");
		recordList.add("0000000003	Bosnić	Andrea	4");
		recordList.add("0000000004	Božić	Marin	0");
		recordList.add("0000000005	Brezović	Jusufadis	2");
				
		assertThrows(IllegalArgumentException.class, () -> {
			new StudentDatabase(recordList);
		});
	}
	
	@Test
	public void testDuplicateJMBAGs() {
		List<String> recordList = new ArrayList<>();
		recordList.add("0000000001	Akšamović	Marin	2");
		recordList.add("0000000002	Bakamović	Petra	3");
		recordList.add("0000000003	Bosnić	Andrea	4");
		recordList.add("0000000004	Božić	Marin	5");
		recordList.add("0000000004	Brezović	Jusufadis	2");
				
		assertThrows(IllegalArgumentException.class, () -> {
			new StudentDatabase(recordList);
		});
	}

}
