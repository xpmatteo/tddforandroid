package com.tdd4android.fairyfingers;

import android.content.Context;
import android.graphics.*;
import android.util.*;
import android.view.*;

import com.tdd4android.fairyfingers.core.*;

public class FairyFingersView extends View {
  private Paint paint = new Paint();
  private ColorSequence colors = new SummerPalette();
  private FairyFingersCore core = new FairyFingersCore(colors);
  private CoreMotionEvent1 e = new CoreMotionEvent1();

  public FairyFingersView(Context context) {
    super(context);
  }

  public FairyFingersView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FairyFingersView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onDraw(final Canvas canvas) {
    paint.setStrokeWidth(10);
    CoreCanvas coreCanvas = new CoreCanvas() {
      @Override
      public void drawLine(float startX, float startY, float stopX, float stopY, int color, int alpha) {
        paint.setColor(color);
        paint.setAlpha(alpha);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
      }
    };
    for (Line line : core.lines()) {
      line.drawOn(coreCanvas);
    }
  }

  @Override
  public boolean onTouchEvent(final MotionEvent event) {
    e.action = event.getActionMasked();
    e.pointerCount = event.getPointerCount();
    e.actionIndex = event.getActionIndex();
    for (int i = 0; i < event.getPointerCount(); i++) {
      e.pointers[i].pointerId = event.getPointerId(i);
      e.pointers[i].x = event.getX(i);
      e.pointers[i].y = event.getY(i);
    }
    e.deliverEventTo(core);
    invalidate();
    return true;
  }

  public void decay() {
    core.decay();
    invalidate();
  }
}
