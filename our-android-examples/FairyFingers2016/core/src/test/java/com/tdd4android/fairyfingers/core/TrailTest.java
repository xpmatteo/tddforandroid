package com.tdd4android.fairyfingers.core;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

public class TrailTest {

  CoreCanvas canvas = mock(CoreCanvas.class);

  @Test
  public void drawNothing() throws Exception {
    Trail trail = new Trail(1f, 2f);

    trail.drawOn(canvas);
  }

  @Test
  public void drawOneSegment() throws Exception {
    Trail trail = new Trail(10f, 20f);
    trail.append(30f, 40f);
    trail.drawOn(canvas);

    verify(canvas).drawLine(10f, 20f, 30f, 40f);
  }

  @Test
  public void drawTwoSegments() throws Exception {
    Trail trail = new Trail(100f, 200f);
    trail.append(300f, 400f);
    trail.append(500f, 600f);
    trail.drawOn(canvas);

    verify(canvas).drawLine(100f, 200f, 300f, 400f);
    verify(canvas).drawLine(300f, 400f, 500f, 600f);
  }

  @After
  public void noMoreInteractions() {
    verifyNoMoreInteractions(canvas);
  }

}
