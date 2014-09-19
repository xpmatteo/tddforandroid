package com.tdd4android.fairyfingers.core;

public interface CoreMotionEvent {
  int getPointerCount();

  void getPointerCoords(int pointerIndex, CorePoint outPointerCoords);

  int getAction();

  float getX();

  float getY();
}
