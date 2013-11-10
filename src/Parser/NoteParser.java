package Parser;

import sound.Note;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a Note Token into a Note Object
 */

public class NoteParser {
  String current_key;

  public NoteParser() {
    this.current_key = "C";
  }

  public void setKey(String Key) {
    this.current_key = Key;
  }

  public Note parse(Token note){
   // Pattern length = Pattern.compile("/?[\\d]*");

    int octave = findOctave(note);
    char baseNote = findBaseNote(note);
    int accidentals = findAccidentals(note, baseNote);
    float length = findNoteLength(note);
    
    return new Note(length, accidentals, baseNote, octave);
    
  }

  int findOctave(Token note){
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

  char findBaseNote(Token note) {
    Pattern letter = Pattern.compile("[a-gA-GzZ]");
    Matcher matcher = letter.matcher(note.value);
    char baseNote = 'C';
    if (matcher.find()) baseNote = matcher.group().toUpperCase().charAt(0);
    return baseNote;
  }

  int findAccidentals(Token note, char baseNote) {
    String shiftedNote = Keys.getKey(current_key).get(""+baseNote);
    return findAccidentals(note.value+shiftedNote);
  }

  int findAccidentals(String note){
    Pattern accidentalsPattern = Pattern.compile("[=_^]");
    Matcher matcher = accidentalsPattern.matcher(note);
    int accidentals = 0;
    while(matcher.find()){
      char accidental = matcher.group().charAt(0);

      if(accidental == '=') {
        accidentals = 0;
        break;
      }
      if(accidental == '_') accidentals -=1;
      else accidentals +=1;
    }
    return accidentals;
  }
  
  float findNoteLength(Token note){
	  	
	  	Pattern lengthPatternWhole = Pattern.compile("([1-9]+)");
		Pattern lengthPatternFraction = Pattern.compile("([1-9]*)" + "(\\/)" + "([1-9]*)");
		
		Matcher matcher = lengthPatternFraction.matcher(note.value);
		Matcher matcher1 = lengthPatternWhole.matcher(note.value);
		
		if (matcher.find()){
			String numerator = matcher.group(1);
			String denominator = matcher.group(3);
			float num = Float.parseFloat(numerator);
			float den = Float.parseFloat(denominator);
			float lengthFraction = num / den;
			return lengthFraction;
			
		}
		
		else if (matcher1.find()){
			String multiplier = matcher1.group(1);
			float lengthWhole = Float.parseFloat(multiplier);
			return lengthWhole;
		}
		
		else{
			float one = 1;
			return one;
		}
	  
  }
}
