package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

class LSystemBuilderImplTest {

	@Test
	public void testGenerate() {
		LSystem ls = new LSystemBuilderImpl()
				.setAxiom("F")
				.registerProduction('F', "F+F--F+F")
				.build();
		
		assertEquals("F", ls.generate(0));
		assertEquals("F+F--F+F", ls.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", ls.generate(2));
	}
	
	@Test
	public void testConfigureFromText() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F"
		};
		
		LSystem ls = new LSystemBuilderImpl()
				.configureFromText(text)
				.build();
		
		assertEquals("F", ls.generate(0));
		assertEquals("F+F--F+F", ls.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", ls.generate(2));
	}
	
	@Test
	public void testExtraWhitespace() {
		String[] text = new String[] {
				"",
				"  axiom \n F	",
				"  ",
				" 		production   F   	F+F--F+F \n ",
				"	"
		};
		
		LSystem ls = new LSystemBuilderImpl()
				.configureFromText(text)
				.build();
		
		assertEquals("F", ls.generate(0));
		assertEquals("F+F--F+F", ls.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", ls.generate(2));
	}
	
	@Test
	public void testUnitLengthDegreeScaler1() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"unitLengthDegreeScaler 1.0 / 2.0"
		};
		
		LSystem ls = new LSystemBuilderImpl()
				.configureFromText(text)
				.build();
		
		assertEquals("F", ls.generate(0));
		assertEquals("F+F--F+F", ls.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", ls.generate(2));
	}
	
	@Test
	public void testUnitLengthDegreeScaler2() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"unitLengthDegreeScaler 1.0/2.0"
		};
		
		LSystem ls = new LSystemBuilderImpl()
				.configureFromText(text)
				.build();
		
		assertEquals("F", ls.generate(0));
		assertEquals("F+F--F+F", ls.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", ls.generate(2));
	}
	
	@Test
	public void testUnitLengthDegreeScaler3() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"unitLengthDegreeScaler 1.0/ 2.0"
		};
		
		LSystem ls = new LSystemBuilderImpl()
				.configureFromText(text)
				.build();
		
		assertEquals("F", ls.generate(0));
		assertEquals("F+F--F+F", ls.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", ls.generate(2));
	}
	
	@Test
	public void testUnitLengthDegreeScaler4() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"unitLengthDegreeScaler 1.0 /2.0"
		};
		
		LSystem ls = new LSystemBuilderImpl()
				.configureFromText(text)
				.build();
		
		assertEquals("F", ls.generate(0));
		assertEquals("F+F--F+F", ls.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", ls.generate(2));
	}
	
	@Test
	public void testUnknownAction() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"command + move 0.4"
		};
		
		assertThrows(IllegalArgumentException.class, () -> {
			new LSystemBuilderImpl().configureFromText(text).build();
		});
	}
	
	@Test
	public void testInvalidActionFormat() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"command + rotate 60 50"
		};
		
		assertThrows(IllegalArgumentException.class, () -> {
			new LSystemBuilderImpl().configureFromText(text).build();
		});
	}
	
	@Test
	public void testInvalidActionValue() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"command + rotate abc"
		};
		
		assertThrows(IllegalArgumentException.class, () -> {
			new LSystemBuilderImpl().configureFromText(text).build();
		});
	}
	
	@Test
	public void testUnknownInstruction() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"abc"
		};
		
		assertThrows(IllegalArgumentException.class, () -> {
			new LSystemBuilderImpl().configureFromText(text).build();
		});
	}
	
	@Test
	public void testMultipleProductionsForSameSymbol() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"production F F-F"
		};
		
		assertThrows(IllegalArgumentException.class, () -> {
			new LSystemBuilderImpl().configureFromText(text).build();
		});
	}
	
	@Test
	public void testMultipleActionsForSameSymbol() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"command + rotate 60",
				"command + rotate 50"
		};
		
		assertThrows(IllegalArgumentException.class, () -> {
			new LSystemBuilderImpl().configureFromText(text).build();
		});
	}
	
	@Test
	public void testInvalidInstructionFormat() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"unitLengthDegreeScaler 1.0 / 2.0 abc"
		};
		
		assertThrows(IllegalArgumentException.class, () -> {
			new LSystemBuilderImpl().configureFromText(text).build();
		});
	}
	
	@Test
	public void testInvalidInstructionValues() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"unitLengthDegreeScaler abc / 2.0"
		};
		
		assertThrows(IllegalArgumentException.class, () -> {
			new LSystemBuilderImpl().configureFromText(text).build();
		});
	}
	
	@Test
	public void testActionForString() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"command abc rotate 50"
		};
		
		assertThrows(IllegalArgumentException.class, () -> {
			new LSystemBuilderImpl().configureFromText(text).build();
		});
	}
	
	@Test
	public void testProductionForString() {
		String[] text = new String[] {
				"axiom F",
				"production F F+F--F+F",
				"production abc aabbcc"
		};
		
		assertThrows(IllegalArgumentException.class, () -> {
			new LSystemBuilderImpl().configureFromText(text).build();
		});
	}

}
