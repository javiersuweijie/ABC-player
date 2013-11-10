package Parser;

import sound.Note;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a Note Token into a Note Object
 * 
 */

public class NoteParser {
  String current_key;

  public NoteParser() {
    this.current_key = "C";
  }

  public void setKey(String Key) {
    this.current_key = Key;
  }

  /**
   * Takes in a note token as the argument and parse 
   * the accidentals, octave, basenote, and length values
   * from the value of the token.
   * 
   * The method returns a new note object with the corresponding
   * attributes according to the value of the note token.
   * 
   * @param Token note
   * @return Note(length, accidentals, baseNote, octave)
   */
  public Note parse(Token note){
    int octave = findOctave(note);
    char baseNote = findBaseNote(note);
    int accidentals = findAccidentals(note, baseNote);
    float length = findNoteLength(note);
    
    return new Note(length, accidentals, baseNote, octave);
  }
  
  /**
   * Parse the Token value and returns the number octave the token is away 
   * from the middle 'C'
   * 
   * @param Token note
   * @return int octave 
   */
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

  /**
   * Parse the Token note and returns the base Note (A to G) in character form
   * so that it can generate the Pitch which takes in the character (A to G)
   * as its argument
   * 
   * @param Token note
   * @return char BaseNote
   */
  
  char findBaseNote(Token note) {
    Pattern letter = Pattern.compile("[a-gA-GzZ]");
    Matcher matcher = letter.matcher(note.value);
    char baseNote = 'C';
    if (matcher.find()) baseNote = matcher.group().toUpperCase().charAt(0);
    return baseNote;
  }
  
  /**
   * Get the transposed note according to the current key and then
   * combine the new note with the old note string to be fed to
   * findAccidentals (String note) and get the value of the transpose
   * from the basenote
   * 
   * @param Token note
   * @param  char baseNote
   * @return int accidental
   */

  int findAccidentals(Token note, char baseNote) {
	 
    String shiftedNote = Keys.getKey(current_key).get(""+baseNote);
    return findAccidentals(note.value+shiftedNote);
  }

  /**
   * This method calculates how much a note is transposed
   * from the basenote. 
   * 
   * @param Token note
   * @return int accidental
   */
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
  
  /**
   * Takes in a note token and calculate the note length of 
   * the corresponding value in the token. There are three cases 
   * on the method, firstly is to tackle fractions, secondly is to 
   * tackle fractions without numerator, and thirdly is
   * to tackle integers.
   * 
   * @param Token note
   * @return float notelength
   */
  float findNoteLength(Token note){

	  	
	  	Pattern lengthPatternWhole = Pattern.compile("([1-9]+)");
		Pattern lengthPatternFraction = Pattern.compile("([1-9]+)" + "(\\/)" + "([1-9]+)");
		Pattern lengthPatternFraction2 = Pattern.compile("(\\/)" + "([1-9]+)");

   

		
		Matcher matcher = lengthPatternFraction.matcher(note.value);
		Matcher matcher1 = lengthPatternWhole.matcher(note.value);
		Matcher matcher2 = lengthPatternFraction2.matcher(note.value);
		
		
		if (matcher.find()){
			String numerator = matcher.group(1);
			String denominator = matcher.group(3);
			float num = Float.parseFloat(numerator);
			float den = Float.parseFloat(denominator);
			float lengthFraction = num / den;
			return lengthFraction;
			
		}
	
		
		else if(matcher2.find()){
			String denominator = matcher2.group(2);
			float den = Float.parseFloat(denominator);
			float one =1;
			float answer = one / den;
			return answer;
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
