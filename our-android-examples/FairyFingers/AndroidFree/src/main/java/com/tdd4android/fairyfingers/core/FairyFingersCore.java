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

  private Map<Integer, Line> openLines = new HashMap<Integer, Line>();
  private List<Line> closedLines = new ArrayList<Line>();

  public void onTouch(CoreMotionEvent event) {
    if (event.getAction() == ACTION_DOWN) {
      openLines.put(0, new Line(event.getX(0), event.getY(0)));
    } else if (event.getAction() == ACTION_POINTER_DOWN) {
      int i = event.getActionIndex();
      openLines.put(event.getPointerId(i), new Line(event.getX(i), event.getY(i)));
    } else if (event.getAction() == ACTION_UP) {
      Line line = openLines.remove(event.getPointerId(0));
      if (null == line)
        throw new IllegalStateException("doppio ohibo' " + event.getPointerId(0));
      closedLines.add(line);
    } else if (event.getAction() == ACTION_POINTER_UP) {
      int i = event.getActionIndex();
      Line line = openLines.remove(event.getPointerId(i));
      if (null == line)
        throw new IllegalStateException("ohibo' " + event.getPointerId(i));
      closedLines.add(line);
    } else if (event.getAction() == ACTION_MOVE) {
      for (int i = 0; i < event.getPointerCount(); i++) {
        Line line = openLines.get(event.getPointerId(i));
        line.addPoint(event.getX(i), event.getY(i));
      }
    } else {
      throw new IllegalArgumentException("OHIBO OHIBO!" + event.getAction());
    }
  }

  public List<Line> lines() {
    List lines = new ArrayList();
    lines.addAll(closedLines);
    lines.addAll(openLines.values());
    return lines;
  }

  public Object lines(int index) {
    return lines().get(index);
  }
}
