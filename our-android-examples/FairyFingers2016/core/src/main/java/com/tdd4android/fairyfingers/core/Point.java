package com.tdd4android.fairyfingers.core;

class Point {
  float x, y;

  public Point(float x, float y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return String.format("(%.1f,%.1f)", x, y);
  }
}

