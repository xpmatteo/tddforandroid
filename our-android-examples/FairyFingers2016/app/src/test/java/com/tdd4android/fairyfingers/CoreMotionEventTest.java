package com.tdd4android.fairyfingers;

import android.view.MotionEvent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoreMotionEventTest {
  @Test
  public void actionDown() throws Exception {
    MotionEvent androidEvent = new MotionEvent(...);

    CoreMotionEvent coreEvent = new CoreMotionEvent(androidEvent);

    assertEquals(MotionEvent.ACTION_DOWN, coreEvent.getAction());
  }
}
