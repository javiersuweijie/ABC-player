package sound;

/**
 * Note is an extension of Pitch. It contains
 * the relative duration of the pitch to be played
 */

public class Note {
  public float length;
  public int accidentals;
  public char baseNote;
  public int octave;

  public Note(float length, int accidentals, char baseNote, int octave){
    this.length = length;
    this.accidentals = accidentals;
    this.baseNote = baseNote;
    this.octave = octave;
  }

  public Pitch toPitch() {
    Pitch pitch = new Pitch(baseNote);
    pitch = pitch.octaveTranspose(octave).accidentalTranspose(accidentals);
    return pitch;
  }
}
