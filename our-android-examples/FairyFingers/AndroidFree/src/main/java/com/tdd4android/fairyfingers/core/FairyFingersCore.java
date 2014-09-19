package com.tdd4android.fairyfingers.core;

import java.util.*;

public class FairyFingersCore {

  // Constants copied from android.view.MotionEvent
  public static final int ACTION_DOWN             = 0;
  public static final int ACTION_UP               = 1;
  public static final int ACTION_MOVE             = 2;
  public static final int ACTION_CANCEL           = 3;
  public static final int ACTION_POINTER_DOWN     = 5;
  public static final int ACTION_POINTER_UP       = 6;

  private List<Line> lines = new ArrayList<Line>();

  public void touch(CoreMotionEvent event) {
    if (event.getAction() == ACTION_DOWN) {
      lines.add(new Line(event.getX(), event.getY()));
    }
    if (event.getAction() == ACTION_MOVE) {
      lines.get(lines.size()-1).addPoint(event.getX(), event.getY());
    }
  }

  public void touch(int action, final float x, final float y) {
    if (action == ACTION_DOWN) {
      lines.add(new Line(x, y));
    }
    if (action == ACTION_MOVE) {
      lines.get(lines.size()-1).addPoint(x, y);
    }
  }

  public Collection<Line> lines() {
    return lines;
  }

  public Object lines(int index) {
    return lines.get(index);
  }
}
