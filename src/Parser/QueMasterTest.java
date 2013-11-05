package Parser;
import sound.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class QueMasterTest {
	private static final double DELTA = 1e-15;
	
	Token tempo = new Token(TokenType.TEMPO,"120");
	Token title = new Token(TokenType.TITLE,"Valse");
	Token meter = new Token(TokenType.METER,"3/4");
	Token length = new Token(TokenType.LENGTH,"1/4");
	Note note_fixture1 = new Note('A',(float) 0.5);
	Note note_fixture2 = new Note('B',2);
	Note note_fixture3 = new Note('C',1);
	

	@Test
	public void testHeader() {
		QueMaster qm = new QueMaster();
		assertEquals("it has proper default tempo", 60, qm.getTempo());
		qm.read(tempo);
		assertEquals("it sets tempo correctly", 120, qm.getTempo());
		qm.read(length);
		assertEquals("it sets length correctly", (float)0.25, qm.getLength(),DELTA);
		qm.read(meter);
		assertEquals("it sets meter correctly", 0.75,qm.getMeter(),DELTA);
	}
	
	@Test
	public void testNoteEventCreator() {
		QueMaster qm = new QueMaster();
		int voice = 1;
		NoteEvent n = qm.queCreator(note_fixture1,voice);
		assertEquals("It should have the right pitch", 69, n.pitch);
		assertEquals("It should have the right start tick", 0,n.start_tick);
		assertEquals("It should have the right end tick",4,n.end_tick);
	}

}
