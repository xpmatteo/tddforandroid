package com.tdd4android.fairyfingers.core;

public class TrailSet {
  private int trails;

  public int size() {
    return trails;
  }

  public void onFingerDown(int x, int y) {
    trails++;
  }

  public void onFingerMove(int x, int y) {

  }

  public void onFingerUp() {
  }

  public Object get(int index) {
    return "";
  }
}
