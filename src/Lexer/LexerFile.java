package Lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Lexer.Token.TokenType;

public class LexerFile {
	private String str;
	private int index=0;
	
	private final Matcher matcher;
	//private static final String NOTE;
	
	private static final Pattern REGEX=Pattern.compile("C\\s*:[^\n]+\n", Pattern.DOTALL);
	
	private static final TokenType[] TOKEN_TYPE={
		TokenType.COMPOSER
	};
	
	public LexerFile(String string){
		this.str=string;
		this.matcher=REGEX.matcher(str);
	}
	
	public Token next() throws IllegalArgumentException{
		
		
		throw new RuntimeException("Regex error - should not reach here.");
	}
}
