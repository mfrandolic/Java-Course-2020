package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryParserTest {
	
	@Test
	public void testParse() {
		QueryParser parser = new QueryParser("jmbag = \"0000000001\"");
		
		assertTrue(parser.isDirectQuery());
		assertEquals("0000000001", parser.getQueriedJMBAG());
		assertEquals(1, parser.getQuery().size());
		
		ConditionalExpression expr = parser.getQuery().get(0);
		assertEquals(FieldValueGetters.JMBAG, expr.getFieldGetter());
		assertEquals("0000000001", expr.getStringLiteral());
		assertEquals(ComparisonOperators.EQUALS, expr.getComparisonOperator());
	}
	
	@Test
	public void testParseWithMultipleExpressions() {
		QueryParser parser = 
			new QueryParser("jmbag = \"0000000001\" and lastName LIKE \"A*\" and firstName >= \"D\"");
		
		assertFalse(parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> {
			parser.getQueriedJMBAG();
		});
		assertEquals(3, parser.getQuery().size());
		
		ConditionalExpression expr1 = parser.getQuery().get(0);
		assertEquals(FieldValueGetters.JMBAG, expr1.getFieldGetter());
		assertEquals("0000000001", expr1.getStringLiteral());
		assertEquals(ComparisonOperators.EQUALS, expr1.getComparisonOperator());
		
		ConditionalExpression expr2 = parser.getQuery().get(1);
		assertEquals(FieldValueGetters.LAST_NAME, expr2.getFieldGetter());
		assertEquals("A*", expr2.getStringLiteral());
		assertEquals(ComparisonOperators.LIKE, expr2.getComparisonOperator());
		
		ConditionalExpression expr3 = parser.getQuery().get(2);
		assertEquals(FieldValueGetters.FIRST_NAME, expr3.getFieldGetter());
		assertEquals("D", expr3.getStringLiteral());
		assertEquals(ComparisonOperators.GREATER_OR_EQUALS, expr3.getComparisonOperator());
	}
	
	@Test
	public void testRepeatedExpressions() {
		QueryParser parser = 
				new QueryParser("jmbag = \"0000000001\" and jmbag = \"0000000001\" and jmbag = \"0000000002\"");
			
			assertFalse(parser.isDirectQuery());
			assertThrows(IllegalStateException.class, () -> {
				parser.getQueriedJMBAG();
			});
			assertEquals(3, parser.getQuery().size());
			
			ConditionalExpression expr1 = parser.getQuery().get(0);
			assertEquals(FieldValueGetters.JMBAG, expr1.getFieldGetter());
			assertEquals("0000000001", expr1.getStringLiteral());
			assertEquals(ComparisonOperators.EQUALS, expr1.getComparisonOperator());
			
			ConditionalExpression expr2 = parser.getQuery().get(1);
			assertEquals(FieldValueGetters.JMBAG, expr2.getFieldGetter());
			assertEquals("0000000001", expr2.getStringLiteral());
			assertEquals(ComparisonOperators.EQUALS, expr2.getComparisonOperator());
			
			ConditionalExpression expr3 = parser.getQuery().get(2);
			assertEquals(FieldValueGetters.JMBAG, expr3.getFieldGetter());
			assertEquals("0000000002", expr3.getStringLiteral());
			assertEquals(ComparisonOperators.EQUALS, expr3.getComparisonOperator());
	}
	
	@Test
	public void testParseWithWhitespace() {
		QueryParser parser = new QueryParser("  jmbag  LIKE  	\"0000000001\"	 \n ");
		
		assertFalse(parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> {
			parser.getQueriedJMBAG();
		});
		assertEquals(1, parser.getQuery().size());
		
		ConditionalExpression expr = parser.getQuery().get(0);
		assertEquals(FieldValueGetters.JMBAG, expr.getFieldGetter());
		assertEquals("0000000001", expr.getStringLiteral());
		assertEquals(ComparisonOperators.LIKE, expr.getComparisonOperator());
	}
	
	@Test
	public void testParseWithNoWhitespace() {
		QueryParser parser = new QueryParser("lastName<\"M\"");
		
		assertFalse(parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> {
			parser.getQueriedJMBAG();
		});
		assertEquals(1, parser.getQuery().size());
		
		ConditionalExpression expr = parser.getQuery().get(0);
		assertEquals(FieldValueGetters.LAST_NAME, expr.getFieldGetter());
		assertEquals("M", expr.getStringLiteral());
		assertEquals(ComparisonOperators.LESS, expr.getComparisonOperator());
	}

	@Test
	public void testParseEmptyQuery() {
		QueryParser parser = new QueryParser("");
		
		assertFalse(parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> {
			parser.getQueriedJMBAG();
		});
		assertEquals(0, parser.getQuery().size());
	}
	
	@Test
	public void testParseBlankQuery() {
		QueryParser parser = new QueryParser("	  	");
		
		assertFalse(parser.isDirectQuery());
		assertThrows(IllegalStateException.class, () -> {
			parser.getQueriedJMBAG();
		});
		assertEquals(0, parser.getQuery().size());
	}
	
	@Test
	public void testParseMultipleWildcards() {
		assertThrows(QueryParserException.class, () -> {
			new QueryParser("firstName LIKE \"*a*\"");
		});
	}
	
	@Test
	public void testParseWhenUnknownTokenType() {
		assertThrows(QueryParserException.class, () -> {
			new QueryParser("firstName * \"abc\"");
		});
	}
	
	@Test
	public void testParseWhenUnknownAttribute() {
		assertThrows(QueryParserException.class, () -> {
			new QueryParser("lastname LIKE \"A*\"");
		});
	}
	
	@Test
	public void testParseWhenAttributeExpected() {
		assertThrows(QueryParserException.class, () -> {
			new QueryParser("jmbag = \"0000000002\" and");
		});
	}
	
	@Test
	public void testParseWhenOperatorExpected() {
		assertThrows(QueryParserException.class, () -> {
			new QueryParser("jmbag");
		});
	}
	
	@Test
	public void testParseWhenStringLiteralExpected() {
		assertThrows(QueryParserException.class, () -> {
			new QueryParser("jmbag = ");
		});
	}
	
	@Test
	public void testParseWhenAndOperatorExpected() {
		assertThrows(QueryParserException.class, () -> {
			new QueryParser("jmbag = \"0000000002\" or");
		});
	}

}
