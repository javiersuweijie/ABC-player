package Lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pattern p=Pattern.compile("(X\\s*:\\s*[0-9]+)");
		String l="ssX:1";
		
		Matcher matcher=p.matcher(l);
		//System.out.println(matcher.find());
		while(matcher.find()){
			System.out.println(matcher.group());
		}
	}

}
