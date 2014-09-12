package com.tdd4android.fairyfingers.test;

import android.graphics.*;
import android.test.*;
import android.view.View;

import com.tdd4android.fairyfingers.*;

import junit.framework.TestCase;


public class FingersViewTest extends ActivityUnitTestCase<DrawActivity> {

  FingersView view;

  public FingersViewTest(Class<DrawActivity> activityClass) {
    super(DrawActivity.class);
  }

  public void testEmptyCanvas() throws Exception {
    FingersView view = (FingersView) getActivity().findViewById(R.id.fingersView);
    assertNoLineisDrawn();

    TouchUtils.drag(this, 10, 20, 10, 20, 2);
  }

  public void testSingleLine() throws Exception {

  }

  private void assertNoLineisDrawn() {
  }

}
