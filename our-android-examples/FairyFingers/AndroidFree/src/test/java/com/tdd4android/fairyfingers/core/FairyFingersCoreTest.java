package com.tdd4android.fairyfingers.core;

import org.junit.*;

import static org.junit.Assert.assertEquals;

public class FairyFingersCoreTest {
  FairyFingersCore app = new FairyFingersCore(null);

  @Test
  public void noLinesAtStart() throws Exception {
    assertEquals(0, app.lines().size());
  }

//  MotionEvent { action=ACTION_DOWN, id[0]=0, x[0]=460.0, y[0]=507.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=70924, downTime=70924, deviceId=0, source=0x1002 }
//  MotionEvent { action=ACTION_MOVE, id[0]=0, x[0]=457.9, y[0]=508.0, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=1, eventTime=71339, downTime=70924, deviceId=0, source=0x1002 }
//  MotionEvent { action=ACTION_MOVE, id[0]=0, x[0]=458.0, y[0]=510.4, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=1, eventTime=71374, downTime=70924, deviceId=0, source=0x1002 }
//  MotionEvent { action=ACTION_UP,   id[0]=0, x[0]=458.0, y[0]=510.4, toolType[0]=TOOL_TYPE_FINGER, buttonState=0, metaState=0, flags=0x0, edgeFlags=0x0, pointerCount=1, historySize=0, eventTime=71570, downTime=70924, deviceId=0, source=0x1002 }

  @Test
  public void oneLine() throws Exception {
    app.touch(FairyFingersCore.ACTION_DOWN, 10.0f, 100.0f);
    app.touch(FairyFingersCore.ACTION_MOVE, 20.9f, 120.0f);
    app.touch(FairyFingersCore.ACTION_MOVE, 30.0f, 130.0f);
    app.touch(FairyFingersCore.ACTION_UP, 30.0f, 130.0f);

    assertEquals(1, app.lines().size());
  }

  @Test
  public void startOneLine() throws Exception {
    app.touch(FairyFingersCore.ACTION_DOWN, 10.0f, 100.0f);

    assertEquals(1, app.lines().size());
    assertEquals("(10.0,100.0)", app.lines(0).toString());
  }

  @Test
  public void startOneLineThenDrag() throws Exception {
    app.touch(FairyFingersCore.ACTION_DOWN, 10.0f, 110.0f);
    app.touch(FairyFingersCore.ACTION_MOVE, 20.0f, 120.0f);
    app.touch(FairyFingersCore.ACTION_MOVE, 30.0f, 130.0f);

    assertEquals("(10.0,110.0)->(20.0,120.0)->(30.0,130.0)", app.lines(0).toString());
  }

  @Test
  public void twoLines() throws Exception {
    app.touch(FairyFingersCore.ACTION_DOWN, 10.0f, 110.0f);
    app.touch(FairyFingersCore.ACTION_MOVE, 20.0f, 120.0f);
    app.touch(FairyFingersCore.ACTION_UP, 20.0f, 120.0f);

    app.touch(FairyFingersCore.ACTION_DOWN, 210.0f, 310.0f);
    app.touch(FairyFingersCore.ACTION_MOVE, 220.0f, 320.0f);
    app.touch(FairyFingersCore.ACTION_UP, 220.0f, 320.0f);

    assertEquals("(10.0,110.0)->(20.0,120.0)", app.lines(0).toString());
    assertEquals("(210.0,310.0)->(220.0,320.0)", app.lines(1).toString());
  }



}
