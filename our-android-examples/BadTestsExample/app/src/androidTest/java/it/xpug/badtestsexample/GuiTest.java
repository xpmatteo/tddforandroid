package it.xpug.badtestsexample;

import android.test.ActivityInstrumentationTestCase2;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class GuiTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public GuiTest() {
        super(MainActivity.class);
    }

    public void testMessageGravity() throws Exception {
        TextView myMessage = (TextView) getActivity().findViewById(R.id.myMessage);
        assertEquals(Gravity.CENTER, myMessage.getGravity());
    }
}
