package Parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {
	String fixture = "TEMPO 100\n"
					+ "KEY Cmajor\n"
					+ "LENGTH 1/8\n"
					+ "BAR [|\n"
					+ "NOTE B\n"
					+ "NOTE C2\n"
					+ "NOTE D2\n"
					+ "NOTE E2\n"
					+ "NOTE ^F\n"
					+ "BAR |]";

	@Test
	public void TestSplitter() {
		Parser p = new Parser(fixture);
		assertEquals("Splits correctly into separate Tokens", "TEMPO 100", p.getSplit()[0]);
		assertEquals("Splits correctly into separate Tokens", "KEY Cmajor", p.getSplit()[1]);
		assertEquals("Splits correctly into separate Tokens", "NOTE D2", p.getSplit()[6]);
	}
	
	@Test
	public void testGetNext() {
		Parser p = new Parser(fixture);

		Token current_token = p.next();
		assertEquals("it returns the current Token Value", new Token(TokenType.TEMPO, "100").value, current_token.value);
		assertEquals("it returns the current Token Type", new Token(TokenType.TEMPO, "100").type, current_token.type);

		current_token = p.next();
		assertEquals("it returns the next Token Value", new Token(TokenType.KEY, "Cmajor").value, current_token.value);
		assertEquals("it returns the next Token Type", new Token(TokenType.KEY, "Cmajor").type, current_token.type);

	}

}
