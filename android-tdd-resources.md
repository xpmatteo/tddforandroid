

## droidcon 2013: Unit tests with Robolectric; Danny Preussler

https://www.youtube.com/watch?v=Tns9zrjQ108
http://de.slideshare.net/dpreussler/robolectric-19062554

## Android Unit and Integration testing

https://github.com/thecodepath/android_guides/wiki/Android-Unit-and-Integration-testing#robotium&sref=https://delicious.com/matteov

+ Mostra un test e2e di codice che crea una nuova Activity

## Test Driven Development and Android Testing

https://www.youtube.com/watch?v=wk8NkHJa8DM

La seconda parte del talk e' molto interessante, da un ingegnere di twitter che la sa lunga.  Parla di Robotium vs Espresso, preferiscono Espresso.


## Responsible design with Android
Book by JB Rainsberger

- builds a nonfuncional useless app
- flow-of-consciousness, lots of winking
- no mention about the problem of dep injection
  (it solves it by using the Activity.onCreate as a Main)
- no e2e test
- builds a pointless feature (cvs export) before closing the loop on the first (entering txs)
+ wrap the android text view in a View object that handles logic
+ presenter first


## Beginning TDD With Android

https://www.youtube.com/watch?v=DdhVZg1XbnY

+ Shows how to setup a test with Robotium
+ Multi-activity app, shows how to take a pic and save the results
- Wastes time setting up test code in the production files to set up the fake camera.
- works all the time on a single, slow, e2e test


