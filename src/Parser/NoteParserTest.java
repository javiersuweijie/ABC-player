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
}
