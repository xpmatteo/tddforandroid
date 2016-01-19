# Writing Android-free code: Dependency Inversion

We want to be able to keep our core logic independent of the Android libraries.  That is pretty easy when the Android framework is calling our logic.  We move from

~~~~~~~~
// Android-dependent code
class MainActivity extends Activity {
  void onSomethingHappened(someAndroidStructure) {
    // our logic ...
  }
}
~~~~~~~~

to

~~~~~~~~
// Android-dependent code
class MainActivity extends Activity {
  void onSomethingHappened(someAndroidStructure) {
    OurObject o = new OurObject();
    o.onSomethingHappened(someAndroidStructure.getThis(), someAndroidStructure.getThat());
  }
}

// Android-free code
class OurObject {
  void onSomethingHappened(String something, Integer somethingElse) {
    // our logic...
  }
}
~~~~~~~~

All we have to do is to extract the relevant information from the Android event data and pass it to an object that is defined inside an Android-free library.  If the data that we need is more than a couple of items, then we are probably better off creating our own Android-free data structure that contains a subset of the Android event data.  It's not too difficult, because the flow of control at run-time goes in the same direction as the compile-time dependencies.

~~~~~~~~~
                            Control flow
                            ------------>

                 provides                 used by
Android code   ----------->   Android   ---------> our logic
                            event data


                            Control flow
                            ------------>

                 provides                 copied to              used by
Android code   ----------->   Android   -----------> our event  ---------> our logic
                            event data                 data
~~~~~~~~~



It is a bit more difficult when the flow of control goes in the other direction: that is, when our logic needs to do something with the device; for instance, when we have to show something on the screen in response to some event.  In this case we can apply the Dependency Inversion trick.

~~~~~~~~~
                    Control flow
                    <------------
                compile dependency
Android code  <--------------------- our logic
~~~~~~~~~

We want to separate our logic from the Android code so that the compile-time dependency goes in the opposite direction to the flow of control.  So we introduce an interface that separates our logic from the Android code.

~~~~~~~~~
                    Control flow
                    <------------
              implements                    calls
Android code ------------|> an interface  <-------- our logic
~~~~~~~~~

The flow of control has not changed, but the direction of source code dependency has been inverted.  The interface is implemented in an Android class, such as an Activity or a View, but the interface itself is part of the Android-free world.  We will use this principle in the following chapters.


For more information on Dependency Inversion, I recommend Robert Martin's video: [The Dependency Inversion Principle](https://cleancoders.com/episode/clean-code-episode-13/show).

An idea related to Dependency Inversion is the Ports and Adapters architecture, also known as Hexagonal architecture.  The Birthday Greetings Kata is an exercise meant to practice the Ports and Adapters architecture.


