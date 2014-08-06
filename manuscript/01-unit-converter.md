# Example: Unit Converter

## Problem description

We want to write an app that converts from various units to other units.  We imagine that it will support an extensive collection of conversions, from cm to yards, from Fahrenheit to Celsius, from HP to KW.

Yes, I know this feature is already implemented by Google on all phones. I use this example because it will allow me to illustrate some important techniques.  It's not too complicated and not too trivial.



## Examples

The feature we want to implement is "convert a number from one unit to another".  To clarify what we want to do to ourselves and our customer, it's a good idea to write down a few examples (a.k.a. scenarios) of how the feature will work.

{icon=fa-cube}
G> ## Example: inches to cm {linebreak]
G>
G> Given the user selected "in" to "cm" {linebreak]
G> When the user types 2 {linebreak]
G> Then the result is "2.00 in = 5.08 cm" {linebreak]

G> ## Example: Fahrenheit to Celsius {linebreak]
G>
G> Given the user selected "F" to "C" {linebreak]
G> When the user types 50 {linebreak]
G> Then the result is "50.00 F = 10.00 C" {linebreak]

G> ## Example: unsupported units {linebreak]
G>
G> Given the user selected "ABC" to "XYZ" {linebreak]
G> Then the result is "I don't know how to convert this" {linebreak]

Note that by writing down the examples we clarified what exactly the customer expects to see: how numbers are formatted, what the result message should look like.

See further: BDD


## Start with a spike

When you are using APIs you're not familiar with, it's better to do a *spike* before you start doing real TDD.   A *spike* is an experiment that you do in order to explore how to do a feature.  A spike will usually not have tests, will be quick and dirty, will not be complete, will not follow our usual rules for good quality.  It's just an exploration.  The rules for spikes are

 1. New project: start the spike in a new project (not by hacking into your production code)
 2. Timebox: set yourself a time limit, for instance two hours.
 3. Throw away: after you're done, you *throw away* the spike code.  You may keep the spike around as a *junkyard* of bits to copy from; but you never turn the spike into your production project.  Start production code with a fresh project.

Our spike implements the "inches to cm" and the "unsupported units" scenario.  It took me 3 pomodori to build.

{width=60%}
![How the unit conversion spike looks like](images/spike-units-screenshot.png)

<<[The activity for the unit conversion spike](../UnitConversionSpike/app/src/main/java/name/vaccari/matteo/unitconversionspike/MyActivity.java)

{lang="xml"}
<<[The layout for the unit conversion spike](../UnitConversionSpike/app/src/main/res/layout/activity_my.xml)

What I learned:

 * How to change the result at every keypress
 * How to use a `RelativeLayout`


## Continue with an end-to-end acceptance test

The first step after the optional spike is to write an end-to-end acceptance test.  The rules for an acceptance tests are

 1. Business talk: write at the same level of abstraction as the examples that were discussed with the customer.
 2. Ignore them: when the ATs pass, then the feature is complete.  Therefore it will take some time before all the ATs pass.  We will ignore all the non-passing ATs until they pass.  (Ignore means that we comment them out, or we use some other trick to temporarily remove them from the build.)
 3. Pass once, pass forever.  When an AT passes, it means that some bit of functionality is present in the system.  From this moment onward, the AT must stay green forever.  The AT now works as a non-regression test.

### Workflow

#### Step 0: new project

