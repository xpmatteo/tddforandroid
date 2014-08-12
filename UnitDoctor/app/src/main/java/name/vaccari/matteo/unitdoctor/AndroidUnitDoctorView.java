package name.vaccari.matteo.unitdoctor;

import android.widget.*;

import name.vaccari.matteo.unitdoctor.core.UnitDoctorView;

public class AndroidUnitDoctorView implements UnitDoctorView {
    private TextView inputNumberField;

    public AndroidUnitDoctorView(TextView inputNumberField) {
        this.inputNumberField = inputNumberField;
    }

    @Override
    public double inputNumber() {
        return Double.valueOf(inputNumberField.getText().toString());
    }

    @Override
    public String fromUnit() {
        return null;
    }

    @Override
    public String toUnit() {
        return null;
    }

    @Override
    public void showResult(double result) {

    }

    @Override
    public void showConversionNotSupported() {

    }
}
