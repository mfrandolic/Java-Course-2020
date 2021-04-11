package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValueWrapperTest {
	
	private final double PRECISION = 1E-10;
	
	@Test
	public void testAddIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		v1.add(v2.getValue());
		
		assertEquals(3, v1.getValue());
		assertEquals(2, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testAddIntegerAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
		v1.add(v2.getValue());
		
		assertEquals(3.5, v1.getValue());
		assertEquals(2.5, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void testAddDoubleAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(3.3));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
		v1.add(v2.getValue());
		
		assertEquals(5.8, (Double) v1.getValue(), PRECISION);
		assertEquals(2.5, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void testAddIntegerAndNull() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		
		assertEquals(1, v1.getValue());
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
	}
	
	@Test
	public void testAddDoubleAndNull() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(2.5));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		
		assertEquals(2.5, (Double) v1.getValue(), PRECISION);
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Double);
	}
	
	@Test
	public void testAddNullAndInteger() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());
		
		assertEquals(1, v1.getValue());
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testAddNullAndDouble() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
		v1.add(v2.getValue());
		
		assertEquals(2.5, (Double) v1.getValue(), PRECISION);
		assertEquals(2.5, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}

	@Test
	public void testAddNullAndNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
	}
	
	@Test
	public void testAddStringThatIsDoubleAndInteger() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());
		
		assertEquals(13.0, (Double) v1.getValue(), PRECISION);
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testAddStringThatIsIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());
		
		assertEquals(13, v1.getValue());
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testAddStringThatIsNotNumberAndInteger() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> {
			v1.add(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof String);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testAddBooleanAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Boolean.valueOf(true));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(5));

		assertThrows(RuntimeException.class, () -> {
			v1.add(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof Boolean);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testAddIntegerAndBoolean() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper(Boolean.valueOf(true));

		assertThrows(RuntimeException.class, () -> {
			v1.add(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Boolean);
	}
	
	@Test
	public void testSubtractIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		v1.subtract(v2.getValue());
		
		assertEquals(-1, v1.getValue());
		assertEquals(2, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testSubtractIntegerAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
		v1.subtract(v2.getValue());
		
		assertEquals(-1.5, v1.getValue());
		assertEquals(2.5, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void testSubtractDoubleAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(3.3));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
		v1.subtract(v2.getValue());
		
		assertEquals(0.8, (Double) v1.getValue(), PRECISION);
		assertEquals(2.5, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void testSubtractIntegerAndNull() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue());
		
		assertEquals(1, v1.getValue());
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
	}
	
	@Test
	public void testSubtractDoubleAndNull() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(2.5));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue());
		
		assertEquals(2.5, (Double) v1.getValue(), PRECISION);
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Double);
	}
	
	@Test
	public void testSubtractNullAndInteger() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.subtract(v2.getValue());
		
		assertEquals(-1, v1.getValue());
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testSubtractNullAndDouble() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
		v1.subtract(v2.getValue());
		
		assertEquals(-2.5, (Double) v1.getValue(), PRECISION);
		assertEquals(2.5, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}

	@Test
	public void testSubtractNullAndNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
	}
	
	@Test
	public void testSubtractStringThatIsDoubleAndInteger() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.subtract(v2.getValue());
		
		assertEquals(11.0, (Double) v1.getValue(), PRECISION);
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testSubtractStringThatIsIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.subtract(v2.getValue());
		
		assertEquals(11, v1.getValue());
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testSubtractStringThatIsNotNumberAndInteger() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> {
			v1.subtract(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof String);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testSubtractBooleanAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Boolean.valueOf(true));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(5));

		assertThrows(RuntimeException.class, () -> {
			v1.subtract(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof Boolean);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testSubtractIntegerAndBoolean() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper(Boolean.valueOf(true));

		assertThrows(RuntimeException.class, () -> {
			v1.subtract(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Boolean);
	}
	
	@Test
	public void testMultiplyIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		v1.multiply(v2.getValue());
		
		assertEquals(2, v1.getValue());
		assertEquals(2, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testMultiplyIntegerAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
		v1.multiply(v2.getValue());
		
		assertEquals(2.5, v1.getValue());
		assertEquals(2.5, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void testMultiplyDoubleAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(3.1));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.0));
		v1.multiply(v2.getValue());
		
		assertEquals(6.2, (Double) v1.getValue(), PRECISION);
		assertEquals(2.0, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void testMultiplyIntegerAndNull() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.multiply(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
	}
	
	@Test
	public void testMultiplyDoubleAndNull() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(2.5));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.multiply(v2.getValue());
		
		assertEquals(0.0, (Double) v1.getValue(), PRECISION);
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Double);
	}
	
	@Test
	public void testMultiplyNullAndInteger() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.multiply(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testMultiplyNullAndDouble() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
		v1.multiply(v2.getValue());
		
		assertEquals(0.0, (Double) v1.getValue(), PRECISION);
		assertEquals(2.5, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}

	@Test
	public void testMultiplyNullAndNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.multiply(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
	}
	
	@Test
	public void testMultiplyStringThatIsDoubleAndInteger() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.multiply(v2.getValue());
		
		assertEquals(12.0, (Double) v1.getValue(), PRECISION);
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testMultiplyStringThatIsIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.multiply(v2.getValue());
		
		assertEquals(12, v1.getValue());
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testMultiplyStringThatIsNotNumberAndInteger() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> {
			v1.multiply(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof String);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testMultiplyBooleanAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Boolean.valueOf(true));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(5));

		assertThrows(RuntimeException.class, () -> {
			v1.multiply(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof Boolean);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testMultiplyIntegerAndBoolean() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper(Boolean.valueOf(true));

		assertThrows(RuntimeException.class, () -> {
			v1.multiply(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Boolean);
	}
	
	@Test
	public void testDivideIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		v1.divide(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(2, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testDivideIntegerAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.0));
		v1.divide(v2.getValue());
		
		assertEquals(1.5, v1.getValue());
		assertEquals(2.0, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void testDivideDoubleAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(3.0));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.0));
		v1.divide(v2.getValue());
		
		assertEquals(1.5, (Double) v1.getValue(), PRECISION);
		assertEquals(2.0, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void testDivideIntegerAndNull() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.divide(v2.getValue());
		
		assertEquals(Integer.MAX_VALUE, v1.getValue());
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
	}
	
	@Test
	public void testDivideDoubleAndNull() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(2.5));
		ValueWrapper v2 = new ValueWrapper(null);
		v1.divide(v2.getValue());
		
		assertEquals(Double.POSITIVE_INFINITY, v1.getValue());
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Double);
	}
	
	@Test
	public void testDivideNullAndInteger() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.divide(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testDivideNullAndDouble() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
		v1.divide(v2.getValue());
		
		assertEquals(0.0, (Double) v1.getValue(), PRECISION);
		assertEquals(2.5, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}

	@Test
	public void testDivideNullAndNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.divide(v2.getValue());
		
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
	}
	
	@Test
	public void testDivideStringThatIsDoubleAndInteger() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.divide(v2.getValue());
		
		assertEquals(12.0, (Double) v1.getValue(), PRECISION);
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testDivideStringThatIsIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.divide(v2.getValue());
		
		assertEquals(12, v1.getValue());
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testDivideStringThatIsNotNumberAndInteger() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> {
			v1.divide(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof String);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testDivideBooleanAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Boolean.valueOf(true));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(5));

		assertThrows(RuntimeException.class, () -> {
			v1.divide(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof Boolean);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testDivideIntegerAndBoolean() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper(Boolean.valueOf(true));

		assertThrows(RuntimeException.class, () -> {
			v1.divide(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Boolean);
	}
	
	@Test
	public void testNumCompareIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
		
		assertEquals(1, v1.getValue());
		assertEquals(2, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testNumCompareIntegerAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(3));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.0));
		
		assertTrue(v1.numCompare(v2.getValue()) > 0);
		
		assertEquals(3, v1.getValue());
		assertEquals(2.0, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void testNumCompareDoubleAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(3.0));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.0));
		
		assertTrue(v1.numCompare(v2.getValue()) > 0);
		
		assertEquals(3.0, (Double) v1.getValue(), PRECISION);
		assertEquals(2.0, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v1.getValue() instanceof Double);
		assertTrue(v2.getValue() instanceof Double);
	}
	
	@Test
	public void testNumCompareIntegerAndNull() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(null);
		
		assertTrue(v1.numCompare(v2.getValue()) > 0);
		
		assertEquals(1, v1.getValue());
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Integer);
	}
	
	@Test
	public void testNumCompareDoubleAndNull() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(2.5));
		ValueWrapper v2 = new ValueWrapper(null);
		
		assertTrue(v1.numCompare(v2.getValue()) > 0);
		
		assertEquals(2.5, (Double) v1.getValue(), PRECISION);
		assertEquals(null, v2.getValue());
		
		assertTrue(v1.getValue() instanceof Double);
	}
	
	@Test
	public void testNumCompareNullAndInteger() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
		
		assertEquals(null, v1.getValue());
		assertEquals(1, v2.getValue());
		
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testNumCompareNullAndDouble() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
		
		assertEquals(null, v1.getValue());
		assertEquals(2.5, (Double) v2.getValue(), PRECISION);
		
		assertTrue(v2.getValue() instanceof Double);
	}

	@Test
	public void testNumCompareNullAndNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);

		assertEquals(0, v1.numCompare(v2.getValue()));
		
		assertEquals(null, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testNumCompareStringThatIsDoubleAndInteger() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		
		assertTrue(v1.numCompare(v2.getValue()) > 0);
		
		assertEquals("1.2E1", v1.getValue());
		assertEquals(1, v2.getValue());
		
		assertTrue(v1.getValue() instanceof String);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testNumCompareStringThatIsIntegerAndInteger() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(12));
		
		assertEquals(0, v1.numCompare(v2.getValue()));
		
		assertEquals("12", v1.getValue());
		assertEquals(12, v2.getValue());
		
		assertTrue(v1.getValue() instanceof String);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testNumCompareStringThatIsNotNumberAndInteger() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> {
			v1.numCompare(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof String);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testNumCompareBooleanAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Boolean.valueOf(true));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(5));

		assertThrows(RuntimeException.class, () -> {
			v1.numCompare(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof Boolean);
		assertTrue(v2.getValue() instanceof Integer);
	}
	
	@Test
	public void testNumCompareIntegerAndBoolean() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper(Boolean.valueOf(true));

		assertThrows(RuntimeException.class, () -> {
			v1.numCompare(v2.getValue());			
		});
		
		assertTrue(v1.getValue() instanceof Integer);
		assertTrue(v2.getValue() instanceof Boolean);
	}

}
