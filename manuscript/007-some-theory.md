
# The Android Development Process

Here we describe in general terms the development process that we propose.  We start with a high-level description, then we follow with a more detailed description of the various parts of the process.  Many of the things that we talk about have been described at length in other books; for instance, TDD or User Stories.  Yet there are many different ways to see these subjects.  In our work experience, it always pays to recap the fundamentals.  For it usually happens that the people in the room have different ideas of what TDD or User Stories are.  So bear with us, so that when we talk of TDD, or other fundamental parts of the process, we are all on the same page.



## At a glance

You have an idea for a great app.  What steps do you take to make it real?  The framework that we describe will orient your actions.

###  Planning

   1. Break the project in user stories
   2. Select the first user story you want to deliver
   3. Write concrete *examples* of how the user story will work.


### Execution

   1. [Optional] Start with a Spike
   2. Create the project
   3. [Optional] Automate an example with an End-to-end Acceptance Test
   4. Implement the example, in TDD, usually using the *presenter-first* technique
   5. Connect the presenter with the view
   6. Test the example manually on the device
   7. If you have an end-to-end test, check that it works.
   8. Choose the next example and go to 4.

This is a rough outline.  It will rarely be this linear in practice; for instance it will happen that when you test the application manually, you will notice things that you want to change.  You will add them to your todo-list.

The loop between steps 4 and 8 should be executed in minutes, not hours... don't spend too much time without checking that your code works on the device.  Conversely, don't rely only on the device for getting feedback on your code.


## Break down a project in User Stories

Do we need a plan?  Yes we do.  Without a plan, we risk to lose *focus*.  The app we have in mind will need many features and details.  If we work on more than one thing at once, we disperse our time and energy.  So the main reason for planning is that we want to focus on a single thing at a time.  Focus and prioritize:

 1. Make a list of the things you want to do
 2. Choose the first thing you want to do
 3. Do that thing and nothing else until it's done.

This is the Agile way to work.  And one more thing: make it so that the things you work on are so *small* that you can build them in hours.  This way you will produce a steady stream of small successes.

So, how do we produce a list of things to do?  We write user stories.

A user story is simply a brief description of something that a user can do with the application.  A user story has:

 1. A Title
 2. [Optional] a Description
 3. [Eventually] an Acceptance Criterion

When you start thinking about the user stories for a project, you will probably start with just a bunch of titles.  Some stories can be further clarified with a brief description.  Before you implement them, you will need to clarify the Acceptance Criterion.  An Acceptance Criterion can be further clarified with Examples.

### Example: a Todo-List application

If I wanted to implement a Todo-List app, I would probably want the following user stories:

 * Create new lists
 * Show the items of a list
 * Add an item to a list
 * Mark an item as done


This is a good set of stories to start.  As we keep thinking, many more stories will come up, related to renaming lists and items, deleting, sharing, notification, etc.

X> Exercise: write the list of user stories for an app that reads blog feeds.


### Rules for user stories

 * Demoable: when the story is done, there should be something *visible* to prove that it's done.
 * Small: it should be possible to implement a story in hours, or at most in a day or two.  Break down big stories in small ones.
 * Independent: it's usually possible to build user stories in any order.  For instance: you can demo the "Show the items of a list" user story before the "Add an item to a list" story: just show a few canned items that you instantiate inside the application code.


## Concrete examples

An Acceptance Criterion can be a general statement of what the application should do.  For instance, the AC for the user story "Hide items when done" could read like

    When an item is marked as 'done' it will be hidden after one week.

To make sure that we understand correctly what this means, we translate it to concrete Examples.  Usually we do this through a conversation with our customer.

    Given Item "Buy milk" that was marked "done" on 1st March 2015 at 14:00:00

    When the current date and time is 8th March 2015 at 13:59:59
    Then the item is shown

    When the current date and time is 8th March 2015 at 14:00:00
    Then the item is not shown

As you can see, where the AC talks in general terms "An item marked as *done*", the examples are about concrete things: a specific item "Buy Milk" that was marked done in some specific instant in time.

Writing the examples is an useful thing, because

 1. They remove ambiguity: what does "after one week" means exactly?  Now we know: it's not just the calendar day, it's also the time of day that matters.
 2. The examples become tests: we can execute them both manually (for instance, by artificially changing the clock of the device) or automatically.

