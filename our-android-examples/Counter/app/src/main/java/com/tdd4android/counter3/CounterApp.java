package com.tdd4android.counter3;

public class CounterApp {
  private Counter counter;
  private CounterGui gui;

  public CounterApp(Counter counter, CounterGui gui) {
    this.counter = counter;
    this.gui = gui;
  }

  public void increment() {
    counter.increment();
    gui.display(counter.value());
  }
}
