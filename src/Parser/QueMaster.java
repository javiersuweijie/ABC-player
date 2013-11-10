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
	private int chord_offset;
	private int repeat_from;
	private byte repeat_num;
	private boolean is_repeating;
	private ArrayList<Integer> voice_channels;
	private ArrayList<NoteEvent> noteEventList;
	public ArrayList<Object> noteEventStorage;
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
		this.noteEventList = new ArrayList<NoteEvent>();
		this.noteEventStorage = new ArrayList<Object>();
		this.voice_channels.add(0);
		this.current_channel = 0;
		this.chord = false;
		this.repeat_num = 0;
		this.repeat_from = 0;
		this.is_repeating = false;
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
	
	public int getStartTick() {
		return this.voice_channels.get(current_channel);
	}
	
	public void setStartTick(int start_time) {
		this.voice_channels.set(current_channel, start_time);
	}
	
	public byte getRepeatNum() {
		return this.repeat_num;
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
						else {
							if (t.value.equals("C")) this.meter = 1;
							else if (t.value.equalsIgnoreCase("C")) this.meter = (float)0.5;
							else this.meter = Float.valueOf(t.value);
						}
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

			case CHORD_END: if(repeat_num!=2) {
							if (chord) {
								this.setStartTick(this.getStartTick()+chord_offset);
								this.chord_offset = 0;
							}
							this.chord = !this.chord;
						}
						if(!is_repeating&&repeat_num!=1) this.noteEventStorage.add(t);
						break;
			case CHORD_ST: if(repeat_num!=2) {
								if (chord) {
									this.setStartTick(this.getStartTick()+chord_offset);
									this.chord_offset = 0;
								}
							this.chord = !this.chord;
							}
							if(!is_repeating&&repeat_num!=1) this.noteEventStorage.add(t);
							break;
						
			case BAR: if (t.value == ":|") {
							is_repeating = true;
							this.repeat_num = 0;
							for (Object note:this.noteEventStorage) {
								if (note.getClass() == Token.class) this.read((Token)note);
								if (note.getClass() == Note.class) this.read((Note)note);
							}
							this.noteEventStorage.clear();
							is_repeating = false;
						}
					  else if (t.value == "|:") {
						  this.noteEventStorage.clear();
						  this.repeat_num = 0;
						  this.repeat_from = this.getStartTick();
					  }
					  break;
			case REPEATNO: if (t.value == "|[1") {
								this.repeat_num = 1;	
							}
							else if (t.value == "|[2") {
								this.repeat_num = 2;
							}
							break;
			case TRIPLET: if(repeat_num!=2) this.triplet = 3;
						  if (!is_repeating&&repeat_num!=1) this.noteEventStorage.add(t);
						  break;
			case DUPLET: if(repeat_num!=2) this.duplet = 2;
						  if (!is_repeating&&repeat_num!=1) this.noteEventStorage.add(t);
						 break;
			case QUADRUPLET: if(repeat_num!=2) this.quadruplet = 4;
						  if (!is_repeating&&repeat_num!=1) this.noteEventStorage.add(t);
		      			 break;

			default: break;
		}
	}
	
	public void read(Note n) {
		if (repeat_num!=2) {
			NoteEvent ne = this.noteEventCreator(n);
			if (ne!=null) {
				this.noteEventList.add(ne);
			}
		}
		if (!is_repeating&&repeat_num!=1) {
			this.noteEventStorage.add(n);
			return;
		}
	}
	
	public NoteEvent noteEventCreator(Note n) {
		int start_tick = voice_channels.get(current_channel);
		double length_modifier = 1;
		if (this.triplet!=0) {
			length_modifier = 2.0/3;
			--this.triplet;
		}
		else if (this.duplet!=0) {
			length_modifier = 3.0/2;
			--this.duplet;
		}
		else if (this.quadruplet!=0) {
			length_modifier = 3.0/4;
			--this.quadruplet;
		}
		int tick_length = (int) (8*3*n.length*length_modifier);

		if (!chord) {
			voice_channels.set(current_channel, ( start_tick + tick_length));
		}
		if (chord) {
			chord_offset=Math.max(chord_offset, tick_length);
		}
		if (!n.isRest()) {
			NoteEvent ne = new NoteEvent(n.toPitch().toMidiNote(),start_tick,tick_length);
			return ne;
		}
		else {
			return null;
		}
	}

	
}
