package Parser;

public class Token {
	TokenType type;
	String value;
	
	public Token(TokenType t, String s) {
		this.type = t;
		this.value = s.trim();
	}

  public String getValue(){
    return this.value;
  }
	
	public boolean isNote() {
		return (this.type == TokenType.NOTE || this.type == TokenType.REST);
	}

  public boolean isKey() {
    return (this.type == TokenType.KEY);
  }
	
	public String toString() {
		return ""+this.type.toString()+": "+this.value;
	}
}
