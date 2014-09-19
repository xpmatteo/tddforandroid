package com.tdd4android.fairyfingers.core;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class FairyFingersCoreTest {
  FairyFingersCore core = new FairyFingersCore();

  @Test
  public void noLinesAtStart() throws Exception {
    assertEquals(0, core.lines().size());
  }

  @Test
  public void oneLine() throws Exception {
    core.touch(FairyFingersCore.ACTION_DOWN, 10.0f, 100.0f);
    core.touch(FairyFingersCore.ACTION_MOVE, 20.9f, 120.0f);
    core.touch(FairyFingersCore.ACTION_MOVE, 30.0f, 130.0f);
    core.touch(FairyFingersCore.ACTION_UP, 30.0f, 130.0f);

    assertEquals(1, core.lines().size());
  }

  @Test
  public void startOneLine() throws Exception {
    core.touch(FairyFingersCore.ACTION_DOWN, 10.0f, 100.0f);

    assertEquals(1, core.lines().size());
    assertEquals("(10.0,100.0)", core.lines(0).toString());
  }

  @Test
  public void startOneLineThenDrag() throws Exception {
    core.touch(FairyFingersCore.ACTION_DOWN, 10.0f, 110.0f);
    core.touch(FairyFingersCore.ACTION_MOVE, 20.0f, 120.0f);
    core.touch(FairyFingersCore.ACTION_MOVE, 30.0f, 130.0f);

    assertEquals("(10.0,110.0)->(20.0,120.0)->(30.0,130.0)", core.lines(0).toString());
  }

  @Test
  public void dashDash() throws Exception {
    core.touch(FairyFingersCore.ACTION_DOWN, 10.0f, 110.0f);
    core.touch(FairyFingersCore.ACTION_MOVE, 20.0f, 120.0f);
    core.touch(FairyFingersCore.ACTION_UP, 20.0f, 120.0f);

    core.touch(FairyFingersCore.ACTION_DOWN, 210.0f, 310.0f);
    core.touch(FairyFingersCore.ACTION_MOVE, 220.0f, 320.0f);
    core.touch(FairyFingersCore.ACTION_UP, 220.0f, 320.0f);

    assertEquals("(10.0,110.0)->(20.0,120.0)", core.lines(0).toString());
    assertEquals("(210.0,310.0)->(220.0,320.0)", core.lines(1).toString());
  }


  @Test@Ignore("not finished")
  public void twoFingers() throws Exception {
    core.touch(FairyFingersCore.ACTION_DOWN, 10.0f, 110.0f);

    core.touch(FairyFingersCore.ACTION_MOVE, 20.0f, 120.0f);
    core.touch(FairyFingersCore.ACTION_POINTER_DOWN, 20.0f, 120.0f);
    core.touch(FairyFingersCore.ACTION_MOVE, 20.0f, 120.0f);

    core.touch(FairyFingersCore.ACTION_POINTER_UP, 20.0f, 120.0f);

    core.touch(FairyFingersCore.ACTION_MOVE, 220.0f, 320.0f);
    core.touch(FairyFingersCore.ACTION_UP, 220.0f, 320.0f);

    assertEquals("(10.0,110.0)->(20.0,120.0)", core.lines(0).toString());
    assertEquals("(210.0,310.0)->(220.0,320.0)", core.lines(1).toString());
  }



}
