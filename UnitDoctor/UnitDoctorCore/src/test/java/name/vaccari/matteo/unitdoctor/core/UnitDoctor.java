package name.vaccari.matteo.unitdoctor.core;

public class UnitDoctor {
    private UnitDoctorView view;

    public UnitDoctor(UnitDoctorView view) {
        this.view = view;
    }

    public void onChange() {
        view.showConversion(25.4);
    }
}
