package it.xpug.lightsout;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class LightsOutGameTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	
	private LightsOutView view = context.mock(LightsOutView.class);
	private LightsOutGame game = new LightsOutGame(view);

	
	@Test
	public void setupGame() {
		context.checking(new Expectations() {{
			oneOf(view).turnButtonOn(0);
		}});
		
		game.onGameStart();
	}
	
	@Test
	public void onClick() throws Exception {
		context.checking(new Expectations() {{
			exactly(2).of(view).turnButtonOn(0);
			oneOf(view).turnButtonOff(0);
		}});
		
		game.onClick();		
		game.onClick();		
		game.onClick();		
	}

}
