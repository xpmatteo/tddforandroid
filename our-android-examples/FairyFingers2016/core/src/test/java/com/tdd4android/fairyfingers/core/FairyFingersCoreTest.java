package com.tdd4android.fairyfingers.core;

import static com.tdd4android.fairyfingers.core.Actions.*;
import static org.junit.Assert.*;

import org.junit.*;

public class FairyFingersCoreTest {
  private FairyFingersCore core = new FairyFingersCore();

  @Test
  public void noTrails() throws Exception {
    assertEquals(0, core.trailsCount());
  }

  @Test
  public void justFingerDown() throws Exception {
    core.onTouchEvent(ACTION_DOWN, 10, 20);

    assertEquals(1, core.trailsCount());
    assertEquals("(10.0,20.0)", core.getTrail(0).toString());
  }

  @Test
  public void unfinishedTrail() throws Exception {
    core.onTouchEvent(ACTION_DOWN, 100, 200);
    core.onTouchEvent(ACTION_MOVE, 300, 400);

    assertEquals(1, core.trailsCount());
    assertEquals("(100.0,200.0)->(300.0,400.0)", core.getTrail(0).toString());
  }

  @Test
  public void aFinishedTrail() throws Exception {
    core.onTouchEvent(ACTION_DOWN,   1.1f,   2.2f);
    core.onTouchEvent(ACTION_MOVE,  33.3f,  44.4f);
    core.onTouchEvent(ACTION_UP,   555.5f, 666.6f);

    assertEquals(1, core.trailsCount());
    assertEquals("(1.1,2.2)->(33.3,44.4)->(555.5,666.6)", core.getTrail(0).toString());
  }

  @Test
  public void twoTrails() throws Exception {
    core.onTouchEvent(ACTION_DOWN, 1.0f, 100.0f);
    core.onTouchEvent(ACTION_MOVE, 2.0f, 200.0f);
    core.onTouchEvent(ACTION_UP,   3.0f, 300.0f);

    core.onTouchEvent(ACTION_DOWN, 4.0f, 400.0f);
    core.onTouchEvent(ACTION_MOVE, 5.0f, 500.0f);
    core.onTouchEvent(ACTION_UP,   6.0f, 600.0f);

    assertEquals(2, core.trailsCount());
    assertEquals("(1.0,100.0)->(2.0,200.0)->(3.0,300.0)", core.getTrail(0).toString());
    assertEquals("(4.0,400.0)->(5.0,500.0)->(6.0,600.0)", core.getTrail(1).toString());
  }
}
