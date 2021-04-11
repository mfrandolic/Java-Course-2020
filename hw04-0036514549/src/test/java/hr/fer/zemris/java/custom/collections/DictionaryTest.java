package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryTest {

	private Dictionary<String, Integer> dictionary;
	
	@BeforeEach
	private void initializeDictionary() {
		dictionary = new Dictionary<String, Integer>();
	}
	
	@Test
	public void testIsEmptyWhenEmpty() {
		assertTrue(dictionary.isEmpty());
	}
	
	@Test
	public void testIsEmptyWhenNotEmpty() {
		dictionary.put("key1", 1);
		dictionary.put("key2", 2);
		dictionary.put("key3", 3);
		
		assertFalse(dictionary.isEmpty());
	}
	
	@Test
	public void testSizeWhenEmpty() {
		assertEquals(0, dictionary.size());
	}
	
	@Test
	public void testSizeWhenNotEmpty() {
		dictionary.put("key1", 1);
		dictionary.put("key2", 2);
		dictionary.put("key3", 3);
		
		assertEquals(3, dictionary.size());
	}
	
	@Test
	public void testPutAndGet() {
		dictionary.put("key1", 1);
		dictionary.put("key2", 2);
		dictionary.put("key3", 3);
		
		assertEquals(3, dictionary.size());
		
		assertEquals(1, dictionary.get("key1"));
		assertEquals(2, dictionary.get("key2"));
		assertEquals(3, dictionary.get("key3"));
	}
	
	@Test
	public void testPutAndGetWithDifferentTypes() {
		Dictionary<Number, String> dictionary = new Dictionary<>();
		
		dictionary.put(-1, "a");
		dictionary.put(-2.2, "b");
		dictionary.put(12345678900000L, "c");
		
		assertEquals(3, dictionary.size());
		
		assertEquals("a", dictionary.get(-1));
		assertEquals("b", dictionary.get(-2.2));
		assertEquals("c", dictionary.get(12345678900000L));
	}
	
	@Test
	public void testPutAndGetWithDifferentTypes2() {
		Dictionary<String, String> dictionary = new Dictionary<>();
		
		dictionary.put("a", "c");
		dictionary.put("b", "b");
		dictionary.put("c", "a");
		
		assertEquals(3, dictionary.size());
		
		assertEquals("c", dictionary.get("a"));
		assertEquals("b", dictionary.get("b"));
		assertEquals("a", dictionary.get("c"));
	}
	
	@Test
	public void testPutAndGetWhenValueUpdated() {
		dictionary.put("key1", 1);
		dictionary.put("key2", 2);
		dictionary.put("key3", 3);
		dictionary.put("key1", 11);
		dictionary.put("key2", 22);
		
		assertEquals(3, dictionary.size());
		
		assertEquals(11, dictionary.get("key1"));
		assertEquals(22, dictionary.get("key2"));
		assertEquals(3, dictionary.get("key3"));
	}
	
	@Test
	public void testPutWhenKeyNull() {
		assertThrows(NullPointerException.class, () -> {
			dictionary.put(null, 1);
		});
	}
	
	@Test
	public void testPutAndGetWhenValueNull() {
		dictionary.put("key1", 1);
		dictionary.put("key2", null);
		dictionary.put("key3", 3);
		
		assertEquals(3, dictionary.size());
		
		assertEquals(1, dictionary.get("key1"));
		assertEquals(null, dictionary.get("key2"));
		assertEquals(3, dictionary.get("key3"));
	}
	
	@Test
	public void testGetWhenKeyNull() {
		dictionary.put("key1", 1);
		dictionary.put("key2", 2);
		dictionary.put("key3", 3);
		
		assertEquals(3, dictionary.size());
		
		assertEquals(null, dictionary.get(null));
		assertEquals(2, dictionary.get("key2"));
		assertEquals(3, dictionary.get("key3"));
	}
	
	@Test
	public void testGetNonExisting() {
		dictionary.put("key1", 1);
		dictionary.put("key2", 2);
		dictionary.put("key3", 3);
		
		assertEquals(3, dictionary.size());
		
		assertEquals(1, dictionary.get("key1"));
		assertEquals(null, dictionary.get("key4"));
		assertEquals(null, dictionary.get("key5"));
	}
	
	@Test
	public void testPutThenClear() {
		dictionary.put("key1", 1);
		dictionary.put("key2", 2);
		dictionary.put("key3", 3);
		
		assertEquals(3, dictionary.size());
		
		assertEquals(1, dictionary.get("key1"));
		assertEquals(2, dictionary.get("key2"));
		assertEquals(3, dictionary.get("key3"));
		
		dictionary.clear();
		
		assertEquals(0, dictionary.size());
		
		assertEquals(null, dictionary.get("key1"));
		assertEquals(null, dictionary.get("key2"));
		assertEquals(null, dictionary.get("key3"));
	}
	
	@Test
	public void testPutThenClearThenPut() {
		dictionary.put("key1", 1);
		dictionary.put("key2", 2);
		dictionary.put("key3", 3);
		
		assertEquals(3, dictionary.size());
		
		assertEquals(1, dictionary.get("key1"));
		assertEquals(2, dictionary.get("key2"));
		assertEquals(3, dictionary.get("key3"));
		
		dictionary.clear();
		
		assertEquals(0, dictionary.size());
		
		assertEquals(null, dictionary.get("key1"));
		assertEquals(null, dictionary.get("key2"));
		assertEquals(null, dictionary.get("key3"));
		
		dictionary.put("key1", 1);
		dictionary.put("key4", 4);
		dictionary.put("key5", 5);
		
		assertEquals(3, dictionary.size());
		
		assertEquals(1, dictionary.get("key1"));
		assertEquals(4, dictionary.get("key4"));
		assertEquals(5, dictionary.get("key5"));
	}
	
	@Test
	public void testPutThenClearThenPutThenClear() {
		dictionary.put("key1", 1);
		dictionary.put("key2", 2);
		dictionary.put("key3", 3);
		
		assertEquals(3, dictionary.size());
		
		assertEquals(1, dictionary.get("key1"));
		assertEquals(2, dictionary.get("key2"));
		assertEquals(3, dictionary.get("key3"));
		
		dictionary.clear();
		
		assertEquals(0, dictionary.size());
		
		assertEquals(null, dictionary.get("key1"));
		assertEquals(null, dictionary.get("key2"));
		assertEquals(null, dictionary.get("key3"));
		
		dictionary.put("key1", 1);
		dictionary.put("key4", 4);
		dictionary.put("key5", 5);
		
		assertEquals(3, dictionary.size());
		
		assertEquals(1, dictionary.get("key1"));
		assertEquals(4, dictionary.get("key4"));
		assertEquals(5, dictionary.get("key5"));
		
		dictionary.clear();
		
		assertEquals(0, dictionary.size());
		
		assertEquals(null, dictionary.get("key1"));
		assertEquals(null, dictionary.get("key2"));
		assertEquals(null, dictionary.get("key3"));
		assertEquals(null, dictionary.get("key4"));
		assertEquals(null, dictionary.get("key5"));
	}

}
