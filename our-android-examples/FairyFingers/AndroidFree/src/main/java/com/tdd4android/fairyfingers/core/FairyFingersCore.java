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
  private ColorSequence colors;

  public FairyFingersCore(ColorSequence colors) {
    this.colors = colors;
  }

  public void onTouch(CoreMotionEvent event) {
    switch (event.getAction()) {
      case ACTION_DOWN: {
        openLines.put(0, new Line(newColor(), event.getX(0), event.getY(0)));
        break;
      }
      case ACTION_POINTER_DOWN: {
        int i = event.getActionIndex();
        openLines.put(event.getPointerId(i), new Line(newColor(), event.getX(i), event.getY(i)));
        break;
      }
      case ACTION_UP: {
        Line line = removeOpenLine(event.getPointerId(0));
        closedLines.add(line);
        break;
      }
      case ACTION_POINTER_UP: {
        int i = event.getActionIndex();
        Line line = removeOpenLine(event.getPointerId(i));
        closedLines.add(line);
        break;
      }
      case  ACTION_MOVE: {
        for (int i = 0; i < event.getPointerCount(); i++) {
          Line line = openLines.get(event.getPointerId(i));
          line.addPoint(event.getX(i), event.getY(i));
        }
        break;
      }
      default:
        throw new IllegalArgumentException("Unexpected motion event type " + event.getAction());
    }
  }

  private int newColor() {
    return colors.next();
  }

  public List<Line> lines() {
    List lines = new ArrayList();
    lines.addAll(closedLines);
    lines.addAll(openLines.values());
    return lines;
  }

  public Line lines(int index) {
    return lines().get(index);
  }

  private Line removeOpenLine(int pointerId) {
    Line line = openLines.remove(pointerId);
    if (null == line)
      throw new IllegalStateException("Could not find open line with id " + pointerId);
    return line;
  }

  public void decay() {
    for (Line line : lines()) {
      line.decay();
    }
  }
}
