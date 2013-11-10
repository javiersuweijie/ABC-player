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
					+ "BAR |]\n"
					+ "CHORD_ST [\n"
					+ "NOTE E\n"
					+ "NOTE C\n"
					+ "CHORD_END ]";


	@Test
	public void TestSplitter() {
		Parser p = new Parser(fixture);
		assertEquals("Splits correctly into separate Tokens", "TEMPO 100", p.getSplit()[0]);
		assertEquals("Splits correctly into separate Tokens", "KEY Cmajor", p.getSplit()[1]);
		assertEquals("Splits correctly into separate Tokens", "NOTE D2", p.getSplit()[6]);
		assertEquals("it splits into the right numbers", 14,p.getSplit().length);
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
	
	@Test
	public void testIterator() {
		Parser p = new Parser(fixture);
		Token current_token = null;
		int count = 0;
		do {
			current_token = p.next();
			System.out.println(current_token);
			++count;
		} while (current_token != null);
		assertEquals("it returns the next Token Type", new Token(TokenType.CHORD_END, "]").type, current_token.type);
		assertEquals("it should have the right number", 14,count);
	}
}
