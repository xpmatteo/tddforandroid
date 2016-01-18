package com.tdd4android.counter2;

public class CounterApp {
  private int value;
  private CounterGui gui;

  public CounterApp(CounterGui gui) {
    this.gui = gui;
  }

  public void increment() {
    value++;
    gui.display(value);
  }
}
