package sound;

import static org.junit.Assert.*;
import org.junit.Test;

public class NoteTest {
  @Test
  public void noteGetPitchTest() {
    Note note = new Note(0,0,'C',0);
    Pitch p = note.toPitch();
    assertEquals("the note renders the correct pitch object","C",p.toString());

    note = new Note(0,0,'C',1);
    p = note.toPitch();
    assertEquals("the note renders the correct pitch object","c",p.toString());

    note = new Note(0,1,'D',-1);
    p = note.toPitch();
    assertEquals("the note renders the correct pitch object","^D,",p.toString());

    note = new Note(0,2,'F',3);
    p = note.toPitch();
    assertEquals("the note renders the correct pitch object","^^f''",p.toString());
  }
}
