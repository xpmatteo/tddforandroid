package it.xpug.lightsoutspike.test; 

import static java.util.Arrays.*;
import it.xpug.lightsoutspike.*;
import it.xpug.lightsoutspike.R;

import java.util.*;

import android.annotation.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.test.*;
import android.view.*;
import android.widget.*;


@SuppressLint("DefaultLocale")
public class LightsOutAcceptanceTest extends ActivityInstrumentationTestCase2<MainActivity> {

	public LightsOutAcceptanceTest() {
		super(MainActivity.class);
	}

	/*
	 *   0  1  2  3
	 *   4  5  6  7
	 *   8  9 10 11
	 *  12 13 14 15
	 */

	/*
	 *   0  .  2  3
	 *   .  .  .  7
	 *   8  . 10 11
	 *  12 13 14 15
	 */
	@UiThreadTest
	public void testClickInTheMiddle() throws Exception {
		givenAllButtonsAreOn();
		whenIClickOnButton(5);
		thenOnlyTheseButtonsAreOff(1, 4, 5, 6, 9);
	}

	/*
	 *   .  .  2  .
	 *   .  5  6  7
	 *   8  9 10 11
	 *   . 13 14 15
	 */
	@UiThreadTest
	public void testClickInTheCorner() throws Exception {
		givenAllButtonsAreOn();
		whenIClickOnButton(0);
		thenOnlyTheseButtonsAreOff(0, 1, 3, 4, 12);
	}

	/*
	 *   0  .  .  3
	 *   .  5  6  .
	 *   8  .  . 11
	 *  12 13 14 15
	 */
	@UiThreadTest
	public void testClickTwice() throws Exception {
		givenAllButtonsAreOn();
		whenIClickOnButton(5);
		andIClickOnButton(6);
		thenOnlyTheseButtonsAreOff(1, 2, 4, 7, 9, 10);
	}
	
	@UiThreadTest
	public void redrawTheScreen() throws Exception {
		givenAllButtonsAreOn();
		whenIClickOnButton(5);
		thenOnlyTheseButtonsAreOff(1, 4, 5, 6, 9);

		whenIResizeTheScreen();
		thenOnlyTheseButtonsAreOff(1, 4, 5, 6, 9);
	}

	private void whenIResizeTheScreen() {
		android.view.WindowManager.LayoutParams x = new android.view.WindowManager.LayoutParams();
		getActivity().onWindowAttributesChanged(x );
	}

	private void thenOnlyTheseButtonsAreOff(Integer ... positions) {
		List<Integer> list = asList(positions);
		for (int i = 0; i < 16; i++) {
			if (list.contains(i))
				assertButtonIsOff(i);
			else
				assertButtonIsOn(i);
		}
	}

	private ColorDrawable getColor(int position) {
		View button = getButton(position);
		return (ColorDrawable) button.getBackground();
	}

	private View getButton(int position) {
		View button = getGrid().findViewById(position);
		String message = String.format("Button %d not found", position);
		assertNotNull(message, button);
		return button;
	}

	private void whenIClickOnButton(int position) {
		GridView grid = getGrid();
		grid.performItemClick(grid, position, position);
	}

	private GridView getGrid() {
		return (GridView) getActivity().findViewById(R.id.grid_view);
	}

	private void andIClickOnButton(int position) {
		whenIClickOnButton(position);
	}

	private void givenAllButtonsAreOn() {
		for (int i = 0; i < 16; i++) {
			assertButtonIsOn(i);			
		}
	}

	private void assertButtonIsOff(int position) {
		String message = String.format("Button %d should be Off", position);
		assertEquals(message, Color.DKGRAY, getColor(position).getColor());
	}

	private void assertButtonIsOn(int position) {
		String message = String.format("Button %d should be On", position);
		assertEquals(message, Color.YELLOW, getColor(position).getColor());
	}

}
