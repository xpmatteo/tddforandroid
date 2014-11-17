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
  public void testAddNewUnfinishedTrail() throws Exception {
    TrailSet trailSet = new TrailSet();

    trailSet.onFingerDown(10, 20);
    trailSet.onFingerMove(30, 40);

    assertEquals(1, trailSet.size());
  }
}