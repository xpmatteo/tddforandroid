package com.tdd4android.fairyfingersspike;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.*;

import java.util.List;

public class FingerView extends View {
  float cxStart = 100;
  float cyStart = 100;
  float cxEnd = 200;
  float cyEnd = 200;

  Path path;

  public FingerView(Context context) {
    super(context);
  }

  public FingerView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public FingerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    Paint paint = new Paint();
    paint.setColor(Color.BLUE);
    if (null == path) {
      path = new Path();
      path.moveTo(100, 100);
      path.lineTo(200, 200);
    }
    paint.setColor(Color.RED);
    paint.setStrokeWidth(3);
    paint.setStyle(Paint.Style.STROKE);
    canvas.drawPath(path, paint);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int action = event.getAction();
    if (action == MotionEvent.ACTION_DOWN) {
      cxStart = event.getX();
      cyStart = event.getY();
    } else if (action == MotionEvent.ACTION_MOVE) {
      cxEnd = event.getX();
      cyEnd = event.getY();
    }
    this.invalidate();
    return true;
  }
}
