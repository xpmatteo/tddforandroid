package name.vaccari.matteo.unitdoctor;

import android.test.AndroidTestCase;
import android.widget.*;

public class UnitDoctorViewTest extends AndroidTestCase {
    EditText fromUnit = new EditText(getContext());
    EditText toUnit = new EditText(getContext());
    EditText inputNumber = new EditText(getContext());
    TextView output = new EditText(getContext());

    public void testExtractValuesFromFields() throws Exception {
        fromUnit.setText("X");
        toUnit.setText("Y");
        inputNumber.setText("123.23");
        AndroidUnitDoctorView unitDoctorView = new AndroidUnitDoctorView();
        assertEquals("X", unitDoctorView.getFromUnit());

    }
}
