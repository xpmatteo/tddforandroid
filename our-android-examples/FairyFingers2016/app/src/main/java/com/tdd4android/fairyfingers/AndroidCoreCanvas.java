package com.tdd4android.fairyfingers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.tdd4android.fairyfingers.core.CoreCanvas;

public class AndroidCoreCanvas implements CoreCanvas {
  private Canvas canvas;
  private Paint paint = new Paint();

  public AndroidCoreCanvas(Canvas canvas) {
    this.canvas = canvas;
    paint.setColor(Color.BLUE);
    paint.setStrokeWidth(3);
  }

  @Override
  public void drawLine(float startX, float startY, float stopX, float stopY) {
    canvas.drawLine(startX, startY, stopX, stopY, paint);
  }
}
