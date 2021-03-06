package com.tdd4android.counter1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tdd4android.counter0.R;


public class CounterActivity extends Activity {

  private CounterApp app = new CounterApp(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_counter);

    View incrementButton = findViewById(R.id.increment);
    incrementButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        app.increment();
      }
    });
  }
}
