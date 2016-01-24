
# Working with Multi-Touch (IN PROGRESS)

The way we dealt with motion events in the previous chapter was simple.  We essentially took only three pieces of information out of the event:

    core.onTouchEvent(event.getActionMasked(), event.getX(), event.getY());

This becomes more complex when we consider multi-touch.  The event will still be about a single pointer (that is, a finger).  But it's not trivial to understand which.  The details are in the Android documentation at [http://developer.android.com/reference/android/view/MotionEvent.html](http://developer.android.com/reference/android/view/MotionEvent.html). Please read the introduction section of the `MotionEvent` class.

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

## How the core should change

So far, the `FairyFingersCore` has a single "current" trail.  We now keep track of more than one open trails.  The best way to understand how it should change is by writing a test.  The simplest multi-touch scenario I can imagine is

    @Test
    public void oneMorePointerDown() throws Exception {
      core.onDown(10, 20);            // down first finger
      core.onMove(30, 40);            // drag it
      core.onPointerDown(100, 200);   // down second finger
      core.onMove(50, 60, 110, 210);  // drag both

      assertEquals("(10.0,20.0)->(30.0,40.0)->(50.0,60.0)", core.getTrail(0).toString());
      assertEquals("(100.0,200.0)->(110.0,210.0)", core.getTrail(1).toString());
    }

This is the sort of level of detail I would like to have in my test.

Q> ## Why only one pair of coordinates in `onPointerDown()`?
Q>
Q> Because one method call should do one thing; adding an additional finger is similar to adding one finger.  It should just tell me that there is a new finger at the given coordinates.  The `onMove()` should be the method that gives me new coordinates for existing pointers.  At least, this is how I feel the API of `FairyFingersCore` should be.  It seems like the Android engineers think likewise, as I observed in my event logging experiments that in a `ACTION_POINTER_DOWN` event, all the pointer positions of existing pointers are unchanged from the previous `ACTION_MOVE` event.  Similarly, `ACTION_POINTER_UP` and `ACTION_UP` can be observed to never report new coordinates.

Trying to make it pass, I realise that step `core.onMove(50, 60, 110, 210)` forces me to have a notion of "open trails", which I currently don't have. It would be too big a step to add the concept of "open trails" and make the test pass at the same time, so I `@Ignore` the test and refactor `FairyFingersCore`.

    public class FairyFingersCore {
      private List<Trail> trails = new ArrayList<>();
      // leanpub-start-insert
      private Trail openTrail;
      // leanpub-end-insert

      public void onTouchEvent(int action, float x, float y) {
        if (ACTION_DOWN == action) {
          // leanpub-start-insert
          openTrail = new Trail(x, y);
          trails.add(openTrail);
          // leanpub-end-insert
          // leanpub-start-delete
          trails.add(new Trail(x, y));
          // leanpub-end-delete
        } else {
          // leanpub-start-insert
          openTrail.append(x, y);
          // leanpub-end-insert
          // leanpub-start-delete
          trails.get(trails.size() - 1).append(x, y);
          // leanpub-end-delete
        }
      }
    }

T> One variation of red-green-refactor happens when making the current test pass is too difficult.  It may be that the current code is not ready to accept the new feature.  In this case, we `@Ignore` the test and keep refactoring until making the test pass becomes easy.  Remember: we should try to stay in the "green" most of the time.  Spending too much time in the red is a smell.

T> Note that in the previous test we avoided assertions such as `assertEquals(1, core.openTrailsCount())`.  It is tempting to use this sort of assertions as a stepping stone to build the concept we think we need; however, it is better to avoid adding methods that are only needed for asserting on them in tests.  As it happens, we can test the open trails concept perfectly well by using the same methods that are needed by the production code.

