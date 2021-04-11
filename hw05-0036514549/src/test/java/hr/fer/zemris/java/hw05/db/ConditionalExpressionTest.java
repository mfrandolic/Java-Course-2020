package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionalExpressionTest {

	@Test
	public void testConditionalExpressionWhenSatisfies() {
		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);
		
		ConditionalExpression expr = new ConditionalExpression(
			FieldValueGetters.LAST_NAME,
			"Bos*",
			ComparisonOperators.LIKE
		);
		
		assertEquals("Bosnić", expr.getFieldGetter().get(record));
		assertEquals("Bos*", expr.getStringLiteral());
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
			expr.getFieldGetter().get(record),
			expr.getStringLiteral()             
		);
		
		assertTrue(recordSatisfies);
	}
	
	@Test
	public void testConditionalExpressionWhenNotSatisfies() {
		StudentRecord record = new StudentRecord("0000000002", "Bakamović", "Petra", 3);
		
		ConditionalExpression expr = new ConditionalExpression(
			FieldValueGetters.LAST_NAME,
			"Bos*",
			ComparisonOperators.LIKE
		);
		
		assertEquals("Bakamović", expr.getFieldGetter().get(record));
		assertEquals("Bos*", expr.getStringLiteral());
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
			expr.getFieldGetter().get(record),
			expr.getStringLiteral()             
		);
		
		assertFalse(recordSatisfies);
	}

}
