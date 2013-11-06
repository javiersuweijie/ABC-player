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
	Token voice_1 = new Token(TokenType.VOICE,"1");
	Token voice_2 = new Token(TokenType.VOICE, "2");
	Token rest = new Token(TokenType.REST, "3");
	Token chord = new Token(TokenType.CHORD, "start");
	Token repeat_start = new Token(TokenType.BAR, "|:");
	Token repeat_end = new Token(TokenType.BAR, ":|");
	Note note_fixture1 = new Note('A',(float) 0.5);
	Note note_fixture2 = new Note('B',2);
	Note note_fixture3 = new Note('C',1);
	Note note_fixture4 = new Note('D',1);
	

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
		
		assertEquals("it should have default voice at 0",0,qm.getCurrentVoice());
		qm.read(voice_1);
		assertEquals("it should have 1 channel",1,qm.getVoiceChannels().size());
		qm.read(voice_2);
		assertEquals("it should have 2 channels",2,qm.getVoiceChannels().size());
		assertEquals("it should have current_voice at 1",1,qm.getCurrentVoice());
	}
	
	@Test
	public void testNoteEventCreator() {
		QueMaster qm = new QueMaster();
		assertEquals("It should have 1 channel", 1,qm.getVoiceChannels().size());
		qm.read(note_fixture1);
		NoteEvent n = qm.getNoteEvents().get(0);
		assertEquals("It should have the right pitch", 69, n.pitch);
		assertEquals("It should have the right start tick", 0,n.start_tick);
		assertEquals("It should have the right tick length",2,n.tick_length);
		
		qm.read(note_fixture2);
		NoteEvent n2 = qm.getNoteEvents().get(1);
		assertEquals("It should have the right pitch", 71, n2.pitch);
		assertEquals("It should have the right start tick", 2,n2.start_tick);
		assertEquals("It should have the right tick length", 8,n2.tick_length);
		
		qm.read(rest);
		qm.read(note_fixture2);
		NoteEvent n3 = qm.getNoteEvents().get(2);
		assertEquals("It should have the right pitch", 71, n3.pitch);
		assertEquals("It should have the right start tick", 22,n3.start_tick);
		assertEquals("It should have the right tick length", 8,n3.tick_length);
	}
	
	@Test
	public void testChordCreation() {
		QueMaster qm = new QueMaster();
		qm.read(chord);
		qm.read(note_fixture3);
		qm.read(note_fixture4);
		qm.read(chord);
		NoteEvent n1 = qm.getNoteEvents().get(0);
		NoteEvent n2 = qm.getNoteEvents().get(1);
		assertEquals("It should have the right pitch", 60, n1.pitch);
		assertEquals("It should have the right start tick(note1)", 0,n1.start_tick);
		assertEquals("It should have the right tick length", 4,n1.tick_length);
		
		assertEquals("It should have the right pitch", 62, n2.pitch);
		assertEquals("It should have the right start tick(note2)", 0,n2.start_tick);
		assertEquals("It should have the right tick length", 4,n2.tick_length);
	}
	
	@Test
	public void testRepeat() {
		QueMaster qm = new QueMaster();
		
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(repeat_end);
		assertEquals("It should have 8 note events",8,qm.getNoteEvents().size());
		NoteEvent n5 = qm.noteEventCreator(note_fixture1);
		assertEquals("It should have the right start tick(note2)",16,n5.start_tick);
	}
	

}
