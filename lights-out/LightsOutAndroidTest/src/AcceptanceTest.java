import static java.util.Arrays.*;

import java.util.*;

import it.xpug.lightsout.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.test.*;
import android.view.*;
import android.widget.*;


public class AcceptanceTest extends ActivityInstrumentationTestCase2<LightsOutActivity> {

	private LightsOutActivity activity;

	public AcceptanceTest() {
		super(LightsOutActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		this.activity = getActivity();
	}
	
	public void testAtStartAllLightsAreOn() throws Exception {
		whenGameStarts();
		allLightsAreOn();
	}

	/*
		 0  1  2  3  4
		 5  6  7  8  9
		10 11 12 13 14
		15 16 17 18 19
		20 21 22 23 24
	 */	
	@UiThreadTest
	public void testClickingOnAButton() throws Exception {
		whenIClickOnButton(6);
		onlyTheseLightsAreOff(1, 5, 6, 7, 11);
	}

	@UiThreadTest
	public void testClickingTwice() throws Exception {
		whenIClickOnButton(6);
		whenIClickOnButton(7);
		onlyTheseLightsAreOff(1, 2, 5, 8, 11, 12);
	}
	
	@UiThreadTest
	public void testRestoreStateAfterConfigChange() throws Exception {
		givenIClickedOnButton(6);
		whenIChangeFromPortraitToLandscape();
		onlyTheseLightsAreOff(1, 5, 6, 7, 11);		
	}

	private void whenIChangeFromPortraitToLandscape() {
		fail("not implemented");
	}

	private void givenIClickedOnButton(int position) {
		whenIClickOnButton(position);
	}

	private void whenIClickOnButton(int position) {
		grid().performItemClick(grid(), position, position);
	}

	private void onlyTheseLightsAreOff(Integer ... positionsOff) {
		List<Integer> positions = asList(positionsOff);
		for (int i = 0; i < 25; i++) {
			if (positions.contains(i))
				assertTrue("should be off at " + i, isLightOffAt(i));
			else
				assertTrue("should be on at " + i, isLightOnAt(i));
		}
	}

	private void allLightsAreOn() {
		for (int i = 0; i < 25; i++) {
			assertTrue(isLightOnAt(i));
		}
	}

	private void whenGameStarts() {
	}

	private int colorAt(int position) {
		ColorDrawable background = (ColorDrawable) buttonAt(position).getBackground();
		return background.getColor();
	}

	private View buttonAt(int position) {
		return grid().findViewById(position);
	}

	private GridView grid() {
		return (GridView) activity.findViewById(R.id.grid);
	}
	
	private boolean isLightOffAt(int position) {
		return colorAt(position) == Color.DKGRAY;
	}

	public boolean isLightOnAt(int position) {
		return colorAt(position) == Color.YELLOW;
	}

}
