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
	private int initial_tempo;
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
	public ArrayList<ArrayList<Object>> noteEventStorage;
	private int current_channel;
	
	private NoteParser np;
	
	/** The QueMaster is constructed with default tempo, meter and length
	 * default tempo = 60, meter = 4/4, length = 1/8
	 * 	
	 */
	public QueMaster() {
		this.tempo = 0;
		this.meter = 1;
		this.length = (float)1/8;
		this.voice_channels = new ArrayList<Integer>();
		this.noteEventList = new ArrayList<NoteEvent>();
		this.noteEventStorage = new ArrayList<ArrayList<Object>>();
		this.noteEventStorage.add(new ArrayList<Object>());
		this.voice_channels.add(0);
		this.current_channel = 0;
		this.chord = false;
		this.repeat_num = 0;
		this.repeat_from = 0;
		this.is_repeating = false;
		this.np = new NoteParser();
	}

	/** Getter for current tempo
	 * 
	 * @return (int) tempo
	 */
	public int getTempo() {
		return this.initial_tempo;
	}
	
	/** Getter for current meter
	 * 
	 * @return (meter) float
	 */
	public float getMeter() {
		return this.meter;
	}
	
	/** Getter for default note length
	 * @return (float) default note length
	 */
	public float getLength() {
		return this.length;
	}
	
	/** Getter for current voice channel
	 * 
	 * @return (int) current voice channel
	 */
	public int getCurrentVoice() {
		return this.current_channel;
	}
	
	/** Getter for the arraylist of note events
	 * 
	 * @return (ArrayList) note event array list
	 */
	public ArrayList<NoteEvent> getNoteEvents() {
		return this.noteEventList;
	}
	
	/** Getter for the integer array that stores the current start tick for different voices
	 * 
	 * @return (int[]) start ticks for different voice channels
	 */
	public ArrayList<Integer> getVoiceChannels() {
		return this.voice_channels;
	}
	
	/** Get the start tick for the current voice
	 * 
	 * @return (int) start tick
	 */
	
	
	
	public int getStartTick() {
		return this.voice_channels.get(current_channel);
	}
	
	/** Setter for start tick
	 * 
	 * @param (int) start_time in ticks
	 */
	public void setStartTick(int start_time) {
		this.voice_channels.set(current_channel, start_time);
	}
	
	
	
	/** Get the state of the current repeat
	 * 
	 * @return (byte) current repeat state
	 */
	public byte getRepeatNum() {
		return this.repeat_num;
	}
	
	
	
	/** Method to align all the start times together
	 * 
	 */
	public void evenStartTick() {
		for (int i=0;i<this.voice_channels.size();++i) {
			this.voice_channels.set(i, this.voice_channels.get(0));
		}
	}
	
	
	
	/** Handles TEMPO tokens
	 *  Automatically called by read(Token t)
	 *  
	 *  Initiates the initial tempo if it hasn't been set
	 *  If initial tempo has already been set, it will set the current tempo so that the QueMaster can
	 *  calculate how to modify tick length
	 * @param t
	 */
	private void tempoHandler(Token t) {
		if (this.tempo == 0){ 
			this.initial_tempo = Integer.valueOf(t.value);
			}
		this.tempo = Integer.valueOf(t.value);
		this.evenStartTick();
	}
	
	
	
 	/** Handles METER tokens
	 *  Automatically called by read(Token t)
	 *  
	 *  Changes the state of the QueMaster
	 * @param t
	 */
	private void meterHandler(Token t) {
		if (t.value.length()>2) {
			String[] s = t.value.split("/");
			this.meter = Float.valueOf(s[0])/Float.valueOf(s[1]);
		}
		else {
			if (t.value.equals("C")) this.meter = 1;
			else if (t.value.equalsIgnoreCase("C")) this.meter = (float)0.5;
			else this.meter = Float.valueOf(t.value);
		}
	}
	
	
	
	/** Handles LENGTH tokens
	 *  Automatically called by read(Token t)
	 *  
	 *  Changes the default length state of the QueMaster
	 * @param t
	 */
	private void lengthHandler(Token t) {
		if (t.value.length()>2) {
			String[] s = t.value.split("/");
			this.length = Float.valueOf(s[0])/Float.valueOf(s[1]);
		}
		else this.length = Float.valueOf(t.value);
	}
	
	
	
	/** Handles VOICE tokens
	 *  Automatically called by read(Token t) method
	 * 
	 *  Changes the current voice to add NoteEvents into
	 * @param t
	 */
	private void voiceHandler(Token t) {
		int v = Integer.valueOf(t.value);
		if (v==1) this.evenStartTick();
		while (v>voice_channels.size()) {
			voice_channels.add(0);
			this.noteEventStorage.add(new ArrayList<Object>());
		}
		current_channel = v-1;
	}
	
	
	
	/** Handles CHORD_ST and CHORD_END tokens
	 *  Automatically called by read(Token t) method
	 * 
	 *  Changes the state of the qm to parse notes as chords
	 * @param t
	 */
	private void chordHandler(Token t) {
		if(repeat_num!=2) {
			if (chord) {
				this.setStartTick(this.getStartTick()+chord_offset);
				this.chord_offset = 0;
			}
			this.chord = !this.chord;
		}
		if(!is_repeating&&repeat_num!=1) this.noteEventStorage.get(current_channel).add(t);
	}
	
	
	
	 /** Handles BAR token
	 *  Automatically called by read(Token t) method
	 *  
	 *  Repeats and add the Tokens from the noteEventStorage depending on the current voice channel
	 *  Cleans the buffer when a repeat start is parsed
	 * @param t
	 */
	private void barHandler(Token t) {
		if (t.value.equalsIgnoreCase(":|")) {
			is_repeating = true;
			this.repeat_num = 0;
			for (Object note:this.noteEventStorage.get(current_channel)) {
				if (note == null) continue;
				this.read((Token)note);
			}
			this.noteEventStorage.get(current_channel).clear();
			is_repeating = false;
		}
	  else if (t.value.equalsIgnoreCase("|:")) {
		  this.noteEventStorage.get(current_channel).clear();
		  this.repeat_num = 0;
		  this.repeat_from = this.getStartTick();
	  }
	}
	
	
	
	/** Handles REPEATNO tokens
	 *  Automatically called by read(Token t) method
	 *  
	 *  Signals to the QueMaster that a repeat number is parsed
	 *  Only adds the 2nd-time repeat measures into the buffer
	 * @param t
	 */
	private void repeatNoHandler(Token t) {
		if (t.value.equalsIgnoreCase("[1")) {
			this.repeat_num = 1;	
		}
		else if (t.value.equalsIgnoreCase("[2")) {
			//this.repeat_num = 2;
		}
	}
	
	
	
	/** Handles TRIPLET tokens
	 *  Automatically called by read(Token t) method
	 *  
	 *  For the next 3 notes/chords, shortens the note length by 2/3
	 * @param t
	 */
	private void tripletHandler(Token t) {
		if(repeat_num!=2) this.triplet = 3;
		if (!is_repeating&&repeat_num!=1) this.noteEventStorage.get(current_channel).add(t);
	}
	
	
	
	/** Handles DUPLET tokens
	 *  Automatically called by read(Token t) method
	 *  
	 *  For the next 2 notes/chords, lengthens the note length by 3/2
	 * @param t
	 */
	private void dupletHandler(Token t) {
		if(repeat_num!=2) this.duplet = 2;
		if (!is_repeating&&repeat_num!=1) this.noteEventStorage.get(current_channel).add(t);
	}
	
	
	
	/** Handles QUADRUPLET tokens
	 *  Automatically called by read(Token t) method
	 *  
	 *  For the next 4 notes/chords, shortens the note length by 4/3
	 * @param t
	 */
	private void quadrupletHandler(Token t) {
		if(repeat_num!=2) this.quadruplet = 4;
		 if (!is_repeating&&repeat_num!=1) this.noteEventStorage.get(current_channel).add(t);
	}
	
	
	
	
	/** Reads a token to change the state of the QueMaster
	 * 
	 * It takes a token and decides what to do with them.
	 * @param Token t (eg. METER, TEMPO, LENGTH, VOICE, CHORD, BAR, REPEAT, TUPLET, NOTE, KEY, REST)
	 */
	public void read(Token t) {
		switch (t.type) {

			case TEMPO: 	this.tempoHandler(t);
							break;
			case METER: 	this.meterHandler(t);
							break;
			case LENGTH: 	this.lengthHandler(t);
						 	break;
			case VOICE: 	this.voiceHandler(t);
							break;
			case CHORD_END: this.chordHandler(t);
						    break;
			case CHORD_ST: 	this.chordHandler(t);
						   	break;
			case BAR: 		this.barHandler(t);
					  		break;
			case REPEATNO:  this.repeatNoHandler(t);
							break;
			case TRIPLET:   this.tripletHandler(t);
							break;
			case DUPLET: 	this.dupletHandler(t);
						 	break;
			case QUADRUPLET:this.quadrupletHandler(t);
		      			 	break;
			case NOTE:		this.noteAndRestHandler(t);
							break;
			case REST:		this.noteAndRestHandler(t);
							break;
			case KEY:		np.setKey(t.getValue());
			default: 		break;
		}
	}
	
	
	public void noteAndRestHandler(Token t) {
		Note n = this.np.parse(t);
		if (repeat_num!=2) {
			NoteEvent ne = this.noteEventCreator(n);
			if (ne!=null) {
				this.noteEventList.add(ne);
			}
		}
		if (!is_repeating&&repeat_num!=1) {
			this.noteEventStorage.get(current_channel).add(t);
			return;
		}
	}
	
	public NoteEvent noteEventCreator(Note n) {
		int start_tick = this.getStartTick();
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
		int tick_length = (int) (8*3*n.length*length_modifier*(double)initial_tempo/tempo*3*11*2*5);
		//int tick_length = (int)(8*3*n.length*length_modifier);
		if (!chord) {
			voice_channels.set(current_channel, ( start_tick + tick_length));
		}
		if (chord) {
			chord_offset=Math.max(chord_offset, tick_length);
			if (this.triplet>0&&this.triplet<2) triplet++;
			//if (this.duplet>0&&this.duplet<1)duplet++;
			//if (this.quadruplet>0&&this.quadruplet<3)quadruplet++;
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
