# Some techniques


## How TDD works


## Acceptance-TDD (ATDD)

One pattern that is mentioned in the original TDD book by Kent Beck is "Child Test".  It means that when it takes me too long to get a test to pass, it probably means that the test represents too big a step for me.  In that case, Beck suggests that you comment out the problematic test, and start writing a simpler test, a "child test".  The child test lets me take a smaller step in the direction of making the larger test pass.  After a while, and probably after several TDD cycles, we are ready to make the big test pass.

One instance of the Child Test pattern is when we write an end-to-end acceptance test as a first step in getting a new feature to work.  That test is likely to take more than a few minutes to pass.  In Android, an end-to-end test will also take minutes to run.  Yet, the end-to-end test is very useful.

 1. It makes me think hard about *examples* of the desired functionality
 2. It makes sure that the configurations are correct and that all the objects inside the application talk to each other correctly

For this reason, it pays to apply Child Test to end-to-end tests.  We start every feature by writing on paper some examples (scenarios) of how the feature will work.  Then we translate those scenarios into end-to-end acceptance tests.  When we are satisfied that they fail, and that they fail for the correct reason, then we comment them out.

This style of work is sometimes called "Acceptance Test-Driven Development" or ATDD.  It was popularized by Freeman and Pryce in the book Growing Object-Oriented Software [GOOS](#goos).  Below you can see the picture from their book.

(figura da Freeman & Pryce)
{width=100%}
![The ATDD cycle](images/tdd-with-acceptance-tests.png)


When we do TDD we often exercise objects in isolation from each other.  The mocks approach explained in [GOOS](#goos) is particularly good in this respect.  The end-to-end AT helps making sure all the objects that we TDDed in isolation talk to each other correctly. Thus we mitigate the risk of mock-based tests making false assumptions on how the real (non-mocked) collaborators really work.



## Mock objects

Quick, what are mock objects good for?  If you answered "for isolating dependencies" then Bzzzzzzt! You got it wrong!

The real reason why mocks are useful is that they help us developing *protocols*, that is a set of messages that an object must understand in order to fulfill a *role* in an object-oriented system.


## Presenter First

Reference ?  Forse Fowler


## Skin & Wrap the API

Reference Working Effectively With Legacy Code

