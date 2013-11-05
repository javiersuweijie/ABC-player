package Parser;

import sound.Note;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a Note Token into a Note Object
 */

public class NoteParser {
  String current_key;

  public Note parse(Token note){
    Pattern accidentals = Pattern.compile("[=_^]");
    Pattern letter = Pattern.compile("[\\w]");
    Pattern length = Pattern.compile("/?[\\d]*");

    int octave = findOctave(note);
    return new Note('C',1);
  }

  public int findOctave(Token note){
    Pattern octave_modifier = Pattern.compile("([(A-G)(a-g)])([,']*)");
    Matcher matcher = octave_modifier.matcher(note.value);

    int octave = 0;
    if (matcher.find()){
      String modifiers = matcher.group(2);
      char letter = matcher.group(1).charAt(0);

      if (letter >= 'a') octave++;
      if (!modifiers.isEmpty()) {
        int length = modifiers.length();
        length *= modifiers.charAt(0) ==','? -1:1;
        octave += length;
      }
    }
    return octave;
  }
}
