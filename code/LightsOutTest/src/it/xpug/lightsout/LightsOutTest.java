package it.xpug.lightsout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;

public class LightsOutTest extends ActivityInstrumentationTestCase2<MainActivity> {
	public LightsOutTest() {
		super(MainActivity.class);
	}

	@UiThreadTest
	public void testToggleButtonScenario() throws Exception {
		theButtonIsOn();
		whenIPressTheButton();
		theButtonIsOff();
		whenIPressTheButton();
		theButtonIsOn();
	}

	private void theButtonIsOn() {
		assertEquals(Color.YELLOW, getButtonBackground().getColor());
	}

	private void whenIPressTheButton() {
		getButton().performClick();
	}

	private void theButtonIsOff() {
		assertEquals(Color.DKGRAY, getButtonBackground().getColor());
	}

	private ColorDrawable getButtonBackground() {
		return (ColorDrawable) getButton().getBackground();
	}

	private Button getButton() {
		return (Button) getActivity().findViewById(R.id.button1);
	}

}
