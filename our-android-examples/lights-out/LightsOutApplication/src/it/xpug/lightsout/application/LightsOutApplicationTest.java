package it.xpug.lightsout.application;

import static org.junit.Assert.*;

import java.util.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;

public class LightsOutApplicationTest {
	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	
	LightsOutView view = context.mock(LightsOutView.class);
	LightsOutModel model = new LightsOutModel(3);
	LightsOutApplication app = new LightsOutApplication(model, view); 
	
	@Test
	public void initialConfiguration() {
		assertIsOn(0);
		assertIsOn(1);
		assertIsOn(2);
		assertIsOn(3);
		assertIsOn(4);
		assertIsOn(5);
		assertIsOn(6);
		assertIsOn(7);
		assertIsOn(8);
	}

	@Test
	public void notAlwaysOn() {
		model.setStatus("...OOO...");
		assertIsOff(0);
		assertIsOff(1);
		assertIsOff(2);
		assertIsOn(3);
		assertIsOn(4);
		assertIsOn(5);
		assertIsOff(6);
		assertIsOff(7);
		assertIsOff(8);
	}
	
	/*
	 *  0 1 2
	 *  3 4 5
	 *  6 7 8
	 */
	@Test
	public void onClickInTheMiddle() throws Exception {
		context.checking(new Expectations() {{ 
			oneOf(view).setLightOffAt(1);
			oneOf(view).setLightOffAt(3);
			oneOf(view).setLightOffAt(4);
			oneOf(view).setLightOffAt(5);
			oneOf(view).setLightOffAt(7);
			ignoringScore();
		}});
		app.onClickOn(4);
	}

	/*
	 *  0 1 2
	 *  3 4 5
	 *  6 7 8
	 */
	@Test
	public void onClickInTheLeftSide() throws Exception {
		context.checking(new Expectations() {{ 
			oneOf(view).setLightOffAt(0);
			oneOf(view).setLightOffAt(3);
			oneOf(view).setLightOffAt(4);
			oneOf(view).setLightOffAt(6);
			ignoringScore();
		}});
		app.onClickOn(3);
	}

	/*
	 *  0 1 2
	 *  3 4 5
	 *  6 7 8
	 */
	@Test
	public void onClickInTheLeftTop() throws Exception {
		context.checking(new Expectations() {{ 
			oneOf(view).setLightOffAt(0);
			oneOf(view).setLightOffAt(1);
			oneOf(view).setLightOffAt(3);
			ignoringScore();
		}});
		app.onClickOn(0);
	}

	/*
	 *  0 1 2
	 *  3 4 5
	 *  6 7 8
	 */
	@Test
	public void onClickInTheRightBottom() throws Exception {
		context.checking(new Expectations() {{ 
			oneOf(view).setLightOffAt(5);
			oneOf(view).setLightOffAt(7);
			oneOf(view).setLightOffAt(8);
			ignoringScore();
		}});
		app.onClickOn(8);
	}
	
	/*
	 *  0 1 2
	 *  3 4 5
	 *  6 7 8
	 */
	@Test
	public void clickTwice() throws Exception {
		context.checking(new Expectations() {{ 
			oneOf(view).setLightOffAt(0);
			oneOf(view).setLightOffAt(1);
			oneOf(view).setLightOffAt(3);

			oneOf(view).setLightOnAt(0);
			oneOf(view).setLightOnAt(1);
			oneOf(view).setLightOffAt(2);
			oneOf(view).setLightOffAt(4);
			ignoringScore();
		}});
		app.onClickOn(0);
		app.onClickOn(1);
	}

	@Test
	public void showCongratsOnVictory() throws Exception {
		model.setStatus("OO.O.....");
		context.checking(new Expectations() {{
			oneOf(view).showVictory();
			ignoringScore();
			ignoringLights();
		}});
		app.onClickOn(0);
		assertTrue("all off", model.isAllOff());
	}
	
	@Test
	public void updatesScoreOnClick() throws Exception {
		context.checking(new Expectations() {{
			oneOf(view).updateScore(1);
			oneOf(view).updateScore(2);
			ignoringLights();
		}});		
		app.onClickOn(anyPosition());
		app.onClickOn(anyPosition());
		assertEquals(2, app.score());
	}

	@Test
	public void saveAndRestoreState() throws Exception {
		app.restoreStatus("OOO...OOO");
		assertEquals("OOO...OOO", app.saveStatus());
	}
	
	private int anyPosition() {
		return new Random().nextInt(model.side()*model.side());
	}

	private void assertIsOn(int position) {
		String message = String.format("position %s expected to be on", position);
		assertTrue(message, app.isOnAt(position));
	}

	private void assertIsOff(int position) {
		String message = String.format("position %s expected to be off", position);
		assertFalse(message, app.isOnAt(position));
	}

	private void ignoringScore() {
		context.checking(new Expectations() {{
			allowing(view).updateScore(with(any(Integer.class)));
		}});
	}

	private void ignoringLights() {
		context.checking(new Expectations() {{
			allowing(view).setLightOffAt(with(any(Integer.class)));
			allowing(view).setLightOnAt(with(any(Integer.class)));
		}});
	}
}
