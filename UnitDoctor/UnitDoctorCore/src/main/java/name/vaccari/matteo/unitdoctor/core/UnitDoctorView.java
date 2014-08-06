package name.vaccari.matteo.unitdoctor.core;

public interface UnitDoctorView {
    String getFromUnit();

    String getToUnit();

    double getInputNumber();

    void showConversion(double result);
}
