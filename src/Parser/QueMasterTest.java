package Parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueMasterTest {
	Token tempo = new Token(TokenType.TEMPO,"120");
	Token title = new Token(TokenType.TITLE,"Valse");
	Token meter = new Token(TokenType.METER,"3/4");
	Token length = new Token(TokenType.LENGTH,"1/4");
	Note note_fixture1 = new Note(23,0.5);
	Note note_fixture2 = new Note(24,2);
	Note note_fixture3 = new Note(1,1);
	

	@Test
	public void testHeader() {
		
		assertEquals("","","");
	}

}
