package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {
	
	private static final double PRECISION = 1E-8;

	@Test
	void constructor() {
		ComplexNumber c = new ComplexNumber(-1, 1);
		
		assertEquals(-1, c.getReal(), PRECISION);
		assertEquals(1, c.getImaginary(), PRECISION);
		assertEquals(Math.sqrt(2), c.getMagnitude(), PRECISION);
		assertEquals(3 * Math.PI / 4, c.getAngle(), PRECISION);
	}
	
	@Test
	void fromReal() {
		ComplexNumber c = ComplexNumber.fromReal(2.62);
		
		assertEquals(2.62, c.getReal(), PRECISION);
		assertEquals(0, c.getImaginary(), PRECISION);
		assertEquals(2.62, c.getMagnitude(), PRECISION);
		assertEquals(0, c.getAngle(), PRECISION);
	}
	
	@Test
	void fromImaginary() {
		ComplexNumber c = ComplexNumber.fromImaginary(-5.334);
		
		assertEquals(0, c.getReal(), PRECISION);
		assertEquals(-5.334, c.getImaginary(), PRECISION);
		assertEquals(5.334, c.getMagnitude(), PRECISION);
		assertEquals(3 * Math.PI / 2, c.getAngle(), PRECISION);
	}
	
	@Test
	void fromMagnitudeAndAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), -Math.PI / 4);
		
		assertEquals(1, c.getReal(), PRECISION);
		assertEquals(-1, c.getImaginary(), PRECISION);
		assertEquals(Math.sqrt(2), c.getMagnitude(), PRECISION);
		assertEquals(7 * Math.PI / 4, c.getAngle(), PRECISION);
	}
	
	@Test
	void fromMagnitudeAndAngleZeroMagnitude() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(0, 2.2);
		
		assertEquals(0, c.getReal(), PRECISION);
		assertEquals(0, c.getImaginary(), PRECISION);
		assertEquals(0, c.getMagnitude(), PRECISION);
		assertEquals(0, c.getAngle(), PRECISION);
	}
	
	@Test
	void fromMagnitudeAndAngleNegativeMagnitude() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber.fromMagnitudeAndAngle(-1.1, 2.21);			
		});
	}
	
	@Test
	void parseOnlyReal() {
		ComplexNumber c1 = ComplexNumber.parse("2.14");
		assertEquals(2.14, c1.getReal(), PRECISION);
		assertEquals(0, c1.getImaginary(), PRECISION);
		
		ComplexNumber c2 = ComplexNumber.parse("+3.13");
		assertEquals(3.13, c2.getReal(), PRECISION);
		assertEquals(0, c2.getImaginary(), PRECISION);
		
		ComplexNumber c3 = ComplexNumber.parse("-2.2");
		assertEquals(-2.2, c3.getReal(), PRECISION);
		assertEquals(0, c3.getImaginary(), PRECISION);
	}
	
	@Test
	void parseOnlyImaginary() {
		ComplexNumber c1 = ComplexNumber.parse("2.14i");
		assertEquals(0, c1.getReal(), PRECISION);
		assertEquals(2.14, c1.getImaginary(), PRECISION);
		
		ComplexNumber c2 = ComplexNumber.parse("+3.13i");
		assertEquals(0, c2.getReal(), PRECISION);
		assertEquals(3.13, c2.getImaginary(), PRECISION);
		
		ComplexNumber c3 = ComplexNumber.parse("-2.2i");
		assertEquals(0, c3.getReal(), PRECISION);
		assertEquals(-2.2, c3.getImaginary(), PRECISION);
		
		ComplexNumber c4 = ComplexNumber.parse("i");
		assertEquals(0, c4.getReal(), PRECISION);
		assertEquals(1, c4.getImaginary(), PRECISION);
		
		ComplexNumber c5 = ComplexNumber.parse("+i");
		assertEquals(0, c5.getReal(), PRECISION);
		assertEquals(1, c5.getImaginary(), PRECISION);
		
		ComplexNumber c6 = ComplexNumber.parse("-i");
		assertEquals(0, c6.getReal(), PRECISION);
		assertEquals(-1, c6.getImaginary(), PRECISION);
	}
	
	@Test
	void parseBothRealAndImaginary() {
		ComplexNumber c1 = ComplexNumber.parse("2.23+3i");
		assertEquals(2.23, c1.getReal(), PRECISION);
		assertEquals(3, c1.getImaginary(), PRECISION);
		
		ComplexNumber c2 = ComplexNumber.parse("+2-3.14i");
		assertEquals(2, c2.getReal(), PRECISION);
		assertEquals(-3.14, c2.getImaginary(), PRECISION);
		
		ComplexNumber c3 = ComplexNumber.parse("+11.1+i");
		assertEquals(11.1, c3.getReal(), PRECISION);
		assertEquals(1, c3.getImaginary(), PRECISION);
		
		ComplexNumber c4 = ComplexNumber.parse("-332-i");
		assertEquals(-332, c4.getReal(), PRECISION);
		assertEquals(-1, c4.getImaginary(), PRECISION);
	}
	
	@Test
	void parseInvalidFormat() {
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("3+i3");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("++3-3i");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("--3-3i");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("+-3-3i");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("-+3-3i");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("3++3i");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("3--3i");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("3+-3i");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("3-+3i");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("+-3");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("+-3i");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("+-i");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("3+3");			
		});
		assertThrows(NumberFormatException.class, () -> {
			ComplexNumber.parse("3i+3i");			
		});
	}

	@Test
	void getReal() {
		ComplexNumber c = new ComplexNumber(-3.14, 1);
		
		assertEquals(-3.14, c.getReal(), PRECISION);
	}
	
	@Test
	void getImaginary() {
		ComplexNumber c = new ComplexNumber(-1, 8.9);
		
		assertEquals(8.9, c.getImaginary(), PRECISION);
	}
	
	@Test
	void getMagnitude() {
		ComplexNumber c1 = new ComplexNumber(2, -1);
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(4.121, 4.5);
		
		assertEquals(Math.sqrt(5), c1.getMagnitude(), PRECISION);
		assertEquals(4.121, c2.getMagnitude(), PRECISION);
	}
	
	@Test
	void getAngle() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(2, -Math.PI / 2);

		assertEquals(Math.PI / 4, c1.getAngle(), PRECISION);
		assertEquals(3 * Math.PI / 2, c2.getAngle(), PRECISION);
	}
	
	@Test
	void add() {
		ComplexNumber c1 = new ComplexNumber(2, 1);
		ComplexNumber c2 = new ComplexNumber(3, -1);
		ComplexNumber result = c1.add(c2);
		
		assertEquals(5, result.getReal(), PRECISION);
		assertEquals(0, result.getImaginary(), PRECISION);
	}
	
	@Test
	void sub() {
		ComplexNumber c1 = new ComplexNumber(2, 1);
		ComplexNumber c2 = new ComplexNumber(3, -1);
		ComplexNumber result = c1.sub(c2);
		
		assertEquals(-1, result.getReal(), PRECISION);
		assertEquals(2, result.getImaginary(), PRECISION);
	}
	
	@Test
	void mul() {
		ComplexNumber c1 = new ComplexNumber(1, 1);
		ComplexNumber c2 = new ComplexNumber(1, -1);
		ComplexNumber result = c1.mul(c2);
		
		assertEquals(2, result.getReal(), PRECISION);
		assertEquals(0, result.getImaginary(), PRECISION);
	}
	
	@Test
	void mulByZero() {
		ComplexNumber c1 = new ComplexNumber(13, -23);
		ComplexNumber c2 = new ComplexNumber(0, 0);
		ComplexNumber result = c1.mul(c2);
		
		assertEquals(0, result.getReal(), PRECISION);
		assertEquals(0, result.getImaginary(), PRECISION);
	}
	
	@Test
	void div() {
		ComplexNumber c1 = new ComplexNumber(2, 1);
		ComplexNumber c2 = new ComplexNumber(2, -1);
		ComplexNumber result = c1.div(c2);
		
		assertEquals(0.6, result.getReal(), PRECISION);
		assertEquals(0.8, result.getImaginary(), PRECISION);
	}
	
	@Test
	void divBySame() {
		ComplexNumber c1 = new ComplexNumber(3, 4);
		ComplexNumber c2 = new ComplexNumber(3, 4);
		ComplexNumber result = c1.div(c2);
		
		assertEquals(1, result.getReal(), PRECISION);
		assertEquals(0, result.getImaginary(), PRECISION);
	}
	
	@Test
	void divByZero() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c1 = new ComplexNumber(3, 4);
			ComplexNumber c2 = new ComplexNumber(0, 0);
			c1.div(c2);			
		});
	}
	
	@Test
	void power() {
		ComplexNumber c = new ComplexNumber(2, -2);
		ComplexNumber result = c.power(2);
		
		assertEquals(0, result.getReal(), PRECISION);
		assertEquals(-8, result.getImaginary(), PRECISION);
	}
	
	@Test
	void powerOneExponent() {
		ComplexNumber c = new ComplexNumber(2, -2);
		ComplexNumber result = c.power(1);
		
		assertEquals(2, result.getReal(), PRECISION);
		assertEquals(-2, result.getImaginary(), PRECISION);
	}
	
	@Test
	void powerZeroExponent() {
		ComplexNumber c = new ComplexNumber(2, -2);
		ComplexNumber result = c.power(0);
		
		assertEquals(1, result.getReal(), PRECISION);
		assertEquals(0, result.getImaginary(), PRECISION);
	}
	
	@Test
	void powerNegativeExponent() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = new ComplexNumber(1, -4);
			c.power(-1);			
		});
	}
	
	@Test
	void root() {
		ComplexNumber c = new ComplexNumber(4, 0);
		ComplexNumber[] roots = c.root(2);
		
		assertEquals(2, roots.length);
		assertEquals(2, roots[0].getReal(), PRECISION);
		assertEquals(0, roots[0].getImaginary(), PRECISION);
		assertEquals(-2, roots[1].getReal(), PRECISION);
		assertEquals(0, roots[1].getImaginary(), PRECISION);
	}
	
	@Test
	void rootOne() {
		ComplexNumber c = new ComplexNumber(-3.14, 1);
		ComplexNumber[] roots = c.root(1);
		
		assertEquals(1, roots.length);
		assertEquals(-3.14, roots[0].getReal(), PRECISION);
		assertEquals(1, roots[0].getImaginary(), PRECISION);
	}
	
	@Test
	void rootZero() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = new ComplexNumber(1, 2);
			c.root(0);			
		});
	}
	
	@Test
	void rootNegative() {
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber c = new ComplexNumber(1, -2);
			c.root(-2);			
		});
	}
	
	@Test
	void toStringComplexNumber() {
		ComplexNumber c1 = new ComplexNumber(2.2, 1);
		ComplexNumber c2 = new ComplexNumber(-2, -3);
		ComplexNumber c3 = new ComplexNumber(0, 4.12);
		ComplexNumber c4 = new ComplexNumber(12.12, 0);
		
		assertEquals("2.2+1.0i", c1.toString());
		assertEquals("-2.0-3.0i", c2.toString());
		assertEquals("0.0+4.12i", c3.toString());
		assertEquals("12.12+0.0i", c4.toString());
	}

}