A further refactoring that seems necessary (because I try to pass the test and find it still too difficult) to rewrite the old test not to use the generic `onTouchEvent()` method and replace it with the more accurate, expressive calls we're using in the new test.

    public class FairyFingersCoreTest {
      private FairyFingersCore core = new FairyFingersCore();

      @Test
      public void noTrails() throws Exception {
        assertEquals(0, core.trailsCount());
      }

      @Test
      public void justFingerDown() throws Exception {
        core.onDown(10, 20);

        assertEquals(1, core.trailsCount());
        assertEquals("(10.0,20.0)", core.getTrail(0).toString());
      }

      @Test
      public void unfinishedTrail() throws Exception {
        core.onDown(100, 200);
        core.onMove(300, 400);

        assertEquals(1, core.trailsCount());
        assertEquals("(100.0,200.0)->(300.0,400.0)", core.getTrail(0).toString());
      }

      @Test
      public void aFinishedTrail() throws Exception {
        core.onDown(1.1f,   2.2f);
        core.onMove(33.3f,  44.4f);
        core.onUp();

        assertEquals(1, core.trailsCount());
        assertEquals("(1.1,2.2)->(33.3,44.4)", core.getTrail(0).toString());
      }

      @Test
      public void twoTrails() throws Exception {
        core.onDown(1.0f, 100.0f);
        core.onMove(2.0f, 200.0f);
        core.onUp();

        core.onDown(4.0f, 400.0f);
        core.onMove(5.0f, 500.0f);
        core.onUp();

        assertEquals(2, core.trailsCount());
        assertEquals("(1.0,100.0)->(2.0,200.0)", core.getTrail(0).toString());
        assertEquals("(4.0,400.0)->(5.0,500.0)", core.getTrail(1).toString());
      }

      @Test@Ignore
      public void oneMorePointerDown() throws Exception {
        core.onDown(10, 20);            // down first finger
        core.onMove(30, 40);            // drag it
        core.onPointerDown(100, 200);   // down second finger
        core.onMove(50, 60, 110, 210);  // drag both

        assertEquals("(10.0,20.0)->(30.0,40.0)->(50.0,60.0)", core.getTrail(0).toString());
        assertEquals("(100.0,200.0)->(110.0,210.0)", core.getTrail(1).toString());
      }
    }

T> "Refactor mercilessly" was a mantra of early Extreme Programmers.  Don't stop refactoring until the code is really clean.

The tests are still green (except the ignored one), but if we remove `FairyFingersCore::onTouchEvent`, the production code breaks:

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      core.onTouchEvent(event.getActionMasked(), event.getX(), event.getY());
      invalidate();
      return true;
    }

We refactor as follows, with a bit of fear as we know that this code is not covered by any automated test:

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      // leanpub-start-insert
      switch (event.getActionMasked()) {
        case MotionEvent.ACTION_DOWN:
          core.onDown(event.getX(), event.getY());
          break;
        case MotionEvent.ACTION_MOVE:
          core.onMove(event.getX(), event.getY());
        case MotionEvent.ACTION_UP:
          core.onUp();
      }
      // leanpub-end-insert
      invalidate();
      return true;
    }

We run the app and check that it still works.  It's OK: good! We commit the code and continue.  This untested switch is a smell that we'll have to deal with later.  We still need one more refactoring to make it so that it will be easy to implement our ignored test: the single `openTrail` should become a list of `openTrails`.

    public class FairyFingersCore {
      private List<Trail> trails = new ArrayList<>();
      private List<Trail> openTrails = new ArrayList<>();

      public int trailsCount() {
        return trails.size();
      }

      public Trail getTrail(int index) {
        return trails.get(index);
      }

      public void onDown(float x, float y) {
        Trail newTrail = new Trail(x, y);
        openTrails.add(newTrail);
        trails.add(newTrail);
      }

      public void onMove(float ... coords) {
        openTrails.get(0).append(coords[0], coords[1]);
      }

      public void onUp() {
        openTrails.remove(0);
      }

      public void onPointerDown(float x, float y) {
      }
    }

We finally are able to un-`@Ignore` the last test and make it pass!

    public class FairyFingersCore {
      // ...
      public void onMove(float ... coords) {
        // leanpub-start-insert
        for (int i=0; i<openTrails.size(); i++) {
          float x = coords[i*2];
          float y = coords[i*2+1];
          Trail trail = openTrails.get(i);
          trail.append(x, y);
        }
        // leanpub-end-insert
        // leanpub-start-delete
        openTrails.get(0).append(coords[0], coords[1]);
        // leanpub-end-delete
      }

      public void onPointerDown(float x, float y) {
        // leanpub-start-insert
        onDown(x, y);
        // leanpub-end-insert
      }
    }

I'm not happy with the index math in `onMove()` but I don't know yet how this code will want to be refactored.









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

We should also be prepared to (according to the `MotionEvent` javadoc):

  * handle `ACTION_CANCEL`
  * handle `ACTION_DOWN` without first having received an `ACTION_UP` for the prior gesture.

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
![Choose "Unit Tests" in the Build Variant panel to run unit tests in `app/src/test/java`](images/multi-touch/build-variants.png)
