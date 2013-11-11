package Parser;

import java.util.HashMap;
import java.util.Map;

public class Keys {
	public static Map<String, String> getKey (String K){
		Map<String, String> keyset = new HashMap<String, String>();
		keyset.put("C",	"C");
		keyset.put("D", "D");
		keyset.put("E", "E");
		keyset.put("F", "F");
		keyset.put("G", "G");
		keyset.put("A", "A");
		keyset.put("B", "B");
		
		if (K.equals("C")|| K.equals("Am")){
			return keyset;
		}
		else if(K.equals("G")|| K.equals("Em")){
				keyset.put("F", "^F");
		}
		else if(K.equals("F")|| K.equals("Dm")){
			keyset.put("B", "_B");
		}
		else if(K.equals("D")||K.equals("Bm")){
			keyset.put("F", "^F");
			keyset.put("C", "^C");
		}
		else if(K.equals("Bb")||K.equals("Gm")){
			keyset.put("B", "_B");
			keyset.put("E", "_E");
		}
		else if(K.equals("A")||K.equals("F#m")){
			keyset.put("C", "^C");
			keyset.put("F", "^F");
			keyset.put("G","^G");
		}
		else if(K.equals("Eb")||K.equals("Cm")){
			keyset.put("E", "_E");
			keyset.put("A", "_A");
			keyset.put("B", "_B");
		}
		else if(K.equals("E")||K.equals("C#m")){
			keyset.put("F","^F");
			keyset.put("G","^G");
			keyset.put("C", "^C");
			keyset.put("D", "^D");
		}
		else if(K.equals("Fm")||K.equals("Ab")){
			keyset.put("A","_A");	
			keyset.put("B", "_B");
			keyset.put("D","_D");
			keyset.put("E", "_E");
		}
		else if(K.equals("B")||K.equals("G#m")){
			keyset.put("A","^A");
			keyset.put("C", "^C");
			keyset.put("D", "^D");
			keyset.put("F","^F");
			keyset.put("G", "^G");
		}
		else if(K.equals("Db")||K.equals("Bbm")){
			keyset.put("G", "_G");
			keyset.put("A","_A");
			keyset.put("B","_B");
			keyset.put("D", "_D");
			keyset.put("E","_E");
		}
		else if(K.equals("F#")||K.equals("D#m")){
			keyset.put("A","^A");
			keyset.put("C", "^C");
			keyset.put("D","^D");
			keyset.put("E","^E");
			keyset.put("G","^G");
			keyset.put("F","^F");
		}
		else if(K.equals("Gb")||K.equals("Ebm")){
			keyset.put("G","_G");
			keyset.put("A","_A");
			keyset.put("B", "_B");
			keyset.put("C","_C");
			keyset.put("D","_D");
			keyset.put("E","_E");
		}
		else if(K.equals("C#")||K.equals("A#m")){
			keyset.put("A","^A");
			keyset.put("B","^B");
			keyset.put("C","^C");
			keyset.put("D","^D");
			keyset.put("E","^E");
			keyset.put("F","^F");
			keyset.put("G","^G");
		}
		else if(K.equals("Cb")||K.equals("Abm")){
			keyset.put("C","_C");
			keyset.put("D","_D");
			keyset.put("E","_E");
			keyset.put("F","_F");
			keyset.put("G", "_G");
			keyset.put("A","_A");
			keyset.put("B","_B");
		}
		return keyset;
	}
		
	}
	

	


