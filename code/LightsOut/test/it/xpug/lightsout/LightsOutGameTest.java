package it.xpug.lightsout;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class LightsOutGameTest {

	@Rule public JUnitRuleMockery context = new JUnitRuleMockery();
	
	private LightsOutView view = context.mock(LightsOutView.class);
	private LightsOutGame game = new LightsOutGame(view);

	
	@Test
	public void setup1x1Game() {
		context.checking(new Expectations() {{
			oneOf(view).createButton();
			will(returnValue(100));
			
			oneOf(view).turnButtonOn(100);
		}});
		
		game.onGameStart(1);
	}

	@Test@Ignore
	public void setup2x2Game() {
		context.checking(new Expectations() {{
			oneOf(view).createButton();
			will(returnValue(1));
			oneOf(view).createButtonRightOf(1);
			will(returnValue(2));
			oneOf(view).createButtonBelow(1);
			will(returnValue(3));
			oneOf(view).createButtonRightOf(3);
			will(returnValue(4));
			
			oneOf(view).turnButtonOn(1);
			oneOf(view).turnButtonOn(2);
			oneOf(view).turnButtonOn(3);
			oneOf(view).turnButtonOn(4);
		}});
		game.onGameStart(2);
	}

}
