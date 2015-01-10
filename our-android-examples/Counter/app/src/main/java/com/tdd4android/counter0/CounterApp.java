package com.tdd4android.counter0;

import android.widget.TextView;

import com.tdd4android.counter1.CounterActivity;

public class CounterApp {
  private int value;

  public int valueToDisplay() {
    return value;
  }

  public void increment() {
    value++;
  }
}
