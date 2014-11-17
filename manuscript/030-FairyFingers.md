# Exercise: Fairy Fingers

## Digging deeper into Android

We've seen how to TDD an application that has a GUI based on ordinary form widgets: text fields and buttons.  Now we'd like to go a bit deeper.  How do we TDD an application that reacts to the user's touch and draws directly on the screen?

The goal of this chapter is to further understand how to decouple the tests from the Android APIs, even when we *need* to use those APIs.


## Problem Description

This problem was invented by Carlo Pescio for his [Ribbons](http://www.aspectroid.com/) book.  The user draws colored lines by dragging fingers on the screen.  The lines fade quickly to nothing.  We call our app "Fairy Fingers".


## Start with a spike

The goals of the spike are:

 * Understand how to draw on the screen
 * Understand how to track the user's finger

We create an empty project and check that it runs.  Then we modify the `res/layout/activity_my.xml` file, removing the standard "hello world" view and replacing it with a custom view.

~~~~~
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MyActivity">

    <com.tdd4android.fairyfingers.spike.MyView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</RelativeLayout>
~~~~~

The IDE complains that the view does not exist, so we go ahead and create it.  We need to override the constructors.  I don't know which ones are really needed, so I override them all.  Then we need to override the `OnDraw` method.  We want to see if we can paint on the screen at all, so we draw a single line.

~~~~~
package com.tdd4android.fairyfingers.spike;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
  public MyView(Context context) {
    super(context);
  }
  public MyView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }
  public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    Paint paint = new Paint();
    paint.setColor(Color.BLUE);
    paint.setStrokeWidth(3);
    canvas.drawLine(10, 10, 100, 100, paint);
  }
}
~~~~~
We run it, and it works!
![We can draw a line!](images/spike-fairy-fingers-0.png)

Now we want to track the user touching the screen.  Basically we must accumulate points in the `onTouchEvent` method, and draw them in the `onDraw` method.  We must also remember to call `invalidate` after every event. This notifies the OS that the screen should be updated; Android will answer by calling `onDraw` later.
~~~~~
public class MyView extends View {
  // ...

  @Override
  protected void onDraw(Canvas canvas) {
    Paint paint = new Paint();
    paint.setColor(Color.BLUE);
    paint.setStrokeWidth(3);
    for (int i=1; i<points.size(); i++) {
      Point from = points.get(i-1);
      Point to = points.get(i);
      canvas.drawLine(from.x, from.y, to.x, to.y, paint);
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    points.add(new Point((int) event.getX(), (int) event.getY()));
    invalidate();
    return true;
  }

  List<Point> points = new ArrayList<Point>();
}
~~~~~
We run it, we drag a finger on the screen and we see the following.
![We can track the user's finger](images/spike-fairy-fingers-1.png)

At the moment, we draw a single line.  Touching again the screen makes the line longer.  The next thing we'd like to do is to draw a new line every time we touch the screen.

The key here is how we use the `MotionEvent` data structure.  The relevant fields return the event coordinates with `getX` and `getY`, and the type of action, that is returned by `getActionMasked`.  The actions we are interested in at the moment are `ACTION_DOWN`, `ACTION_MOVE` and `ACTION_UP`, corresponding to touching, dragging and lifting the finger.  We can exploit this information to clear the old line whenever the finger goes down on the screen.
~~~~~
public boolean onTouchEvent(MotionEvent event) {
  int action = event.getActionMasked();
  if (action == MotionEvent.ACTION_DOWN) {
    points.clear();
    points.add(new Point((int) event.getX(), (int) event.getY()));
  } else if (action == MotionEvent.ACTION_MOVE) {
    points.add(new Point((int) event.getX(), (int) event.getY()));
  }
  invalidate();
  return true;
}
~~~~~

So far, so good.  We could explore the `MotionEvent` further in order to understand how to deal with multitouch, but we can leave that for later.  We learned enough already for writing a first version of Fairy Fingers that only supports single touch.





