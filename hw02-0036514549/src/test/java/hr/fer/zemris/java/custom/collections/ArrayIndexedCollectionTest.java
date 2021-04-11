package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

	private ArrayIndexedCollection arrayCollection;
	
	@BeforeEach
	public void initializeCollection() {
	    arrayCollection = new ArrayIndexedCollection();
	}
	
	@Test
	void defaultConstructor() {
		assertEquals(0, arrayCollection.size());
		assertTrue(arrayCollection.isEmpty());
	}
	
	@Test
	void constructorWithCapacity() {
		arrayCollection = new ArrayIndexedCollection(5);
		
		assertEquals(0, arrayCollection.size());
		assertTrue(arrayCollection.isEmpty());
	}
	
	@Test
	void constructorWithCapacityZeroCapacity() {
		assertThrows(IllegalArgumentException.class, () -> {
			arrayCollection = new ArrayIndexedCollection(0);
		});
	}
	
	@Test
	void constructorWithCapacityNegativeCapacity() {
		assertThrows(IllegalArgumentException.class, () -> {
			arrayCollection = new ArrayIndexedCollection(-2);
		});
	}
	
	@Test
	void constructorWithAnotherCollection() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		other.add("a");
		other.add("b");
		other.add("c");
		
		arrayCollection = new ArrayIndexedCollection(other);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, arrayCollection.toArray());
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void constructorWithAnotherCollectionNull() {
		assertThrows(NullPointerException.class, () -> {
			arrayCollection = new ArrayIndexedCollection(null);
		});
	}
	
	@Test
	void constructorWithAnotherCollectionAndCapacityBiggerCapacity() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		other.add("a");
		other.add("b");
		other.add("c");
		
		arrayCollection = new ArrayIndexedCollection(other, 20);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, arrayCollection.toArray());
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void constructorWithAnotherCollectionAndCapacityBiggerCollection() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		other.add("a");
		other.add("b");
		other.add("c");
		
		arrayCollection = new ArrayIndexedCollection(other, 1);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, arrayCollection.toArray());
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void constructorWithAnotherCollectionAndCapacityNullCollection() {
		assertThrows(NullPointerException.class, () -> {
			arrayCollection = new ArrayIndexedCollection(null, 10);
		});
	}
	
	@Test
	void constructorWithAnotherCollectionAndCapacityZeroCapacity() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		assertThrows(IllegalArgumentException.class, () -> {
			arrayCollection = new ArrayIndexedCollection(other, 0);
		});
	}
	
	@Test
	void constructorWithAnotherCollectionAndCapacityNegativeCapacity() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		assertThrows(IllegalArgumentException.class, () -> {
			arrayCollection = new ArrayIndexedCollection(other, -2);
		});
	}
	
	@Test
	void insertFirst() {
		arrayCollection.insert("a", 0);
		arrayCollection.insert("b", 0);
		arrayCollection.insert("c", 0);
		
		assertArrayEquals(new Object[] {"c", "b", "a"}, arrayCollection.toArray());
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void insertLast() {
		arrayCollection.insert("a", 0);
		arrayCollection.insert("b", 1);
		arrayCollection.insert("c", 2);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, arrayCollection.toArray());
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void insertInFirstHalf() {
		arrayCollection.insert(Integer.valueOf(0), 0);
		arrayCollection.insert(Integer.valueOf(1), 1);
		arrayCollection.insert(Integer.valueOf(2), 2);
		arrayCollection.insert(Integer.valueOf(3), 3);
		arrayCollection.insert(Integer.valueOf(4), 4);
		arrayCollection.insert(Integer.valueOf(5), 5);
		arrayCollection.insert(Integer.valueOf(6), 6);
		
		arrayCollection.insert("a", 2);
		
		assertArrayEquals(new Object[] {0, 1, "a", 2, 3, 4, 5, 6},
				          arrayCollection.toArray()
		);
	}
	
	@Test
	void insertInSecondHalf() {
		arrayCollection.insert(Integer.valueOf(0), 0);
		arrayCollection.insert(Integer.valueOf(1), 1);
		arrayCollection.insert(Integer.valueOf(2), 2);
		arrayCollection.insert(Integer.valueOf(3), 3);
		arrayCollection.insert(Integer.valueOf(4), 4);
		arrayCollection.insert(Integer.valueOf(5), 5);
		arrayCollection.insert(Integer.valueOf(6), 6);
		
		arrayCollection.insert("b", 4);
		
		assertArrayEquals(new Object[] {0, 1, 2, 3, "b", 4, 5, 6},
				          arrayCollection.toArray()
		);
	}
	
	@Test
	void insertWhenArrayFull() {
		arrayCollection = new ArrayIndexedCollection(2);
		
		arrayCollection.insert("a", 0);
		arrayCollection.insert("b", 1);
		assertEquals(2, arrayCollection.size());
		
		arrayCollection.insert("c", 2);
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void insertNull() {
		assertThrows(NullPointerException.class, () -> {
			arrayCollection.insert(null, 0);
		});
	}
	
	@Test
	void insertAtIllegalPosition() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			arrayCollection.insert("abc", 5);
		});
	}
	
	@Test
	void add() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, arrayCollection.toArray());
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void addWhenArrayFull() {
		arrayCollection = new ArrayIndexedCollection(2);
		
		arrayCollection.add("a");
		arrayCollection.add("b");
		assertEquals(2, arrayCollection.size());
		
		arrayCollection.add("c");
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void addNull() {
		assertThrows(NullPointerException.class, () -> {
			arrayCollection.add(null);
		});
	}
	
	@Test
	void get() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertEquals("a", arrayCollection.get(0));
		assertEquals("b", arrayCollection.get(1));
		assertEquals("c", arrayCollection.get(2));
	}
	
	@Test
	void getIllegalIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			arrayCollection.get(5);
		});
	}
	
	@Test
	void clear() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertEquals(3, arrayCollection.size());
		assertFalse(arrayCollection.isEmpty());
		
		arrayCollection.clear();
		
		assertEquals(0, arrayCollection.size());
		assertTrue(arrayCollection.isEmpty());
	}
	
	@Test
	void indexOf() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertEquals(0, arrayCollection.indexOf("a"));
		assertEquals(1, arrayCollection.indexOf("b"));
		assertEquals(2, arrayCollection.indexOf("c"));
	}
	
	@Test
	void indexOfNonExisting() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertEquals(-1, arrayCollection.indexOf("d"));
	}
	
	@Test
	void indexOfNull() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertEquals(-1, arrayCollection.indexOf(null));
	}
	
	@Test
	void indexOfWhenEmpty() {
		assertEquals(-1, arrayCollection.indexOf("a"));
	}
	
	@Test
	void removeByIndex() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		arrayCollection.remove(1);
		
		assertArrayEquals(new Object[] {"a", "c"}, arrayCollection.toArray());
		assertEquals(2, arrayCollection.size());
	}
	
	@Test
	void removeByIndexFirst() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		arrayCollection.remove(0);
		
		assertArrayEquals(new Object[] {"b", "c"}, arrayCollection.toArray());
		assertEquals(2, arrayCollection.size());
	}
	
	@Test
	void removeByIndexLast() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		arrayCollection.remove(arrayCollection.size() - 1);
		
		assertArrayEquals(new Object[] {"a", "b"}, arrayCollection.toArray());
		assertEquals(2, arrayCollection.size());
	}
	
	@Test
	void removeByIndexIllegalIndex() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			arrayCollection.remove(5);
		});
	}
	
	@Test
	void removeByValue() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertTrue(arrayCollection.remove("b"));
		assertArrayEquals(new Object[] {"a", "c"}, arrayCollection.toArray());
		assertEquals(2, arrayCollection.size());
	}
	
	@Test
	void removeByValueFirst() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertTrue(arrayCollection.remove("a"));
		assertArrayEquals(new Object[] {"b", "c"}, arrayCollection.toArray());
		assertEquals(2, arrayCollection.size());
	}
	
	@Test
	void removeByValueLast() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertTrue(arrayCollection.remove("c"));
		assertArrayEquals(new Object[] {"a", "b"}, arrayCollection.toArray());
		assertEquals(2, arrayCollection.size());
	}
	
	@Test
	void removeByValueWhenMultiple() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("a");
		
		assertTrue(arrayCollection.remove("a"));
		assertArrayEquals(new Object[] {"b", "a"}, arrayCollection.toArray());
		assertEquals(2, arrayCollection.size());
	}
	
	@Test
	void removeByValueNonExisting() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertFalse(arrayCollection.remove("d"));
		assertArrayEquals(new Object[] {"a", "b", "c"}, arrayCollection.toArray());	
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void removeByValueNull() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		
		assertFalse(arrayCollection.remove(null));
		assertArrayEquals(new Object[] {"a", "b", "c"}, arrayCollection.toArray());
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void sizeWhenNotEmpty() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
			
		assertEquals(3, arrayCollection.size());
	}
	
	@Test
	void sizeWhenEmpty() {
		assertEquals(0, arrayCollection.size());
	}
	
	@Test
	void isEmptyWhenNotEmpty() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
			
		assertFalse(arrayCollection.isEmpty());
	}
	
	@Test
	void isEmptyWhenEmpty() {
		assertTrue(arrayCollection.isEmpty());
	}
	
	
	@Test
	void contains() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
			
		assertTrue(arrayCollection.contains("b"));
	}
	
	@Test
	void containsFirst() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
			
		assertTrue(arrayCollection.contains("a"));
	}
	
	@Test
	void containsLast() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
			
		assertTrue(arrayCollection.contains("c"));
	}
	
	@Test
	void containsWhenNonExisting() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
			
		assertFalse(arrayCollection.contains("d"));
	}
	
	@Test
	void containsWhenEmpty() {		
		assertFalse(arrayCollection.contains("a"));
	}
	
	@Test
	void containsNull() {
		assertFalse(arrayCollection.contains(null));
	}
	
	@Test
	void toArray() {
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
			
		assertArrayEquals(new Object[] {"a", "b", "c"}, arrayCollection.toArray());
		assertEquals(3, arrayCollection.toArray().length);
	}
	
	@Test
	void toArrayWhenEmpty() {
		assertArrayEquals(new Object[0], arrayCollection.toArray());
		assertEquals(0, arrayCollection.toArray().length);
	}
	
	@Test
	void addAll() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		other.add("a");
		other.add("b");
		other.add("c");
		
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		arrayCollection.addAll(other);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, other.toArray());
		assertArrayEquals(new Object[] {"a", "b", "c", "a", "b", "c"}, 
				          arrayCollection.toArray()
		);
	}
	
	@Test
	void addAllWhenEmpty() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		other.add("a");
		other.add("b");
		other.add("c");
		
		arrayCollection.addAll(other);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, other.toArray());
		assertArrayEquals(new Object[] {"a", "b", "c"}, arrayCollection.toArray());
	}
	
	@Test
	void addAllWhenOtherEmpty() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		
		arrayCollection.add("a");
		arrayCollection.add("b");
		arrayCollection.add("c");
		arrayCollection.addAll(other);
		
		assertArrayEquals(new Object[] {"a", "b", "c"}, arrayCollection.toArray());
	}
	
}
