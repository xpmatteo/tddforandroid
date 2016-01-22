
# Working with Multi-Touch

The way we dealt with motion events in the previous chapter was simple.  We essentially took only three pieces of information out of the event:

    core.onTouchEvent(event.getActionMasked(), event.getX(), event.getY());

This becomes quite a bit more complex when we start considering multi-touch.  The event will still be about a single pointer (that is, a finger).  But it's not trivial to understand which.  The details are in the Android documentation at [http://developer.android.com/reference/android/view/MotionEvent.html](http://developer.android.com/reference/android/view/MotionEvent.html).

The important things to note are:

 * There are two more actions we need to be aware of: `ACTION_POINTER_DOWN` and `ACTION_POINTER_UP`.  These happen when a finger is being added or removed to a group of fingers on the screen.
 * The event assigns a new *identifier* to every finger that arrives
 * Every event gives us information about the position of *all* pointers (fingers)
 * However, the fingers are reported in an arbitrary sequence within the event record.  Therefore we must distinguish between a pointer *identifier* (which is assigned to a finger and does not change as long as the finger stays on the screen) and a pointer index (that represents a position inside the pointers array contained in the `MotionEvent`)


## Logging information about motion events

If you are like me, after reading the documentation you will still feel a bit confused.  One useful trick to start to get a feeling for what the Android motion event contain is to log some information from the device.

So I start a new spike project, with a custom view, and override the `onTouchEvent()` method of the view this way:


I run the application on a real phone, and I drag one finger on the screen.  In the log I find the following (slightly edited for brevity):

{lang=text}
    MotionEvent { action=ACTION_DOWN, id[0]=0, x[0]=231.0, y[0]=678.0, ... }
    MotionEvent { action=ACTION_MOVE, id[0]=0, x[0]=233.08652, y[0]=678.0, ... }

    .... many more ....

    MotionEvent { action=ACTION_MOVE, id[0]=0, x[0]=407.5, y[0]=405.0, .. }
    MotionEvent { action=ACTION_UP, id[0]=0, x[0]=407.5, y[0]=405.0, ... }

Then I try what happens if I drag first one finger, then two.  I try various combination of drag to see how the events are being generated.  For instance, I observe that when I drag one finger, and touch three times with the other finger, in a pattern like this:

{lang=text}
    -----------
     -- -- --

then the sequence of actions is:

{lang=text}
    ACTION_DOWN
    ACTION_MOVE (many of those)
    ACTION_POINTER_DOWN (I touch the screen with a second finger)
    ACTION_MOVE (many)
    ACTION_POINTER_UP  (I pull up the second finger)
    ACTION_MOVE (many)
    ACTION_POINTER_DOWN (down again)
    ACTION_MOVE (many)
    ACTION_POINTER_UP (up again)
    ACTION_MOVE (many)
    ACTION_UP (up the last finger)

Here is an excerpt:

    MotionEvent { action=ACTION_DOWN, id[0]=0, x[0]=351.2183, y[0]=121.680405,  ...  }
    MotionEvent { action=ACTION_MOVE, id[0]=0, x[0]=338.6877, y[0]=127.67291,  ...  }
    ...
    MotionEvent { action=ACTION_POINTER_DOWN(1), id[0]=0, x[0]=309.3056, y[0]=148.6467, id[1]=1, x[1]=353.21414, y[1]=356.38702  ...  }
    MotionEvent { action=ACTION_MOVE, id[0]=0, x[0]=308.30768, y[0]=149.29865, id[1]=1, x[1]=353.21414, y[1]=356.38702  ...  }
    MotionEvent { action=ACTION_POINTER_UP(1), id[0]=0, x[0]=301.32224, y[0]=152.6417, id[1]=1, x[1]=335.25156, y[1]=362.37955  ...  }
    MotionEvent { action=ACTION_MOVE, id[0]=0, x[0]=300.10278, y[0]=152.6417,  ...  }
    ...
    MotionEvent { action=ACTION_MOVE, id[0]=0, x[0]=96.74844, y[0]=215.56305,  ...  }
    MotionEvent { action=ACTION_UP, id[0]=0, x[0]=96.74844, y[0]=215.56305,  ...  }

Note that:

  * while there are two fingers down, the MotionEvent contains two pairs of coordinates.
  * The `ACTION_POINTER_DOWN` and `ACTION_POINTER_UP` actions contain the index of the finger that was added or removed.  You may get that index with the `getActionIndex()` method.
  * The `ACTION_UP` event is fired when the last finger is lifted
  * The `ACTION_UP` event contains the same coordinates as the last `ACTION_MOVE` event.
  * The `id[0]=0` means that the identifier of the pointer at index 0 is 0, and `id[1]=1` similarly means that the index 1 of the record contains data about the pointer of index 1.  I made several tests, and I notice that it *does* happen to see them swapped: `id[0]=1` would mean that the pointer of identifier 1 is at index 0 in the array.

## Creating an adapter

The Android MotionEvent is way too complicated for my tastes.  I would rather work with an event that presents its information in the way that my core logic wants it.  In particular, we want to be able to get the coordinates of pointers by identifier, and not by index.  So we want to find a way to transform the Android `MotionEvent` into a `CoreMotionEvent`: a class that does not exist yet.  How do we do it?  Starting with a test, of course.

First, let's make a list of the test that we think we need:

 * `action=ACTION_DOWN, id[0]=0, x[0]=351.2183, y[0]=121.680405`
 * `action=ACTION_UP`
 * `action=ACTION_MOVE, id[0]=0, x[0]=338.6877, y[0]=127.67291`
 * `action=ACTION_MOVE, id[0]=0, x[0]=10.0, y[0]=20.0, id[1]=1, x[1]=30.0, y[1]=40.0`
 * `action=ACTION_MOVE, id[0]=1, ..., id[1]=0, ...`
 * `action=ACTION_POINTER_DOWN(1), ...`
 * `action=ACTION_POINTER_UP(1), ...`

Let's start with `ACTION_DOWN`.  How do we write a test?  When in doubt, *start with the assertion*.  So, when we receive an event like `action=ACTION_DOWN, id[0]=0, x[0]=351.2183, y[0]=121.680405`, we'd like to assert that:

 * the action is ACTION_DOWN
 * the number of pointers is 1
 * the coordinates of the first pointer are (351.2183, y[0]=121.680405)

The test we'd like to write is

    @Test
    public void actionDown() throws Exception {
      // pseudo-code
      MotionEvent androidEvent = new MotionEvent(... action=ACTION_DOWN, id[0]=0, x[0]=351.2183, y[0]=121.680405 ...);

      CoreMotionEvent coreEvent = new CoreMotionEvent(androidEvent);

      assertEquals(MotionEvent.ACTION_DOWN, coreEvent.getAction());
      assertEquals(1, coreEvent.getNumberOfPointers());
      // ...
    }

Since it depends on MotionEvent, this test should live in the Android-dependent module of the application.  It probably does not need to run on the device, so we'd like to make it a unit test.  To do so, we create the `CoreMotionEventTest` class in the `app/src/test` folder.  We start with

    @Test
    public void someTest() throws Exception {
      assertEquals(0, 1);
    }

just to see if we can make this test fail.  In order to run it in Android Studio, we must change the "build variant": go to Build -> Select Build Variant.  This will open a small panel that contains a "Test Artifact" menu.  Choose "Unit Tests" in the Test Artifact menu.

{width=50%}
![Choose "Unit Tests" in the Build Variant panel to run tests in `src/test/java`](images/multi-touch/build-variants.png)
