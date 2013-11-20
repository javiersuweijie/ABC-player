package player;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {

	@Test
	public void testDebussy() {
		String filename="sample_abc/debussy.abc";
		Main.play(filename);
	}
	
	@Test
	public void testfur_elise() {
		String filename="sample_abc/fur_elise.abc";
		Main.play(filename);
	}
	
	@Test
	public void testInvention() {
		String filename="sample_abc/invention.abc";
		Main.play(filename);
	}
	
	@Test
	public void testLittle_night_music() {
		String filename="sample_abc/little_night_music.abc";
		Main.play(filename);
	}
	
	@Test
	public void testPaddy() {
		String filename="sample_abc/paddy.abc";
		Main.play(filename);
	}
	
	@Test
	public void testPrelude() {
		String filename="sample_abc/prelude.abc";
		Main.play(filename);
	}
	
	@Test
	public void testSample1() {
		String filename="sample_abc/sample1.abc";
		Main.play(filename);
	}
	
	@Test
	public void testSample2() {
		String filename="sample_abc/sample2.abc";
		Main.play(filename);
	}
	
	@Test
	public void testSample3() {
		String filename="sample_abc/sample3.abc";
		Main.play(filename);
	}
	
	@Test
	public void testScale() {
		String filename="sample_abc/scale.abc";
		Main.play(filename);
	}
}
