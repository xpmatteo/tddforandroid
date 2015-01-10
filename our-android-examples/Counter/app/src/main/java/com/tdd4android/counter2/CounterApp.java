package com.tdd4android.counter2;

import android.widget.TextView;

import com.tdd4android.counter0.R;

public class CounterApp {
  private int value;

  public CounterApp(CounterGui gui) {
  }

  public void increment() {
    value++;
  }
}
