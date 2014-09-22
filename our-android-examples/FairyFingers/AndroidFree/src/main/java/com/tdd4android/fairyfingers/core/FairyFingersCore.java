package com.tdd4android.fairyfingers.core;

import java.util.*;

public class FairyFingersCore {

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

  private List<Line> openLines = new ArrayList<Line>();
  private List<Line> closedLines = new ArrayList<Line>();

  public void onTouch(CoreMotionEvent event) {
    if (event.getAction() == ACTION_DOWN) {
      openLines.add(new Line(event.getX(0), event.getY(0)));
    }
    if (event.getAction() == ACTION_UP) {
      closedLines.add(openLines.remove(0));
    }
    if (event.getAction() == ACTION_MOVE) {

      for (int pointerIndex = 0; pointerIndex < event.getPointerCount(); pointerIndex++) {
        openLines.get(openLines.size()-1).addPoint(event.getX(pointerIndex), event.getY(pointerIndex));
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
