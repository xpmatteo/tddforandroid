
# Why doing TDD with Android is challenging



## Android problem #1: the OS does the instantiating

The usual way to write a unit test for a Java object is to

 1. create the object, bringing it to a desired state
 2. perform an action on it
 3. check the results

For instance, suppose we wanted to test a simple "dictionary" object.  We could do it by means of this test:

    // 1. create the object and bring it to a desired state
    Dictionary dictionary = new Dictionary();
    dictionary.define("cat", "gatto");
    dictionary.define("dog", "cane");

    // 2. perform an action
    String translation = dictionary.translationFor("cat");

    // 3. check the results
    assertEquals("gatto", translation);

The problem for Android programmers is that in Android, all of the important objects: the Activity, the Views, the Services, the Content providers, are instantiated by the system.  This makes it very difficult to do a unit test on such objects, because we cannot even instantiate the objects; we must get the OS to instantiate them for us.

The solution: don't try to unit test them.  Regard them as a kind of "main" program.

### A non-Android example

Let's illustrate the principle with an example.  You have a simple Java program that reads a text file from standard input, and outputs the same file with line numbers in front.  The program, being so simple, can be written as a single "main" program, like this:

    public class LineNumbersFilter {
      public static void main(String ... args) throws IOException {
        String line;
        int count = 0;
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
        while ((line = reader.readLine()) != null) {
          count++;
          System.out.println(String.format("%d %s", count, line));
        }
      }
    }

It's extremely difficult to unit test this program.  The difficulty lies in the fact that the program accesses `System.in` and `System.out` directly.  It's difficult to set up the contents of System.in within a unit test, and it's difficult to check the contents of System.out in a unit test.  You might try, but it's going to be messy, and very *expensive* in terms of your time.

On the other hand, if you break down the program in two parts, the "main" part and the "logic" part, you will find that the logic can be unit tested easily.

    class LineNumbersFilter {
      private InputStream in;
      private PrintStream out;
      public LineNumbersFilter(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
      }
      public void apply() throws IOException {
        String line;
        int count = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        while ((line = reader.readLine()) != null) {
          count++;
          out.println(String.format("%d %s", count, line));
        }
      }
      // The "logic" part is above

      // The "main"  part is below
      public static void main(String ... args) throws IOException {
        LineNumbersFilter filter = new LineNumbersFilter(System.in, System.out);
        filter.apply();
      }
    }

Now we can set up the LineNumbersFilter with in-memory streams, that can be easily constructed and checked within a unit test.

    @Test
    public void oneLineOfInput() throws Exception {
      ByteArrayInputStream inputStream =
            new ByteArrayInputStream("one line".getBytes());
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream printStream = new PrintStream(outputStream);

      new LineNumbersFilter(inputStream, printStream).apply();

      assertEquals("1 one line\n", outputStream.toString());
    }

You may object: "you are saying that there is no way to test main, and we risk letting defects seep into our main".  We have two answers for this.

The first answer is that if we properly separate logic from creation, there will be no "IFs" in the main.  So it will be easy to check by hand that it works; a single manual test will do it.  In other words: if there is an error in the main, it will be readily apparent as soon as we try to run it.

The second answer is that it's actually quite easy to do an **end-to-end** test of the main.  The Bash script below does it quite nicely.

{lang="bash"}
    cat > /tmp/expected.txt <<EOF
    1 first line
    2 second line
    EOF

    java LineNumbersFilter > /tmp/actual.txt <<EOF
    first line
    second line
    EOF

    if diff /tmp/expected.txt /tmp/actual.txt > /tmp/difference.txt
    then
      echo "ok"
    else
      echo "ERROR:"; cat /tmp/difference.txt
    fi

This, however, is not a unit test.  It does not just test "main"; it tests main and everything else.  It can be a very good thing to write such an end-to-end test.  However, such tests usually take a long time to write, and it usually takes a long time to get them to pass.  For this reason, although they can be very useful in general, they are not very useful for TDD.


### What's the consequence for Android programmers

In Android, we can't just instantiate Activities or Views.  We must let the system instantiate them for us.  Therefore it's not practical to unit-test them.  So we don't!  We treat the Activity, View, Service or Content Provider as **our "main"**.  We take care to move all the logic away from them, into objects that we can create as we please.


## Android problem #2: all tests run on the device

The second big problem is that all the tooling that goes with Android assumes that the JUnit tests will run *inside the device*.  We cannot even assert that 3+4 is 7 without downloading the test code to the device and executing it there.

But the problem is even worse than that.  The "unit testing" toolkit that Google provides is a mess.  It's poorly documented: the examples were written for who knows which old version of the OS and they don't always work.  Some kind of tests work reasonably well; in our experience, tests for simple forms with input and output text fields tend to work.  Other things are absolutely difficult to test; if you want a challenge, try writing a test that starts more than one activity.

Even if you get the test to work at all, it's going to be very slow to run.  It will take about 30 seconds to download the code to the device.  This is too much.

Additional toolkits of various kinds, such as Robolectric, Espresso or Robotium try to ameliorate the situation, but fail.  They are all *extremely* difficult to setup.  The official instructions are very long to start with and they are out-of-date most of the time. Then you must follow a trail of blog posts hoping to find up-to date information or hacks.  These tools are a major time sink and we recommend that TDDers avoid them.  They might be useful for *testers*, but they are not so useful for *programmers*.

Our solution is to avoid running unit tests in the device.  Move all the logic from the android-dependent module of your project to a new, android-independent module that contains pure Java logic.  This the same "no logic in main" technique we saw in the previous section.

Our job is to build solutions for our customers in reasonable time.  We cannot afford to spend major time nursing the test environments.  This would be *pure waste*.  Not only that; these tools push us to work at the lowest level of abstraction, that is, the level of the Android API.  A much more productive use of our time is to reason on how to separate the logic of our application from the Android programming environment.  This will push us to find a more abstract level of APIs that will be more productive to us.

We still recommend to use tools like Monkey Runner or Robotium for end-to-end testing.  Just don't use them for TDD.