Another name for Examples is Scenarios.

The concept of Example/Scenario is crucial, because it drives development.  A scenario is the smallest unit of functionality that can be delivered.  The



## Start with a Spike

What is a spike? A *spike* is an experiment that you do in order to explore how to do a feature.  A spike will usually not have tests, will be quick and dirty, will not be complete, will not follow our usual rules for good quality.  It's just an exploration.

Spikes are important because we do often have to work with APIs that we don't know well.  This is particularly true in the Android environment, where we have to deal with complex, rich components whose behaviour is not clear until we start playing with them.

Important point: don't try to write TDD with APIs that you don't know well.  There is the risk of wasting a lot of time writing ineffective tests.

The rules for spikes are:

  2. Goal: you have a learning goal for the spike
  2. Timebox: you set yourself a time limit; for instance, two hours.
  1. New project: start the spike in a new project (not by hacking into your production code)
  3. Throw away: after you're done, you *throw away* the spike code.  You may keep the spike around as a *junkyard* of bits to copy from; but you never turn the spike into your production project.  Start production code with a fresh project.





## How TDD works

TBD



## Acceptance-TDD (ATDD)

One pattern that is mentioned in the original [TDD book by Kent Beck](#tdd) is "Child Test".  It means that when it takes me too long to get a test to pass, it probably means that the test represents too big a step for me.  In that case, Beck suggests that you comment out the problematic test, and start writing a simpler test, a "child test".  The child test lets me take a smaller step in the direction of making the larger test pass.  After a while, and probably after several TDD cycles, we are ready to make the big test pass.

One instance of the Child Test pattern is when we write an end-to-end acceptance test as a first step in getting a new feature to work.  That test is likely to take more than a few minutes to pass.  In Android, an end-to-end test will also take minutes to run.  Yet, the end-to-end test is very useful.

 1. It makes me think hard about *examples* of the desired functionality
 2. It makes sure that the configurations are correct and that all the objects inside the application talk to each other correctly

For this reason, it pays to apply Child Test to end-to-end tests.  We start every feature by writing on paper some examples (scenarios) of how the feature will work.  Then we translate those scenarios into end-to-end acceptance tests.  When we are satisfied that they fail, and that they fail for the correct reason, then we comment them out.

This style of work is sometimes called "Acceptance Test-Driven Development" or ATDD.  It was popularized by Freeman and Pryce in the book [Growing Object-Oriented Software](#goos).  Below you can see the picture from their book:

{width=100%}
![The ATDD cycle; image cc-by-sa courtesy of Freeman and Pryce](images/tdd-with-acceptance-tests.png)


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

This preference for telling objects to do things rather than asking objects to return values was called "Tell, don't ask!" in a famous paper by Andy Hunt and Dave Thomas (TBD - reference).


### Mocks

I hope I've convinced you that *Tell, don't ask* is good.  Now you have a problem: how do you test an object that does not have getters?  Ha!

The only way to do test such an object is to *observe its behaviour*.  That is, we send it a message, and observe what other messages it sends to its neighbours.  I send a `drawYourselfOn(canvas)` message to the point, and I want to test that the canvas was used correctly.  How could I test that?  Should I ask the canvas?  That would require adding *getters* to the canvas.

    // Bleah!  Don't do this!
    for (int x=0; x<=canvas.getMaxX(); x++) {
      for (int y=0; y<=canvas.getMaxY(); y++) {
        if (x == point.getX() && y == point.getY())
          assertEquals(point.getColor(), canvas.getColorAt(x, y));
        else
          assertEquals(WHITE, canvas.getColorAt(x, y));
      }
    }

I'd rather express my test (in pseudocode) this way:

{lang=plain}
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

The point of this test is that we can use it to define how the point should talk to its collaborator.

The syntax looks a bit esoteric at first, but it will all make sense.  The details on how JMock works are [in the appendix](#appendix-jmock).  More about mocks in [GOOS].


## Presenter First

Reference ?  Forse Fowler


## Skin & Wrap the API

TBD Reference Working Effectively With Legacy Code

