package Parser;

import sound.Note;

import java.util.regex.Pattern;

/**
 * Parses a Note Token into a Note Object
 */

public class NoteParser {
  String current_key;
  public Note parse(Token note){
    String note_value = note.value;
    Pattern letter = Pattern.compile("\\w");
    return new Note('C',1);
  }
}
