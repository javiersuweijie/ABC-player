package Parser;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

public class KeysTest {

	@Test
	public void test() {
		Map<String, String> keyset = new HashMap<String, String>();
		keyset.put("C",	"C");
		keyset.put("D", "_D");
		keyset.put("E", "_E");
		keyset.put("F", "F");
		keyset.put("G", "G");
		keyset.put("A", "_A");
		keyset.put("B", "_B");
		
		assertEquals(keyset, Keys.getKey("Fm"));
		assertEquals(keyset, Keys.getKey("Ab"));
		
		Map<String, String> keyset1 = new HashMap<String, String>();
		keyset1.put("C",	"^C");
		keyset1.put("D", "^D");
		keyset1.put("E", "^E");
		keyset1.put("F", "^F");
		keyset1.put("G", "^G");
		keyset1.put("A", "^A");
		keyset1.put("B", "^B");
		
		assertEquals(keyset1, Keys.getKey("C#"));
		assertEquals(keyset1, Keys.getKey("A#m"));
	
		
	}

}

