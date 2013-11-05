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
    Pattern octave_modifier = Pattern.compile("([\\w])([,']?)");
    Matcher matcher = octave_modifier.matcher(note.value);

    int octave = 0;
    if (matcher.find()){
      char letter = matcher.group(1).charAt(0);   //The letter in the note
      char modifier = matcher.group(2).isEmpty()?'x':matcher.group(2).charAt(0); //The Symbol that follows the letter
      System.out.println(modifier);
      if (letter > 'Z') octave++;
      if (modifier == '\'') octave++;
      else if (modifier == ',') octave--;
    }
    return octave;
  }
}
