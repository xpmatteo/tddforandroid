package com.tdd4android.fairyfingers;

import android.view.MotionEvent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoreMotionEventTest {
  @Test
  public void actionDown() throws Exception {
    MotionEvent.PointerProperties[] pointerProperties = new MotionEvent.PointerProperties[] {
            new MotionEvent.PointerProperties()
    };
    pointerProperties[0].id = 0;
    MotionEvent.PointerCoords[] pointerCoords = new MotionEvent.PointerCoords[] {
            new MotionEvent.PointerCoords()
    };
    pointerCoords[0].x = 100;
    pointerCoords[0].y = 200;
    MotionEvent androidEvent = MotionEvent.obtain(0, 0, MotionEvent.ACTION_DOWN, 1, pointerProperties,
            pointerCoords, 0, 0, 0, 0, 0, 0, 0, 0);

    SimplifiedMotionEvent simplifiedEvent = new SimplifiedMotionEvent(androidEvent);

    assertEquals(MotionEvent.ACTION_DOWN, simplifiedEvent.getAction());
  }
}
