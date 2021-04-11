package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	private LinkedListIndexedCollection linkedListCollection;
	
	@BeforeEach
	public void initializeCollection() {
	    linkedListCollection = new LinkedListIndexedCollection();
	}
	
	@Test
	void defaultConstructor() {
		assertEquals(0, linkedListCollection.size());
		assertTrue(linkedListCollection.isEmpty());
	}
	
	@Test
	void constructorWithAnotherCollection() {
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		other.add("a");
		other.add("b");
		other.add("c");
		
		linkedListCollection = new LinkedListIndexedCollection(other);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, linkedListCollection.toArray());
		assertEquals(3, linkedListCollection.size());
	}
	
	@Test
	void constructorWithAnotherCollectionNull() {
		assertThrows(NullPointerException.class, () -> {
			linkedListCollection = new LinkedListIndexedCollection(null);
		});
	}
	
	@Test
	void insertFirst() {
		linkedListCollection.insert("a", 0);
		linkedListCollection.insert("b", 0);
		linkedListCollection.insert("c", 0);
		
		assertArrayEquals(new Object[] {"c", "b", "a"}, linkedListCollection.toArray());
		assertEquals(3, linkedListCollection.size());
	}
	
	@Test
	void insertLast() {
		linkedListCollection.insert("a", 0);
		linkedListCollection.insert("b", 1);
		linkedListCollection.insert("c", 2);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, linkedListCollection.toArray());
		assertEquals(3, linkedListCollection.size());
	}
	
	@Test
	void insertInFirstHalf() {
		linkedListCollection.insert(Integer.valueOf(0), 0);
		linkedListCollection.insert(Integer.valueOf(1), 1);
		linkedListCollection.insert(Integer.valueOf(2), 2);
		linkedListCollection.insert(Integer.valueOf(3), 3);
		linkedListCollection.insert(Integer.valueOf(4), 4);
		linkedListCollection.insert(Integer.valueOf(5), 5);
		linkedListCollection.insert(Integer.valueOf(6), 6);
		
		linkedListCollection.insert("a", 2);
		
		assertArrayEquals(new Object[] {0, 1, "a", 2, 3, 4, 5, 6},
				          linkedListCollection.toArray()
		);
	}
	
	@Test
	void insertInSecondHalf() {
		linkedListCollection.insert(Integer.valueOf(0), 0);
		linkedListCollection.insert(Integer.valueOf(1), 1);
		linkedListCollection.insert(Integer.valueOf(2), 2);
		linkedListCollection.insert(Integer.valueOf(3), 3);
		linkedListCollection.insert(Integer.valueOf(4), 4);
		linkedListCollection.insert(Integer.valueOf(5), 5);
		linkedListCollection.insert(Integer.valueOf(6), 6);
		
		linkedListCollection.insert("b", 4);
		
		assertArrayEquals(new Object[] {0, 1, 2, 3, "b", 4, 5, 6},
				          linkedListCollection.toArray()
		);
	}
	
	@Test
	void insertNull() {
		assertThrows(NullPointerException.class, () -> {
			linkedListCollection.insert(null, 0);
		});
	}
	
	@Test
	void insertAtIllegalPosition() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			linkedListCollection.insert("abc", 5);
		});
	}
	
	@Test
	void add() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, linkedListCollection.toArray());
		assertEquals(3, linkedListCollection.size());
	}
	
	@Test
	void addNull() {
		assertThrows(NullPointerException.class, () -> {
			linkedListCollection.add(null);
		});
	}
	
	@Test
	void get() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertEquals("a", linkedListCollection.get(0));
		assertEquals("b", linkedListCollection.get(1));
		assertEquals("c", linkedListCollection.get(2));
	}
	
	@Test
	void getIllegalIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			linkedListCollection.get(5);
		});
	}
	
	@Test
	void clear() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertEquals(3, linkedListCollection.size());
		assertFalse(linkedListCollection.isEmpty());
		
		linkedListCollection.clear();
		
		assertEquals(0, linkedListCollection.size());
		assertTrue(linkedListCollection.isEmpty());
	}
	
	@Test
	void indexOf() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertEquals(0, linkedListCollection.indexOf("a"));
		assertEquals(1, linkedListCollection.indexOf("b"));
		assertEquals(2, linkedListCollection.indexOf("c"));
	}
	
	@Test
	void indexOfNonExisting() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertEquals(-1, linkedListCollection.indexOf("d"));
	}
	
	@Test
	void indexOfNull() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertEquals(-1, linkedListCollection.indexOf(null));
	}
	
	@Test
	void indexOfWhenEmpty() {
		assertEquals(-1, linkedListCollection.indexOf("a"));
	}
	
	@Test
	void removeByIndex() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		linkedListCollection.remove(1);
		
		assertArrayEquals(new Object[] {"a", "c"}, linkedListCollection.toArray());
		assertEquals(2, linkedListCollection.size());
	}
	
	@Test
	void removeByIndexFirst() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		linkedListCollection.remove(0);
		
		assertArrayEquals(new Object[] {"b", "c"}, linkedListCollection.toArray());
		assertEquals(2, linkedListCollection.size());
	}
	
	@Test
	void removeByIndexLast() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		linkedListCollection.remove(linkedListCollection.size() - 1);
		
		assertArrayEquals(new Object[] {"a", "b"}, linkedListCollection.toArray());
		assertEquals(2, linkedListCollection.size());
	}
	
	@Test
	void removeByIndexIllegalIndex() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			linkedListCollection.remove(5);
		});
	}
	
	@Test
	void removeByValue() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertTrue(linkedListCollection.remove("b"));
		assertArrayEquals(new Object[] {"a", "c"}, linkedListCollection.toArray());
		assertEquals(2, linkedListCollection.size());
	}
	
	@Test
	void removeByValueFirst() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertTrue(linkedListCollection.remove("a"));
		assertArrayEquals(new Object[] {"b", "c"}, linkedListCollection.toArray());
		assertEquals(2, linkedListCollection.size());
	}
	
	@Test
	void removeByValueLast() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertTrue(linkedListCollection.remove("c"));
		assertArrayEquals(new Object[] {"a", "b"}, linkedListCollection.toArray());
		assertEquals(2, linkedListCollection.size());
	}
	
	@Test
	void removeByValueWhenMultiple() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("a");
		
		assertTrue(linkedListCollection.remove("a"));
		assertArrayEquals(new Object[] {"b", "a"}, linkedListCollection.toArray());
		assertEquals(2, linkedListCollection.size());
	}
	
	@Test
	void removeByValueNonExisting() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertFalse(linkedListCollection.remove("d"));
		assertArrayEquals(new Object[] {"a", "b", "c"}, linkedListCollection.toArray());
		assertEquals(3, linkedListCollection.size());
	}
	
	@Test
	void removeByValueNull() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		
		assertFalse(linkedListCollection.remove(null));
		assertArrayEquals(new Object[] {"a", "b", "c"}, linkedListCollection.toArray());
		assertEquals(3, linkedListCollection.size());
	}
	
	@Test
	void sizeWhenNotEmpty() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
			
		assertEquals(3, linkedListCollection.size());
	}
	
	@Test
	void sizeWhenEmpty() {
		assertEquals(0, linkedListCollection.size());
	}
	
	@Test
	void isEmptyWhenNotEmpty() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
			
		assertFalse(linkedListCollection.isEmpty());
	}
	
	@Test
	void isEmptyWhenEmpty() {
		assertTrue(linkedListCollection.isEmpty());
	}
	
	
	@Test
	void contains() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
			
		assertTrue(linkedListCollection.contains("b"));
	}
	
	@Test
	void containsFirst() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
			
		assertTrue(linkedListCollection.contains("a"));
	}
	
	@Test
	void containsLast() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
			
		assertTrue(linkedListCollection.contains("c"));
	}
	
	@Test
	void containsWhenNonExisting() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
			
		assertFalse(linkedListCollection.contains("d"));
	}
	
	@Test
	void containsWhenEmpty() {		
		assertFalse(linkedListCollection.contains("a"));
	}
	
	@Test
	void containsNull() {
		assertFalse(linkedListCollection.contains(null));
	}
	
	@Test
	void toArray() {
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
			
		assertArrayEquals(new Object[] {"a", "b", "c"}, linkedListCollection.toArray());
		
		assertEquals(3, linkedListCollection.toArray().length);
	}
	
	@Test
	void toArrayWhenEmpty() {
		assertArrayEquals(new Object[0], linkedListCollection.toArray());
		assertEquals(0, linkedListCollection.toArray().length);
	}
	
	@Test
	void addAll() {
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		other.add("a");
		other.add("b");
		other.add("c");
		
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		linkedListCollection.addAll(other);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, other.toArray());
		assertArrayEquals(new Object[] {"a", "b", "c", "a", "b", "c"}, 
				          linkedListCollection.toArray()
		);
	}
	
	@Test
	void addAllWhenEmpty() {
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		other.add("a");
		other.add("b");
		other.add("c");
		
		linkedListCollection.addAll(other);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, other.toArray());
		assertArrayEquals(new Object[] {"a", "b", "c"}, linkedListCollection.toArray());
	}
	
	@Test
	void addAllWhenOtherEmpty() {
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		
		linkedListCollection.add("a");
		linkedListCollection.add("b");
		linkedListCollection.add("c");
		linkedListCollection.addAll(other);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, linkedListCollection.toArray());
	}

}
