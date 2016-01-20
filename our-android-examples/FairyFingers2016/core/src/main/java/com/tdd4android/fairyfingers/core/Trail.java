package com.tdd4android.fairyfingers.core;

import java.util.ArrayList;
import java.util.List;

public class Trail {
  private List<Point> points = new ArrayList<>();

  public Trail(float x, float y) {
    points.add(new Point(x, y));
  }

  public void append(float x, float y) {
    points.add(new Point(x, y));
  }

  @Override
  public String toString() {
    String description = "";
    for (Point p : points) {
      if (!description.isEmpty())
        description += "->";
      description += p.toString();
    }
    return description;
  }

  public void drawOn(CoreCanvas canvas) {
    Point start = points.get(0);
    for (int i=1; i<points.size(); i++) {
      Point stop = points.get(i);
      canvas.drawLine(start.x, start.y, stop.x, stop.y);
      start = stop;
    }
  }
}
