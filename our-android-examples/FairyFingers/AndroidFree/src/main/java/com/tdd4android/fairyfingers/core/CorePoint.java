package com.tdd4android.fairyfingers.core;

class CorePoint {
  public float x;
  public float y;
  public int alpha;

  CorePoint(float x, float y) {
    this.x = x; this.y = y; this.alpha=255;
  }

  public void decay() {
    this.alpha = Math.max(alpha-25, 0);
  }

  public boolean isVisible() {
    return alpha > 0;
  }
}
