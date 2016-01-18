package com.tdd4android.counter3;


import com.tdd4android.counter2.*;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class JMockCounterAppTest {

  // This is the JMock machinery that we need
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  @Test
  public void increment() throws Exception {
    // We ask JMock to make a mock of the CounterGui interface
    final CounterGui gui = context.mock(CounterGui.class);

    // We create the app, passing the gui as a collaborator
    Counter counter = new Counter();
    CounterApp app = new CounterApp(counter, gui);

    // We setup our expectations
    context.checking(new Expectations() {{
      // Exactly one time, gui will be called with display(1)
      oneOf(gui).display(1);
    }});

    // Whenever we call
    app.increment();
  }
}