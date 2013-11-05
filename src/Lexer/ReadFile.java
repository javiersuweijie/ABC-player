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
	private int index=0;
	protected Matcher matcher;
	
	//m?, it will appear once or not at all
	//X+, X appear one or more times
	//X*, X appear zero or more times
	
	private static final Pattern REGEX=Pattern.compile(
			"^(X\\s*:\\s*[0-9]+\n)"+												//field-index
			"|"+
			"(T\\s*:[^\n]+\n)"+														//field-title
			"|"+
			"(C\\s*:[^\n]+\n)"+   													//field-composer
			"|"+
			"(L\\s*:\\s*[0-9]+/[0-9]+\n)"+											//field-length
			"|"+
			"(M\\s*:\\s*C\\||M:C|M:[0-9]+/[0-9]+\n)"+									//field-meter
			"|"+
			"(Q\\s*:\\s*[0-9]+\n)"+													//field-tempo
			"|"+
			"(V\\s*:[^\n]+\n)"+														//field-voice
			"|"+
			"(K\\s*:\\s*[a-gA-G][#b]?m?\n)"											//field-key																
			, Pattern.DOTALL);
    
    static final String[] TOKEN={
    	"INDEX", "TITLE", "COMPOSER", "TEMP0", "LENGTH", "METER", "KEY", "VOICE"
    };
    
    public ReadFile(String filename) {
        this.filename= filename;
    }
     
    public String content() throws IOException {
            //StringBuilder result = new StringBuilder();
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
            
            while ((line = reader.readLine()) != null) {
            	this.str=line;
            	this.matcher=REGEX.matcher(str);
            	if(this.matcher.find()){
            		String val=matcher.group(0);
                	val=val.replaceAll("[A-Z ]+:\\s*", "").replace("\n", "");
                	
                	for(int i=1;i<=TOKEN.length;++i){
                		if(matcher.group(i)!=null){
                			String s=TOKEN[i-1];
                			result=s+" "+val+"\n";
                		}
                	}
            	}
            	
            }
            
            fileReader.close();
            reader.close();
            
            System.out.println(result);
            return result.toString();
    }
    
    public static void main(String args[]){
    	String filename="sample_abc/scale.abc";
		ReadFile file_reader=new ReadFile(filename);
		try {
			file_reader.content();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
