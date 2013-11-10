package Parser;

public class Token {
	TokenType type;
	String value;
	
	public Token(TokenType t, String s) {
		this.type = t;
		this.value = s;
	}
	
	public boolean isNote() {
		return (this.type == TokenType.NOTE || this.type == TokenType.REST || this.type == TokenType.KEY);
	}
	
	public String toString() {
		return ""+this.type.toString()+": "+this.value;
	}
}
