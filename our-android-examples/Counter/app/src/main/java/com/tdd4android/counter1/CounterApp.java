package com.tdd4android.counter1;

import android.widget.TextView;

import com.tdd4android.counter0.R;

public class CounterApp {
  private int value;
  private CounterActivity counterActivity;

  public CounterApp(CounterActivity counterActivity) {
    this.counterActivity = counterActivity;
  }

  public void increment() {
    value++;
    TextView view = (TextView) counterActivity.findViewById(R.id.counter);
    view.setText(String.valueOf(value));
  }
}
