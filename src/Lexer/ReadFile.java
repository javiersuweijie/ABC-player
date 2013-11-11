package Lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadFile {

        private String filename;
        private String str;
        protected Matcher matcher;
        
        //m?, it will appear once or not at all
        //X+, X appear one or more times
        //X*, X appear zero or more times
        //Pattern.DOTALL cover all characters, including "\n"
        
        //private static final Pattern REGEX=Pattern.compile("(X\\s*:\\s*[0-9]+)"+"|"+"(T\\s*:[^\n]+)");
        
        /** A ABC file is read. The Lexer would split strings into meaningful tokens.
         * for the Parser to analyze.

        Grammar:

        ~~~HEADER: 

        abc-tune ::= abc-header abc-music

        abc-header ::= field-number comment* field-title other-fields* field-key
                
        field-number ::= "X:" DIGIT+ end-of-line
        field-title ::= "T:" text end-of-line
        other-fields ::= field-composer | field-default-length | field-meter 
        	| field-tempo | field-voice | comment
        field-composer ::= "C:" text end-of-line
        field-default-length ::= "L:" note-length-strict end-of-line
        field-meter ::= "M:" meter end-of-line
        field-tempo ::= "Q:" tempo end-of-line
        field-voice ::= "V:" text end-of-line
        field-key ::= "K:" key end-of-line

        key ::= keynote [mode-minor]
        keynote ::= basenote [key-accidental]
        key-accidental ::= "#" | "b"
        mode-minor ::= "m"

        meter ::= "C" | "C|" | meter-fraction
        meter-fraction ::= DIGIT+ "/" DIGIT+ 

        tempo ::= DIGIT+ 

        ~~~BODY: 

        abc-music ::= abc-line+
        abc-line ::= (element+ linefeed) | mid-tune-field | comment
        element ::= note-element | tuplet-element | barline | nth-repeat | space 

        note-element ::= (note | multi-note)

        // note is either a pitch or a rest
        note ::= note-or-rest [note-length]
        note-or-rest ::= pitch | rest
        pitch ::= [accidental] basenote [octave]
        octave ::= ("'"+) | (","+)
        note-length ::= [DIGIT+] ["/" [DIGIT+]]
        note-length-strict ::= DIGIT+ "/" DIGIT+

        ; "^" is sharp, "_" is flat, and "=" is neutral
        accidental ::= "^" | "^^" | "_" | "__" | "="

        basenote ::= "C" | "D" | "E" | "F" | "G" | "A" | "B"
                | "c" | "d" | "e" | "f" | "g" | "a" | "b"

        rest ::= "z"

        // tuplets
        tuplet-element ::= tuplet-spec note-element+
        tuplet-spec ::= "(" DIGIT 

        // chords
        multi-note ::= "[" note+ "]"

        barline ::= "|" | "||" | "[|" | "|]" | ":|" | "|:"
        nth-repeat ::= "[1" | "[2"

        ; A voice field might reappear in the middle of a piece
        ; to indicate the change of a voice
        mid-tune-field- ::= field-voice

        comment ::= "%" text linefeed
        end-of-line ::= comment | linefeed

         */
        //Used for notes, triplet, duplet, quadruplet, chords
      	private static final String notes = "([\\_]*[\\^]*[\\=]*[A-Ga-g][',+]*[0-9]?\\/?[0-9]?)";
        
        private static final Pattern REGEX=Pattern.compile(
                        "(X\\s*:\\s*[0-9]+)"+                       //field-index
                        "|"+
                        "(T\\s*:[^\n]+)"+                           //field-title
                        "|"+
                        "(C\\s*:[^\n]+)"+                           //field-composer
                        "|"+
                        "(L\\s*:\\s*[0-9]+/[0-9]+)"+                //field-length
                        "|"+
                        "(M\\s*:\\s*C\\||M:C|M:[0-9]+/[0-9]+)"+     //field-meter
                        "|"+
                        "(Q\\s*:\\s*[0-9]+)"+                       //field-tempo
                        "|"+
                        "(V\\s*:[^\n]+)"+                           //field-voice
                        "|"+
                        "(K\\s*:\\s*[a-gA-G][#b]?m?)"+              //field-key 
                        "|"+
                        notes +                                     //basenote+accidental+octave+note length
                        "|"+                          
                        "(z[0-9]?\\/?[0-9]?)" +                     //rest+note length
                        "|"+
                        "(\\:?\\|\\]?\\:?\\|?)"+                        //barline
                        "|"+
                        "(\\[[12])" +                               //nth-repeat
                        "|"+
                        "(\\[)" +                                   //chord start
                        "|"+
                        "(\\])" +                                   //chord end
                        "|"+
                        "(\\(4)" +                                  //quadruplet 
                        "|"+
                        "(\\(3)" +                                 //triplet
                        "|"+
                        "(\\(2)" +                                 //duplet
                        "|"+
                        "(\\%$|\\%\\s*[A-Za-z\\s*0-9\\,\\.\\!]+$|\\%\\s*m[0-9]+\\-[0-9]+$)"   //comment
                        );                                        

    
	static final String[] TOKEN={
        "INDEX", "TITLE", "COMPOSER", "LENGTH", "METER", "TEMPO", "VOICE", "KEY",
        "NOTE", "REST", "BAR", "REPEATNO", "CHORD_ST", "CHORD_END", "QUADRUPLET", "TRIPLET", "DUPLET", "COMMENT"
	};
    
    public ReadFile(String filename) {
        this.filename= filename;
    }
     
    public String content() throws IOException {
            //StringBuilder final_result = new StringBuilder();
            String result="";
    		FileReader fileReader;
            
            try {
                    fileReader = new FileReader(filename);
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException("File not found");
            }
            
            BufferedReader reader = new BufferedReader(fileReader);
            String line = "";
            
            while((line=reader.readLine())!=null){
            	this.str=line;
            	matcher=REGEX.matcher(this.str);            	
            	
            	while(matcher.find()){
            		String val=matcher.group(0);
            		val=val.replaceAll("[A-Z]+:\\s*", "").replace("\n", "");
            		for(int i=1;i<=TOKEN.length;i++){
            			if(matcher.group(i)!=null){
            				String s=TOKEN[i-1];
            				result =result+ s+" "+val+"\n";
            			}
            		}
            	}

            }
            
            fileReader.close();
            reader.close();
            
            System.out.println(result);
            return result.toString();
    }

}
