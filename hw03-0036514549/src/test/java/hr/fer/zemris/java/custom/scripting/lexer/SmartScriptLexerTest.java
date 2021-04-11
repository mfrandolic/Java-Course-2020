package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

class SmartScriptLexerTest {

	@Test
	void testExample1() {
		String document = readExample("extra/primjer1");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo je \nsve jedan text node\n"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	void testExample2() {
		String document = readExample("extra/primjer2");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo je \nsve jedan {$ text node\n"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	void testExample3() {
		String document = readExample("extra/primjer3");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo je \nsve jedan \\{$text node\n"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	void testExample4() {
		String document = readExample("extra/primjer4");
		
		assertThrows(SmartScriptLexerException.class, () -> {
			SmartScriptLexer lexer = new SmartScriptLexer(document);
			lexer.nextToken();
		});
	}
	
	@Test
	void testExample5() {
		String document = readExample("extra/primjer5");
		
		assertThrows(SmartScriptLexerException.class, () -> {
			SmartScriptLexer lexer = new SmartScriptLexer(document);
			lexer.nextToken();
		});
	}
	
	@Test
	void testExample6() {
		String document = readExample("extra/primjer6");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo je OK "),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.STRING, "String ide\nu više redaka\nčak tri"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData2);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData3[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData3);
	}
	
	@Test
	void testExample7() {
		String document = readExample("extra/primjer7");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo je isto OK "),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.STRING, "String ide\nu \"više\" \nredaka\novdje a stvarno četiri"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData2);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData3[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData3);
	}
	
	@Test
	void testExample8() {
		String document = readExample("extra/primjer8");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo se ruši "),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "=")
		};
		checkTokenStream(lexer, correctData2);
		
		assertThrows(SmartScriptLexerException.class, () -> {
			lexer.nextToken();
		});
	}
	
	@Test
	void testExample9() {
		String document = readExample("extra/primjer9");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Ovo se ruši "),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "=")
		};
		checkTokenStream(lexer, correctData2);
		
