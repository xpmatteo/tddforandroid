package com.tdd4android.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.tdd4android.helloworld.core.HelloWorld;

public class HelloWorldActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hello_world);
    TextView view = (TextView) findViewById(R.id.message);
    view.setText(new HelloWorld().message());
  }
}
