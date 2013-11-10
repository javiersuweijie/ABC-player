package Parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class NoteParserTest {
  @Test
  public void testNoteParse(){
    Token note = new Token(TokenType.NOTE, "C");
    NoteParser np = new NoteParser();
    //assertEquals("It Parses the Note Correctly", np.parse(note).toString(),"");
  }

  @Test
  
  public void testFindOctave(){
    NoteParser np = new NoteParser();

    Token note = new Token(TokenType.NOTE, "C");
    assertEquals("It does not change the octave",0, np.findOctave(note));

    note = new Token(TokenType.NOTE, "C,");
    assertEquals("It reduces the octave by 1", -1, np.findOctave(note));

    note = new Token(TokenType.NOTE, "c'");
    assertEquals("It increases the octave by 2",2, np.findOctave(note));

    note = new Token(TokenType.NOTE, "c");
    assertEquals("It does not change the octave",1, np.findOctave(note));

    note = new Token(TokenType.NOTE, "c'''");
    assertEquals("It increases the octave by 4",4, np.findOctave(note));

    note = new Token(TokenType.NOTE, "C,,,");
    assertEquals("It reduces the octave by -3",-3, np.findOctave(note));
  }

  @Test
  public void testGetBaseNote(){
    NoteParser np = new NoteParser();

    Token note = new Token(TokenType.NOTE, "C");
    assertEquals("The note does not change",'C', np.findBaseNote(note));

    note = new Token(TokenType.NOTE, "g");
    assertEquals("The Note is capitalized", 'G', np.findBaseNote(note));
  }

  @Test
  public void testGetAccidentals(){
    NoteParser np = new NoteParser();

    Token note = new Token(TokenType.NOTE, "_C");
    assertEquals("It reduces the accidental by 1", -1, np.findAccidentals(note, 'C'));

    note = new Token(TokenType.NOTE, "^C");
    assertEquals("It increases the accidental by 1", 1, np.findAccidentals(note, 'C'));

    note = new Token(TokenType.NOTE, "^^C");
    assertEquals("It increases the accidental by 1", 2, np.findAccidentals(note, 'C'));

    np.setKey("D");
    note = new Token(TokenType.NOTE, "c");
    assertEquals("It increases the accidental by 1", 1, np.findAccidentals(note, 'C'));
    note = new Token(TokenType.NOTE, "=c");
    assertEquals("It does not change the accidental", 0, np.findAccidentals(note, 'C'));
    note = new Token(TokenType.NOTE, "^c");
    assertEquals("It does not change the accidental", 2, np.findAccidentals(note, 'C'));
    note = new Token(TokenType.NOTE, "_c");
    assertEquals("It does not change the accidental", 0, np.findAccidentals(note, 'C'));
  }
  
  @Test
  public void getNoteLength(){
	  
	 Token note = new Token(TokenType.NOTE, "C3");
	 NoteParser np = new NoteParser();
	 assertEquals(3.0, np.findNoteLength(note), 0.0);
	 
	 note = new Token(TokenType.NOTE, "c1/2");
	 
	 assertEquals(0.5, np.findNoteLength(note), 0.0);
	 
	 note = new Token(TokenType.NOTE, "^^C3");
	 assertEquals(3.0, np.findNoteLength(note), 0.0);
	 
	 note = new Token(TokenType.NOTE, "_c'2");
	 assertEquals(2.0, np.findNoteLength(note), 0.0);
	 
	 note = new Token(TokenType.NOTE, "_G'2");
	 assertEquals(2.0, np.findNoteLength(note), 0.0);
  }
  
@Test
  public void testRest() {
	  Token rest = new Token(TokenType.REST, "z2");
	  NoteParser np = new NoteParser(); 
	  assertEquals("It can handle rests length", 2, np.findNoteLength(rest),0.0);
	  assertEquals("It can handle rests char", 'Z', np.findBaseNote(rest));
	  
	  rest = new Token(TokenType.REST, "z1/2");
	  assertEquals("It can handle float lengths for rest", 0.5, np.findNoteLength(rest), 0.0);
	  assertEquals("It can handle rests char", 'Z', np.findBaseNote(rest));
	  
  }  
}