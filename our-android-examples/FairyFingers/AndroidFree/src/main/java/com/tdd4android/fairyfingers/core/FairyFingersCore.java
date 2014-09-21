package com.tdd4android.fairyfingers.core;

import java.util.*;

public class FairyFingersCore {

  // Constants copied from android.view.MotionEvent
  public static final int ACTION_DOWN             = 0;
  public static final int ACTION_UP               = 1;
  public static final int ACTION_MOVE             = 2;

  /**
   * Constant for {@link #getActionMasked}: A non-primary pointer has gone down.
   * <p>
   * Use {@link #getActionIndex} to retrieve the index of the pointer that changed.
   * </p><p>
   * The index is encoded in the {@link #ACTION_POINTER_INDEX_MASK} bits of the
   * unmasked action returned by {@link #getAction}.
   * </p>
   */
  public static final int ACTION_POINTER_DOWN     = 5;
  public static final int ACTION_POINTER_UP       = 6;

  private List<Line> openLines = new ArrayList<Line>();
  private List<Line> closedLines = new ArrayList<Line>();

  public void onTouch(CoreMotionEvent event) {
    CorePoint p = new CorePoint();
    if (event.getAction() == ACTION_DOWN) {
      event.getPointerCoords(0, p);
      openLines.add(new Line(p.x, p.y));
    }
    if (event.getAction() == ACTION_POINTER_DOWN) {
      event.getPointerCoords(0, p);
      openLines.add(new Line(p.x, p.y));
    }
    if (event.getAction() == ACTION_POINTER_UP) {
      closedLines.add(openLines.remove(0));
    }
    if (event.getAction() == ACTION_UP) {
      closedLines.add(openLines.remove(0));
    }
    if (event.getAction() == ACTION_MOVE) {
      for (int pointerIndex = 0; pointerIndex < event.getPointerCount(); pointerIndex++) {
        event.getPointerCoords(pointerIndex, p);
        openLines.get(openLines.size()-1).addPoint(p.x, p.y);
      }
    }
  }

  public List<Line> lines() {
    List lines = new ArrayList();
    lines.addAll(closedLines);
    lines.addAll(openLines);
    return lines;
  }

  public Object lines(int index) {
    return lines().get(index);
  }
}
