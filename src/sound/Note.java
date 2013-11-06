package sound;

/**
 * Note is an extension of Pitch. It contains
 * the relative duration of the pitch to be played
 */

public class Note extends Pitch {
  float length;
  int accidentals;
  char baseNote;
  int octave;

  public Note(float length, int accidentals, char baseNote, int octave){
    super(baseNote);
    this.length = length;
    this.accidentals = accidentals;
    this.baseNote = baseNote;
    this.octave = octave;
    
  }
}
