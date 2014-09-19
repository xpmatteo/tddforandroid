package com.tdd4android.fairyfingers;

import android.content.Context;
import android.graphics.*;
import android.util.*;
import android.view.*;

import com.tdd4android.fairyfingers.core.*;

public class FairyFingersView extends View {

  private Paint paint = new Paint();
  private FairyFingersCore core = new FairyFingersCore();

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
    paint.setColor(Color.BLUE);
    paint.setStrokeWidth(4);
    for (Line line : core.lines()) {
      line.drawOn(new Drawable() {
        @Override
        public void drawLine(float startX, float startY, float stopX, float stopY) {
          canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
      });
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    event.getPointerCount();
    event.getPointerId(0);
    MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
    event.getPointerCoords(0, coords);
    core.touch(event.getAction(), event.getX(), event.getY());
    invalidate();
    return true;
  }
}
