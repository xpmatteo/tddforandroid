package name.vaccari.matteo.unitdoctor.core;

public class UnitDoctor {
    private UnitDoctorView view;

    public UnitDoctor(UnitDoctorView view) {
        this.view = view;
    }

    public void convert() {
        double inputNumber = view.inputNumber();
        if (view.fromUnit().equals("in") && view.toUnit().equals("cm"))
            view.showResult(inputNumber * 2.54);
        else
            view.showConversionNotSupported();
    }
}
