package com.tdd4android.fairyfingers.core;

import org.junit.*;

import static com.tdd4android.fairyfingers.core.FairyFingersCore.*;
import static org.junit.Assert.assertEquals;

public class FairyFingersCoreTest {
  FairyFingersCore core = new FairyFingersCore();

  @Test
  public void noLinesAtStart() throws Exception {
    assertEquals(0, core.lines().size());
  }

  @Test
  public void startOneLine() throws Exception {
    core.touch(down(10.0f, 100.0f));

    assertEquals(1, core.lines().size());
    assertEquals("(10.0,100.0)", core.lines(0).toString());
  }

  @Test
  public void oneLineDownMove() throws Exception {
    core.touch(down(10.0f, 110.0f));
    core.touch(move(20.0f, 120.0f));
    core.touch(move(30.0f, 130.0f));

    assertEquals("(10.0,110.0)->(20.0,120.0)->(30.0,130.0)", core.lines(0).toString());
  }

  @Test
  public void oneLineDownMoveUp() throws Exception {
    core.touch(down(10.0f, 100.0f));
    core.touch(move(20.9f, 120.0f));
    core.touch(move(30.0f, 130.0f));
    core.touch(up(30.0f, 130.0f));

    assertEquals(1, core.lines().size());
    assertEquals("(10.0,100.0)->(20.9,120.0)->(30.0,130.0)", core.lines(0).toString());
  }


  @Test
  public void dashDash() throws Exception {
    core.touch(down(10.0f, 110.0f));
    core.touch(move(20.0f, 120.0f));
    core.touch(up(20.0f, 120.0f));

    core.touch(down(210.0f, 310.0f));
    core.touch(move(220.0f, 320.0f));
    core.touch(up(220.0f, 320.0f));

    assertEquals("(10.0,110.0)->(20.0,120.0)", core.lines(0).toString());
    assertEquals("(210.0,310.0)->(220.0,320.0)", core.lines(1).toString());
  }


  @Test@Ignore("not finished")
  public void twoFingersStaggered() throws Exception {
    core.touch(touch(ACTION_DOWN, 0, 1f, 2f));
    core.touch(touch(ACTION_MOVE, 0, 3f, 4f));

    core.touch(touch(ACTION_POINTER_DOWN, 0, 3f, 4f, 1, 100f, 200f));
    core.touch(touch(ACTION_MOVE, 0, 5f, 6f, 1, 300f, 400f));

    core.touch(touch(ACTION_POINTER_UP, 0, 5f, 6f, 1, 300f, 400f));

    core.touch(touch(ACTION_MOVE, 1, 500f, 600f));
    core.touch(touch(ACTION_UP, 1, 500f, 600f));

    assertEquals(2, core.lines().size());
    assertEquals("(1.0,2.0)->(3.0,4.0)->(5.0,6.0)", core.lines(0).toString());
    assertEquals("(100.0,200.0)->(300.0,400.0)->(500.0,600.0)", core.lines(1).toString());
  }

  private CoreMotionEvent down(final float x, final float y) {
    return touch(ACTION_DOWN, x, y);
  }

  private CoreMotionEvent up(final float x, final float y) {
    return touch(ACTION_UP, x, y);
  }

  private CoreMotionEvent move(final float x, final float y) {
    return touch(ACTION_MOVE, x, y);
  }

  private CoreMotionEvent touch(final int action, final float x, final float y) {
    return touch(action, 0, x, y);
  }

  private CoreMotionEvent touch(final int action, final int pointerId, final float x, final float y) {
    return new CoreMotionEvent() {
      @Override
      public int getPointerCount() {
        return 1;
      }

      @Override
      public int getPointerId(int pointerIndex) {
        return pointerId;
      }

      @Override
      public void getPointerCoords(int pointerIndex, CorePoint outPointerCoords) {
        if (pointerIndex != 0)
          throw new IllegalArgumentException("PointerIndex " + pointerIndex);
        outPointerCoords.x = x;
        outPointerCoords.y = y;
      }

      @Override
      public int getAction() {
        return action;
      }
    };
  }

  private CoreMotionEvent touch(final int action, final int id0, final float x0, final float y0, final int id1, final float x1, final float y1) {
    return new CoreMotionEvent() {
      @Override
      public int getPointerCount() {
        return 2;
      }

      @Override
      public int getPointerId(int pointerIndex) {
        switch (pointerIndex) {
          case 0: return id0;
          case 1: return id1;
          default: throw new IllegalArgumentException("" + pointerIndex);
        }
      }

      @Override
      public void getPointerCoords(int pointerIndex, CorePoint outPointerCoords) {
        switch (pointerIndex) {
          case 0:
            outPointerCoords.x = x0;
            outPointerCoords.y = y0;
            break;
          case 1:
            outPointerCoords.x = x1;
            outPointerCoords.y = y1;
            break;
          default: throw new IllegalArgumentException("" + pointerIndex);
        }
      }

      @Override
      public int getAction() {
        return action;
      }
    };
  }
}
