package name.vaccari.matteo.unitdoctor.core;

public class UnitDoctor {
    private UnitDoctorView view;

    public UnitDoctor(UnitDoctorView view) {
        this.view = view;
    }

    public void onChange() {
        String fromUnit = view.getFromUnit();
        String toUnit = view.getToUnit();
        double inputNumber = view.getInputNumber();
        if (fromUnit.equals("in") && toUnit.equals("cm")) {
            view.showConversion(inputNumber * 2.54);
        } else {
            view.showConversion((inputNumber - 32.0) * 5.0/9.0);
        }
    }
}
