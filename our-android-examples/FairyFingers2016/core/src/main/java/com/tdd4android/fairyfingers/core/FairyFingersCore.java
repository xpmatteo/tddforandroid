package com.tdd4android.fairyfingers.core;

import java.util.*;

import static com.tdd4android.fairyfingers.core.Actions.ACTION_DOWN;

public class FairyFingersCore {
  private List<Trail> trails = new ArrayList<>();
  private Trail openTrail;

  public int trailsCount() {
    return trails.size();
  }

  public Trail getTrail(int index) {
    return trails.get(index);
  }

  public void onDown(float x, float y) {
    openTrail = new Trail(x, y);
    trails.add(openTrail);
  }

  public void onMove(float ... coords) {
    openTrail.append(coords[0], coords[1]);
  }

  public void onPointerDown(float x, float y) {
    openTrail = new Trail(x, y);
    trails.add(openTrail);
  }

  public void onUp() {
  }
}
