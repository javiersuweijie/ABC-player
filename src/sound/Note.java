package sound;

public class Note {
  float length;
  int accidentals;
  char baseNote;
  int octave;

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
