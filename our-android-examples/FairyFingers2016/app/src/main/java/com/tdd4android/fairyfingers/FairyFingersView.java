package com.tdd4android.fairyfingers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class FairyFingersView extends View {
  public FairyFingersView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FairyFingersView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    Paint paint = new Paint();
    paint.setColor(Color.MAGENTA);
    paint.setStrokeWidth(3);
    canvas.drawLine(100, 100, 200, 200, paint);
  }
}
