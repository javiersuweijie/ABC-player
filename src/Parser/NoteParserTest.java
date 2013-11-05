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
    assertEquals("It does not change the octave",2, np.findOctave(note));

    note = new Token(TokenType.NOTE, "c");
    assertEquals("It does not change the octave",1, np.findOctave(note));
  }
}
