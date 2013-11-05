package Lexer;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ReadFileTest {

	@Test
	public void test() {
		String filename="sample_abc/scale.abc";
		ReadFile file_reader=new ReadFile(filename);
		String actualString="";
		String expectedString="X:1\n"+"T:Simple scale\n"+
		"C:Unknown\n"+"M:4/4\n"+"L:1/4\n"+"Q:120\n"+"K:C\n"+"C D E F | G A B c | c B A G F E D C |\n";	
		
		try{
			actualString=file_reader.content();
			assertEquals(actualString, expectedString);
		} catch(IOException e){
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

}
