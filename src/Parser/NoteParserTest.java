package Parser;

import org.junit.Test;
import sound.Note;

import static org.junit.Assert.*;

public class NoteParserTest {


  double EPSILON = 0.001;

  @Test
  public void testNoteParse(){
    NoteParser np = new NoteParser();

    Token noteToken = new Token(TokenType.NOTE, "C");
    Note note = np.parse(noteToken);
    assertEquals("It parses the pitch attributes form the token","C" ,note.toPitch().toString());
    assertEquals("It parses the note length from the token",1.0, note.length,EPSILON);

    noteToken = new Token(TokenType.NOTE, "_f'1/2");
    note = np.parse(noteToken);
    assertEquals("It parses the pitch attributes form the token","_f'" ,note.toPitch().toString());
    assertEquals("It parses the note length from the token",0.5, note.length,EPSILON);

    np.setKey("A#m");

    noteToken = new Token(TokenType.NOTE, "G,,2");
    note = np.parse(noteToken);
    assertEquals("It parses the correct pitch from the token","^G,," ,note.toPitch().toString());
    assertEquals("It parses the note length from the token",2, note.length,EPSILON);

    noteToken = new Token(TokenType.NOTE, "_E");
    note = np.parse(noteToken);
    assertEquals("It parses the correct pitch from the token","E" ,note.toPitch().toString());
    assertEquals("It parses the note length from the token",1, note.length,EPSILON);

    noteToken = new Token(TokenType.NOTE, "=b'1/8");
    note = np.parse(noteToken);
    assertEquals("It parses the correct pitch from the token","b'" ,note.toPitch().toString());
    assertEquals("It parses the note length from the token",1.0/8, note.length,EPSILON);
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

     Token rest = new Token(TokenType.REST, "z2");
	 assertEquals("It can handle rests length", 2, np.findNoteLength(rest),0.0);
	 assertEquals("It can handle rests char", 'Z', np.findBaseNote(rest));
	  
	 rest = new Token(TokenType.REST, "z1/2");
	 assertEquals("It can handle float lengths for rest", 0.5, np.findNoteLength(rest), 0.0);
	 assertEquals("It can handle rests char", 'Z', np.findBaseNote(rest));
	 
	 note = new Token(TokenType.NOTE, "G/2");
	 assertEquals(0.5, np.findNoteLength(note), 0.0);
	 
	 note = new Token(TokenType.NOTE, "^F,,/4");
	 assertEquals(0.25, np.findNoteLength(note), 0.0);
	 
	 note = new Token(TokenType.NOTE, "F/");
	 assertEquals(0.5, np.findNoteLength(note),0.0);
  }
}
