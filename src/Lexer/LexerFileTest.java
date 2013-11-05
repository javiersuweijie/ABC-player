package Lexer;

import static org.junit.Assert.*;

import org.junit.Test;

import Lexer.Token.TokenType;

public class LexerFileTest {

	@Test
	public void testComposer() {
		LexerFile testLexer=new LexerFile("C:Ludwig van Beethoven\n");
		TokenType expectedTokenType=TokenType.COMPOSER;
		String expectedToTokenName="Ludwing van Beethoven";
		Token currentToken=testLexer.next();
		assertEquals(expectedTokenType, currentToken.getTokenType());
		assertEquals(expectedToTokenName, currentToken.getTokenName());
	}

}
