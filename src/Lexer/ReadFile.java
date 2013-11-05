package Lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
	private String filename;
    
    public ReadFile(String filename) {
            this.filename= filename;
    }
    
    public String content() throws IOException {
            StringBuilder result = new StringBuilder();
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
                result.append(line+"\n");
            }
            
            fileReader.close();
            reader.close();
            
            return result.toString();
    }

}
