package name.vaccari.matteo.unitdoctor;

import android.test.AndroidTestCase;
import android.widget.EditText;

public class AndroidUnitDoctorViewTest extends AndroidTestCase {
    public void testReturnInputValues() throws Exception {
        EditText inputNumberField = new EditText(getContext());
        inputNumberField.setText("3.14159");
        AndroidUnitDoctorView view = new AndroidUnitDoctorView(inputNumberField);

        assertEquals(3.14159, view.inputNumber());
    }
}
