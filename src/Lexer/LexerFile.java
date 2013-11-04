package Lexer;

public class LexerFile {
	public static enum TokenType{
		COMPOSER,
		KEY,
		LENGTH,
		METER,
		TEMPO,
		TITLE,
		INDEX,
		VOICE,
		INT,
		STRING,
		BAR,
		NOTE,
		COMMENT,
		CHORT_ST,
		CHORT_END,
		TRIPLET,
		DUPLET,
		QUADRUPLET
	}
	
	private String tokenName;
	private TokenType tokenType;
	
	
	public String getTokenName(){
		return tokenName;
	}
	
	public TokenType getTokenType(){
		return tokenType;
	}
}
