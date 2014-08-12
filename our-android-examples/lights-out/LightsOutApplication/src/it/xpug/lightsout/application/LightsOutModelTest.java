package it.xpug.lightsout.application;

import static org.junit.Assert.*;

import org.junit.*;

public class LightsOutModelTest {

	@Test
	public void initiallyAllSet() {
		LightsOutModel model = new LightsOutModel(2);
		assertEquals("OOOO", model.toString());
	}
	
	@Test
	public void toggle() throws Exception {
		LightsOutModel model = new LightsOutModel(2);
		model.toggle(1);
		assertEquals("O.OO", model.toString());
	}
	
	@Test
	public void initializeWithString() throws Exception {
		LightsOutModel model = new LightsOutModel("...OOO...");
		assertEquals(3, model.side());
		assertEquals("...OOO...", model.toString());
	}
	
	@Test
	public void allOff() throws Exception {
		LightsOutModel model = new LightsOutModel("....");
		assertTrue("all off", model.isAllOff());
		model.toggle(0);
		assertFalse("not all off", model.isAllOff());
	}
	
	@Test
	public void allOn() throws Exception {
		LightsOutModel model = new LightsOutModel("OOOO");
		assertTrue("all on", model.isAllOn());
		model.toggle(3);
		assertFalse("not all on", model.isAllOn());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void iMAfraidICantDoThat() throws Exception {
		LightsOutModel model = new LightsOutModel(2);
		model.toggle(-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void outOfBounds() throws Exception {
		LightsOutModel model = new LightsOutModel(2);
		model.toggle(4);
	}
	
	@Test
	public void queryPosition() throws Exception {
		LightsOutModel model = new LightsOutModel("O.O.");
		assertTrue("0", model.isOnAt(0));
		assertFalse("1", model.isOnAt(1));
	}

}
