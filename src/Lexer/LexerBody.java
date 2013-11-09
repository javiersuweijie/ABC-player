package Lexer;


import java.util.regex.Pattern;



public class LexerBody {
	public static void main(String[] args) {
		
		// \p{ASCII} matches all ASCII characters
		// \p{lowercase} matches all a-z
		// a?b -> ? means repeat a for 0 or 1 time
		// a*b -> * means repeat a for 0 or more times
		// a+b -> + means repeat a for 1 or more times
		
		//Used for notes, triplet, duplet, quadruplet, chords
		String notes = "([\\_]*[\\^]*[\\=]*[A-Ga-g][',+]*[0-9]?\\/?[0-9]?)";
		
		//Bar and repeats:  |   |:   :|   |[1   |[2   |]  
		String reg1 = "(\\:?\\|\\]?\\:?\\|?)"; 
		System.out.println("| "+ Pattern.matches(reg1, "|")); //returns true
		System.out.println("|: "+ Pattern.matches(reg1, "|:")); //returns true
		System.out.println(":| "+ Pattern.matches(reg1, ":|")); //returns true
		System.out.println("|] "+ Pattern.matches(reg1, "|]")); //returns true
		System.out.println("|[ "+ Pattern.matches(reg1, "|[")); //returns false
		
		//RepeatFirst or RepeatTwice
		String reg9 = "(\\[[12])"; 
		System.out.println("[1 "+ Pattern.matches(reg9, "[1")); //returns true
		System.out.println("[2 "+ Pattern.matches(reg9, "[2")); //returns true
		
		//Comment: % osdif
		String reg2 = "(\\%)"; 
		System.out.println("% "+ Pattern.matches(reg2, "%")); //returns true

		//Chord start: [   
		String reg3 = "(\\[)"; 
		System.out.println("[ "+ Pattern.matches(reg3, "[")); //returns true
		System.out.println("|[ "+ Pattern.matches(reg3, "|[")); //returns false
		
		//Chord end: ]
		String reg10 = "(\\])"; 
		System.out.println("[ "+ Pattern.matches(reg10, "]")); //returns true
		System.out.println("|] "+ Pattern.matches(reg10, "|]")); //returns false
		
		//Rest
		String reg4 = "(z[0-9]?\\/?[0-9]?)";
		System.out.println("z2 "+ Pattern.matches(reg4, "z2")); //returns true
		System.out.println("z/3 "+ Pattern.matches(reg4, "z/3")); //returns true
		
		//Note
		String reg5 = notes;
		System.out.println("__A, "+ Pattern.matches(reg5, "__A,")); //returns true
		System.out.println("d' " + Pattern.matches(reg5, "d'")); //returns true
		
		//Triplet
		String reg6 = "(\\(3)";
		System.out.println("(3 "+ Pattern.matches(reg6, "(3")); //returns true
		
		//Duplet
		String reg7 = "(\\(2)";
		System.out.println("(2 "+ Pattern.matches(reg7, "(2")); //returns true
		
		//Quadruplet
		String reg8 = "(\\(4)";
		System.out.println("(4 "+ Pattern.matches(reg8, "(4")); //returns true
		
	}
}
