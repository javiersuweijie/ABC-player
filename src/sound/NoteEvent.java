package sound;

public class NoteEvent {
	
	public int pitch;
	public int start_tick;
	public int tick_length;
	
	public NoteEvent(int pitch, int start_tick, int tick_length) {
		this.pitch = pitch;
		this.start_tick = start_tick;
		this.tick_length = tick_length;
	}

	public NoteEvent startTimeOffset(int offset) {
		return new NoteEvent(this.pitch,this.start_tick+offset,this.tick_length);
	}

}
