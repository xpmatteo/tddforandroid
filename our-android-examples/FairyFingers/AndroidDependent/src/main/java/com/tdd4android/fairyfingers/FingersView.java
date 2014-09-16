package com.tdd4android.fairyfingers;

import android.content.Context;
import android.graphics.Canvas;
import android.util.*;
import android.view.*;

import com.tdd4android.fairyfingers.core.FairyFingersApp;

public class FingersView extends View {

  FairyFingersApp app = new FairyFingersApp(null);

  public FingersView(Context context) {
    super(context);
  }

  public FingersView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FingersView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onDraw(Canvas canvas) {

  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    Log.v("TOUCH-EVENT", event.toString());
    return true;
  }
}
