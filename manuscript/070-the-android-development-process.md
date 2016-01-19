
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

> Given Item "Buy milk" that was marked "done" on 1st March 2015 at 14:00:00
>
> **Example: item not yet hidden** \\
>    When the current date and time is 8th March 2015 at 13:59:59 \\
>    Then the item is shown
>
> **Example: item hidden** \\
>    When the current date and time is 8th March 2015 at 14:00:00 \\
>    Then the item is NOT shown

As you can see, where the AC talks in general terms "An item marked as *done*", the examples are about concrete things: a specific item "Buy Milk" that was marked done in some specific instant in time.

Writing the examples is an useful thing, because

 1. They remove ambiguity: what does "after one week" means exactly?  Now we know: it's not just the calendar day, it's also the time of day that matters.
 2. The examples become tests: we can execute them both manually (for instance, by artificially changing the clock of the device) or automatically.

Another name for Examples is Scenarios.

The concept of Example/Scenario is crucial, because it drives development.  A scenario is the smallest unit of functionality that can be delivered.



## Start with a Spike

What is a spike? A *spike* is an experiment that you do in order to explore how to do a feature.  A spike will usually not have tests, will be quick and dirty, will not be complete, will not follow our usual rules for good quality.  It's just an exploration.

Spikes are important because we do often have to work with APIs that we don't know well.  This is particularly true in the Android environment, where we have to deal with complex, rich components whose behaviour is not clear until we start playing with them.

Important point: don't try to write TDD with APIs that you don't know well.  There is the risk of wasting a lot of time writing ineffective tests.

So, whenever we start working with a bit of Android APIs that we don't know well, it pays to start with a little experiment.

The rules for spikes are:

  2. Goal: you have a learning goal for the spike
  2. Timebox: you set yourself a time limit; for instance, two hours.
  1. New project: start the spike in a new project (not by hacking into your production code)
  3. Throw away: after you're done, you *throw away* the spike code.  You may keep the spike around as a *junkyard* of bits to copy from; but you never turn the spike into your production project.  Start production code with a fresh project.



## Test-Driven Development


TDD is about writing code in *small increments*, driven by an *automated test*, keeping the code *as simple as possible* at all times.  This process has a dual nature: it is a way to *design* programs, and also is a way to build a good *suite of tests*.

The process was described first by Kent Beck in [Test-Driven Development: By Example](#tdd).  It's probably still the best book on the subject.  It's a subtle book: it seems like it's glossing over many things, but when you go back to it and read it again, you find it has new answers to your questions.


### The micro-cycle

The process is easy to describe.  Kent Beck in [Test-Driven Development: By Example](#tdd) writes the following sequence:

  1. Quickly write a test
  2. Run all the tests, see the new one fail
  3. Make a small change
  4. Run all the tests, see them all succeed
  5. Refactor to remove duplication

Note the emphasis on "small": the cycle is meant to be repeated every few minutes, not hours.  The point of TDD is to provide quick feedback on the quality of your code.

If you've never seen an accomplished TDD practitioner at work, it's difficult to grasp how the TDD cycle should be done.  We suggest you to watch good TDD videos, like the [TDD Videos by Kent Beck](#beck-video)




## Skin & Wrap the API

TBD Reference Working Effectively With Legacy Code

