package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class QueryFilterTest {

	@Test
	public void testAcceptsWhenSatisfiesAll() {
		List<ConditionalExpression> expressions = new ArrayList<>();;
		expressions.add(new ConditionalExpression(
				FieldValueGetters.FIRST_NAME, "M*", ComparisonOperators.LIKE));
		expressions.add(new ConditionalExpression(
				FieldValueGetters.LAST_NAME, "*ić", ComparisonOperators.LIKE));
		expressions.add(new ConditionalExpression(
				FieldValueGetters.JMBAG, "0", ComparisonOperators.GREATER));
		
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		
		QueryFilter queryFilter = new QueryFilter(expressions);
		assertTrue(queryFilter.accepts(record));
	}
	
	@Test
	public void testAcceptsWhenSatisfiesSome() {
		List<ConditionalExpression> expressions = new ArrayList<>();;
		expressions.add(new ConditionalExpression(
				FieldValueGetters.FIRST_NAME, "A*", ComparisonOperators.LIKE));
		expressions.add(new ConditionalExpression(
				FieldValueGetters.LAST_NAME, "*ić", ComparisonOperators.LIKE));
		expressions.add(new ConditionalExpression(
				FieldValueGetters.JMBAG, "0", ComparisonOperators.GREATER));
		
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		
		QueryFilter queryFilter = new QueryFilter(expressions);
		assertFalse(queryFilter.accepts(record));
	}
	
	@Test
	public void testAcceptsWhenSatisfiesNone() {
		List<ConditionalExpression> expressions = new ArrayList<>();;
		expressions.add(new ConditionalExpression(
				FieldValueGetters.FIRST_NAME, "A*", ComparisonOperators.LIKE));
		expressions.add(new ConditionalExpression(
				FieldValueGetters.LAST_NAME, "B*", ComparisonOperators.LIKE));
		expressions.add(new ConditionalExpression(
				FieldValueGetters.JMBAG, "0000000002", ComparisonOperators.EQUALS));
		
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		
		QueryFilter queryFilter = new QueryFilter(expressions);
		assertFalse(queryFilter.accepts(record));
	}

}
