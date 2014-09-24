package com.tdd4android.fairyfingers;

import android.content.Context;
import android.graphics.*;
import android.util.*;
import android.view.*;

import com.tdd4android.fairyfingers.core.*;

import java.util.Iterator;

public class FairyFingersView extends View {
  private Paint paint = new Paint();
  private Iterator<Integer> colors = new SummerPalette();
  private FairyFingersCore core = new FairyFingersCore(colors);

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
    for (Line line : core.lines()) {
      line.drawOn(new CoreCanvas() {
        @Override
        public void drawLine(float startX, float startY, float stopX, float stopY, int color, int alpha) {
          paint.setColor(color);
          paint.setAlpha(alpha);
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
      public float getX(int pointerIndex) {
        return event.getX(pointerIndex);
      }

      @Override
      public float getY(int pointerIndex) {
        return event.getY(pointerIndex);
      }

      @Override
      public int getActionIndex() {
        return event.getActionIndex();
      }

      @Override
      public int getAction() {
        return event.getActionMasked();
      }
    });
    invalidate();
    return true;
  }

  public void decay() {
    core.decay();
    invalidate();
  }
}
