package name.vaccari.matteo.unitdoctor;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class UnitConversionAcceptanceTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public UnitConversionAcceptanceTest() {
        super(MainActivity.class);
    }

    public void testInchesToCentimeters() throws Exception {
        givenTheUserSelectedConversion("in", "cm");
        whenTheUserEnters("2");
        thenTheResultIs("2 in = 5.08 cm");
    }

    public void testFahrenheitToCelsius() throws Exception {
        givenTheUserSelectedConversion("F", "C");
        whenTheUserEnters("50");
        thenTheResultIs("50.00 F = 10.00 C");
    }

    public void testUnknownUnits() throws Exception {
        givenTheUserSelectedConversion("ABC", "XYZ");
        thenTheResultIs("I don't know how to convert this");
    }

    private void givenTheUserSelectedConversion(String fromUnit, String toUnit) {
        getField(R.id.fromUnit).setText(fromUnit);
        getField(R.id.toUnit).setText(toUnit);
    }

    private void whenTheUserEnters(String inputNumber) {
        getField(R.id.inputNumber).setText(inputNumber);
    }

    private void thenTheResultIs(String expectedResult) {
        assertEquals(expectedResult, getField(R.id.result));
    }

    private TextView getField(int id) {
        return (TextView) getActivity().findViewById(id);
    }

}