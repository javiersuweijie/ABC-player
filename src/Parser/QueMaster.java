package Parser;
import java.util.ArrayList;

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
	private boolean chord;
	private boolean bar_repeat;
	private ArrayList<Integer> voice_channels;
	private ArrayList<Note> note_storage;
	private ArrayList<NoteEvent> noteEventList;
	private int current_channel;
	
	//private ArrayList<NoteEvent>;
/** The QueMaster is constructed with default tempo, meter and length
 * default tempo = 60, meter = 4/4, length = 1/8
 * 	
 */
	public QueMaster() {
		this.tempo = 60;
		this.meter = 1;
		this.length = (float)1/8;
		this.voice_channels = new ArrayList<Integer>();
		this.note_storage = new ArrayList<Note>();
		this.noteEventList = new ArrayList<NoteEvent>();
		this.voice_channels.add(0);
		this.current_channel = 0;
		this.chord = false;
		this.bar_repeat = false;
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
	
	public int getCurrentVoice() {
		return this.current_channel;
	}
	

	public ArrayList<NoteEvent> getNoteEvents() {
		return this.noteEventList;
	}
	
	public ArrayList<Integer> getVoiceChannels() {
		return this.voice_channels;
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
						 
			case VOICE: int v = Integer.valueOf(t.value);
						while (v>voice_channels.size()) {
							voice_channels.add(0);
						}
						current_channel = v-1;
						break;
						
			case REST: int rest = (int)(Integer.valueOf(t.value)*32*this.length);
					   int start = voice_channels.get(current_channel);
					   voice_channels.set(current_channel, start+rest);
					   break;
					   
			case CHORD: this.chord = !this.chord;
						break;
						
			case BAR: if (t.value == ":|") {
							for (Note note:this.note_storage) {
								NoteEvent ne = this.noteEventCreator(note);
								this.noteEventList.add(ne);
							}
							this.note_storage.clear();
						}
					  else if (t.value == "|:") {
						  this.note_storage.clear();
					  }
					  break;

			default: break;
		}
	}
	
	public void read(Note n) {
		this.note_storage.add(n);
		NoteEvent ne = this.noteEventCreator(n);
		this.noteEventList.add(ne);
		
		
	}
	
	public NoteEvent noteEventCreator(Note n) {
		int start_tick = voice_channels.get(current_channel);
		int tick_length = (int) (32*n.length*this.length);
		NoteEvent ne = new NoteEvent(n.toMidiNote(),start_tick,tick_length);
		if (!chord) {
			voice_channels.set(current_channel, ( start_tick + tick_length));
		}
		return ne;
	}

	
}
