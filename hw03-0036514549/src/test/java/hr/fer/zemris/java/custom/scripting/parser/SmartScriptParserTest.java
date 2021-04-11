package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

class SmartScriptParserTest {

	@Test
	void testExample1() {
		String document = readExample("extra/primjer1");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(1, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		TextNode textNode = new TextNode("Ovo je \nsve jedan text node\n");
		expectedDocumentNode.addChildNode(textNode);
		
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();
		
		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testExample2() {
		String document = readExample("extra/primjer2");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(1, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		TextNode textNode = new TextNode("Ovo je \nsve jedan {$ text node\n");
		expectedDocumentNode.addChildNode(textNode);
		
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testExample3() {
		String document = readExample("extra/primjer3");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(1, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		TextNode textNode = new TextNode("Ovo je \nsve jedan \\{$text node\n");
		expectedDocumentNode.addChildNode(textNode);
		
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testExample4() {
		String document = readExample("extra/primjer4");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testExample5() {
		String document = readExample("extra/primjer5");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testExample6() {
		String document = readExample("extra/primjer6");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(3, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		TextNode textNode1 = new TextNode("Ovo je OK ");
		Element[] elements = new Element[] {
			new ElementString("String ide\nu više redaka\nčak tri")
		};
		EchoNode echoNode = new EchoNode(elements);
		TextNode textNode2 = new TextNode("\n");
		expectedDocumentNode.addChildNode(textNode1);
		expectedDocumentNode.addChildNode(echoNode);
		expectedDocumentNode.addChildNode(textNode2);
		
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testExample7() {
		String document = readExample("extra/primjer7");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(3, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		TextNode textNode1 = new TextNode("Ovo je isto OK ");
		Element[] elements = new Element[] {
			new ElementString("String ide\nu \"više\" \nredaka\novdje a stvarno četiri")
		};
		EchoNode echoNode = new EchoNode(elements);
		TextNode textNode2 = new TextNode("\n");
		expectedDocumentNode.addChildNode(textNode1);
		expectedDocumentNode.addChildNode(echoNode);
		expectedDocumentNode.addChildNode(textNode2);
		
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testExample8() {
		String document = readExample("extra/primjer8");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testExample9() {
		String document = readExample("extra/primjer9");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testExampleFromDocument() {
		String document = readExample("doc1");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(4, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		TextNode textNode1 = new TextNode("This is sample text.\n");
		ForLoopNode forLoopNode1 = new ForLoopNode(
			new ElementVariable("i"),
			new ElementConstantInteger(1),
			new ElementConstantInteger(10),
			new ElementConstantInteger(1)
		);
		TextNode textNode2 = new TextNode("\n  This is ");
		Element[] elements1 = new Element[] {
			new ElementVariable("i")
		};
		EchoNode echoNode1 = new EchoNode(elements1);
		TextNode textNode3 = new TextNode("-th time this message is generated.\n");
		TextNode textNode4 = new TextNode("\n");
		ForLoopNode forLoopNode2 = new ForLoopNode(
			new ElementVariable("i"),
			new ElementConstantInteger(0),
			new ElementConstantInteger(10),
			new ElementConstantInteger(2)
		);
		TextNode textNode5 = new TextNode("\n  sin(");
		Element[] elements2 = new Element[] {
			new ElementVariable("i")
		};
		EchoNode echoNode2 = new EchoNode(elements2);
		TextNode textNode6 = new TextNode("^2) = ");
		Element[] elements3 = new Element[] {
			new ElementVariable("i"),
			new ElementVariable("i"),
			new ElementOperator("*"),
			new ElementFunction("sin"),
			new ElementString("0.000"),
			new ElementFunction("decfmt"),
		};
		EchoNode echoNode3 = new EchoNode(elements3);
		TextNode textNode7 = new TextNode("\n");
		expectedDocumentNode.addChildNode(textNode1);
		forLoopNode1.addChildNode(textNode2);
		forLoopNode1.addChildNode(echoNode1);
		forLoopNode1.addChildNode(textNode3);
		expectedDocumentNode.addChildNode(forLoopNode1);
		expectedDocumentNode.addChildNode(textNode4);
		forLoopNode2.addChildNode(textNode5);
		forLoopNode2.addChildNode(echoNode2);
		forLoopNode2.addChildNode(textNode6);
		forLoopNode2.addChildNode(echoNode3);
		forLoopNode2.addChildNode(textNode7);
		expectedDocumentNode.addChildNode(forLoopNode2);
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
    @Test
	void testDifferentTagName() {
		String document = readExample("differentTagName");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testEchoNode() {
		String document = readExample("echoNode");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(1, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		Element[] elements = new Element[] {
			new ElementConstantInteger(1),
			new ElementConstantDouble(1.2),
			new ElementVariable("abc"),
			new ElementString("string"),
			new ElementFunction("function"),
			new ElementOperator("*")
		};
		EchoNode echoNode = new EchoNode(elements);
		expectedDocumentNode.addChildNode(echoNode);
		
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testEmptyTag() {
		String document = readExample("emptyTag");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testEscapingInString() {
		String document = readExample("escapingInString");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(1, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		Element[] elements = new Element[] {
			new ElementString("abc \" abc \n abc \t abc \r abc \\ abc")
		};
		EchoNode echoNode = new EchoNode(elements);
		expectedDocumentNode.addChildNode(echoNode);
		
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testEscapingOutsideOfTags() {
		String document = readExample("escapingOutsideOfTags");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(1, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		TextNode textNode = new TextNode("abc \\ abc {$ abc { abc { abc $} abc");
		expectedDocumentNode.addChildNode(textNode);
		
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}

	@Test
	void testForLoopWithFourParameters() {
		String document = readExample("forLoopWithFourParameters");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(1, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		ForLoopNode forLoopNode = new ForLoopNode(
			new ElementVariable("abc"),
			new ElementString(""),
			new ElementConstantInteger(1),
			new ElementConstantInteger(2)
		);
		TextNode textNode = new TextNode("\n");
		forLoopNode.addChildNode(textNode);
		expectedDocumentNode.addChildNode(forLoopNode);
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testForLoopWithNoEndTag() {
		String document = readExample("forLoopWithNoEndTag");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testForLoopWithIllegalParameters() {
		String document = readExample("forLoopWithIllegalParameters");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testForLoopWithThreeParameters() {
		String document = readExample("forLoopWithThreeParameters");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(1, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		ForLoopNode forLoopNode = new ForLoopNode(
			new ElementVariable("a"),
			new ElementConstantInteger(1),
			new ElementString("a"),
			null
		);
		TextNode textNode = new TextNode("\n");
		forLoopNode.addChildNode(textNode);
		expectedDocumentNode.addChildNode(forLoopNode);
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testForLoopWithTooFewParameters() {
		String document = readExample("forLoopWithTooFewParameters");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testForLoopWithTooManyEndTags() {
		String document = readExample("forLoopWithTooManyEndTags");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testForLoopWithTooManyParameters() {
		String document = readExample("forLoopWithTooManyParameters");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testMoreTags() {
		String document = readExample("moreTags");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(2, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		TextNode textNode1 = new TextNode("Example\n");
		ForLoopNode forLoopNode = new ForLoopNode(
			new ElementVariable("a"),
			new ElementConstantInteger(1),
			new ElementConstantInteger(2),
			new ElementConstantInteger(3)
		);
		TextNode textNode2 = new TextNode("\n    abc\n    ");
		Element[] elements = new Element[] {
			new ElementVariable("abc")
		};
		EchoNode echoNode = new EchoNode(elements);
		TextNode textNode3 = new TextNode("\n");
		forLoopNode.addChildNode(textNode2);
		forLoopNode.addChildNode(echoNode);
		forLoopNode.addChildNode(textNode3);
		expectedDocumentNode.addChildNode(textNode1);
		expectedDocumentNode.addChildNode(forLoopNode);
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testIllegalEscapingInString() {
		String document = readExample("illegalEscapingInString");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testIllegalEscapingOutsideOfTags() {
		String document = readExample("illegalEscapingOutsideOfTags");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testIllegalVariableName() {
		String document = readExample("illegalVariableName");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testNumbersInTag() {
		String document = readExample("numbersInTag");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(1, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		Element[] elements = new Element[] {
			new ElementConstantInteger(1),
			new ElementConstantDouble(12.12),
			new ElementConstantInteger(0),
			new ElementConstantDouble(0.0),
			new ElementVariable("a"),
			new ElementConstantInteger(-2),
			new ElementOperator("-"),
			new ElementConstantInteger(3),
			new ElementConstantDouble(-5.6),
			new ElementVariable("abc"),
		};
		EchoNode echoNode = new EchoNode(elements);
		expectedDocumentNode.addChildNode(echoNode);
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testSpacingInTag() {
		String document = readExample("spacingInTag");
		SmartScriptParser parser = new SmartScriptParser(document);
		DocumentNode actualDocumentNode = parser.getDocumentNode();
		
		assertEquals(1, actualDocumentNode.numberOfChildren());
		
		DocumentNode expectedDocumentNode = new DocumentNode();
		Element[] elements = new Element[] {
			new ElementVariable("abc"),
			new ElementString("abc123"),
			new ElementFunction("sqrt"),
			new ElementConstantInteger(7),
		};
		EchoNode echoNode = new EchoNode(elements);
		expectedDocumentNode.addChildNode(echoNode);
		assertEquals(expectedDocumentNode, actualDocumentNode);
		
		String reproducedDocument = actualDocumentNode.toString();
		SmartScriptParser parser2 = new SmartScriptParser(reproducedDocument);
		DocumentNode reproducedDocumentNode = parser2.getDocumentNode();

		assertEquals(actualDocumentNode, reproducedDocumentNode);
	}
	
	@Test
	void testTagWithNoName() {
		String document = readExample("tagWithNoName");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	@Test
	void testTooLargeNumber() {
		String document = readExample("tooLargeNumber");
		
		assertThrows(SmartScriptParserException.class, () -> {
			new SmartScriptParser(document);
		});
	}
	
	private String readExample(String fileName) {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName + ".txt")) {
			if (is == null) {
				throw new RuntimeException("Datoteka " + fileName + ".txt je nedostupna.");
			}
			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch (IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}
	
}
