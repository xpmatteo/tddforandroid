package com.tdd4android.fairyfingers.core;

public interface CoreMotionEvent {
  int getAction();
  int getPointerCount();
  int getPointerId(int pointerIndex);
  float getX(int pointerIndex);
  float getY(int pointerIndex);
  int getActionIndex();
}
