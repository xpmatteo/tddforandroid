package com.tdd4android.fairyfingers.core;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;

import static com.tdd4android.fairyfingers.core.CoreMotionEvent1.*;

public class CoreMotionEvent1Test {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private FingerEventTarget target = context.mock(FingerEventTarget.class);
  private CoreMotionEvent1 coreMotionEvent1 = new CoreMotionEvent1();

  @Test
  public void convertActionDownToFingerDown() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerDown(0, 100f, 200f);
    }});

    touch(ACTION_DOWN, 0, 0, 100f, 200f);
  }

  @Test
  public void pointerDown() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerDown(33, 200f, 300f);
    }});

    // { action=ACTION_POINTER_DOWN(1), id[0]=0, x[0]=381.1559, y[0]=387.34833, id[1]=1, x[1]=273.38046, y[1]=137.66043, }

    touch(ACTION_POINTER_DOWN, 1, 0, -1, -1, 33, 200f, 300f);
  }

  @Test(expected=CoreMotionEvent1.UnrecognizedActionException.class)
  public void unrecognizedEvent() throws Exception {
    int unrecognizedAction = 1001;
    touch(unrecognizedAction, -1, -1, -1, -1);
  }

  @Test
  public void actionMove() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerMove(0, 123f, 456f);
    }});

    touch(ACTION_MOVE, -1, 0, 123f, 456f);
  }

  @Test
  public void actionMoveTwoPointers() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerMove(44, 45f, 46f);
      oneOf(target).onFingerMove(77, 78f, 79f);
    }});

    touch(ACTION_MOVE, -1, 44, 45f, 46f, 77, 78f, 79f);
  }

  @Test
  public void actionPointerUp() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerUp(99);
    }});

    touch(ACTION_POINTER_UP, 1, -1, -1, -1, 99, -1, -1);
  }

  @Test
  public void actionUp() throws Exception {
    context.checking(new Expectations() {{
      oneOf(target).onFingerUp(84);
    }});

    touch(ACTION_UP, 0, 84, -1, -1);
  }

  private void touch(final int action, final int actionIndex, final int pointerId, final float x, final float y) {
    coreMotionEvent1.action = action;
    coreMotionEvent1.actionIndex = actionIndex;
    coreMotionEvent1.pointerCount = 1;
    coreMotionEvent1.pointers[0].pointerId = pointerId;
    coreMotionEvent1.pointers[0].x = x;
    coreMotionEvent1.pointers[0].y = y;

    coreMotionEvent1.deliverEventTo(target);
  }

  private void touch(final int action, final int actionIndex, final int id0, final float x0, final float y0, final int id1, final float x1, final float y1) {
    coreMotionEvent1.action = action;
    coreMotionEvent1.actionIndex = actionIndex;
    coreMotionEvent1.pointerCount = 2;
    coreMotionEvent1.pointers[0].pointerId = id0;
    coreMotionEvent1.pointers[0].x = x0;
    coreMotionEvent1.pointers[0].y = y0;
    coreMotionEvent1.pointers[1].pointerId = id1;
    coreMotionEvent1.pointers[1].x = x1;
    coreMotionEvent1.pointers[1].y = y1;

    coreMotionEvent1.deliverEventTo(target);
  }

}
