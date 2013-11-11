package Lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args){
		//"((\\[|\\])*)"
		//Rest: (z+)
		/*Pattern p=Pattern.compile("\\%");
		String l="%2";
		
		Matcher matcher=p.matcher(l);
		while(matcher.find()){
			System.out.println(matcher.group());
		}*/
		
		String reg8 = "(\\%\\s*[A-Za-z\\s*0-9\\.]+)";
        System.out.println("% "+ Pattern.matches(reg8, "% Make sure 10 happy B."));
	}

}
