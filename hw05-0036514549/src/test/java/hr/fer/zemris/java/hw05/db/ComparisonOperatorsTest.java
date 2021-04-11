package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	@Test
	public void testLess() {
		assertTrue(ComparisonOperators.LESS.satisfied("Ana", "Jasna"));
		assertFalse(ComparisonOperators.LESS.satisfied("Ana", "Ana"));
		assertTrue(ComparisonOperators.LESS.satisfied("Ana", "ana"));
		assertFalse(ComparisonOperators.LESS.satisfied("Ana", "An"));
	}
	
	@Test
	public void testLessOrEquals() {
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana", "Jasna"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana", "Ana"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana", "ana"));
		assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana", "An"));
	}
	
	@Test
	public void testGreater() {
		assertTrue(ComparisonOperators.GREATER.satisfied("Jasna", "Ana"));
		assertFalse(ComparisonOperators.GREATER.satisfied("Ana", "Ana"));
		assertTrue(ComparisonOperators.GREATER.satisfied("ana", "Ana"));
		assertFalse(ComparisonOperators.GREATER.satisfied("An", "Ana"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Jasna", "Ana"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Ana", "Ana"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("ana", "Ana"));
		assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("An", "Ana"));
	}
	
	@Test
	public void testEquals() {
		assertTrue(ComparisonOperators.EQUALS.satisfied("Jasna", "Jasna"));
		assertTrue(ComparisonOperators.EQUALS.satisfied("Ana", "Ana"));
		assertFalse(ComparisonOperators.EQUALS.satisfied("ana", "Ana"));
		assertFalse(ComparisonOperators.EQUALS.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testNotEquals() {
		assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Jasna", "Jasna"));
		assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Ana", "Ana"));
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("ana", "Ana"));
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testLike() {
		assertFalse(ComparisonOperators.LIKE.satisfied("Zagreb", "Aba*"));
		assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Abc", "A*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Abc", "*c"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Abc", "*bc"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Abc", "A*c"));
		assertTrue(ComparisonOperators.LIKE.satisfied("Abc", "Abc"));
		assertFalse(ComparisonOperators.LIKE.satisfied("Abc", "abc"));
		assertThrows(IllegalArgumentException.class, () -> {
			ComparisonOperators.LIKE.satisfied("Abc", "**");			
		});
	}

}
