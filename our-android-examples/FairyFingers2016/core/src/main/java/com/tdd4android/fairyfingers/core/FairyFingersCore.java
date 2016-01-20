package com.tdd4android.fairyfingers.core;

import java.util.*;

import static com.tdd4android.fairyfingers.core.Actions.ACTION_DOWN;

public class FairyFingersCore {
  private List<Trail> trails = new ArrayList<>();

  public void onMotionEvent(int action, float x, float y) {
    if (ACTION_DOWN == action) {
      trails.add(new Trail(x, y));
    } else {
      trails.get(trails.size() - 1).append(x, y);
    }
  }

  public int trailsCount() {
    return trails.size();
  }

  public Trail getTrail(int index) {
    return trails.get(index);
  }
}
