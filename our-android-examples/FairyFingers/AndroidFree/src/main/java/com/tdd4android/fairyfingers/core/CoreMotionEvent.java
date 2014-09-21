package com.tdd4android.fairyfingers.core;

public interface CoreMotionEvent {
  int getAction();
  int getPointerCount();
  int getPointerId(int pointerIndex);
  void getPointerCoords(int pointerIndex, CorePoint outPointerCoords);
  int getActionIndex();
}
