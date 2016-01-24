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
    for (int i=0; i<openTrails.size(); i++) {
      float x = coords[i*2];
      float y = coords[i*2+1];
      Trail trail = openTrails.get(i);
      trail.append(x, y);
    }
  }

  public void onUp() {
    openTrails.remove(0);
  }

  public void onPointerDown(float x, float y) {
    onDown(x, y);
  }
}
