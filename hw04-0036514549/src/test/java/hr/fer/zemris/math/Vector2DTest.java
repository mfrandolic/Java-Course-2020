package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2DTest {
	
	private static final double PRECISION = 1E-8;

	@Test
	public void testConstuctorAndGetters() {
		Vector2D v1 = new Vector2D(1, -2);
		Vector2D v2 = new Vector2D(-1.1, 2.2);
		
		assertEquals(1, v1.getX(), PRECISION);
		assertEquals(-2, v1.getY(), PRECISION);
		assertEquals(-1.1, v2.getX(), PRECISION);
		assertEquals(2.2, v2.getY(), PRECISION);
	}
	
	@Test
	public void testTranslate() {
		Vector2D v1 = new Vector2D(1, 2);
		Vector2D v2 = new Vector2D(-11, 5);
		
		v1.translate(v2);
		
		assertEquals(-10, v1.getX(), PRECISION);
		assertEquals(7, v1.getY(), PRECISION);
		assertEquals(-11, v2.getX(), PRECISION);
		assertEquals(5, v2.getY(), PRECISION);
		
		v2.translate(v1);
		
		assertEquals(-10, v1.getX(), PRECISION);
		assertEquals(7, v1.getY(), PRECISION);
		assertEquals(-21, v2.getX(), PRECISION);
		assertEquals(12, v2.getY(), PRECISION);
	}
	
	@Test
	public void testTranslated() {
		Vector2D v1 = new Vector2D(-2.3, -2);
		Vector2D v2 = new Vector2D(-1.1, 7);
		
		Vector2D v3 = v1.translated(v2);
		Vector2D v4 = v2.translated(v1);
		
		assertEquals(-2.3, v1.getX(), PRECISION);
		assertEquals(-2, v1.getY(), PRECISION);
		assertEquals(-1.1, v2.getX(), PRECISION);
		assertEquals(7, v2.getY(), PRECISION);
				
		assertEquals(-3.4, v3.getX(), PRECISION);
		assertEquals(5, v3.getY(), PRECISION);
		assertEquals(-3.4, v4.getX(), PRECISION);
		assertEquals(5, v4.getY(), PRECISION);
	}
	
	@Test
	public void testTranslateByZero() {
		Vector2D v1 = new Vector2D(3, 4);
		Vector2D v2 = new Vector2D(0, 0);
		
		v1.translate(v2);
		
		assertEquals(3, v1.getX(), PRECISION);
		assertEquals(4, v1.getY(), PRECISION);
		assertEquals(0, v2.getX(), PRECISION);
		assertEquals(0, v2.getY(), PRECISION);
		
		v2.translate(v1);
		
		assertEquals(3, v1.getX(), PRECISION);
		assertEquals(4, v1.getY(), PRECISION);
		assertEquals(3, v2.getX(), PRECISION);
		assertEquals(4, v2.getY(), PRECISION);
	}
	
	@Test
	public void testTranslatedByZero() {
		Vector2D v1 = new Vector2D(-3.7, 2);
		Vector2D v2 = new Vector2D(0, 0);
		
		Vector2D v3 = v1.translated(v2);
		Vector2D v4 = v2.translated(v1);
		
		assertEquals(-3.7, v1.getX(), PRECISION);
		assertEquals(2, v1.getY(), PRECISION);
		assertEquals(0, v2.getX(), PRECISION);
		assertEquals(0, v2.getY(), PRECISION);
				
		assertEquals(-3.7, v3.getX(), PRECISION);
		assertEquals(2, v3.getY(), PRECISION);
		assertEquals(-3.7, v4.getX(), PRECISION);
		assertEquals(2, v4.getY(), PRECISION);
	}
	
	@Test
	public void testRotate() {
		Vector2D v1 = new Vector2D(1, 1);
		Vector2D v2 = new Vector2D(-1, 1);
		
		v1.rotate(Math.PI / 4);
		v2.rotate(Math.PI);
		
		assertEquals(0, v1.getX(), PRECISION);
		assertEquals(Math.sqrt(2), v1.getY(), PRECISION);
		assertEquals(1, v2.getX(), PRECISION);
		assertEquals(-1, v2.getY(), PRECISION);
	}
	
	@Test
	public void testRotated() {
		Vector2D v1 = new Vector2D(0, 1);
		Vector2D v2 = new Vector2D(-2, 0);
		
		Vector2D v3 = v1.rotated(2 * Math.PI);
		Vector2D v4 = v2.rotated(Math.PI / 2);
		
		assertEquals(0, v1.getX(), PRECISION);
		assertEquals(1, v1.getY(), PRECISION);
		assertEquals(-2, v2.getX(), PRECISION);
		assertEquals(0, v2.getY(), PRECISION);
				
		assertEquals(0, v3.getX(), PRECISION);
		assertEquals(1, v3.getY(), PRECISION);
		assertEquals(0, v4.getX(), PRECISION);
		assertEquals(-2, v4.getY(), PRECISION);
	}
	
	@Test
	public void testRotateByZero() {
		Vector2D v1 = new Vector2D(1.23, 4);
		Vector2D v2 = new Vector2D(-1.123, -3);
		
		v1.rotate(0);
		v2.rotate(0);
		
		assertEquals(1.23, v1.getX(), PRECISION);
		assertEquals(4, v1.getY(), PRECISION);
		assertEquals(-1.123, v2.getX(), PRECISION);
		assertEquals(-3, v2.getY(), PRECISION);
	}
	
	@Test
	public void testRotatedByZero() {
		Vector2D v1 = new Vector2D(20, -11);
		Vector2D v2 = new Vector2D(-2.9, 0);
		
		Vector2D v3 = v1.rotated(0);
		Vector2D v4 = v2.rotated(0);
		
		assertEquals(20, v1.getX(), PRECISION);
		assertEquals(-11, v1.getY(), PRECISION);
		assertEquals(-2.9, v2.getX(), PRECISION);
		assertEquals(0, v2.getY(), PRECISION);
				
		assertEquals(20, v3.getX(), PRECISION);
		assertEquals(-11, v3.getY(), PRECISION);
		assertEquals(-2.9, v4.getX(), PRECISION);
		assertEquals(-0, v4.getY(), PRECISION);
	}
	
	@Test
	public void testScale() {
		Vector2D v1 = new Vector2D(1.1, 2);
		Vector2D v2 = new Vector2D(-0.5, 10);
		
		v1.scale(3);
		v2.scale(-0.5);
		
		assertEquals(3.3, v1.getX(), PRECISION);
		assertEquals(6, v1.getY(), PRECISION);
		assertEquals(0.25, v2.getX(), PRECISION);
		assertEquals(-5, v2.getY(), PRECISION);
	}
	
	@Test
	public void testScaled() {
		Vector2D v1 = new Vector2D(-1, -2);
		Vector2D v2 = new Vector2D(-1.1, 9);
		
		Vector2D v3 = v1.scaled(2);
		Vector2D v4 = v2.scaled(-4);
		
		assertEquals(-1, v1.getX(), PRECISION);
		assertEquals(-2, v1.getY(), PRECISION);
		assertEquals(-1.1, v2.getX(), PRECISION);
		assertEquals(9, v2.getY(), PRECISION);
				
		assertEquals(-2, v3.getX(), PRECISION);
		assertEquals(-4, v3.getY(), PRECISION);
		assertEquals(4.4, v4.getX(), PRECISION);
		assertEquals(-36, v4.getY(), PRECISION);
	}
	
	@Test
	public void testScaleByZero() {
		Vector2D v1 = new Vector2D(121, -12);
		Vector2D v2 = new Vector2D(125, 10);
		
		v1.scale(0);
		v2.scale(0);
		
		assertEquals(0, v1.getX(), PRECISION);
		assertEquals(0, v1.getY(), PRECISION);
		assertEquals(0, v2.getX(), PRECISION);
		assertEquals(0, v2.getY(), PRECISION);
	}
	
	@Test
	public void testScaledByZero() {
		Vector2D v1 = new Vector2D(12, -2.0);
		Vector2D v2 = new Vector2D(-1.8, 12.12);
		
		Vector2D v3 = v1.scaled(0);
		Vector2D v4 = v2.scaled(0);
		
		assertEquals(12, v1.getX(), PRECISION);
		assertEquals(-2, v1.getY(), PRECISION);
		assertEquals(-1.8, v2.getX(), PRECISION);
		assertEquals(12.12, v2.getY(), PRECISION);
				
		assertEquals(0, v3.getX(), PRECISION);
		assertEquals(0, v3.getY(), PRECISION);
		assertEquals(0, v4.getX(), PRECISION);
		assertEquals(0, v4.getY(), PRECISION);
	}
	
	@Test
	public void testCopy() {
		Vector2D v1 = new Vector2D(13, -2.5);
		Vector2D v2 = new Vector2D(1, -6);
		
		Vector2D v3 = v1.copy();
		Vector2D v4 = v2.copy();
		
		assertEquals(13, v1.getX(), PRECISION);
		assertEquals(-2.5, v1.getY(), PRECISION);
		assertEquals(1, v2.getX(), PRECISION);
		assertEquals(-6, v2.getY(), PRECISION);
				
		assertEquals(13, v3.getX(), PRECISION);
		assertEquals(-2.5, v3.getY(), PRECISION);
		assertEquals(1, v4.getX(), PRECISION);
		assertEquals(-6, v4.getY(), PRECISION);
	}

}
