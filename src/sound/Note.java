package sound;

/**
 * Note is an extension of Pitch. It contains
 * the relative duration of the pitch to be played
 */

public class Note extends Pitch {
  float length;

  public Note(char c, float length){
    super(c);
    this.length = length;
  }
}
