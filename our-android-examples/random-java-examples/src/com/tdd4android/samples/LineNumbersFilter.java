package com.tdd4android.samples;

import java.io.*;

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
      BufferedReader reader =
    		  new BufferedReader(new InputStreamReader(in));
      while ((line = reader.readLine()) != null) {
        count++;
        out.println(String.format("%d %s", count, line));
      }
    }

    public static void main(String ... args) throws IOException {
      LineNumbersFilter filter =
    		  new LineNumbersFilter(System.in, System.out);
      filter.apply();
    }
  }
