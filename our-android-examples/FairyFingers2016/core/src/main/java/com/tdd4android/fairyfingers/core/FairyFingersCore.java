package com.tdd4android.fairyfingers.core;

import java.util.*;

import static com.tdd4android.fairyfingers.core.Actions.ACTION_DOWN;

public class FairyFingersCore {
  private List<Trail> trails = new ArrayList<>();
  private Trail openTrail;

  public void onTouchEvent(int action, float x, float y) {
    if (ACTION_DOWN == action) {
      openTrail = new Trail(x, y);
      trails.add(openTrail);
    } else {
      openTrail.append(x, y);
    }
  }

  public int trailsCount() {
    return trails.size();
  }

  public Trail getTrail(int index) {
    return trails.get(index);
  }

  public void onDown(float x, float y) {
    trails.add(new Trail(x, y));
  }

  public void onMove(float ... coords) {
  }

  public void onPointerDown(float x, float y) {
    openTrail = new Trail(x, y);
    trails.add(openTrail);
  }
}
