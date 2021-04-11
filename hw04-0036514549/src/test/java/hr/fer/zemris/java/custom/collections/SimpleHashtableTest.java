package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleHashtableTest {

	private SimpleHashtable<String, Integer> hashTable;
	
	@BeforeEach
	private void initializeHashTable() {
		hashTable = new SimpleHashtable<String, Integer>();
	}
	
	@Test
	public void testDefaultConstructor() {
		assertEquals(0, hashTable.size());
	}
	
	@Test
	public void testConstructorWithLegalCapacity() {
		hashTable = new SimpleHashtable<String, Integer>(14);
		assertEquals(0, hashTable.size());
	}
	
	@Test
	public void testConstructorWithIllegalCapacity() {
		assertThrows(IllegalArgumentException.class, () -> {
			hashTable = new SimpleHashtable<String, Integer>(-1);
		});
	}

	@Test
	public void testSizeWhenEmpty() {
		assertEquals(0, hashTable.size());
	}
	
	@Test
	public void testSizeWhenNotEmpty() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertEquals(3, hashTable.size());
	}
	
	@Test
	public void testIsEmptyWhenEmpty() {
		assertTrue(hashTable.isEmpty());
	}
	
	@Test
	public void testIsEmptyWhenNotEmpty() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertFalse(hashTable.isEmpty());
	}
	
	@Test
	public void testPutAndGet() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertEquals(3, hashTable.size());
		
		assertEquals(1, hashTable.get("key1"));
		assertEquals(2, hashTable.get("key2"));
		assertEquals(3, hashTable.get("key3"));
	}
	
	@Test
	public void testPutAndGetWithResizeNeeded() {
		hashTable = new SimpleHashtable<String, Integer>(2);
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		hashTable.put("key4", 4);
		hashTable.put("key5", 5);
		hashTable.put("key6", 6);
		hashTable.put("key7", 7);
		hashTable.put("key8", 8);
		hashTable.put("key9", 9);
		
		assertEquals(9, hashTable.size());
		
		assertEquals(1, hashTable.get("key1"));
		assertEquals(2, hashTable.get("key2"));
		assertEquals(3, hashTable.get("key3"));
		assertEquals(4, hashTable.get("key4"));
		assertEquals(5, hashTable.get("key5"));
		assertEquals(6, hashTable.get("key6"));
		assertEquals(7, hashTable.get("key7"));
		assertEquals(8, hashTable.get("key8"));
		assertEquals(9, hashTable.get("key9"));
	}
	
	@Test
	public void testPutAndGetWithDifferentTypes() {
		SimpleHashtable<Number, Character> hashTable = new SimpleHashtable<>();
		
		hashTable.put(-1, 'a');
		hashTable.put(2.22, 'b');
		hashTable.put(-3.14, 'c');
		
		assertEquals(3, hashTable.size());
		
		assertEquals('a', hashTable.get(-1));
		assertEquals('b', hashTable.get(2.22));
		assertEquals('c', hashTable.get(-3.14));
	}
	
	@Test
	public void testPutAndGetWhenValueUpdated() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		hashTable.put("key1", 11);
		hashTable.put("key2", 22);
		
		assertEquals(3, hashTable.size());
		
		assertEquals(11, hashTable.get("key1"));
		assertEquals(22, hashTable.get("key2"));
		assertEquals(3, hashTable.get("key3"));
	}
	
	@Test
	public void testPutWhenKeyNull() {
		assertThrows(NullPointerException.class, () -> {
			hashTable.put(null, 1);
		});
	}
	
	@Test
	public void testContainsKeyWhenContains() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertTrue(hashTable.containsKey("key1"));
		assertTrue(hashTable.containsKey("key2"));
		assertTrue(hashTable.containsKey("key3"));
	}
	
	@Test
	public void testContainsKeyWhenNotContains() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertFalse(hashTable.containsKey("key4"));
		assertFalse(hashTable.containsKey("key5"));
	}
	
	@Test
	public void testContainsKeyNull() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertFalse(hashTable.containsKey(null));
	}
	
	@Test
	public void testContainsValueWhenContains() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertTrue(hashTable.containsValue(1));
		assertTrue(hashTable.containsValue(2));
		assertTrue(hashTable.containsValue(3));
	}
	
	@Test
	public void testContainsValueWhenNotContains() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertFalse(hashTable.containsValue(4));
		assertFalse(hashTable.containsValue(5));
	}
	
	@Test
	public void testContainsValueNullWhenContains() {
		hashTable.put("key1", null);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertTrue(hashTable.containsValue(null));
	}
	
	@Test
	public void testContainsValueNullWhenNotContains() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertFalse(hashTable.containsValue(null));
	}
	
	@Test
	public void testPutThenClear() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertEquals(3, hashTable.size());
		
		hashTable.clear();
		
		assertEquals(0, hashTable.size());
	}
	
	@Test
	public void testPutThenClearThenPut() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertEquals(3, hashTable.size());
		
		hashTable.clear();
		
		assertEquals(0, hashTable.size());
		
		hashTable.put("key1", 1);
		hashTable.put("key4", 4);
		hashTable.put("key5", 5);
		
		assertEquals(3, hashTable.size());
		
		assertEquals(1, hashTable.get("key1"));
		assertEquals(4, hashTable.get("key4"));
		assertEquals(5, hashTable.get("key5"));
	}
	
	@Test
	public void testPutThenRemove() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertEquals(3, hashTable.size());
		
		hashTable.remove("key1");
		
		assertEquals(2, hashTable.size());
		assertEquals(2, hashTable.get("key2"));
		assertEquals(3, hashTable.get("key3"));
	}
	
	@Test
	public void testPutThenRemoveThenPut() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertEquals(3, hashTable.size());
		
		hashTable.remove("key1");
		hashTable.remove("key2");
		
		assertEquals(1, hashTable.size());
		assertEquals(3, hashTable.get("key3"));
		
		hashTable.put("key1", 1);
		hashTable.put("key4", 4);
		hashTable.put("key5", 5);
		
		assertEquals(4, hashTable.size());
		
		assertEquals(1, hashTable.get("key1"));
		assertEquals(3, hashTable.get("key3"));
		assertEquals(4, hashTable.get("key4"));
		assertEquals(5, hashTable.get("key5"));
	}
	
	@Test
	public void testRemoveNull() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		assertEquals(3, hashTable.size());
		
		hashTable.remove(null);
		
		assertEquals(3, hashTable.size());
		
		assertEquals(1, hashTable.get("key1"));
		assertEquals(2, hashTable.get("key2"));
		assertEquals(3, hashTable.get("key3"));
	}
	
	@Test
	public void testTableEntry() {
		SimpleHashtable.TableEntry<String, Integer> entry = 
				new SimpleHashtable.TableEntry<String, Integer>("key1", 1);
		
		assertEquals("key1", entry.getKey());
		assertEquals(1, entry.getValue());
		
		entry.setValue(11);
		assertEquals(11, entry.getValue());
	}
	
	@Test
	public void testTableEntryWhenKeyNull() {
		assertThrows(NullPointerException.class, () -> {
			new SimpleHashtable.TableEntry<String, Integer>(null, 1);			
		});
	}
	
	@Test
	public void testIteratorHasNextAndNext() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		Iterator<SimpleHashtable.TableEntry<String, Integer>> it =
				hashTable.iterator();
		
		assertTrue(it.hasNext());
		SimpleHashtable.TableEntry<String, Integer> entry = it.next();
		assertEquals("key1", entry.getKey());
		assertEquals(1, entry.getValue());
		assertEquals(3, hashTable.size());
		
		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("key2", entry.getKey());
		assertEquals(2, entry.getValue());
		assertEquals(3, hashTable.size());
		
		assertTrue(it.hasNext());
		entry = it.next();
		assertEquals("key3", entry.getKey());
		assertEquals(3, entry.getValue());
		assertEquals(3, hashTable.size());
		
		assertFalse(it.hasNext());
		assertEquals(3, hashTable.size());
		assertThrows(NoSuchElementException.class, () -> {
			it.next();
		});
	}
	
	@Test
	public void testIteratorRemove() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		Iterator<SimpleHashtable.TableEntry<String, Integer>> it =
				hashTable.iterator();
		
		while (it.hasNext()) {
			if (it.next().getKey().equals("key2")) {
				it.remove();
			}
		}
		
		assertEquals(2, hashTable.size());
		
		assertEquals(1, hashTable.get("key1"));
		assertEquals(null, hashTable.get("key2"));
		assertEquals(3, hashTable.get("key3"));
	}
	
	@Test
	public void testIteratorIllegalRemove() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		Iterator<SimpleHashtable.TableEntry<String, Integer>> it =
				hashTable.iterator();
		
		assertThrows(IllegalStateException.class, () -> {
			while (it.hasNext()) {
				if (it.next().getKey().equals("key2")) {
					it.remove();
					it.remove();
				}
			}
		});
	}
	
	@Test
	public void testIteratorConcurrentRemove() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		Iterator<SimpleHashtable.TableEntry<String, Integer>> it =
				hashTable.iterator();
		
		assertThrows(ConcurrentModificationException.class, () -> {
			while (it.hasNext()) {
				if (it.next().getKey().equals("key2")) {
					hashTable.remove("key2");
				}
			}
		});
	}
	
	@Test
	public void testIteratorRemoveAll() {
		hashTable.put("key1", 1);
		hashTable.put("key2", 2);
		hashTable.put("key3", 3);
		
		Iterator<SimpleHashtable.TableEntry<String, Integer>> it =
				hashTable.iterator();
		
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
		
		assertEquals(0, hashTable.size());
		
		assertEquals(null, hashTable.get("key1"));
		assertEquals(null, hashTable.get("key2"));
		assertEquals(null, hashTable.get("key3"));
	}

}
