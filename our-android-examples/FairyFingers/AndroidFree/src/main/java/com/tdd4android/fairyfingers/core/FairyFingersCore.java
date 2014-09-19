package com.tdd4android.fairyfingers.core;

import java.util.*;

public class FairyFingersCore {
  public static final int ACTION_DOWN             = 0;
  public static final int ACTION_UP               = 1;
  public static final int ACTION_MOVE             = 2;
  public static final int ACTION_CANCEL           = 3;

  private List<Line> lines = new ArrayList<Line>();

  public void touch(int action, final float x, final float y) {
    if (action == ACTION_DOWN) {
      lines.add(new Line(x, y));
    }
    if (action == ACTION_MOVE) {
      lines.get(lines.size()-1).addPoint(x, y);
    }
  }

  public Collection lines() {
    return lines;
  }

  public Object lines(int index) {
    return lines.get(index);
  }
}
