package com.tdd4android.fairyfingers.core;

import java.util.List;

public class FairyFingersApp {
  public FairyFingersApp(PathStorage pathStorage) {

  }

  public void onDraw() {

  }

  public void touch(int x, int y, int pathIdentifier) {
    System.out.println(String.format("Siamo disperati %s %s %s", x, y, pathIdentifier));
  }
}
