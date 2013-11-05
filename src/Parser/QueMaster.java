package Parser;
import sound.*;
/** QueMaster handles all rhythm and note related 
 * tokens to create an array of NoteEvents
 * 
 *
 */
public class QueMaster {
	
	private int tempo;
	private float meter;
	private float length;
	private int duplet;
	private int triplet;
	private int quadruplet;
	//private ArrayList<NoteEvent>;
/** The QueMaster is constructed with default tempo, meter and length
 * default tempo = 60, meter = 4/4, length = 1/8
 * 	
 */
	public QueMaster() {
		this.tempo = 60;
		this.meter = 1;
		this.length = 1/8;
	}

	public int getTempo() {
		return this.tempo;
	}
	
	public float getMeter() {
		return this.meter;
	}
	
	public float getLength() {
		return this.length;
	}
	/** Reads a token to change the state of the QueMaster
	 * 
	 * @param Token t
	 * The tokens that it can read are music notation related ones.
	 * eg. METER, TEMPO, LENGTH
	 */
	public void read(Token t) {
		switch (t.type) {
		case TEMPO: this.tempo = Integer.valueOf(t.value);
					break;
		case METER: if (t.value.length()>2) {
						String[] s = t.value.split("/");
						this.meter = Float.valueOf(s[0])/Float.valueOf(s[1]);
					}
					else this.meter = Float.valueOf(t.value);
					break;
		case LENGTH: if (t.value.length()>2) {
						String[] s = t.value.split("/");
						this.length = Float.valueOf(s[0])/Float.valueOf(s[1]);
					}
					else this.length = Float.valueOf(t.value);
					 break;
		default: break;
		}
	}
	
	public void read(Note n) {
		
	}
	
	
}
