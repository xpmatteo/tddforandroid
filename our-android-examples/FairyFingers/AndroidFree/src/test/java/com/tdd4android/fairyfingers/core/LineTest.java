package com.tdd4android.fairyfingers.core;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class LineTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private CoreCanvas coreCanvas = context.mock(CoreCanvas.class);
  private Line line = new Line(10f, 20f);


  @Test
  public void drawNoSegments() throws Exception {
    context.checking(new Expectations() {{
      never(coreCanvas);
    }});

    line.drawOn(coreCanvas);
  }


  @Test
  public void drawOneSegment() throws Exception {
    context.checking(new Expectations() {{
      oneOf(coreCanvas).drawLine(10f, 20f, 30f, 40f, 255);
    }});

    line.addPoint(30f, 40f);
    line.drawOn(coreCanvas);
  }

  @Test
  public void drawMoreSegments() throws Exception {
    context.checking(new Expectations() {{
      oneOf(coreCanvas).drawLine(10f, 20f, 100f, 400f, 255);
      oneOf(coreCanvas).drawLine(100f, 400f, 200f, 500f, 255);
      oneOf(coreCanvas).drawLine(200f, 500f, 300f, 600f, 255);
    }});

    line.addPoint(100f, 400f);
    line.addPoint(200f, 500f);
    line.addPoint(300f, 600f);
    line.drawOn(coreCanvas);
  }

  @Test
  public void testStoreOpacityInSegment() throws Exception {
    line.addPoint(10.0f, 20.0f);

    assertEquals(255, line.getAlpha(0));
  }

  @Test
  public void testDecrementOpacity() throws Exception {
      line.addPoint(10.0f, 20.0f);
      line.decay();

      line.addPoint(20.0f, 20.0f);
      line.decay();

      assertEquals(255 - 50, line.getAlpha(0));
      assertEquals(255 - 25, line.getAlpha(1));
  }
}
