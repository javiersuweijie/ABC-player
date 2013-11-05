package Parser;

import org.junit.Test;
import static org.junit.Assert.*;

public class NoteParserTest {
  @Test
  public void testNoteParse(){
    Token note = new Token(TokenType.NOTE, "C");
    NoteParser np = new NoteParser();
    assertEquals("It Parses the Note Correctly", np.parse(note).toMidiNote(),60);
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
    assertEquals("The Note is capitalized",'G', np.findBaseNote(note));
  }
}