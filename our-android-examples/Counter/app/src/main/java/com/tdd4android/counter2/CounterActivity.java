package com.tdd4android.counter2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tdd4android.counter0.R;


public class CounterActivity extends Activity implements CounterGui {
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

  @Override
  public void display(int number) {
    TextView textView = (TextView) findViewById(R.id.counter);
    textView.setText(String.valueOf(number));
  }
}
