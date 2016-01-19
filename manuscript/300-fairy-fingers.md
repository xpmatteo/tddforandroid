# Drawing on the screen and sensing touch

## Digging deeper into Android

We've seen how to TDD an application based on ordinary form widgets: text fields and buttons.  Now we'd like to go a bit deeper.  How do we TDD an application that reacts to the user's touch and draws directly on the screen?

The goal of this chapter is to further understand how to decouple the tests from the Android APIs, even when we *need* to use those APIs.  You may find the source code for this chapter in [https://github.com/xpmatteo/fairy-fingers](https://github.com/xpmatteo/fairy-fingers).


## Problem Description

This problem was invented by Carlo Pescio for his [Ribbons](http://www.aspectroid.com/) book.  The user draws colored lines by dragging fingers on the screen.  The lines fade quickly to nothing.  We call our app "Fairy Fingers".


## Start with a spike

The goals of the spike are:

 * Understand how to draw on the screen
 * Understand how to track the user's finger

We create an empty project and check that it shows a "hello world" on our device.   Then we modify the `res/layout/activity_my.xml` file, removing the standard "hello world" view and replacing it with a custom view (see line 7 in next listing).

{lang="xml", line-numbers=on}
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

{width=60%}
![We can draw a line!](images/spike-fairy-fingers-0.png)

Now we want to track the user touching the screen.  Android calls the `onTouchEvent` method whenever the user touches or drags her finger on the screen.  It gives us a `MotionEvent` object that contains the screen coordinates of the user's finger.  We must store those coordinates in a list
 and then draw them in the `onDraw` method.  We must also remember to call `invalidate` after every touch event. This notifies the OS that the screen should be updated; Android will answer by calling `onDraw` later.

~~~~~
public class MyView extends View {
  private List<Point> points = new ArrayList<Point>();

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
}
~~~~~

We drag a finger on the screen and we see the following.

{width=60%}
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

So far, so good.  We could explore the `MotionEvent` further in order to understand multitouch, but we can leave that for later.  We learned enough already for writing a first version of Fairy Fingers that only supports single touch.

What have we learned?  There are two points where we interact with the device.

1. When the user touches the screen, we receive data through the `onTouchEvent(MotionEvent e)` call. We should accumulate these data in some kind of data structure.
2. When the screen is being drawn, Android calls `onDraw(Canvas c)`.  We should use our previously created data structure to know what we should draw on the canvas.

{width=100%}
![How FairyFingers interacts with the device](images/spike-fairy-fingers-3.jpg)


## Acceptance tests

We start with the following acceptance tests:

 - Single touch.  Dragging the finger should produce a colored trail
 - Fade.  The trails should fade to nothing.
 - Many trails.  We draw a trail, then we draw another.
 - Many colours.  Every time we draw a trail, we should get a different colour
 - Multi-touch.  Dragging two fingers should produce two trails.
 - Multi-touch dashes.  We draw a continuous trail with one finger, and a dashed trail with another finger at the same time.  We should see a pattern like

        --   --   --
        ------------


These acceptance tests are meant to be executed manually.  Some can and will be automated.  The ones that deal with multi-touch cannot be automated with present-generation tools (Monkeyrunner).

Note that we started using the word "trail" instead of "line".  We'd like to have a special name for the "things" that we draw with in this app, and the word "line" is both too generic and too specific.  The Android `canvas` object has a `drawLine` method; we'd like to distinguish our own "lines" from what Android calls a "line".  Therefore we will call them "trails".

## Setup the project

We create a new project for Fairy Fingers.  We start much like we did in the spike; we create a custom view.  Then we add a new `Core` module that will contain the pure Java code, as described in the [Hello, World](#hello-world) chapter.

Our entry point will be in methods `onDraw()` and `onTouchEvent()` of the `FairyFingersView`.  We will delegate most of the work to a `FairyFingersCore` object.  We imagine that we will have something like the following pseudo-code:

~~~~~~~
private FairyFingersCore core = new FairyFingersCore();

@Override
protected void onDraw(Canvas canvas) {
  for every trail in core {
    draw the trail on the canvas
  }
}

@Override
public boolean onTouchEvent(MotionEvent event) {
  core.onTouchEvent(event.getActionMasked(), event.getX(), event.getY());
  invalidate();
  return true;
}
~~~~~~~


## Start the TDD

The first step for TDD is to write a test list.  We start by writing a todo list from the acceptance tests list:

 - create a two-points trail
 - create a many-points trail
 - create two trails
 - fade a trail
 - randomize colours
 - draw all the trails

Where do we start?  We choose "create a two points trail" because we'd like to discover how we will solve this. Now we can write the first test.

~~~~~
package com.tdd4android.fairyfingers.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class FairyFingersCoreTest {
  // Constants copied from android.view.MotionEvent
  public static final int ACTION_DOWN             = 0;
  public static final int ACTION_UP               = 1;
  public static final int ACTION_MOVE             = 2;

  @Test
  public void twoPointsTrail() throws Exception {
    FairyFingersCore core = new FairyFingersCore();

    core.onMotionEvent(ACTION_DOWN, 10, 20);
    core.onMotionEvent(ACTION_MOVE, 30, 40);
    core.onMotionEvent(ACTION_UP, 50, 60);

    assertEquals(1, core.trailsCount());
  }
}
~~~~~~

While we write this test, we think of a two simpler ones:

~~~~~
@Test
public void noTrails() throws Exception {
  FairyFingersCore core = new FairyFingersCore();
  assertEquals(0, core.trailsCount());
}
~~~~~~

and

~~~~~
@Test
public void unfinishedTrail() throws Exception {
  FairyFingersCore core = new FairyFingersCore();

  core.onMotionEvent(ACTION_DOWN, 10, 20);
  core.onMotionEvent(ACTION_MOVE, 30, 40);

  assertEquals(1, core.trailsCount());
}
~~~~~

The last one is needed because we expect the trail to be visible even while it's not finished yet.

The first implementation of `FairyFingersCore` makes these three tests pass, but is not very useful yet.

~~~~~
package com.tdd4android.fairyfingers.core;

public class FairyFingersCore {
  private int trailsCount;

  public int trailsCount() {
    return trailsCount;
  }

  public void onMotionEvent(int action, float x, float y) {
    if (action == ACTION_DOWN)
      trailsCount++;
  }
}
~~~~~

The problem is in the assertions.  The assertion on the `trailsCount()` by itself is not very useful.  It does not prove that the important data about the trail have been memorized. How can we improve the tests in a way that forces us to flesh out `FairyFingersCore` better?

(Pause for a minute.  What would YOU do?)

* * *

The FairyFingersCore should build a Trail object, and we'd like this Trail to contain exactly the coordinates that were supplied by the tests.  One way to do this is with getters:

~~~~~
assertEquals(10, core.getTrail(0).getPoints(0).getX());
assertEquals(20, core.getTrail(0).getPoints(0).getY());
assertEquals(30, core.getTrail(0).getPoints(1).getX());
assertEquals(40, core.getTrail(0).getPoints(1).getY());
~~~~~

But this test code is extremely boring to write!  Being bored is an important signal.  It's the test pushing back: it doesn't want to be written like this.  One concrete problem is that there are too many "dots" in the assertion.  We dig too much further into the objects.  One other problem is that we are assuming that we will need all those getters; it's not clear yet that these getters will be used in production code.

Trick: use "toString".  The `toString` of the `Trail` will certainly be needed for debugging and logging.  How about:

~~~~~
assertEquals("(10,20)->(30,40)", core.getTrail(0).toString());
~~~~~

In our test, we are only assuming that the `FairyFingersCore` will return an object that somehow contains the expected points in a certain order.  We don't even assert what kind of object it is.  It looks promising!  The tests are rewritten in the following style:

~~~~~~
private FairyFingersCore core = new FairyFingersCore();

@Test
public void justFingerDown() throws Exception {
  core.onMotionEvent(ACTION_DOWN, 10, 20);

  assertEquals(1, core.trailsCount());
  assertEquals("(10.0,20.0)", core.getTrail(0).toString());
}

@Test
public void unfinishedTrail() throws Exception {
  core.onMotionEvent(ACTION_DOWN, 100, 200);
  core.onMotionEvent(ACTION_MOVE, 300, 400);

  assertEquals(1, core.trailsCount());
  assertEquals("(100.0,200.0)->(300.0,400.0)", core.getTrail(0).toString());
}

@Test
public void aFinishedTrail() throws Exception {
  core.onMotionEvent(ACTION_DOWN, 1.1, 2.2);
  core.onMotionEvent(ACTION_MOVE, 3.33, 4.44);
  core.onMotionEvent(ACTION_UP, 5.555, 6.666);

  assertEquals(1, core.trailsCount());
  assertEquals("(1.1,2.2)->(3.33,4.44)->(5.555,6.666)", core.getTrail(0).toString());
}
~~~~~~
A> We moved the FairyFingersCore object in a field, in order to remove the duplication of creating it in every test.  We could use a `@Before` method to initialize it, but doing it this way is shorter.  We rely on the fact that JUnit creates a new `FairyFingersCoreTest` object for every test method it calls.  Therefore, every test has a fresh `FairyFingersCore` object.
A>
A> Also note that we always use different numbers in the tests.  If we wrote `core.onMotionEvent(ACTION_DOWN, 10, 10)` we would be open to the risk of swapping x and y in our production code.
A>
A> We avoid using the same test data in multiple tests.  Using always different data prevents us to leave hardcoded test data in production code!
A>
A> One last observation: Android uses the `float` data type for coordinates. Our code should do the same.