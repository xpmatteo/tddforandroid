package com.tdd4android.fairyfingers.core;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;

public class LineTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private Drawable drawable = context.mock(Drawable.class);
  private Line line = new Line(10f, 20f);


  @Test
  public void drawNoSegments() throws Exception {
    context.checking(new Expectations() {{
      never(drawable);
    }});

    line.drawOn(drawable);
  }


  @Test
  public void drawOneSegment() throws Exception {
    context.checking(new Expectations() {{
      oneOf(drawable).drawLine(10f, 20f, 30f, 40f);
    }});

    line.addPoint(30f, 40f);
    line.drawOn(drawable);
  }

  @Test
  public void drawMoreSegments() throws Exception {
    context.checking(new Expectations() {{
      oneOf(drawable).drawLine(10f, 20f, 100f, 400f);
      oneOf(drawable).drawLine(100f, 400f, 200f, 500f);
      oneOf(drawable).drawLine(200f, 500f, 300f, 600f);
    }});

    line.addPoint(100f, 400f);
    line.addPoint(200f, 500f);
    line.addPoint(300f, 600f);
    line.drawOn(drawable);
  }

}
