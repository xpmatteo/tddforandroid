package com.tdd4android.fairyfingers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tdd4android.fairyfingers.core.CoreCanvas;
import com.tdd4android.fairyfingers.core.FairyFingersCore;

public class FairyFingersView extends View {
  FairyFingersCore core = new FairyFingersCore();

  public FairyFingersView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FairyFingersView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    for (int i=0; i<core.trailsCount(); i++) {
      core.getTrail(i).drawOn(new AndroidCoreCanvas(canvas));
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    core.onTouchEvent(event.getActionMasked(), event.getX(), event.getY());
    invalidate();
    return true;
  }
}
