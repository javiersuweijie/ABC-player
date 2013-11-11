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
import Parser.TokenType;

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
        try {
                lexed_string = file_reader.content();
                Parser token_parser = new Parser(lexed_string);
                Token t = null;
                do {
                	t = token_parser.next();
                	if (t==null) break;
                	else {
                		qm.read(t);
                	}
                } while (t!=null);
                try {
                	System.out.println(qm.getTempo());
                	System.out.println(qm.getLength());
                	System.out.println(qm.getMeter());
                	System.out.println(qm.getVoiceChannels().toString());
					SequencePlayer player = new SequencePlayer((int)(qm.getTempo()),24);
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
        String filename="sample_abc/little_night_music.abc";
		Main.play(filename);
	}
}
