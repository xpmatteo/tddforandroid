package com.tdd4android.fairyfingers.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrailSetTest {

  @Test
  public void emptyTrailSet() throws Exception {
    TrailSet trailSet = new TrailSet();
    assertEquals(0, trailSet.size());
  }

  @Test
  public void unfinishedTrail() throws Exception {
    TrailSet trailSet = new TrailSet();

    trailSet.onFingerDown(10, 20);
    trailSet.onFingerMove(30, 40);

    assertEquals(1, trailSet.size());
    assertEquals("(10,20)->(30,40)", trailSet.get(0).toString());
  }

  @Test
  public void twoPointsTrail() throws Exception {
    TrailSet trailSet = new TrailSet();

    trailSet.onFingerDown(10, 20);
    trailSet.onFingerMove(30, 40);
    trailSet.onFingerUp();

    assertEquals(1, trailSet.size());
    assertEquals("(10,20)->(30,40)", trailSet.get(0).toString());
  }

}