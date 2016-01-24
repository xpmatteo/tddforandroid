package com.tdd4android.fairyfingers.core;

import java.util.*;

import static com.tdd4android.fairyfingers.core.Actions.ACTION_DOWN;

public class FairyFingersCore {
  private List<Trail> trails = new ArrayList<>();
  private List<Trail> openTrails = new ArrayList<>();

  public int trailsCount() {
    return trails.size();
  }

  public Trail getTrail(int index) {
    return trails.get(index);
  }

  public void onDown(float x, float y) {
    Trail newTrail = new Trail(x, y);
    openTrails.add(newTrail);
    trails.add(newTrail);
  }

  public void onMove(float ... coords) {
    openTrails.get(0).append(coords[0], coords[1]);
  }

  public void onUp() {
    openTrails.remove(0);
  }

  public void onPointerDown(float x, float y) {
  }
}
