package com.tdd4android.fairyfingers.core;

import java.util.*;

public class Line {
  private int color;
  private float startX;
  private float startY;
  private List<CorePoint> points = new ArrayList<CorePoint>();

  public Line(int color, float x, float y) {
    this.color = color;
    this.startX = x;
    this.startY = y;
  }

  public void addPoint(float x, float y) {
    points.add(new CorePoint(x, y));
  }

  public String toString() {
    String result = String.format("(%s,%s)", startX, startY);
    for (CorePoint p : points) {
      result += String.format("->(%s,%s)", p.x, p.y);
    }
    return result;
  }

  public void drawOn(CoreCanvas canvas) {
    CorePoint last = new CorePoint(startX, startY);
    for (CorePoint point : points) {
        if (point.alpha > 0)
            canvas.drawLine(last.x, last.y, point.x, point.y, color, point.alpha);
      last = point;
    }
  }

  public void decay() {
    for (CorePoint point : points) {
      point.decay();
    }
  }
}
