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

{lang="xml"}
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


## Acceptance tests

We start with the following acceptance tests:

 - Single touch.  Dragging the finger should produce a colored trail
 - Fade.  The trails should fade to nothing.
 - Many trails.  We draw a trail, then we draw another.
 - Many colours.  Every time we draw a trail, we should get a different colour
 - Multi-touch.  Dragging two fingers should produce two trails.
 - Multi-touch dashes.  We draw a continuous trail with one finger, and a dashed trail with another finger at the same time.  We should see a pattern like

~~~~~
--   --   --
------------
~~~~~

These acceptance tests are meant to be executed manually.  Some can and will be automated.  The ones that deal with multi-touch cannot be automated with present-generation tools (Monkeyrunner).

Note that we started using the word "trail" instead of "line".  We'd like to have a special name for the "things" that we draw with in this app, and the word "line" is both too generic and too specific.  The Android `canvas` object has a `drawLine` method; we'd like to distinguish our own "lines" from what Android calls a "line".  Therefore we will call them "trails".

## Setup the project

We create a new project for Fairy Fingers.  We start much like we did in the spike; we create a custom view and then we stop to consider.  What next?

Before we can write any test, we need to update the project.  The pure Java tests will live in a separate, android-free module that we call `core`.

1. We create a module of type "Java Library" with Android Studio.
2. We name it `core`.
3. We create the folder `core/src/test/java` in it.
4. We add JUnit support to `core/build.gradle`

The updated Gradle file looks like this:

{line-numbers=on, lang="groovy"}
~~~~~
apply plugin: 'java'

dependencies {
   compile fileTree(dir: 'libs', include: ['*.jar'])
   testCompile 'junit:junit-dep:4.11'
   testCompile 'org.jmock:jmock-junit4:2.6.0'
}

test {
   testLogging {
       events "passed", "skipped", "failed", "standardOut", "standardError"
   }
}
~~~~~
  Lines 5-6 add support for JUnit and JMock.  Lines 9-13 improve the way Android Studio reports test results.

We test the setup by creating an simple test file and watching it fail.



## TDD

The first step for TDD is to write a test list.  We start by copying things from the acceptance tests list:

 - create a two-points trail
 - create a many-points trail
 - create two trails
 - fade a trail
 - randomize colours
 - draw all the trails

Where do we start?  We choose "create a two points trail" because we'd like to discover how we will solve this.

Which object will accumulate points?  I imagine there will be an object that represents a set of trails.  It will receive messages from the `onTouchEvent` method of the view and it will react accordingly.

Now we can write the first test.

~~~~~
package com.tdd4android.fairyfingers.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class TrailSetTest {
  @Test
  public void twoPointsTrail() throws Exception {
    TrailSet trailSet = new TrailSet();

    trailSet.onFingerDown(10, 20);
    trailSet.onFingerMove(30, 40);
    trailSet.onFingerUp();

    assertEquals(1, trailSet.size());
  }
}
~~~~~~

While we write this test, we think of a two simpler ones:

~~~~~
@Test
public void noTrails() throws Exception {
  TrailSet trailSet = new TrailSet();
  assertEquals(0, trailSet.size());
}
~~~~~~

and

~~~~~
@Test
public void unfinishedTrail() throws Exception {
  TrailSet trailSet = new TrailSet();

  trailSet.onFingerDown(10, 20);
  trailSet.onFingerMove(30, 40);

  assertEquals(1, trailSet.size());
}
~~~~~

The last one is needed because we expect the trail to be visible even while it's not finished yet.

The first implementation of `TrailSet` makes these three tests pass, but is not very useful yet.

~~~~~
package com.tdd4android.fairyfingers.core;

public class TrailSet {
  private int trails;

  public int size() {
    return trails;
  }

  public void onFingerDown(int x, int y) {
    trails++;
  }

  public void onFingerMove(int x, int y) {
  }

  public void onFingerUp() {
  }
}
~~~~~

How can we improve the tests in a way that forces us to make `TrailSet` more useful?

(Pause for a minute.  What would YOU do?)

The TrailSet should build a Trail object, and we'd like this Trail to contain exactly the coordinates that were supplied by the tests.  One way to do this is with getters:

~~~~~
assertEquals(10, trailSet.get(0).getPoints(0).getX());
assertEquals(20, trailSet.get(0).getPoints(0).getY());
assertEquals(30, trailSet.get(0).getPoints(1).getX());
assertEquals(40, trailSet.get(0).getPoints(1).getY());
~~~~~

But this test code is extremely boring to write!  Being bored is an important signal.  It's the test pushing back: it doesn't want to be written like this.  One concrete problem is that there are too many "dots" in the assertion.  We dig too much further into the objects.  One other problem is that we are assuming that we will need all those getters; it's not clear yet that these getters will be used in production code.

Trick: use "toString".  The `toString` of the `Trail` will certainly be needed for debugging and logging.  How about:

~~~~~
assertEquals("(10,20)->(30,40)", trailSet.get(0).toString());
~~~~~

In this test, are only assuming that the `TrailSet` will return an object that contains the expected points in a certain order.  It looks promising!


