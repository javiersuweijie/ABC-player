package Parser;

public class Token {
	TokenType type;
	String value;
	
	public Token(TokenType t, String s) {
		this.type = t;
		this.value = s;
	}
}
