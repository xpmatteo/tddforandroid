package name.vaccari.matteo.unitdoctor.core;

import oracle.jrockit.jfr.StringConstantPool;

public interface UnitDoctorView {
    String getFromUnit();

    String getToUnit();

    double getInputNumber();

    void showConversion(double result);
}
