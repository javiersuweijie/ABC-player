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
		else if(K =="D"|K=="Bm"){
			keyset.put("F", "^F");
			keyset.put("C", "^C");
		}
		else if(K=="Bb"|K=="Gm"){
			keyset.put("B", "_B");
			keyset.put("E", "_E");
		}
		else if(K=="A"|K=="F#m"){
			keyset.put("C", "^C");
			keyset.put("F", "^F");
			keyset.put("G","^G");
		}
		else if(K=="Eb"|K=="Cm"){
			keyset.put("E", "_E");
			keyset.put("A", "_A");
			keyset.put("B", "_B");
		}
		else if(K=="E"|K=="C#m"){
			keyset.put("F","^F");
			keyset.put("G","^G");
			keyset.put("C", "^C");
			keyset.put("D", "^D");
		}
		else if(K.equals("Fm")||K=="Ab"){
			keyset.put("A","_A");	
			keyset.put("B", "_B");
			keyset.put("D","_D");
			keyset.put("E", "_E");
		}
		else if(K=="B"|K=="G#m"){
			keyset.put("A","^A");
			keyset.put("C", "^C");
			keyset.put("D", "^D");
			keyset.put("F","^F");
			keyset.put("G", "^G");
		}
		else if(K=="Db"|K=="Bbm"){
			keyset.put("G", "_G");
			keyset.put("A","_A");
			keyset.put("B","_B");
			keyset.put("D", "_D");
			keyset.put("E","_E");
		}
		else if(K=="F#"|K=="D#m"){
			keyset.put("A","^A");
			keyset.put("C", "^C");
			keyset.put("D","^D");
			keyset.put("E","^E");
			keyset.put("G","^G");
			keyset.put("F","^F");
		}
		else if(K=="Gb"|K=="Ebm"){
			keyset.put("G","_G");
			keyset.put("A","_A");
			keyset.put("B", "_B");
			keyset.put("C","_C");
			keyset.put("D","_D");
			keyset.put("E","_E");
		}
		else if(K=="C#"|K=="A#m"){
			keyset.put("A","^A");
			keyset.put("B","^B");
			keyset.put("C","^C");
			keyset.put("D","^D");
			keyset.put("E","^E");
			keyset.put("F","^F");
			keyset.put("G","^G");
		}
		else if(K=="Cb"|K=="Abm"){
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
	

	


