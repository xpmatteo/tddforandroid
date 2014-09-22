package com.tdd4android.fairyfingers;

import android.content.Context;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;

import com.tdd4android.fairyfingers.core.*;

import java.util.concurrent.Delayed;

public class FairyFingersView extends View {

  private static final long REFRESH_INTERVAL = 50;

  private Paint paint = new Paint();
  private FairyFingersCore core = new FairyFingersCore();

  Runnable updateView = new Runnable() {
    @Override
    public void run() {
      core.decay();
      invalidate();
      handler.postDelayed(updateView, REFRESH_INTERVAL);
    }
  };

  private Handler handler;

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
    if (null == handler) {
      this.handler = new Handler();
      handler.post(updateView);
    }

    // stop refresh with handler.removeCallbacks(updateView);

    paint.setColor(Color.BLUE);
    paint.setStrokeWidth(8);
    for (Line line : core.lines()) {
      line.drawOn(new CoreCanvas() {
        @Override
        public void drawLine(float startX, float startY, float stopX, float stopY, int alpha) {
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
}
