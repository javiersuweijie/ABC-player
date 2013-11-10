package player;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import sound.Note;
import sound.NoteEvent;
import sound.SequencePlayer;
import Lexer.ReadFile;
import Parser.NoteParser;
import Parser.Parser;
import Parser.QueMaster;
import Parser.Token;

/**
 * Main entry point of your application.
 */
public class Main {

	/**
	 * Plays the input file using Java MIDI API and displays
	 * header information to the standard output stream.
	 * 
	 * <p>Your code <b>should not</b> exit the application abnormally using
	 * System.exit()</p>
	 * 
	 * @param file the name of input abc file
	 */
	public static void play(String file) {

        String lexed_string;
        ReadFile file_reader=new ReadFile(file);
        QueMaster qm = new QueMaster();
        Note note;
        NoteParser np = new NoteParser();
        try {
                lexed_string = file_reader.content();
                System.out.println(lexed_string);
                Parser token_parser = new Parser(lexed_string);
                Token t = null;
                do {
                	t = token_parser.next();
                	if (t==null) break;
                	note = null;
                    System.out.println(t);
                	if (t.isNote()) {
                		note = np.parse(t);
                		if (note != null) {
                			qm.read(note);
                		}
                	}
                	else qm.read(t);
                } while (t!=null);
                System.out.println(qm.getNoteEvents());
                try {
					SequencePlayer player = new SequencePlayer(120*9, 6);
					for (NoteEvent ne:qm.getNoteEvents()) {
						player.addNote(ne.pitch, ne.start_tick, ne.tick_length);
					}
					player.play();
				} catch (MidiUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidMidiDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
	}
	public static void main(String arg[]) {
        String filename="sample_abc/invention.abc";
		Main.play(filename);
	}
}
