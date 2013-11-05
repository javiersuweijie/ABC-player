package sound;

public class NoteEvent {
	
	public int pitch;
	public int start_tick;
	public int end_tick;
	
	NoteEvent(int pitch, int start_tick, int end_tick) {
		this.pitch = pitch;
		this.start_tick = start_tick;
		this.end_tick = end_tick;
	}

}