		assertThrows(SmartScriptLexerException.class, () -> {
			lexer.nextToken();
		});
	}
	
	@Test
	void testExampleFromDocument() {
		String document = readExample("doc1");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "This is sample text.\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "for"),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(10)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData2);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData3[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n  This is "),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData3);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData4[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData4);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData5[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "-th time this message is generated.\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData5);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData6[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "end"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData6);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData7[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData7);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData8[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "for"),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(0)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(10)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(2)),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData8);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData9[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n  sin("),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData9);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData10[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData10);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData11[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "^2) = "),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData11);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData12[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"),
			new SmartScriptToken(SmartScriptTokenType.OPERATOR, "*"),
			new SmartScriptToken(SmartScriptTokenType.FUNCTION, "sin"),
			new SmartScriptToken(SmartScriptTokenType.STRING, "0.000"),
			new SmartScriptToken(SmartScriptTokenType.FUNCTION, "decfmt"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData12);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData13[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData13);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData14[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "end"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData14);
	}
	
	@Test
	void testDifferentTagName() {
		String document = readExample("differentTagName");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "my_tag_name"),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "abc"),
			new SmartScriptToken(SmartScriptTokenType.STRING, "string"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(123)),
			new SmartScriptToken(SmartScriptTokenType.FUNCTION, "math"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null),
		};
		checkTokenStream(lexer, correctData2);
	}
	
	@Test
	void testEchoNode() {
		String document = readExample("echoNode");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.valueOf(1.2)),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "abc"),
			new SmartScriptToken(SmartScriptTokenType.STRING, "string"),
			new SmartScriptToken(SmartScriptTokenType.FUNCTION, "function"),
			new SmartScriptToken(SmartScriptTokenType.OPERATOR, "*"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData2);
	}
	
	@Test
	void testEmptyTag() {
		String document = readExample("emptyTag");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData2);
	}
	
	@Test
	void testEscapingInString() {
		String document = readExample("escapingInString");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.STRING, "abc \" abc \n abc \t abc \r abc \\ abc"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData2);
	}
	
	@Test
	void testEscapingOutsideOfTags() {
		String document = readExample("escapingOutsideOfTags");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "abc \\ abc {$ abc { abc { abc $} abc")
		};
		checkTokenStream(lexer, correctData);
	}

	@Test
	void testForLoopWithFourParameters() {
		String document = readExample("forLoopWithFourParameters");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "for"),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "abc"),
			new SmartScriptToken(SmartScriptTokenType.STRING, ""),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(2)),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData2);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData3[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData3);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData4[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "end"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData4);
	}
	
	@Test
	void testForLoopWithNoEndTag() {
		String document = readExample("forLoopWithNoEndTag");
		SmartScriptLexer lexer = new SmartScriptLexer(document);

		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "for"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(2)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(3)),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData2);
	}
	
	@Test
	void testForLoopWithIllegalParameters() {
		String document = readExample("forLoopWithIllegalParameters");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "for"),
			new SmartScriptToken(SmartScriptTokenType.FUNCTION, "sin"),
			new SmartScriptToken(SmartScriptTokenType.OPERATOR, "*"),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "abc"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData2);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData3[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData3);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData4[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "end"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData4);
	}
	
	@Test
	void testForLoopWithThreeParameters() {
		String document = readExample("forLoopWithThreeParameters");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "for"),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "a"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.STRING, "a"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData2);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData3[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData3);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData4[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "end"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData4);
	}
	
	@Test
	void testForLoopWithTooFewParameters() {
		String document = readExample("forLoopWithTooFewParameters");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "for"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(2)),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData2);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData3[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData3);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData4[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "end"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData4);
	}
	
	@Test
	void testForLoopWithTooManyEndTags() {
		String document = readExample("forLoopWithTooManyEndTags");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "for"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(2)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(3)),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData2);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData3[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData3);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData4[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "end"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData4);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData5[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData5);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData6[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "end"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData6);
	}
	
	@Test
	void testForLoopWithTooManyParameters() {
		String document = readExample("forLoopWithTooManyParameters");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "for"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(2)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(3)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(4)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(5)),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData2);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData3[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData3);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData4[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "end"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData4);
	}
	
	@Test
	void testMoreTags() {
		String document = readExample("moreTags");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "Example\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "for"),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "a"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(2)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(3)),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData2);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData3[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n    abc\n    "),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData3);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData4[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "abc"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null)
		};
		checkTokenStream(lexer, correctData4);
		
		lexer.setState(SmartScriptLexerState.TEXT);
		
		SmartScriptToken correctData5[] = {
			new SmartScriptToken(SmartScriptTokenType.TEXT, "\n"),
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData5);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData6[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "end"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData6);
	}
	
	@Test
	void testIllegalEscapingInString() {
		String document = readExample("illegalEscapingInString");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "=")
		};
		checkTokenStream(lexer, correctData2);
		
		assertThrows(SmartScriptLexerException.class, () -> {
			lexer.nextToken();
		});
	}
	
	@Test
	void testIllegalEscapingOutsideOfTags() {
		String document = readExample("illegalEscapingOutsideOfTags");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		assertThrows(SmartScriptLexerException.class, () -> {
			lexer.nextToken();
		});
	}
	
	@Test
	void testIllegalVariableName() {
		String document = readExample("illegalVariableName");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "=")
		};
		checkTokenStream(lexer, correctData2);
		
		assertThrows(SmartScriptLexerException.class, () -> {
			lexer.nextToken();
		});
	}
	
	@Test
	void testNumbersInTag() {
		String document = readExample("numbersInTag");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(1)),
			new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.valueOf(12.12)),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(0)),
			new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.valueOf(0.0)),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "a"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(-2)),
			new SmartScriptToken(SmartScriptTokenType.OPERATOR, "-"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(3)),
			new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.valueOf(-5.6)),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "abc"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null),
		};
		checkTokenStream(lexer, correctData2);
	}
	
	@Test
	void testSpacingInTag() {
		String document = readExample("spacingInTag");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "abc"),
			new SmartScriptToken(SmartScriptTokenType.STRING, "abc123"),
			new SmartScriptToken(SmartScriptTokenType.FUNCTION, "sqrt"),
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(7)),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData2);
	}
	
	@Test
	void testTagWithNoName() {
		String document = readExample("tagWithNoName");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(12)),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData2);
	}
	
	@Test
	void testTooLargeNumber() {
		String document = readExample("tooLargeNumber");
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "=")
		};
		checkTokenStream(lexer, correctData2);
		
		assertThrows(SmartScriptLexerException.class, () -> {
			lexer.nextToken();
		});
	}
	
	@Test
	void testGetTokenAfterEOF() {
		String document = "{$= abc $}";
		SmartScriptLexer lexer = new SmartScriptLexer(document);
		
		SmartScriptToken correctData1[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_OPEN, null)
		};
		checkTokenStream(lexer, correctData1);
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		SmartScriptToken correctData2[] = {
			new SmartScriptToken(SmartScriptTokenType.TAG_NAME, "="),
			new SmartScriptToken(SmartScriptTokenType.VARIABLE, "abc"),
			new SmartScriptToken(SmartScriptTokenType.TAG_CLOSE, null),
			new SmartScriptToken(SmartScriptTokenType.EOF, null),
		};
		checkTokenStream(lexer, correctData2);
		
		assertThrows(SmartScriptLexerException.class, () -> {
			lexer.nextToken();
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
	
	private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for (SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}

}
