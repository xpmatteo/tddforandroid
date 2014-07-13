package it.xpug.lightsout;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class LightsOutGameTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	
	private LightsOutView view = context.mock(LightsOutView.class);

	private List<Boolean> status = new ArrayList<>();
	private LightsOutController game = new LightsOutController(status, view);
	
	@Test
	public void setupGame() {
		status.addAll(asList(true, false, true));

		context.checking(new Expectations() {{
			oneOf(view).turnButtonOn(0);
			oneOf(view).turnButtonOff(1);
			oneOf(view).turnButtonOn(2);
		}});
		
		game.onGameStart();
	}
	
	@Test
	public void onClick() throws Exception {
		status.addAll(asList(true, true, true, true));

		context.checking(new Expectations() {{
			oneOf(view).turnButtonOff(0);
			oneOf(view).turnButtonOff(1);
			oneOf(view).turnButtonOff(2);
		}});
		
		game.onClick(1);
		assertEquals(asList(false, false, false, true), status);
	}

}
