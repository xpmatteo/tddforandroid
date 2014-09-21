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
  public boolean onTouchEvent(final MotionEvent event) {
    core.onTouch(new CoreMotionEvent() {
      @Override
      public int getPointerCount() {
        return event.getPointerCount();
      }

      @Override
      public int getPointerId(int pointerIndex) {
        return event.getPointerId(pointerIndex);
      }

      @Override
      public void getPointerCoords(int pointerIndex, CorePoint outPointerCoords) {
        MotionEvent.PointerCoords coords = new MotionEvent.PointerCoords();
        event.getPointerCoords(pointerIndex, coords);
        outPointerCoords.x = coords.x;
        outPointerCoords.y = coords.y;
      }

      @Override
      public int getActionIndex() {
        return event.getActionIndex();
      }

      @Override
      public int getAction() {
        return event.getAction();
      }
    });

    invalidate();
    return true;
  }
}
