package sound;

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
  
  public Note(float length, char baseNote) {
	  this.length = length;
	  this.baseNote = baseNote;
	  this.octave = 0;
	  this.accidentals = 0;
  }

  public boolean isRest() {
	  if (this.baseNote == 'z' || this.baseNote == 'Z') {
		  return true;
	  }
	  return false;
  }
  public Pitch toPitch() {
    Pitch pitch = new Pitch(baseNote);
    pitch = pitch.octaveTranspose(octave).accidentalTranspose(accidentals);
    return pitch;
  }
}
