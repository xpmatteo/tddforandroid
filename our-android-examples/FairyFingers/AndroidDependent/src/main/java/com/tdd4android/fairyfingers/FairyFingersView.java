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
  private FingerEventAdapter fingerEventAdapter = new FingerEventAdapter(core);

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
    fingerEventAdapter.onTouchEvent(new CoreMotionEventAdapter(event));
    invalidate();
    return true;
  }

  public void decay() {
    core.decay();
    invalidate();
  }

  private static class CoreMotionEventAdapter implements CoreMotionEvent {
    private final MotionEvent event;

    public CoreMotionEventAdapter(MotionEvent event) {
      this.event = event;
    }

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
  }
}
