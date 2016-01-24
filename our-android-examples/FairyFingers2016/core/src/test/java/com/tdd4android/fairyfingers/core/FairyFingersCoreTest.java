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
    core.onDown(10, 20);

    assertEquals(1, core.trailsCount());
    assertEquals("(10.0,20.0)", core.getTrail(0).toString());
  }

  @Test
  public void unfinishedTrail() throws Exception {
    core.onDown(100, 200);
    core.onMove(300, 400);

    assertEquals(1, core.trailsCount());
    assertEquals("(100.0,200.0)->(300.0,400.0)", core.getTrail(0).toString());
  }

  @Test
  public void aFinishedTrail() throws Exception {
    core.onDown(1.1f,   2.2f);
    core.onMove(33.3f,  44.4f);
    core.onUp();

    assertEquals(1, core.trailsCount());
    assertEquals("(1.1,2.2)->(33.3,44.4)", core.getTrail(0).toString());
  }

  @Test
  public void twoTrails() throws Exception {
    core.onDown(1.0f, 100.0f);
    core.onMove(2.0f, 200.0f);
    core.onUp();

    core.onDown(4.0f, 400.0f);
    core.onMove(5.0f, 500.0f);
    core.onUp();

    assertEquals(2, core.trailsCount());
    assertEquals("(1.0,100.0)->(2.0,200.0)", core.getTrail(0).toString());
    assertEquals("(4.0,400.0)->(5.0,500.0)", core.getTrail(1).toString());
  }

  @Test@Ignore
  public void oneMorePointerDown() throws Exception {
    core.onDown(10, 20);            // down first finger
    core.onMove(30, 40);            // drag it
    core.onPointerDown(100, 200);   // down second finger
    core.onMove(50, 60, 110, 210);  // drag both

    assertEquals("(10.0,20.0)->(30.0,40.0)", core.getTrail(0).toString());
    assertEquals("(100.0,200.0)->(110.0,210.0)", core.getTrail(1).toString());
  }
}
