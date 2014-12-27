package com.tdd4android.helloworld.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloWorldTest {
  @Test
  public void testHelloWorld() throws Exception {
    assertEquals("42", new HelloWorld().message());
  }
}
