package com.tdd4android.fairyfingers.core;

public class Pippo {
  // Constants copied from android.view.MotionEvent
  public static final int ACTION_DOWN             = 0;
  public static final int ACTION_UP               = 1;
  public static final int ACTION_MOVE             = 2;
  /**
   * Constant for getActionMasked: A non-primary pointer has gone down.
   * Use getActionIndex to retrieve the index of the pointer that changed.
   * The index is encoded in the ACTION_POINTER_INDEX_MASK bits of the
   * unmasked action returned by getAction.
   */
  public static final int ACTION_POINTER_DOWN     = 5;
  public static final int ACTION_POINTER_UP       = 6;
  private FingerEvent target;

  public Pippo(FingerEvent target) {
    this.target = target;
  }

  public void onTouchEvent(CoreMotionEvent event) {
    switch (event.getAction()) {
      case ACTION_MOVE:
        for (int pointerIndex = 0; pointerIndex < event.getPointerCount(); pointerIndex++) {
          int pointerId = event.getPointerId(pointerIndex);
          target.onFingerMove(pointerId, event.getX(pointerIndex), event.getY(pointerIndex));
        }
        break;
      case ACTION_DOWN:
      case ACTION_POINTER_DOWN:
        int actionIndex = event.getActionIndex();
        int pointerId = event.getPointerId(actionIndex);
        target.onFingerDown(pointerId, event.getX(actionIndex), event.getY(actionIndex));
        break;
      case ACTION_UP:
      case ACTION_POINTER_UP:
        target.onFingerUp(event.getPointerId(event.getActionIndex()));
        break;
      default:
        throw new UnrecognizedActionException();
    }
  }

  public static class UnrecognizedActionException extends RuntimeException {
  }

}

