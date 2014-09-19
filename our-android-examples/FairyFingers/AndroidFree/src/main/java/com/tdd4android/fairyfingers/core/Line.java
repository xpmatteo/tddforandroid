package com.tdd4android.fairyfingers.core;

import java.util.*;

public class Line {
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

  public void drawOn(Drawable canvas) {
    Point last = new Point(startX, startY);
    for (Point point : points) {
      canvas.drawLine(last.x, last.y, point.x, point.y);
      last = point;
    }
  }
}
