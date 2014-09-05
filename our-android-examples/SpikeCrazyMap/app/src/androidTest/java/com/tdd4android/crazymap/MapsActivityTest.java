package com.tdd4android.crazymap;

import android.app.Activity;
import android.test.*;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.*;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.openContextualActionModeOverflowMenu;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

public class MapsActivityTest extends ActivityInstrumentationTestCase2<MapsActivity> {

  public MapsActivityTest() {
    super(MapsActivity.class);
  }

  //@UiThreadTest
  public void testIsVisible() throws Throwable {
    getActivity();
    getInstrumentation().waitForIdleSync();

    Thread.sleep(400);
    runTestOnUiThread(new Runnable() {
      @Override
      public void run() {
        assertFalse(getActivity().isOnMap());
      }
    });


  }
  public void testIsVisiblewithEspresso() throws Throwable {
    MapsActivity a = getActivity();
    onView(withId(R.id.map)).check()
        .check(assertEquals(false,  a.isOnMap())
  }
}
