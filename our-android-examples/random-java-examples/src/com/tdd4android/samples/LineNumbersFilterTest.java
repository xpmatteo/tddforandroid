package com.tdd4android.samples;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;

public class LineNumbersFilterTest {

    @Test
    public void oneLineOfInput() throws Exception {
  		ByteArrayInputStream inputStream = new ByteArrayInputStream("one line".getBytes());
  		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  		PrintStream printStream = new PrintStream(outputStream);

  		LineNumbersFilter filter = new LineNumbersFilter(inputStream, printStream);
  		filter.apply();

  		assertEquals("1 one line\n", outputStream.toString());
    }

}
