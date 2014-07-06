package it.xpug.lightsout;

import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.TextView;

public class LightsOutTest extends ActivityInstrumentationTestCase2<MainActivity> {
	public LightsOutTest() {
		super(MainActivity.class);
	}

	public void testApplicationTitleAppearsOnLayout() throws Exception {
		TextView text = (TextView) getActivity().findViewById(R.id.application_title_text);
		assertEquals("Lights Out!", text.getText());
	}

	@UiThreadTest
	public void testToggleButtonScenario() throws Exception {
		theButtonIsOff();
		whenIPressTheButton();
		theButtonIsOn();
		whenIPressTheButton();
		theButtonIsOff();
	}

	private void theButtonIsOn() {
		assertEquals("ON", getButton().getText());
	}

	private Button getButton() {
		Button button = (Button) getActivity().findViewById(R.id.button1);
		return button;
	}

	private void whenIPressTheButton() {
		getButton().performClick();
	}

	private void theButtonIsOff() {
		assertEquals("OFF", getButton().getText());
	}

}
