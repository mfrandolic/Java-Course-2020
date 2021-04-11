package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObjectMultistackTest {
	
	private ObjectMultistack multistack;
	
	@BeforeEach
	public void init() {
		multistack = new ObjectMultistack();
	}

	@Test
	public void testPushAndIsEmpty() {
		assertTrue(multistack.isEmpty("a"));
		assertTrue(multistack.isEmpty("b"));
		
		multistack.push("a", new ValueWrapper(1));
		multistack.push("b", new ValueWrapper(2));
		
		assertFalse(multistack.isEmpty("a"));
		assertFalse(multistack.isEmpty("b"));
		
		multistack.push("a", new ValueWrapper("abc"));
		multistack.push("b", new ValueWrapper("def"));
		
		assertFalse(multistack.isEmpty("a"));
		assertFalse(multistack.isEmpty("b"));
	}
	
	@Test
	public void testPop() {
		multistack.push("a", new ValueWrapper(1));
		multistack.push("a", new ValueWrapper("abc"));
		multistack.push("b", new ValueWrapper(2));
		multistack.push("b", new ValueWrapper("def"));
		
		assertEquals("abc", multistack.pop("a").getValue());
		assertEquals(1, multistack.pop("a").getValue());
		assertEquals("def", multistack.pop("b").getValue());
		assertEquals(2, multistack.pop("b").getValue());
		
		assertTrue(multistack.isEmpty("a"));
		assertTrue(multistack.isEmpty("b"));
	}
	
	@Test
	public void testPopEmpty() {
		multistack.push("a", new ValueWrapper(1));
		multistack.push("a", new ValueWrapper("abc"));
		
		assertEquals("abc", multistack.pop("a").getValue());
		assertEquals(1, multistack.pop("a").getValue());
		
		assertTrue(multistack.isEmpty("a"));
		assertThrows(NoSuchElementException.class, () -> {
			multistack.pop("a");
		});
		
		assertTrue(multistack.isEmpty("b"));
		assertThrows(NoSuchElementException.class, () -> {
			multistack.pop("b");
		});
	}
	
	@Test
	public void testPeek() {
		multistack.push("a", new ValueWrapper(1));
		assertEquals(1, multistack.peek("a").getValue());
		
		multistack.push("a", new ValueWrapper("abc"));
		assertEquals("abc", multistack.peek("a").getValue());
		
		multistack.push("b", new ValueWrapper(2));
		assertEquals(2, multistack.peek("b").getValue());
		
		multistack.push("b", new ValueWrapper("def"));
		assertEquals("def", multistack.peek("b").getValue());
		
		assertFalse(multistack.isEmpty("a"));
		assertFalse(multistack.isEmpty("b"));
	}
	
	@Test
	public void testPeekEmpty() {
		assertTrue(multistack.isEmpty("a"));
		assertThrows(NoSuchElementException.class, () -> {
			multistack.peek("a");
		});
	}

}
