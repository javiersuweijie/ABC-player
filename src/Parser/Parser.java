package Parser;

import java.util.Iterator;

public class Parser {

	String[] splitted;
	int current_position;
	
	public Parser(String lexed) {
		splitted = lexed.split("\n");
		current_position = 0;
	}
	
	public String[] getSplit() {
		return this.splitted;
	}
	
	public Token next() {
		String[] a = getSplit()[current_position].split(" ");
		TokenType token = TokenType.valueOf(a[0]);
		String value = a[1];
		Token t = new Token(token,value);
		++current_position;
		return t;
	}
}
