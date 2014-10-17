# A little theory


## How TDD works





## Acceptance-TDD (ATDD)

One pattern that is mentioned in the original [TDD book by Kent Beck](#tdd) is "Child Test".  It means that when it takes me too long to get a test to pass, it probably means that the test represents too big a step for me.  In that case, Beck suggests that you comment out the problematic test, and start writing a simpler test, a "child test".  The child test lets me take a smaller step in the direction of making the larger test pass.  After a while, and probably after several TDD cycles, we are ready to make the big test pass.

One instance of the Child Test pattern is when we write an end-to-end acceptance test as a first step in getting a new feature to work.  That test is likely to take more than a few minutes to pass.  In Android, an end-to-end test will also take minutes to run.  Yet, the end-to-end test is very useful.

 1. It makes me think hard about *examples* of the desired functionality
 2. It makes sure that the configurations are correct and that all the objects inside the application talk to each other correctly

For this reason, it pays to apply Child Test to end-to-end tests.  We start every feature by writing on paper some examples (scenarios) of how the feature will work.  Then we translate those scenarios into end-to-end acceptance tests.  When we are satisfied that they fail, and that they fail for the correct reason, then we comment them out.

This style of work is sometimes called "Acceptance Test-Driven Development" or ATDD.  It was popularized by Freeman and Pryce in the book [Growing Object-Oriented Software](#goos).  Below you can see the picture from their book:

{width=100%}
![The ATDD cycle](images/tdd-with-acceptance-tests.png)


When we do TDD we often exercise objects in isolation from each other.  The mocks approach explained in [GOOS](#goos) is particularly good in this respect.  The end-to-end AT helps making sure all the objects that we TDDed in isolation talk to each other correctly. Thus we mitigate the risk of mock-based tests making false assumptions on how the real (non-mocked) collaborators really work.



## Mock objects

Quick, what are mock objects good for?  If you answered "for isolating dependencies" then Bzzzzzzt! You got it wrong!

The real reason why mocks are useful is that they help us developing *protocols*, that is a set of messages that an object must understand in order to fulfill a *role* in an object-oriented system.


<!-- Some objects have responsibilities for *knowing* things.  For instance, a Point object might be responsible for knowing its cartesian coordinates, and it might be a reasonable implementation to give this object "getter" methods so that we can know where in the plane is this Point.

Other objects have responsibilities for *doing* things.  For instance, a Point object could be responsible for knowing how to draw itself on a canvas.

It's easy (if maybe boring) to use TDD to develop the first kind of Point.  But how would you use TDD to define the behaviour of the second kind of test? -->

### Tell, don't ask

An object works by sending and receiving *messages*.  When an object receives a message, it can react by sending messages to other objects.  How do we test an object then?  The simple way is to send a message to an object and then use *getters* to access the object internal state.  One problem with this is that the getters will force us to expose at least part of the object's internal representation.  This will make it harder to change the object.

Not only that.  Using getters will push us to separate data and behaviour.  Consider

    if (point.isPolar()) {
      x = point.getRadius() * Math.cos(point.getAngle());
      y = point.getRadius() * Math.sin(point.getAngle());
    } else {
      x = point.getX();
      y = point.getY();
    }
    canvas.drawPoint(x, y);

We ask the point a question: *are you represented with polar coordinates?* Then depending on the question we do one thing or another.  What we have is that the point is a dumb data structure with no behaviour.  The code that uses the point is doing all the reasoning and has all the behaviour; it converts from polar coordinates to cartesian if necessary, then draws the point on a canvas.

Why do we open up the object and see its internals?  We don't have to!  We could ask the point to give us the cartesian coordinates, doing the conversion internally if necessary.  The above code becomes

    x = point.getX();
    y = point.getY();
    canvas.drawPoint(x, y);

But we can go one step further.  What if the code was

    if (!point.isInvisible()) {
      canvas.drawPoint(point.getX(), point.getY());
    }

Why do we have to ask all this questions to the point object?  Couldn't we just ask it to draw itself unless it's invisible?

    point.drawYourselfUnlessInvisibleOn(canvas);

Now the calling code is much simpler! And it's a lot easier to maintain.  Suppose we add a *color* to the point.  The old calling code would have to change to

    if (!point.isInvisible()) {
      canvas.drawPoint(point.getX(), point.getY(), point.getColor());
    }

but the new calling code does not change much

    point.drawYourselfUnlessInvisibleWithAppropriateColorOn(canvas);

We can increase this isolation by making the name of the message simpler:

    point.drawYourselfOn(canvas)

Here we don't want to know *any* detail: nothing about color, shape, visibility, or anything else.  This isolation makes code **much easier to change**, because changes in the protocol between point and canvas will not impact the callers of either.

This preference for telling objects to do things rather than asking objects to return values was called "Tell, don't ask!" in a famous paper by Andy Hunt and Dave Thomas.


### Mocks

I hope I've convinced you that *Tell, don't ask* is good.  Now you have a problem: how do you test an object that does not have getters?  Ha!

The only way to do test such an object is to *observe its behaviour*.  That is, we send it a message, and observe what other messages it sends to its neighbours.  I send a `drawYourselfOn(canvas)` message to the point, and I want to test that the canvas was used correctly.  How do I test that?  Should I ask the canvas?  But then, again, would require adding *getters* to the canvas.

    // Bleah!  Don't do this!
    for (int x=0; x<=canvas.getMaxX(); x++) {
      for (int y=0; y<=canvas.getMaxY(); y++) {
        if (x == point.getX() && y == point.getY())
          assertEquals(point.getColor(), canvas.getColorAt(x, y))
        else
          assertEquals(WHITE, canvas.getColorAt(x, y))
      }
    }

I'd rather express my test (in pseudocode) this way:

    I expect that
      canvas will receive drawPoint(x, y)
    whenever I do
      point.drawYourselfOn(canvas)

So instead of an *assertion* we have an **expectation**.  We expect that the canvas will be called in a certain way, *and nothing else*.  The nice thing in this test is that *we don't care what's the behaviour of the canvas*.  For all we care, we can just assume that `canvas` is just an interface.  This means that we can develop an object with TDD before its collaborators even exist!

The above test, expressed in JMock, would look like the following:

    Point point = new Point(10, 20);
    Canvas canvas = context.mock(Canvas.class);
    context.checking(new Expectations() {{
      oneOf(canvas).drawPoint(10, 20);
    }});
    point.drawYourselfOn(canvas);

It looks a bit esoteric at first, but it will all make sense.  The details on how JMock works are [in the appendix](#appendix-jmock).  More about mocks in [GOOS].


## Presenter First

Reference ?  Forse Fowler


## Skin & Wrap the API

Reference Working Effectively With Legacy Code

