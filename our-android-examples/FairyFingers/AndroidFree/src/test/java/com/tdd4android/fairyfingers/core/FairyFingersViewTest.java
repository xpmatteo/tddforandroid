package com.tdd4android.fairyfingers.core;

import com.tdd4android.fairyfingers.core.*;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.*;

public class FairyFingersViewTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  PathStorage pathStorage = context.mock(PathStorage.class);
  private FairyFingersApp app = new FairyFingersApp(pathStorage);


  @Test
  public void startNewLine() throws Exception {
    final OurPath newPath = context.mock(OurPath.class);
    context.checking(new Expectations() {{
      oneOf(pathStorage).getPath(11111);
      will(returnValue(newPath));
      oneOf(newPath).drag(10, 20);
    }});

    app.touch(10, 20, 11111);
  }

  @Test
  public void twoLines() throws Exception {
    final OurPath path1 = context.mock(OurPath.class, "1");
    final OurPath path2 = context.mock(OurPath.class, "2");
    context.checking(new Expectations() {{
      allowing(pathStorage).getPath(111);
      will(returnValue(path1));
      allowing(pathStorage).getPath(222);
      will(returnValue(path2));
      oneOf(path1).drag(10, 20);
      oneOf(path2).drag(30, 40);
    }});

    app.touch(10, 20, 111);
    app.touch(30, 40, 222);
  }

  @Test
  public void whatToDoOnDraw() throws Exception {


  }
}
