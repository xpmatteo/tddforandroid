package com.tdd4android.counter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CounterActivity extends ActionBarActivity implements View.OnClickListener {

  private CounterApp app = new CounterApp();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_counter);

    Button incrementButton = (Button) findViewById(R.id.increment);
    incrementButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    app.increment();
    TextView textView = (TextView) findViewById(R.id.counter);
    textView.setText(String.valueOf(app.valueToDisplay()));
  }
}
