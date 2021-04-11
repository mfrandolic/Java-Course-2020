package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	public void testHextobyteWhenUppercase() {
		byte[] actual1 = Util.hextobyte("32A42FBB");
		byte[] expected1 = new byte[] {50, -92, 47, -69};
		assertArrayEquals(expected1, actual1);
		
		byte[] actual2 = Util.hextobyte("65D992B89B");
		byte[] expected2 = new byte[] {101, -39, -110, -72, -101};
		assertArrayEquals(expected2, actual2);
		
		byte[] actual3 = Util.hextobyte("AD1D211E");
		byte[] expected3 = new byte[] {-83, 29, 33, 30};
		assertArrayEquals(expected3, actual3);
	}
	
	@Test
	public void testHextobyteWhenLowercase() {
		byte[] actual1 = Util.hextobyte("abcd");
		byte[] expected1 = new byte[] {-85, -51};
		assertArrayEquals(expected1, actual1);
		
		byte[] actual2 = Util.hextobyte("4f02b7");
		byte[] expected2 = new byte[] {79, 2, -73};
		assertArrayEquals(expected2, actual2);
		
		byte[] actual3 = Util.hextobyte("07123de8");
		byte[] expected3 = new byte[] {7, 18, 61, -24};
		assertArrayEquals(expected3, actual3);
	}
	
	@Test
	public void testHextobyteWhenUppercaseAndLowercase() {
		byte[] actual1 = Util.hextobyte("01aE22");
		byte[] expected1 = new byte[] {1, -82, 34};
		assertArrayEquals(expected1, actual1);
		
		byte[] actual2 = Util.hextobyte("eF39fD19");
		byte[] expected2 = new byte[] {-17, 57, -3, 25};
		assertArrayEquals(expected2, actual2);
		
		byte[] actual3 = Util.hextobyte("8fE552C7ed63");
		byte[] expected3 = new byte[] {-113, -27, 82, -57, -19, 99};
		assertArrayEquals(expected3, actual3);
	}
	
	@Test
	public void testHextobyteWhenZeroLength() {
		assertArrayEquals(new byte[0], Util.hextobyte(""));
	}
	
	@Test
	public void testHextobyteWhenOddLength() {
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("a");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("3BC");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("F78b2");
		});
	}
	
	@Test
	public void testHextobyteWhenInvalidCharacters() {
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("a4br");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("5GF390");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("m6J2f134");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hextobyte("12$abc");
		});
	}
	
	@Test
	public void testBytetohex() {
		assertEquals("ff0e10e801bd", Util.bytetohex(new byte[] {-1, 14, 16, -24, 1, -67}));
		assertEquals("1b88d7cb", Util.bytetohex(new byte[] {27, -120, -41, -53}));
		assertEquals("daa0f8e7", Util.bytetohex(new byte[] {-38, -96, -8, -25}));
		assertEquals("7a1dcd", Util.bytetohex(new byte[] {122, 29, -51}));
		assertEquals("a2", Util.bytetohex(new byte[] {-94}));
		assertEquals("0a02", Util.bytetohex(new byte[] {10, 2}));
	}
	
	@Test
	public void testBytetohexWhenZeroLength() {
		assertEquals("", Util.bytetohex(new byte[0]));
	}
	
}
