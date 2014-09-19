package com.tdd4android.fairyfingers.core;

import java.util.*;

public class FairyFingersCore {
  public static final int ACTION_DOWN             = 0;
  public static final int ACTION_UP               = 1;
  public static final int ACTION_MOVE             = 2;
  public static final int ACTION_CANCEL           = 3;

  private List<Line> lines = new ArrayList<Line>();

  private static class Point {
    float x, y;
    Point(float x, float y) {
      this.x = x; this.y = y;
    }
  }

  private static class Line {
    private float startX;
    private float startY;
    private List<Point> points = new ArrayList<Point>();

    public Line(float x, float y) {
      this.startX = x;
      this.startY = y;
    }

    public void addPoint(float x, float y) {
      points.add(new Point(x, y));
    }

    public String toString() {
      String result = String.format("(%s,%s)", startX, startY);
      for (Point p : points) {
        result += String.format("->(%s,%s)", p.x, p.y);
      }
      return result;
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

  public Collection lines() {
    return lines;
  }

  public Object lines(int index) {
    return lines.get(index);
  }
}
