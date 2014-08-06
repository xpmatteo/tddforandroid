package name.vaccari.matteo.unitdoctor.core;

public class UnitDoctor {
    private UnitDoctorView view;

    public UnitDoctor(UnitDoctorView view) {
        this.view = view;
    }

    public void onChange() {
        double inputNumber = view.getInputNumber();

        view.showConversion(inputNumber * 2.54);
    }
}
