package Parser;
import java.util.ArrayList;

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
	Token chord = new Token(TokenType.CHORD_ST, "[");
	Token repeat_start = new Token(TokenType.BAR, "|:");
	Token repeat_end = new Token(TokenType.BAR, ":|");
	Token repeat_one = new Token(TokenType.REPEATNO,"[1");
	Token repeat_two = new Token(TokenType.REPEATNO,"[2");
	Token duplet = new Token(TokenType.DUPLET,"");
	Token triplet = new Token(TokenType.TRIPLET, "");
	Token quadruplet = new Token(TokenType.QUADRUPLET,"");
	Token rest_fixture_half = new Token(TokenType.REST,"z/2");
	Token rest_fixture = new Token(TokenType.REST,"z");
	Token note_fixture1 = new Token(TokenType.NOTE,"A/2");
	Token note_fixture2 = new Token(TokenType.NOTE,"B2");
	Token note_fixture3 = new Token(TokenType.NOTE,"C");
	Token note_fixture4  = new Token(TokenType.NOTE,"D");
	
	

	@Test
	public void testHeader() {
		QueMaster qm = new QueMaster();
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
		assertEquals("It should have the right tick length",12,n.tick_length);
		
		qm.read(note_fixture2);
		NoteEvent n2 = qm.getNoteEvents().get(1);
		assertEquals("It should have the right pitch", 71, n2.pitch);
		assertEquals("It should have the right start tick", 12,n2.start_tick);
		assertEquals("It should have the right tick length", 48,n2.tick_length);
		
		qm.read(rest_fixture);
		qm.read(note_fixture2);
		NoteEvent n3 = qm.getNoteEvents().get(2);
		assertEquals("It should have the right pitch", 71, n3.pitch);
		assertEquals("It should have the right start tick", 84,n3.start_tick);
		assertEquals("It should have the right tick length", 48,n3.tick_length);
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
		assertEquals("It should have the right tick length", 24,n1.tick_length);
		
		assertEquals("It should have the right pitch", 62, n2.pitch);
		assertEquals("It should have the right start tick(note2)", 0,n2.start_tick);
		assertEquals("It should have the right tick length", 24,n2.tick_length);
		
		assertEquals("It should have the right next start time",24,qm.getStartTick());
	}
	
	@Test
	public void testRepeat() {
		QueMaster qm = new QueMaster();
		
		qm.read(note_fixture1); 
		qm.read(note_fixture1); 
		qm.read(rest_fixture); 
		qm.read(note_fixture1); 
		qm.read(note_fixture1); 
		qm.read(repeat_end); 
		assertEquals("It should have 8 note events",8,qm.getNoteEvents().size());
		assertEquals("It should have the right start tick(note2)",144,qm.getStartTick());
		qm.read(note_fixture1);	
		
		qm.read(repeat_start); 
		qm.read(note_fixture3); 
		qm.read(note_fixture3); 
		qm.read(rest_fixture_half); 
		qm.read(note_fixture1); 
		qm.read(repeat_end); 
		assertEquals("It should have the right start tick",300,qm.getStartTick());
		
		qm.read(repeat_start); 
		qm.read(note_fixture3); //start:300 length:24 
		qm.read(note_fixture3);  //start:324 length:24
		qm.read(repeat_one); //start:348
		qm.read(rest_fixture_half); //start:348 length:12
		qm.read(note_fixture1); //start:360 length:12
		qm.read(repeat_end); //start:372 length: 72
		qm.read(repeat_two); 
		qm.read(note_fixture1); 
		qm.read(rest_fixture_half); 
		assertEquals("It should have the right start tick",444,qm.getStartTick());
	}
	
	@Test
	public void testOneTwoRepeat() {
		QueMaster qm = new QueMaster();
		
		qm.read(repeat_start);
		qm.read(note_fixture1); //start:0 length:12
		qm.read(repeat_one); 
		qm.read(note_fixture2); //start:12 length:48
		qm.read(rest_fixture); //start:60 length:24
		qm.read(repeat_end);  //start:84 length:12
		qm.read(repeat_two);
		qm.read(note_fixture1); //start:96 length:12

		assertEquals("It should have the right start tick",108,qm.getStartTick());
		assertEquals("It should have the right notes[0]",12,qm.getNoteEvents().get(0).tick_length);
		assertEquals("It should have the right notes[1]",48,qm.getNoteEvents().get(1).tick_length);
		assertEquals("It should have the right notes[2]",12,qm.getNoteEvents().get(2).tick_length);
		assertEquals("It should have the right notes[3]",12,qm.getNoteEvents().get(3).tick_length);
	}
	
	@Test
	public void testOneTwoRepeatWithVoices() {
		QueMaster qm = new QueMaster();
		qm.read(voice_1);
		qm.read(voice_2);
		
		qm.read(voice_1);
		qm.read(note_fixture1); //start:0 length:12
		qm.read(voice_2);
		qm.read(note_fixture1); //start:0 length:12
		
		qm.read(voice_1);
		qm.read(repeat_one); 
		qm.read(note_fixture2); //start:12 length:48
		qm.read(rest_fixture); //start:60 length:24
		qm.read(repeat_end);  //start:84 length:12
		qm.read(repeat_two);
		qm.read(note_fixture1); //start:96 length:12
		assertEquals("It should have the right start tick",108,qm.getStartTick());
		
		qm.read(voice_2);
		qm.read(repeat_one); 
		qm.read(note_fixture2); //start:12 length:48
		qm.read(rest_fixture); //start:60 length:24
		qm.read(repeat_end);  //start:84 length:12
		qm.read(repeat_two);
		qm.read(note_fixture1); //start:96 length:12
		qm.read(note_fixture1); //start:108 length:12
		
		assertEquals("It should have the right channel",1,qm.getCurrentVoice());
		assertEquals("It should have the right start tick (voice2)",120,qm.getStartTick());
		assertEquals("It should have the right notes[0]",12,qm.getNoteEvents().get(0).tick_length);
		assertEquals("It should have the right note[0] start tick", 0,qm.getNoteEvents().get(0).start_tick);
		assertEquals("It should have the right notes[1]",12,qm.getNoteEvents().get(1).tick_length);
		assertEquals("It should have the right note[1] start tick", 0,qm.getNoteEvents().get(1).start_tick);
		assertEquals("It should have the right notes[2]",48,qm.getNoteEvents().get(2).tick_length);
		assertEquals("It should have the right notes[3]",12,qm.getNoteEvents().get(3).tick_length);
	}
	
	@Test
	public void testRepeatWithTuples() {
		QueMaster qm = new QueMaster();
		
		qm.read(note_fixture3); //start:0 length:24
		qm.read(note_fixture3); //start:24 length:24
		qm.read(repeat_one); //start:48
		qm.read(duplet); 
		qm.read(note_fixture1); //start:48 length:18
		qm.read(note_fixture1); //start:66 length:18
		qm.read(repeat_two);
		qm.read(quadruplet);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(repeat_end); //start:84 length:84
		
		assertEquals("It should have the right start tick",168,qm.getStartTick());
		assertEquals("It should have the right duplet note length",18,qm.getNoteEvents().get(2).tick_length);
		assertEquals("It should have the right quad note length",9,qm.getNoteEvents().get(6).tick_length);
	}
	
	@Test
	public void testTuplesWithChords() {
		QueMaster qm = new QueMaster();
		
		qm.read(triplet);
		qm.read(chord);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(chord);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		assertEquals("It should have right start tick",24, qm.getStartTick());
		qm.read(note_fixture3);
		qm.read(note_fixture3);
		assertEquals("It should have right start tick",72, qm.getStartTick());
		
	}
	
	@Test
	public void restRepeatWithChords() {
		QueMaster qm = new QueMaster();
		qm.read(note_fixture3); //start:0 length:24
		
		qm.read(note_fixture3); //start:24 length:24
		qm.read(repeat_one); //start:48
		qm.read(duplet); 
		qm.read(note_fixture1); //start:48 length:18
		qm.read(note_fixture1); //start:66 length:18
		qm.read(repeat_two);
		qm.read(chord);
		qm.read(note_fixture1);
		qm.read(note_fixture2);
		qm.read(note_fixture3);
		qm.read(note_fixture4);
		qm.read(chord); //length:48
		qm.read(repeat_end); //start:84 length: 96
		
		assertEquals("It should have the right start time",180,qm.getStartTick());
	}
	
	@Test
	public void testTuple() {
		QueMaster qm = new QueMaster();
		
		qm.read(duplet); //DUPLET
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		assertEquals("it should make duplet notes",18,qm.getNoteEvents().get(0).tick_length);
		assertEquals("it should have the right start tick",36,qm.getStartTick());
		
		qm.read(triplet); //TRIPLET
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		assertEquals("it should make triplet notes",8,qm.getNoteEvents().get(4).tick_length);
		assertEquals("it should have the right start tick",60,qm.getStartTick());
		
		qm.read(quadruplet); //QUADRUPLET
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		qm.read(note_fixture1);
		assertEquals("it should have the right start tick",96,qm.getStartTick());
		assertEquals("it should make quadrulet notes",9,qm.getNoteEvents().get(6).tick_length);
	}
	

}