We create a new project (remember, we don't want to "evolve" the spike!).  I use Android Studio and I let it set up the new project with its wizard.  I invent a fancy name and I call it "Unit Doctor".

{width=60%}
![The new project wizard](images/unitdoctor-project-setup-wizard.png)


#### Step 1: write the test *as it should read*

Android Studio set up a source folder named `src/androidTest`.  I create a new Java class there.

{linenos=on}
~~~~~~~~~~~~~~~~~~~~~~~~~
package name.vaccari.matteo.unitdoctor;

import android.test.ActivityInstrumentationTestCase2;

public class UnitConversionAcceptanceTest
    extends ActivityInstrumentationTestCase2<MainActivity> {

  public UnitConversionAcceptanceTest() {
    super(MainActivity.class);
  }

  public void testInchesToCentimeters() throws Exception {
    givenTheUserSelectedConversion("in", "cm");
    whenTheUserEnters("2");
    thenTheResultIs("2.00 in = 5.08 cm");
  }
}
~~~~~~~~~~~~~~~~~~~~~~~~~

The test is written in the same language as the original example, by imagining that we have implemented the three methods at lines 13-15.

#### Step 2: implement the helper methods

We assume at first that the UI is similar to what we developed in the spike.  So our first implementation of the testing language is quite simple.

~~~~~~~~~~~~~~~
public void testInchesToCentimeters() throws Exception {
  givenTheUserSelectedConversion("in", "cm");
  whenTheUserEnters("2");
  thenTheResultIs("2.00 in = 5.08 cm");
}

private void givenTheUserSelectedConversion(String fromUnit, String toUnit) {
  getField(R.id.fromUnit).setText(fromUnit);
  getField(R.id.toUnit).setText(toUnit);
}

private void whenTheUserEnters(String inputNumber) {
  getField(R.id.inputNumber).setText(inputNumber);
}

private void thenTheResultIs(String expectedResult) {
  assertEquals(expectedResult, getField(R.id.result).getText());
}

private TextView getField(int id) {
  return (TextView) getActivity().findViewById(id);
}
~~~~~~~~~~~~~~~

The test does not compile yet, because the identifiers like `R.id.fromUnit` etc. are not yet defined.

#### Step 3: implement other scenarios

We implement a few other significant scenarios.  This way we check that the testing language that we are developing is expressive enough.

~~~~~~~~~~~~~~~~
public void testFahrenheitToCelsius() throws Exception {
    givenTheUserSelectedConversion("F", "C");
    whenTheUserEnters("50");
    thenTheResultIs("50.00 F = 10.00 C");
}

public void testUnknownUnits() throws Exception {
    givenTheUserSelectedConversion("ABC", "XYZ");
    thenTheResultIs("I don't know how to convert this");
}
~~~~~~~~~~~~~~~~


#### Step 4: implement just enough layout so that we can see the test fail

We turn to editing the layout file so that we can fix all the IDs.  This time I make a small effort at creating a tidy UI.

![The Android Studio preview of the UI](images/unitdoctor-layout-first-version.png)

Now we can run the tests and see them all fail

![The first run of the ATs unexpectedly produces errors, not failures](images/unitdoctor-at-failing.png)

But are they failing for the expected reason?

{lang="text"}
    android.view.ViewRootImpl$CalledFromWrongThreadException:
    Only the original thread that created a view hierarchy can touch its views.
    at android.view.ViewRootImpl.checkThread(ViewRootImpl.java:4607)
    ...
    at name.vaccari.matteo.unitdoctor.UnitConversionAcceptanceTest.givenTheUserSelectedConversion(UnitConversionAcceptanceTest.java:30)
    at name.vaccari.matteo.unitdoctor.UnitConversionAcceptanceTest.testInchesToCentimeters(UnitConversionAcceptanceTest.java:13)
    ...

The error message is "Only the original thread that created a view hierarchy can touch its views."  So it fails because we can only touch an element of the UI, such as when we call `setText()` on the text fields, using the main thread of the application.  The easiest way to solve the problem, for now, is to annotate the tests with `@UiThreadTest`.  We do so, and now we check that the error message is what we expect:

{lang="text"}
    junit.framework.AssertionFailedError:
    expected:<50.00 F = 10.00 C> but was:<Result goes here>

We also observe that Android Studio colors the tests differently, to tell us that the tests produce failures, not errors.

![Now the ATs produce failures as expected](images/unitdoctor-at-failing-for-the-right-reason.png)

T> Tip: Always check the error message, to make sure that the tests are failing for the right reason.

Q> What is the difference between a *failure* and an *error*?  A "failure" is when your tests fails because of a broken assertion.  An "error" is when the test fails because of an exception.  In general, we want our test to produce failures, not errors, because errors mean that our software is doing something  unexpected.


## Shift to TDD

Now that we have some failing acceptance tests, we leave them be.  We now shift to a faster gear by starting to write some *unit* tests.  
