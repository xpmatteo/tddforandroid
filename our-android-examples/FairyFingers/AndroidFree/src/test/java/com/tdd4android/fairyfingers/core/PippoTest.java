package com.tdd4android.fairyfingers.core;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;

import static com.tdd4android.fairyfingers.core.Pippo.*;

public class PippoTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private FingerEvent target = context.mock(FingerEvent.class);
  private Pippo pippo = new Pippo(target);

  @Test
  public void convertActionDownToFingerDown() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerDown(0, 100f, 200f);
    }});

    pippo.onTouchEvent(touch(ACTION_DOWN, 0, 0, 100f, 200f));
  }

  @Test
  public void pointerDown() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerDown(33, 200f, 300f);
    }});

    // { action=ACTION_POINTER_DOWN(1), id[0]=0, x[0]=381.1559, y[0]=387.34833, id[1]=1, x[1]=273.38046, y[1]=137.66043, }

    pippo.onTouchEvent(touch(ACTION_POINTER_DOWN, 1, 0, -1, -1, 33, 200f, 300f));
  }

  @Test(expected=UnrecognizedActionException.class)
  public void unrecognizedEvent() throws Exception {
    int unrecognizedAction = 1001;
    pippo.onTouchEvent(touch(unrecognizedAction, -1, -1, -1, -1));
  }

  @Test
  public void actionMove() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerMove(0, 123f, 456f);
    }});

    pippo.onTouchEvent(touch(ACTION_MOVE, -1, 0, 123f, 456f));
  }

  @Test
  public void actionMoveTwoPointers() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerMove(44, 45f, 46f);
      oneOf(target).onFingerMove(77, 78f, 79f);
    }});

    pippo.onTouchEvent(touch(ACTION_MOVE, -1, 44, 45f, 46f, 77, 78f, 79f));
  }

  @Test
  public void actionPointerUp() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerUp(99);
    }});

    pippo.onTouchEvent(touch(ACTION_POINTER_UP, 1, -1, -1, -1, 99, -1, -1));
  }

  @Test
  public void actionUp() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerUp(84);
    }});

    pippo.onTouchEvent(touch(ACTION_UP, 0, 84, -1, -1));
  }

  private CoreMotionEvent touch(final int action, final int actionIndex, final int pointerId, final float x, final float y) {
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
      public float getX(int pointerIndex) {
        return x;
      }

      @Override
      public float getY(int pointerIndex) {
        return y;
      }

      @Override
      public int getActionIndex() {
        return actionIndex;
      }

      @Override
      public int getAction() {
        return action;
      }
    };
  }

  private CoreMotionEvent touch(final int action, final int actionIndex, final int id0, final float x0, final float y0, final int id1, final float x1, final float y1) {
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
      public float getX(int pointerIndex) {
        switch (pointerIndex) {
          case 0: return x0;
          case 1: return x1;
          default: throw new IllegalArgumentException("" + pointerIndex);
        }
      }

      @Override
      public float getY(int pointerIndex) {
        switch (pointerIndex) {
          case 0: return y0;
          case 1: return y1;
          default: throw new IllegalArgumentException("" + pointerIndex);
        }
      }

      @Override
      public int getActionIndex() {
        return actionIndex;
      }

      @Override
      public int getAction() {
        return action;
      }
    };
  }

}
